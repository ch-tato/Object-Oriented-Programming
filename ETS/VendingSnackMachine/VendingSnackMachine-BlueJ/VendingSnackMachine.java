
/**
 * Write a description of class VendingSnackMachine here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.Scanner;
public class VendingSnackMachine
{
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