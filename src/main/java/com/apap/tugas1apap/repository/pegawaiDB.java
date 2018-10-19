package com.apap.tugas1apap.repository;

import com.apap.tugas1apap.model.instansiModel;
import com.apap.tugas1apap.model.pegawaiModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface    pegawaiDB extends JpaRepository<pegawaiModel, Long> {
    pegawaiModel findByNip(String nip);
    List <pegawaiModel> findByInstansiOrderByTanggalLahirAsc(instansiModel instansi);
}
