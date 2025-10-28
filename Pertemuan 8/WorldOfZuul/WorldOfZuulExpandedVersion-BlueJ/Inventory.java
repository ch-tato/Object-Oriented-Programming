
/**
 * Write a description of class Inventory here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.*;
public class Inventory
{
    private List<Item> items = new ArrayList<>();
    public void add(Item it) { items.add(it); }
    public boolean has(String name) {
        return items.stream().anyMatch(i -> i.getName().equalsIgnoreCase(name));
    }
    public Item remove(String name) {
        for (Item i : items)
            if (i.getName().equalsIgnoreCase(name)) { items.remove(i); return i; }
        return null;
    }
    public void show() {
        if (items.isEmpty()) { System.out.println("Inventory empty."); return; }
        System.out.println("You carry:");
        items.forEach(i -> System.out.println(" - " + i.getName() + ": " + i.getDesc()));
    }
}