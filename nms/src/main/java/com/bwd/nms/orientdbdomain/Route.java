package com.bwd.nms.orientdbdomain;

//import com.orientechnologies.orient.core.sql.executor.OResult;

import com.orientechnologies.orient.core.sql.executor.OResult;

import java.io.Serializable;
import java.util.*;

/**
 * A Route.
 */
//@Entity
//@Table(name = "route")
public class Route implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<Object,Object> routeMap = new HashMap<Object, Object>();



    // @Id
    //  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    //  @NotNull
    // @Column(name = "routename", nullable = false)
    private String routename;

    // @Column(name = "labelname")
    private String labelname;

    // @Column(name = "labelname")
    private String comment;


    private String aend;

    private String zend;

    private List<String> segments;

    private Set<Site> segmentsDetails = new HashSet<>();

    public Route(String id, String name, String aend, String zend, List<String> segmentnames, String comment) {
        setId(id);
        setRoutename(name);
        setAend(aend);
        setZend(zend);
        setSegments(segmentnames);
        setComment(comment);
    }

    public Set<Site> getSegmentsDetails() {
        return segmentsDetails;
    }

    public void setSegmentsDetails(Set<Site> segmentsDetails) {
        this.segmentsDetails = segmentsDetails;
    }

    public List<String> getSegments() {
        return segments;
    }

    public void setSegments(List<String> segments) {
        this.segments = segments;
    }

    public String getAend() {
        return aend;
    }

    public void setAend(String aend) {
        this.aend = aend;
    }

    public String getZend() {
        return zend;
    }

    public void setZend(String zend) {
        this.zend = zend;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

//	public List<String> getSegments() {
//		return segments;
//	}
//
//	public void setSegments(List<String> segments) {
//		this.segments = segments;
//	}

//	@OneToMany(mappedBy = "routename")
//    @JsonIgnore
    // private List<String> segments;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoutename() {
        return routename;
    }

    public Route routename(String routename) {
        this.routename = routename;
        return this;
    }

    public void setRoutename(String routename) {
        this.routename = routename;
    }

    public String getLabelname() {
        return labelname;
    }

    public Route labelname(String labelname) {
        this.labelname = labelname;
        return this;
    }

    public void setLabelname(String labelname) {
        this.labelname = labelname;
    }





//    public Route removeSegments(Segment segment) {
//        this.segments.remove(segment);
//        segment.setRoutename(null);
//        return this;
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
        Route route = (Route) o;
        if (route.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), route.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Route{" +
            "id=" + getId() +
            ", routename='" + getRoutename() + "'" +
            ", labelname='" + getLabelname() + "'" +
            "}";
    }

    public Route(OResult route) {
        this.id = route.getProperty("@rid").toString();
        this.routename = route.getProperty("name");
        this.labelname = route.getProperty("labelname");
        //  this.segments = route.getProperty("segments");
        this.comment = route.getProperty("comment");
        this.aend = route.getProperty("aend");
        this.zend = route.getProperty("zend");

        if(route.getProperty("segmentnames") != null)
            this.segments = route.getProperty("segmentnames");

        if(route.getProperty("lat") != null) {
            List<String> latArray = 	((ArrayList<String>)route.getProperty("lat"));
            List<String> longArray = 	((ArrayList<String>)route.getProperty("long"));
            Site s= null;
            for(int i = 0 ; i < latArray.size(); i++) {
                s = new Site();
                s.latitude(Float.valueOf(Float.parseFloat(latArray.get(i))));
                s.longitude(Float.valueOf(Float.parseFloat(longArray.get(i))));
                this.segmentsDetails.add(s);

            }
        }
    }

    public Map<Object,Object> routeMap() {
        routeMap.put("@rid",  this.id);
        routeMap.put("name",  this.routename);
        routeMap.put("comment",  this.comment);
        routeMap.put("labelname",  this.labelname);
//        routeMap.put("segments",  this.segments);
        routeMap.put("aend",  this.aend);
        routeMap.put("zend",  this.zend);

        return routeMap;
    }

    public Route() {}
}
