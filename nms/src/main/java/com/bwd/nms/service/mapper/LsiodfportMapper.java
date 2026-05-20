package com.bwd.nms.service.mapper;

import com.bwd.nms.orientdbdomain.LsiodfportData;
import com.orientechnologies.orient.core.sql.executor.OResult;
import org.springframework.stereotype.Service;

@Service
public class LsiodfportMapper {

    public LsiodfportData fromResult(OResult doc) {
        return new LsiodfportData(doc);
    }
}
