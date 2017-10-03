package sample.model;

import java.util.Date;

import com.sun.jmx.snmp.Timestamp;

public class Invoice {

    private String invoiceid;
    private String title;
    private String detail;
    private String totalfee;
    private Date update_date;
    public String getInvoiceId() {
        return invoiceid;
    }
    public void setInvoiceId(String invoiceid) {
        this.invoiceid = invoiceid;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDetail() {
        return detail;
    }
    public void setDetail(String detail) {
        this.detail = detail;
    }
    public String getTotalFee() {
        return totalfee;
    }
    public void setTotalFee(String totalfee) {
        this.totalfee = totalfee;
    }
    public Date getUpdateDate() {
        return update_date;
    }
    public void setUpdateDate(Date update_date) {
        this.update_date = update_date;
    }
    @Override
    public String toString() {
        return "Invoice [invoiceid=" + invoiceid + ", title=" + title
                + ", detail=" + detail + ", totalfee=" + totalfee + ", update_date="
                + update_date + "]";
    }    
}