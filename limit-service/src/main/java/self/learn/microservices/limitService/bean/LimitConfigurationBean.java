package self.learn.microservices.limitService.bean;

public class LimitConfigurationBean {
	
	private int minimum;
	private int maxmium;
	
	public LimitConfigurationBean(int min, int max) {
		this.minimum = min;
		this.maxmium = max;
	}

	public int getMinimum() {
		return minimum;
	}

	public int getMaxmium() {
		return maxmium;
	}
	
	

}
