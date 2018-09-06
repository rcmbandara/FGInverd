package beans;

import java.util.Date;

public class WorkOrderDetailBean {
	private int woid;
	private int wo;
	private int revision;
	private String Customer;
	private String suborderno;
	private String invoiceno;
	private Date date;
	private int lastRevision;
	private int nos;
	private int pids;

	public int getWoid() {
		return woid;
	}

	public void setWoid(int woid) {
		this.woid = woid;
	}

	public int getWo() {
		return wo;
	}

	public void setWo(int wo) {
		this.wo = wo;
	}

	public int getRevision() {
		return revision;
	}

	public void setRevision(int revision) {
		this.revision = revision;
	}

	public String getCustomer() {
		return Customer;
	}

	public void setCustomer(String customer) {
		Customer = customer;
	}

	public String getSuborderno() {
		return suborderno;
	}

	public void setSuborderno(String suborderno) {
		this.suborderno = suborderno;
	}

	public String getInvoiceno() {
		return invoiceno;
	}

	public void setInvoiceno(String invoiceno) {
		this.invoiceno = invoiceno;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getLastRevision() {
		return lastRevision;
	}

	public void setLastRevision(int lastRevision) {
		this.lastRevision = lastRevision;
	}

	public int getNos() {
		return nos;
	}

	public void setNos(int nos) {
		this.nos = nos;
	}

	public int getPids() {
		return pids;
	}

	public void setPids(int pids) {
		this.pids = pids;
	}

}
