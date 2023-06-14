package com.tpe.repository;

import com.tpe.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository //opsiyonel
public interface StudentRepository extends JpaRepository<Student,Long> { //JpaRepository<Entity Class,Id nin data tipi>
    Boolean existsByEmail(String email); //bu emaile sahip kayit varsa TRUE yoksa FAlSE



}
