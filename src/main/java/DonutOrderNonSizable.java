public class DonutOrderNonSizable extends NonSizableOrder {

	private String flavour;

	public DonutOrderNonSizable(int quantity, double price, String flavour) {
		super(quantity, price);
		this.flavour = flavour;
	}
	
	@Override
	public double totalPrice() {
		double totalPrice = this.getQuantity() > 5 ? this.getQuantity() * this.getPrice() : this.getQuantity() * this.getPrice() * 1.07;
		return totalPrice;
	}
	

	public String toString() {

		return String.format("Donut order of %d %s worth %.2f.",this.getQuantity(), this.getFlavour(), this.totalPrice());
	}

	private String getFlavour() {
		return this.flavour;
	}
}
