package com.mongodb.service;

import com.mongodb.entity.Arrangement;
import com.mongodb.repository.ArrangementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArrangementServiceImpl implements ArrangementService {

    @Autowired
    private ArrangementRepository arrangementRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public String save(Arrangement arrangement) {
        arrangement.setData(LocalDate.now());
        return arrangementRepository.save(arrangement).getId();
    }

    @Override
    public List<Arrangement> getByBandeira(String bandeira) {
        return arrangementRepository.findByBandeira(bandeira);
    }

    @Override
    public List<Arrangement> search(String status, String bandeira, Integer page, Integer size) {

        Query query = new Query().skip(size*(page-1)).limit(size);
        List<Criteria> criteria = new ArrayList<>();

        if(status != null && !status.isEmpty() && !status.isBlank()) {
            criteria.add(Criteria.where("status").is(status));
        }

        if(bandeira != null && !bandeira.isEmpty() && !bandeira.isBlank()) {
            criteria.add(Criteria.where("bandeira").is(bandeira));
        }

        if(!criteria.isEmpty()) {
            query.addCriteria(new Criteria()
                    .andOperator(criteria.toArray(new Criteria[0])));
        }

        List<Arrangement> arrangements = mongoTemplate.find(query, Arrangement.class);

        return arrangements;

    }
}
