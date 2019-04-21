import java.util.*;

class Payment {
	Date timeOfPay;
	double amount;
	String modeOfPay;
	String vehicle_id;
	String customerId;
	
	public Date getTimeOfPay() {
		return timeOfPay;
	}
	public void setTimeOfPay(Date timeOfPay) {
		this.timeOfPay = timeOfPay;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getModeOfPay() {
		return modeOfPay;
	}
	public void setModeOfPay(String modeOfPay) {
		this.modeOfPay = modeOfPay;
	}
	public String getVehicle_id() {
		return vehicle_id;
	}
	public void setVehicle_id(String vehicle_id) {
		this.vehicle_id = vehicle_id;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	
}