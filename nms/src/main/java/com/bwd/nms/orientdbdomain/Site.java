package com.bwd.nms.orientdbdomain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.orientechnologies.orient.core.sql.executor.OResult;
import java.io.Serializable;
import java.util.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class Site implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<Object, Object> siteMap = new HashMap<Object, Object>();

    private String id;

    private String streetnumber;

    private String comment;
    private String country;

    // @Column(name = "streetname")
    private String streetname;

    private int sitecount;

    //@NotNull
    // @Column(name = "sitename", nullable = false)
    private String sitename;

    // @Column(name = "labelname")
    private String labelname;

    // @Column(name = "city")
    private String city;

    // @Column(name = "zipcode")
    private String zipcode;

    // @Column(name = "latitude", precision=10, scale=2)
    private Float latitude;

    // @Column(name = "longitude", precision=10, scale=2)
    private Float longitude;

    // @Column(name = "createdby")
    private String createdby;

    // @Column(name = "updatedby")
    private String updatedby;

    // @Column(name = "buisneesowner")
    private String buisneesowner;

    // @Column(name = "leasedcompany")
    private String leasedcompany;

    // @Column(name = "datecreated")
    private Date datecreated;

    // @Column(name = "dateupdated")
    private Date dateupdated;
    //@OneToMany(mappedBy = "sitename")
    //@JsonIgnore
    private Set<RoomLocation> rooms = new HashSet<>();

    //    @ManyToOne
    private Segment segmentname;

    //  @ManyToOne
    private Fiber fibername;

    public int getSitecount() {
        return sitecount;
    }

    public void setSitecount(int sitecount) {
        this.sitecount = sitecount;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStreetnumber() {
        return streetnumber;
    }

    public Site streetnumber(String streetnumber) {
        this.streetnumber = streetnumber;
        return this;
    }

    public void setStreetnumber(String streetnumber) {
        this.streetnumber = streetnumber;
    }

    public String getStreetname() {
        return streetname;
    }

    public Site streetname(String streetname) {
        this.streetname = streetname;
        return this;
    }

    public void setStreetname(String streetname) {
        this.streetname = streetname;
    }

    public String getSitename() {
        return sitename;
    }

    public Site sitename(String sitename) {
        this.sitename = sitename;
        return this;
    }

    public void setSitename(String sitename) {
        this.sitename = sitename;
    }

    public String getLabelname() {
        return labelname;
    }

    public Site labelname(String labelname) {
        this.labelname = labelname;
        return this;
    }

    public void setLabelname(String labelname) {
        this.labelname = labelname;
    }

    public String getCity() {
        return city;
    }

    public Site city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public Site zipcode(String zipcode) {
        this.zipcode = zipcode;
        return this;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public Float getLatitude() {
        return latitude;
    }

    public Site latitude(Float latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public Site longitude(Float longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public String getCreatedby() {
        return createdby;
    }

    public Site createdby(String createdby) {
        this.createdby = createdby;
        return this;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public String getUpdatedby() {
        return updatedby;
    }

    public Site updatedby(String updatedby) {
        this.updatedby = updatedby;
        return this;
    }

    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }

    public String getBuisneesowner() {
        return buisneesowner;
    }

    public Site buisneesowner(String buisneesowner) {
        this.buisneesowner = buisneesowner;
        return this;
    }

    public void setBuisneesowner(String buisneesowner) {
        this.buisneesowner = buisneesowner;
    }

    public String getLeasedcompany() {
        return leasedcompany;
    }

    public Site leasedcompany(String leasedcompany) {
        this.leasedcompany = leasedcompany;
        return this;
    }

    public void setLeasedcompany(String leasedcompany) {
        this.leasedcompany = leasedcompany;
    }

    public Date getDatecreated() {
        return datecreated;
    }

    public Site datecreated(Date datecreated) {
        this.datecreated = datecreated;
        return this;
    }

    public void setDatecreated(Date datecreated) {
        this.datecreated = datecreated;
    }

    public Date getDateupdated() {
        return dateupdated;
    }

    public Site dateupdated(Date dateupdated) {
        this.dateupdated = dateupdated;
        return this;
    }

    public void setDateupdated(Date dateupdated) {
        this.dateupdated = dateupdated;
    }

    public Set<RoomLocation> getRooms() {
        return rooms;
    }

    public Site rooms(Set<RoomLocation> roomLocations) {
        this.rooms = roomLocations;
        return this;
    }

    public Site addRooms(RoomLocation roomLocation) {
        this.rooms.add(roomLocation);
        roomLocation.setSitename(this);
        return this;
    }

    public Site removeRooms(RoomLocation roomLocation) {
        this.rooms.remove(roomLocation);
        roomLocation.setSitename(null);
        return this;
    }

    public void setRooms(Set<RoomLocation> roomLocations) {
        this.rooms = roomLocations;
    }

    public Segment getSegmentname() {
        return segmentname;
    }

    public Site segmentname(Segment segment) {
        this.segmentname = segment;
        return this;
    }

    public void setSegmentname(Segment segment) {
        this.segmentname = segment;
    }

    public Fiber getFibername() {
        return fibername;
    }

    public Site fibername(Fiber fiber) {
        this.fibername = fiber;
        return this;
    }

    public void setFibername(Fiber fiber) {
        this.fibername = fiber;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Site site = (Site) o;
        if (site.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), site.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return (
            "Site{" +
            "sitename='" +
            getSitename() +
            "'" +
            ", comment='" +
            getComment() +
            "'" +
            ", latitude=" +
            getLatitude() +
            ", longitude=" +
            getLongitude() +
            "}"
        );
    }

    public Site(OResult service) {
        this.id = service.getProperty("@rid").toString();
        this.sitename = service.getProperty("name");
        this.comment = service.getProperty("comment");
        this.labelname = service.getProperty("labelname");
        this.streetnumber = service.getProperty("streetnumber");
        this.streetname = service.getProperty("streetname");
        if (service.getProperty("sitecount") != null) this.sitecount = service.getProperty("sitecount");

        this.city = service.getProperty("city");
        this.zipcode = service.getProperty("zipcode");
        if (service.getProperty("latitude") != null) this.latitude = Float.parseFloat(service.getProperty("latitude"));
        if (service.getProperty("longitude") != null) this.longitude = Float.parseFloat(service.getProperty("longitude"));
        this.buisneesowner = service.getProperty("buisneesowner");
        this.leasedcompany = service.getProperty("leasedcompany");

        this.createdby = service.getProperty("createdby");
        this.updatedby = service.getProperty("updatedby");
        this.datecreated = service.getProperty("datecreated");
        this.dateupdated = service.getProperty("dateupdated");
    }

    public Site() {}

    public Site(
        String id,
        String labelname,
        float latitude,
        float longitude,
        String comment,
        String streetnumber,
        String city,
        String zipcode,
        String buisneesowner,
        String leasedcompany
    ) {
        setId(id);
        setLabelname(labelname);
        setLatitude(latitude);
        setLongitude(longitude);
        setComment(comment);
        setStreetnumber(streetnumber);
        setCity(city);
        setZipcode(zipcode);
        setBuisneesowner(buisneesowner);
        setLeasedcompany(leasedcompany);
    }

    public Map<Object, Object> siteMap() {
        siteMap.put("id", this.id);
        siteMap.put("name", this.sitename);
        siteMap.put("comment", this.comment);
        siteMap.put("labelname", this.labelname);
        siteMap.put("streetnumber", this.streetnumber);
        siteMap.put("streetname", this.streetname);
        siteMap.put("city", this.city);
        siteMap.put("zipcode", this.zipcode);
        siteMap.put("latitude", this.latitude);
        siteMap.put("longitude", this.longitude);
        siteMap.put("buisneesowner", this.buisneesowner);
        siteMap.put("leasedcompany", this.leasedcompany);
        siteMap.put("createdby", this.createdby);
        siteMap.put("updatedby", this.updatedby);
        siteMap.put("datecreated", this.datecreated);
        siteMap.put("dateupdated", this.dateupdated);
        return siteMap;
    }
}
