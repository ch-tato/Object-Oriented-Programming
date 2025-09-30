import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Dosen {
    private String nama;
    private String nip;
    
    public Dosen(String nama, String nip) {
        this.nama = nama;
        this.nip = nip;
    }
    
    public String getNama() { return nama; }
    public String getNip() { return nip; }
}

class MataKuliah {
    private String namaMK;
    private String kodeMK;
    private int sks;
    private Dosen dosenPengampu;
    private int kuota;
    private int maxKuota;
    private int jumlahMhs;
    private char kelas;
    
    public MataKuliah(String namaMK, String kodeMK, int sks, Dosen dosenPengampu, int maxKuota, int jumlahMhs, char kelas) {
        this.namaMK = namaMK;
        this.kodeMK = kodeMK;
        this.sks = sks;
        this.dosenPengampu = dosenPengampu;
        this.maxKuota = maxKuota;
        this.kuota = maxKuota - jumlahMhs;
        this.jumlahMhs = jumlahMhs;
        this.kelas = kelas;
    }
    
    public String getNamaMK() { return namaMK; }
    public String getKodeMK() { return kodeMK; }
    public int getSks() { return sks; }
    public int getKuota() { return kuota; }
    public int getMaxKuota() { return maxKuota; }
    public char getKelas() { return kelas; }
    public boolean isFull() { return kuota <= 0; }
    public void decreaseKuota() { if(kuota > 0) kuota--; }
    public void increaseKuota() { kuota++; }
    public int getJumlahMhs() { return jumlahMhs; }
    public void increaseJumlahMhs() { jumlahMhs++; }
    public void decreaseJumlahMhs() { if(jumlahMhs > 0) jumlahMhs--; }
    public String getDosenPengampu() { return dosenPengampu.getNama(); }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        MataKuliah that = (MataKuliah) obj;
        return kodeMK.equals(that.kodeMK);
    }
}

class Mahasiswa {
    private String nama;
    private String nrp;
    private Dosen doswal;
    
    public Mahasiswa(String nama, String nrp, Dosen doswal) {
        this.nama = nama;
        this.nrp = nrp;
        this.doswal = doswal;
    }
    
    public String getNama() { return nama; }
    public String getNrp() { return nrp; }
    public String getDoswal() { return doswal.getNama(); }
}

class FRS {
    private Mahasiswa mahasiswa;
    private List<MataKuliah> mataKuliahsList;
    private int totalSks;
    private int maxSks;
    private String tahun;
    private String semester;
    
    public FRS(Mahasiswa mahasiswa, String tahun, String semester, int maxSks) {
        this.mahasiswa = mahasiswa;
        this.mataKuliahsList = new ArrayList<>();
        this.totalSks = 0;
        this.maxSks = maxSks;
        this.tahun = tahun;
        this.semester = semester;
    }

    public Mahasiswa getMahasiswa() { return mahasiswa; }
    public List<MataKuliah> getMataKuliahsList() { return mataKuliahsList; }
    public int getTotalSks() { return totalSks; }
    public int getMaxSks() { return maxSks; }
    public String getTahun() { return tahun; }
    public String getSemester() { return semester; }

