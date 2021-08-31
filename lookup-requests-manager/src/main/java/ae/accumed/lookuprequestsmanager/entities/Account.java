package ae.accumed.lookuprequestsmanager.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Account {
    private int id;
    private Boolean isactive;
    private String password;
    private String userName;
    private Facility facilityByFacilityId;
    private Payers payersByPayerId;
    private Collection<Transactions> transactionsById;
    private String errorMessage;
    private Boolean isSuspended;

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
    @Column(name = "isactive")
    public Boolean getIsactive() {
        return isactive;
    }

    public void setIsactive(Boolean isactive) {
        this.isactive = isactive;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "user_name")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id &&
                Objects.equals(isactive, account.isactive) &&
                Objects.equals(password, account.password) &&
                Objects.equals(userName, account.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isactive, password, userName);
    }

    @ManyToOne
    @JoinColumn(name = "facility_id", referencedColumnName = "id", nullable = false)
    public Facility getFacilityByFacilityId() {
        return facilityByFacilityId;
    }

    public void setFacilityByFacilityId(Facility facilityByFacilityId) {
        this.facilityByFacilityId = facilityByFacilityId;
    }

    @ManyToOne
    @JoinColumn(name = "payer_id", referencedColumnName = "id", nullable = false)
    public Payers getPayersByPayerId() {
        return payersByPayerId;
    }

    public void setPayersByPayerId(Payers payersByPayerId) {
        this.payersByPayerId = payersByPayerId;
    }

    @OneToMany(mappedBy = "accountByAccountId")
    public Collection<Transactions> getTransactionsById() {
        return transactionsById;
    }

    public void setTransactionsById(Collection<Transactions> transactionsById) {
        this.transactionsById = transactionsById;
    }

    @Basic
    @Column(name = "error_message")
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Basic
    @Column(name = "is_suspended")
    public Boolean getSuspended() {
        return isSuspended;
    }

    public void setSuspended(Boolean suspended) {
        isSuspended = suspended;
    }
}
