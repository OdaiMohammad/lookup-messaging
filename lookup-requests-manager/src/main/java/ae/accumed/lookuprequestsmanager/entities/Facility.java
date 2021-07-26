package ae.accumed.lookuprequestsmanager.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Facility {
    private int id;
    private String description;
    private String facilityCode;
    private String facilityName;
    private boolean facilityActive;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "facility_code")
    public String getFacilityCode() {
        return facilityCode;
    }

    public void setFacilityCode(String facilityCode) {
        this.facilityCode = facilityCode;
    }

    @Basic
    @Column(name = "facility_name")
    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Facility facility = (Facility) o;
        return id == facility.id &&
                Objects.equals(description, facility.description) &&
                Objects.equals(facilityCode, facility.facilityCode) &&
                Objects.equals(facilityName, facility.facilityName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, facilityCode, facilityName);
    }

    @Basic
    @Column(name = "facility_active")
    public boolean isFacilityActive() {
        return facilityActive;
    }

    public void setFacilityActive(boolean facilityActive) {
        this.facilityActive = facilityActive;
    }
}
