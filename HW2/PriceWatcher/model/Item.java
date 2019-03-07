package PriceWatcher.model;

public class Item {

	private String name;
	private double originalPrice,currentPrice,priceChange;
	private String url, dateAdded;

	
	public Item(String name, double price, String url, String dateAdded) {
		this.name = name;
		this.currentPrice = price;
		this.originalPrice = price;
		this.url = url;
		this.priceChange =  0.0;
		this.dateAdded = dateAdded;
	}
	
	public void updatePrice(double newPrice) {
		this.priceChange =  ((newPrice - this.originalPrice) / (this.originalPrice)) * 100;
		this.currentPrice = newPrice;
	}
	
	public double getOriginalPrice() {
		return this.originalPrice;
	}
	
	public double getCurrentPrice() {
		return this.currentPrice;
	}
	
	public double getPriceChange() {
		return this.priceChange;
	}
	
	public String getURL() {
		return this.url;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getDateAdded() {
		return this.dateAdded;
	}
	
	public void setOriginalPrice(double originalPrice) {
		this.originalPrice = originalPrice;
	}
	
	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}
	
	public void setPriceChange(double priceChange) {
		this.priceChange = priceChange;
	}
	
	public void setURL(String url) {
		this.url = url;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setDateAdded(String dateAdded) {
		this.dateAdded = dateAdded;
	}
	

}
