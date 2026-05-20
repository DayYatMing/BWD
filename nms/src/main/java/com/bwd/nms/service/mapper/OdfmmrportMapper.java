package com.bwd.nms.service.mapper;

import com.bwd.nms.orientdbdomain.OdfmmrportData;
import com.orientechnologies.orient.core.sql.executor.OResult;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OdfmmrportMapper {

    public OdfmmrportData fromResult(OResult doc) {
        return new OdfmmrportData(doc);
    }
}
