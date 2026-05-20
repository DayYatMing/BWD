package com.bwd.nms.orientdbdomain;

import com.bwd.nms.orientdbdomain.enumeration.ServiceStatus;
import com.orientechnologies.orient.core.sql.executor.OResult;

import javax.persistence.Lob;
import java.io.Serializable;
import java.util.*;

/**
 * A Service.
 */
//@Entity
//@Table(name = "service")
public class Services implements Serializable {

	private static final long serialVersionUID = 1L;
	Map<Object, Object> serviceMap = new HashMap<Object, Object>();

	public Services() {
	}

    public Services(
        String id,
        String serviceName,
        String labelname,
        String comment,
        ServiceStatus servicestatus,
        String servicecapacity,
        String serviceimage,
        byte[] servicedata,
        String servicedataContentType,
        String customername,
        int max,
        String portid,
        Boolean internal) {

        setId(id);
        setServicename(serviceName);
        setLabelname(labelname);
        setComment(comment);
        setServicestatus(servicestatus);
        setServicecapacity(servicecapacity);
        setServiceimage(serviceimage);
        setServicedata(servicedata);
        setServicedataContentType(servicedataContentType);
        setCustomername(customername);
        setMax(max);
        setPortid(portid);
        setInternal(internal);
    }

	// @Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;

	// @NotNull
	// @Column(name = "servicename", nullable = false)
	private String servicename;

	// @Column(name = "labelname")
	private String labelname;


	private String comment;

	// @NotNull
	// @Enumerated(EnumType.STRING)
	// @Column(name = "servicestatus", nullable = false)
	private ServiceStatus servicestatus;

	// @NotNull
	// @Enumerated(EnumType.STRING)
	// @Column(name = "servicecapacity", nullable = false)
	private String servicecapacity;

	// @Column(name = "serviceimage")
	private String serviceimage;

	@Lob
	// @Column(name = "servicedata")
	private byte[] servicedata;

	// @Column(name = "servicedata_content_type")
	private String servicedataContentType;

	// @Column(name = "createdby")
	private String createdby;

	// @Column(name = "updatedby")
	private String updatedby;

	// @Column(name = "datecreated")
	private Date datecreated;

	// @Column(name = "dateupdated")
	private Date dateupdated;

	private String customername;


	private String servicetype;

	private int max;

	private String portid;

	private Boolean internal;

	// @ManyToOne
//	private Customer customername;

	public Boolean getInternal() {
		return internal;
	}

	public void setInternal(Boolean internal) {
		this.internal = internal;
	}

	public String getPortid() {
		return portid;
	}

	public void setPortid(String portid) {
		this.portid = portid;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public String getServicetype() {
		return servicetype;
	}

	public void setServicetype(String servicetype) {
		this.servicetype = servicetype;
	}

	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}

	private String route;

	private String segment;


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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	// jhipster-needle-entity-add-field - JHipster will add fields here, do not
	// remove
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getServicename() {
		return servicename;
	}

	public Services servicename(String servicename) {
		this.servicename = servicename;
		return this;
	}

	public void setServicename(String servicename) {
		this.servicename = servicename;
	}

	public String getLabelname() {
		return labelname;
	}

	public Services labelname(String labelname) {
		this.labelname = labelname;
		return this;
	}

	public void setLabelname(String labelname) {
		this.labelname = labelname;
	}

	public ServiceStatus getServicestatus() {
		return servicestatus;
	}

	public Services servicestatus(ServiceStatus servicestatus) {
		this.servicestatus = servicestatus;
		return this;
	}

	public void setServicestatus(ServiceStatus servicestatus) {
		this.servicestatus = servicestatus;
	}

	public String getServicecapacity() {
		return servicecapacity;
	}

	public Services servicecapacity(String servicecapacity) {
		this.servicecapacity = servicecapacity;
		return this;
	}

	public void setServicecapacity(String servicecapacity) {
		this.servicecapacity = servicecapacity;
	}

	public String getServiceimage() {
		return serviceimage;
	}

	public Services serviceimage(String serviceimage) {
		this.serviceimage = serviceimage;
		return this;
	}

	public void setServiceimage(String serviceimage) {
		this.serviceimage = serviceimage;
	}

	public byte[] getServicedata() {
		return servicedata;
	}

	public Services servicedata(byte[] servicedata) {
		this.servicedata = servicedata;
		return this;
	}

	public void setServicedata(byte[] servicedata) {
		this.servicedata = servicedata;
	}

	public String getServicedataContentType() {
		return servicedataContentType;
	}

	public Services servicedataContentType(String servicedataContentType) {
		this.servicedataContentType = servicedataContentType;
		return this;
	}

	public void setServicedataContentType(String servicedataContentType) {
		this.servicedataContentType = servicedataContentType;
	}

