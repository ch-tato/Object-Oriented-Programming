
/**
 * Write a description of class Student here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Student
{
    private String nrp;
    private String name;
    private String major;

    public Student(String nrp, String name, String major) {
        this.nrp = nrp;
        this.name = name;
        this.major = major;
    }

    public String getNim() {
        return nrp;
    }

    public String getName() {
        return name;
    }

    public String getMajor() {
        return major;
    }

    @Override
    public String toString() {
        return "NRP: " + nrp + " | Nama: " + name + " | Jurusan: " + major;
    }
}