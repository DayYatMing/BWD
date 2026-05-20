package com.bwd.nms.service.mapper;

import com.bwd.nms.orientdbdomain.ServiceCorrelationData;
import com.orientechnologies.orient.core.sql.executor.OResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ServiceCorrelationMapper {
    public ServiceCorrelationData oResultToData(OResult data) {
        return new ServiceCorrelationData(data);
    }

    /**
     *
     * @param oResults
     * @return
     */
    public List<ServiceCorrelationData> oResultsToDataDTOs(List<OResult> oResults) {
        return oResults.stream().filter(Objects::nonNull).map(this::oResultToData).collect(Collectors.toList());
    }

    /**
     *
     * @param data
     * @return
     */
    public Map<Object,Object> dataToMap(ServiceCorrelationData data) {
        return data.dataMap();
    }
}
