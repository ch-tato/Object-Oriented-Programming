###### Selasa, 16 September 2025
### Pertemuan 4
---
<h1><b>Digital Clock</b></h1>

---
# Daftar Isi
- [0. Pendahuluan](#0-pendahuluan)
- [1. clock.java](#1-clockjava)
- [2. ClockGUI.java](#2-clockguijava)
- [3. Kesimpulan](#3-kesimpulan)

# 0. Pendahuluan
Dalam Object-Oriented Programming (OOP), terdapat dua konsep penting yaitu **modularization** dan **abstraction**.

- **Modularization** adalah proses membagi program menjadi bagian-bagian kecil (modul) yang memiliki tanggung jawab spesifik. Hal ini memudahkan pengelolaan, perawatan, dan pengembangan program. Dalam OOP, modularization biasanya dilakukan dengan membagi kode ke dalam class atau package yang berbeda.
- **Abstraction** adalah cara menyembunyikan detail implementasi dari pengguna, hanya menampilkan fungsionalitas penting. Dalam OOP, abstraction dapat diwujudkan dengan menggunakan class, interface, atau method yang mendefinisikan apa yang dapat dilakukan tanpa harus menunjukkan bagaimana caranya dilakukan.

Penggunaan modularization dan abstraction pada OOP membuat kode lebih terstruktur, mudah dipahami, serta fleksibel untuk dikembangkan.

# 1. clock.java

## A. Source code lengkap

```java
import java.util.Random;
public class clock {
    public class numberDisplay{
        private int curr;
        private int max;

        public numberDisplay(int maxValue){
            curr = 0;
            max = maxValue;
        }

        public void setValue(int value){
            if(value >= 0 && value < max) curr = value;
        }

        public int getValue(){
            return curr;
        }

        public String getDisplay(){
            if(curr < 10) return "0" + curr;
            else return "" + curr;
        }

        public void increment(){
            curr = (curr + 1) % max;
        }
    }

    public class clockDisplay{
        private numberDisplay second;
        private numberDisplay minute;
        private numberDisplay hour;
        private numberDisplay day;
        private numberDisplay month;
        private numberDisplay year;
        private String currTime;
        private String currDate;
        private String currInfo;

        public class temp{
            private int min, max;
            private int value;
            private Random random = new Random();

            public temp(){
                min = 28;
                max = 33;
                value = random.nextInt(max - min + 1) + min;
            }

            public int getValue(){
                value = random.nextInt(max - min + 1) + min;
                return value;   
            }
        }

        private temp tempValue;
        private int temperature;

        public clockDisplay(){
            second = new numberDisplay(60);
            minute = new numberDisplay(60);
            hour = new numberDisplay(24);
            month = new numberDisplay(12);
            if(month.getValue()%2 == 0)
                day = new numberDisplay(31);
            else{
                if(month.getValue() == 2)
                    day = new numberDisplay(28);
                else
                    day = new numberDisplay(30);
            }
            year = new numberDisplay(9999);
            tempValue = new temp();
            temperature = tempValue.getValue();
            setTimeStr();
        }

        private void setTimeStr(){
            currTime = hour.getDisplay() + ":" + minute.getDisplay() + ":" + second.getDisplay();
            currDate = day.getDisplay() + "/" + month.getDisplay() + "/" + year.getDisplay();
            currInfo = currTime + "\n" + currDate + "\n" + temperature + "°C";
        }

        public void setTime(int hh, int mm, int ss, int dd, int mo, int yy, int t){
            hour.setValue(hh);
            minute.setValue(mm);
            second.setValue(ss);
            day.setValue(dd);
            month.setValue(mo);
            year.setValue(yy);
            temperature = t;
            setTimeStr();
        }

        public String getTime(){
            return currInfo;
        }
        public void timeIncrement(){
            temperature = tempValue.getValue();
            second.increment();
            if(second.getValue() == 0){
                minute.increment();
                if(minute.getValue() == 0){
                    hour.increment();
                    if(hour.getValue() == 0){
                        day.increment();
                        if(day.getValue() == 0){
                            day.increment();
                            month.increment();
                            if(month.getValue() == 0){
                                month.increment();
                                year.increment();
                            }
                        }
                    }
                }
            }
            setTimeStr();
        }
    }

    public static void main(String[] arg){
        clock c = new clock();
        clockDisplay clock = c.new clockDisplay();
        clock.setTime(15, 18, 55, 16, 9, 2025, 30);

        
        while(true){
            clock.timeIncrement();
            System.out.println("tick...");
            System.out.println("===================");
            System.out.println("" + clock.getTime());
            System.out.println("===================");
            sleepMilis(1000);
        }
    }
    
    public static void sleepMilis(int milisecond){
        try{Thread.sleep(milisecond);}
        catch(Exception e){}
    }
}
```

---

## B. Apa yang dilakukan kode ini

Program [`clock.java`](source%20code/clock.java) ini adalah implementasi sederhana dari *digital clock* menggunakan nested inner classes di Java. Secara ringkas program:

- Mendefinisikan `numberDisplay` sebagai unit yang menyimpan dan mengatur sebuah nilai numerik yang memiliki batas maksimal (mis. detik 0..59).
- Mendefinisikan `clockDisplay` yang menggunakan beberapa `numberDisplay` untuk mewakili detik, menit, jam, hari, bulan, dan tahun. Juga menyertakan inner class `temp` untuk menghasilkan nilai temperatur acak.
- Di `main`, program membuat instance `clockDisplay`, meng-set waktu awal, lalu memasuki loop tak hingga yang setiap iterasi menambah waktu satu detik, mencetak string waktu/ tanggal/ suhu ke konsol, dan menunggu 1 detik.

Output yang terlihat di konsol akan berupa blok teks yang berisi waktu (jam:menit:detik), tanggal (dd/mm/yyyy), dan suhu (mis. `30°C`) yang diperbarui setiap detik.

---

## C. Penjelasan masing-masing class dan anggota

### class `numberDisplay`
Class ini berfungsi sebagai blok pembangun untuk angka yang berputar (wrap-around) — cocok untuk detik/menit/jam.

**Field**:

```java
private int curr; // nilai saat ini
private int max;  // batas atas (nilai valid: 0 .. max-1)
```

**Constructor**:

```java
public numberDisplay(int maxValue){
    curr = 0;
    max = maxValue;
}
```
- Menginisialisasi `curr` ke 0 dan menyimpan batas `max`.

**Method**:

```java
public void setValue(int value){
    if(value >= 0 && value < max) curr = value;
}
```
- Mengatur `curr` jika `value` berada dalam jangkauan.

```java
public int getValue(){
    return curr;
}
```
- Mengembalikan nilai numerik saat ini.

```java
public String getDisplay(){
    if(curr < 10) return "0" + curr;
    else return "" + curr;
}
```
- Mengembalikan representasi string dengan leading zero jika < 10.

```java
public void increment(){
    curr = (curr + 1) % max;
}
```
- Menambah nilai dan melakukan wrap-around ke 0 saat mencapai `max`.

---

### class `clockDisplay`
Class ini menggabungkan beberapa `numberDisplay` untuk membuat tampilan jam lengkap plus tanggal dan temperatur.

**Field inti**:

```java
private numberDisplay second;
private numberDisplay minute;
private numberDisplay hour;
private numberDisplay day;
private numberDisplay month;
private numberDisplay year;
private String currTime; // string untuk baris waktu (hh:mm:ss)
private String currDate; // string untuk baris tanggal (dd/mm/yyyy)
private String currInfo; // gabungan currTime + currDate + temperature
```

**Inner class `temp` (disingkat penjelasan di bawah)** menyediakan nilai temperatur acak tiap pemanggilan `getValue()`.

**Constructor `clockDisplay()`**

Potongan penting:

```java
second = new numberDisplay(60);
minute = new numberDisplay(60);
hour = new numberDisplay(24);
month = new numberDisplay(12);
if(month.getValue()%2 == 0)
    day = new numberDisplay(31);
else{
    if(month.getValue() == 2)
        day = new numberDisplay(28);
    else
        day = new numberDisplay(30);
}
year = new numberDisplay(9999);
tempValue = new temp();
temperature = tempValue.getValue();
setTimeStr();
```

- Menginisialisasi `numberDisplay` untuk detik, menit, jam, bulan, tahun.
- Untuk `day` ada logika menentukan jumlah hari berdasarkan `month.getValue()`.
- Mengambil nilai awal temperatur melalui `tempValue`.
- Memanggil `setTimeStr()` untuk membentuk `currInfo` awal.

**Method `setTimeStr()`** — membentuk string yang akan dicetak:

```java
private void setTimeStr(){
    currTime = hour.getDisplay() + ":" + minute.getDisplay() + ":" + second.getDisplay();
    currDate = day.getDisplay() + "/" + month.getDisplay() + "/" + year.getDisplay();
    currInfo = currTime + "\n" + currDate + "\n" + temperature + "°C";
}
```

**Method `setTime(...)`** — meng-set nilai eksplisit untuk semua unit waktu + suhu.

```java
public void setTime(int hh, int mm, int ss, int dd, int mo, int yy, int t){
    hour.setValue(hh);
    minute.setValue(mm);
    second.setValue(ss);
    day.setValue(dd);
    month.setValue(mo);
    year.setValue(yy);
    temperature = t;
    setTimeStr();
}
```

**Method `getTime()`** — mengembalikan `currInfo` (string multi-baris) yang siap dicetak.

```java
public String getTime(){
    return currInfo;
}
```

**Method `timeIncrement()`** — menambahkan waktu satu detik dan melakukan rollover ke menit/jam/hari/bulan/tahun sesuai kondisi. Juga memperbarui temperatur.

Potongan kode utama:

```java
public void timeIncrement(){
    temperature = tempValue.getValue();
    second.increment();
    if(second.getValue() == 0){
        minute.increment();
        if(minute.getValue() == 0){
            hour.increment();
            if(hour.getValue() == 0){
                day.increment();
                if(day.getValue() == 0){
                    day.increment();
                    month.increment();
                    if(month.getValue() == 0){
                        month.increment();
                        year.increment();
                    }
                }
            }
        }
    }
    setTimeStr();
}
```

Penjelasan alur:
- Setiap `timeIncrement()` memanggil `second.increment()`.
- Bila detik kembali ke 0, berarti menit di-increment.
- Bila menit kembali ke 0, jam di-increment.
- Bila jam kembali ke 0, hari di-increment.
- Ada pengecekan lanjutan: jika setelah increment hari menjadi 0, ada pemanggilan `day.increment()` lagi, diikuti `month.increment()`; jika month menjadi 0, month di-increment lagi dan `year.increment()` dipanggil.

> Catatan implementasi: logika `day`/`month`/`year` di sini tampak dibuat agar tanggal tidak pernah bernilai 0 — sehingga ada pemanggilan `day.increment()` dan `month.increment()` ganda ketika rollover. Namun cara ini rawan membingungkan karena `numberDisplay` menggunakan rentang 0..max-1, sedangkan manusia berharap tanggal 1..N dan bulan 1..12. Lebih lanjut di bagian *catatan & saran*.

---

### inner class `temp` di dalam `clockDisplay`

Class ini bertugas memberikan nilai temperatur acak dalam rentang yang ditentukan.

```java
public class temp{
    private int min, max;
    private int value;
    private Random random = new Random();

    public temp(){
        min = 28;
        max = 33;
        value = random.nextInt(max - min + 1) + min;
    }

    public int getValue(){
        value = random.nextInt(max - min + 1) + min;
        return value;   
    }
}
```

- `min` dan `max` default 28..33.
- `getValue()` mengacak ulang nilai setiap kali dipanggil dan mengembalikannya.
- Digunakan untuk memperbarui `temperature` setiap `timeIncrement()`.

---

### method `main` dan utilitas `sleepMilis`

`main` membuat instance `clock` lalu `clockDisplay`, melakukan `setTime(15, 18, 55, 16, 9, 2025, 30);` lalu masuk loop tak hingga:

```java
while(true){
    clock.timeIncrement();
    System.out.println("tick...");
    System.out.println("===================");
    System.out.println("" + clock.getTime());
    System.out.println("===================");
    sleepMilis(1000);
}
```

`timeIncrement()` dipanggil sebelum mencetak, jadi nilai detik yang dicetak sudah bertambah 1 dari yang di-set sebelumnya.

`sleepMilis` hanya wrapper kecil untuk `Thread.sleep` dengan `try/catch` yang mengabaikan exception:

```java
public static void sleepMilis(int milisecond){
    try{Thread.sleep(milisecond);}
    catch(Exception e){}
}
```

---

## D. Contoh output

Jika program di-run sebagaimana `main` menetapkan `clock.setTime(15, 18, 55, 16, 9, 2025, 30);`, maka setiap loop akan menambah detik dan mencetak blok seperti berikut. Contoh snapshot 3 iterasi berturut-turut (nilai temperatur acak di antara 28..33 — contoh di bawah hanya ilustrasi):
|Gambar 1. Contoh output `clock.java`|
|-|
![alt text](screenshots/Screenshot%202025-09-16%20212154.png)

```
tick...
===================
15:18:56
16/09/2025
31°C
===================

tick...
===================
15:18:57
16/09/2025
29°C
===================

tick...
===================
15:18:58
16/09/2025
30°C
===================
```

Perhatikan: detik meningkat dari 55 (yang di-set) menjadi 56 pada output pertama karena `timeIncrement()` dipanggil sebelum `getTime()`.


# 2. ClockGUI.java
## A. Source code lengkap

> **Kosongkan bagian ini — biar kamu yang menempelkan source code**

```java
import java.awt.*;
import javax.swing.*;
import java.util.Random;

public class ClockGUI extends JPanel {
    // === Inner Class NumberDisplay ===
    public class NumberDisplay {
        private int curr;
        private int max;

        public NumberDisplay(int maxValue) {
            curr = 0;
            max = maxValue;
        }

        public void setValue(int value) {
            if (value >= 0 && value < max) curr = value;
        }

        public int getValue() {
            return curr;
        }

        public String getDisplay() {
            if (curr < 10) return "0" + curr;
            else return "" + curr;
        }

        public void increment() {
            curr = (curr + 1) % max;
        }
    }

    // === Inner Class ClockDisplay ===
    public class ClockDisplay {
        private NumberDisplay second, minute, hour, day, month, year;
        private String currTime, currDate, currInfo;

        public class Temp {
            private int min = 28, max = 33;
            private int value;
            private Random random = new Random();

            public Temp() {
                value = random.nextInt(max - min + 1) + min;
            }

            public int getValue() {
                value = random.nextInt(max - min + 1) + min;
                return value;
            }
        }

        private Temp tempValue;
        private int temperature;

        public ClockDisplay() {
            second = new NumberDisplay(60);
            minute = new NumberDisplay(60);
            hour = new NumberDisplay(24);
            month = new NumberDisplay(12);
            day = new NumberDisplay(31);
            year = new NumberDisplay(9999);
            tempValue = new Temp();
            temperature = tempValue.getValue();
            setTimeStr();
        }

        private void setTimeStr() {
            currTime = hour.getDisplay() + ":" + minute.getDisplay() + ":" + second.getDisplay();
            currDate = day.getDisplay() + "/" + month.getDisplay() + "/" + year.getDisplay();
            currInfo = currTime + "\n" + currDate + "\n" + temperature + "°C";
        }

        public void setTime(int hh, int mm, int ss, int dd, int mo, int yy, int t) {
            hour.setValue(hh);
            minute.setValue(mm);
            second.setValue(ss);
            day.setValue(dd);
            month.setValue(mo);
            year.setValue(yy);
            temperature = t;
            setTimeStr();
        }

        public String getTime() {
            return currInfo;
        }

        public void timeIncrement() {
            temperature = tempValue.getValue();
            second.increment();
            if (second.getValue() == 0) {
                minute.increment();
                if (minute.getValue() == 0) {
                    hour.increment();
                    if (hour.getValue() == 0) {
                        day.increment();
                        if (day.getValue() == 0) {
                            month.increment();
                            if (month.getValue() == 0) {
                                year.increment();
                            }
                        }
                    }
                }
            }
            setTimeStr();
        }
    }

    // === GUI Part ===
    private ClockDisplay clock;

    public ClockGUI() {
        clock = new ClockDisplay();
        clock.setTime(15, 18, 55, 16, 9, 2025, 30);

        // Timer update setiap 1 detik
        Timer timer = new Timer(1000, e -> {
            clock.timeIncrement();
            repaint();
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.BLACK);

        g.setColor(Color.GREEN);
        g.setFont(new Font("Consolas", Font.BOLD, 40));
        g.drawString(clock.currTime, 50, 80);

        g.setFont(new Font("Consolas", Font.PLAIN, 30));
        g.drawString(clock.currDate, 50, 130);

        g.setFont(new Font("Consolas", Font.PLAIN, 25));
        g.drawString(clock.temperature + "°C", 50, 180);
    }

    // === Main Method ===
    public static void main(String[] args) {
        JFrame frame = new JFrame("Digital Clock");
        ClockGUI panel = new ClockGUI();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.add(panel);
        frame.setVisible(true);
    }
}

```

---

## B. Apa yang dilakukan kode ini (dibanding `clock.java`)

[`ClockGUI.java`](source%20code/ClockGUI.java) adalah versi yang memvisualisasikan `clock.java` menggunakan Swing GUI:

- **Fungsi utama tetap sama**: menyimpan unit waktu (`second`, `minute`, `hour`, `day`, `month`, `year`) dan melakukan increment setiap detik; juga menghasilkan nilai temperatur acak.
- **Perbedaan penting**:
  - Alih-alih mencetak ke konsol dalam loop tak-henti (`while(true)` + `Thread.sleep`) seperti `clock.java`, `ClockGUI` menggunakan `javax.swing.Timer` yang memicu update setiap 1000 ms dan memanggil `repaint()` untuk merender ulang GUI.
  - Output teks digambar pada panel Swing (`paintComponent`) dengan font, warna, dan layout yang sederhana — menjadi tampilan jam digital grafis.
  - Beberapa perubahan kecil pada inner classes: nama class menggunakan PascalCase (`NumberDisplay`, `ClockDisplay`, `Temp`) dan inisialisasi `day`/`month` dibuat lebih langsung (`day = new NumberDisplay(31); month = new NumberDisplay(12);`) sehingga logika jumlah hari per bulan di konstruktor lama dihapus.
  - `ClockGUI` meng-extend `JPanel` dan menampilkan `currTime`, `currDate`, dan `temperature` langsung di `paintComponent`.

Secara UX dan arsitektur, perubahan ini membuat program: non-blocking (tidak mengunci thread utama), responsive terhadap event Swing, dan lebih mudah untuk ditampilkan/ditangkap sebagai gambar daripada output console text.

---

## C. Penjelasan masing-masing class dan anggota yang bertambah / berbeda

> Fokus di sini hanya pada perubahan/penambahan dibanding `clock.java`.

### Inner class `NumberDisplay` (perbandingan)
Structurnya sama fungsinya dengan `numberDisplay` di `clock.java` — hanya penamaan yang disesuaikan ke gaya Java (PascalCase). Contoh kode terkait:

```java
public class NumberDisplay {
    private int curr;
    private int max;

    public NumberDisplay(int maxValue) { ... }
    public void setValue(int value) { ... }
    public int getValue() { ... }
    public String getDisplay() { ... }
    public void increment() { ... }
}
```

Perbedaan kecil:
- Nama class berubah (`numberDisplay` → `NumberDisplay`).
- Implementasi method sama — masih memakai wrap-around `curr = (curr+1)%max`.

---

### Inner class `ClockDisplay` (perbandingan & penambahan)
`ClockDisplay` masih menyimpan unit waktu dan string yang akan ditampilkan, tetapi beberapa perubahan/penyederhanaan terlihat:

- Deklarasi variabel sekarang:

```java
private NumberDisplay second, minute, hour, day, month, year;
private String currTime, currDate, currInfo;
```

- **Inisialisasi hari/bulan**: Di konstruktor `ClockDisplay()` pada `ClockGUI.java` hari dan bulan diinisialisasi langsung ke `new NumberDisplay(31)` dan `new NumberDisplay(12)` — berbeda dari `clock.java` yang mencoba memilih 30/31/28 berdasarkan bulan saat konstruktor.

```java
second = new NumberDisplay(60);
minute = new NumberDisplay(60);
hour   = new NumberDisplay(24);
month  = new NumberDisplay(12);
day    = new NumberDisplay(31);
year   = new NumberDisplay(9999);
```

- **Inner class `Temp`**: nama objek temp diubah ke `Temp` dan field `min`/`max` diinisialisasi inline (lebih ringkas). Fungsi `getValue()` tetap sama—mengembalikan temperatur acak tiap pemanggilan.

```java
public class Temp {
    private int min = 28, max = 33;
    private int value;
    private Random random = new Random();

    public Temp() { value = random.nextInt(max - min + 1) + min; }
    public int getValue() { ... }
}
```

- **Method `timeIncrement()`**: perilaku rollover di-`simplify` dibanding `clock.java`. Perhatikan perbedaan berikut (potongan penting):

```java
public void timeIncrement() {
    temperature = tempValue.getValue();
    second.increment();
    if (second.getValue() == 0) {
        minute.increment();
        if (minute.getValue() == 0) {
            hour.increment();
            if (hour.getValue() == 0) {
                day.increment();
                if (day.getValue() == 0) {
                    month.increment();
                    if (month.getValue() == 0) {
                        year.increment();
                    }
                }
            }
        }
    }
    setTimeStr();
}
```

Perbedaan utama:
- Di `clock.java` terdapat beberapa `day.increment()`/`month.increment()` ekstra untuk mencegah tampilnya 0; di `ClockGUI.java` struktur kondisional dibuat lebih sederhana (tidak memanggil `day.increment()` dua kali). Hasilnya perilaku rollover sedikit berbeda — kemungkinan lebih natural (tetapi tetap bergantung pada representasi 0-based vs 1-based).

- `setTime(...)` dan `setTimeStr()` tetap ada dan berfungsi serupa: membentuk `currTime`, `currDate`, dan `currInfo`.

### Aksesibilitas field dari outer class
Meskipun `currTime`, `currDate`, dan `temperature` dideklarasikan `private` di `ClockDisplay`, `paintComponent` di outer class (`ClockGUI`) mengakses `clock.currTime` dan `clock.currDate` secara langsung. Ini **legal** di Java karena inner class dan outer class berada di dalam satu top-level class — mereka saling boleh mengakses member `private` masing-masing.

Contoh akses di `paintComponent`:

```java
g.drawString(clock.currTime, 50, 80);
...
g.drawString(clock.currDate, 50, 130);
...
g.drawString(clock.temperature + "°C", 50, 180);
```

---

### Bagian GUI: `ClockGUI` (extends `JPanel`) dan `paintComponent`

Bagian baru yang tidak ada di `clock.java` adalah seluruh rendering GUI.

- **Konstruktor `ClockGUI()`**
  - Membuat `ClockDisplay` dan memanggil `setTime(...)` untuk meng-set waktu awal.
  - Membuat `javax.swing.Timer` yang setiap 1000 ms memanggil `clock.timeIncrement()` dan `repaint()`.

```java
public ClockGUI() {
    clock = new ClockDisplay();
    clock.setTime(15, 18, 55, 16, 9, 2025, 30);

    Timer timer = new Timer(1000, e -> {
        clock.timeIncrement();
        repaint();
    });
    timer.start();
}
```

- **`paintComponent(Graphics g)`**
  - Melakukan `super.paintComponent(g)` lalu menggambar latar hitam, teks hijau untuk waktu, dan font yang berbeda untuk tanggal & temperatur.
  - Menggunakan `g.setFont(...)` dan `g.drawString(...)`.

```java
@Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    setBackground(Color.BLACK);

    g.setColor(Color.GREEN);
    g.setFont(new Font("Consolas", Font.BOLD, 40));
    g.drawString(clock.currTime, 50, 80);

    g.setFont(new Font("Consolas", Font.PLAIN, 30));
    g.drawString(clock.currDate, 50, 130);

    g.setFont(new Font("Consolas", Font.PLAIN, 25));
    g.drawString(clock.temperature + "°C", 50, 180);
}
```

---

### Timer Swing vs loop `while` di `clock.java`

Perbandingan:
- `clock.java`: `while(true)` + `sleepMilis(1000)` — **memblokir** thread yang menjalankan loop. Jika dijalankan di UI thread, akan men-freeze UI. Juga tidak cocok untuk GUI.
- `ClockGUI.java`: `new Timer(1000, e -> { ... })` — tidak memblokir, menjalankan aksi periodik di EDT. Ini adalah pendekatan yang benar untuk Swing.

---

### Main method (pembuatan JFrame)

`ClockGUI.java` membuat `JFrame` di `main` dan menambahkan panel:

```java
public static void main(String[] args) {
    JFrame frame = new JFrame("Digital Clock");
    ClockGUI panel = new ClockGUI();

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(300, 300);
    frame.add(panel);
    frame.setVisible(true);
}
```

---

## D. Contoh output

|Gambar 2. Contoh output `ClockGUI.java`|
|-|
![alt text](screenshots/Screenshot%202025-09-16%20211743.png)

# 3. Kesimpulan
- **clock.java** menunjukkan penerapan konsep OOP dengan modularization dan abstraction melalui pemisahan class dan method untuk menangani waktu, tanggal, dan suhu. Program ini berbasis console.  
- **ClockGUI.java** mengembangkan program menjadi berbasis GUI dengan memanfaatkan Swing dan AWT, sehingga tampilan lebih menarik dan realistis seperti jam digital pada umumnya.  
- Penggunaan modularization memudahkan transisi dari console ke GUI tanpa harus mengubah logika inti, cukup menambahkan lapisan tampilan baru.
---
###### Ditulis oleh **Muhammad Quthbi Danish Abqori - 5025241036**