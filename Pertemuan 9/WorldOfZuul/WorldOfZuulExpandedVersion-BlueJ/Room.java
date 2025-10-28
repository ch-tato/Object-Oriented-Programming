
/**
 * Write a description of class Room here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.*;
public class Room
{
    protected String description;
    private Map<String,Room> exits = new HashMap<>();
    private List<Item> items = new ArrayList<>();
    private NPC npc;

    public Room(String desc) { this.description = desc; }
    public void setExit(String dir, Room r) { exits.put(dir, r); }
    public Room getExit(String dir) { return exits.get(dir); }
    public void printExits() {
        System.out.print("Exits:");
        exits.keySet().forEach(d -> System.out.print(" " + d));
        System.out.println();
    }
    public String getDesc() { return description; }
    public void addItem(Item it) { items.add(it); }
    public void setNPC(NPC n) { this.npc = n; }
    public NPC getNPC() { return npc; }

    public Item takeItem(String name) {
        for (Item i : items)
            if (i.getName().equalsIgnoreCase(name)) { items.remove(i); return i; }
        return null;
    }
    public void showItems() {
        if (items.isEmpty()) return;
        System.out.print("Items here: ");
        items.forEach(i -> System.out.print(i.getName() + " "));
        System.out.println();
    }
}