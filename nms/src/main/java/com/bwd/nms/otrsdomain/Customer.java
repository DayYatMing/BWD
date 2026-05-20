package com.bwd.nms.otrsdomain;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "customer_company")

public class Customer {

    private static final long serialVersionUID = 1L;

    @Column(name = "customer_id"  ,  insertable = false , updatable = false)
    private String id;

    @Column(name = "name"  ,  insertable = false , updatable = false)
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
