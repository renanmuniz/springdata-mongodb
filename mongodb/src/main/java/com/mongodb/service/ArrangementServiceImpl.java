package com.mongodb.service;

import com.mongodb.dto.PaginationMongo;
import com.mongodb.entity.Arrangement;
import com.mongodb.repository.ArrangementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

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
    public PaginationMongo search(String status, String bandeira, Integer page, Integer size) {

        Query query = new Query().skip(size * (page - 1)).limit(size);
        Query queryCount = new Query();
        List<Criteria> criteria = new ArrayList<>();

        if (status != null && !status.isEmpty() && !status.isBlank()) {
            criteria.add(Criteria.where("status").is(status));
        }

        if (bandeira != null && !bandeira.isEmpty() && !bandeira.isBlank()) {
            criteria.add(Criteria.where("bandeira").is(bandeira));
        }

        if (!criteria.isEmpty()) {
            query.addCriteria(new Criteria()
                    .andOperator(criteria.toArray(new Criteria[0])));
            queryCount.addCriteria(new Criteria()
                    .andOperator(criteria.toArray(new Criteria[0])));
        }


        var arrangementsCount = mongoTemplate.count(queryCount, Arrangement.class);
        List<Arrangement> arrangements = mongoTemplate.find(query, Arrangement.class);

        return PaginationMongo.createPage(arrangements, page, size, arrangementsCount);
    }

    @Override
    public PaginationMongo searchDetailed(List<String> status, List<String> bandeira, Integer page, Integer size) {

        List<AggregationOperation> aggregationOperations = new ArrayList<>();
        List<AggregationOperation> aggregationOperationsCount = new ArrayList<>();

        if (status != null && !status.isEmpty()) {
            aggregationOperations.add(Aggregation.match(Criteria.where("status").in(status)));
            aggregationOperationsCount.add(Aggregation.match(Criteria.where("status").in(status)));
        }

        if (bandeira != null && !bandeira.isEmpty()) {
            aggregationOperations.add(Aggregation.match(Criteria.where("bandeira").in(bandeira)));
            aggregationOperationsCount.add(Aggregation.match(Criteria.where("bandeira").in(bandeira)));
        }

        //query documents:
        aggregationOperations.add(LookupOperation.newLookup()
                .from("receivables")
                .localField("urs")
                .foreignField("_id")
                .as("receivables"));
        aggregationOperations.add(skip((long)size * (page - 1)));
        aggregationOperations.add(limit(size));
        TypedAggregation<Arrangement> aggregation = newAggregation(Arrangement.class,aggregationOperations);

        //just for count total elements:
        aggregationOperationsCount.add(Aggregation.count().as("totalEntityCount"));
        TypedAggregation<Arrangement> aggregationCount = newAggregation(Arrangement.class, aggregationOperationsCount);

        //Perform mongodb data fetch:
        AggregationResults<Arrangement> response = mongoTemplate.aggregate(aggregation, "arrangement", Arrangement.class);
        AggregationResults<Arrangement> responseCount = mongoTemplate.aggregate(aggregationCount, "arrangement", Arrangement.class);

        return PaginationMongo.createPage(response.getMappedResults(), page, size, responseCount.getMappedResults().get(0).getTotalEntityCount());
    }
}
