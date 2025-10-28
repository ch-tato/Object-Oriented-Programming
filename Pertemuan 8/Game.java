// expanded version

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

class Command {
    private String commandWord;
    private String secondWord;
    public Command(String first, String second) {
        commandWord = first;
        secondWord  = second;
    }
    public String getCommandWord() { return commandWord; }
    public String getSecondWord()  { return secondWord; }
    public boolean isUnknown()     { return commandWord == null; }
    public boolean hasSecondWord() { return secondWord != null; }
}

class CommandWords {
    private static final String[] valid = {
        "go","quit","help","take","use","talk","inventory"
    };
    public boolean isCommand(String s) {
        return Arrays.asList(valid).contains(s);
    }
    public void showAll() {
        System.out.println("Your commands are:");
        for (String c : valid) System.out.print(c + " ");
        System.out.println();
    }
}

class Item {
    private String name, desc;
    public Item(String name, String desc) {
        this.name = name; this.desc = desc;
    }
    public String getName() { return name; }
    public String getDesc() { return desc; }
}

class Inventory {
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

class NPC {
    private String name, dialog;
    public NPC(String name, String dialog) {
        this.name = name; this.dialog = dialog;
    }
    public void talk() { System.out.println(name + " says: \"" + dialog + "\""); }
}

class Room {
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

class DarkRoom extends Room {
    public DarkRoom(String desc) { super(desc); }
    public boolean isDark() { return true; }
}

class Parser {
    private CommandWords cmds = new CommandWords();
    private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    public Command getCommand() {
        String line = "";
        try { System.out.print("> "); line = in.readLine(); }
        catch (Exception e) { System.out.println("Input error."); }
        StringTokenizer tk = new StringTokenizer(line);
        String w1 = null, w2 = null;
        if (tk.hasMoreTokens()) w1 = tk.nextToken();
        if (tk.hasMoreTokens()) w2 = tk.nextToken();
        if (w1 != null && cmds.isCommand(w1)) return new Command(w1, w2);
        return new Command(null, w2);
    }
}

public class Game {
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