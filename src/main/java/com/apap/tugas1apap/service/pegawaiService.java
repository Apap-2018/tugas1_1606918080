package com.apap.tugas1apap.service;

import com.apap.tugas1apap.model.instansiModel;
import com.apap.tugas1apap.model.jabatanModel;
import com.apap.tugas1apap.model.pegawaiModel;
import java.util.List;

public interface pegawaiService {
    pegawaiModel getPegawaiDetailByNip(String nip);
    void addPegawai(pegawaiModel pegawai);
    List<pegawaiModel> getAllPegawai();
    double getGajiLengkapByNip(String nip);
    List<pegawaiModel> findByInstansiOrderByTanggalLahirAsc(instansiModel instansi);
    String makeNip(instansiModel instansi, pegawaiModel pegawai);

    void delete(pegawaiModel pegawai);

    void update(pegawaiModel pegawaiUpdate, pegawaiModel pegawaiBefore);

    List<pegawaiModel> findByInstansiAndJabatan(instansiModel instansi, jabatanModel jabatan);

    List<pegawaiModel> findByInstansi(instansiModel instansi);

    List<pegawaiModel> findAll();
}
