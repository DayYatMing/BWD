package com.bwd.nms.otrsdomain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Table("ticket_type")  // R2DBC reactive table mapping
public class TicketType {

    private static final long serialVersionUID = 1L;

    @Id
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

}
