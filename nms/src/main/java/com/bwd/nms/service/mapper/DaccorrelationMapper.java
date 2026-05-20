package com.bwd.nms.service.mapper;

import com.bwd.nms.orientdbdomain.DaccorrelationData;
import com.orientechnologies.orient.core.sql.executor.OResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DaccorrelationMapper {

    public DaccorrelationData fromResult(OResult dacPort) {
        return new DaccorrelationData(dacPort);
    }

    public List<DaccorrelationData> oResultsToDacPortDTOs(List<OResult> oResults) {
        return oResults.stream().filter(Objects::nonNull).map(this::fromResult).collect(Collectors.toList());
    }

    public Map<Object,Object> dacportToMap(DaccorrelationData dacPort) {
        return dacPort.dacportMap();
    }
}
