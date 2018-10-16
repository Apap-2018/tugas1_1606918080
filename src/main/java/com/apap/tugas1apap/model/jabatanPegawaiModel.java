package com.apap.tugas1apap.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "jabatan_pegawai")
public class jabatanPegawaiModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_pegawai", referencedColumnName="id", nullable=false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private pegawaiModel pegawai;


    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "id_jabatan", referencedColumnName="id", nullable=false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private jabatanModel jabatan;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public pegawaiModel getPegawai() {
        return pegawai;
    }

    public void setPegawai(pegawaiModel pegawai) {
        this.pegawai = pegawai;
    }

    public jabatanModel getJabatan() {
        return jabatan;
    }

    public void setJabatan(jabatanModel jabatan) {
        this.jabatan = jabatan;
    }
}
