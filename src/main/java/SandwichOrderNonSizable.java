public class SandwichOrderNonSizable extends NonSizableOrder {

	private String filling;
	private String bread;

	public SandwichOrderNonSizable(int quantity, double price, String filling, String bread) {
		super(quantity, price);
		this.filling = filling;
		this.bread = bread;
	}
	
	@Override
	public double totalPrice() {
		double totalPrice = this.getQuantity() * this.getPrice() * 1.07;
		return totalPrice;
	}
	

	public String toString() {

//		return String.format("Donut order of %s worth %f.",this.getFlavour(), this.totalPrice());
		return String.format("Sandwich order of %d %s sandwiches with %s bread worth %.2f.",this.getQuantity(), this.getFilling(), this.getBread(), this.totalPrice());
	}

	private String getFilling() {
		return this.filling;
	}

	private String getBread() {
		return this.bread;
	}
}
