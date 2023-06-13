package com.tpe.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastName;
    private Integer grade;
    private String email;
    private String phoneNumber;
    private LocalDateTime createDate = LocalDateTime.now();
}
