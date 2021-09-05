package ae.accumed.lookuprequestsmanager.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Payers {
    private int id;
    private String crawlingMethode;
    private Boolean needCaptcha;
    private Boolean payerActive;
    private String payerCode;
    private String payerName;
    private int crawlerCountMs;

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
    @Column(name = "crawling_methode")
    public String getCrawlingMethode() {
        return crawlingMethode;
    }

    public void setCrawlingMethode(String crawlingMethode) {
        this.crawlingMethode = crawlingMethode;
    }

    @Basic
    @Column(name = "need_captcha")
    public Boolean getNeedCaptcha() {
        return needCaptcha;
    }

    public void setNeedCaptcha(Boolean needCaptcha) {
        this.needCaptcha = needCaptcha;
    }

    @Basic
    @Column(name = "payer_active")
    public Boolean getPayerActive() {
        return payerActive;
    }

    public void setPayerActive(Boolean payerActive) {
        this.payerActive = payerActive;
    }

    @Basic
    @Column(name = "payer_code")
    public String getPayerCode() {
        return payerCode;
    }

    public void setPayerCode(String payerCode) {
        this.payerCode = payerCode;
    }

    @Basic
    @Column(name = "payer_name")
    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payers payers = (Payers) o;
        return id == payers.id &&
                Objects.equals(crawlingMethode, payers.crawlingMethode) &&
                Objects.equals(needCaptcha, payers.needCaptcha) &&
                Objects.equals(payerActive, payers.payerActive) &&
                Objects.equals(payerCode, payers.payerCode) &&
                Objects.equals(payerName, payers.payerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, crawlingMethode, needCaptcha, payerActive, payerCode, payerName);
    }

    @Basic
    @Column(name = "crawler_count_ms")
    public int getCrawlerCountMs() {
        return crawlerCountMs;
    }

    public void setCrawlerCountMs(int crawlerCountMs) {
        this.crawlerCountMs = crawlerCountMs;
    }
}
