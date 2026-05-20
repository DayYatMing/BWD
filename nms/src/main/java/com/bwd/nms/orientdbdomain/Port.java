package com.bwd.nms.orientdbdomain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.bwd.nms.orientdbdomain.Card;
import com.bwd.nms.orientdbdomain.enumeration.Connector;
import com.bwd.nms.orientdbdomain.enumeration.StatusType;
import com.orientechnologies.orient.core.sql.executor.OResult;

/**
 * A Port.
 */

public class Port implements Serializable {

    private static final long serialVersionUID = 1L;
    private Map<Object,Object> portMap = new HashMap<Object, Object>();

    private String id;
    private String roomlocation;
    private String position;
    private String vendor;
    private String comment;
    private String prrperties;
    private String thirdparty;
    private String portname;
    private String serviceid;
    private String portnumber;
    private String description;
    private String labelname;
    private String imagename;
    private String frequency;
    private String dls;
    private String wavelength;
    private Integer capacity;
    private String direction;
    private String connector;
    private String portstatus;
    private String createdby;
    private String lsiodf;
    private String site;
    private String node;
    private String shelf;
    private String slot;
    private String pmsource;
    private String portproperty;
    private String mappedportname;


    private String cardtype;
    private String updatedby;
    private Date datecreated;
    private Date dateupdated;

    private String patch1;
    private String patch1_comment;
    private String patch1_thirdparty;

    private String patch2;
    private String patch2_comment;
    private String patch2_thirdparty;

    private String patch3;
    private String patch3_comment;
    private String patch3_thirdparty;

    private String patch4;
    private String patch4_comment;
    private String patch4_thirdparty;

    private List<String> thirdpartysegments;
    private String route;
    private String segment;

    private List<String> device;

    public Port(String id, String portname, String mappedportname, String site, String roomlocation, String cardtype,
                String shelf, String slot, String position, String labelname, String direction,
                String connector, Integer capacity, String thirdparty, String comment,
                String frequency, String wavelength, String portstatus, String serviceid) {
        setId(id);
        setPortname(portname);
        setMappedportname(mappedportname);
        setSite(site);
        setRoomlocation(roomlocation);
        setCardtype(cardtype);
        setShelf(shelf);
        setSlot(slot);
        setPosition(position);
        setLabelname(labelname);
        setDirection(direction);
        setConnector(connector);
        setCapacity(capacity);
        setThirdparty(thirdparty);
        setComment(comment);
        setFrequency(frequency);
        setWavelength(wavelength);
        setPortstatus(portstatus);
        setServiceid(serviceid);
    }


    public String getMappedportname() {
        return mappedportname;
    }

    public void setMappedportname(String mappedportname) {
        this.mappedportname = mappedportname;
    }

    public String getPortproperty() {
        return portproperty;
    }

    public void setPortproperty(String portproperty) {
        this.portproperty = portproperty;
    }


    public String getPmsource() {
        return pmsource;
    }

    public void setPmsource(String pmsource) {
        this.pmsource = pmsource;
    }

    public Port() {}

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove



    public String getId() {
        return id;
    }

    public String getPatch3() {
        return patch3;
    }

    public void setPatch3(String patch3) {
        this.patch3 = patch3;
    }

    public String getPatch3_comment() {
        return patch3_comment;
    }

    public void setPatch3_comment(String patch3_comment) {
        this.patch3_comment = patch3_comment;
    }

    public String getPatch3_thirdparty() {
        return patch3_thirdparty;
    }

    public void setPatch3_thirdparty(String patch3_thirdparty) {
        this.patch3_thirdparty = patch3_thirdparty;
    }

    public String getPatch4() {
        return patch4;
    }

    public void setPatch4(String patch4) {
        this.patch4 = patch4;
    }

    public String getPatch4_comment() {
        return patch4_comment;
    }

    public void setPatch4_comment(String patch4_comment) {
        this.patch4_comment = patch4_comment;
    }

    public String getPatch4_thirdparty() {
        return patch4_thirdparty;
    }

    public void setPatch4_thirdparty(String patch4_thirdparty) {
        this.patch4_thirdparty = patch4_thirdparty;
    }

    public List<String> getThirdpartysegments() {
        return thirdpartysegments;
    }

