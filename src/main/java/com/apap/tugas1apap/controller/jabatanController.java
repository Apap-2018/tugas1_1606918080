package com.apap.tugas1apap.controller;

import com.apap.tugas1apap.model.jabatanModel;
import com.apap.tugas1apap.model.pegawaiModel;
import com.apap.tugas1apap.service.jabatanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class jabatanController {
    @Autowired
    private jabatanService jabatanService;

    @GetMapping(value = "/jabatan/tambah")
    private String tambahJabatanGet(Model model){
        jabatanModel jabatan = new jabatanModel();
        return "addJabatan";
    }

    @PostMapping(value = "/jabatan/tambah")
    private String tambahJabatanPost (@ModelAttribute jabatanModel jabatan, Model model){
        jabatanService.addJabatan(jabatan);
        model.addAttribute("jabatan", jabatan);
        return "addJabatanSuccess";
    }

    @GetMapping(value = "/jabatan/view")
    private String viewJabatan(@RequestParam(value="idJabatan") Long id, Model model) {
        jabatanModel jabatan = jabatanService.getJabatanById(id).get();
        model.addAttribute("jabatan", jabatan);
        return "viewJabatan";
    }

    @GetMapping(value = "/jabatan/ubah")
    private String ubahJabatanGet(@RequestParam(value = "idJabatan") String id_jabatan, Model model){
        Long id = Long.parseLong(id_jabatan);
        jabatanModel jabatan = jabatanService.getJabatanById(id).get();
        model.addAttribute("jabatan", jabatan);
        return "ubahJabatan";
    }

    @PostMapping(value = "/jabatan/ubah")
    private String ubahJabatanPost(@ModelAttribute jabatanModel jabatan, Model model){
        Long id = jabatan.getId();
        jabatanService.updateJabatan(id, jabatan);
        model.addAttribute("jabatan", jabatan);
        return "ubahJabatanSuccess";

    }

    @PostMapping (value = "/jabatan/hapus")
    private String hapusJabatanGet(@ModelAttribute jabatanModel jabatan, Model model){
        System.out.println(jabatan.getId());
        jabatanService.deleteJabatan(jabatan);
        return "deleteJabatanSuccess";
    }

    @GetMapping(value = "/jabatan/viewall")
    private String viewAll(Model model){
        List<jabatanModel> semuaJabatan = jabatanService.getAll();
        model.addAttribute("allJabatan", semuaJabatan);
        return "viewAllJabatan";
    }

}
