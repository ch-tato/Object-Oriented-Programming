###### Selasa, 7 Oktober 2025
### Pertemuan 7
---
<h1><b>JarkomSupport.java</b></h1>

---

## Daftar Isi

0. [Pendahuluan](#pendahuluan)
1. [Source Code](#source-code)
2. [Penjelasan Tiap Variabel dan Method](#penjelasan-tiap-variable-dan-method)
3. [Contoh Output](#contoh-output)
4. [Kesimpulan](#kesimpulan)

---

## 0. Pendahuluan

Program **Jarkom Support Bot** merupakan aplikasi chatbot sederhana berbasis teks yang dirancang untuk membantu pengguna dalam menyelesaikan permasalahan umum yang sering terjadi pada **praktikum Jaringan Komputer (Jarkom)** — khususnya ketika menggunakan GNS3.
Program ini meniru perilaku asisten teknis yang dapat memberikan **saran, diagnosis awal, dan petunjuk troubleshooting** berdasarkan *kata kunci* yang ditemukan dalam input pengguna.

**Ide utama cara kerja program ini:**
1. Program menyimpan kumpulan pasangan **kata kunci → respon** dalam struktur data `HashMap`.
2. Ketika pengguna mengetik kalimat, sistem akan **mengubah kalimat menjadi huruf kecil** (`toLowerCase`) agar pencarian tidak sensitif terhadap kapital.
3. Program memeriksa apakah kalimat tersebut **mengandung kata atau frasa kunci** yang ada di `Map`.
4. Jika ditemukan, bot akan menampilkan respon yang sesuai dengan masalah jaringan tersebut.
5. Jika tidak ada kata kunci yang cocok, maka bot akan memberikan **respon generik** berupa saran umum yang relevan dengan troubleshooting jaringan.

Dengan mekanisme ini, bot berfungsi layaknya *asisten laboratorium virtual* yang bisa membantu mahasiswa memahami sumber masalah dasar pada konfigurasi jaringan.

---

## 1. Source Code

```java
import java.util.Map;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class JarkomSupport {
    private Map<String, String> responses;
    private Random random;
    public JarkomSupport() {
        responses = new HashMap<>();
        random = new Random();

        responses.put("ping", "Pastikan device bisa resolve DNS dan route-nya benar. Coba ping IP langsung (misal 8.8.8.8) dulu untuk memastikan konektivitas layer 3.");
        responses.put("dns", "Cek konfigurasi DNS di /etc/resolv.conf (Linux) atau ipconfig /all (Windows). Pastikan alamat DNS-nya benar.");
        responses.put("nat", "Pastikan NAT diaktifkan di router yang terhubung ke internet. Cek juga apakah interface mengarah ke NAT sudah diatur sebagai 'ip nat outside'.");
        responses.put("subnet", "Pastikan routing antar subnet sudah dikonfigurasi dengan benar dan setiap router tahu network lain lewat static route atau dynamic routing.");
        responses.put("dhcp", "Cek apakah DHCP server aktif dan interface-nya sudah sesuai. Gunakan 'show ip dhcp binding' atau 'ipconfig /renew' untuk debugging.");
        responses.put("error", "Apakah ada pesan error spesifik? Coba tampilkan output 'show interface' atau 'ping detail' untuk analisis lebih lanjut.");
        responses.put("lambat", "Coba lihat penggunaan CPU dan RAM di GNS3, atau mungkin link antar node terlalu banyak menyebabkan delay.");
        responses.put("route", "Gunakan perintah 'show ip route' untuk memastikan route ke tujuan sudah ada. Jika tidak, tambahkan static route.");
        responses.put("icmp", "Kalau ICMP tidak diterima, bisa jadi ada ACL (Access Control List) atau firewall yang memblokir ping.");
        responses.put("firewall", "Cek aturan firewall di router atau OS. Pastikan port yang diperlukan tidak diblokir.");
        
        responses.put("ga bisa ping google", 
            "Coba cek apakah DNS bisa resolve (ping 8.8.8.8 dulu). Jika 8.8.8.8 bisa diping tapi google.com tidak, maka masalah ada di DNS.");
        responses.put("ga bisa ping 8.8.8.8", 
            "Periksa koneksi NAT atau gateway default. Bisa jadi router belum diarahkan keluar ke internet.");
        responses.put("ping antar subnet gagal", 
            "Pastikan routing antar subnet dikonfigurasi. Coba tambahkan static route atau aktifkan dynamic routing seperti RIP atau OSPF.");
        responses.put("dhcp gak jalan", 
            "Periksa apakah interface DHCP server berada di jaringan yang sama dengan client. Kalau lewat router, aktifkan DHCP relay (ip helper-address).");
        responses.put("ga bisa kirim dhcp packet", 
            "Mungkin interface belum diatur dengan benar atau diblokir firewall. Cek juga apakah DHCP server mendengarkan pada interface yang benar.");
        responses.put("ga bisa terima dhcp packet", 
            "Coba cek apakah broadcast packet diizinkan lewat jaringan dan DHCP relay sudah diatur di router.");
        responses.put("node lain ga bisa ping google", 
            "Router mungkin belum meneruskan paket keluar (NAT tidak diatur untuk subnet lain). Pastikan semua subnet disertakan dalam NAT overload configuration.");
    }

    public String getResponse(String userInput) {
        String input = userInput.toLowerCase();
        for (String key : responses.keySet()) {
            if (input.contains(key)) {
                return responses.get(key);
            }
        }
        String[] generic = {
            "Bisa jelaskan topologinya dulu? Kadang masalah ada di router tengahnya.",
            "Coba kasih tahu langkah-langkah yang udah kamu lakukan.",
            "Apakah semua interface sudah up dan punya IP address?",
            "Hmm... bisa jadi ada masalah routing. Sudah cek tabel routing?",
            "Coba cek konfigurasi jaringan dan pastikan semua interface sudah aktif.",
            "Pastikan semua perangkat dalam jaringan bisa saling terhubung.",
            "Coba restart perangkat jaringan atau GNS3 untuk menyegarkan koneksi.",
            "Periksa apakah ada konflik IP di jaringan.",
            "Coba gunakan perintah traceroute untuk melihat jalur paket."
        };
        return generic[random.nextInt(generic.length)];
    }

    public static void main(String[] args) {
        JarkomSupport bot = new JarkomSupport();
        Scanner sc = new Scanner(System.in);
        System.out.println("Selamat datang di Jarkom Support Bot! Ketik 'exit' untuk keluar.");
        while (true) {
            System.out.print("Anda: ");
            String userInput = sc.nextLine();
            if (userInput.equalsIgnoreCase("exit")) {
                break;
            }
            String response = bot.getResponse(userInput);
            System.out.println("Bot: " + response);
        }
        sc.close();
    }
}
```

---

## 2. Penjelasan Tiap Variabel dan Method

### **Variabel:**

- `private Map<String, String> responses;`
  Menyimpan pasangan kata kunci dan jawaban yang sesuai. Menggunakan `HashMap` agar pencarian data berlangsung cepat dan efisien.

- `private Random random;`
  Digunakan untuk memilih jawaban acak dari kumpulan respon generik ketika tidak ada kata kunci yang cocok dengan input pengguna.

---

### ⚙️ **Constructor: `public JarkomSupport()`**

* Bertugas untuk **menginisialisasi data awal** chatbot.
* Di dalamnya dibuat objek `HashMap` baru dan objek `Random`.
* Konstruktor juga memuat daftar kata kunci beserta respon yang umum digunakan di konteks jaringan komputer, seperti:

  * *ping*, *dns*, *nat*, *subnet*, *dhcp*, *route*, *firewall*, dan lain-lain.
* Setiap kata kunci memiliki pesan yang membantu pengguna melakukan langkah-langkah pengecekan jaringan sesuai permasalahannya.

---

### **Method: `public String getResponse(String userInput)`**

* Bertugas menentukan respon yang akan diberikan berdasarkan input pengguna.
* Langkah-langkah yang dilakukan:

  1. Mengubah input menjadi huruf kecil agar tidak sensitif terhadap kapitalisasi.
  2. Mengecek setiap kunci dalam `responses`. Jika `input` mengandung kata/frasa tertentu (`input.contains(key)`), maka nilai dari `responses` akan dikembalikan sebagai respon.
  3. Jika tidak ada yang cocok, maka dipilih salah satu respon acak dari array `generic` yang berisi saran troubleshooting umum, seperti memeriksa IP, routing, atau konfigurasi interface.

---

### **Method: `public static void main(String[] args)`**

* Merupakan titik masuk utama program.
* Langkah-langkah yang dilakukan:

  1. Membuat objek `JarkomSupport` bernama `bot`.
  2. Menginisialisasi objek `Scanner` untuk menerima input pengguna dari keyboard.
  3. Menampilkan pesan pembuka dan instruksi keluar (`exit`).
  4. Menjalankan **loop `while (true)`** untuk terus menerima input pengguna.
  5. Jika pengguna mengetik “exit”, loop berhenti dan program berakhir.
  6. Jika tidak, maka `bot.getResponse(userInput)` dipanggil untuk mendapatkan jawaban yang sesuai.
  7. Menampilkan respon bot ke layar dengan format percakapan.

---

## 3. Contoh Output

|Gambar 1 Contoh Output|
|-|
||

---

## 4. Kesimpulan

Program **Jarkom Support Bot** menunjukkan implementasi sederhana namun efektif dari konsep *keyword-based chatbot* menggunakan bahasa Java.
Dengan memanfaatkan struktur data `HashMap`, program ini dapat dengan cepat mencocokkan input pengguna dengan respon yang relevan.

Selain itu, penggunaan `Random` untuk memberikan respon acak menjadikan interaksi terasa lebih alami.

Secara keseluruhan, program ini merupakan **alat bantu belajar dan troubleshooting yang efisien** bagi mahasiswa jaringan komputer dalam memahami permasalahan dasar konfigurasi dan konektivitas jaringan saat praktikum.

---
###### Ditulis oleh **Muhammad Quthbi Danish Abqori - 5025241036**