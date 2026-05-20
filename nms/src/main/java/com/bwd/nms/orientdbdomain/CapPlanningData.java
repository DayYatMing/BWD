package com.bwd.nms.orientdbdomain;

import com.orientechnologies.orient.core.sql.executor.OResult;

import java.io.Serializable;
import java.util.List;

public class CapPlanningData implements Serializable {

    private static final long serialVersionUID = 1L;
    private String customername;
    private String cardtype;
    private String vendor;
    private String portstatus;
    private String servicename;
    private String sitename;
    private String segmentnm;
    private Integer capacity;
    private String route;
    private String portname;
    private String frequency;
    private String lsiodf;
    private String networktype;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;

    public CapPlanningData(){}

    public CapPlanningData(String id, String segmentnm, String sitename, Integer capacity, String portstatus,
                           String cardtype, String networktype, String frequency, String route, String vendor,
                           String servicename, String customername, String portname, String lsiodf) {
        setId(id);
        setSegmentnm(segmentnm);
        setSitename(sitename);
        setCapacity(capacity);
        setPortstatus(portstatus);
        setCardtype(cardtype);
        setNetworktype(networktype);
        setFrequency(frequency);
        setRoute(route);
        setVendor(vendor);
        setServicename(servicename);
        setCustomername(customername);
        setPortname(portname);
        setLsiodf(lsiodf);
    }

    public String getLsiodf() {
        return lsiodf;
    }

    public void setLsiodf(String lsiodf) {
        this.lsiodf = lsiodf;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getPortname() {
        return portname;
    }

    public void setPortname(String portname) {
        this.portname = portname;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getCardtype() {
        return cardtype;
    }

    public void setCardtype(String cardtype) {
        this.cardtype = cardtype;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getPortstatus() {
        return portstatus;
    }

    public void setPortstatus(String portstatus) {
        this.portstatus = portstatus;
    }

    public String getServicename() {
        return servicename;
    }

    public void setServicename(String servicename) {
        this.servicename = servicename;
    }

    public String getSitename() {
        return sitename;
    }

    public void setSitename(String sitename) {
        this.sitename = sitename;
    }

    public String getSegmentnm() {
        return segmentnm;
    }

    public void setSegmentnm(String segmentnm) {
        this.segmentnm = segmentnm;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getNetworktype() {
        return networktype;
    }

    public void setNetworktype(String networktype) {
        this.networktype = networktype;
    }

    public CapPlanningData(OResult data) {
        this.portname = data.getProperty("name");
        if (data.getProperty("frequency") != null)
            this.frequency = data.getProperty("frequency");
        if (data.getProperty("capacity") != null)
            this.capacity = data.getProperty("capacity");
        if (data.getProperty("portstatus") != null)
            this.portstatus = data.getProperty("portstatus").toString();
        this.route = data.getProperty("route");
        this.vendor = data.getProperty("vendor");

        List<String> lsiodfs = data.getProperty("lsiodf");
        if (lsiodfs != null && !lsiodfs.isEmpty())
            this.lsiodf = lsiodfs.get(0);

        if (data.getProperty("networktype") != null && data.getProperty("networktype") instanceof List) {
            List<String> networktypes = data.getProperty("networktype");
            if (networktypes != null && !networktypes.isEmpty())
                this.networktype = networktypes.get(0);
        } else
            this.networktype = data.getProperty("networktype");

        if (data.getProperty("segmentnm") != null && data.getProperty("segmentnm") instanceof List) {
            List<String> segments = data.getProperty("segmentnm");
            if (segments != null && !segments.isEmpty())
                this.segmentnm = segments.get(0);
        } else
            this.segmentnm = data.getProperty("segmentnm");

        List<String> vendors = data.getProperty("vendors");
        if (vendors != null && !vendors.isEmpty())
            this.vendor = vendors.get(0);

        List<String> services = data.getProperty("service");
        if (services != null && !services.isEmpty())
            this.servicename = services.get(0);
        else
            this.servicename = data.getProperty("serviceid");


        if (data.getProperty("cardtype") != null && data.getProperty("cardtype") instanceof List) {
            List<String> cardtypes = data.getProperty("cardtype");
            if (cardtypes != null && !cardtypes.isEmpty())
                this.cardtype = cardtypes.get(0);
        }else
            this.cardtype = data.getProperty("cardtype");

        if (data.getProperty("site") != null && data.getProperty("site") instanceof List) {
            List<String> sites = data.getProperty("site");
            if (sites != null && !sites.isEmpty())
                this.sitename = sites.get(0);
        } else
            this.sitename = data.getProperty("site");

        List<String> customers = data.getProperty("customer");
        if (customers != null && !customers.isEmpty())
            this.customername = customers.get(0);

        if (this.cardtype == null) {
            this.cardtype = data.getProperty("device");

        }
    }
}
