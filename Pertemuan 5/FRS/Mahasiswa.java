
/**
 * Write a description of class Mahasiswa here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Mahasiswa{
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