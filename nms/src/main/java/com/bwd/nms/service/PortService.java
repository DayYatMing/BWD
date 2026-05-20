package com.bwd.nms.service;

import com.bwd.nms.orientdbdomain.Card;
import com.bwd.nms.orientdbdomain.Port;
import com.bwd.nms.orientdbrepository.PortRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Service
public class PortService {

    private final Logger log = LoggerFactory.getLogger(PortService.class);

    @Autowired
    public PortRepository portRepository;

    public Flux<Port> getPorts() {
        return portRepository.findAll();
    }

    public Mono<Void> update(Port port) {
        return portRepository.update(port);
    }

    public Mono<Void> save(Port port) {
        return portRepository.save(port);
    }

    public Mono<Void> upload(FilePart bulkPorts) {

        if (bulkPorts == null) {
            return Mono.empty();
        }

        return bulkPorts.content()
            .map(dataBuffer -> {
                byte[] bytes = new byte[dataBuffer.readableByteCount()];
                dataBuffer.read(bytes);
                DataBufferUtils.release(dataBuffer);
                return new String(bytes, StandardCharsets.UTF_8);
            })
            .flatMap(lineChunk -> {
                String[] lines = lineChunk.split("\\r?\\n");
                return Flux.fromArray(lines);
            })
            .skip(3)
            .map(this::parseLineToPort)
            .flatMap(this::save)
            .then();
    }

    private Port parseLineToPort(String line) {
        String[] fields = line.split(";");
        Port port = new Port();

        port.setPortname(fields[0]);
        port.setMappedportname(fields[1]);
        port.setSite(fields[2]);
        port.setRoomlocation(fields[3]);
        port.setCardtype(fields[4]);
        port.setShelf(fields[5]);
        port.setSlot(fields[6]);
        port.setPosition(fields[7]);
        port.setPortnumber(fields[8]);
        port.setDirection(fields[9]);
        port.setConnector(fields[10]);
        port.setThirdparty(fields[11]);
        port.setComment(fields[12]);
        port.setFrequency(fields[13]);
        port.setWavelength(fields[14]);
        port.setPortstatus(fields[15]);
        port.setServiceid(fields[16]);

        return port;
    }
}
