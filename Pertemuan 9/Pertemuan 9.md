###### Selasa, 21 Oktober 2025
### Pertemuan 9
---
<h1><b>World of Zuul</b></h1>

---

## Daftar Isi

0. [Pendahuluan](#0-pendahuluan)
1. [Source Code](#1-source-code)
2. [Penjelasan Mekanisme Kode](#2-penjelasan-mekanisme-kode)
3. [Contoh Output](#3-contoh-output)
4. [World of Zuul Expanded Version](#4-world-of-zuul-expanded-version)
4. [Kesimpulan](#kesimpulan)

---

## 0. Pendahuluan
*World of Zuul* adalah permainan petualangan berbasis teks (text-based adventure) yang dikembangkan oleh Michael Kolling dan David J. Barnes sebagai contoh penerapan pemrograman berorientasi objek.  
Pemain berperan sebagai eksplorator yang berjalan antar ruangan, memberikan perintah sederhana melalui keyboard, dan berinteraksi dengan lingkungan untuk menyelesaikan tujuan tertentu.  
*World of Zuul* versi aslinya (yang ada di *slideshow*) sangat minimal, yaitu hanya berpindah ruangan, meminta bantuan, dan keluar.  
Dokumen ini menjelaskan *Expanded Edition* yang telah ditambahkan berbagai fitur baru agar permainan menjadi lebih kompleks.

---

## 1. Source Code
```java
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
```

---

## 2. Penjelasan Mekanisme Kode
### 2.1 Arsitektur Kelas
| Kelas | Tugas Utama |
|-------|-------------|
| **Command** | Menyimpan perintah pemain (kata perintah + argumen kedua). |
| **CommandWords** | Menyimpan daftar perintah valid serta validasi input. |
| **Parser** | Membaca input dari keyboard, memecah menjadi dua kata, dan mengembalikan objek `Command`. |
| **Item** | Representasi barang: nama dan deskripsi. |
| **Inventory** | Mengelola daftar barang yang dimiliki pemain (tambah, hapus, cek keberadaan). |
| **NPC** | Menyimpan nama dan kalimat dialog. |
| **Room** | Representasi ruangan: deskripsi, daftar exit, daftar barang, dan NPC. |
| **DarkRoom** | Sub-kelas `Room` yang menandakan ruangan gelap; memerlukan senter untuk memasuki/melihat isinya. |
| **Game** | *Controller* utama: inisialisasi dunia, loop permainan, pemrosesan perintah, dan pengecekan kemenangan. |

### 2.2 Alur Permainan
1. Objek `Game` dibuat → `createWorld()` dieksekusi.  
2. `printWelcome()` menampilkan pengantar dan kondisi ruangan pertama.  
3. **Loop utama**:  
   a. `Parser.getCommand()` membaca input.  
   b. `processCommand()` mengeksekusi perintah.  
   c. Jika `finished == true`, loop berhenti.  
4. **Kemenangan**: jika pemain kembali ke `outside` **dan** memiliki `artifact`, pesan kemenangan ditampilkan dan permainan berakhir.

### 2.3 Perintah yang Dikenali
| Perintah | Contoh | Efek |
|----------|--------|------|
| `go <arah>` | `go down` | Pindah ruangan jika exit tersedia. |
| `take <item>` | `take flashlight` | Pindahkan barang dari ruangan ke inventory. |
| `use <item>` | `use flashlight` | Aktifkan senter (agar bisa masuk `DarkRoom`). |
| `talk` | `talk` | Tampilkan dialog NPC di ruangan saat ini. |
| `inventory` | `inventory` | Tampilkan daftar barang yang dibawa. |
| `help` | `help` | Tampilkan daftar perintah. |
| `quit` | `quit` | Keluar dari permainan. |

### 2.4 Mekanisme Ruangan Gelap
- `DarkRoom` adalah `Room` dengan flag `isDark() = true`.  
- Saat pemain mencoba masuk, dicek apakah `inventory.has("flashlight")`.  
  - Jika **tidak**, masuk ditolak dan pesan peringatan muncul.  
  - Jika **ya**, pemain masuk seperti biasa.  
- Setelah di dalam, pemain bisa `take artifact` dan `go up` untuk keluar.

### 2.5 Kondisi Menang
Dievaluasi setelah setiap perintah `go`:
```java
if (current.getDesc().contains("outside") && inv.has("artifact"))
    -> finished = true; pesan kemenangan ditampilkan.
```

---

## 3. Contoh Output
Cara pakai (CLI):
```bash
javac Game.java
java Game
```
Berikut adalah contoh nyata saat memainkan *game*-nya:
```
Welcome to Zuul Expanded!
Find the artifact and return outside to win.
Type 'help' for commands.

You are outside the main entrance
Items here: flashlight 
Exits: east down
> go east
You are in the main hall
Exits: north west
> help
Your commands are:
go quit help take use talk inventory 
> go north
You are at the exit gate - freedom is near!
Exits: south
> go south
You are in the main hall
Exits: north west
> go west
You are outside the main entrance
Items here: flashlight 
Exits: east down
> go down
It's pitch black. You need a flashlight to enter.
> take flashlight
Taken: flashlight
> go down
You are in a dark basement - you can't see a thing without light!
Items here: artifact
Exits: up
> help
Your commands are:
go quit help take use talk inventory
> talk
Old Guardian says: "The artifact is cursed... only light reveals the path."
> use flashlight
You turn on the flashlight - the way is clear!
> take artifact
Taken: artifact
> inventory
You carry:
 - flashlight: A battery-powered flashlight.
 - artifact: An ancient glowing artifact - your prize!
> go up
You are outside the main entrance
Exits: east down

*** You escaped with the artifact - YOU WIN! ***
> quit
Thank you for playing. Good bye.
```

---

## 4. World of Zuul Expanded Version
Perbedaan utama dari World of Zuul yang saya kembangkan sendiri dibanding versi asli (bedarasarkan materi di *slideshow*):

| Fitur | Versi Dasar | *Expanded Edition* |
|-------|-------------|------------------|
| Perintah | `go`, `help`, `quit` | Tambahan: `take`, `use`, `talk`, `inventory` |
| Barang & Inventaris | Tidak ada | Ada |
| NPC & Dialog | Tidak ada | Ada |
| Ruangan khusus (gelap) | Tidak ada | `DarkRoom` memerlukan senter |
| Tujuan permainan | Tidak jelas | Temukan `artifact` dan kembali ke `outside` |
| Kondisi menang | Tidak ada | Ada |

---

## 5. Kesimpulan
*World of Zuul Expanded Edition* menunjukkan bagaimana pendekatan berorientasi objek memudahkan penambahan fitur baru tanpa mengubah inti arsitektur yang sudah ada.  
Dengan konsep kelas turunan (`DarkRoom`), enkapsulasi (`Inventory`, `Item`), dan polymorphism, kode tetap modular dan mudah dikembangkan lebih lanjut (misalnya penambahan musuh, sistem poin, atau penyimpanan data).

---
###### Ditulis oleh **Muhammad Quthbi Danish Abqori - 5025241036**
