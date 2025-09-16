###### Selasa, 9 September 2025
### Pertemuan 3
---
<h1><b>Pengantar Konsep OOP</b></h1>

---
### Daftar Isi
- [0. Pendahuluan](#0-pendahuluan)
- [1. Apa Itu OOP?](#1-apa-itu-oop)
- [2. OOP vs POP](#2-oop-vs-pop)
- [3. Contoh Sederhana POP & OOP (Java)](#3-contoh-sederhana-pop--oop-java)
- [Mengapa OOP?](#4-mengapa-oop)
- [Komponen Dasar OOP](#5-komponen-dasar-oop)
    - [a. *Class*](#a-class)
    - [b. *Object*](#b-object)
    - [c. *Attribute (Field/Property)*](#c-attribute-fieldproperty)
    - [d. *Method*](#d-method)
    - [e. *Constructor*](#e-constructor)
- [Konsep OOP](#6-konsep-oop)
    - [a. *Encapsulation*](#a-encapsulation)
    - [b. *Abstraction*](#b-abstraction)
    - [c. *Inheritance*](#c-inheritance)
    - [d. *Polymorphism*](#d-polymorphism)
- [Contoh Program Jadi (Java)](#7-contoh-program-jadi-java)
- [Kesimpulan](#8-kesimpulan)
---
### 0. Pendahuluan
OOP atau *Object-Oriented Programming* adalah paradigma pemrograman modern yang lebih efisien dibandingkan POP atau *Procedural-Oriented Programming* dan sering digunakan dalam membangun sebuah *framework*. OOP adalah konsep yang harus dipahami ketika mempelajari bahasa pemrograman Java lebih dalam karena Java sendiri merupakan bahasa pemrograman yang memang didesain untuk OOP.

---
### 1. Apa Itu OOP?
Sesuai namanya, ***Object-Oriented Programming*** atau Pemrograman Berorientasi Objek dalam Bahasa Indonesia adalah sebuah paradigma atau teknik pemrograman yang berorientasikan objek. Pada OOP, fungsi dan variabel dibungkus dalam sebuah objek atau ***class*** yang dapat saling berinteraksi sehingga membentuk sebuah program. Objek yang dimaksud di sini adalah representasi nyata yang memiliki ***attribute* (variabel/data)** dan **perilaku (*method*/fungsi)**.

Tujuan utama dari OOP adalah mempermudah pengembangan perangkat lunak dengan konsep **modularitas, enkapsulasi, pewarisan (*inheritance*), dan polimorfisme**.

Konsep OOP pertama kali dikenalkan dalam sebuah bahasa pemrograman bernama **Simula** yang dikembangkan di Norwegia. Simula memaparkan ide *class* dan *object* yang menjadi dasar dari OOP hingga saat ini. Selanjutnya, pada 1970-an, Xerox PARC mengembangkan bahasa pemrograman **Smalltalk** yang semakin mempopulerkan paradigma berbasis objek. Dari sana, lahirlah bahasa-bahasa pemrograman modern, seperti C++, Java, Python, dan C#, yang menjadi prinsip OOP dan menjadi standar utama dalam pengembangan perangkat lunak saat ini.

---
### 2. OOP vs POP
*Object-Oriented Programming* memiliki perbedaan mendasar dengan *Procedural-Oriented Programming* yaitu sebagai berikut.

|Aspek|POP (*Procedural-Oriented Programming*)|OOP (*Object-Oriented Programming*)|
|-----|---------------------------------------|-------------------------------|
|Konsep dasar|Program berbasis fungsi/prosedur|Program berbasis objek (*class* & *object*)|
|Pendekatan|Langkah demi langkah (*top-down*)|Modular dan berbasis entitas nyata|
|Data|Data dan fungsi terpisah|Data dan fungsi digabung dalam objek|
|Reusability|Sulit digunakan ulang|Mudah digunakan ulang (*class, inheritance*)|
|Contoh bahasa|C, Pascal|Java, C++, Python|

---
### 3. Contoh Sederhana POP & OOP (Java)
Berikut adalah contoh sederhana dari implementasi POP dan OOP dalam bahasa Java.
#### a. Contoh POP (*Procedural-Oriented Programming*)
Berikut adalah [`POP.java`](pop.java) yang merupakan contoh sederhana dari implementasi POP.
```java
public class POP{
    // Fungsi untuk menghitung luas persegi panjang
    public static int getArea(int l, int w){
        return l*w;
    }

    public static void Main(String[] args){
        int length = 5, width = 3;
        int area = getArea(length, width);
        System.out.println("Area = " + area);
    }
}
```
**Output:**
```
Area = 15
```

#### b. Contoh OOP (*Object-Oriented Programming*)
Berikut adalah [`OOP.java`](oop.java) yang merupakan contoh sederhana dari implementasi OOP.
```java
class Rectangle{
    int length, width;

    // Konstruktor
    Rectangle(int l, int w){
        length = l;
        width = w;
    }

    // Method (fungsi) untuk menghitung luas
    int getArea(){
        return length*width;
    }
}

public class OOP{
    public static void Main(String[] args){
        Rectangle rc = new Rectangle(3, 5);
        System.out.println("Area = " + rc.getArea());
    }
}
```
**Output:**
```
Area = 15
```
---
### 4. Mengapa OOP?
Ada beberapa alasan mengapa OOP banyak digunakan yaitu sebagai berikut:
- **Modularitas**\
    Kode dapat dipisah ke dalam *class* dan *object* sehingga lebih terstruktur.
- ***Reusability***\
    Kode dapat digunakan kembali melalui konsep *inheritance* dan *polymorphism*.
- ***Maintainability***\
    Program lebih mudah diperbaiki atau dikembangkan karena tiap bagian memiliki fungsi yang jelas.
- **Abstraksi dunia nyata**\
    Objek dalam kode dapat direpresentasikan entitas nyata sehingga lebih intuitif.

---
### 5. Komponen Dasar OOP?
#### **a.** ***Class***
*Class* bisa kita artikan sebagai *blueprint* atau cetak biru untuk membuat objek, berisi *attribute* dan *method*. Contohnya adalah kita membuat sebuah *class* bernama `Mobil`. Dari `class Mobil` tersebut, kita bisa tambahkan *attribute* dan *method*, misalnya `horse_power` (*attribute*) dan `accelerate()` (*method*).

#### **b.** ***Object***
*Object* adalah instansi dari *class*, mewakili entitas nyata. Contohnya adalah jika kita mempunyai `class Mobil`, maka kita bisa membuat sebuah *object* bernama `ferrari` yang merupakan sebuah variabel bertipe data `Mobil`.

#### **c.** ***Attribute (Field/Property)***
*Attribute* adalah data atau variabel yang dimiliki oleh objek. Contohnya adalah jika kita punya objek `ferrari` dari *class* `Mobil`, maka kita bisa tambahkan *attribute* `horse_power` ke `ferrari` tersebut dengan cara `ferrari.horse_power = 1000`.

#### **d.** ***Method***
*Method* adalah Perilaku atau fungsi yang dapat dilakukan oleh objek. Contohnya adalah jika kita punya objek `ferrari` dari `class Mobil`, maka kita bisa jalankan fungsi `accelerate()` ke `ferrari` dengan cara `ferrari.accelerate()`.

#### **e.** ***Constructor***
*Constructor* adalah *method* khusus untuk membuat objek baru. Contohnya jika kita ingin membuat objek baru dari *class* `Mobil`, kita bisa lakukan `Mobil mclaren = new Mobil()`.

---
### 6. Konsep OOP
#### **a.** ***Encapsulation***
Menyembunyikan detail implementasi dan hanya menampilkan hal yang penting melalui *getter* dan *setter*. Contohnya adalah variabel `private` yang hanya bisa diakses lewat *method* publik.

#### **b.** ***Abstraction***
Menyembunyikan detail kompleks dan hanya memperlihatkan fungsi utama. Contohnya adalah *interface* atau *abstract class* yang hanya mendefinisikan perilaku tanpa implementasi detail.

#### **c.** ***Inheritance***
Kemampuan sebuah *class* untuk mewarisi *attribute* dan *method* dari *class* lain. Contohnya adalah `class Anjing` mewarisi dari `class Hewan`.

#### **d.** ***Polymorphism***
Kemampuan objek untuk memiliki banyak bentuk (perilaku berbeda dengan nama *method* yang sama). Contohnya *method* `suara()` bisa menghasilkan `"Guk guk"` untuk objek `Anjing`, dan `"Meong"` untuk objek `Kucing`.

---
### 7. Contoh Program Jadi (Java)
Berikut adalah [`Main.java`](Main.java) yang mempunyai komponen-komponen sebagai berikut:
-  *Class* → `Mobil`
- *Object* → `ferrari` dan `mclaren`
- *Attribute* → `merk`, `horsePower`, `topSpeed`, dan `tahun`.
- *Method* → `accelerate()`, `brake()`, dan `infoMobil()`.
- *Constructor* → untuk inisialisasi objek.

```java
// File: Main.java

class Mobil {
    // Attribute (Field)
    String merk;
    int horsePower;
    int topSpeed;
    int tahun;

    // Constructor
    public Mobil(String merk, int horsePower, int topSpeed, int tahun) {
        this.merk = merk;
        this.horsePower = horsePower;
        this.topSpeed = topSpeed;
        this.tahun = tahun;
    }

    // Method: menampilkan informasi mobil
    void infoMobil() {
        System.out.println("=== Info Mobil ===");
        System.out.println("Merk       : " + merk);
        System.out.println("Horse Power: " + horsePower + " HP");
        System.out.println("Top Speed  : " + topSpeed + " km/h");
        System.out.println("Tahun      : " + tahun);
        System.out.println();
    }

    // Method: mempercepat mobil
    void accelerate() {
        System.out.println(merk + " berakselerasi dengan cepat!");
    }

    // Method: mengerem mobil
    void brake() {
        System.out.println(merk + " melakukan pengereman!");
    }
}

public class Main {
    public static void main(String[] args) {
        // Membuat object dengan constructor
        Mobil ferrari = new Mobil("Ferrari F8", 710, 340, 2021);
        Mobil mclaren = new Mobil("McLaren 720S", 710, 341, 2022);

        // Menampilkan informasi mobil
        ferrari.infoMobil();
        mclaren.infoMobil();

        // Menggunakan method
        ferrari.accelerate();
        ferrari.brake();

        mclaren.accelerate();
        mclaren.brake();
    }
}

```
**Output:**
```
=== Info Mobil ===
Merk       : Ferrari F8
Horse Power: 710 HP
Top Speed  : 340 km/h
Tahun      : 2021

=== Info Mobil ===
Merk       : McLaren 720S
Horse Power: 710 HP
Top Speed  : 341 km/h
Tahun      : 2022

Ferrari F8 berakselerasi dengan cepat!
Ferrari F8 melakukan pengereman!
McLaren 720S berakselerasi dengan cepat!
McLaren 720S melakukan pengereman!

```

---
### 8. Kesimpulan

OOP adalah paradigma pemrograman yang berorientasi pada objek, menggabungkan data (***attribute***) dan perilaku (***method***) dalam satu kesatuan. Dengan komponen dasar seperti ***class*, *object*, *attribute*, *method*,** dan ***constructor***, serta empat konsep utama (***encapsulation*, *abstraction*, *inheritance*, *polymorphism***), OOP membuat kode lebih **modular, *reusable*, dan mudah dipelihara**. Karena itulah OOP menjadi standar utama dalam pengembangan perangkat lunak modern, termasuk di Java.

---
###### Ditulis oleh **Muhammad Quthbi Danish Abqori - 5025241036**
