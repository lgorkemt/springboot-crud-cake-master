package com.waracle.cakemgr.rest;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.waracle.cakemgr.entity.CakeEntity;
import com.waracle.cakemgr.service.CakeService;
import com.waracle.cakemgr.service.InitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

@RestController
@RequestMapping("/")
public class  CakeRestController {

    CakeService cakeService;
    InitService initService;

    // inject Cake Service and Init Service
    @Autowired
    public CakeRestController(CakeService cakeService, InitService initService){
        this.cakeService = cakeService;
        this.initService = initService;
    }

    @PostConstruct
    public void init() {
        initService.initDB(cakeService);
    }

    // expose cakes and return list of cakes
    @GetMapping("/")
    public String welcome(){
        return "Welcome to cake Master!";
    }

    // expose cakes and return list of cakes
    @GetMapping("/cakes")
    public List<CakeEntity> getCakes(){
        List<CakeEntity> cakeEntityList = cakeService.findAll();
        return cakeEntityList;
    }

    // add mapping for GET /cakes/{cakeId}
    @GetMapping("/cakes/{cakeId}")
    public CakeEntity getCake(@PathVariable int cakeId){
        CakeEntity cakeEntity = cakeService.findCakeById(cakeId);

        if(cakeEntity == null){
            throw new RuntimeException("Cake not found : " + cakeId );
        }
        return cakeEntity;
    }

    // add mapping for POST /cakes - adding a new cake
    @PostMapping("/cakes")
    public CakeEntity addCake(@RequestBody CakeEntity cakeEntity){
        cakeEntity.setCakeId(0);
        cakeService.saveCake(cakeEntity);
        return cakeEntity;
    }

    // add mapping for PUT /cakes - updating a new cake
    @PutMapping("/cakes")
    public CakeEntity updateCake(@RequestBody CakeEntity cakeEntity){
        cakeService.saveCake(cakeEntity);
        return cakeEntity;
    }

    // add mapping for DELETE /cakes/{cakeId} - delete a cake
    @DeleteMapping("/cakes/{cakeId}")
    public String deleteCake(@PathVariable int cakeId){
        CakeEntity cakeEntity = cakeService.findCakeById(cakeId);

        if(cakeEntity == null){
            throw new RuntimeException("Cake not found !");
        }

        cakeService.deleteCake(cakeId);

        return "Cake " + cakeId + " deleted !";
    }
}
