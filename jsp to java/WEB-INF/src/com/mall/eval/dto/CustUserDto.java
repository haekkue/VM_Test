package com.hmall.eval.dto;
import java.io.Serializable;
import com.hmall.eval.util.BaseObject;

public class CustUserDto extends BaseObject implements Serializable{

	private static final long serialVersionUID = -1115013073291973900L;

	private String cust_No;
	private String custUserId;
	private String custName;
	private String custMail;
	private String custTel;
	private String custPartCode;
	private String custPartCodeName;
	private String custSectionCode;
	private String custSectionCodeName;
	private String adminFlag;
	private String custRoleCode;
	private String custRoleCodeName;
	private String selectMemberFlag;
	private String findItem;
	private String findWord;
	private boolean isAdmin;
	private String tot_Saveamt;
	private String md_sabun;
	
	private String startDate;
	private String endDate;
	
	public String getTot_Saveamt() {
		return tot_Saveamt;
	}
	public void setTot_Saveamt(String tot_Saveamt) {
		this.tot_Saveamt = tot_Saveamt;
	}
	public String getAdminFlag() {
		return adminFlag;
	}
	public void setAdminFlag(String adminFlag) {
		this.adminFlag = adminFlag;
	}
	public String getCust_No() {
		return cust_No;
	}
	public void setCust_No(String cust_No) {
		this.cust_No = cust_No;
	}
	public String getCustMail() {
		return custMail;
	}
	public void setCustMail(String custMail) {
		this.custMail = custMail;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getCustPartCode() {
		return custPartCode;
	}
	public void setCustPartCode(String custPartCode) {
		this.custPartCode = custPartCode;
	}
	public String getCustPartCodeName() {
		return custPartCodeName;
	}
	public void setCustPartCodeName(String custPartCodeName) {
		this.custPartCodeName = custPartCodeName;
	}
	public String getCustRoleCode() {
		return custRoleCode;
	}
	public void setCustRoleCode(String custRoleCode) {
		this.custRoleCode = custRoleCode;
	}
	public String getCustRoleCodeName() {
		return custRoleCodeName;
	}
	public void setCustRoleCodeName(String custRoleCodeName) {
		this.custRoleCodeName = custRoleCodeName;
	}
	public String getCustSectionCode() {
		return custSectionCode;
	}
	public void setCustSectionCode(String custSectionCode) {
		this.custSectionCode = custSectionCode;
	}
	public String getCustSectionCodeName() {
		return custSectionCodeName;
	}
	public void setCustSectionCodeName(String custSectionCodeName) {
		this.custSectionCodeName = custSectionCodeName;
	}
	public String getCustTel() {
		return custTel;
	}
	public void setCustTel(String custTel) {
		this.custTel = custTel;
	}
	public String getCustUserId() {
		return custUserId;
	}
	public void setCustUserId(String custUserId) {
		this.custUserId = custUserId;
	}
	public String getFindItem() {
		return findItem;
	}
	public void setFindItem(String findItem) {
		this.findItem = findItem;
	}
	public String getFindWord() {
		return findWord;
	}
	public void setFindWord(String findWord) {
		this.findWord = findWord;
	}
	public String getSelectMemberFlag() {
		return selectMemberFlag;
	}
	public void setSelectMemberFlag(String selectMemberFlag) {
		this.selectMemberFlag = selectMemberFlag;
	}
	public boolean isAdmin() {
		return isAdmin;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getMd_sabun() {
		return md_sabun;
	}
	public void setMd_sabun(String md_sabun) {
		this.md_sabun = md_sabun;
	}
		
}
