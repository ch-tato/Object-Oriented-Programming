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