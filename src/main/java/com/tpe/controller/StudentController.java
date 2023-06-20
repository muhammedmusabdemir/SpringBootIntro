package com.tpe.controller;


import com.tpe.domain.Student;
import com.tpe.dto.StudentDTO;
import com.tpe.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Controller
@RestController
@RequestMapping("/students") //http://localhost:8080/students
public class StudentController {

    Logger logger = LoggerFactory.getLogger(StudentController.class);

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
    @PreAuthorize("hasRole('ADMIN')") //sadece ROLE_ADMIN bu istegi yapabilir
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

    //5-belirli bir id ile studenti goruntuleyelim + Request Param
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
    //http://localhost:8080/students/1 + DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,String>> deleteStudent (@PathVariable Long id ){

        studentService.deleteStudent(id);

        Map<String,String> response = new HashMap<>();
        response.put("message","Student is deleted successfully..");
        response.put("status","success");
        return ResponseEntity.ok(response); //200
    }

    //9-belirli bir id ile studenti update edelim.(name,lastName,grade,email,phoneNumber)
    //http://localhost:8080/students/1 + UPDATE + JSON
    @PutMapping("/{id}")
    public ResponseEntity<Map<String,String>> updateStudent(@PathVariable("id") Long id,
                                                            @Valid @RequestBody StudentDTO studentDTO){

        studentService.updateStudent(id,studentDTO);

        Map<String,String> response = new HashMap<>();
        response.put("message","Student is updated successfully..");
        response.put("status","success");
        return ResponseEntity.ok(response); //200
    }

    //11-pagination - sayfalandirma
    //tum kayitlari page page listeleyelim
    //http://localhost:8080/students/page?
    //                                    page=1&
    //                                    size=10&
    //                                    sort=name&
    //                                    direction=DESC + GET
    @GetMapping("/page")
    public ResponseEntity<Page<Student>> getAllStudentByPage(//@RequestParam("page") int page, //hangi page gosterilsin ilk deger 0
                                                               @RequestParam(value = "page",required = false,defaultValue = "0") int page,//hangi page gösterilsin ilk değer 0
                                                             //@RequestParam("size") int size, //kac kayit
                                                               @RequestParam(value = "size",required = false,defaultValue = "2") int size,//kaç kayıt
                                                               @RequestParam("sort") String prop, //hangi fielda gore
                                                               @RequestParam("direction") Sort.Direction direction) //siralama yonu
    {
        Pageable pageable = PageRequest.of(page,size,Sort.by(direction,prop));
        Page<Student> studentsByPage= studentService.getAllStudentPageing(pageable);
        return new ResponseEntity<>(studentsByPage,HttpStatus.OK);

    }
    //page,size,sort,direction parametrelerinin girilmesini opsiyonel yapabiliriz.(required=false)
    //parametrelerin girilmesi default oldugunda default value vermeliyiz.(defaultValue = "0")





    //13-lastName ile studentlari listeleyelim.
    //http://localhost:8080/students/querylastname?lastName=Bey
    @GetMapping("/querylastname")
    public ResponseEntity<List<Student>> getAllStudentsByLastName(@RequestParam("lastName") String lastname){

        List<Student> studentList = studentService.getAllStudentByLastName(lastname);
        return ResponseEntity.ok(studentList);

    }

    //15-grade ile studentlari listeleyim. **ODEV**
    //http://localhost:8080/students/grade/99
    @GetMapping("/grade/{grade}")
    public ResponseEntity<List<Student>> getAllStudentByGrade(@PathVariable("grade") Integer grade){

        List<Student> studentList = studentService.getAllStudentByGrade(grade);
        return ResponseEntity.ok(studentList);
    }

    //17-idsi verilen studentın görüntüleme requestine response olarak DTO dönelim
    //http://localhost:8080/students/dto/1 + GET
    @GetMapping("/dto/{id}")
    public ResponseEntity<StudentDTO> getStudentDtoById(@PathVariable("id") Long id){
        StudentDTO studentDTO = studentService.getStudentDtoById(id);

        logger.warn("------------Serviceden StudentDTO objesi alindi: " + studentDTO.getName());

        return ResponseEntity.ok(studentDTO);
    }

    //19-
    @GetMapping("/welcome")
    public String welcome(HttpServletRequest request){

        logger.info("welcome mesaji   {}",request.getServletPath());

        return "WELCOME:)";
    }

}
