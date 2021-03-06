package com.apap.tugas1apap.service;

import com.apap.tugas1apap.model.jabatanPegawaiModel;
import com.apap.tugas1apap.repository.instansiDB;
import com.apap.tugas1apap.repository.jabatanPegawaiDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class jabatanPegawaiServiceImpl implements jabatanPegawaiService {
    @Autowired
    private jabatanPegawaiDB jabatanPegawaiDB;

    @Override
    public Optional<List<jabatanPegawaiModel>> getJabatanByPegawaiId(String nip) {
        return jabatanPegawaiDB.findAllByPegawai_Nip(nip);
    }

    @Override
    public List<jabatanPegawaiModel> findAllByJabatan_Id(Long id){
        return jabatanPegawaiDB.findAllByJabatan_Id(id);
    }

    @Override
    public void add(jabatanPegawaiModel jabatanPegawai) {
        // TODO Auto-generated method stub
        jabatanPegawaiDB.save(jabatanPegawai);

    }
}
