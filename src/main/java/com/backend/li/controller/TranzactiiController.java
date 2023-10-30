package com.backend.li.controller;

import com.backend.li.model.TranzactiiEntity;
import com.backend.li.service.TranzactiiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tranzactii")
public class TranzactiiController {

    @Autowired
    private TranzactiiService tranzactiiService;

    @PostMapping
    public TranzactiiEntity createTranzactii(@RequestBody TranzactiiEntity tranzactii) {
        return tranzactiiService.createTranzactii(tranzactii);
    }

    @GetMapping
    public List<TranzactiiEntity> getAllTranzactii() {
        return tranzactiiService.getAllTranzactii();
    }

    @GetMapping("/{id}")
    public TranzactiiEntity getTranzactiiById(@PathVariable("id") Integer id) {
        return tranzactiiService.getTranzactiiById(id);
    }

    @PutMapping("/{id}")
    public TranzactiiEntity updateTranzactii(@PathVariable("id") Integer id,
                                             @RequestBody TranzactiiEntity tranzactii) {
        return tranzactiiService.updateTranzactii(id, tranzactii);
    }

    @DeleteMapping("/{id}")
    public void deleteTranzactii(@PathVariable("id") Integer id) {
        tranzactiiService.deleteTranzactii(id);
    }
}