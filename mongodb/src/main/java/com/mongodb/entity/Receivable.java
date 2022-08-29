package com.mongodb.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@Document(collection = "receivable")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Receivable {

    @Id
    private String id;
    private String documentId;
    private String data;
    private BigDecimal valor;
    private String status;

}
