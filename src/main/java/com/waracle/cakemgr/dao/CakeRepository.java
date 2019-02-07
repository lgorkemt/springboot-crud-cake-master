package com.waracle.cakemgr.dao;


import com.waracle.cakemgr.entity.CakeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CakeRepository extends JpaRepository<CakeEntity, Integer> {
    // No need to write any code
}
