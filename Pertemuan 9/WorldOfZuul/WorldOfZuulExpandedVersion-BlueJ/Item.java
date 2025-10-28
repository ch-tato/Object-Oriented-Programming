
/**
 * Write a description of class Item here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Item
{
    private String name, desc;
    public Item(String name, String desc) {
        this.name = name; this.desc = desc;
    }
    public String getName() { return name; }
    public String getDesc() { return desc; }
}