package com.apap.tugas1apap.service;

import com.apap.tugas1apap.model.jabatanModel;

import java.util.List;
import java.util.Optional;

public interface jabatanService {
    Optional<jabatanModel> getJabatanById(Long id);

    void addJabatan(jabatanModel jabatan);

    void deleteJabatan(jabatanModel jabatan);

    List<jabatanModel> getAll();

    void updateJabatan(Long id, jabatanModel jabatan);

    jabatanModel findById(Long id);
}
