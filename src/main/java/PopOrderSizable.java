public class PopOrderSizable extends SizableOrder {

	private String brand;

	public PopOrderSizable(int quantity, String size, String brand) {

		super(quantity, size);
		this.brand = brand;
	}
	
	@Override
	public double totalPrice() {
		double totalPrice = 0.0;
		switch(this.getSize()) {
			case "small":
				totalPrice += 1.79 * this.getQuantity();
				break;
			case "medium":
				totalPrice += 2.09 * this.getQuantity();
				break;
			case "large":
				totalPrice += 2.49 * this.getQuantity();
				break;
			default:
				System.out.println("Something is wrong. Sorry, we do not serve this size.");
		}
		return totalPrice;
	}

	public String getBrand () {
		return this.brand;
	}

	public String toString() {
		return String.format("Pop of %s order of %d %s-sized pop worth %.2f", this.getBrand(), this.getQuantity(), this.getSize(), this.totalPrice());
	}
	
}