	public String getCreatedby() {
		return createdby;
	}

	public Services createdby(String createdby) {
		this.createdby = createdby;
		return this;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public String getUpdatedby() {
		return updatedby;
	}

	public Services updatedby(String updatedby) {
		this.updatedby = updatedby;
		return this;
	}

	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}

	public Date getDatecreated() {
		return datecreated;
	}

	public Services datecreated(Date datecreated) {
		this.datecreated = datecreated;
		return this;
	}

	public void setDatecreated(Date datecreated) {
		this.datecreated = datecreated;
	}

	public Date getDateupdated() {
		return dateupdated;
	}

	public Services dateupdated(Date dateupdated) {
		this.dateupdated = dateupdated;
		return this;
	}

	public void setDateupdated(Date dateupdated) {
		this.dateupdated = dateupdated;
	}

//	public Customer getCustomername() {
//		return customername;
//	}
//
//	public Service customername(Customer customer) {
//		this.customername = customer;
//		return this;
//	}
//
//	public void setCustomername(Customer customer) {
//		this.customername = customer;
//	}
	// jhipster-needle-entity-add-getters-setters - JHipster will add getters and
	// setters here, do not remove

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Services service = (Services) o;
		if (service.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), service.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "Service{" + "id=" + getId() + ", servicename='" + getServicename() + "'" + ", labelname='"
				+ getLabelname() + "'" + ", servicestatus='" + getServicestatus() + "'" + ", servicecapacity='"
				+ getServicecapacity() + "'" + ", serviceimage='" + getServiceimage() + "'" + ", servicedata='"
				+ getServicedata() + "'" + ", servicedataContentType='" + getServicedataContentType() + "'"
				+ ", createdby='" + getCreatedby() + "'" + ", updatedby='" + getUpdatedby() + "'" + ", datecreated='"
				+ getDatecreated() + "'" + ", dateupdated='" + getDateupdated() + "'" + "}";
	}

	public Services(OResult service) {
		if( service.getProperty("@rid") != null)
		this.id = service.getProperty("@rid").toString();
		this.servicename = service.getProperty("name");
		this.labelname = service.getProperty("labelname");
		this.comment = service.getProperty("comment");
		if(service.getProperty("state") != null)
		this.servicestatus = ServiceStatus.fromString(service.getProperty("state"));
		this.servicecapacity = service.getProperty("bandwidth");

		if(this.servicecapacity == null){
            List<Integer> capacities = (List<Integer>)service.getProperty("servicecapacity");
            if( capacities != null && capacities.size() > 0 && capacities.get(0) != null)
                this.servicecapacity = Integer.toString(capacities.get(0));
        }

		this.route = service.getProperty("route");
		this.segment = service.getProperty("segment");
		this.servicedata = service.getProperty("imagedata");
		this.serviceimage = service.getProperty("imagename");
		this.createdby = service.getProperty("createdby");
		this.servicetype = service.getProperty("servicetype");
		this.updatedby = service.getProperty("updatedby");
		this.datecreated = service.getProperty("datecreated");
		this.dateupdated = service.getProperty("dateupdated");
		this.portid = service.getProperty("PORTID");
		if(service.getProperty("max") != null)
			this.max = Integer.parseInt(service.getProperty("max"));
		if( this.route == null) {
            List<String> routes = (List<String>) service.getProperty("routes");
            if (routes != null && routes.size() > 0)
                this.route = routes.get(0);
            else {
                routes = (List<String>) service.getProperty("routes1");
                if (routes != null && routes.size() > 0)
                    this.route = routes.get(0);
            }
        }
		List<String> customers = (List<String>)service.getProperty("customers");
		if( customers != null && customers.size() > 0)
		this.customername = customers.get(0);

		this.internal = service.getProperty("internal");



	}

	public Map<Object, Object> serviceMap() {

		serviceMap.put("@rid", this.id);
		serviceMap.put("name", this.servicename);
		serviceMap.put("labelname", this.labelname);
		serviceMap.put("comment", this.comment);
		serviceMap.put("createdby", this.createdby);
		serviceMap.put("updatedby", this.updatedby);
		serviceMap.put("datecreated", this.datecreated);
		serviceMap.put("dateupdated", this.dateupdated);
		serviceMap.put("serviceimage", this.serviceimage);
		serviceMap.put("servicedata", this.servicedata);
		serviceMap.put("state", this.servicestatus.name());
		serviceMap.put("bandwidth", this.servicecapacity);
		serviceMap.put("route", this.route);
		serviceMap.put("segment", this.segment);
		serviceMap.put("servicetype", this.servicetype);
		serviceMap.put("servicecapacity", this.servicecapacity);
		serviceMap.put("customername", this.customername);
		serviceMap.put("internal", this.internal);
		return serviceMap;
	}
}
