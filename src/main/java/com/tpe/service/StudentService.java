package com.tpe.service;

import com.tpe.domain.Student;
import com.tpe.dto.StudentDTO;
import com.tpe.exception.ConflictException;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    //2-
    public List<Student> getAllStudent() {
        //List<Student> students = studentRepository.findAll(); //select * from Student
        //return students;
        return studentRepository.findAll();
    }

    //4-
    public void saveStudent(Student student) {
        //student dah once kaydedilmis mi --> ayni emaile sahip student var mi?

        if(studentRepository.existsByEmail(student.getEmail())){
            throw new ConflictException("Email is already exist!");
        }
        studentRepository.save(student);
    }

    //6-
    public Student getStudentById(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Student not found by id: " + id));
        return student;
    }

    //8-
    public void deleteStudent(Long id) {
        //bu id ye sahip bir student var mi?
        Student foundStudent = getStudentById(id);
        studentRepository.delete(foundStudent);
    }

    //10-
    public void updateStudent(Long id, StudentDTO studentDTO) {
        //gelen id ile student var mi? varsa bulalim
        Student foundStudent = getStudentById(id);

        //studentDTO.getEmail() zaten daha onceden DB de varsa??
        boolean existsEmail = studentRepository.existsByEmail(studentDTO.getEmail());

        //existsEmail true ise bu email baska bir studentin olabilir, studentin kendi emaili olabilir??
        // id:3 student email:a@email.com
        //dto                student
        //b@email.com        id:4 b@email.com -> existEmail:true -->exception  - seneryo 1
        //c@email.com        DB de yok   ------> existEmail:false --> update:OK  - seneryo 2
        //a@email.com        id:3 a@email.com -> existEmail:true --> update:OK  - seneryo 3



        if (existsEmail && !foundStudent.getEmail().equals(studentDTO.getEmail())){
            throw new ConflictException("Email already exist!!!");
        }

        foundStudent.setName(studentDTO.getName());
        foundStudent.setLastName(studentDTO.getLastName());
        foundStudent.setGrade(studentDTO.getGrade());
        foundStudent.setPhoneNumber(studentDTO.getPhoneNumber());
        foundStudent.setEmail(studentDTO.getEmail());

        studentRepository.save(foundStudent); //saveOtUpdate gibi işlem yapar
    }

    //12-
    public Page<Student> getAllStudentPageing(Pageable pageable) {

        return studentRepository.findAll(pageable);
    }

    //14-
    public List<Student> getAllStudentByLastName(String lastname) {

        return studentRepository.findAllByLastName(lastname);
    }
}
