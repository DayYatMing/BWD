/**
 *
 */
package com.bwd.nms.otrsdomain;


import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "customer_user")

public class CustomerUser implements Serializable{

	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;


    @Column(name = "customer_id")
   	private String customerid;

    @Column(name = "login")
   	private String login;

    @Column(name = "email")
    private String email;

    @Column(name = "valid_id")
    private Long valid;

    public Long getValid() {
        return valid;
    }

    public void setValid(Long valid) {
        this.valid = valid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}



}
