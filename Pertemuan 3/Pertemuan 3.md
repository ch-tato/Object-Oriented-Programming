###### Selasa, 9 September 2025
## Pertemuan 3
# TicketMachine.java
---
### Daftar Isi
- [0. Pendahuluan](#0-pendahuluan)
- [1. Deskripsi](#1-deskripsi)
- [2. Source Code](#2-source-code)
- [3. Penjelasan Variabel](#3-penjelasan-variabel)
- [4. Tujuan Setiap Method](#4-tujuan-setiap-method)
- [5. Cara Kerja di BlueJ](#5-cara-kerja-di-bluej)
- [6. Kesimpulan](#6-kesimpulan)
---
### 0. Pendahuluan
BlueJ adalah sebuah **Integrated Development Environment (IDE)** sederhana untuk bahasa pemrograman Java. BlueJ dirancang khusus untuk tujuan pendidikan, sehingga cocok digunakan oleh pemula untuk belajar konsep dasar pemrograman berorientasi objek (OOP). BlueJ memiliki tampilan yang sederhana dengan fitur visualisasi class dan objek, sehingga mempermudah mahasiswa dalam memahami hubungan antar class, atribut, serta metode.

### 1. Deskripsi
Proyek **TicketMachine** ini mensimulasikan mesin penjual tiket sederhana. Mesin ini memiliki harga tiket tetap. Pengguna dapat memasukkan uang (insert money), lalu mencetak tiket (print ticket) apabila saldo mencukupi. Jika uang yang dimasukkan lebih dari harga tiket, maka mesin akan memberikan kembalian (change). Program ini juga mendukung pengembalian uang (refund) jika pengguna membatalkan transaksi.

### 2. Source Code
Berikut adalah [`TimeMachine.java`](TicketMachine/TicketMachine.java)
```java
/**
 * Ticket Machine is a machine to take a ticket.
 *
 * @author Muhammad Quthbi Danish Abqori
 * @version 1.0
 */
public class TicketMachine
{
    // instance variables
    private int price;      // price of one ticket
    private int balance;    // money inserted so far
    private int total;      // total money collected by machine

    /**
     * Constructor for objects of class TicketMachine
     */
    public TicketMachine(int price){
        // initialise instance variables
        this.price = price;
        this.balance = 0; // 
        this.total = 0;
    } 

    // To return the price of the ticket
    public int getPrice(){
        return price;
    }

    // To return the user's balance
    public int getBalance(){
        return balance;
    }

    // To user inserts an amount of money
    public void insertMoney(int amount){
        if(amount > 0){
            balance += amount;
            System.out.println("You inserted " + amount + "Rupiah.");
        }
        else{
            System.out.println("Invalid number. Please insert a positive amount!");
        }
    }

    // To print the ticket (if the balance is enough)
    public void printTicket(){
        if(balance >= price){
            System.out.println("###############################");
            System.out.println("#      Ticket issued          #");
            System.out.println("#      Price: " + price + " Rupiah #");
            System.out.println("###############################");

            total += price;
            balance -= price;

            if (balance > 0){
                System.out.println("Don't forget your change: " + balance);
                balance = 0; // return change
            }
        }
        else{
            System.out.println("You must insert at least " + (price - balance) + " more Rupiah.");
        }
    }

    public int getTotal(){
        return total;
    }

    // To refund the money if the user cancels
    public void refund(){
        if(balance > 0){
            System.out.println("You refunded " + balance + "Rupiah");
            balance = 0;
        }
        else{
            System.out.println("No balance to refund.");
        }
    }
}
```
### 3. Penjelasan Variabel
Dalam class `TicketMachine`, terdapat beberapa variabel:
```java
private int price;      // price of one ticket
private int balance;    // money inserted so far
private int total;      // total money collected by machine
```

- **`price`** : Menyimpan harga satu tiket. (Tipe: `int`)  
- **`balance`** : Menyimpan jumlah uang yang sudah dimasukkan oleh pengguna. (Tipe: `int`)  
- **`total`** : Menyimpan total uang yang sudah dikumpulkan oleh mesin. (Tipe: `int`)  

### 4. Tujuan Setiap Method
Berikut penjelasan fungsi-fungsi yang ada di dalam program:

1. **Constructor: `TicketMachine(int ticketPrice)`**
    ```java
    public TicketMachine(int price){
        // initialise instance variables
        this.price = price;
        this.balance = 0; // 
        this.total = 0;
    }
    ```
    Digunakan untuk membuat objek mesin tiket baru dengan harga tiket tertentu.  

2. **Getter: `getTicketPrice()`**
    ```java
    public int getPrice(){
        return price;
    }
    ```
   Mengembalikan nilai harga tiket.  

3. **Getter: `getBalance()`**  
    ```java
    public int getBalance(){
        return balance;
    }
    ```
   Mengembalikan saldo yang sudah dimasukkan pengguna.  

4. **Setter: `insertMoney(int amount)`**  
    ```java
    public void insertMoney(int amount){
        if(amount > 0){
            balance += amount;
            System.out.println("You inserted " + amount + "Rupiah.");
        }
        else{
            System.out.println("Invalid number. Please insert a positive amount!");
        }
    }
    ```
   Menginput (menambahkan) uang ke saldo. Hanya menerima input positif.  

5. **Setter: `printTicket()`**  
    ```java
    public void printTicket(){
        if(balance >= price){
            System.out.println("###############################");
            System.out.println("#      Ticket issued          #");
            System.out.println("#      Price: " + price + " Rupiah #");
            System.out.println("###############################");

            total += price;
            balance -= price;

            if (balance > 0){
                System.out.println("Don't forget your change: " + balance);
                balance = 0; // return change
            }
        }
        else{
            System.out.println("You must insert at least " + (price - balance) + " more Rupiah.");
        }
    }
    ```
   - Mencetak tiket jika saldo cukup.  
   - Mengurangi saldo sesuai harga tiket.  
   - Menambahkan pendapatan mesin ke variabel `total`.  
   - Mengembalikan kembalian jika saldo lebih besar dari harga tiket.  

6. **Getter: `getTotal()`**  
    ```java
    public int getTotal(){
        return total;
    }
    ```
   Mengembalikan total pendapatan mesin dari tiket yang berhasil terjual.  

7. **Aksi: `refund()`**  
    ```java
    public void refund(){
        if(balance > 0){
            System.out.println("You refunded " + balance + "Rupiah");
            balance = 0;
        }
        else{
            System.out.println("No balance to refund.");
        }
    }
    ```
   Mengembalikan saldo yang sudah dimasukkan pengguna jika transaksi dibatalkan.  

### 5. Cara Kerja di BlueJ
1. Program diawali dengan pembuatan objek `TicketMachine` dengan harga tiket tertentu. Input harga tiket `price` sebesar `25000`

    | **Gambar 1.** Membuat objek baru| **Gambar 2.** Menginput `price`|
    |-------------------------------|-|
    |![alt text](<Screenshot 2025-09-09 161538.png>)|![alt text](<Screenshot 2025-09-09 161559.png>)|

    Setelah objek berhasil dibuat, objek akan terlihat di bagian pojok kiri bawah jendela. Klik kanan untuk melihat daftar method yang tersedia.

    | **Gambar 3.** Objek baru telah dibuat| **Gambar 4.** Daftar method pada objek|
    |-|-|
    |![alt text](<Screenshot 2025-09-09 161617.png>)|![alt text](<Screenshot 2025-09-09 161756.png>)|
    
2. Pengguna memasukkan uang menggunakan `insertMoney()`, misalnya sebesar `20000`.

    | **Gambar 5.** Menginput uang sebesar `20000`| **Gambar 6.** Output dari `insertMoney()`|
    |-|-|
    |![alt text](<Screenshot 2025-09-09 161820.png>)|![alt text](<Screenshot 2025-09-09 161831.png>)|


3. Jika saldo cukup, pengguna dapat memanggil `printTicket()` untuk mencetak tiket. Karena tadi pengguna baru menginput uangnya sebesar `20000`, maka outputnya akan terlihat seperti pada Gambar 7.
    | **Gambar 7.** Contoh ouput `printTicket()` yang gagal|
    |-------------------------------|
    ![alt text](<Screenshot 2025-09-09 162042.png>)

    Oleh karena itu, pengguna harus mengisi `balance`. Setelah itu, pengguna baru bisa memanggil `printTicket()` untuk mencetak tiket.
    | **Gambar 8.** Contoh ouput `printTicket()` yang berhasil|
    |-------------------------------|
    ![alt text](<Screenshot 2025-09-09 165100.png>)

4. Jika uang yang dimasukkan lebih besar, mesin memberikan kembalian seperti yang terlihat pada Gambar 8.
5. Jika transaksi dibatalkan, pengguna dapat memanggil `refund()` dan `balance` pengguna akan bernilai 0. Untuk mengecek `balance`, panggil `getBalance()`.
    | **Gambar 9.** Contoh ouput dari `refund()`| **Gambar 10.** Sisa `balance` pengguna|
    |-|-|
    |![alt text](<Screenshot 2025-09-09 162421.png>)|![alt text](<Screenshot 2025-09-09 162528.png>)|



6. Total pendapatan mesin dapat dilihat dengan `getTotal()`.
    | **Gambar 11.** Contoh ouput `getTotal()` yang gagal|
    |-------------------------------|
    ![alt text](<Screenshot 2025-09-09 165855.png>)


## 6. Kesimpulan

Proyek **TicketMachine** adalah contoh sederhana penerapan **pemrograman berorientasi objek (OOP)** dalam Java menggunakan BlueJ. Program ini memperkenalkan konsep class, atribut (variabel), constructor, getter, setter, serta metode aksi. Melalui simulasi mesin tiket ini, mahasiswa dapat memahami bagaimana sebuah objek bekerja, bagaimana data dikelola, serta bagaimana interaksi pengguna diproses oleh program.

---
###### Ditulis oleh **Muhammad Quthbi Danish Abqori - 5025241036**