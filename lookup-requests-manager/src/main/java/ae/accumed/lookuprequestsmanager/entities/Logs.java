package ae.accumed.lookuprequestsmanager.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Logs {
    private int id;
    private String receivedMessage;
    private Timestamp logDate;
    private String result;
    private String state;

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
    @Column(name = "received_message")
    public String getReceivedMessage() {
        return receivedMessage;
    }

    public void setReceivedMessage(String receivedMessage) {
        this.receivedMessage = receivedMessage;
    }

    @Basic
    @Column(name = "log_date")
    public Timestamp getLogDate() {
        return logDate;
    }

    public void setLogDate(Timestamp logDate) {
        this.logDate = logDate;
    }

    @Basic
    @Column(name = "result")
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Basic
    @Column(name = "state")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Logs logs = (Logs) o;
        return id == logs.id &&
                Objects.equals(receivedMessage, logs.receivedMessage) &&
                Objects.equals(logDate, logs.logDate) &&
                Objects.equals(result, logs.result) &&
                Objects.equals(state, logs.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, receivedMessage, logDate, result, state);
    }
}
