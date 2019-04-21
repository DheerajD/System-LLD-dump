class ParkingSpot {
	private boolean isAvailable;
	private double price_per_hr;
	private String type; //for car or bike 
	private boolean isForHandicapped;

	public boolean isAvailable() {
		return isAvailable;
	}
	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	public double getPrice_per_hr() {
		return price_per_hr;
	}
	public void setPrice_per_hr(double price_per_hr) {
		this.price_per_hr = price_per_hr;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isForHandicapped() {
		return isForHandicapped;
	}
	public void setForHandicapped(boolean isForHandicapped) {
		this.isForHandicapped = isForHandicapped;
	}
	
}