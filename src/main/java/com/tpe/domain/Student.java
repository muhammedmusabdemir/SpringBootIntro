package com.tpe.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor //tum fieldlarla parametreli constructor
@NoArgsConstructor //default constructor
//@RequiredArgsConstructor //final veya notnull fieldlardan constructor
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Getter
    //@Setter(AccessLevel.NONE)
    private Long id;

    @NotBlank(message = "name can not be space")
    @Size(min = 2,max = 25,message = "name '${validatedValue}' must be between {min} and {max}")
    @Column(nullable = false,length = 25)
    /*final*/ private String name;

    @NotBlank(message = "lastname can not be space")
    @Size(min = 2,max = 25,message = "lastname '${validatedValue}' must be between {min} and {max}")
    @Column(nullable = false,length = 25)
    /*final*/ private String lastName;


    /*final*/ private Integer grade;

    @Column(nullable = false,unique = true,length = 50)
    @Email(message = "Please provide valid email") //aaa@bbb.ccc
    /*final*/ private String email;


    private String phoneNumber;
    private LocalDateTime createDate = LocalDateTime.now();

    //getter-setter
    //parametreli/siz constructor
}
