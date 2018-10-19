package com.apap.tugas1apap.service;

import com.apap.tugas1apap.model.instansiModel;
import com.apap.tugas1apap.model.jabatanModel;
import com.apap.tugas1apap.model.pegawaiModel;
import com.apap.tugas1apap.repository.pegawaiDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class pegawaiServiceImpl implements pegawaiService {
    @Autowired
    private pegawaiDB pegawaiDB;

    @Override
    public pegawaiModel getPegawaiDetailByNip(String nip) {
        return pegawaiDB.findByNip(nip);
    }


    @Override
    public void addPegawai(pegawaiModel pegawai){
        pegawaiDB.save(pegawai);
    }

    @Override
    public List<pegawaiModel> getAllPegawai(){
        return pegawaiDB.findAll();
    }

    @Override
    public double getGajiLengkapByNip(String nip) {
        double gajiLengkap = 0;
        pegawaiModel pegawai = this.getPegawaiDetailByNip(nip);
        double gajiTerbesar = 0;
        for (jabatanModel jabatan:pegawai.getJabatanList()) {
            if (jabatan.getGajiPokok() > gajiTerbesar) {
                gajiTerbesar = jabatan.getGajiPokok();
            }
        }
        System.out.println("Pokok: " + gajiTerbesar);
        gajiLengkap += gajiTerbesar;
        double presentaseTunjangan = pegawai.getInstansi().getProvinsi().getPresentaseTunjangan();
        System.out.println("Presentase: " + presentaseTunjangan);
        gajiLengkap += (gajiLengkap * presentaseTunjangan/100);
        System.out.println("Lengkap: " + gajiLengkap);
        return gajiLengkap;
    }

    @Override
    public List<pegawaiModel> findByInstansiOrderByTanggalLahirAsc(instansiModel instansi){
        return pegawaiDB.findByInstansiOrderByTanggalLahirAsc(instansi);
    }
}
