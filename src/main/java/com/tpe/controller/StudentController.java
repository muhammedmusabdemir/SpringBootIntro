package com.tpe.controller;


import com.tpe.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Controller
@RestController
@RequestMapping("/students") //http://localhost:8080/students
public class StudentController {

    @Autowired
    private StudentService studentService;

    //spring boot u selamlama:)
    //http://localhost:8080/students/greet+GET
    @GetMapping("/greet")
    public String greet(){
        return "Hello Spring Boot";
    }


//CRUD


}
