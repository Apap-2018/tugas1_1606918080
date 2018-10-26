package com.apap.tugas1apap.service;

import com.apap.tugas1apap.model.instansiModel;
import com.apap.tugas1apap.repository.instansiDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class instansiServiceImpl implements instansiService {
    @Autowired
    private instansiDB instansiDB;

    public Optional<instansiModel> getInstansiById(long idInstansi){
        return instansiDB.findById(idInstansi);
    }

    public List<instansiModel> getAllInstansi(){
        return instansiDB.findAll();
    }

    @Override
    public instansiModel findById(Long id) {
        // TODO Auto-generated method stub
        return instansiDB.findById(id).get();
    }
}
