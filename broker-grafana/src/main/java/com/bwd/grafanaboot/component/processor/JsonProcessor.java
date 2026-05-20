package com.bwd.grafanaboot.component.processor;

import com.bwd.grafanaboot.component.model.TimeSeries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class JsonProcessor {
    private static final Logger logger = LoggerFactory.getLogger(JsonProcessor.class);

    public List<TimeSeries> getTemsOCHPrettifiedData(List<Object[]> results) {

        List<TimeSeries> lstTs =  new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");

        for (Object[] result : results) {

            TimeSeries ts = new TimeSeries();

            ts.setTime(LocalDateTime.parse(result[0].toString(), formatter));
            ts.setMetric((String) result[1]);
            ts.setLinkFailSecIn((result[2] != null) ? Double.parseDouble(result[2].toString()) : null);
            ts.setLinkFailSecOut((result[3] != null) ? Double.parseDouble(result[3].toString()) : null);
            ts.setPhysicalErrCntIn((result[4] != null) ? Double.parseDouble(result[4].toString()) : null);
            ts.setPhysicalErrCntOut((result[5] != null) ? Double.parseDouble(result[5].toString()) : null);
            ts.setFrameChkSeqErrCntIn((result[6] != null) ? Double.parseDouble(result[6].toString()) : null);
            ts.setFrameChkSeqErrCntOut((result[7] != null) ? Double.parseDouble(result[7].toString()) : null);
            ts.setNumOfSecInBinTxLineCard((result[8] != null) ? Double.parseDouble(result[8].toString()) : null);
            ts.setNumOfSecInBinRxLineCard((result[9] != null) ? Double.parseDouble(result[9].toString()) : null);
            ts.setErrSecIn((result[10] != null) ? Double.parseDouble(result[10].toString()) : null);
            ts.setErrSecOut((result[11] != null) ? Double.parseDouble(result[11].toString()) : null);
            ts.setSeverelyErrSecIn((result[12] != null) ? Double.parseDouble(result[12].toString()) : null);
            ts.setSeverelyErrSecOut((result[13] != null) ? Double.parseDouble(result[13].toString()) : null);

            lstTs.add(ts);
        }



        return lstTs;
    }

}
