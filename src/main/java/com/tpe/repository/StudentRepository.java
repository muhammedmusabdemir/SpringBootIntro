package com.tpe.repository;

import com.tpe.domain.Student;
import com.tpe.dto.StudentDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository //opsiyonel
public interface StudentRepository extends JpaRepository<Student,Long> { //JpaRepository<Entity Class,Id nin data tipi>
    Boolean existsByEmail(String email); //bu emaile sahip kayit varsa TRUE yoksa FAlSE


    List<Student> findAllByLastName(String lastname); //findAllById

    List<Student> findAllByGrade(Integer grade);

    //JPQL
      @Query("SELECT s FROM Student s WHERE s.grade=:pGrade")
//    @Query("FROM Student s WHERE s.grade=:pGrade")
      List<Student> findAllGradeEquals(@Param("pGrade") Integer grade);


    //SQL
//    @Query(value = "SELECT * FROM student s WHERE s.grade=:pGrade",nativeQuery = true) //Student
//    List<Student> findAllGradeEquals(@Param("pGrade") Integer grade);


    //DB den gelen studenti DTO ya cevirerek gonderiyor
    @Query("SELECT new com.tpe.dto.StudentDTO(s) FROM Student s WHERE s.id=:pId")
    Optional<StudentDTO> findStudentDtoById(@Param("pId") Long id);



}