    public void setThirdpartysegments(List<String> thirdpartysegments) {
        this.thirdpartysegments = thirdpartysegments;
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

    public String getPatch1() {
        return patch1;
    }

    public void setPatch1(String patch1) {
        this.patch1 = patch1;
    }

    public String getPatch1_comment() {
        return patch1_comment;
    }

    public void setPatch1_comment(String patch1_comment) {
        this.patch1_comment = patch1_comment;
    }

    public String getPatch1_thirdparty() {
        return patch1_thirdparty;
    }

    public void setPatch1_thirdparty(String patch1_thirdparty) {
        this.patch1_thirdparty = patch1_thirdparty;
    }

    public String getPatch2() {
        return patch2;
    }

    public void setPatch2(String patch2) {
        this.patch2 = patch2;
    }

    public String getPatch2_comment() {
        return patch2_comment;
    }

    public void setPatch2_comment(String patch2_comment) {
        this.patch2_comment = patch2_comment;
    }

    public String getPatch2_thirdparty() {
        return patch2_thirdparty;
    }

    public void setPatch2_thirdparty(String patch2_thirdparty) {
        this.patch2_thirdparty = patch2_thirdparty;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoomlocation() {
        return roomlocation;
    }

    public void setRoomlocation(String roomlocation) {
        this.roomlocation = roomlocation;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPrrperties() {
        return prrperties;
    }

    public void setPrrperties(String prrperties) {
        this.prrperties = prrperties;
    }

    public String getThirdparty() {
        return thirdparty;
    }

    public void setThirdparty(String thirdparty) {
        this.thirdparty = thirdparty;
    }

    public String getPortname() {
        return portname;
    }

    public void setPortname(String portname) {
        this.portname = portname;
    }

    public String getServiceid() {
        return serviceid;
    }

    public void setServiceid(String serviceid) {
        this.serviceid = serviceid;
    }

    public String getPortnumber() {
        return portnumber;
    }

    public void setPortnumber(String portnumber) {
        this.portnumber = portnumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLabelname() {
        return labelname;
    }

    public void setLabelname(String labelname) {
        this.labelname = labelname;
    }

    public String getImagename() {
        return imagename;
    }

    public void setImagename(String imagename) {
        this.imagename = imagename;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getDls() {
        return dls;
    }

    public void setDls(String dls) {
        this.dls = dls;
    }

    public String getWavelength() {
        return wavelength;
    }

    public void setWavelength(String wavelength) {
        this.wavelength = wavelength;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

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

    public String getPortstatus() {
        return portstatus;
    }

    public void setPortstatus(String portstatus) {
        this.portstatus = portstatus;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public String getLsiodf() {
        return lsiodf;
    }

    public void setLsiodf(String lsiodf) {
        this.lsiodf = lsiodf;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getShelf() {
        return shelf;
    }

    public void setShelf(String shelf) {
        this.shelf = shelf;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getCardtype() {
        return cardtype;
    }

    public void setCardtype(String cardtype) {
        this.cardtype = cardtype;
    }

    public String getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }

    public Date getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(Date datecreated) {
        this.datecreated = datecreated;
    }

    public Date getDateupdated() {
        return dateupdated;
    }

    public void setDateupdated(Date dateupdated) {
        this.dateupdated = dateupdated;
    }


    public List<String> getDevice() {
        return device;
    }

    public void setDevice(List<String> device) {
        this.device = device;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Port port = (Port) o;
        if (port.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), port.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Port{" +
            "id=" + getId() +
            ", portname='" + getPortname() + "'" +
            ", description='" + getDescription() + "'" +
            ", labelname='" + getLabelname() + "'" +
            ", imagename='" + getImagename() + "'" +
            ", frequency='" + getFrequency() + "'" +
            ", wavelength='" + getWavelength() + "'" +
            ", capacity=" + getCapacity() +
            ", connector='" + getConnector() + "'" +
            ", portstatus='" + getPortstatus() + "'" +
            ", createdby='" + getCreatedby() + "'" +
            ", updatedby='" + getUpdatedby() + "'" +
            ", datecreated='" + getDatecreated() + "'" +
            ", dateupdated='" + getDateupdated() + "'" +
            "}";
    }

    public Port(OResult port) {
        if( port.getProperty("@rid") != null)
            this.id = port.getProperty("@rid").toString();
        this.portname = port.getProperty("name");
        this.labelname = port.getProperty("labelname");
        this.createdby = port.getProperty("createdby");
        this.updatedby = port.getProperty("updatedby");
        this.datecreated = port.getProperty("datecreated");
        this.dateupdated = port.getProperty("dateupdated");
        this.description = port.getProperty("description");
        this.imagename = port.getProperty("imagename");
        this.frequency = port.getProperty("frequency");
        this.wavelength = port.getProperty("wavelength");
        this.portstatus = port.getProperty("portstatus");
        this.connector = port.getProperty("connector");
        this.capacity = port.getProperty("capacity");
        this.comment = port.getProperty("comment");
        this.thirdparty = port.getProperty("thirdparty");
        this.route = port.getProperty("route");
        this.segment = port.getProperty("segment");
        this.direction = port.getProperty("direction");
        this.pmsource = port.getProperty("pmsource");
        this.portproperty = port.getProperty("portproperty");

        this.device = (List<String>)port.getProperty("device");


        this.thirdpartysegments = (List<String>)port.getProperty("thirdpartysegments");
        //this.mappedportname =

        List<String> mappedPorts = (List<String>)port.getProperty( "odf");
        if( mappedPorts != null && mappedPorts.size() > 0)
            this.mappedportname = mappedPorts.get(0);


        mappedPorts = (List<String>)port.getProperty( "internalport");
        if( mappedPorts != null && mappedPorts.size() > 0 &&  this.mappedportname == null)
            this.mappedportname = mappedPorts.get(0);


        List<String> serviceids = (List<String>)port.getProperty( "serviceid");
        if( serviceids != null && serviceids.size() > 0)
            this.serviceid = serviceids.get(0);

        List<String> lsiodfs = (List<String>)port.getProperty("lsiodfs");
        if( lsiodfs != null && lsiodfs.size() > 0)
            this.lsiodf = lsiodfs.get(0);

        List<String> sites = (List<String>)port.getProperty("site");
        if( sites != null && sites.size() > 0)
            this.site = sites.get(0);


        List<String> rooms = (List<String>)port.getProperty("roomlocation");
        if( rooms != null && rooms.size() > 0)
            this.roomlocation = rooms.get(0);

        List<String> cardtypes = (List<String>)port.getProperty("cardtype");
        if( cardtypes != null && cardtypes.size() > 0)
            this.cardtype = cardtypes.get(0);

        List<String> shelfs = (List<String>)port.getProperty("shelf");
        if( shelfs != null && shelfs.size() > 0)
            this.shelf = shelfs.get(0);

        List<String> slots = (List<String>)port.getProperty("slot");
        if( slots != null && slots.size() > 0)
            this.slot = slots.get(0);

        List<String> positions = (List<String>)port.getProperty("position");
        if( positions != null && positions.size() > 0)
            this.position = positions.get(0);

        List<String> dlss = (List<String>)port.getProperty("dls");
        if( dlss != null && dlss.size() > 0)
            this.dls = dlss.get(0);

        if(port.getProperty("patch1") != null && port.getProperty("patch1") instanceof String ){
            this.patch1 =  port.getProperty("patch1");
            this.patch1_comment = port.getProperty("patch1_comment");
            this.patch1_thirdparty = port.getProperty("patch1_thirdparty");
        }else if (port.getProperty("patch1") != null && port.getProperty("patch1") instanceof List ) {
            List<String> patch1s = (List<String>) port.getProperty("patch1");
            if (patch1s != null && patch1s.size() > 0)
                this.patch1 = patch1s.get(0);

            List<String> patch1_comments = (List<String>) port.getProperty("patch1_comment");
            if (patch1_comments != null && patch1_comments.size() > 0)
                this.patch1_comment = patch1_comments.get(0);

            List<String> patch1_thirdpartys = (List<String>) port.getProperty("patch1_thirdparty");
            if (patch1_thirdpartys != null && patch1_thirdpartys.size() > 0)
                this.patch1_thirdparty = patch1_thirdpartys.get(0);
        }
        List<String> patch2s = (List<String>)port.getProperty("patch2");
        if( patch2s != null && patch2s.size() > 0)
            this.patch2 = patch2s.get(0);

        List<String> patch2_comments = (List<String>)port.getProperty("patch2_comment");
        if( patch2_comments != null && patch2_comments.size() > 0)
            this.patch2_comment = patch2_comments.get(0);

        List<String> patch2_thirdpartys = (List<String>)port.getProperty("patch2_thirdparty");
        if( patch2_thirdpartys != null && patch2_thirdpartys.size() > 0)
            this.patch2_thirdparty = patch2_thirdpartys.get(0);

        List<String> patch3s = (List<String>)port.getProperty("patch3");
        if( patch3s != null && patch3s.size() > 0)
            this.patch3 = patch3s.get(0);

        List<String> patch3_comments = (List<String>)port.getProperty("patch3_comment");
        if( patch3_comments != null && patch3_comments.size() > 0)
            this.patch3_comment = patch3_comments.get(0);

        List<String> patch3_thirdpartys = (List<String>)port.getProperty("patch3_thirdparty");
        if( patch3_thirdpartys != null && patch3_thirdpartys.size() > 0)
            this.patch3_thirdparty = patch3_thirdpartys.get(0);

        List<String> patch4s = (List<String>)port.getProperty("patch4");
        if( patch4s != null && patch4s.size() > 0)
            this.patch4 = patch4s.get(0);

        List<String> patch4_comments = (List<String>)port.getProperty("patch4_comment");
        if( patch4_comments != null && patch4_comments.size() > 0)
            this.patch4_comment = patch4_comments.get(0);

        List<String> patch4_thirdpartys = (List<String>)port.getProperty("patch4_thirdparty");
        if( patch4_thirdpartys != null && patch4_thirdpartys.size() > 0)
            this.patch4_thirdparty = patch4_thirdpartys.get(0);

    }

    public Map<Object,Object> portMap() {
        portMap.put("@rid",  this.id);
        portMap.put("name",  this.portname);
        portMap.put("roomlocation",  this.roomlocation);
        portMap.put("position",  this.position);
        portMap.put("vendor",  this.vendor);
        portMap.put("prrperties",  this.prrperties);
        portMap.put("serviceid",  this.serviceid);
        portMap.put("portnumber",  this.portnumber);
        portMap.put("comment",  this.comment);
        portMap.put("cardtype",  this.cardtype);
        portMap.put("dls",  this.dls);
        portMap.put("shelf",  this.shelf);
        portMap.put("node",  this.node);
        portMap.put("slot",  this.slot);
        portMap.put("labelname",  this.labelname);
        portMap.put("createdby",  this.createdby);
        portMap.put("updatedby",  this.updatedby);
        portMap.put("datecreated",  this.datecreated);
        portMap.put("dateupdated",  this.dateupdated);
        portMap.put("imagename",  this.imagename);
        //portMap.put("imagedata",  this.imagedata);
        portMap.put("frequency",  this.frequency);
        portMap.put("wavelength",  this.wavelength);
        portMap.put("portstatus",  this.portstatus);
        portMap.put("connector",  this.connector);
        portMap.put("capacity",  this.capacity);
        portMap.put("thirdparty", this.thirdparty);
        portMap.put("route", this.route);
        portMap.put("segment", this.segment);
        portMap.put("site", this.site);
        portMap.put("lsiodf", this.lsiodf);
        portMap.put("direction", this.direction);
        portMap.put("pmsource", this.pmsource);
        portMap.put("portproperty", this.portproperty);
        portMap.put("mappedportname", this.mappedportname);
        return portMap;
    }


    public Port(Card card , String portNumber) {
        String portName = 	card.getCardname() + ":P_" + portNumber
            +  card.getConnector() == null ? "" :  ":C_" +card.getConnector();

        this.portname = portName;
        this.labelname = portNumber.replace("-Tx", "").replace("-Rx", "");
        this.frequency = card.getFrequency();
        this.wavelength = card.getWavelength();
        this.portstatus = StatusType.FREE.toString();
        this.connector = Connector.fromString(card.getConnector()).toString();
        this.capacity = card.getCapacity();

    }
}
