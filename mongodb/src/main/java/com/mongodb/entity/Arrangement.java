package com.mongodb.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@Document(collection = "arrangement")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Arrangement {

    @Id
    private String id;
    private String documentId;
    private LocalDate data;
    private String bandeira;
    private String status;
    private List<String> urs;

}
