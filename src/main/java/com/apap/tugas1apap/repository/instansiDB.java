package com.apap.tugas1apap.repository;

import com.apap.tugas1apap.model.instansiModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface instansiDB extends JpaRepository <instansiModel, Long> {
}
