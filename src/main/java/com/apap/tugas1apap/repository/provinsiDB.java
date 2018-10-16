package com.apap.tugas1apap.repository;

import com.apap.tugas1apap.model.provinsiModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface provinsiDB extends JpaRepository<provinsiModel, Long> {
}
