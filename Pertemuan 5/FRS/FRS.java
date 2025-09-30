
/**
 * Write a description of class FRS here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.util.ArrayList;
import java.util.List;
public class FRS{
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