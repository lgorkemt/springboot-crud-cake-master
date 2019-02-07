package com.waracle.cakemgr.service;

import com.waracle.cakemgr.dao.CakeRepository;
import com.waracle.cakemgr.entity.CakeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CakeServiceImpl implements CakeService {

    CakeRepository cakeRepository;

    @Autowired
    public CakeServiceImpl(CakeRepository cakeRepository){
        this.cakeRepository = cakeRepository;
    }

    @Override
    public List<CakeEntity> findAll() {
        return cakeRepository.findAll();
    }

    @Override
    public CakeEntity findCakeById(int id) {
        Optional<CakeEntity> result = cakeRepository.findById(id);

        CakeEntity cakeEntity = null;
        if(result.isPresent()){
            cakeEntity = result.get();
        }
        else {
            throw new RuntimeException("Did not find the cake with the id : " + id);
        }

        return cakeEntity;
    }

    @Override
    public void saveCake(CakeEntity cakeEntity) {
        cakeRepository.save(cakeEntity);
    }

    @Override
    public void deleteCake(int id) {
        cakeRepository.deleteById(id);
    }

}
