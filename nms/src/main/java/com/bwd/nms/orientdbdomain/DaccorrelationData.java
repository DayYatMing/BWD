package com.bwd.nms.orientdbdomain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.orientechnologies.orient.core.sql.executor.OResult;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DaccorrelationData implements Serializable {

    private static final long serialVersionUID = 1L;
    private Map<Object, Object> dacportMap = new HashMap<Object, Object>();
    private String fromcomment;
    private String fromdevice;
    private String fromserviceid;
    private String fromconnectortype;
    private String fromdls;
    private String fromfrequency;
    private String fromport;
    private String fromrr;
    private String fromshelf;
    private String fromsite;
    private String fromslot;
    private String fromname;
    private String tocomment;
    private String todevice;
    private String toconnectortype;
    private String toserviceid;
    private String todls;
    private String tofrequency;
    private String toport;
    private String torr;
    private String toshelf;
    private String tosite;
    private String toslot;
    private String toname;
    private String fromtblid;
    private String totblid;
    private String fromPortStatus;
    private String toPortStatus;

    public String getFromserviceid() {
        return fromserviceid;
    }

    public void setFromserviceid(String fromserviceid) {
        this.fromserviceid = fromserviceid;
    }

    public String getToserviceid() {
        return toserviceid;
    }

    public void setToserviceid(String toserviceid) {
        this.toserviceid = toserviceid;
    }

    public String getFromname() {
        return fromname;
    }

    public void setFromname(String fromname) {
        this.fromname = fromname;
    }

    public String getToname() {
        return toname;
    }

    public void setToname(String toname) {
        this.toname = toname;
    }

    public String getFromcomment() {
        return fromcomment;
    }

    public void setFromcomment(String fromcomment) {
        this.fromcomment = fromcomment;
    }

    public String getFromdls() {
        return fromdls;
    }

    public void setFromdls(String fromdls) {
        this.fromdls = fromdls;
    }

    public String getFromfrequency() {
        return fromfrequency;
    }

    public void setFromfrequency(String fromfrequency) {
        this.fromfrequency = fromfrequency;
    }

    public String getFromport() {
        return fromport;
    }

    public void setFromport(String fromport) {
        this.fromport = fromport;
    }

    public String getFromrr() {
        return fromrr;
    }

    public void setFromrr(String fromrr) {
        this.fromrr = fromrr;
    }

    public String getFromshelf() {
        return fromshelf;
    }

    public void setFromshelf(String fromshelf) {
        this.fromshelf = fromshelf;
    }

    public String getFromsite() {
        return fromsite;
    }

    public void setFromsite(String fromsite) {
        this.fromsite = fromsite;
    }

    public String getFromslot() {
        return fromslot;
    }

    public void setFromslot(String fromslot) {
        this.fromslot = fromslot;
    }

    public String getTocomment() {
        return tocomment;
    }

    public void setTocomment(String tocomment) {
        this.tocomment = tocomment;
    }

    public String getTodls() {
        return todls;
    }

    public void setTodls(String todls) {
        this.todls = todls;
    }

    public String getTofrequency() {
        return tofrequency;
    }

    public void setTofrequency(String tofrequency) {
        this.tofrequency = tofrequency;
    }

    public String getToport() {
        return toport;
    }

    public void setToport(String toport) {
        this.toport = toport;
    }

    public String getTorr() {
        return torr;
    }

    public void setTorr(String torr) {
        this.torr = torr;
    }

    public String getToshelf() {
        return toshelf;
    }

    public void setToshelf(String toshelf) {
        this.toshelf = toshelf;
    }

    public String getTosite() {
        return tosite;
    }

    public void setTosite(String tosite) {
        this.tosite = tosite;
    }

    public String getToslot() {
        return toslot;
    }

    public void setToslot(String toslot) {
        this.toslot = toslot;
    }

    public String getFromdevice() {
        return fromdevice;
    }

    public void setFromdevice(String fromdevice) {
        this.fromdevice = fromdevice;
    }

    public String getFromconnectortype() {
        return fromconnectortype;
    }

    public void setFromconnectortype(String fromconnectortype) {
        this.fromconnectortype = fromconnectortype;
    }

    public String getTodevice() {
        return todevice;
    }

    public void setTodevice(String todevice) {
        this.todevice = todevice;
    }

    public String getToconnectortype() {
        return toconnectortype;
    }

    public void setToconnectortype(String toconnectortype) {
        this.toconnectortype = toconnectortype;
    }

    public String getFromtblid() {
        return fromtblid;
    }

    public void setFromtblid(String fromtblid) {
        this.fromtblid = fromtblid;
    }

    public String getTotblid() {
        return totblid;
    }

    public void setTotblid(String totblid) {
        this.totblid = totblid;
    }

    public String getFromPortStatus() {
        return fromPortStatus;
    }

    public void setFromPortStatus(String fromPortStatus) {
        this.fromPortStatus = fromPortStatus;
    }

    public String getToPortStatus() {
        return toPortStatus;
    }

    public void setToPortStatus(String toPortStatus) {
        this.toPortStatus = toPortStatus;
    }

    public DaccorrelationData(){

    }

    public DaccorrelationData(OResult dacport) {

        if (dacport.getProperty("fromport") != null) {
            this.fromcomment = dacport.getProperty("fromcomment");
            this.fromdls = dacport.getProperty("fromdls");
            this.fromfrequency = dacport.getProperty("fromfrequency");
            this.fromport = dacport.getProperty("fromport");
            this.fromrr = dacport.getProperty("fromrr");
            this.fromshelf = dacport.getProperty("fromshelf");
            this.fromslot = dacport.getProperty("fromslot");
            this.fromsite = dacport.getProperty("fromsite");
            this.fromname = dacport.getProperty("fromname");
            this.fromdevice = dacport.getProperty("fromdevice");
            this.fromconnectortype = dacport.getProperty("fromconnectortype");
            this.fromserviceid = dacport.getProperty("fromserviceid");
            this.fromtblid = dacport.getProperty("fromtblid").toString();
            this.totblid = dacport.getProperty("totblid").toString();
            this.fromPortStatus = dacport.getProperty("fromportstatus");
            // this.fromcustomerserviceid = dacport.getProperty("fromcustomerserviceid");

            List<String> toserviceids = (List<String>) dacport.getProperty("toserviceid");
            if (toserviceids != null && toserviceids.size() > 0)
                this.toserviceid = toserviceids.get(0);

            List<String> tocomments = (List<String>) dacport.getProperty("tocomments");
            if (tocomments != null && tocomments.size() > 0)
                this.tocomment = tocomments.get(0);

            List<String> tonames = (List<String>) dacport.getProperty("tonames");
            if (tonames != null && tonames.size() > 0)
                this.toname = tonames.get(0);

            List<String> todlss = (List<String>) dacport.getProperty("todlss");
            if (todlss != null && todlss.size() > 0)
                this.todls = todlss.get(0);

            List<String> tofrequencys = (List<String>) dacport.getProperty("tofrequencys");
            if (tofrequencys != null && tofrequencys.size() > 0)
                this.tofrequency = tofrequencys.get(0);

            List<String> toports = (List<String>) dacport.getProperty("toports");
            if (toports != null && toports.size() > 0)
                this.toport = toports.get(0);

            List<String> torrs = (List<String>) dacport.getProperty("torrs");
            if (torrs != null && torrs.size() > 0)
                this.torr = torrs.get(0);

            List<String> toshelfs = (List<String>) dacport.getProperty("toshelfs");
            if (toshelfs != null && toshelfs.size() > 0)
                this.toshelf = toshelfs.get(0);

            List<String> tosites = (List<String>) dacport.getProperty("tosites");
            if (tosites != null && tosites.size() > 0)
                this.tosite = tosites.get(0);

            List<String> toslots = (List<String>) dacport.getProperty("toslots");
            if (toslots != null && toslots.size() > 0)
                this.toslot = toslots.get(0);

            List<String> todevices = (List<String>) dacport.getProperty("todevices");
            if (todevices != null && todevices.size() > 0)
                this.todevice = todevices.get(0);

            List<String> toconnectortypes = (List<String>) dacport.getProperty("toconnectortypes");
            if (toconnectortypes != null && toconnectortypes.size() > 0)
                this.toconnectortype = toconnectortypes.get(0);

            List<String> toportstatus = (List<String>) dacport.getProperty("toportstatus");
            if (toportstatus != null && toportstatus.size() > 0)
                this.toPortStatus = toportstatus.get(0);

        }
    }

    public Map<Object, Object> dacportMap() {
        dacportMap.put("fromcomment", this.fromcomment);
        dacportMap.put("fromdls", this.fromdls);
        dacportMap.put("fromfrequency", this.fromfrequency);
        dacportMap.put("fromport", this.fromport);
        dacportMap.put("fromrr", this.fromrr);
        dacportMap.put("fromshelf", this.fromshelf);
        dacportMap.put("fromsite", this.fromsite);
        dacportMap.put("fromslot", this.fromslot);
        dacportMap.put("tocomment", this.tocomment);
        dacportMap.put("todls", this.todls);
        dacportMap.put("tofrequency", this.tofrequency);
        dacportMap.put("toport", this.toport);
        dacportMap.put("torr", this.torr);
        dacportMap.put("toshelf", this.toshelf);
        dacportMap.put("tosite", this.tosite);
        dacportMap.put("toslot", this.toslot);
        dacportMap.put("fromname", this.fromname);
        dacportMap.put("toname", this.toname);
        dacportMap.put("fromdevice", this.fromdevice);
        dacportMap.put("fromconnectortype", this.fromconnectortype);
        dacportMap.put("todevice", this.todevice);
        dacportMap.put("toconnectortype", this.toconnectortype);
        dacportMap.put("fromserviceid", this.fromserviceid);
        dacportMap.put("toserviceid", this.toserviceid);
        dacportMap.put("fromtblid", this.fromtblid);
        dacportMap.put("totblid", this.totblid);
        dacportMap.put("fromportstatus", this.fromPortStatus);
        dacportMap.put("toportstatus", this.toPortStatus);


        return dacportMap;
    }

}
