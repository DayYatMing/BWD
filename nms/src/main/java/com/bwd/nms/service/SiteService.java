package com.bwd.nms.service;

import com.bwd.nms.orientdbdomain.Site;
import com.bwd.nms.orientdbrepository.SiteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SiteService {

    private final Logger log = LoggerFactory.getLogger(SiteService.class);

    @Autowired
    public SiteRepository siteRepository;

    public Flux<Site> getSites() {
        return siteRepository.findAll();
    }

    public Mono<Void> save(Site site) {
        return siteRepository.save(site);
    }

    public Mono<Void> update(Site site) {
        return siteRepository.update(site);
    }

    public Mono<Site> findById(String siteId) {
        return siteRepository.findById(siteId);
    }
}
