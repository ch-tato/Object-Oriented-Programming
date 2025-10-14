
/**
 * Write a description of class Snack here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Snack
{
    private String name;
    private int price;
    private int stock;

    public Snack(String name, int price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Nama: " + name + "\t| Harga: " + price + "\t| Stok: " + stock;
    }
}