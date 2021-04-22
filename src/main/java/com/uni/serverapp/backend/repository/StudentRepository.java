package com.uni.serverapp.backend.repository;

import com.uni.serverapp.backend.entity.CombinedInt;
import org.springframework.data.jpa.repository.JpaRepository;
import com.uni.serverapp.backend.entity.Student;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query(value = "SELECT student.first_name AS firstName, student.last_name AS lastName, sum(credits) AS credits FROM student\n" +
            "    JOIN course_student cs on student.id = cs.student_id\n" +
            "    JOIN course c ON c.id = cs.course_id GROUP BY student_id;", //3
            nativeQuery = true)
    List<CombinedInt> findAllStudentsWithCredits();
}

