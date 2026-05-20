package com.bwd.nms.service;

import com.bwd.nms.mediationdomain.EstimatedFpCapacity;
import com.bwd.nms.mediationrepository.EstimatedFpCapacityRepository;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class ManageEstimatedCapacity {

    private final EstimatedFpCapacityRepository estimatedFpCapacityRepository;

    public ManageEstimatedCapacity(EstimatedFpCapacityRepository estimatedFpCapacityRepository) {
        this.estimatedFpCapacityRepository = estimatedFpCapacityRepository;
    }

    // ✅ GET (already good, just slightly cleaned)
    public Flux<EstimatedFpCapacity> getEstimatedFpCapacity() {

        return estimatedFpCapacityRepository.findEstimatedCapacityData()
            .doOnNext(capacity ->
                System.out.println("Service fetched EstimatedFpCapacity: " + capacity)
            );
    }

    public Mono<EstimatedFpCapacity> updateEstimatedFpCapacity(EstimatedFpCapacity estimatedFpCapacity) {

        return estimatedFpCapacityRepository.updateEstimatedCapacity(
                estimatedFpCapacity.getau_nz_fp1_dls16(),
                estimatedFpCapacity.getnz_hi_fp1_dls02(),
                estimatedFpCapacity.getnz_hi_fp1_dls13(),
                estimatedFpCapacity.getnz_hi_fp1_dls11(),
                estimatedFpCapacity.getau_hi_fp1_dls14(),
                estimatedFpCapacity.getau_hi_fp2_dls54(),
                estimatedFpCapacity.gethi_uf_fp1_dls27(),
                estimatedFpCapacity.gethi_uf_fp1_dls67(),
                estimatedFpCapacity.gethi_uf_fp2_dls57(),
                estimatedFpCapacity.gethi_uf_fp3_dls(),
                estimatedFpCapacity.getId()
            )
            .flatMap(rowsUpdated -> {
                if (rowsUpdated > 0) {
                    return Mono.just(estimatedFpCapacity);
                } else {
                    return Mono.empty();
                }
            });
    }
}
