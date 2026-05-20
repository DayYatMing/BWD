package com.bwd.nms.orientdbdomain;

//import com.orientechnologies.orient.core.sql.executor.OResult;

import java.io.Serializable;
import java.util.*;

/**
 * A Segment.
 */
//@Entity
//@Table(name = "segment")
public class Segment implements Serializable {

    private static final long serialVersionUID = 1L;
    private Map<Object, Object> segmentMap = new HashMap<Object, Object>();

    //   @Id
    //   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    //   @NotNull
    // @Column(name = "segmentname", nullable = false)
    private String segmentname;

    // @Column(name = "labelname")
    private String labelname;

    // @Column(name = "createdby")
    private String createdby;

    // @Column(name = "updatedby")
    private String updatedby;

    // @Column(name = "datecreated")
    private Date datecreated;

    // @Column(name = "dateupdated")
    private Date dateupdated;

    private String dls;

    private String comment;

    private String aend;

    private String bend;

    private String aendfiber;

    private String bendfiber;

    private Integer directionorder;

    private int segmentcount;

    private String networktype;

    public String getNetworktype() {
        return networktype;
    }

    public void setNetworktype(String networktype) {
        this.networktype = networktype;
    }

    public int getSegmentcount() {
        return segmentcount;
    }

    public void setSegmentcount(int segmentcount) {
        this.segmentcount = segmentcount;
    }

    public Integer getDirectionorder() {
        return directionorder;
    }

    public void setDirectionorder(Integer directionorder) {
        this.directionorder = directionorder;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDls() {
        return dls;
    }

    public void setDls(String dls) {
        this.dls = dls;
    }

    //  @OneToMany(mappedBy = "segmentname")
    //  @JsonIgnore
    private Set<Site> sites = new HashSet<>();

    // @ManyToOne
    private Route routename;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSegmentname() {
        return segmentname;
    }

    public Segment segmentname(String segmentname) {
        this.segmentname = segmentname;
        return this;
    }

    public void setSegmentname(String segmentname) {
        this.segmentname = segmentname;
    }

    public String getLabelname() {
        return labelname;
    }

    public Segment labelname(String labelname) {
        this.labelname = labelname;
        return this;
    }

    public void setLabelname(String labelname) {
        this.labelname = labelname;
    }

    public String getCreatedby() {
        return createdby;
    }

    public Segment createdby(String createdby) {
        this.createdby = createdby;
        return this;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public String getUpdatedby() {
        return updatedby;
    }

    public Segment updatedby(String updatedby) {
        this.updatedby = updatedby;
        return this;
    }

    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }

    public Date getDatecreated() {
        return datecreated;
    }

    public Segment datecreated(Date datecreated) {
        this.datecreated = datecreated;
        return this;
    }

    public void setDatecreated(Date datecreated) {
        this.datecreated = datecreated;
    }

    public Date getDateupdated() {
        return dateupdated;
    }

    public Segment dateupdated(Date dateupdated) {
        this.dateupdated = dateupdated;
        return this;
    }

    public void setDateupdated(Date dateupdated) {
        this.dateupdated = dateupdated;
    }

    public Set<Site> getSites() {
        return sites;
    }

    public Segment sites(Set<Site> sites) {
        this.sites = sites;
        return this;
    }

    public Segment addSites(Site site) {
        this.sites.add(site);
        site.setSegmentname(this);
        return this;
    }

    public Segment removeSites(Site site) {
        this.sites.remove(site);
        site.setSegmentname(null);
        return this;
    }

    public void setSites(Set<Site> sites) {
        this.sites = sites;
    }

    public Route getRoutename() {
        return routename;
    }

    public Segment routename(Route route) {
        this.routename = route;
        return this;
    }

    public void setRoutename(Route route) {
        this.routename = route;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        Segment segment = (Segment) o;
        if (segment.getSegmentname() == null || getSegmentname() == null) {
            return false;
        }
        return getSegmentname().equals(segment.getSegmentname());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getSegmentname());
    }

    @Override
    public String toString() {
        return (
            "Segment{" +
            " segmentename='" +
            getSegmentname() +
            "'" +
            " dls='" +
            getDls() +
            "'" +
            " AEnd='" +
            getAend() +
            "'" +
            " BEnd='" +
            getBend() +
            "'" +
            " AEndFiberPair='" +
            getAendfiber() +
            "'" +
            " BEndFiberPair='" +
            getBendfiber() +
            "'" +
            " NetworkType='" +
            getNetworktype() +
            "'" +
            ", comment='" +
            getComment() +
            "'" +
            "}"
        );
    }

    public Map<Object, Object> segmentMap() {
        segmentMap.put("@rid", this.id);
        segmentMap.put("name", this.segmentname);
        segmentMap.put("comment", this.comment);
        segmentMap.put("labelname", this.labelname);
        segmentMap.put("dls", this.dls);
        segmentMap.put("aend", this.aend);
        segmentMap.put("bend", this.bend);
        segmentMap.put("aendfiber", this.aendfiber);
        segmentMap.put("bendfiber", this.bendfiber);
        segmentMap.put("directionorder", this.directionorder);
        segmentMap.put("createdby", this.createdby);
        segmentMap.put("updatedby", this.updatedby);
        segmentMap.put("datecreated", this.datecreated);
        segmentMap.put("dateupdated", this.dateupdated);
        segmentMap.put("networktype", this.networktype);
        return segmentMap;
    }

    public Segment(String id, String segmentname, String comment, String labelname, String dls, String aend, String bend,
                   String aendfiber, String bendfiber, Integer directionorder, String createdby, String updatedby,
                   Date datecreated, Date dateupdated, String networktype,
                   float latitude,
                   float longitude  )
    {
        setId(id);
        setSegmentname(segmentname);
        setComment(comment);
        setLabelname(labelname);
        setDls(dls);
        setAend(aend);
        setBend(bend);
        setAendfiber(aendfiber);
        setBendfiber(bendfiber);
        setDirectionorder(directionorder);
        setCreatedby(createdby);
        setUpdatedby(updatedby);
        setDatecreated(datecreated);
        setDateupdated(dateupdated);
        setNetworktype(networktype);
        setLatitude(latitude);
        setLongitude(longitude);
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    private Float latitude;
    private Float longitude;
}
