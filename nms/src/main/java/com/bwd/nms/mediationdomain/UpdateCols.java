package com.bwd.nms.mediationdomain;
import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "reserved_capacity_calculation")
public class UpdateCols implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "total"  )
    private String total;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getTotal() {
        return total;
    }
    public void setTotal(String total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "UpdateCols{" +
            "id='" + id + '\'' +
            ", total='" + total + '\'' +

            '}';
    }
}
