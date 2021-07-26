package ae.accumed.lookuprequestsmanager.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
public class Transactions {
    private int id;
    private Integer bulkId;
    private Date createDate;
    private String eid;
    private String html;
    private String result;
    private Date resultDate;
    private String source;
    private String status;
    private Account accountByAccountId;
    private Account accountByAccountId_0;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "bulk_id")
    public Integer getBulkId() {
        return bulkId;
    }

    public void setBulkId(Integer bulkId) {
        this.bulkId = bulkId;
    }

    @Basic
    @Column(name = "create_date")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Basic
    @Column(name = "eid")
    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    @Basic
    @Column(name = "html")
    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
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
    @Column(name = "result_date")
    public Date getResultDate() {
        return resultDate;
    }

    public void setResultDate(Date resultDate) {
        this.resultDate = resultDate;
    }

    @Basic
    @Column(name = "source")
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Basic
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transactions that = (Transactions) o;
        return id == that.id &&
                Objects.equals(bulkId, that.bulkId) &&
                Objects.equals(createDate, that.createDate) &&
                Objects.equals(eid, that.eid) &&
                Objects.equals(html, that.html) &&
                Objects.equals(result, that.result) &&
                Objects.equals(resultDate, that.resultDate) &&
                Objects.equals(source, that.source) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bulkId, createDate, eid, html, result, resultDate, source, status);
    }

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    public Account getAccountByAccountId() {
        return accountByAccountId;
    }

    public void setAccountByAccountId(Account accountByAccountId) {
        this.accountByAccountId = accountByAccountId;
    }
}
