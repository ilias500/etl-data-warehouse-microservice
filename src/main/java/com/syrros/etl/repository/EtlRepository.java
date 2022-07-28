package com.syrros.etl.repository;

import com.syrros.etl.model.Etl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EtlRepository extends JpaRepository<Etl, Long> {
}
