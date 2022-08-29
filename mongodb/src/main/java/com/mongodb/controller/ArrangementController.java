package com.mongodb.controller;

import com.mongodb.entity.Arrangement;
import com.mongodb.service.ArrangementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/arrangement")
public class ArrangementController {

    @Autowired
    private ArrangementService arrangementService;

    @PostMapping
    public String save(@RequestBody Arrangement arrangement) {
        return arrangementService.save(arrangement);
    }

    @GetMapping
    public List<Arrangement> getArrangementByDate(@RequestParam String bandeira) {
        return arrangementService.getByBandeira(bandeira);
    }

    @GetMapping("/search")
    public ResponseEntity searchArrangement(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String bandeira,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "3") Integer size)
            {
                return new ResponseEntity<>(arrangementService.search(status, bandeira, page, size), HttpStatus.OK);
    }

    @GetMapping("/detailed")
    public ResponseEntity searchArrangementDetailed(
            @RequestParam(required = false) List<String> status,
            @RequestParam(required = false) List<String> bandeira,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "3") Integer size)
    {
        return new ResponseEntity<>(arrangementService.searchDetailed(status, bandeira, page, size), HttpStatus.OK);
    }


}
