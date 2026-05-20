package com.bwd.nms.orientdbdomain;

//import com.orientechnologies.orient.core.sql.executor.OResult;

import com.orientechnologies.orient.core.sql.executor.OResult;

import java.io.Serializable;
import java.util.*;

public class Backhaul implements Serializable {

    private static final long serialVersionUID = 1L;
    private Map<Object,Object> backhaulMap = new HashMap<Object, Object>();

    private Integer capacity;
    private String comment;
    private String createdby;
    private String datecreated;
    private String dateupdated;
    private String labelname;
    private String name;
    private boolean protectedcircuit;
    private String provider;
    private String providerName;
    private String servicedetails;
    private String status;
    private String updatedby;
    private String aend;
    private String bend;
    private String customer;
    private String service;
    private String segment;

    //private ArrayList ports;
    private String id;

    public Backhaul(String id,
                    String providerName,
                    String name,
                    Integer capacity,
                    Boolean protectedCircuit,
                    String segment,
                    String aend,
                    String bend,
                    String customer,
                    String service,
                    String status,
                    String serviceDetails,
                    String comment) {
        setId(id);
        setProviderName(providerName);
        setName(name);
        setCapacity(capacity);
        setProtectedcircuit(protectedCircuit);
        setSegment(segment);
        setAend(aend);
        setBend(bend);
        setCustomer(customer);
        setService(service);
        setStatus(status);
        setServicedetails(serviceDetails);
        setComment(comment);
    }


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
    public String getProvider() {
        return provider;
    }
    public void setProvider(String provider) {
        this.provider = provider;
    }
    public String getProviderName() {
        return providerName;
    }
    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }
    public String getServicedetails() {
        return servicedetails;
    }
    public void setServicedetails(String servicedetails) {
        this.servicedetails = servicedetails;
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
        return "Backhaul{" +
            " BackhaulId='" +  getName()  + "'" +
            ", VendorName='" + getProviderName() + "'" +
            ", Capacity='" + getCapacity() + "'" +
            ", Protected='" + getProtectedcircuit() + "'" +
            ", Details='" + getServicedetails() + "'" +
            ", Segment='" + getSegment() + "'" +
            ", AEnd='" + getAend() + "'" +
            ", BEnd='" + getBend() + "'" +
            ", Customer='" + getCustomer() + "'" +
            ", Service='" + getService() + "'" +
            ", Misc='" + getComment() + "'" +
            "}";
    }

    public Backhaul(OResult backhaul) {
//        this.id = backhaul.getProperty("@rid").toString();
//        this.name = backhaul.getProperty("name");
//        this.capacity = backhaul.getProperty("capacity");
//        this.labelname = backhaul.getProperty("labelname");
//        this.comment = backhaul.getProperty("comment");
//        this.protectedcircuit = backhaul.getProperty("protectedcircuit");
//        this.provider = backhaul.getProperty("provider");
//        this.providerName = backhaul.getProperty("providerName");
//        this.servicedetails = backhaul.getProperty("servicedetails");
//        this.status = backhaul.getProperty("status");
//        // this.ports = backhaul.getProperty("ports");
//        this.createdby = backhaul.getProperty("createdby");
//        this.createdby = backhaul.getProperty("createdby");
//        this.updatedby = backhaul.getProperty("updatedby");
//        this.datecreated = backhaul.getProperty("datecreated");
//        this.dateupdated = backhaul.getProperty("dateupdated");
//        this.segment = backhaul.getProperty("segment");
//
//        if(backhaul.getProperty("aend") != null && ((List<String>)backhaul.getProperty("aend")).size() >0) {
//            Site siteA = new Site();
//            Site siteB = new Site();
//            siteA.setSitename(((List<String>)backhaul.getProperty("aend")).get(0));
//            siteB.setSitename(((List<String>)backhaul.getProperty("bend")).get(0));
//
//            siteA.setLatitude(Float.parseFloat(((List<String>)backhaul.getProperty("aendlat")).get(0)));
//            siteB.setLatitude(Float.parseFloat(((List<String>)backhaul.getProperty("bendlat")).get(0)));
//            siteA.setLongitude(Float.parseFloat(((List<String>)backhaul.getProperty("aendlong")).get(0)));
//            siteB.setLongitude(Float.parseFloat(((List<String>)backhaul.getProperty("bendlong")).get(0)));
//            sites.add(siteA);
//            sites.add(siteB);
//        }
//        List<String> customers = backhaul.getProperty("customer");
//        for(String customer:safe(customers)) {
//            if(this.customer == null)
//                this.customer = customer;
//            else
//                this.customer = this.customer + "," + customer;
//        }
//        List<String> services = backhaul.getProperty("service");
//        for(String service:safe(services)){
//            if(this.service == null)
//                this.service = service;
//            else
//                this.service = this.service + "," + service;
//        }
//        List<String> aends = backhaul.getProperty("aend");
//        if (aends != null && aends.size() > 0)
//            this.aend = aends.get(0);
//        List<String> bends = backhaul.getProperty("bend");
//        if (bends != null && bends.size() > 0)
//            this.bend = bends.get(0);

    }

    public Map<Object,Object> backhaulMap() {
        backhaulMap.put("@rid",  this.id);
        backhaulMap.put("name",  this.name.trim());
        backhaulMap.put("comment",  this.comment);
        backhaulMap.put("labelname",  this.name.trim());
        backhaulMap.put("capacity",  this.capacity);
        backhaulMap.put("aend",  this.aend);
        backhaulMap.put("bend",  this.bend);
        backhaulMap.put("protectedcircuit",  this.protectedcircuit);
        backhaulMap.put("provider",  this.provider);
        backhaulMap.put("providerName",  this.providerName);
        backhaulMap.put("status",  this.status);
        backhaulMap.put("servicedetails",  this.servicedetails);
        backhaulMap.put("createdby",  this.createdby);
        backhaulMap.put("updatedby",  this.updatedby);
        backhaulMap.put("customer",  this.customer);
        backhaulMap.put("service",  this.service);
        backhaulMap.put("segment",  this.segment);
//        backhaulMap.put("ports",  this.ports);

        return backhaulMap;

    }

    public Backhaul() {
        // TODO Auto-generated constructor stub
    }

    private <E> List<E> safe( List<E> other ) {
        return other == null ? Collections.EMPTY_LIST : other;
    }
}
