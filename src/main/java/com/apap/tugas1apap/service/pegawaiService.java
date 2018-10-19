package com.apap.tugas1apap.service;

import com.apap.tugas1apap.model.instansiModel;
import com.apap.tugas1apap.model.pegawaiModel;
import java.util.List;

public interface pegawaiService {
    pegawaiModel getPegawaiDetailByNip(String nip);
    void addPegawai(pegawaiModel pegawai);
    List<pegawaiModel> getAllPegawai();
    double getGajiLengkapByNip(String nip);
    List<pegawaiModel> findByInstansiOrderByTanggalLahirAsc(instansiModel instansi);
}
