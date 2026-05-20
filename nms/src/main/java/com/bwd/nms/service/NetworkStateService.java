package com.bwd.nms.service;

import com.bwd.nms.domain.NetworkState;
import com.bwd.nms.mediationdomain.PMConfiguration;
import com.bwd.nms.mediationrepository.PMConfigurationRepository;
import com.bwd.nms.repository.NetworkStateRepository;
import com.bwd.nms.service.dto.AlertsStatusResponse;
import com.bwd.nms.service.dto.MCPAlarmsResponse;
import com.bwd.nms.service.dto.NetworkStateDTO;
import com.bwd.nms.service.dto.PMConfigurationDTO;
import com.bwd.nms.service.mapper.NetworkStateMapper;
import com.bwd.nms.service.util.SvgUtil;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@Service
public class NetworkStateService {

    private final Logger log = LoggerFactory.getLogger(NetworkStateService.class);

    @Autowired
    public NetworkStateRepository  networkStateRepository;

    @Autowired
    public PMConfigurationRepository pmConfigurationRepository;

    @Autowired
    public ExternalService externalService;

    @Autowired
    SvgUtil svgUtil;

    NetworkStateMapper networkStateMapper = new NetworkStateMapper();

    public Mono<Void> uploadFile(String updatedBy, FilePart networkImage) {

        return DataBufferUtils.join(networkImage.content())
            .map(dataBuffer -> {
                byte[] bytes = new byte[dataBuffer.readableByteCount()];
                dataBuffer.read(bytes);
                DataBufferUtils.release(dataBuffer);
                return new String(bytes, StandardCharsets.UTF_8);
            })
            .flatMap(content -> {
                NetworkState networkState = new NetworkState();
                networkState.setNetworkimage(content);
                networkState.setUpdatedby(updatedBy);

                NetworkState encryptedState = encryptImage(networkState);
                return networkStateRepository.save(encryptedState);
            })
            .then();
    }

    public Mono<NetworkStateDTO> getNetworkState() {
        return networkStateRepository.findNetworkState()
            .map(networkStateMapper::networkStateToNetworkStateDTO);
    }

    public Mono<NetworkStateDTO> updateNetworkState(Long id, boolean withDetails) {

        Mono<List<PMConfigurationDTO>> lstPMConfigurationsMono = pmConfigurationRepository.findAllConfigurationDTO()
            .collectList();
        Mono<List<AlertsStatusResponse>> lstServiceStatusMono = Mono.fromCallable(() -> externalService.sendAndProcessEvents())
            .subscribeOn(Schedulers.boundedElastic());
        Mono<List<MCPAlarmsResponse>> lstMCPAlarmsResponsesMono = Mono.fromCallable(() -> externalService.sendAndProcessAlarms())
            .subscribeOn(Schedulers.boundedElastic());
        Mono<NetworkState> networkStateMono = networkStateRepository.findByIdNetworkState(id)
            .map(this::decryptImage);

        return Mono.zip(lstPMConfigurationsMono, lstServiceStatusMono, lstMCPAlarmsResponsesMono, networkStateMono)
            .flatMap(tuple -> {
                List<PMConfigurationDTO> lstPMConfigurationDTO = tuple.getT1();
                List<AlertsStatusResponse> lstServiceStatus = tuple.getT2();
                List<MCPAlarmsResponse> lstMCPAlarmsResponses = tuple.getT3();
                NetworkState networkState = tuple.getT4();

                return Mono.fromCallable(() ->
                        svgUtil.processSvgFile(lstPMConfigurationDTO, lstServiceStatus, lstMCPAlarmsResponses, networkState, withDetails)
                    )
                    .subscribeOn(Schedulers.boundedElastic())
                    .map(processedNetworkState -> networkStateMapper.networkStateToNetworkStateDTO(processedNetworkState))
                    .onErrorResume(ex -> {
                        log.error("SVG processing failed", ex);
                        return Mono.just(networkStateMapper.networkStateToNetworkStateDTO(networkState));
                    });
            });
    }

    private NetworkState encryptImage(NetworkState networkState) {
        if (networkState == null || networkState.getNetworkimage() == null) return networkState;

        String rawData = networkState.getNetworkimage();
        String prefix = "data:image/svg+xml;base64,";

        if (rawData.startsWith(prefix)) {
            rawData = rawData.substring(prefix.length());
        }

        String encoded = Base64.getEncoder()
            .encodeToString(rawData.getBytes(StandardCharsets.UTF_8));

        networkState.setNetworkimage(prefix + encoded);
        return networkState;
    }

    private NetworkState decryptImage(NetworkState networkState) {
        String data = networkState.getNetworkimage();
        if (data == null) return networkState;

        String prefix = "data:image/svg+xml;base64,";

        if (data.startsWith(prefix)) {
            data = data.substring(prefix.length());
        }

        byte[] decoded = Base64.getDecoder().decode(data);
        String result = new String(decoded, StandardCharsets.UTF_8);

        networkState.setNetworkimage(result);
        return networkState;
    }
}
