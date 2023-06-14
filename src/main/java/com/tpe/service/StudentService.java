package com.tpe.service;

import com.tpe.domain.Student;
import com.tpe.exception.ConflictException;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
}
