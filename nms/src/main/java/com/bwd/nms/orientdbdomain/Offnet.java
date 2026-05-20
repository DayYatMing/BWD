package com.bwd.nms.orientdbdomain;

//import com.orientechnologies.orient.core.sql.executor.OResult;

import com.orientechnologies.orient.core.sql.executor.OResult;

import java.io.Serializable;
import java.util.*;

public class Offnet implements Serializable {

    private static final long serialVersionUID = 1L;
    private Map<Object,Object> offnetMap = new HashMap<Object, Object>();

    private Integer capacity;
    private String comment;
    private String createdby;
    private String datecreated;
    private String dateupdated;
    private String labelname;
    private String name;
    private boolean protectedcircuit;
    private String vendorname;
    private String aenddetails;
    private String benddetails;
    private String status;
    private String updatedby;
    private String aend;
    private String bend;
    private String customer;
    private String service;
    private String segment;
    private String frequency;

    public Offnet(String id, String vendorname, String name, Integer capacity, Boolean protectedcircuit, String segments, String aend, String aenddetails, String bend, String benddetails, String customer, String service, String status, String comment) {
        setId(id);
        setVendorname(vendorname);
        setName(name);
        setCapacity(capacity);
        setProtectedcircuit(protectedcircuit);
        setSegment(segments);
        setAend(aend);
        setAenddetails(aenddetails);
        setBend(bend);
        setBenddetails(benddetails);
        setCustomer(customer);
        setService(service);
        setStatus(status);
        setComment(comment);
    }


    public String getVendorname() {
        return vendorname;
    }
    public void setVendorname(String vendorname) {
        this.vendorname = vendorname;
    }
    public String getAenddetails() {
        return aenddetails;
    }
    public void setAenddetails(String aenddetails) {
        this.aenddetails = aenddetails;
    }
    public String getBenddetails() {
        return benddetails;
    }
    public void setBenddetails(String benddetails) {
        this.benddetails = benddetails;
    }
    public String getFrequency() {
        return frequency;
    }
    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    //private ArrayList ports;
    private String id;


    public String getSegment() {
        return segment;
    }
    public void setSegment(String segment) {
        this.segment = segment;
    }

    private Set<Site> sites = new HashSet<>();



    public Set<Site> getSites() {
        return sites;
    }
    public void setSites(Set<Site> sites) {
        this.sites = sites;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Integer getCapacity() {
        return capacity;
    }
    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getCreatedby() {
        return createdby;
    }
    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }
    public String getDatecreated() {
        return datecreated;
    }
    public void setDatecreated(String datecreated) {
        this.datecreated = datecreated;
    }
    public String getDateupdated() {
        return dateupdated;
    }
    public void setDateupdated(String dateupdated) {
        this.dateupdated = dateupdated;
    }
    public String getLabelname() {
        return labelname;
    }
    public void setLabelname(String labelname) {
        this.labelname = labelname;
    }
    public String getName() {
        return name.trim();
    }
    public void setName(String name) {
        this.name = name.trim();
    }
    public boolean getProtectedcircuit() {
        return protectedcircuit;
    }
    public void setProtectedcircuit(boolean protectedcircuit) {
        this.protectedcircuit = protectedcircuit;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getUpdatedby() {
        return updatedby;
    }
    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }
    public String getAend() {
        return aend;
    }
    public void setAend(String aend) {
        this.aend = aend;
    }
    public String getBend() {
        return bend;
    }
    public void setBend(String bend) {
        this.bend = bend;
    }
    public String getCustomer() {
        return customer;
    }
    public void setCustomer(String customer) {
        this.customer = customer;
    }
    public String getService() {
        return service;
    }
    public void setService(String service) {
        this.service = service;
    }
//	public ArrayList<String> getPorts() {
//		return ports;
//	}
//	public void setPort(ArrayList<String> ports) {
//		this.ports = ports;
//	}



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Segment segment = (Segment) o;
        if (segment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), segment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Offnet{" +
            "OffnetID='" + getName() + "'" +
            ", VendorName='" + getVendorname() + "'" +
            ", Capacity='" + getCapacity() + "'" +
            ", Frequency='" + getFrequency() + "'" +
            ", Segment='" + getSegment() + "'" +
            ", AEnd='" + getAend() + "'" +
            ", AEndDetails='" + getAenddetails() + "'" +
            ", BEnd='" + getBend() + "'" +
            ", BEndDetails='" + getBenddetails() + "'" +
            ", Customer='" + getCustomer() + "'" +
            ", Service='" + getService() + "'" +
            ", Misc='" + getComment() + "'" +
            "}";
    }

