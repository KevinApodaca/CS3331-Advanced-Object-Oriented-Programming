//package pricewatcher.model;


public class PriceFinder {
	
	public PriceFinder() {
	}
	
	public double getNewPrice (String url) {
		/* Gets the current price of an item. For this version, price is randomly generated within a range.*/
		return 25.0 + Math.random() * (100.0 - 25.0);
	}
	
}