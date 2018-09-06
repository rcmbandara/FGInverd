package beans;

import java.util.Date;

public class StocktbldgBean {
private int sn;
private int pid;
private String qualitygrade;
private Date scandate;
private int lid;
public int getSn() {
	return sn;
}
public void setSn(int sn) {
	this.sn = sn;
}
public int getPid() {
	return pid;
}
public void setPid(int pid) {
	this.pid = pid;
}
public String getQualitygrade() {
	return qualitygrade;
}
public void setQualitygrade(String qualitygrade) {
	this.qualitygrade = qualitygrade;
}
public Date getScandate() {
	return scandate;
}
public void setScandate(Date scandate) {
	this.scandate = scandate;
}
public int getLid() {
	return lid;
}
public void setLid(int lid) {
	this.lid = lid;
}

}
