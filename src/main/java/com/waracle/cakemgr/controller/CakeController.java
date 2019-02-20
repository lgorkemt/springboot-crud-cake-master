package com.waracle.cakemgr.controller;

import com.waracle.cakemgr.entity.CakeEntity;
import com.waracle.cakemgr.service.CakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/ui")
public class CakeController {

    CakeService cakeService;

    @Autowired
    public CakeController(CakeService cakeService){
        this.cakeService = cakeService;
    }

    @GetMapping("/list")
    public String listEmployees(Model model){

        List<CakeEntity> cakes = cakeService.findAll();

        model.addAttribute("cakes", cakes);

        return "ui/list-cakes";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model){

        CakeEntity cakeEntity = new CakeEntity();

        model.addAttribute("cake" , cakeEntity);

        return "ui/cake-form";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("cakeId") int cakeId, Model model){

        // Get the cake by id
        CakeEntity cakeEntity = cakeService.findCakeById(cakeId);

        // set the cake as a model attribute and send it to the view to pre-populate
        model.addAttribute("cake" , cakeEntity);

        return "ui/cake-form";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("cakeId") int cakeId, Model model){

        // delete the cake
        cakeService.deleteCake(cakeId);
        
        // use a redirect to /ui/list
        return "redirect:/ui/list";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("cake") CakeEntity cakeEntity){

        // save the cake
        cakeService.saveCake(cakeEntity);

        // use a redirect to prevent duplicate submissions
        return "redirect:/ui/list";
    }

}
