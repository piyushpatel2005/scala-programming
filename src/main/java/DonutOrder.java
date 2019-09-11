public class DonutOrder extends Order {

	private double price;
	private String flavour;
	
	public DonutOrder(int quantity, double price, String flavour) {
		super(quantity);
		this.price = price;
		this.flavour = flavour;
	}
	
	@Override
	public double totalPrice() {
		double totalPrice = this.getQuantity() > 5 ? this.getQuantity() * this.getPrice() : this.getQuantity() * this.getPrice() * 1.07;
		return totalPrice;
	}
	
	public double getPrice() {
		return this.price;
	}
	
	public String toString() {

//		return String.format("Donut order of %s worth %f.",this.getFlavour(), this.totalPrice());
		return String.format("Donut order of %s worth %.2f.",this.getFlavour(), this.totalPrice());
	}

	private String getFlavour() {
		return this.flavour;
	}
}
