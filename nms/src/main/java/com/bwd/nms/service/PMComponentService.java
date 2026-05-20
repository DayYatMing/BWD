package com.bwd.nms.service;

import com.bwd.nms.mediationdomain.PMConfiguration;
import com.bwd.nms.mediationdomain.PMCustomer;
import com.bwd.nms.mediationdomain.PMService;
import com.bwd.nms.mediationdomain.PMSource;
import com.bwd.nms.mediationrepository.PMConfigurationRepository;
import com.bwd.nms.mediationrepository.PMCustomerRepository;
import com.bwd.nms.mediationrepository.PMServiceRepository;
import com.bwd.nms.mediationrepository.PMSourceRepository;
import com.bwd.nms.service.dto.PMConfigurationDTO;
import com.bwd.nms.service.dto.PMCustomerDTO;
import com.bwd.nms.service.dto.PMServiceDTO;
import com.bwd.nms.service.dto.PMSourceDTO;
import com.bwd.nms.service.mapper.PMConfigurationMapper;
import com.bwd.nms.service.mapper.PMCustomerMapper;
import com.bwd.nms.service.mapper.PMServiceMapper;
import com.bwd.nms.service.mapper.PMSourceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PMComponentService {

    private final Logger log = LoggerFactory.getLogger(PMComponentService.class);

    @Autowired
    public PMCustomerRepository pmCustomerRepository;

    @Autowired
    public PMServiceRepository pmServiceRepository;

    @Autowired
    public PMSourceRepository pmSourceRepository;

    @Autowired
    public PMConfigurationRepository pmConfigurationRepository;

    @Autowired
    public PMCustomerMapper pmCustomerMapper;

    @Autowired
    public PMServiceMapper pmServiceMapper;

    @Autowired
    public PMSourceMapper pmSourceMapper;

    @Autowired
    public PMConfigurationMapper pmConfigurationMapper;

    public Flux<PMCustomer> getPMCustomers() {
        return pmCustomerRepository.findCustomers();
    }

    public Flux<PMService> getPMServices(String customerId) {
        return pmServiceRepository.findServices(customerId);
    }

    public Flux<PMSource> getPMSources(String serviceId) {
        return pmSourceRepository.findSources(serviceId);
    }

    public Flux<PMConfiguration> getPMConfigurations(String serviceId) {
        return pmConfigurationRepository.findConfigurations(serviceId);
    }

    public Mono<Void> updateOrAddPMCustomer(PMCustomerDTO pmCustomerDTO) {
        PMCustomer pmc = pmCustomerMapper.covertToPMCustomer(pmCustomerDTO);
        pmc.setId(pmc.getId() == 0 ? null : pmc.getId());

        return pmCustomerRepository.save(pmc).then();
    }

    public Mono<Void> updateOrAddPMService(PMServiceDTO pmServiceDTO) {
        PMService pms = pmServiceMapper.covertToPMService(pmServiceDTO);
        pms.setId(pms.getId() == null || pms.getId() == 0 ? null : pms.getId());

        return pmServiceRepository.save(pms).then();
    }

    public Mono<Void> updateOrAddPMSource(PMSourceDTO pmSourceDTO) {
        PMSource pms = pmSourceMapper.covertToPMSource(pmSourceDTO);
        pms.setId(pms.getId() == null || pms.getId() == 0 ? null : pms.getId());

        return pmSourceRepository.save(pms).then();
    }

    public Mono<Void> updateOrAddPMConfiguration(PMConfigurationDTO pmConfigurationDTO) {
        PMConfiguration pmc = pmConfigurationMapper.covertToPMConfiguration(pmConfigurationDTO);
        pmc.setId(pmc.getId() == null || pmc.getId() == 0 ? null : pmc.getId());

        return pmConfigurationRepository.save(pmc).then();
    }
}
