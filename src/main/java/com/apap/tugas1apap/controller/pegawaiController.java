package com.apap.tugas1apap.controller;

import com.apap.tugas1apap.model.instansiModel;
import com.apap.tugas1apap.model.jabatanModel;
import com.apap.tugas1apap.model.jabatanPegawaiModel;
import com.apap.tugas1apap.model.pegawaiModel;
import com.apap.tugas1apap.service.instansiService;
import com.apap.tugas1apap.service.jabatanPegawaiService;
import com.apap.tugas1apap.service.jabatanService;
import com.apap.tugas1apap.service.pegawaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = ("/pegawai/tambah"))
    private String addPegawaiGet(Model model){
        return "index";
    }

    @PostMapping(value = ("pegawai/tambah"))
    private String addPegawaiPost(ModelAttribute pegawaiModel ){
        return "index";
    }

}
