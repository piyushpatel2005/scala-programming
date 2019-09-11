public abstract class SizableOrder extends Order {
    private String size;

    public SizableOrder (int quantity, String size){
        super(quantity);
        this.size = size;
    }

    public String getSize() {
        return this.size;
    }

}
