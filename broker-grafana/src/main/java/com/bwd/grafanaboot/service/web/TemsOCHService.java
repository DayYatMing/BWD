package com.bwd.grafanaboot.service.web;

import com.bwd.grafanaboot.component.model.TimeSeries;
import com.bwd.grafanaboot.component.processor.JsonProcessor;
import com.bwd.grafanaboot.persistence.repository.TemsOCHRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class TemsOCHService {

    private static final Logger logger = LoggerFactory.getLogger(TemsOCHService.class);

    @Autowired
    TemsOCHRepository temsOCHRepository;

    public List<TimeSeries> getTemsOCH(String serviceId, String pmSource, LocalDateTime from, LocalDateTime to)
            throws JsonProcessingException {

        String temp = pmSource.substring(1, pmSource.length() - 1);
        List<String> pmSources = Arrays.asList(temp.split(","));
        List<Object[]> results = temsOCHRepository.findResults(serviceId, pmSources, from, to);

        JsonProcessor jsonProcessor = new JsonProcessor();
        List<TimeSeries> jsonList = jsonProcessor.getTemsOCHPrettifiedData(results);

        return jsonList;
    }

}
