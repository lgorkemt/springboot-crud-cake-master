package com.waracle.cakemgr.service;

import com.waracle.cakemgr.entity.CakeEntity;

import java.util.List;

public interface CakeService {

    public List<CakeEntity> findAll();

    public CakeEntity findCakeById(int id);

    public void saveCake(CakeEntity cakeEntity);

    public void deleteCake(int id);
}
