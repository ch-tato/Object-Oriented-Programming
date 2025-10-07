###### Selasa, 30 September 2025
### Pertemuan 6
---
<h1><b>Grouping Object</b></h1>

---

## Daftar Isi
0. [Pendahuluan](#0-pendahuluan)
1. [LibraryCatalog](#1-librarycatalog)
2. [NotebookApp](#2-notebookapp)
3. [StudentRecordSystem](#3-studentrecordsystem)
4. [Kesimpulan](#4-kesimpulan)

## 0. Pendahuluan

**Grouping object** adalah konsep pemodelan di mana objek-objek yang memiliki karakteristik atau tujuan yang sama dikelompokkan menjadi satu struktur kolektif—biasanya menggunakan koleksi seperti `ArrayList`, `Set`, atau `Map`. Tujuan grouping object:

- Memudahkan manajemen banyak entitas (mis. daftar buku, catatan, mahasiswa).
- Menyediakan operasi kolektif: tambah, hapus, cari, iterasi, filter.
- Memisahkan tanggung jawab (single responsibility): objek domain (mis. `Book`, `Note`, `Student`) menyimpan state, sedangkan kelas yang mengelola koleksi bertugas menyediakan antarmuka pengguna dan logika koleksi.
- Mempermudah perluasan fitur: penyimpanan persisten, sortir, filter lanjutan, atau UI lain nantinya.

Contoh pattern yang dipakai pada tiga kode yang kamu kirim: setiap entitas (Book/Note/Student) adalah objek domain sederhana, dan kelas utama (`LibraryCatalog`, `NotebookApp`, `StudentRecordSystem`) menggunakan `ArrayList<T>` untuk grouping object dan menyediakan menu interaktif berbasis console.

## 1. LibraryCatalog

### Source code

```java
import java.util.ArrayList;
import java.util.Scanner;

class Book {
    private String title;
    private String author;
    private int year;

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Judul: " + title + " | Penulis: " + author + " | Tahun: " + year;
    }
}

public class LibraryCatalog {
    public static void main(String[] args) {
        ArrayList<Book> catalog = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n=== Library Catalog ===");
            System.out.println("1. Tambah Buku");
            System.out.println("2. Lihat Semua Buku");
            System.out.println("3. Cari Buku");
            System.out.println("4. Hapus Buku");
            System.out.println("5. Keluar");
            System.out.print("Pilih menu: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // clear buffer

            switch (choice) {
                case 1:
                    System.out.print("Masukkan judul buku: ");
                    String title = scanner.nextLine();
                    System.out.print("Masukkan nama penulis: ");
                    String author = scanner.nextLine();
                    System.out.print("Masukkan tahun terbit: ");
                    int year = scanner.nextInt();
                    catalog.add(new Book(title, author, year));
                    System.out.println("Buku berhasil ditambahkan!");
                    break;

                case 2:
                    System.out.println("\nDaftar Buku:");
                    if (catalog.isEmpty()) {
                        System.out.println("Tidak ada buku dalam katalog.");
                    } else {
                        for (int i = 0; i < catalog.size(); i++) {
                            System.out.println((i + 1) + ". " + catalog.get(i));
                        }
                    }
                    break;

                case 3:
                    System.out.print("Masukkan judul buku yang dicari: ");
                    String searchTitle = scanner.nextLine().toLowerCase();
                    boolean found = false;

                    for (Book book : catalog) {
                        if (book.getTitle().toLowerCase().contains(searchTitle)) {
                            System.out.println(book);
                            found = true;
                        }
                    }

                    if (!found) {
                        System.out.println("Buku tidak ditemukan.");
                    }
                    break;

                case 4:
                    System.out.print("Masukkan nomor buku yang akan dihapus: ");
                    int index = scanner.nextInt();
                    if (index > 0 && index <= catalog.size()) {
                        catalog.remove(index - 1);
                        System.out.println("Buku berhasil dihapus!");
                    } else {
                        System.out.println("Nomor buku tidak valid.");
                    }
                    break;

                case 5:
                    System.out.println("Keluar dari katalog. Terima kasih!");
                    break;

                default:
                    System.out.println("Pilihan tidak valid, coba lagi.");
            }

        } while (choice != 5);

        scanner.close();
    }
}
```

### Penjelasan singkat

- **Tujuan:** Mengelola katalog buku sederhana lewat menu console.
- **Kelas penting:**

  - `Book` — objek domain yang menyimpan `title`, `author`, `year` dan mengoverride `toString()` untuk tampilan ringkas.
  - `LibraryCatalog` — kelas berisi `main` yang:

    - Menyimpan `ArrayList<Book> catalog`.
    - Menggunakan `Scanner` untuk input pengguna.
    - Menyediakan menu berulang (`do...while`) dengan pilihan:

      1. Tambah buku (membaca judul, penulis, tahun; membuat `Book` baru dan menambah ke `catalog`).
      2. Lihat semua buku (menampilkan semua elemen `catalog` atau pesan jika kosong).
      3. Cari buku (mencari berdasarkan judul, case-insensitive `contains`).
      4. Hapus buku (menghapus berdasarkan nomor indeks yang diminta pengguna).
      5. Keluar.
- **Alur input/output:** Input dari user via console; output daftar/konfirmasi/error ditampilkan ke console.
- **Perhatian / kemungkinan perbaikan:**

  - Validasi input untuk `year` (hindari `InputMismatchException` saat user memasukkan non-nomer).
  - Menangani kasus ketika `scanner.nextInt()` diikuti `nextLine()` (buffer) sudah ditangani di contoh, tetapi tetap perlu hati-hati.
  - Tambahkan fungsi edit, sortir (mis. berdasarkan tahun), atau penyimpanan ke file/database agar data bertahan antar-run.

### Contoh Output

|Gambar 1.1 Contoh Output 1| Gambar 1.2 Contoh Output 2|Gambar 1.3 Contoh Output 3|
|-|-|-|
|![Contoh Output 1](./assets/Screenshot%202025-10-07%20124559.png)|![Contoh Output 2](./assets/Screenshot%202025-10-07%20124623.png)|![Contoh Output 3](./assets/Screenshot%202025-10-07%20124633.png)|


## 2. NotebookApp

### Source code

```java
import java.util.ArrayList;
import java.util.Scanner;

class Note {
    private String title;
    private String content;

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Judul: " + title + "\nIsi: " + content;
    }
}

public class NotebookApp {
    public static void main(String[] args) {
        ArrayList<Note> notes = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n=== Personal Notebook ===");
            System.out.println("1. Tambah Catatan");
            System.out.println("2. Lihat Semua Catatan");
            System.out.println("3. Cari Catatan");
            System.out.println("4. Hapus Catatan");
            System.out.println("5. Keluar");
            System.out.print("Pilih menu: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // clear buffer

            switch (choice) {
                case 1:
                    System.out.print("Masukkan judul catatan: ");
                    String title = scanner.nextLine();
                    System.out.print("Masukkan isi catatan: ");
                    String content = scanner.nextLine();
                    notes.add(new Note(title, content));
                    System.out.println("Catatan berhasil ditambahkan!");
                    break;

                case 2:
                    System.out.println("\nDaftar Catatan:");
                    if (notes.isEmpty()) {
                        System.out.println("Belum ada catatan.");
                    } else {
                        for (int i = 0; i < notes.size(); i++) {
                            System.out.println((i + 1) + ". " + notes.get(i).getTitle());
                        }
                    }
                    break;

                case 3:
                    System.out.print("Masukkan judul catatan yang dicari: ");
                    String searchTitle = scanner.nextLine().toLowerCase();
                    boolean found = false;

                    for (Note note : notes) {
                        if (note.getTitle().toLowerCase().contains(searchTitle)) {
                            System.out.println("\nCatatan ditemukan:\n" + note);
                            found = true;
                        }
                    }

                    if (!found) {
                        System.out.println("Catatan tidak ditemukan.");
                    }
                    break;

                case 4:
                    System.out.print("Masukkan judul catatan yang akan dihapus: ");
                    String deleteTitle = scanner.nextLine().toLowerCase();
                    boolean removed = notes.removeIf(
                        note -> note.getTitle().toLowerCase().equals(deleteTitle)
                    );

                    if (removed) {
                        System.out.println("Catatan berhasil dihapus.");
                    } else {
                        System.out.println("Catatan dengan judul tersebut tidak ditemukan.");
                    }
                    break;

                case 5:
                    System.out.println("Keluar dari Notebook. Sampai jumpa!");
                    break;

                default:
                    System.out.println("Pilihan tidak valid, coba lagi.");
            }

        } while (choice != 5);

        scanner.close();
    }
}
```

### Penjelasan singkat

- **Tujuan:** Aplikasi catatan pribadi sederhana via console.
- **Kelas penting:**

  - `Note` — entitas domain yang menyimpan `title` dan `content`, serta `toString()` yang menampilkan judul dan isi.
  - `NotebookApp` — `main` yang:

    - Menyimpan `ArrayList<Note> notes`.
    - Menyediakan menu (Tambah, Lihat Semua, Cari, Hapus, Keluar).
    - Implementasi pencarian menggunakan `toLowerCase()` sehingga pencarian tidak peka huruf besar/kecil.
    - Hapus catatan menggunakan `removeIf(...)` berdasarkan judul yang cocok (exact match setelah lower-case).
- **Alur behavior:**

  - Menambah catatan: baca judul & isi dari pengguna lalu `notes.add(new Note(...))`.
  - Melihat daftar: menampilkan hanya judul dalam daftar (untuk ringkas).
  - Mencari: menampilkan selengkap objek (`toString()`) saat judul mengandung string pencarian.
  - Hapus: meminta judul, menghapus semua yang judulnya persis sama (case-insensitive).
- **Perhatian / perbaikan yang disarankan:**

  - Jika judul tidak unik, penghapusan berdasarkan judul akan menghapus semua yang cocok — bisa ubah menjadi hapus berdasarkan indeks untuk lebih presisi.
  - Tambahkan fitur edit, tag/kategori, atau penyimpanan (file/DB).
  - Pertimbangkan sanitasi input (hindari kosong/whitespace-only judul).

### Contoh Output

|Gambar 2.1 Contoh Output 1| Gambar 2.2 Contoh Output 2|
|-|-|
|![Contoh Output 1](./assets/Screenshot%202025-10-07%20124947.png)|![Contoh Output 2](./assets/Screenshot%202025-10-07%20125003.png)|

## 3. StudentRecordSystem

### Source code

```java
import java.util.ArrayList;
import java.util.Scanner;

class Student {
    private String nrp;
    private String name;
    private String major;

    public Student(String nrp, String name, String major) {
        this.nrp = nrp;
        this.name = name;
        this.major = major;
    }

    public String getNim() {
        return nrp;
    }

    public String getName() {
        return name;
    }

    public String getMajor() {
        return major;
    }

    @Override
    public String toString() {
        return "NRP: " + nrp + " | Nama: " + name + " | Jurusan: " + major;
    }
}

public class StudentRecordSystem {
    public static void main(String[] args) {
        ArrayList<Student> records = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n=== Student Record System ===");
            System.out.println("1. Tambah Mahasiswa");
            System.out.println("2. Lihat Semua Mahasiswa");
            System.out.println("3. Cari Mahasiswa");
            System.out.println("4. Hapus Mahasiswa");
            System.out.println("5. Keluar");
            System.out.print("Pilih menu: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // clear buffer

            switch (choice) {
                case 1:
                    System.out.print("Masukkan NRP: ");
                    String nrp = scanner.nextLine();
                    System.out.print("Masukkan Nama: ");
                    String name = scanner.nextLine();
                    System.out.print("Masukkan Jurusan: ");
                    String major = scanner.nextLine();
                    records.add(new Student(nrp, name, major));
                    System.out.println("Mahasiswa berhasil ditambahkan!");
                    break;

                case 2:
                    System.out.println("\nDaftar Mahasiswa:");
                    if (records.isEmpty()) {
                        System.out.println("Belum ada data mahasiswa.");
                    } else {
                        for (Student s : records) {
                            System.out.println(s);
                        }
                    }
                    break;

                case 3:
                    System.out.print("Masukkan NRP yang dicari: ");
                    String searchNim = scanner.nextLine();
                    boolean found = false;

                    for (Student s : records) {
                        if (s.getNim().equalsIgnoreCase(searchNim)) {
                            System.out.println("Data ditemukan: " + s);
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        System.out.println("Mahasiswa dengan NRP " + searchNim + " tidak ditemukan.");
                    }
                    break;

                case 4:
                    System.out.print("Masukkan NRP mahasiswa yang akan dihapus: ");
                    String deleteNim = scanner.nextLine();
                    boolean removed = records.removeIf(s -> s.getNim().equalsIgnoreCase(deleteNim));

                    if (removed) {
                        System.out.println("Data mahasiswa berhasil dihapus.");
                    } else {
                        System.out.println("Mahasiswa dengan NRP " + deleteNim + " tidak ditemukan.");
                    }
                    break;

                case 5:
                    System.out.println("Keluar dari sistem. Terima kasih!");
                    break;

                default:
                    System.out.println("Pilihan tidak valid, coba lagi.");
            }
        } while (choice != 5);

        scanner.close();
    }
}
```

### Penjelasan singkat

- **Tujuan:** Sistem pencatatan mahasiswa sederhana (tambah, lihat, cari, hapus) lewat console.
- **Kelas penting:**

  - `Student` — menyimpan `nim`, `name`, `major` dan `toString()` untuk tampilan ringkas.
  - `StudentRecordSystem` — `main` yang:

    - Menyimpan `ArrayList<Student> records`.
    - Menyediakan menu berulang dengan pilihan tambah, lihat, cari, hapus, keluar.
    - Pencarian dilakukan berdasarkan `nim` (menggunakan `equalsIgnoreCase` untuk fleksibilitas huruf).
    - Penghapusan menggunakan `removeIf` dengan kecocokan `nim`.
- **Alur behavior:**

  - Tambah mahasiswa: baca `nim`, `name`, `major`, lalu tambahkan objek `Student` ke `records`.
  - Lihat semua: iterasi `records` dan cetak setiap student.
  - Cari: cari student dengan `nim` tertentu, tampilkan jika ditemukan.
  - Hapus: hapus semua student dengan `nim` yang cocok (seharusnya unik—idealnya NIM unik).
- **Perhatian / perbaikan yang disarankan:**

  - Pastikan `nim` bersifat unik — tambahkan validasi sebelum menambah untuk mencegah duplikasi.
  - Saat menghapus/pencarian gunakan pesan yang jelas jika tidak ditemukan.
  - Pertimbangkan penggunaan `Map<String, Student>` (key = NIM) jika akses by-NIM sering dilakukan — operasi cari/hapus jadi O(1).
  - Penanganan input invalid dan konfirmasi sebelum hapus bisa menambah keamanan data.
  - Untuk produksi, simpan data ke file atau database agar persistensi tersedia.

### Contoh Output

|Gambar 3.1 Contoh Output 1| Gambar 3.2 Contoh Output 2|Gambar 3.3 Contoh Output 3|
|-|-|-|
|![Contoh Output 1](./assets/Screenshot%202025-10-07%20125458.png)|![Contoh Output 2](./assets/Screenshot%202025-10-07%20125526.png)|![Contoh Output 3](./assets/Screenshot%202025-10-07%20125534.png)|

## 4. Kesimpulan

- Ketiga program adalah contoh bagus dari **grouping object**: objek domain sederhana + koleksi (`ArrayList`) untuk mengelola banyak entitas.
- Struktur umum yang sama: entitas (`Book`/`Note`/`Student`) + manajer/menu console yang melakukan operasi CRUD dasar.
- Untuk pengembangan lebih lanjut, fokus pada:

  - Validasi input & penanganan error.
  - Menjamin keunikan identifier (mis. NIM) bila diperlukan.
  - Menambahkan persistence (file/DB).
  - Menambah fitur UX: edit, sortir, filter, pagination untuk daftar panjang.


---
###### Ditulis oleh **Muhammad Quthbi Danish Abqori - 5025241036**