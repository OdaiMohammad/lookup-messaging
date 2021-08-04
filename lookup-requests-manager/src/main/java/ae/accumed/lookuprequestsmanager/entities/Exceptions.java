package ae.accumed.lookuprequestsmanager.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Exceptions {
    private int id;
    private Timestamp exceptionDate;
    private String exceptionTrace;
    private String exceptionMessage;
    private String emiratesId;
    private String facility;

    @Id
    @Basic
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "exception_date")
    public Timestamp getExceptionDate() {
        return exceptionDate;
    }

    public void setExceptionDate(Timestamp exceptionDate) {
        this.exceptionDate = exceptionDate;
    }

    @Basic
    @Column(name = "exception_trace")
    public String getExceptionTrace() {
        return exceptionTrace;
    }

    public void setExceptionTrace(String exceptionTrace) {
        this.exceptionTrace = exceptionTrace;
    }

    @Basic
    @Column(name = "exception_message")
    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    @Basic
    @Column(name = "emirates_id")
    public String getEmiratesId() {
        return emiratesId;
    }

    public void setEmiratesId(String emiratesId) {
        this.emiratesId = emiratesId;
    }

    @Basic
    @Column(name = "Facility")
    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exceptions that = (Exceptions) o;
        return id == that.id &&
                Objects.equals(exceptionDate, that.exceptionDate) &&
                Objects.equals(exceptionTrace, that.exceptionTrace) &&
                Objects.equals(exceptionMessage, that.exceptionMessage) &&
                Objects.equals(emiratesId, that.emiratesId) &&
                Objects.equals(facility, that.facility);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, exceptionDate, exceptionTrace, exceptionMessage, emiratesId, facility);
    }
}
