package com.tpe.controller;


import com.tpe.domain.Student;
import com.tpe.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    //1-tum studentlari listeleyelim:READ
    //http://localhost:8080/students + GET
    @GetMapping
    public ResponseEntity<List<Student>> listAllStudents(){
        List<Student> studentList= studentService.getAllStudent();
        //return new ResponseEntity<>(studentList, HttpStatus.OK); //200
        return ResponseEntity.ok(studentList); //200

    }
    //response:body(data) + HTTP status code
    //ResponseEntity: response bodysi ile birlikte HTTP status code nu gondermemizi saglar.
    //ResponseEntity.ok() methodu HTTP status olarak OK yada 200 donmek icin bir kisayoldur.

    //3-yeni bir student CREATE etme
    //http://localhost:8080/students + POST + RequestBody(JSON)
    @PostMapping
    public ResponseEntity<Map<String,String>> createStudent(@Valid @RequestBody Student student){

        studentService.saveStudent(student);

        Map<String,String> response = new HashMap<>();
        response.put("message","Student is created successfully");
        response.put("status","success");
        return new ResponseEntity<>(response,HttpStatus.CREATED); //201

    }
    //@RequestBody: HTTP requestin bodysindeki JSON formatindaki bilgiyi student objesine mapler.
    //(Entity obje <-> JSON) --> Jackson

    //5-belirli bir id ile studenti goruntuleyelim
    //http://localhost:8080/students/query?id=1 + GET
    @GetMapping("/query")
    public ResponseEntity<Student> getStudent(@RequestParam("id") Long id){
        Student student = studentService.getStudentById(id);
        return new ResponseEntity<>(student,HttpStatus.OK); //200
    }



    //5(2.yontem)-belirli bir id ile studenti goruntuleyelim + Path Param
    //http://localhost:8080/students/1 + GET
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById (@PathVariable("id") Long id){
        Student student = studentService.getStudentById(id);
        return new ResponseEntity<>(student,HttpStatus.OK); //200
    }

    //clienttan bilgi almak icin:JSON formatinda request body
    //                          :request param query?id=1
    //                          :path param /1


    //7-belirli bir id ile studenti silelim + Path Param
    //http://localhost:8080/students/1
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,String>> deleteStudent (@PathVariable Long id ){

        studentService.deleteStudent(id);

        Map<String,String> response = new HashMap<>();
        response.put("message","Student is deleted successfully..");
        response.put("status","success");
        return ResponseEntity.ok(response); //200
    }

    //9-update + DTO


}
