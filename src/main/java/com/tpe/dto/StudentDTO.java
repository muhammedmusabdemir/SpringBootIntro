package com.tpe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {


    @NotBlank(message = "name can not be space")
    @Size(min = 2, max = 25, message = "name '${validatedValue}' must be between {min} and {max}")
    private String name;

    @NotBlank(message = "lastname can not be space")
    @Size(min = 2, max = 25, message = "lastname '${validatedValue}' must be between {min} and {max}")
    private String lastName;


    private Integer grade;

    @Email(message = "Pleasei provide valid email")//aaa@bbb.ccc
    private String email;

    private String phoneNumber;

}
