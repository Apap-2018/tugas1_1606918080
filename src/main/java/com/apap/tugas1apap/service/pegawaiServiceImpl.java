package com.apap.tugas1apap.service;

import com.apap.tugas1apap.model.*;
import com.apap.tugas1apap.repository.jabatanPegawaiDB;
import com.apap.tugas1apap.repository.pegawaiDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class pegawaiServiceImpl implements pegawaiService {
    @Autowired
    private pegawaiDB pegawaiDB;
    @Autowired
    private jabatanPegawaiDB jabatanPegawaiDB;

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
        pegawaiModel pegawai = pegawaiDB.findByNip(nip);
        List<jabatanPegawaiModel> listJabatan = pegawai.getJabatanPegawaiList();
        int banyakJabatan = listJabatan.size();
        double gajiPokok = 0;

        for (int i = 0; i < banyakJabatan; i++) {
            double gajiJabatan = listJabatan.get(i).getJabatan().getGajiPokok();
            if (gajiPokok < gajiJabatan) {
                gajiPokok = gajiJabatan;
            }
        }

        double presentaseTunjangan = pegawai.getInstansi().getProvinsi().getPresentaseTunjangan();
        System.out.println();
        System.out.println(gajiPokok);
        System.out.println(presentaseTunjangan);
        gajiPokok += (presentaseTunjangan*gajiPokok)/100;

        System.out.println();
        System.out.println(gajiPokok);
        System.out.println();

        return (int) gajiPokok;
//        double gajiLengkap = 0;
//        pegawaiModel pegawai = this.getPegawaiDetailByNip(nip);
//        double gajiTerbesar = 0;
//        for (jabatanModel jabatan:pegawai.getJabatanList()) {
//            if (jabatan.getGajiPokok() > gajiTerbesar) {
//                gajiTerbesar = jabatan.getGajiPokok();
//            }
//        }
//        System.out.println("Pokok: " + gajiTerbesar);
//        gajiLengkap += gajiTerbesar;
//        double presentaseTunjangan = pegawai.getInstansi().getProvinsi().getPresentaseTunjangan();
//        System.out.println("Presentase: " + presentaseTunjangan);
//        gajiLengkap += (gajiLengkap * presentaseTunjangan/100);
//        System.out.println("Lengkap: " + gajiLengkap);
//        return gajiLengkap;
    }

    @Override
    public List<pegawaiModel> findByInstansiOrderByTanggalLahirAsc(instansiModel instansi){
        return pegawaiDB.findByInstansiOrderByTanggalLahirAsc(instansi);
    }
    @Override
    public String makeNip(instansiModel instansi, pegawaiModel pegawai) {
        // TODO Auto-generated method stub

        provinsiModel provinsi = instansi.getProvinsi();

        String nip = "";
        nip += instansi.getId();

        Date tanggalLahir = pegawai.getTanggalLahir();
        String[] tglLahir = (""+tanggalLahir).split("-");
        for (int i = tglLahir.length-1; i >= 0; i--) {
            int ukuranTgl = tglLahir[i].length();
            nip += tglLahir[i].substring(ukuranTgl-2, ukuranTgl);
        }

        nip += pegawai.getTahunMasuk();

        List<pegawaiModel> listPegawai = pegawaiDB.findByTanggalLahirAndTahunMasukAndInstansi(pegawai.getTanggalLahir(), pegawai.getTahunMasuk(), pegawai.getInstansi());

        int banyakPegawai = listPegawai.size();

        if (banyakPegawai >= 10) {
            nip += banyakPegawai;
        }
        else {
            nip += "0" + (banyakPegawai+1);
        }


        return nip;
    }

    @Override
    public void delete(pegawaiModel pegawai) {
        // TODO Auto-generated method stub
        pegawaiDB.delete(pegawai);
    }

    @Override
    public void update(pegawaiModel pegawaiUpdate, pegawaiModel pegawaiBefore) {
        // TODO Auto-generated method stub
        pegawaiBefore.setInstansi(pegawaiUpdate.getInstansi());
        pegawaiBefore.setNama(pegawaiUpdate.getNama());
        pegawaiBefore.setNip(pegawaiUpdate.getNip());
        pegawaiBefore.setTahunMasuk(pegawaiUpdate.getTahunMasuk());
        pegawaiBefore.setTanggalLahir(pegawaiUpdate.getTanggalLahir());
        pegawaiBefore.setTempatLahir(pegawaiUpdate.getTempatLahir());


        // update jabatan
        int jumlahList = pegawaiBefore.getJabatanPegawaiList().size();
        for (int i = 0; i< jumlahList; i++) {
            pegawaiBefore.getJabatanPegawaiList().get(i).setJabatan(pegawaiUpdate.getJabatanPegawaiList().get(i).getJabatan());
        }

        for (int i = jumlahList; i < pegawaiUpdate.getJabatanPegawaiList().size(); i++) {
            pegawaiUpdate.getJabatanPegawaiList().get(i).setPegawai(pegawaiBefore);
            jabatanPegawaiDB.save(pegawaiUpdate.getJabatanPegawaiList().get(i));
        }

    }

    @Override
    public List<pegawaiModel> findByInstansiAndJabatan(instansiModel instansi, jabatanModel jabatan) {
        // TODO Auto-generated method stub
        List<pegawaiModel> hasil = new ArrayList<pegawaiModel>();
        List<pegawaiModel> listPegawaiInstansi = pegawaiDB.findByInstansi(instansi);

        for (int i = 0; i < listPegawaiInstansi.size(); i++) {
            int sizeJ = listPegawaiInstansi.get(i).getJabatanList().size();
            for (int j = 0; j < sizeJ; j++ ) {
                if (listPegawaiInstansi.get(i).getJabatanPegawaiList().get(j).getJabatan().getId() == jabatan.getId()) {
                    hasil.add(listPegawaiInstansi.get(i));
                }
            }

        }
        return hasil;
    }

    @Override
    public List<pegawaiModel> findByInstansi(instansiModel instansi) {
        // TODO Auto-generated method stub
        return pegawaiDB.findByInstansi(instansi);
    }

    @Override
    public List<pegawaiModel> findAll() {
        // TODO Auto-generated method stub
        return pegawaiDB.findAll();
    }
}
