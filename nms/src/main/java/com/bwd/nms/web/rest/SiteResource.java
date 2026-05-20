package com.bwd.nms.web.rest;

import com.bwd.nms.domain.Feature;
import com.bwd.nms.orientdbdomain.Site;
import com.bwd.nms.security.SecurityUtils;
import com.bwd.nms.service.NotificationService;
import com.bwd.nms.service.SiteService;
import com.bwd.nms.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * REST controller for managing Site.
 */
@RestController
@RequestMapping("/api")
public class SiteResource {

    private final Logger log = LoggerFactory.getLogger(SiteResource.class);

    private SiteService siteService;

    private NotificationService notificationService;

    public SiteResource(SiteService siteService, NotificationService notificationService) {
        this.siteService = siteService;
        this.notificationService = notificationService;
    }

    @GetMapping("/sites")
    public Flux<Site> getAllSites() {
        log.debug("REST request to get Sites");
        return siteService.getSites();
    }

    @PostMapping("/sites")
    public Mono<Void> createSite(@Valid @RequestBody Site site) {
        log.debug("REST request to save Site : {}", site);
        if (site.getId() != null) {
            throw new BadRequestAlertException("A new site cannot already have an ID", "SITE", "id exists");
        }

        return siteService.save(site)
            .then(Mono.defer(() ->
                notificationService
                    .createNotificationFromFeature(null, site, "INSERT", Feature.SITE)
                    .flatMap(notificationService::enabledNotification)
            ));
    }

    @PutMapping("/sites")
    public Mono<Void> updateSite(@Valid @RequestBody Site site) {
        log.debug("REST request to update Site : {}", site);

        String siteId;
        if (site.getId() == null) {
            return createSite(site);
        }else{
            siteId = site.getId();
        }

        return siteService.findById(site.getId())
            .flatMap(oldSite ->
                siteService.update(site)
                    .then(
                        notificationService.createNotificationFromFeature(oldSite, site, "UPDATE", Feature.SITE)
                            .flatMap(notificationService::enabledNotification)
                    )
            );
    }
}
