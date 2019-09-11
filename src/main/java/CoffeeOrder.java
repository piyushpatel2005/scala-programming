public class CoffeeOrder extends Order {
	private String size;
//	private int quantity;
	
	public CoffeeOrder(int quantity, String size) {
		super(quantity);
		this.size = size;
	}
	
	@Override
	public double totalPrice() {
		double totalPrice = 0.0;
		switch(size) {
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

	private String getSize() {
		return this.size;
	}


	public String toString() {
//		return "Coffee order of " + this.getQuantity() + " " + this.getSize() + "-sized coffee worth " + this.totalPrice();
		return String.format("Coffee order of %d %s-sized coffee worth %.2f", this.getQuantity(), this.getSize(), this.totalPrice());
	}
	
}
