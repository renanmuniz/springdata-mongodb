package com.mongodb.service;

import com.mongodb.entity.Arrangement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArrangementService {
    String save(Arrangement arrangement);

    List<Arrangement> getByBandeira(String bandeira);

    List<Arrangement> search(String status, String bandeira, Integer page, Integer size);
}
