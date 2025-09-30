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