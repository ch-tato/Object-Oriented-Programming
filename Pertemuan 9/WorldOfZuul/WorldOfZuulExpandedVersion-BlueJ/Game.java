
/**
 * Write a description of class Game here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.*;
public class Game
{
    private Parser parser = new Parser();
    private CommandWords cmds = new CommandWords();
    private Room current;
    private Inventory inv = new Inventory();
    private boolean finished = false;

    public static void main(String[] args) { new Game().play(); }

    public Game() { createWorld(); }

    private void createWorld() {
        Room outside = new Room("outside the main entrance");
        DarkRoom basement = new DarkRoom("in a dark basement - you can't see a thing without light!");
        Room hall     = new Room("in the main hall");
        Room exitGate = new Room("at the exit gate - freedom is near!");

        outside.setExit("east", hall);
        outside.setExit("down", basement);
        hall.setExit("west", outside);
        hall.setExit("north", exitGate);
        exitGate.setExit("south", hall);
        basement.setExit("up", outside);

        outside.addItem(new Item("flashlight", "A battery-powered flashlight."));
        basement.addItem(new Item("artifact", "An ancient glowing artifact - your prize!"));
        basement.setNPC(new NPC("Old Guardian",
                                "The artifact is cursed... only light reveals the path."));

        current = outside;
    }

    public void play() {
        printWelcome();
        while (!finished) {
            Command c = parser.getCommand();
            finished = processCommand(c);
        }
        System.out.println("Thank you for playing. Good bye.");
    }

    private void printWelcome() {
        System.out.println("\nWelcome to Zuul Expanded!");
        System.out.println("Find the artifact and return outside to win.");
        System.out.println("Type 'help' for commands.\n");
        lookAndCheck();
    }

    private boolean processCommand(Command c) {
        if (c.isUnknown()) { System.out.println("I don't understand…"); return false; }
        String word = c.getCommandWord();
        switch (word) {
            case "help": cmds.showAll(); break;
            case "go":   goRoom(c); break;
            case "take": take(c); break;
            case "use":  use(c); break;
            case "talk": talk(); break;
            case "inventory": inv.show(); break;
            case "quit": return quit(c);
        }
        return false;
    }

    private void goRoom(Command c) {
        if (!c.hasSecondWord()) { System.out.println("Go where?"); return; }
        String dir = c.getSecondWord();
        Room next = current.getExit(dir);
        if (next == null) { System.out.println("No exit that way."); return; }

        if (next instanceof DarkRoom && !inv.has("flashlight")) {
            System.out.println("It's pitch black. You need a flashlight to enter.");
            return;
        }
        current = next;
        lookAndCheck();
    }

    private void look() {
        System.out.println("You are " + current.getDesc());
        current.showItems();
        current.printExits();
    }

    private void take(Command c) {
        if (!c.hasSecondWord()) { System.out.println("Take what?"); return; }
        String name = c.getSecondWord();
        Item it = current.takeItem(name);
        if (it == null) System.out.println("No such item here.");
        else { inv.add(it); System.out.println("Taken: " + name); }
    }

    private void use(Command c) {
        if (!c.hasSecondWord()) { System.out.println("Use what?"); return; }
        String name = c.getSecondWord();
        if (!inv.has(name)) { System.out.println("You don't have that."); return; }
        if (name.equals("flashlight")) {
            System.out.println("You turn on the flashlight - the way is clear!");
        } else {
            System.out.println("Nothing happens.");
        }
    }

    private void talk() {
        NPC n = current.getNPC();
        if (n == null) System.out.println("No one here to talk to.");
        else n.talk();
    }

    private boolean quit(Command c) {
        if (c.hasSecondWord()) { System.out.println("Quit what?"); return false; }
        return true;
    }

    private void checkWin() {
        if (current.getDesc().contains("outside") && inv.has("artifact")) {
            System.out.println("\n*** You escaped with the artifact - YOU WIN! ***");
            finished = true;
        }
    }

    private void lookAndCheck() { look(); checkWin(); }
}