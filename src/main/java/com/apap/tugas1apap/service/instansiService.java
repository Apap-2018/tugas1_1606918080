package com.apap.tugas1apap.service;

import com.apap.tugas1apap.model.instansiModel;

import java.util.List;
import java.util.Optional;

public interface instansiService {
    Optional<instansiModel> getInstansiById(long idInstansi);

    List<instansiModel> getAllInstansi();

    instansiModel findById(Long id);
}
