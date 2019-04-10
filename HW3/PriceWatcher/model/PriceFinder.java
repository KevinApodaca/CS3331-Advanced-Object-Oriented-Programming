package PriceWatcher.model;

import java.math.BigDecimal;

public class PriceFinder {
	
	public PriceFinder() {
	}
	
	public double getNewPrice (String url) {
		return roundPrice(25.0 + Math.random() * (100.0 - 25.0));

	}

	public double roundPrice (double price){
		BigDecimal bd = new BigDecimal(price);
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		return bd.doubleValue();
	}
	
}