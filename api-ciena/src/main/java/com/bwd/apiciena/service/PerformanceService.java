package com.bwd.apiciena.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PerformanceService {

    private final Logger log = LoggerFactory.getLogger(PerformanceService.class);

    @Autowired
    ExternalService externalService;

    final String PERFORMANCE_METRICS = "/pm/api/v3/query/metrics";

    public String getPM() {
        String body =
            """
            {
                "data": {
                    "attributes": {
                        "filter": [
                            "and",
                            [
                                "startsWith",
                                "networkElementName",
                                "AUSY-CWS5-5"
                            ],
                            [
                                "=",
                                "facilityNameNative",
                                "Port-1-3"
                            ],
                            [
                                "=",
                                "granularity",
                                "15_MINUTE"
                            ],
                            [
                                "or",
                                [
                                    "=",
                                    "parameterNative",
                                    "pcsunavailableSeconds"
                                ],
                                [
                                    "=",
                                    "parameterNative",
                                    "pcsseverelyErroredSeconds"
                                ],
                                [
                                    "=",
                                    "parameterNative",
                                    "pcserroredSeconds"
                                ],
                                [
                                    "=",
                                    "parameterNative",
                                    "rxCrcErroredPackets"
                                ],
                                [
                                    "=",
                                    "parameterNative",
                                    "txCrcErroredPackets"
                                ],
                                [
                                    "=",
                                    "parameterNative",
                                    "rxblock-errors"
                                ],
                                [
                                    "=",
                                    "parameterNative",
                                    "txblock-errors"
                                ]
                            ]
                        ],
                        "pageSize": 5000,
                        "range": {
                            "endTime": "2026-04-13T20:15:00.000-00:00",
                            "startTime": "2026-04-13T20:00:00.000-00:00",
                            "type": "absolute"
                        },
                        "sort": [
                            "-facilityNameNative",
                            "parameter",
                            "direction"
                        ]
                    }
                }
            }
            """;

        return externalService.sendAndProcessEvents(PERFORMANCE_METRICS, body);
    }
}
