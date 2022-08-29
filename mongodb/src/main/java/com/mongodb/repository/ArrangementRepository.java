package com.mongodb.repository;

import com.mongodb.entity.Arrangement;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArrangementRepository extends MongoRepository<Arrangement, String> {
    List<Arrangement> findByBandeira(String bandeira);
}
