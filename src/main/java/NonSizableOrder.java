
public abstract class NonSizableOrder extends Order {
    private double price;

    public NonSizableOrder (int quantity, double price){
        super(quantity);
        this.setPrice(price);
    }

    public void setPrice(double price) {
        if (price > 0.0) {
            this.price = price;
            return;
        }
        System.out.println("Price must be greater than 0.0");
    }

    public double getPrice() {
        return this.price;
    }
}