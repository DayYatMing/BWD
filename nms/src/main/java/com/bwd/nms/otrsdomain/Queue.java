package com.bwd.nms.otrsdomain;
import javax.persistence.*;
@Entity
@Table(name = "queue")
public class Queue {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name"  ,  insertable = false , updatable = false)
    private String name;
}
