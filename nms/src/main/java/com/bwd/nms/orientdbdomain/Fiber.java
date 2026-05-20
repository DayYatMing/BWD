package com.bwd.nms.orientdbdomain;

//import com.orientechnologies.orient.core.sql.executor.OResult;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

/**
 * A Fiber.
 */
public class Fiber implements Serializable {

    private static final long serialVersionUID = 1L;
    private Map<Object, Object> fiberMap = new HashMap<Object, Object>();

    //    @Id
    //    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //    @NotNull
    //    @Column(name = "fibername", nullable = false)
    private String fibername;

    //    @Column(name = "labelname")
    private String labelname;

    //    @Column(name = "createdby")
    private String createdby;

    //    @Column(name = "updatedby")
    private String updatedby;

    //    @Column(name = "datecreated")
    private LocalDate datecreated;

    //    @Column(name = "dateupdated")
    private LocalDate dateupdated;

    //    @OneToMany(mappedBy = "fibername")
    //    @JsonIgnore
    private Set<Site> sites = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFibername() {
        return fibername;
    }

    public Fiber fibername(String fibername) {
        this.fibername = fibername;
        return this;
    }

    public void setFibername(String fibername) {
        this.fibername = fibername;
    }

    public String getLabelname() {
        return labelname;
    }

    public Fiber labelname(String labelname) {
        this.labelname = labelname;
        return this;
    }

    public void setLabelname(String labelname) {
        this.labelname = labelname;
    }

    public String getCreatedby() {
        return createdby;
    }

    public Fiber createdby(String createdby) {
        this.createdby = createdby;
        return this;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public String getUpdatedby() {
        return updatedby;
    }

    public Fiber updatedby(String updatedby) {
        this.updatedby = updatedby;
        return this;
    }

    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }

    public LocalDate getDatecreated() {
        return datecreated;
    }

    public Fiber datecreated(LocalDate datecreated) {
        this.datecreated = datecreated;
        return this;
    }

    public void setDatecreated(LocalDate datecreated) {
        this.datecreated = datecreated;
    }

    public LocalDate getDateupdated() {
        return dateupdated;
    }

    public Fiber dateupdated(LocalDate dateupdated) {
        this.dateupdated = dateupdated;
        return this;
    }

    public void setDateupdated(LocalDate dateupdated) {
        this.dateupdated = dateupdated;
    }

    public Set<Site> getSites() {
        return sites;
    }

    public Fiber sites(Set<Site> sites) {
        this.sites = sites;
        return this;
    }

    public Fiber addSites(Site site) {
        this.sites.add(site);
        site.setFibername(this);
        return this;
    }

    public Fiber removeSites(Site site) {
        this.sites.remove(site);
        site.setFibername(null);
        return this;
    }

    public void setSites(Set<Site> sites) {
        this.sites = sites;
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
        Fiber fiber = (Fiber) o;
        if (fiber.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fiber.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return (
            "Fiber{" +
            "id=" +
            getId() +
            ", fibername='" +
            getFibername() +
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

    //    public Fiber(OResult fiber) {
    //        this.id = fiber.getProperty("@rid");
    //        this.fibername = fiber.getProperty("name");
    //        this.labelname = fiber.getProperty("labelname");
    //        this.createdby = fiber.getProperty("createdby");
    //        this.updatedby = fiber.getProperty("updatedby");
    //        this.datecreated = fiber.getProperty("datecreated");
    //        this.dateupdated = fiber.getProperty("dateupdated");
    //    }

    public Map<Object, Object> getFiberMap() {
        //    	serviceMap
        //        this.id = customer.getProperty("@rid");
        //        this.customername = customer.getProperty("name");
        //        this.shortname = customer.getProperty("shortname");
        //        this.address = customer.getProperty("address");
        //        this.comment = customer.getProperty("comment");
        //        this.contactdetails = customer.getProperty("contactdetails");
        //        this.createdby = customer.getProperty("createdby");
        //        this.updatedby = customer.getProperty("updatedby");
        //        this.datecreated = customer.getProperty("datecreated");
        //        this.dateupdated = customer.getProperty("dateupdated");

        return fiberMap;
    }
}
