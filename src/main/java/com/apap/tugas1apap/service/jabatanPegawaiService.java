package com.apap.tugas1apap.service;

import com.apap.tugas1apap.model.jabatanPegawaiModel;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;

//@Configuration
//@EnableAutoConfiguration
//@ComponentScan
public interface jabatanPegawaiService {
    Optional<List<jabatanPegawaiModel>> getJabatanByPegawaiId(String nip);
    List<jabatanPegawaiModel> findAllByJabatan_Id(Long id);
}