    public boolean addMK(MataKuliah mk) {
        if(totalSks + mk.getSks() > maxSks) {
            JOptionPane.showMessageDialog(null, "Total SKS melebihi batas maksimum.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(mk.isFull()) {
            JOptionPane.showMessageDialog(null, "Kuota mata kuliah sudah penuh.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(mataKuliahsList.contains(mk)) {
            JOptionPane.showMessageDialog(null, "Mata kuliah sudah ada dalam FRS.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        mataKuliahsList.add(mk);
        totalSks += mk.getSks();
        mk.decreaseKuota();
        mk.increaseJumlahMhs();
        JOptionPane.showMessageDialog(null, "Mata kuliah " + mk.getNamaMK() + " berhasil ditambahkan.", "Success", JOptionPane.INFORMATION_MESSAGE);
        return true;
    }

    public boolean dropMK(MataKuliah mk) {
        if(mataKuliahsList.remove(mk)) {
            totalSks -= mk.getSks();
            mk.increaseKuota();
            mk.decreaseJumlahMhs();
            JOptionPane.showMessageDialog(null, "Mata kuliah " + mk.getNamaMK() + " berhasil dihapus.", "Success", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Mata kuliah " + mk.getNamaMK() + " tidak ditemukan dalam FRS.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}

public class FRSGUI {
    private JFrame frame;
    private JTable tableMataKuliah;
    private JTable tableFRS;
    private DefaultTableModel modelMataKuliah;
    private DefaultTableModel modelFRS;
    private FRS frs;
    private MataKuliah[] semuaMK;
    private JPanel panelFRS; // Deklarasi panelFRS sebagai field class
    
    public FRSGUI() {
        initializeData();
        initializeGUI();
    }
    
    private void initializeData() {
        Dosen d1 = new Dosen("Prof. Ir. Ary Mazharuddin Shiddiqi", "198106202005011001");
        Dosen d2 = new Dosen("Fajar Baskoro, S.Kom., M.T.", "198106202005011002");

        MataKuliah mk1 = new MataKuliah("Pemrograman Web", "EF234301", 3, d1, 40, 35, 'C');
        MataKuliah mk2 = new MataKuliah("PBO", "EF234302", 3, d2, 40, 38, 'B');
        MataKuliah mk3 = new MataKuliah("Basis Data", "EF234303", 3, d1, 35, 30, 'A');
        MataKuliah mk4 = new MataKuliah("Jaringan Komputer", "EF234304", 2, d2, 30, 25, 'B');
        
        semuaMK = new MataKuliah[]{mk1, mk2, mk3, mk4};

        Mahasiswa m1 = new Mahasiswa("Muhammad Quthbi Danish Abqori", "5025241036", d1);
        frs = new FRS(m1, "2025/2026", "Gasal", 24);
    }
    
    private void initializeGUI() {
        frame = new JFrame("Sistem FRS - Formulir Rencana Studi");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLayout(new BorderLayout());
        
        // Panel header dengan info mahasiswa
        JPanel panelHeader = new JPanel(new GridLayout(4, 2, 5, 5));
        panelHeader.setBorder(BorderFactory.createTitledBorder("Informasi Mahasiswa"));
        panelHeader.add(new JLabel("Nama:"));
        panelHeader.add(new JLabel(frs.getMahasiswa().getNama()));
        panelHeader.add(new JLabel("NRP:"));
        panelHeader.add(new JLabel(frs.getMahasiswa().getNrp()));
        panelHeader.add(new JLabel("Dosen Wali:"));
        panelHeader.add(new JLabel(frs.getMahasiswa().getDoswal()));
        panelHeader.add(new JLabel("Tahun/Semester:"));
        panelHeader.add(new JLabel(frs.getTahun() + " / " + frs.getSemester()));
        
        // Panel untuk daftar mata kuliah tersedia
        JPanel panelMataKuliah = new JPanel(new BorderLayout());
        panelMataKuliah.setBorder(BorderFactory.createTitledBorder("Daftar Mata Kuliah Tersedia"));
        
        String[] columnNames = {"Kode MK", "Nama Mata Kuliah", "SKS", "Dosen Pengampu", "Kelas", "Kuota Tersedia"};
        modelMataKuliah = new DefaultTableModel(columnNames, 0);
        tableMataKuliah = new JTable(modelMataKuliah);
        updateTableMataKuliah();
        
        JScrollPane scrollMataKuliah = new JScrollPane(tableMataKuliah);
        panelMataKuliah.add(scrollMataKuliah, BorderLayout.CENTER);
        
        // Panel untuk FRS mahasiswa
        panelFRS = new JPanel(new BorderLayout()); // Inisialisasi panelFRS
        panelFRS.setBorder(BorderFactory.createTitledBorder("FRS Mahasiswa - Total SKS: " + frs.getTotalSks() + "/" + frs.getMaxSks()));
        
        String[] columnNamesFRS = {"Kode MK", "Nama Mata Kuliah", "SKS", "Dosen Pengampu", "Kelas"};
        modelFRS = new DefaultTableModel(columnNamesFRS, 0);
        tableFRS = new JTable(modelFRS);
        
        JScrollPane scrollFRS = new JScrollPane(tableFRS);
        panelFRS.add(scrollFRS, BorderLayout.CENTER);
        
        // Panel untuk tombol-tombol
        JPanel panelButtons = new JPanel(new FlowLayout());
        JButton btnTambah = new JButton("Tambah Mata Kuliah");
        JButton btnHapus = new JButton("Hapus Mata Kuliah");
        JButton btnRefresh = new JButton("Refresh");
        JButton btnKeluar = new JButton("Keluar");
        
        panelButtons.add(btnTambah);
        panelButtons.add(btnHapus);
        panelButtons.add(btnRefresh);
        panelButtons.add(btnKeluar);
        
        // Action listeners
        btnTambah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tambahMataKuliah();
            }
        });
        
        btnHapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hapusMataKuliah();
            }
        });
        
        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshData();
            }
        });
        
        btnKeluar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(frame, 
                    "Apakah Anda yakin ingin keluar?", "Konfirmasi Keluar", 
                    JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        
        // Split pane untuk membagi layar
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelMataKuliah, panelFRS);
        splitPane.setResizeWeight(0.5);
        
        // Menambahkan komponen ke frame
        frame.add(panelHeader, BorderLayout.NORTH);
        frame.add(splitPane, BorderLayout.CENTER);
        frame.add(panelButtons, BorderLayout.SOUTH);
        
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        // Update table FRS setelah GUI selesai diinisialisasi
        updateTableFRS();
    }
    
    private void updateTableMataKuliah() {
        modelMataKuliah.setRowCount(0);
        for (MataKuliah mk : semuaMK) {
            Object[] rowData = {
                mk.getKodeMK(),
                mk.getNamaMK(),
                mk.getSks(),
                mk.getDosenPengampu(),
                mk.getKelas(),
                mk.getKuota() + "/" + mk.getMaxKuota()
            };
            modelMataKuliah.addRow(rowData);
        }
    }
    
    private void updateTableFRS() {
        modelFRS.setRowCount(0);
        for (MataKuliah mk : frs.getMataKuliahsList()) {
            Object[] rowData = {
                mk.getKodeMK(),
                mk.getNamaMK(),
                mk.getSks(),
                mk.getDosenPengampu(),
                mk.getKelas()
            };
            modelFRS.addRow(rowData);
        }
        
        // Update judul panel FRS dengan total SKS terbaru
        // Gunakan referensi langsung ke panelFRS yang sudah disimpan
        panelFRS.setBorder(BorderFactory.createTitledBorder(
            "FRS Mahasiswa - Total SKS: " + frs.getTotalSks() + "/" + frs.getMaxSks()));
    }
    
    private void tambahMataKuliah() {
        int selectedRow = tableMataKuliah.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Pilih mata kuliah yang ingin ditambahkan.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        MataKuliah mk = semuaMK[selectedRow];
        boolean success = frs.addMK(mk);
        if (success) {
            refreshData();
        }
    }
    
    private void hapusMataKuliah() {
        int selectedRow = tableFRS.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Pilih mata kuliah yang ingin dihapus dari FRS.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String kodeMK = (String) modelFRS.getValueAt(selectedRow, 0);
        MataKuliah mkToRemove = null;
        
        for (MataKuliah mk : semuaMK) {
            if (mk.getKodeMK().equals(kodeMK)) {
                mkToRemove = mk;
                break;
            }
        }
        
        if (mkToRemove != null) {
            boolean success = frs.dropMK(mkToRemove);
            if (success) {
                refreshData();
            }
        }
    }
    
    private void refreshData() {
        updateTableMataKuliah();
        updateTableFRS();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FRSGUI();
            }
        });
    }
}