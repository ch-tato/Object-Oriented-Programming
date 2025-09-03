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