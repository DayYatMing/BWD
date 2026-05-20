package com.bwd.nms.orientdbdomain;

//import com.orientechnologies.orient.core.sql.executor.OResult;

import com.orientechnologies.orient.core.sql.executor.OResult;

import java.io.Serializable;
import java.util.*;

/**
 * A RoomLocation.
 */
//@Entity
//@Table(name = "roomlocation")
public class RoomLocation implements Serializable {

    private Map<Object,Object> roomLocationMap = new HashMap<Object, Object>();
    private static final long serialVersionUID = 1L;

    public RoomLocation() {

    }

    //  @Id
    //   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    // @NotNull
    // @Column(name = "rrname", nullable = false)
    private String rrname;

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

    private String floor;

    private String room;

    private String row;

    private String position;

    private String comment;

    private String site;

    private int rrcount;

    public RoomLocation(String id, String site, String labelname, String comment) {
        setId(id);
        setSite(site);
        setLabelname(labelname);
        setComment(comment);
    }


    public int getRrcount() {
        return rrcount;
    }

    public void setRrcount(int rrcount) {
        this.rrcount = rrcount;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    // @OneToMany(mappedBy = "rrname")
    // @JsonIgnore
    private Set<Node> nodes = new HashSet<>();

    // @ManyToOne
    private Site sitename;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRrname() {
        return rrname;
    }

    public RoomLocation rrname(String rrname) {
        this.rrname = rrname;
        return this;
    }

    public void setRrname(String rrname) {
        this.rrname = rrname;
    }

    public String getLabelname() {
        return labelname;
    }

    public RoomLocation labelname(String labelname) {
        this.labelname = labelname;
        return this;
    }

    public void setLabelname(String labelname) {
        this.labelname = labelname;
    }

    public String getCreatedby() {
        return createdby;
    }

    public RoomLocation createdby(String createdby) {
        this.createdby = createdby;
        return this;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public String getUpdatedby() {
        return updatedby;
    }

    public RoomLocation updatedby(String updatedby) {
        this.updatedby = updatedby;
        return this;
    }

    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }

    public Date getDatecreated() {
        return datecreated;
    }

    public RoomLocation datecreated(Date datecreated) {
        this.datecreated = datecreated;
        return this;
    }

    public void setDatecreated(Date datecreated) {
        this.datecreated = datecreated;
    }

    public Date getDateupdated() {
        return dateupdated;
    }

    public RoomLocation dateupdated(Date dateupdated) {
        this.dateupdated = dateupdated;
        return this;
    }

    public void setDateupdated(java.util.Date dateupdated) {
        this.dateupdated = dateupdated;
    }

    public Set<Node> getRacks() {
        return nodes;
    }

    public RoomLocation nodes(Set<Node> nodes) {
        this.nodes = nodes;
        return this;
    }

    public RoomLocation addRacks(Node node) {
        this.nodes.add(node);
        //  node.setRrname(this);
        return this;
    }

    public RoomLocation removeRacks(Node node) {
        this.nodes.remove(node);
        //    node.setRrname(null);
        return this;
    }

    public void setRacks(Set<Node> nodes) {
        this.nodes = nodes;
    }

    public Site getSitename() {
        return sitename;
    }

    public RoomLocation sitename(Site site) {
        this.sitename = site;
        return this;
    }

    public void setSitename(Site site) {
        this.sitename = site;
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
        RoomLocation roomLocation = (RoomLocation) o;
        if (roomLocation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), roomLocation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RoomLocation{" +
            "id=" + getId() +
            ", rrname='" + getRrname() + "'" +
            ", labelname='" + getLabelname() + "'" +
            ", createdby='" + getCreatedby() + "'" +
            ", updatedby='" + getUpdatedby() + "'" +
            ", datecreated='" + getDatecreated() + "'" +
            ", dateupdated='" + getDateupdated() + "'" +
            "}";
    }

    public RoomLocation(OResult roomlocation) {
        this.id = roomlocation.getProperty("@rid").toString();
        this.rrname = roomlocation.getProperty("name");
        this.labelname = roomlocation.getProperty("labelname");
        this.createdby = roomlocation.getProperty("createdby");
        this.updatedby = roomlocation.getProperty("updatedby");
        this.datecreated = roomlocation.getProperty("datecreated");
        this.dateupdated = roomlocation.getProperty("dateupdated");
        this.floor = roomlocation.getProperty("floor");
        this.room = roomlocation.getProperty("room");
        this.row = roomlocation.getProperty("row");
        this.position = roomlocation.getProperty("position");
        this.comment = roomlocation.getProperty("comment");
        List<String> sites = roomlocation.getProperty("site");
        if (sites != null && sites.size() > 0)
            this.site = sites.get(0);

        if(roomlocation.getProperty("rrcount")!=null)
            this.rrcount = roomlocation.getProperty("rrcount");
    }

    public Map<Object,Object> roomLocationMap() {
        roomLocationMap.put("@rid",  this.id);
        roomLocationMap.put("name",  this.rrname);
        roomLocationMap.put("labelname",  this.labelname);
        roomLocationMap.put("createdby",  this.createdby);
        roomLocationMap.put("updatedby",  this.updatedby);
        roomLocationMap.put("datecreated",  this.datecreated);
        roomLocationMap.put("floor",  this.floor);
        roomLocationMap.put("room",  this.room);
        roomLocationMap.put("row",  this.row);
        roomLocationMap.put("position",  this.position);
        roomLocationMap.put("comment",  this.comment);
        roomLocationMap.put("site",  this.site);
        return roomLocationMap;
    }
}
