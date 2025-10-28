
/**
 * Write a description of class NPC here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class NPC
{
    private String name, dialog;
    public NPC(String name, String dialog) {
        this.name = name; this.dialog = dialog;
    }
    public void talk() { System.out.println(name + " says: \"" + dialog + "\""); }
}