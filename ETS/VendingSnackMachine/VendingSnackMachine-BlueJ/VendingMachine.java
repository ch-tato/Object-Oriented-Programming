
/**
 * Write a description of class VendingMachine here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.ArrayList;
import java.util.Scanner;
public class VendingMachine
{
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