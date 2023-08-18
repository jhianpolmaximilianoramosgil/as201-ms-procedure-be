package com.ms_procedure.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.Transient;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "procedure")
public class Procedure {
    @Id private Long id;
    @Column private Long unique_identifier;
    @Column private Long procedure_config_id;
    @Column private Long phase_id;
    @Column private Integer person_id;
    @Column private Integer student_id;
    @Column private Long institute_id;
    @Column private String link;
    @Column private Long batch;
    //-------------
    @Column private String collaborator_type_id;
    @Column private String createddate;
    @Column private String modifieddate;
    @Column private String active;
    @Column private Integer note;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String institutionalEmail;
    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String personName;
    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String career_name;


}