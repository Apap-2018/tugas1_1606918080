package com.apap.tugas1apap.service;

import com.apap.tugas1apap.model.provinsiModel;
import com.apap.tugas1apap.repository.provinsiDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class provinsiServiceImpl implements provinsiService {
    @Autowired
    private provinsiDB provinsiDb;

    @Override
    public provinsiModel findById(Long id) {
        // TODO Auto-generated method stub

        return provinsiDb.findById(id).get();
    }

    @Override
    public List<provinsiModel> findAll() {
        // TODO Auto-generated method stub
        return provinsiDb.findAll();
    }
}
