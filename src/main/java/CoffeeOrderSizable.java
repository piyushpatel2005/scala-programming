public class CoffeeOrderSizable extends SizableOrder {

	public CoffeeOrderSizable(int quantity, String size) {
		super(quantity, size);
	}
	
	@Override
	public double totalPrice() {
		double totalPrice = 0.0;
		switch(this.getSize()) {
			case "small":
				totalPrice += 1.39 * this.getQuantity();
				break;
			case "medium":
				totalPrice += 1.69 * this.getQuantity();
				break;
			case "large":
				totalPrice += 1.99 * this.getQuantity();
				break;
			default:
				System.out.println("Something is wrong. Size is not allowed.");
		}
		return totalPrice;
	}

	public String toString() {
//		return "Coffee order of " + this.getQuantity() + " " + this.getSize() + "-sized coffee worth " + this.totalPrice();
		return String.format("Coffee order of %d %s-sized coffee worth %.2f", this.getQuantity(), this.getSize(), this.totalPrice());
	}
	
}