    public Offnet(OResult offnet) {
        this.id = offnet.getProperty("@rid").toString();
        this.name = offnet.getProperty("name");
        this.capacity = offnet.getProperty("capacity");
        this.labelname = offnet.getProperty("labelname");
        this.comment = offnet.getProperty("comment");
        this.protectedcircuit = offnet.getProperty("protectedcircuit");
        this.vendorname = offnet.getProperty("vendorname");
        this.status = offnet.getProperty("status");
        this.createdby = offnet.getProperty("createdby");
        this.createdby = offnet.getProperty("createdby");
        this.updatedby = offnet.getProperty("updatedby");
        this.datecreated = offnet.getProperty("datecreated");
        this.dateupdated = offnet.getProperty("dateupdated");


        if(offnet.getProperty("aend") != null && ((List<String>)offnet.getProperty("aend")).size() >0) {
            this.aenddetails = offnet.getProperty("aenddetails");
            this.benddetails = offnet.getProperty("benddetails");
            Site siteA = new Site();
            Site siteB = new Site();
            siteA.setSitename(((List<String>)offnet.getProperty("aend")).get(0));
            siteB.setSitename(((List<String>)offnet.getProperty("bend")).get(0));

            siteA.setLatitude(Float.parseFloat(((List<String>)offnet.getProperty("aendlat")).get(0)));
            siteB.setLatitude(Float.parseFloat(((List<String>)offnet.getProperty("bendlat")).get(0)));
            siteA.setLongitude(Float.parseFloat(((List<String>)offnet.getProperty("aendlong")).get(0)));
            siteB.setLongitude(Float.parseFloat(((List<String>)offnet.getProperty("bendlong")).get(0)));
            sites.add(siteA);
            sites.add(siteB);
        }
        List<String> customers = offnet.getProperty("customer");
        for(String customer:safe(customers)) {
            if(this.customer == null)
                this.customer = customer;
            else
                this.customer = this.customer + "," + customer;
        }
        List<String> services = offnet.getProperty("service");
        for(String service:safe(services)){
            if(this.service == null)
                this.service = service;
            else
                this.service = this.service + "," + service;
        }
        List<String> aends = offnet.getProperty("aend");
        if (aends != null && aends.size() > 0)
            this.aend = aends.get(0);
        List<String> bends = offnet.getProperty("bend");
        if (bends != null && bends.size() > 0)
            this.bend = bends.get(0);
        List<String> segments = offnet.getProperty("segments");
        if (segments != null && segments.size() > 0)
            this.segment = segments.get(0);

    }

    public Map<Object,Object> offnetMap() {
        offnetMap.put("@rid",  this.id);
        offnetMap.put("name",  this.name.trim());
        offnetMap.put("comment",  this.comment);
        offnetMap.put("labelname",  this.name.trim());
        offnetMap.put("capacity",  this.capacity);
        offnetMap.put("protectedcircuit",this.protectedcircuit);
        offnetMap.put("aend",  this.aend);
        offnetMap.put("bend",  this.bend);
        offnetMap.put("aenddetails",  this.aenddetails);
        offnetMap.put("vendorname",  this.vendorname);
        offnetMap.put("status",  this.status);
        offnetMap.put("benddetails",  this.benddetails);
        offnetMap.put("createdby",  this.createdby);
        offnetMap.put("updatedby",  this.updatedby);
        offnetMap.put("customer",  this.customer);
        offnetMap.put("frequency",  this.frequency);
        offnetMap.put("service",  this.service);
        offnetMap.put("segment",  this.segment);
        //backhaulMap.put("ports",  this.ports);

        return offnetMap;

    }

    public Offnet() {
        // TODO Auto-generated constructor stub
    }

    private <E> List<E> safe( List<E> other ) {
        return other == null ? Collections.EMPTY_LIST : other;
    }
}
