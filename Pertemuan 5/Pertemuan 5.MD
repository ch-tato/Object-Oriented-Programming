###### Selasa, 30 September 2025
### Pertemuan 5
---
<h1><b>Sistem FRS (Formulir Rencana Studi)</b></h1>

---

## Daftar Isi
0. [Pendahuluan](#0-pendahuluan)
1. [Source Code](#1-source-code)
2. [Class Dosen](#2-class-dosen)
3. [Class MataKuliah](#3-class-matakuliah)
4. [Class Mahasiswa](#4-class-mahasiswa)
5. [Class FRS](#5-class-frs)
6. [Class Main](#6-class-main)

## 0. Pendahuluan

**FRS (Formulir Rencana Studi)** adalah dokumen akademik yang digunakan oleh mahasiswa untuk merencanakan mata kuliah yang akan diambil dalam satu semester tertentu. FRS berfungsi sebagai:

- Panduan penyusunan jadwal kuliah
- Pengendalian beban studi (SKS)
- Monitoring perkembangan akademik mahasiswa
- Dasar penentuan pembayaran SKS

Dalam sistem ini, FRS memungkinkan mahasiswa untuk menambah dan menghapus mata kuliah dengan mempertimbangkan kuota dan batas maksimal SKS.

## 1. Source Code
Berikut adalah isi dari [`Main.java`](/Pertemuan%205/Main.java):

```java
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Dosen{
    private String nama;
    private String nip;
    
    public Dosen(String nama, String nip){
        this.nama = nama;
        this.nip = nip;
    }
    public String getNama(){
        return nama;
    }
    public String getNip(){
        return nip;
    }
}

class MataKuliah{
    private String namaMK;
    private String kodeMK;
    private int sks;
    private Dosen dosenPengampu;
    private int kuota;
    private int maxKuota;
    private int jumlahMhs;
    private char kelas;
    
    public MataKuliah(String namaMK, String kodeMK, int sks, Dosen dosenPengampu, int maxKuota, int jumlahMhs, char kelas){
        this.namaMK = namaMK;
        this.kodeMK = kodeMK;
        this.sks = sks;
        this.dosenPengampu = dosenPengampu;
        this.maxKuota = maxKuota;
        this.kuota = maxKuota - jumlahMhs;
        this.jumlahMhs = jumlahMhs;
        this.kelas = kelas;
    }
    
    public String getNamaMK(){
        return namaMK;
    }
    
    public String getKodeMK(){
        return kodeMK;
    }
    
    public int getSks(){
        return sks;
    }

    public int getKuota(){
        return kuota;
    }
    
    public int maxKuota(){
        return maxKuota;
    }
    
    public char getKelas(){
        return kelas;
    }

    public boolean isFull(){
        return kuota <= 0;
    }

    public void decreaseKuota(){
        if(kuota > 0){
            kuota--;
        }
    }

    public int jumlahMhs(){
        return jumlahMhs;
    }

    public void increaseJumlahMhs(){
        jumlahMhs++;
    }

    public String getDosenPengampu(){
        return dosenPengampu.getNama();
    }
}

class Mahasiswa{
    private String nama;
    private String nrp;
    private Dosen doswal;
    
    public Mahasiswa(String nama, String nrp, Dosen doswal){
        this.nama = nama;
        this.nrp = nrp;
        this.doswal = doswal;
    }
    
    public String getNama(){
        return nama;
    }
    
    public String getNrp(){
        return nrp;
    }
    
    public String getDoswal(){
        return doswal.getNama();
    }
}

class FRS{
    private Mahasiswa mahasiswa;
    private List<MataKuliah> mataKuliahsList;
    private int totalSks;
    private int maxSks;
    private String tahun;
    private String semester;
    
    public FRS(Mahasiswa mahasiswa, String tahun, String semester, int maxSks){
        this.mahasiswa = mahasiswa;
        this.mataKuliahsList = new ArrayList<>();
        this.totalSks = 0;
        this.maxSks = maxSks;
        this.tahun = tahun;
        this.semester = semester;
    }

    public Mahasiswa getMahasiswa(){
        return mahasiswa;
    }

    public boolean addMK(MataKuliah mk){
        if(totalSks + mk.getSks() > maxSks){
            System.out.println("Total SKS melebihi batas maksimum.");
            return false;
        }
        if(mk.isFull()){
            System.out.println("Kuota mata kuliah sudah penuh.");
            return false;
        }
        
        mataKuliahsList.add(mk);
        totalSks += mk.getSks();
        mk.decreaseKuota();
        mk.increaseJumlahMhs();
        System.out.println("Mata kuliah " + mk.getNamaMK() + " berhasil ditambahkan.");
        return true;
    }

    public boolean dropMK(MataKuliah mk){
        if(mataKuliahsList.remove(mk)){
            totalSks -= mk.getSks();
            System.out.println("Mata kuliah " + mk.getNamaMK() + " berhasil dihapus.");
            return true;
        } else {
            System.out.println("Mata kuliah " + mk.getNamaMK() + " tidak ditemukan dalam FRS.");
            return false;
        }
    }
    
    public void printFRS(){
        System.out.println("FRS Mahasiswa: " + mahasiswa.getNama() + " (" + mahasiswa.getNrp() + ")");
        System.out.println("Dosen Wali: " + mahasiswa.getDoswal());
        System.out.println("Tahun: " + tahun + ", Semester: " + semester);
        System.out.println("Mata Kuliah yang diambil:");
        for(MataKuliah mk : mataKuliahsList){
            System.out.println("- " + mk.getNamaMK() + " (" + mk.getKodeMK() + "), SKS: " + mk.getSks());
        }
        System.out.println("Total SKS: " + totalSks + "/" + maxSks);
    }
}

public class Main{
    public static void main(String[] args){
        Dosen d1 = new Dosen("Prof. Ir. Ary Mazharuddin Shiddiqi, S.Kom., M.Comp.Sc., Ph.D., IPM.", "198106202005011001");
        Dosen d2 = new Dosen("Fajar Baskoro, S.Kom., M.T.", "198106202005011002");
        Dosen d3 = new Dosen("Misbakhul Munir Irfan Subakti, S.Kom., M.Sc.", "196504101993031003");
        Dosen d4 = new Dosen("Imam Mustafa Kamal, S.ST, Ph.D", "196504101993031004");
        Dosen d5 = new Dosen("Dr. Sarwosri, S.Kom. M.T", "196504101993031005");
        Dosen d6 = new Dosen("Arya Yudhi Wijaya, S.Kom, M.Kom.", "196504101993031006");
        Dosen d7 = new Dosen("Ir. Khakim Ghozali, M.MT", "196403051989031004");
        Dosen d8 = new Dosen("Prof. Dr. Ir. Achmad Roesyadi, DEA", "195004281979031002");
        Dosen d9 = new Dosen("Prof. Dr. Ir. Ali Altway, M.Sc", "195108041974121001");
        Dosen d10 = new Dosen("Prof. Dr. Ir. Sugeng Winardi, M.Eng", "195307191978031001");
        Dosen d11 = new Dosen("Prof. Dr. Ir. Mahfud, DEA", "196108021986011001");
        Dosen d12 = new Dosen("Prof. Dr. Ir. Heru Setyawan, M.Eng", "196702031991021001");
        Dosen d13 = new Dosen("Prof. Dr. Ir. Kuswandi, DEA", "195806121984031003");
        Dosen d14 = new Dosen("Dr. Eng. Muhamad Hilmil Muchtar Aditya Pradana, S.Kom, M.Sc", "195510151980031001");

        MataKuliah mk1 = new MataKuliah("Pemrograman Web", "EF234301", 3, d3, 40, 35, 'C');
        MataKuliah mk2 = new MataKuliah("Pemrograman Berorientasi Objek", "EF234302", 3, d2, 40, 38, 'B');
        MataKuliah mk3 = new MataKuliah("Jaringan Komputer", "EF234303", 4, d1, 45, 40, 'C');
        MataKuliah mk4 = new MataKuliah("Teori Graf", "EF234304", 3, d6, 35, 30, 'A');
        MataKuliah mk5 = new MataKuliah("Matematika Diskrit", "EF234305", 3, d14, 50, 45, 'B');
        MataKuliah mk6 = new MataKuliah("Konsep Pengembangan Perangkat Lunak", "EF234307", 2, d5, 30, 28, 'A');
        MataKuliah mk7 = new MataKuliah("Konsep Kecerdasan Artifisial", "EK234201", 3, d4, 40, 37, 'D');
        MataKuliah mk8  = new MataKuliah("Pemrograman Web", "EF234301", 3, d7, 40, 40, 'A');
        MataKuliah mk9  = new MataKuliah("Pemrograman Berorientasi Objek", "EF234302", 3, d8, 40, 32, 'C');
        MataKuliah mk10 = new MataKuliah("Sistem Operasi", "EF234308", 4, d9, 45, 40, 'B');
        MataKuliah mk11 = new MataKuliah("Analisis Algoritma", "EF234309", 3, d10, 35, 35, 'A');
        MataKuliah mk12 = new MataKuliah("Manajemen Proyek Perangkat Lunak", "EF234310", 3, d11, 40, 36, 'B');
        MataKuliah mk13 = new MataKuliah("Sistem Basis Data", "EF234311", 4, d12, 50, 47, 'C');
        MataKuliah mk14 = new MataKuliah("Interaksi Manusia dan Komputer", "EF234312", 2, d13, 40, 40, 'A');
        MataKuliah[] semuaMK = {mk1, mk2, mk3, mk4, mk5, mk6, mk7, mk8, mk9, mk10, mk11, mk12, mk13, mk14};

        Mahasiswa m1 = new Mahasiswa("Muhammad Quthbi Danish Abqori", "5025241036", d1);

        FRS frs1 = new FRS(m1, "2025/2026", "Gasal", 24);

        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("\n=== MENU FRS ===");
            System.out.println("1. Lihat daftar mata kuliah");
            System.out.println("2. Tambah mata kuliah ke FRS");
            System.out.println("3. Hapus mata kuliah dari FRS");
            System.out.println("4. Lihat FRS mahasiswa");
            System.out.println("5. Keluar");
            System.out.print("Pilih menu: ");
            int pilihan = sc.nextInt();
            sc.nextLine();

            if(pilihan == 1){
                System.out.println("\n=== Daftar Mata Kuliah ===");
                for(int i=0; i<semuaMK.length; i++){
                    MataKuliah mk = semuaMK[i];
                    System.out.println((i+1) + ". " + mk.getNamaMK() + " (" + mk.getKodeMK() + 
                                       "), SKS: " + mk.getSks() + 
                                       ", Dosen: " + mk.getDosenPengampu() + 
                                       ", Kuota: " + mk.getKuota() + "/" + mk.maxKuota());
                }
            } 
            else if(pilihan == 2){
                System.out.print("Masukkan kode MK yang ingin ditambah: ");
                String kode = sc.nextLine();
                MataKuliah found = null;
                for(MataKuliah mk : semuaMK){
                    if(mk.getKodeMK().equalsIgnoreCase(kode)){
                        found = mk;
                        break;
                    }
                }
                if(found != null){
                    frs1.addMK(found);
                } else {
                    System.out.println("Kode MK tidak ditemukan.");
                }
            }
            else if(pilihan == 3){
                System.out.print("Masukkan kode MK yang ingin dihapus: ");
                String kode = sc.nextLine();
                MataKuliah found = null;
                for(MataKuliah mk : semuaMK){
                    if(mk.getKodeMK().equalsIgnoreCase(kode)){
                        found = mk;
                        break;
                    }
                }
                if(found != null){
                    frs1.dropMK(found);
                } else {
                    System.out.println("Kode MK tidak ditemukan.");
                }
            }
            else if(pilihan == 4){
                frs1.printFRS();
            }
            else if(pilihan == 5){
                System.out.println("Terima kasih! Keluar dari sistem FRS.");
                break;
            }
            else{
                System.out.println("Pilihan tidak valid.");
            }
        }
        sc.close();
    }
}
```

## 2. Class Dosen

Class `Dosen` merepresentasikan data dosen dalam sistem akademik.

### Constructor
```java
public Dosen(String nama, String nip){
    this.nama = nama;
    this.nip = nip;
}
```
**Penjelasan:** Constructor ini digunakan untuk membuat objek dosen baru dengan menginisialisasi nama dan NIP.

### Method getNama()
```java
public String getNama(){
    return nama;
}
```
**Penjelasan:** Method getter untuk mengambil nilai atribut nama dosen. Mengembalikan string berisi nama dosen.

### Method getNip()
```java
public String getNip(){
    return nip;
}
```
**Penjelasan:** Method getter untuk mengambil nilai atribut NIP dosen. Mengembalikan string berisi Nomor Induk Pegawai dosen.

## 3. Class MataKuliah

Class `MataKuliah` merepresentasikan data mata kuliah yang ditawarkan dalam sistem akademik.

### Constructor
```java
public MataKuliah(String namaMK, String kodeMK, int sks, Dosen dosenPengampu, int maxKuota, int jumlahMhs, char kelas){
    this.namaMK = namaMK;
    this.kodeMK = kodeMK;
    this.sks = sks;
    this.dosenPengampu = dosenPengampu;
    this.maxKuota = maxKuota;
    this.kuota = maxKuota - jumlahMhs;
    this.jumlahMhs = jumlahMhs;
    this.kelas = kelas;
}
```
**Penjelasan:** Constructor untuk membuat objek mata kuliah dengan parameter lengkap termasuk menghitung kuota tersedia berdasarkan maksimal kuota dan jumlah mahasiswa yang sudah mendaftar.

### Method getNamaMK()
```java
public String getNamaMK(){
    return namaMK;
}
```
**Penjelasan:** Mengembalikan nama mata kuliah.

### Method getKodeMK()
```java
public String getKodeMK(){
    return kodeMK;
}
```
**Penjelasan:** Mengembalikan kode mata kuliah yang unik.

### Method getSks()
```java
public int getSks(){
    return sks;
}
```
**Penjelasan:** Mengembalikan jumlah SKS (Satuan Kredit Semester) untuk mata kuliah tersebut.

### Method getKuota()
```java
public int getKuota(){
    return kuota;
}
```
**Penjelasan:** Mengembalikan jumlah kuota yang masih tersedia untuk mata kuliah.

### Method maxKuota()
```java
public int maxKuota(){
    return maxKuota;
}
```
**Penjelasan:** Mengembalikan jumlah kuota maksimal yang diperbolehkan untuk mata kuliah.

### Method getKelas()
```java
public char getKelas(){
    return kelas;
}
```
**Penjelasan:** Mengembalikan karakter yang merepresentasikan kelas mata kuliah (A, B, C, dst).

### Method isFull()
```java
public boolean isFull(){
    return kuota <= 0;
}
```
**Penjelasan:** Mengecek apakah kuota mata kuliah sudah penuh. Mengembalikan `true` jika kuota ≤ 0, `false` jika masih tersedia.

### Method decreaseKuota()
```java
public void decreaseKuota(){
    if(kuota > 0){
        kuota--;
    }
}
```
**Penjelasan:** Mengurangi kuota tersedia sebanyak 1, dengan pengecekan bahwa kuota masih tersedia.

### Method jumlahMhs()
```java
public int jumlahMhs(){
    return jumlahMhs;
}
```
**Penjelasan:** Mengembalikan jumlah mahasiswa yang sudah terdaftar di mata kuliah.

### Method increaseJumlahMhs()
```java
public void increaseJumlahMhs(){
    jumlahMhs++;
}
```
**Penjelasan:** Menambah jumlah mahasiswa yang terdaftar sebanyak 1.

### Method getDosenPengampu()
```java
public String getDosenPengampu(){
    return dosenPengampu.getNama();
}
```
**Penjelasan:** Mengembalikan nama dosen pengampu mata kuliah dengan memanggil method getNama() dari objek Dosen.

## 4. Class Mahasiswa

Class `Mahasiswa` merepresentasikan data mahasiswa dalam sistem.

### Constructor
```java
public Mahasiswa(String nama, String nrp, Dosen doswal){
    this.nama = nama;
    this.nrp = nrp;
    this.doswal = doswal;
}
```
**Penjelasan:** Constructor untuk membuat objek mahasiswa dengan nama, NRP, dan dosen wali.

### Method getNama()
```java
public String getNama(){
    return nama;
}
```
**Penjelasan:** Mengembalikan nama mahasiswa.

### Method getNrp()
```java
public String getNrp(){
    return nrp;
}
```
**Penjelasan:** Mengembalikan NRP (Nomor Registrasi Pokok) mahasiswa.

### Method getDoswal()
```java
public String getDoswal(){
    return doswal.getNama();
}
```
**Penjelasan:** Mengembalikan nama dosen wali mahasiswa.

## 5. Class FRS

Class `FRS` merepresentasikan Formulir Rencana Studi yang berisi kumpulan mata kuliah yang diambil mahasiswa dalam satu semester.

### Constructor
```java
public FRS(Mahasiswa mahasiswa, String tahun, String semester, int maxSks){
    this.mahasiswa = mahasiswa;
    this.mataKuliahsList = new ArrayList<>();
    this.totalSks = 0;
    this.maxSks = maxSks;
    this.tahun = tahun;
    this.semester = semester;
}
```
**Penjelasan:** Constructor untuk membuat objek FRS baru dengan menginisialisasi daftar mata kuliah kosong, total SKS 0, dan parameter maksimal SKS.

### Method getMahasiswa()
```java
public Mahasiswa getMahasiswa(){
    return mahasiswa;
}
```
**Penjelasan:** Mengembalikan objek mahasiswa pemilik FRS.

### Method addMK()
```java
public boolean addMK(MataKuliah mk){
    if(totalSks + mk.getSks() > maxSks){
        System.out.println("Total SKS melebihi batas maksimum.");
        return false;
    }
    if(mk.isFull()){
        System.out.println("Kuota mata kuliah sudah penuh.");
        return false;
    }
    
    mataKuliahsList.add(mk);
    totalSks += mk.getSks();
    mk.decreaseKuota();
    mk.increaseJumlahMhs();
    System.out.println("Mata kuliah " + mk.getNamaMK() + " berhasil ditambahkan.");
    return true;
}
```
**Penjelasan:** Method untuk menambahkan mata kuliah ke FRS dengan dua pengecekan:
1. Apakah penambahan SKS tidak melebihi batas maksimal
2. Apakah kuota mata kuliah masih tersedia
Jika berhasil, akan menambah ke daftar, update total SKS, dan update kuota mata kuliah.

### Method dropMK()
```java
public boolean dropMK(MataKuliah mk){
    if(mataKuliahsList.remove(mk)){
        totalSks -= mk.getSks();
        System.out.println("Mata kuliah " + mk.getNamaMK() + " berhasil dihapus.");
        return true;
    } else {
        System.out.println("Mata kuliah " + mk.getNamaMK() + " tidak ditemukan dalam FRS.");
        return false;
    }
}
```
**Penjelasan:** Method untuk menghapus mata kuliah dari FRS. Mengecek apakah mata kuliah ada dalam daftar sebelum menghapus, kemudian mengurangi total SKS.

### Method printFRS()
```java
public void printFRS(){
    System.out.println("FRS Mahasiswa: " + mahasiswa.getNama() + " (" + mahasiswa.getNrp() + ")");
    System.out.println("Dosen Wali: " + mahasiswa.getDoswal());
    System.out.println("Tahun: " + tahun + ", Semester: " + semester);
    System.out.println("Mata Kuliah yang diambil:");
    for(MataKuliah mk : mataKuliahsList){
        System.out.println("- " + mk.getNamaMK() + " (" + mk.getKodeMK() + "), SKS: " + mk.getSks());
    }
    System.out.println("Total SKS: " + totalSks + "/" + maxSks);
}
```
**Penjelasan:** Method untuk menampilkan informasi lengkap FRS termasuk data mahasiswa, tahun akademik, daftar mata kuliah yang diambil, dan total SKS.

## 6. Class Main

Class `Main` berisi method utama yang menjalankan program dan menyediakan interface menu untuk pengguna.

### Method main()
```java
public static void main(String[] args){
    // Inisialisasi data dosen
    Dosen d1 = new Dosen("Prof. Ir. Ary Mazharuddin Shiddiqi, S.Kom., M.Comp.Sc., Ph.D., IPM.", "198106202005011001");
    // ... (inisialisasi dosen lainnya)
    
    // Inisialisasi mata kuliah
    MataKuliah mk1 = new MataKuliah("Pemrograman Web", "EF234301", 3, d3, 40, 35, 'C');
    // ... (inisialisasi mata kuliah lainnya)
    
    MataKuliah[] semuaMK = {mk1, mk2, mk3, /* ... */};
    
    // Inisialisasi mahasiswa
    Mahasiswa m1 = new Mahasiswa("Muhammad Quthbi Danish Abqori", "5025241036", d1);
    
    // Membuat FRS
    FRS frs1 = new FRS(m1, "2025/2026", "Gasal", 24);
    
    // Menu interaktif
    Scanner sc = new Scanner(System.in);
    while(true){
        System.out.println("\n=== MENU FRS ===");
        System.out.println("1. Lihat daftar mata kuliah");
        System.out.println("2. Tambah mata kuliah ke FRS");
        System.out.println("3. Hapus mata kuliah dari FRS");
        System.out.println("4. Lihat FRS mahasiswa");
        System.out.println("5. Keluar");
        System.out.print("Pilih menu: ");
        int pilihan = sc.nextInt();
        sc.nextLine();
        
        // Logika untuk setiap pilihan menu
        if(pilihan == 1){
            // Menampilkan daftar mata kuliah
        } 
        else if(pilihan == 2){
            // Menambah mata kuliah ke FRS
        }
        else if(pilihan == 3){
            // Menghapus mata kuliah dari FRS
        }
        else if(pilihan == 4){
            // Menampilkan FRS
        }
        else if(pilihan == 5){
            // Keluar dari program
        }
        else{
            System.out.println("Pilihan tidak valid.");
        }
    }
    sc.close();
}
```
**Penjelasan:** Method utama yang berisi:
- Inisialisasi data dummy (dosen, mata kuliah, mahasiswa)
- Pembuatan objek FRS
- Loop menu interaktif dengan 5 pilihan:
  1. Melihat daftar mata kuliah
  2. Menambah mata kuliah ke FRS
  3. Menghapus mata kuliah dari FRS
  4. Melihat FRS mahasiswa
  5. Keluar dari program

Program menggunakan `Scanner` untuk input pengguna dan menangani berbagai skenario seperti penambahan mata kuliah dengan pengecekan kuota dan batas SKS.

*Catatan: Saya juga sudah membuat versi FRS yang menggunakan GUI yang dapat dilihat di* [`FRSGUI.java`](/Pertemuan%205/FRSGUI/FRSGUI.java)

## 7. Contoh Output

|Gambar 1. Struktur hubungan antar class|
|-|
|![alt text](<Screenshot 2025-09-30 154233.png>)|


|Gambar 2. Contoh menampilkan daftar MK| Gambar 3. Contoh menambahkan MK ke FRS| Gambar 4. Contoh menghapus MK dari FRS|
|-|-|-|
|![alt text](<Screenshot 2025-09-30 154320.png>)|![alt text](<Screenshot 2025-09-30 154401.png>) |![alt text](<Screenshot 2025-09-30 154533.png>) |

---
###### Ditulis oleh **Muhammad Quthbi Danish Abqori - 5025241036**