package com.apap.tugas1apap.controller;

import com.apap.tugas1apap.model.*;
import com.apap.tugas1apap.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class pegawaiController {
    @Autowired
    private pegawaiService pegawaiService;
    @Autowired
    private jabatanPegawaiService jabatanPegawaiService;
    @Autowired
    private jabatanService jabatanService;
    @Autowired
    private instansiService instansiService;
    @Autowired
    private provinsiService provinsiService;

    @RequestMapping("/")
    private String index(Model model) {
        List<jabatanModel> jabatan = jabatanService.getAll();
        List<instansiModel> listInstansi = instansiService.getAllInstansi();
        model.addAttribute("listInstansi", listInstansi);
        model.addAttribute("listJabatan", jabatan);
        model.addAttribute("title", "Home");
        return "index";
    }

    @GetMapping (value = "/pegawai")
    private String getPegawai(@RequestParam(value = "nip") String nipPegawai, Model model){
        pegawaiModel pegawai = pegawaiService.getPegawaiDetailByNip(nipPegawai);
        List<jabatanPegawaiModel> jabatanPegawai = jabatanPegawaiService.getJabatanByPegawaiId(nipPegawai).get();
        double gajiPegawai = pegawaiService.getGajiLengkapByNip(nipPegawai);
        long l = (new Double(gajiPegawai)).longValue();


        model.addAttribute("pegawai", pegawai);
        model.addAttribute("gajiPegawai", l);
        model.addAttribute("jabatanPegawai", jabatanPegawai);
        model.addAttribute("title", "Data Pegawai");
        return "viewPegawai";
    }

    @GetMapping(value = "/pegawai/termuda-tertua")
    private String viewPegawaiMudaDanTua(@RequestParam(value = "idInstansi") String idInstansi, Model model){
        instansiModel instansiYangDicari = instansiService.getInstansiById(Long.parseLong(idInstansi)).get();
        List<pegawaiModel> listPegawai = pegawaiService.findByInstansiOrderByTanggalLahirAsc(instansiYangDicari);
        pegawaiModel pegawaiTertua = listPegawai.get(0);
        System.out.println("ancol");
        System.out.println(pegawaiTertua.getNama());
        pegawaiModel pegawaiTermuda = listPegawai.get(listPegawai.size()-1);
        List<jabatanModel> jabatanPegawaiTertua = pegawaiTertua.getJabatanList();
        List<jabatanModel> jabatanPegawaiTermuda = pegawaiTermuda.getJabatanList();
        model.addAttribute("instansi", instansiYangDicari);
        model.addAttribute("pegawaiTertua", pegawaiTertua);
        model.addAttribute("pegawaiTermuda", pegawaiTermuda);
        model.addAttribute("jabatanPegawaiTertua", jabatanPegawaiTertua);
        model.addAttribute("jabatanPegawaiTermuda", jabatanPegawaiTermuda);
        return "viewPegawaiTertuaTermuda";
    }


    @RequestMapping(value = "/pegawai/tambah", method = RequestMethod.GET)
    public String tambahPegawai(Model model) {
        pegawaiModel pegawai = new pegawaiModel();
        List<provinsiModel> listProvinsi = provinsiService.findAll();
        List<jabatanPegawaiModel> listJabatanPegawai = new ArrayList<jabatanPegawaiModel>();
        pegawai.setJabatanPegawaiList(listJabatanPegawai);

        jabatanPegawaiModel jabatanPegawai = new jabatanPegawaiModel();
        jabatanPegawai.setPegawai(pegawai);
        pegawai.getJabatanPegawaiList().add(jabatanPegawai);
        List<instansiModel> listInstansi = listProvinsi.get(0).getInstansiProvinsi();

        List<jabatanModel> listJabatan = jabatanService.getAll();

        model.addAttribute("listProvinsi", listProvinsi);
        model.addAttribute("listInstansi", listInstansi);
        model.addAttribute("listJabatan", listJabatan);
        model.addAttribute("title", "Tambah Pegawai");
        model.addAttribute("pegawai", pegawai);
        return "tambah-pegawai";
    }

    @RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST, params= {"addJabatan"})
    public String tambahJabatan(@ModelAttribute pegawaiModel pegawai_new, Model model) {
        pegawaiModel pegawai = pegawai_new;

        jabatanPegawaiModel jabatanPegawai = new jabatanPegawaiModel();
        jabatanPegawai.setPegawai(pegawai);
        pegawai.getJabatanPegawaiList().add(jabatanPegawai);

        List<provinsiModel> listProvinsi = provinsiService.findAll();

        List<instansiModel> listInstansi = new ArrayList<instansiModel>();
        listInstansi = listProvinsi.get(0).getInstansiProvinsi();

        List<jabatanModel> listJabatan = jabatanService.getAll();


        model.addAttribute("title", "Tambah Pegawai");
        model.addAttribute("listProvinsi", listProvinsi);
        model.addAttribute("listInstansi", listInstansi);
        model.addAttribute("listJabatan", listJabatan);
        model.addAttribute("pegawai", pegawai);
        return "tambah-pegawai";
    }

    @RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST)
    public String tambahJabatanBaru(@ModelAttribute pegawaiModel pegawai, Model model) {

        // membuat nip
        String nip = pegawaiService.makeNip(pegawai.getInstansi(), pegawai);
        pegawai.setNip(nip);

        List<jabatanPegawaiModel> listJabatan = pegawai.getJabatanPegawaiList();

        pegawai.setJabatanPegawaiList(new ArrayList<jabatanPegawaiModel>());

        // tambahkan pegawai pada db dan megeset instansi
        pegawaiService.addPegawai(pegawai);



        // menambahkan setiap jabatanPegawai pada list
        for (int i = 0; i < listJabatan.size(); i++) {
            listJabatan.get(i).setPegawai(pegawai);
            jabatanPegawaiService.add(listJabatan.get(i));
        }


        model.addAttribute("title", "Sukses");
        model.addAttribute("nipPegawai", nip);
        return "tambah-pegawai-sukses";
    }

    @RequestMapping(value = "/pegawai/ubah", method = RequestMethod.GET)
    public String ubahPegawai(@RequestParam(value="nip") String nip, Model model) {
        pegawaiModel pegawai = pegawaiService.getPegawaiDetailByNip(nip);
        List<provinsiModel> listProvinsi = provinsiService.findAll();

        List<instansiModel> listInstansi = pegawai.getInstansi().getProvinsi().getInstansiProvinsi();

        List<jabatanModel> listJabatan = jabatanService.getAll();

        model.addAttribute("title", "Ubah Pegawai");
        model.addAttribute("listProvinsi", listProvinsi);
        model.addAttribute("listInstansi", listInstansi);
        model.addAttribute("listJabatan", listJabatan);
        model.addAttribute("pegawai", pegawai);
        return "ubah-pegawai";
    }

    @RequestMapping(value = "/pegawai/ubah", method = RequestMethod.POST, params= {"addJabatan"})
    public String ubahJabatan(@ModelAttribute pegawaiModel pegawai_new, Model model) {
        pegawaiModel pegawai = pegawai_new;

        jabatanPegawaiModel jabatanPegawai = new jabatanPegawaiModel();
        jabatanPegawai.setPegawai(pegawai);
        pegawai.getJabatanPegawaiList().add(jabatanPegawai);

        List<provinsiModel> listProvinsi = provinsiService.findAll();

        List<instansiModel> listInstansi = new ArrayList<instansiModel>();
        listInstansi = listProvinsi.get(0).getInstansiProvinsi();


        List<jabatanModel> listJabatan = jabatanService.getAll();


        model.addAttribute("title", "Ubah Pegawai");
        model.addAttribute("listProvinsi", listProvinsi);
        model.addAttribute("listInstansi", listInstansi);
        model.addAttribute("listJabatan", listJabatan);
        model.addAttribute("pegawai", pegawai);
        return "ubah-pegawai";
    }

    @RequestMapping(value = "/pegawai/ubah", method = RequestMethod.POST)
    public String ubahJabatanBaru(@ModelAttribute pegawaiModel pegawai, Model model) {
        // ambil pegawai sebelum diupdate
        pegawaiModel pegawaiBefore = pegawaiService.getPegawaiDetailByNip(pegawai.getNip());

        String nip = pegawaiService.makeNip(pegawai.getInstansi(), pegawai);
        pegawai.setNip(nip);

        pegawaiService.update(pegawai, pegawaiBefore);


        model.addAttribute("title", "Sukses");
        model.addAttribute("nipPegawai", nip);
        return "ubah-pegawai-sukses";
    }


    @RequestMapping(value = "/pegawai/cari", method = RequestMethod.GET)
    public String cariPegawai(Model model) {

        List<provinsiModel> listProvinsi = provinsiService.findAll();

        List<instansiModel> listInstansi = new ArrayList<instansiModel>();
        listInstansi = listProvinsi.get(0).getInstansiProvinsi();
        List<jabatanModel> listJabatan = jabatanService.getAll();

        model.addAttribute("title", "Cari Pegawai");
        model.addAttribute("listProvinsi", listProvinsi);
        model.addAttribute("listInstansi", listInstansi);
        model.addAttribute("listJabatan", listJabatan);

        return "cari-pegawai";
    }

    @RequestMapping(value = "/pegawai/cari", method = RequestMethod.GET, params= {"search"})
    public String cariPegawaiBaru(@RequestParam(value="IdProvinsi", required=false) Optional<Long> IdProvinsi,
                                  @RequestParam(value="IdInstansi", required=false) Optional<Long> IdInstansi,
                                  @RequestParam(value="IdJabatan", required=false) Optional<Long> IdJabatan, Model model) {



        List<pegawaiModel> listPegawai = new ArrayList<pegawaiModel>();
        if (IdProvinsi.isPresent()) {
            if (IdInstansi.isPresent()) {
                if (IdJabatan.isPresent()) {
                    instansiModel instansi = instansiService.findById(IdInstansi.get());
                    jabatanModel jabatan = jabatanService.findById(IdJabatan.get());

                    listPegawai = pegawaiService.findByInstansiAndJabatan(instansi, jabatan);

                }
                else {
                    instansiModel instansi = instansiService.findById(IdInstansi.get());
                    listPegawai = pegawaiService.findByInstansi(instansi);
                }

            }
            else {
                if (IdJabatan.isPresent()) {
                    List<instansiModel> listInstansi = provinsiService.findById(IdProvinsi.get()).getInstansiProvinsi();

                    for (int i = 0; i < listInstansi.size(); i++) {
                        List<pegawaiModel> listPegawaiBaru = listInstansi.get(i).getPegawaiInstansi();
                        for (int j = 0; j < listPegawaiBaru.size(); j++) {
                            for (int k = 0; k < listPegawaiBaru.get(j).getJabatanPegawaiList().size(); k++) {
                                if (listPegawaiBaru.get(j).getJabatanPegawaiList().get(k).getJabatan().getId() == IdJabatan.get()) {
                                    listPegawai.add(listPegawaiBaru.get(j));
                                    break;
                                }
                            }

                        }
                    }


                }
                else {
                    List<instansiModel> listInstansi = provinsiService.findById(IdProvinsi.get()).getInstansiProvinsi();
                    for (int i = 0; i < listInstansi.size(); i++) {
                        List<pegawaiModel> listPegawaiBaru = listInstansi.get(i).getPegawaiInstansi();
                        listPegawai.addAll(listPegawaiBaru);
                    }
                }
            }
        }
        else {
            if (IdJabatan.isPresent()) {
                jabatanModel jabatan = jabatanService.findById(IdJabatan.get());
                for (int i = 0; i< jabatan.getPegawaiList().size(); i++) {
                    listPegawai.add(jabatan.getJabatanPegawaiList().get(i).getPegawai());
                }

            }
        }

        List<provinsiModel> listProvinsi = provinsiService.findAll();

        List<instansiModel> listInstansi = new ArrayList<instansiModel>();
        listInstansi = listProvinsi.get(0).getInstansiProvinsi();
        List<jabatanModel> listJabatan = jabatanService.getAll();

        model.addAttribute("title", "Cari Pegawai");
        model.addAttribute("listProvinsi", listProvinsi);
        model.addAttribute("listInstansi", listInstansi);
        model.addAttribute("listJabatan", listJabatan);
        model.addAttribute("listPegawai", listPegawai);
        return "cari-pegawai";
    }

}
