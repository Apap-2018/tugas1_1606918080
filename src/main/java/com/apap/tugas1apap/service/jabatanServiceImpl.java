package com.apap.tugas1apap.service;

import com.apap.tugas1apap.model.jabatanModel;
import com.apap.tugas1apap.repository.jabatanDB;
import com.apap.tugas1apap.repository.jabatanPegawaiDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class jabatanServiceImpl implements jabatanService {
    @Autowired
    private jabatanDB jabatanDB;
    @Autowired
    private jabatanPegawaiDB jabatanPegawaiDB;

    public Optional<jabatanModel> getJabatanById(Long id){
        return jabatanDB.findById(id);
    }

    public void updateJabatan(Long id, jabatanModel jabatan){
        jabatanModel jabatanUpdated = jabatanDB.getOne(id);
        jabatanUpdated.setDeskripsi(jabatan.getDeskripsi());
        jabatanUpdated.setGajiPokok(jabatan.getGajiPokok());
        jabatanUpdated.setId(jabatan.getId());
        jabatanUpdated.setNama(jabatan.getNama());
        jabatanDB.save(jabatanUpdated);
    }

    public void addJabatan(jabatanModel jabatan){
        jabatanDB.save(jabatan);
    }

    public void deleteJabatan(jabatanModel jabatan){
        jabatanDB.deleteById(jabatan.getId());
    }

    public List<jabatanModel> getAll(){
        return jabatanDB.findAll();
    }

    @Override
    public jabatanModel findById(Long id) {
        // TODO Auto-generated method stub
        return jabatanDB.findById(id).get();
    }
}
