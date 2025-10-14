import java.util.ArrayList;
import java.util.Scanner;

class Snack{
    private String name;
    private int price;
    private int stock;

    public Snack(String name, int price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Nama: " + name + "\t| Harga: " + price + "\t| Stok: " + stock;
    }
}

class VendingMachine {
    private ArrayList<Snack> snacks;
    private int balance;
    private ArrayList<String> log;
    private Scanner sc = new Scanner(System.in);
    private int total;

    public VendingMachine() {
        snacks = new ArrayList<>();
        balance = 0;
        log = new ArrayList<>();
    }

    public int getBalance() {
        return balance;
    }

    public void addSnack(Snack snack) {
        snacks.add(snack);
    }

    public void displaySnacks() {
        System.out.println("\nDaftar Snack:");
        for (int i = 0; i < snacks.size(); i++) {
            System.out.println((i + 1) + ". " + snacks.get(i));
        }
    }

    public void insertMoney(int amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Saldo saat ini: " + balance);
        } else {
            System.out.println("Jumlah uang tidak valid.");
        }
    }

    public void selectSnack(int index) {
        if (index < 1 || index > snacks.size()) {
            System.out.println("Pilihan tidak valid.");
            return;
        }

        Snack selectedSnack = snacks.get(index - 1);
        if (selectedSnack.getStock() <= 0) {
            System.out.println("Maaf, snack ini habis. Admin akan mengisinya.");
            return;
        }

        System.out.print("Masukkan total snack: ");
        total = sc.nextInt();
        if (total > selectedSnack.getStock()) {
            System.out.println("Stok tidak cukup. Stok tersedia: " + selectedSnack.getStock());
            return;
        }
        if (balance < selectedSnack.getPrice() * total) {
            System.out.println("Saldo Anda tidak cukup.");
            return;
        }
        balance -= selectedSnack.getPrice() * total;
        selectedSnack.setStock(selectedSnack.getStock() - total);
        System.out.println("Anda membeli: " + total + " " + selectedSnack.getName());
        System.out.println("Sisa saldo: " + balance);
        log.add("Membeli " + total + " " + selectedSnack.getName() + " seharga " + selectedSnack.getPrice() + " (total: " + (selectedSnack.getPrice() * total) + ")");
    }

    public void returnChange() {
        if (balance > 0) {
            System.out.println("Kembalian Anda: " + balance);
            balance = 0;
        } else {
            System.out.println("Tidak ada saldo untuk dikembalikan.");
        }
    }

    public void displayLog() {
        if(log.isEmpty()){
            System.out.println("Belum ada transaksi.");
            return;
        }
        System.out.println("\nLog Transaksi:");
        for (String entry : log) {
            System.out.println(entry);
        }
    }
}

public class VendingSnackMachine {
    public static void main(String[] args){
        VendingMachine vm = new VendingMachine();
        Scanner sc = new Scanner(System.in);

        vm.addSnack(new Snack("Chitato", 5000, 10));
        vm.addSnack(new Snack("Snickers", 7000, 5));
        vm.addSnack(new Snack("RedBull", 6000, 8));
        vm.addSnack(new Snack("Chocolattos", 3000, 15));
        vm.addSnack(new Snack("BangBang", 4000, 12));
        vm.addSnack(new Snack("Susu Milo", 8000, 7));

        int choice;
        do {
            System.out.println("\n=== Mesin Snack ===");
            System.out.println("1. Tampilkan Snack");
            System.out.println("2. Tampilkan Uang");
            System.out.println("3. Masukkan Uang");
            System.out.println("4. Pilih Snack");
            System.out.println("5. Kembalikan Uang");
            System.out.println("6. Lihat Log Transaksi");
            System.out.println("7. Keluar");
            System.out.print("Pilih menu: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    vm.displaySnacks();
                    break;
                case 2:
                    System.out.println("Saldo saat ini: " + vm.getBalance());
                    break;
                case 3:
                    System.out.print("Masukkan jumlah uang: ");
                    int amount = sc.nextInt();
                    vm.insertMoney(amount);
                    break;
                case 4:
                    vm.displaySnacks();
                    System.out.print("Pilih nomor snack: ");
                    int snackIndex = sc.nextInt();
                    vm.selectSnack(snackIndex);
                    break;
                case 5:
                    vm.returnChange();
                    break;
                case 6:
                    vm.displayLog();
                    break;
                case 7:
                    System.out.println("Terima kasih telah menggunakan mesin snack!");
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        } while (choice != 7);
        sc.close();
    }
}
