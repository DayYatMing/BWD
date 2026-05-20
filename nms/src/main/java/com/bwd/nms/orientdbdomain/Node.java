package com.bwd.nms.orientdbdomain;

//import com.orientechnologies.orient.core.sql.executor.OResult;

import com.bwd.nms.orientdbdomain.enumeration.CardType;

import java.io.Serializable;
import java.util.*;

/**
 * A Node.
 */
//@Entity
//@Table(name = "rack")
public class Node implements Serializable {

    private static final long serialVersionUID = 1L;
    private Map<Object, Object> nodeMap = new HashMap<Object, Object>();

    //@Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    //@NotNull
    // @Column(name = "rackname", nullable = false)
    private String name;

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

    private String comment;

    private String sno;

    private String imagename;

    private byte[] imagedata;

    private String position;

    private String slot;

    private String shelf;

    private String site;

    private String roomlocation;

    public Node(String id, String name, String comment, String labelname, String createdby,
                String updatedby, Date dateupdated, Date datecreated, String sno, String imagename, byte[] imagedata,
                String position, String slot, String shelf, String site, String roomlocation) {
        setId(id);
        setName(name);
        setComment(comment);
        setCreatedby(createdby);
        setLabelname(labelname);
        setUpdatedby(updatedby);
        setDatecreated(datecreated);
        setDateupdated(dateupdated);
        setSno(sno);
        setImagename(imagename);
        setImagedata(imagedata);
        setPosition(position);
        setSlot(slot);
        setShelf(shelf);
        setSite(site);
        setRoomlocation(roomlocation);

    }

    //	// @OneToMany(mappedBy = "rackname")
    //  //  @JsonIgnore
    //    private Set<Slot> slots = new HashSet<>();
    //
    ////    @ManyToOne
    //    private RoomLocation rrname;

    public String getShelf() {
        return shelf;
    }

    public void setShelf(String shelf) {
        this.shelf = shelf;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getRoomlocation() {
        return roomlocation;
    }

    public void setRoomlocation(String roomlocation) {
        this.roomlocation = roomlocation;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getImagename() {
        return imagename;
    }

    public void setImagename(String imagename) {
        this.imagename = imagename;
    }

    public byte[] getImagedata() {
        return imagedata;
    }

    public void setImagedata(byte[] imagedata) {
        this.imagedata = imagedata;
    }

    public Node() {}

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Node name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabelname() {
        return labelname;
    }

    public Node labelname(String labelname) {
        this.labelname = labelname;
        return this;
    }

    public void setLabelname(String labelname) {
        this.labelname = labelname;
    }

    public String getCreatedby() {
        return createdby;
    }

    public Node createdby(String createdby) {
        this.createdby = createdby;
        return this;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public String getUpdatedby() {
        return updatedby;
    }

    public Node updatedby(String updatedby) {
        this.updatedby = updatedby;
        return this;
    }

    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }

    public Date getDatecreated() {
        return datecreated;
    }

    public Node datecreated(Date datecreated) {
        this.datecreated = datecreated;
        return this;
    }

    public void setDatecreated(Date datecreated) {
        this.datecreated = datecreated;
    }

    public Date getDateupdated() {
        return dateupdated;
    }

    public Node dateupdated(Date dateupdated) {
        this.dateupdated = dateupdated;
        return this;
    }

    public void setDateupdated(Date dateupdated) {
        this.dateupdated = dateupdated;
    }

    //    public Set<Slot> getSlots() {
    //        return slots;
    //    }
    //
    //    public Node slots(Set<Slot> slots) {
    //        this.slots = slots;
    //        return this;
    //    }
    //
    //    public Node addSlots(Slot slot) {
    //        this.slots.add(slot);
    //        slot.setRackname(this);
    //        return this;
    //    }
    //
    //    public Node removeSlots(Slot slot) {
    //        this.slots.remove(slot);
    //        slot.setRackname(null);
    //        return this;
    //    }
    //
    //    public void setSlots(Set<Slot> slots) {
    //        this.slots = slots;
    //    }
    //
    //    public RoomLocation getRrname() {
    //        return rrname;
    //    }
    //
    //    public Node rrname(RoomLocation roomLocation) {
    //        this.rrname = roomLocation;
    //        return this;
    //    }
    //
    //    public void setRrname(RoomLocation roomLocation) {
    //        this.rrname = roomLocation;
    //    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Node node = (Node) o;
        if (node.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), node.getId());
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return (
            "Node{" +
            "id=" +
            getId() +
            ", name='" +
            getName() +
            "'" +
            ", labelname='" +
            getLabelname() +
            "'" +
            ", createdby='" +
            getCreatedby() +
            "'" +
            ", updatedby='" +
            getUpdatedby() +
            "'" +
            ", datecreated='" +
            getDatecreated() +
            "'" +
            ", dateupdated='" +
            getDateupdated() +
            "'" +
            "}"
        );
    }

    //    public Node(OResult node) {
    //        this.id = node.getProperty("@rid").toString();
    //        this.name = node.getProperty("name");
    //        this.labelname = node.getProperty("labelname");
    //        this.createdby = node.getProperty("createdby");
    //        this.updatedby = node.getProperty("updatedby");
    //        this.datecreated = node.getProperty("datecreated");
    //        this.dateupdated = node.getProperty("dateupdated");
    //        this.comment = node.getProperty("comment");
    //        this.sno = node.getProperty("sno");
    //        this.imagename = node.getProperty("imagename");
    //        this.imagedata = node.getProperty("imagedata");
    //        this.position = node.getProperty("position");
    //        this.slot = node.getProperty("slot");
    //        this.shelf = node.getProperty("shelf");
    //
    //        if(this.shelf == null && this.labelname.contains("S_"))
    //        	this.shelf =  this.labelname.substring( this.labelname.indexOf("S_")+2, this.labelname.indexOf(":"));
    //        if(this.slot == null && this.labelname.contains("Sl_"))
    //        	this.slot =  this.labelname.substring(this.labelname.indexOf("Sl_")+3);
    //        if(this.position == null && this.labelname.contains("Ps_"))
    //        	this.position = this.labelname.substring(this.labelname.indexOf("Ps_")+3);
    //
    //
    //        List<String> sites = node.getProperty("site");
    //      	if (sites != null && sites.size() > 0)
    //      		this.site = sites.get(0);
    //
    //      	 List<String> roomlocations = node.getProperty("roomlocation");
    //       	if (roomlocations != null && roomlocations.size() > 0)
    //       		this.roomlocation = roomlocations.get(0);
    //    }

    public Map<Object, Object> getRackMap() {
        nodeMap.put("@rid", this.id);
        nodeMap.put("name", this.name);
        nodeMap.put("comment", this.comment);
        nodeMap.put("labelname", this.labelname);
        nodeMap.put("createdby", this.createdby);
        nodeMap.put("updatedby", this.updatedby);
        nodeMap.put("datecreated", this.datecreated);
        nodeMap.put("sno", this.sno);
        nodeMap.put("imagename", this.imagename);
        nodeMap.put("imagedata", this.imagedata);
        nodeMap.put("position", this.position);
        nodeMap.put("slot", this.slot);
        nodeMap.put("position", this.position);
        nodeMap.put("shelf", this.shelf);
        nodeMap.put("site", this.site);
        nodeMap.put("roomlocation", this.roomlocation);

        return nodeMap;
    }
}
