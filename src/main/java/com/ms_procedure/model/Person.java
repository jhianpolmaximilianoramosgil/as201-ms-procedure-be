package com.ms_procedure.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    private Long id;
    private String id_type;
    private String id_number;
    private String name;
    private String lastname;
    private String cellphone;
    private String email;
    private boolean active;

}
