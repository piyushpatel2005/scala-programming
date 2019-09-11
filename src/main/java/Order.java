import java.util.Objects;

public abstract class Order implements Comparable<Object> {
	private int quantity;

	// made public because subclasses will call this constructor
	public Order(int quantity) {
		this.setQuantity(quantity);
	}

	// This order is not specific type of order so should not be capable of instantiation.
	public abstract double totalPrice ();

	public int getQuantity() {
		return this.quantity;
	}

	// use this one to ensure quantity is greater than 0
	private void setQuantity(int quantity) {
		if (quantity > 0) {
			this.quantity = quantity;
		} else {
			System.out.println("This is invalid quantity. Quantity must be greater than 0");
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Order order = (Order) o;
		return this.totalPrice() == order.totalPrice();
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.totalPrice());
	}

	public int compareTo(Object o) {
		Order order = (Order) o;
		return (int)((this.totalPrice() - order.totalPrice()) * 100); // had to multiply by 100 because 4.91 and 5.07 becomes 5 after casting and they don't appear in correct sorted order
	}
}
