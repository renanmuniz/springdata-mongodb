package com.mongodb.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
    private List<Receivable> receivables;
    private Long totalEntityCount;

}
