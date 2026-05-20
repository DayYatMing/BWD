package com.bwd.nms.service.dto;
import com.bwd.nms.mediationdomain.PMCustomer;
import java.io.Serializable;

public class PMCustomerDTO implements Serializable {

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    private long id;
    private String name;
    private String shortname;
    private String active;

    public PMCustomerDTO() {}

    public PMCustomerDTO(PMCustomer pmCustomer) {
        this.id = pmCustomer.getId();
        this.name = pmCustomer.getName();
        this.shortname = pmCustomer.getShortname();
        this.active = pmCustomer.getActive();
    }
}
