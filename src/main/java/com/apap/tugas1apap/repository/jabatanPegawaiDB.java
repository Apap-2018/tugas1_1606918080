package com.apap.tugas1apap.repository;

import com.apap.tugas1apap.model.jabatanPegawaiModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
@Repository
public interface jabatanPegawaiDB extends JpaRepository<jabatanPegawaiModel, Long> {
    Optional<List<jabatanPegawaiModel>> findAllByPegawai_Nip(String nip);
}
