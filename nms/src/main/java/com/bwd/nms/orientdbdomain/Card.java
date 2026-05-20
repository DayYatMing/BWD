package com.bwd.nms.orientdbdomain;

//import com.orientechnologies.orient.core.sql.executor.OResult;

import com.bwd.nms.orientdbdomain.enumeration.CardState;
import com.bwd.nms.orientdbdomain.enumeration.CardType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.orientechnologies.orient.core.sql.executor.OResult;

import java.io.Serializable;
import java.util.*;

public class Card implements Serializable {

    private static final long serialVersionUID = 1L;
    private Map<Object,Object> cardMap = new HashMap<Object, Object>();
    private String id;
    private String cardname;
    private CardType cardtype;
    private String labelname;
    private String imagename;
    private byte[] imagedata;
    private String imagedataContentType;
    private String vendor;
    private String softwareversion;
    private String createdby;
    private String updatedby;
    private Date datecreated;
    private Date dateupdated;
    private String comment;
    private String prrperties;
    private String dls;
    private Integer freeports;
    private String model;
    private String sno;
    private CardState cardState;
    private Integer totalports;
    private Integer freePorts;
    private String shelf;
    private String slot;
    private String position;
    private String connector;
    private String site;
    private String cardlabelname;
    private String roomlocation;
    private boolean tx;
    private Integer capacity;
    private String frequency;
    private String wavelength;
    private String thirdparty;
    private String cardcomment;
    private String nodename;
    private List<String> portnames;

    public Card(String id, String site, String roomlocation, CardType cardtype, String cardlabelname,
                String shelf, String slot, String position, String dls, Integer capacity, Integer totalports, String comment) {
        setId(id);
        setSite(site);
        setRoomlocation(roomlocation);
        setCardtype(cardtype);
        setCardlabelname(cardlabelname);
        setShelf(shelf);
        setSlot(slot);
        setPosition(position);
        setDls(dls);
        setCapacity(capacity);
        setTotalports(totalports);
        setComment(comment);
    }

    public String getNodename() {
        return nodename;
    }

    public void setNodename(String nodename) {
        this.nodename = nodename;
    }

    public List<String> getPortnames() {
        return portnames;
    }

    public void setPortnames(List<String> portnames) {
        this.portnames = portnames;
    }

    public boolean isTx() {
        return tx;
    }

    public void setTx(boolean tx) {
        this.tx = tx;
    }

    public String getCardcomment() {
        return cardcomment;
    }

    public void setCardcomment(String cardcomment) {
        this.cardcomment = cardcomment;
    }

    public String getThirdparty() {
        return thirdparty;
    }

    public void setThirdparty(String thirdparty) {
        this.thirdparty = thirdparty;
    }

    public String getPrrperties() {
        return prrperties;
    }

