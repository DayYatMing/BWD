package com.bwd.nms.orientdbdomain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.orientechnologies.orient.core.sql.executor.OResult;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceCorrelationData implements Serializable {
    private static final long serialVersionUID = 1L;

    Map<Object, Object> dataMap = new HashMap<Object, Object>();

    private String id;

    private String customername;

    private String customershortname;

    private String devicename;

    private String cardtype;

    private String connector;

    private String comment;

    private String direction;

    private String dls;

    private String model;

    private String vendor;

    private String nodename;

    private String shelf;

    private String slot;

    private String position;

    private String commnent;

    private String portname;

    private String frequency;

    private String wavelength;

    private String portstatus;

    private String thirdparty;

    private String roomlocation;

    private String routename;

    private String segmentname;

    private String servicename;

    private String servicecapacity;

    private String servicestate;

    private String sitename;

    private String patch;

    private String patch1;

    private String patch2;

    private Integer capacity;

    private String route;

    private String segment;

    private String lsiodf;

    private String thirdpartysegment;

    private List<String> thirdpartysegments;

    private String lsi_odfcardtype;

    private String lsi_roomlocation;

    private String lsi_shelf;

    private String lsi_position;

    private String lsi_port;

    private String lsi_connector;

    private String patch1_odfcardtype;

    private String patch1_roomlocation;

    private String patch1_shelf;

    private String patch1_position;

    private String patch1cardtype;

    private String patch1roomlocation;

    private String patch1_port;

    private String patch1_connector;

    private String patch1_comment;

    private String patch1_thirdparty;

    private String patch2_odfcardtype;

    private String patch2_roomlocation;

    private String patch2_shelf;

    private String patch2_position;

    private String patch2_cardtype;

    private String patch2_port;

    private String patch2_connector;

    private String patch2_comment;

    private String patch2_thirdparty;

    private String segmentnm;

    private String aendfiber;

    private String bendfiber;

    private String nename;

    private String neportname;

    private String tnumber;

    private String backhaul;

    private String backhaulvendor;

    private String backhauldetails;

    private String backhaulaend;

    private String offnetvendor;

    private String offnetid;

    private String offnetprotected;

    private String offnetaenddetails;

    private String offnetbenddetails;

    private String offnetsegment;

    private String tblId;

    @JsonProperty("customerserviceid")
    private String customerserviceid;


    public String getOffnetvendor() {
        return offnetvendor;
    }

    public void setOffnetvendor(String offnetvendor) {
        this.offnetvendor = offnetvendor;
    }

    public String getOffnetid() {
        return offnetid;
    }

    public void setOffnetid(String offnetid) {
        this.offnetid = offnetid;
    }

    public String getOffnetprotected() {
        return offnetprotected;
    }

    public void setOffnetprotected(String offnetprotected) {
        this.offnetprotected = offnetprotected;
    }

    public String getOffnetaenddetails() {
        return offnetaenddetails;
    }

    public void setOffnetaenddetails(String offnetaenddetails) {
        this.offnetaenddetails = offnetaenddetails;
    }

    public String getOffnetbenddetails() {
        return offnetbenddetails;
    }

    public void setOffnetbenddetails(String offnetbenddetails) {
        this.offnetbenddetails = offnetbenddetails;
    }

    public String getOffnetsegment() {
        return offnetsegment;
    }

    public void setOffnetsegment(String offnetsegment) {
        this.offnetsegment = offnetsegment;
    }

    public void setPatch1_comment(String patch1_comment) {
        this.patch1_comment = patch1_comment;
    }

    public void setPatch1_thirdparty(String patch1_thirdparty) {
        this.patch1_thirdparty = patch1_thirdparty;
    }

    public void setPatch2_comment(String patch2_comment) {
        this.patch2_comment = patch2_comment;
    }

    public void setPatch2_thirdparty(String patch2_thirdparty) {
        this.patch2_thirdparty = patch2_thirdparty;
    }

    public String getBackhauldetails() {
        return backhauldetails;
    }

    public void setBackhauldetails(String backhauldetails) {
        this.backhauldetails = backhauldetails;
    }

    public String getBackhaul() {
        return backhaul;
    }

    public void setBackhaul(String backhaul) {
        this.backhaul = backhaul;
    }

    public String getBackhaulvendor() {
        return backhaulvendor;
    }

    public void setBackhaulvendor(String backhaulvendor) {
        this.backhaulvendor = backhaulvendor;
    }

    public String getBackhaulaend() {
        return backhaulaend;
    }

    public void setBackhaulaend(String backhaulaend) {
        this.backhaulaend = backhaulaend;
    }

    public String getTnumber() {
        return tnumber;
    }

    public void setTnumber(String tnumber) {
        this.tnumber = tnumber;
    }

    public ServiceCorrelationData() {}

    public String getNename() {
        return nename;
    }

    public void setNename(String nename) {
        this.nename = nename;
    }

    public String getNeportname() {
        return neportname;
    }

    public void setNeportname(String neportname) {
        this.neportname = neportname;
    }

    public String getAendfiber() {
        return aendfiber;
    }

    public void setAendfiber(String aendfiber) {
        this.aendfiber = aendfiber;
    }

    public String getBendfiber() {
        return bendfiber;
    }

    public void setBendfiber(String bendfiber) {
        this.bendfiber = bendfiber;
    }

    public List<String> getThirdpartysegments() {
        return thirdpartysegments;
    }

    public void setThirdpartysegments(List<String> thirdpartysegments) {
        this.thirdpartysegments = thirdpartysegments;
    }

    public String getThirdpartysegment() {
        return thirdpartysegment;
    }

    public void setThirdpartysegment(String thirdpartysegment) {
        this.thirdpartysegment = thirdpartysegment;
    }

    public String getSegmentnm() {
        return segmentnm;
    }

    public void setSegmentnm(String segmentnm) {
        this.segmentnm = segmentnm;
    }

    public String getPatch1_comment() {
        return patch1_comment;
    }

    public String getPatch1_thirdparty() {
        return patch1_thirdparty;
    }

    public String getPatch2_comment() {
        return patch2_comment;
    }

    public String getPatch2_thirdparty() {
        return patch2_thirdparty;
    }

    public String getPatch1cardtype() {
        return patch1cardtype;
    }

    public void setPatch1cardtype(String patch1cardtype) {
        this.patch1cardtype = patch1cardtype;
    }

    public String getPatch1roomlocation() {
        return patch1roomlocation;
    }

    public void setPatch1roomlocation(String patch1roomlocation) {
        this.patch1roomlocation = patch1roomlocation;
    }

    public String getPatch2_odfcardtype() {
        return patch2_odfcardtype;
    }

    public void setPatch2_odfcardtype(String patch2_odfcardtype) {
        this.patch2_odfcardtype = patch2_odfcardtype;
    }

    public String getPatch2_roomlocation() {
        return patch2_roomlocation;
    }

    public void setPatch2_roomlocation(String patch2_roomlocation) {
        this.patch2_roomlocation = patch2_roomlocation;
    }

    public String getPatch2_shelf() {
        return patch2_shelf;
    }

    public void setPatch2_shelf(String patch2_shelf) {
        this.patch2_shelf = patch2_shelf;
    }

    public String getPatch2_position() {
        return patch2_position;
    }

    public void setPatch2_position(String patch2_position) {
        this.patch2_position = patch2_position;
    }

    public String getPatch2_cardtype() {
        return patch2_cardtype;
    }

    public void setPatch2_cardtype(String patch2_cardtype) {
        this.patch2_cardtype = patch2_cardtype;
    }

    public String getPatch2_port() {
        return patch2_port;
    }

    public void setPatch2_port(String patch2_port) {
        this.patch2_port = patch2_port;
    }

    public String getPatch2_connector() {
        return patch2_connector;
    }

    public void setPatch2_connector(String patch2_connector) {
        this.patch2_connector = patch2_connector;
    }

    public String getLsi_odfcardtype() {
        return lsi_odfcardtype;
    }

    public void setLsi_odfcardtype(String lsi_odfcardtype) {
        this.lsi_odfcardtype = lsi_odfcardtype;
    }

    public String getLsi_roomlocation() {
        return lsi_roomlocation;
    }

    public void setLsi_roomlocation(String lsi_roomlocation) {
        this.lsi_roomlocation = lsi_roomlocation;
    }

    public String getLsi_shelf() {
        return lsi_shelf;
    }

    public void setLsi_shelf(String lsi_shelf) {
        this.lsi_shelf = lsi_shelf;
    }

    public String getLsi_position() {
        return lsi_position;
    }

    public void setLsi_position(String lsi_position) {
        this.lsi_position = lsi_position;
    }

    public String getLsi_port() {
        return lsi_port;
    }

    public void setLsi_port(String lsi_port) {
        this.lsi_port = lsi_port;
    }

    public String getLsi_connector() {
        return lsi_connector;
    }

    public void setLsi_connector(String lsi_connector) {
        this.lsi_connector = lsi_connector;
    }

    public String getPatch1_odfcardtype() {
        return patch1_odfcardtype;
    }

    public void setPatch1_odfcardtype(String patch1_odfcardtype) {
        this.patch1_odfcardtype = patch1_odfcardtype;
    }

    public String getPatch1_roomlocation() {
        return patch1_roomlocation;
    }

    public void setPatch1_roomlocation(String patch1_roomlocation) {
        this.patch1_roomlocation = patch1_roomlocation;
    }

    public String getPatch1_shelf() {
        return patch1_shelf;
    }

    public void setPatch1_shelf(String patch1_shelf) {
        this.patch1_shelf = patch1_shelf;
    }

    public String getPatch1_position() {
        return patch1_position;
    }

    public void setPatch1_position(String patch1_position) {
        this.patch1_position = patch1_position;
    }

    public String getPatch1_port() {
        return patch1_port;
    }

    public void setPatch1_port(String patch1_port) {
        this.patch1_port = patch1_port;
    }

    public String getPatch1_connector() {
        return patch1_connector;
    }

    public void setPatch1_connector(String patch1_connector) {
        this.patch1_connector = patch1_connector;
    }

    public String getPortnumber() {
        return portnumber;
    }

    public void setPortnumber(String portnumber) {
        this.portnumber = portnumber;
    }

    private String portnumber;

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getConnector() {
        return connector;
    }

    public void setConnector(String connector) {
        this.connector = connector;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ServiceCorrelationData(OResult data) {
        this.portname = data.getProperty("name");
        if(data.getProperty("@rid") != null) {
            this.tblId = data.getProperty("@rid").toString();
        }
        if(data.getProperty("customerserviceid") != null) {
            this.customerserviceid = data.getProperty("customerserviceid").toString();
        }
        this.wavelength = data.getProperty("wavelength");
        this.frequency = data.getProperty("frequency");
        if(data.getProperty("capacity")  != null && data.getProperty("capacity") instanceof Integer )
            this.capacity = data.getProperty("capacity");
        else if(data.getProperty("capacity")  != null &&data.getProperty("capacity") instanceof String)
            this.capacity = Integer.parseInt(data.getProperty("capacity").toString().trim());
        else
            this.capacity = 0;
        if(data.getProperty("portstatus") != null)
            this.portstatus = data.getProperty("portstatus").toString();
        this.route = data.getProperty("route");
        this.segment = data.getProperty("segment");
        this.comment = data.getProperty("comment");
        this.connector = data.getProperty("connector");
        this.vendor = data.getProperty("vendor");
        this.shelf = data.getProperty("shelf");
        this.slot = data.getProperty("slot");
        this.position = data.getProperty("position");
        this.direction = data.getProperty("direction");
        this.portnumber = data.getProperty("portnumber");
        this.thirdpartysegment = data.getProperty("thirdpartysegment");
        this.thirdpartysegments = (List<String>)data.getProperty("thirdpartysegments");


        List<String> aendfibers = data.getProperty("aendfibers");
        if (aendfibers != null && aendfibers.size() > 0)
            this.aendfiber = aendfibers.get(0);

        List<String> bendfibers = data.getProperty("bendfibers");
        if (bendfibers != null && bendfibers.size() > 0)
            this.bendfiber = bendfibers.get(0);

        List<String> segments = data.getProperty("segmentnm");
        if (segments != null && segments.size() > 0)
            this.segmentnm = segments.get(0);

        List<String> vendors = data.getProperty("vendors");
        if (vendors != null && vendors.size() > 0)
            this.vendor = vendors.get(0);

        List<String> shelfs = data.getProperty("shelfs");
        if (shelfs != null && shelfs.size() > 0)
            this.shelf = shelfs.get(0);

        List<String> slots = data.getProperty("slots");
        if (slots != null && slots.size() > 0)
            this.slot = slots.get(0);

        List<String> positions = data.getProperty("positions");
        if (positions != null && positions.size() > 0)
            this.position = positions.get(0);

        List<String> lsiodfs = data.getProperty("lsiodf");
        if (lsiodfs != null && lsiodfs.size() > 0)
            this.lsiodf = lsiodfs.get(0);

        List<String> lsi_odfcardtypes = data.getProperty("lsi_odfcardtype");
        if (lsi_odfcardtypes != null && lsi_odfcardtypes.size() > 0)
            this.lsi_odfcardtype = lsi_odfcardtypes.get(0);

        List<String> lsi_roomlocations = data.getProperty("lsi_roomlocation");
        if (lsi_roomlocations != null && lsi_roomlocations.size() > 0)
            this.lsi_roomlocation = lsi_roomlocations.get(0);


        List<String> lsi_shelfs = data.getProperty("lsi_shelf");
        if (lsi_shelfs != null && lsi_shelfs.size() > 0)
            this.lsi_shelf = lsi_shelfs.get(0);

        List<String> lsi_positions = data.getProperty("lsi_position");
        if (lsi_positions != null && lsi_positions.size() > 0)
            this.lsi_position = lsi_positions.get(0);

        List<String> lsi_ports = data.getProperty("lsi_port");
        if (lsi_ports != null && lsi_ports.size() > 0)
            this.lsi_port = lsi_ports.get(0);

        List<String> lsi_connectors = data.getProperty("lsi_connector");
        if (lsi_connectors != null && lsi_connectors.size() > 0)
            this.lsi_connector = lsi_connectors.get(0);

        List<String> patch1s = data.getProperty("patch1");
        if (patch1s != null && patch1s.size() > 0)
            this.patch1 = patch1s.get(0);

        List<String> patch1cardtypes = data.getProperty("patch1_cardtype");
        if (patch1cardtypes != null && patch1cardtypes.size() > 0)
            this.patch1cardtype = patch1cardtypes.get(0);

        List<String> patch1roomlocations = data.getProperty("patch1_roomlocation");
        if (patch1roomlocations != null && patch1roomlocations.size() > 0)
            this.patch1roomlocation = patch1roomlocations.get(0);


        List<String> patch1_shelfs = data.getProperty("patch1_shelf");
        if (patch1_shelfs != null && patch1_shelfs.size() > 0)
            this.patch1_shelf = patch1_shelfs.get(0);

        List<String> patch1_positions = data.getProperty("patch1_position");
        if (patch1_positions != null && patch1_positions.size() > 0)
            this.patch1_position = patch1_positions.get(0);

        List<String> patch1_ports = data.getProperty("patch1_port");
        if (patch1_ports != null && patch1_ports.size() > 0)
            this.patch1_port = patch1_ports.get(0);

        List<String> patch1_connectors = data.getProperty("patch1_connector");
        if (patch1_connectors != null && patch1_connectors.size() > 0)
            this.patch1_connector = patch1_connectors.get(0);

        List<String> patch2s = data.getProperty("patch2");
        if (patch2s != null && patch2s.size() > 0)
            this.patch2 = patch2s.get(0);

        List<String> patch2cardtypes = data.getProperty("patch2_cardtype");
        if (patch2cardtypes != null && patch2cardtypes.size() > 0)
            this.patch2_cardtype = patch2cardtypes.get(0);

        List<String> patch2roomlocations = data.getProperty("patch2_roomlocation");
        if (patch2roomlocations != null && patch2roomlocations.size() > 0)
            this.patch2_roomlocation = patch2roomlocations.get(0);

        List<String> patch2_shelfs = data.getProperty("patch2_shelf");
        if (patch2_shelfs != null && patch2_shelfs.size() > 0)
            this.patch2_shelf = patch2_shelfs.get(0);

        List<String> patch2_positions = data.getProperty("patch2_position");
        if (patch2_positions != null && patch2_positions.size() > 0)
            this.patch2_position = patch2_positions.get(0);

        List<String> patch2_ports = data.getProperty("patch2_port");
        if (patch2_ports != null && patch2_ports.size() > 0)
            this.patch2_port = patch2_ports.get(0);

        List<String> patch2_connectors = data.getProperty("patch2_connector");
        if (patch2_connectors != null && patch2_connectors.size() > 0)
            this.patch2_connector = patch2_connectors.get(0);

        List<String> services = data.getProperty("service");
        if (services != null && services.size() > 0)
            this.servicename = services.get(0);

        List<String> cardtypes = data.getProperty("cardtype");
        if (cardtypes != null && cardtypes.size() > 0)
            this.cardtype = cardtypes.get(0);

        List<String> nodenames = data.getProperty("nodename");
        if (nodenames != null && nodenames.size() > 0)
            this.nodename = nodenames.get(0);

        List<String> roomloactions = data.getProperty("roomlocation");
        if (nodenames != null && nodenames.size() > 0)
            this.roomlocation = roomloactions.get(0);

        List<String> sites = data.getProperty("site");
        if (sites != null && sites.size() > 0)
            this.sitename = sites.get(0);

        List<String> customers = data.getProperty("customer");
        if (customers != null && customers.size() > 0)
            this.customername = customers.get(0);

        List<String> dlss = data.getProperty("dls");
        if (dlss != null && dlss.size() > 0)
            this.dls = dlss.get(0);


        List<String> patch1_comments = data.getProperty("patch1_comment");
        if (patch1_comments != null && patch1_comments.size() > 0)
            this.patch1_comment = patch1_comments.get(0);


        List<String> patch1_thirdpartys = data.getProperty("patch1_thirdparty");
        if (patch1_thirdpartys != null && patch1_thirdpartys.size() > 0)
            this.patch1_thirdparty = patch1_thirdpartys.get(0);


        List<String> patch2_comments = data.getProperty("patch2_comment");
        if (patch2_comments != null && patch2_comments.size() > 0)
            this.patch2_comment = patch2_comments.get(0);


        List<String> patch2_thirdpartys = data.getProperty("patch2_thirdparty");
        if (patch2_thirdpartys != null && patch2_thirdpartys.size() > 0)
            this.patch2_thirdparty = patch2_thirdpartys.get(0);

        List<String> offnetvendors = data.getProperty("offnetvendor");
        if (offnetvendors != null && offnetvendors.size() > 0)
            this.offnetvendor = offnetvendors.get(0);

        List<String> offnetids = data.getProperty("offnetid");
        if (offnetids != null && offnetids.size() > 0)
            this.offnetid = offnetids.get(0);

        List<Boolean> offnetprotecteds = data.getProperty("offnetprotected");
        if (offnetprotecteds != null && offnetprotecteds.size() > 0)
            this.offnetprotected = offnetprotecteds.get(0).toString();

        String offnetaend = "";
        List<String> offnetaends = data.getProperty("offnetaend");
        if (offnetaends != null && offnetaends.size() > 0)
            offnetaend =  offnetaends.get(0);

        List<String> offnetaenddetailss = data.getProperty("offnetaenddetails");
        if (offnetaenddetailss != null && offnetaenddetailss.size() > 0)
            this.offnetaenddetails = offnetaend + ": " + offnetaenddetailss.get(0);

        String offnetbend = "";
        List<String> offnetbends = data.getProperty("offnetbend");
        if (offnetbends != null && offnetbends.size() > 0)
            offnetbend =  offnetbends.get(0);

        List<String> offnetbenddetailss = data.getProperty("offnetbenddetails");
        if (offnetbenddetailss != null && offnetbenddetailss.size() > 0)
            this.offnetbenddetails =  offnetbend + ": " + offnetbenddetailss.get(0);

        List<String> offnetsegments = data.getProperty("offnetsegment");
        if (offnetsegments != null && offnetsegments.size() > 0)
            this.offnetsegment = offnetsegments.get(0);

        List<String> backhauls = data.getProperty("backhaul");
        if (backhauls != null && backhauls.size() > 0) {
            List<String> backhaulaends = data.getProperty("backhaulaend");
            if (backhaulaends != null && backhaulaends.size() > 0) {
                for(int backhaulindex = 0; backhaulindex<backhaulaends.size();backhaulindex++) {
                    if(this.portname.contains(backhaulaends.get(backhaulindex))){
                        if(this.backhaul == null) {
                            this.backhaul = backhauls.get(backhaulindex);
                            List<String> backhaulvendors = data.getProperty("backhaulvendor");
                            if(backhaulvendors != null && backhaulvendors.size() > 0)
                                this.backhaulvendor = backhaulvendors.get(backhaulindex);
                            List<String> backhauldetailss = data.getProperty("backhauldetails");
                            if(backhauldetailss != null && backhauldetailss.size() > 0)
                                this.backhauldetails = backhauldetailss.get(backhaulindex);
                        }else {
                            this.backhaul = this.backhaul + " , "+ backhauls.get(backhaulindex);
                            List<String> backhaulvendors = data.getProperty("backhaulvendor");
                            if(backhaulvendors != null && backhaulvendors.size() > 0)
                                this.backhaulvendor = this.backhaulvendor + " , "+  backhaulvendors.get(backhaulindex);
                            List<String> backhauldetailss = data.getProperty("backhauldetails");
                            if(backhauldetailss != null && backhauldetailss.size() > 0)
                                this.backhauldetails = this.backhauldetails  + " , "+  backhauldetailss.get(backhaulindex);
                        }
                    }
                }
            }
        }

    }

    public String getShelf() {
        return shelf;
    }

    public void setShelf(String shelf) {
        this.shelf = shelf;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public String getLsiodf() {
        return lsiodf;
    }

    public void setLsiodf(String lsiodf) {
        this.lsiodf = lsiodf;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getPatch1() {
        return patch1;
    }

    public void setPatch1(String patch1) {
        this.patch1 = patch1;
    }

    public String getPatch2() {
        return patch2;
    }

    public void setPatch2(String patch2) {
        this.patch2 = patch2;
    }

    public String getPatch() {
        return patch;
    }

    public void setPatch(String patch) {
        this.patch = patch;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.tblId = id;
    }

    public String getTblId() {
        return tblId;
    }

    public void setTblId(String tblId) {
        this.tblId = tblId;
    }


    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getCustomershortname() {
        return customershortname;
    }

    public void setCustomershortname(String customershortname) {
        this.customershortname = customershortname;
    }

    public String getDevicename() {
        return devicename;
    }

    public void setDevicename(String devicename) {
        this.devicename = devicename;
    }

    public String getCardtype() {
        return cardtype;
    }

    public void setCardtype(String cardtype) {
        this.cardtype = cardtype;
    }

    public String getDls() {
        return dls;
    }

    public void setDls(String dls) {
        this.dls = dls;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getNodename() {
        return nodename;
    }

    public void setNodename(String nodename) {
        this.nodename = nodename;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCommnent() {
        return commnent;
    }

    public void setCommnent(String commnent) {
        this.commnent = commnent;
    }

    public String getPortname() {
        return portname;
    }

    public void setPortname(String portname) {
        this.portname = portname;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getWavelength() {
        return wavelength;
    }

    public void setWavelength(String wavelength) {
        this.wavelength = wavelength;
    }

    public String getPortstatus() {
        return portstatus;
    }

    public void setPortstatus(String portstatus) {
        this.portstatus = portstatus;
    }

    public String getThirdparty() {
        return thirdparty;
    }

    public void setThirdparty(String thirdparty) {
        this.thirdparty = thirdparty;
    }

    public String getRoomlocation() {
        return roomlocation;
    }

    public void setRoomlocation(String roomlocation) {
        this.roomlocation = roomlocation;
    }

    public String getRoutename() {
        return routename;
    }

    public void setRoutename(String routename) {
        this.routename = routename;
    }

    public String getSegmentname() {
        return segmentname;
    }

    public void setSegmentname(String segmentname) {
        this.segmentname = segmentname;
    }

    public String getServicename() {
        return servicename;
    }

    public void setServicename(String servicename) {
        this.servicename = servicename;
    }

    public String getServicecapacity() {
        return servicecapacity;
    }

    public void setServicecapacity(String servicecapacity) {
        this.servicecapacity = servicecapacity;
    }

    public String getServicestate() {
        return servicestate;
    }

    public void setServicestate(String servicestate) {
        this.servicestate = servicestate;
    }

    public String getSitename() {
        return sitename;
    }


    public void setSitename(String sitename) {
        this.sitename = sitename;
    }

    public String getCustomerServiceId(){
        return customerserviceid;
    }

    public void setCustomerServiceId(String customerserviceid){
        this.customerserviceid = customerserviceid;
    }

    public Map<Object, Object> dataMap() {

        dataMap.put("serviceid", this.servicename);
        dataMap.put("tblid", this.tblId);
        dataMap.put("customerserviceid", this.customerserviceid);
        dataMap.put("comment", this.comment);
        dataMap.put("thirdparty", this.thirdparty);
        dataMap.put("portname", this.portname);
        dataMap.put("portstatus", this.portstatus);
        dataMap.put("route", this.route);
        dataMap.put("segment", this.segment);
        if(this.segment == null || this.segment.trim().isEmpty())
            dataMap.put("segment", this.dls);
        dataMap.put("patch1", this.patch1);
        dataMap.put("patch1_thirdparty", this.patch1_thirdparty);
        dataMap.put("patch1_comment", this.patch1_comment);
        dataMap.put("patch2", this.patch2);
        dataMap.put("patch2_thirdparty", this.patch2_thirdparty);
        dataMap.put("patch2_comment", this.patch2_comment);
        dataMap.put("thirdpartysegment", this.thirdpartysegment);
        return dataMap;
    }
}
