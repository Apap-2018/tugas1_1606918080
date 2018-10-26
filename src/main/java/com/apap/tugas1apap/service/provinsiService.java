package com.apap.tugas1apap.service;

import com.apap.tugas1apap.model.provinsiModel;

import java.util.List;

public interface provinsiService {
    provinsiModel findById(Long id);
    List<provinsiModel> findAll();
}
