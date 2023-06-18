package com.tpe.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Book { //Many


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("bookName") //json formatinda name fieldini bu key ile goster
    private String name;

    @JsonIgnore //book objemi JSON a maplerken student i alma
    @ManyToOne //1-Student --> Many Book
    private Student student; //One
}
