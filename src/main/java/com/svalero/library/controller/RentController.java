package com.svalero.library.controller;

import com.svalero.library.domain.Notice;
import com.svalero.library.domain.Rent;
import com.svalero.library.service.NoticeService;
import com.svalero.library.service.RentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RentController {

    @Autowired
    private RentService rentService;
    @GetMapping("/rents")
    public List<Rent> getRents() {
        return rentService.findAll();
    }

    @GetMapping("/rents/{code}")
    public Rent getRent(@PathVariable String code) {
        return rentService.findByCode(code);
    }
}
