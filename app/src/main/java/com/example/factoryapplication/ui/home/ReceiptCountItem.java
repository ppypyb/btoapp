package com.example.factoryapplication.ui.home;
/**
 * 工单统计 item
 * @author dyc
 *
 */
public class ReceiptCountItem {

	private String departName ;
	
	private String receiptCount ;
	
	public ReceiptCountItem(String departName, String receiptCount) {
		super();
		this.departName = departName;
		this.receiptCount = receiptCount;
	}

	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	public String getReceiptCount() {
		return receiptCount;
	}

	public void setReceiptCount(String receiptCount) {
		this.receiptCount = receiptCount;
	}

	@Override
	public String toString() {
		return "ReceiptCountItem [departName=" + departName + ", receiptCount="
				+ receiptCount + "]";
	}
	
	
}