    public void setPrrperties(String prrperties) {
        this.prrperties = prrperties;
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

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getRoomlocation() {
        return roomlocation;
    }

    public void setRoomlocation(String roomlocation) {
        this.roomlocation = roomlocation;
    }

    public String getCardlabelname() {
        return cardlabelname;
    }

    public void setCardlabelname(String cardlabelname) {
        this.cardlabelname = cardlabelname;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Integer getFreePorts() {
        return freePorts;
    }

    public void setFreePorts(Integer freePorts) {
        this.freePorts = freePorts;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getConnector() {
        return connector;
    }

    public void setConnector(String connector) {
        this.connector = connector;
    }

    public String getDls() {
        return dls;
    }

    public void setDls(String dls) {
        this.dls = dls;
    }

    public Integer getFreeports() {
        return freeports;
    }

    public void setFreeports(Integer freeports) {
        this.freeports = freeports;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public CardState getCardState() {
        return cardState;
    }

    public void setCardState(CardState cardState) {
        this.cardState = cardState;
    }

    public Integer getTotalports() {
        return totalports;
    }

    public void setTotalports(Integer totalports) {
        this.totalports = totalports;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Card() {}

    @JsonIgnore
    private Set<Port> ports = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCardname() {
        return cardname;
    }

    public Card cardname(String cardname) {
        this.cardname = cardname;
        return this;
    }

    public void setCardname(String cardname) {
        this.cardname = cardname;
    }

    public CardType getCardtype() {
        return cardtype;
    }

    public Card cardtype(CardType cardtype) {
        this.cardtype = cardtype;
        return this;
    }

    public void setCardtype(CardType cardtype) {
        this.cardtype = cardtype;
    }

    public String getLabelname() {
        return labelname;
    }

    public Card labelname(String labelname) {
        this.labelname = labelname;
        return this;
    }

    public void setLabelname(String labelname) {
        this.labelname = labelname;
    }

    public String getImagename() {
        return imagename;
    }

    public Card imagename(String imagename) {
        this.imagename = imagename;
        return this;
    }

    public void setImagename(String imagename) {
        this.imagename = imagename;
    }

    public byte[] getImagedata() {
        return imagedata;
    }

    public Card imagedata(byte[] imagedata) {
        this.imagedata = imagedata;
        return this;
    }

    public void setImagedata(byte[] imagedata) {
        this.imagedata = imagedata;
    }

    public String getImagedataContentType() {
        return imagedataContentType;
    }

    public Card imagedataContentType(String imagedataContentType) {
        this.imagedataContentType = imagedataContentType;
        return this;
    }

    public void setImagedataContentType(String imagedataContentType) {
        this.imagedataContentType = imagedataContentType;
    }

    public String getVendor() {
        return vendor;
    }

    public Card vendor(String vendor) {
        this.vendor = vendor;
        return this;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getSoftwareversion() {
        return softwareversion;
    }

    public Card softwareversion(String softwareversion) {
        this.softwareversion = softwareversion;
        return this;
    }

    public void setSoftwareversion(String softwareversion) {
        this.softwareversion = softwareversion;
    }

    public String getCreatedby() {
        return createdby;
    }

    public Card createdby(String createdby) {
        this.createdby = createdby;
        return this;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public String getUpdatedby() {
        return updatedby;
    }

    public Card updatedby(String updatedby) {
        this.updatedby = updatedby;
        return this;
    }

    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }

    public Date getDatecreated() {
        return datecreated;
    }

    public Card datecreated(Date datecreated) {
        this.datecreated = datecreated;
        return this;
    }

    public void setDatecreated(Date datecreated) {
        this.datecreated = datecreated;
    }

    public Date getDateupdated() {
        return dateupdated;
    }

    public Card dateupdated(Date dateupdated) {
        this.dateupdated = dateupdated;
        return this;
    }

    public void setDateupdated(Date dateupdated) {
        this.dateupdated = dateupdated;
    }

    public Set<Port> getPorts() {
        return ports;
    }

    public Card ports(Set<Port> ports) {
        this.ports = ports;
        return this;
    }

    public void setPorts(Set<Port> ports) {
        this.ports = ports;
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
        Card card = (Card) o;
        if (card.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), card.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Card{" +
            "id=" + getId() +
            ", cardname='" + getCardname() + "'" +
            ", cardtype='" + getCardtype() + "'" +
            ", labelname='" + getLabelname() + "'" +
            ", imagename='" + getImagename() + "'" +
            ", imagedata='" + getImagedata() + "'" +
            ", imagedataContentType='" + getImagedataContentType() + "'" +
            ", vendor='" + getVendor() + "'" +
            ", softwareversion='" + getSoftwareversion() + "'" +
            ", createdby='" + getCreatedby() + "'" +
            ", updatedby='" + getUpdatedby() + "'" +
            ", datecreated='" + getDatecreated() + "'" +
            ", dateupdated='" + getDateupdated() + "'" +
            "}";
    }

    public Card(OResult card) {
        this.id = card.getProperty("@rid").toString();
        this.cardname = card.getProperty("name");
        this.cardlabelname = card.getProperty("cardlabelname");
        this.labelname = card.getProperty("labelname");
        this.cardtype = CardType.fromString(card.getProperty("cardtype"));
        this.imagedata = card.getProperty("imagedata");
        this.imagename = card.getProperty("imagename");
        this.vendor = card.getProperty("vendor");
        this.softwareversion = card.getProperty("softwareversion");
        this.createdby = card.getProperty("createdby");
        this.updatedby = card.getProperty("updatedby");
        this.datecreated = card.getProperty("datecreated");
        this.dateupdated = card.getProperty("dateupdated");
        this.cardcomment = card.getProperty("comment");
        this.dls = card.getProperty("dls");
        this.freeports = card.getProperty("freeports");
        this.model = card.getProperty("model");
        this.sno = card.getProperty("sno");
        this.shelf = card.getProperty("shelf");
        this.slot = card.getProperty("slot");
        this.position = card.getProperty("position");
        this.roomlocation = card.getProperty("roomlocation");
        this.connector = card.getProperty("connectorType");
        this.prrperties = card.getProperty("portproperty");
        this.site = card.getProperty("site");
        this.totalports = card.getProperty("totalports");
        this.tx = card.getProperty("txrx") != null ? card.getProperty("txrx") : false;
        this.cardState = CardState.fromString(card.getProperty("cardState"));
        this.portnames =  card.getProperty("portnames") != null ? (List<String>)card.getProperty("portnames") : null;
        this.capacity =  card.getProperty("capacity") != null ? card.getProperty("capacity")  : 0;
        this.wavelength =  card.getProperty("wavelength");
        this.frequency =  card.getProperty("frequency");
        this.connector =  card.getProperty("connector");
        this.comment =  card.getProperty("comment");
        this.nodename =  card.getProperty("nodename");
        this.tx =  card.getProperty("tx") != null ? Boolean.valueOf(card.getProperty("tx"))  : false;
    }

    public Map<Object,Object> cardMap() {
        cardMap.put("@rid",  this.id);
        cardMap.put("name",  this.cardname);
        cardMap.put("comment",  this.comment);
        cardMap.put("labelname",  this.labelname);
        cardMap.put("createdby",  this.createdby);
        cardMap.put("updatedby",  this.updatedby);
        cardMap.put("datecreated",  this.datecreated);
        cardMap.put("sno",  this.sno);
        cardMap.put("imagename",  this.imagename);
        cardMap.put("imagedata",  this.imagedata);
        cardMap.put("cardtype",  this.cardtype.name());
        cardMap.put("vendor",  this.vendor);
        cardMap.put("softwareversion",  this.softwareversion);
        cardMap.put("dls",  this.dls);
        cardMap.put("freeports",  this.freeports);
        cardMap.put("model",  this.model);
        cardMap.put("cardState",  this.cardState);
        cardMap.put("totalports",  this.totalports);
        cardMap.put("tx",  this.tx);
        cardMap.put("nodename",  this.nodename);
        cardMap.put("shelf",  this.shelf);
        cardMap.put("slot",  this.slot);
        cardMap.put("position",  this.position);
        cardMap.put("roomlocation",  this.roomlocation);
        cardMap.put("connector",  this.connector);
        cardMap.put("site",  this.site);
        cardMap.put("thirdparty",  this.thirdparty);
        cardMap.put("cardcomment",  this.cardcomment);
        cardMap.put("frequency",  this.frequency);
        cardMap.put("capacity",  this.capacity);
        cardMap.put("wavelength",  this.wavelength);
        cardMap.put("portnames",  this.portnames);
        cardMap.put("prrperties",  this.prrperties);
        return cardMap;
    }
}
