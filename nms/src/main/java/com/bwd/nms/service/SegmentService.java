package com.bwd.nms.service;

import com.bwd.nms.orientdbdomain.Segment;
import com.bwd.nms.orientdbdomain.Site;
import com.bwd.nms.orientdbrepository.SegmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SegmentService {

    private final Logger log = LoggerFactory.getLogger(SegmentService.class);

    @Autowired
    public SegmentRepository segmentRepository;

    public Flux<Segment> getSegments() {
        return segmentRepository.findAll();
    }

    public Mono<Void> update(Segment segment) {
        return segmentRepository.update(segment);
    }

    public Mono<Void> save(Segment segment) {
        return segmentRepository.save(segment);
    }

    public Mono<Segment> findById(String id) {
        return segmentRepository.findById(id);
    }
}
