package com.apap.tugas1apap.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "jabatan")
public class jabatanModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "nama", nullable = false)
    private String nama;

    @NotNull
    @Size(max = 255)
    @Column(name = "deskripsi", nullable = false)
    private String deskripsi;

    @NotNull
    @Column(name = "gaji_pokok", nullable = false)
    private double gajiPokok;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "jabatanList")
    private List<pegawaiModel> pegawaiList;

    @OneToMany(mappedBy = "jabatan", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<jabatanPegawaiModel> jabatanPegawaiList;

    public List<jabatanPegawaiModel> getJabatanPegawaiList() {
        return jabatanPegawaiList;
    }

    public void setJabatanPegawaiList(List<jabatanPegawaiModel> jabatanPegawaiList) {
        this.jabatanPegawaiList = jabatanPegawaiList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public double getGajiPokok() {
        return gajiPokok;
    }

    public void setGajiPokok(double gajiPokok) {
        this.gajiPokok = gajiPokok;
    }

    public List<pegawaiModel> getPegawaiList() {
        return pegawaiList;
    }

    public void setPegawaiList(List<pegawaiModel> pegawaiList) {
        this.pegawaiList = pegawaiList;
    }
}
