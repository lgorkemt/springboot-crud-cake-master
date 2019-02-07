package com.waracle.cakemgr.rest;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.waracle.cakemgr.entity.CakeEntity;
import com.waracle.cakemgr.service.CakeService;
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

    // inject Cake Service
    @Autowired
    public CakeRestController(CakeService cakeService){
        this.cakeService = cakeService;
    }

    @PostConstruct
    public void init() {

        System.out.println("init started");

        System.out.println("downloading cake json");

        try (InputStream inputStream = new URL("https://gist.githubusercontent.com/hart88/198f29ec5114a3ec3460/raw/8dd19a88f9b8d24c23d9960f3300d0c917a4f07c/cake.json").openStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuffer buffer = new StringBuffer();
            String line = reader.readLine();
            while (line != null) {
                buffer.append(line);
                line = reader.readLine();
            }

            System.out.println("parsing cake json");
            JsonParser parser = new JsonFactory().createParser(buffer.toString());
            if (JsonToken.START_ARRAY != parser.nextToken()) {
                throw new Exception("bad token");
            }

            JsonToken nextToken = parser.nextToken();
            while(nextToken == JsonToken.START_OBJECT) {
                System.out.println("creating cake entity");

                CakeEntity cakeEntity = new CakeEntity();
                System.out.println(parser.nextFieldName());
                cakeEntity.setTitle(parser.nextTextValue());

                System.out.println(parser.nextFieldName());
                cakeEntity.setDescription(parser.nextTextValue());

                System.out.println(parser.nextFieldName());
                cakeEntity.setImage(parser.nextTextValue());

                cakeService.saveCake(cakeEntity);

                nextToken = parser.nextToken();
                System.out.println(nextToken);

                nextToken = parser.nextToken();
                System.out.println(nextToken);
            }

        } catch (Exception ex) {
             ex.printStackTrace();
        }
        System.out.println("init finished");
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

    // add mapping for POST /cakes - adding a new employee
    @PostMapping("/cakes")
    public CakeEntity addCake(@RequestBody CakeEntity cakeEntity){
        cakeEntity.setCakeId(0);
        cakeService.saveCake(cakeEntity);
        return cakeEntity;
    }

    // add mapping for PUT /cakes - updating a new employee
    @PutMapping("/cakes")
    public CakeEntity updateCake(@RequestBody CakeEntity cakeEntity){
        cakeService.saveCake(cakeEntity);
        return cakeEntity;
    }

    // add mapping for GET /cakes/{employeeId}
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
