package com.mongodb.service;

import com.mongodb.dto.PaginationMongo;
import com.mongodb.entity.Arrangement;

import java.util.List;

public interface ArrangementService {
    String save(Arrangement arrangement);

    List<Arrangement> getByBandeira(String bandeira);

    PaginationMongo search(String status, String bandeira, Integer page, Integer size);

    PaginationMongo searchDetailed(List<String> status, List<String> bandeira, Integer page, Integer size);
}
