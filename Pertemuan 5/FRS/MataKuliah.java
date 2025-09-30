
/**
 * Write a description of class MataKuliah here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class MataKuliah{
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