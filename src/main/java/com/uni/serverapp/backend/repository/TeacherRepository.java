package com.uni.serverapp.backend.repository;

import com.uni.serverapp.backend.entity.CombinedInt;
import com.uni.serverapp.backend.entity.TeacherInt;
import org.springframework.data.jpa.repository.JpaRepository;
import com.uni.serverapp.backend.entity.Teacher;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    @Query(value = "SELECT t.first_name AS firstName, t.last_name AS lastName, \n" +
            "       COUNT(student_id) AS credits FROM teacher t\n" +
            "    JOIN course c ON t.id = c.teacher_id\n" +
            "    JOIN course_student cs ON c.id = cs.course_id\n" +
            "    JOIN student s ON cs.student_id = s.id\n" +
            "    GROUP BY t.id ORDER BY COUNT(student_id) DESC LIMIT 3", //6
    nativeQuery = true)
    List<CombinedInt> findTopTeachers();

    @Query(value = "SELECT first_name AS firstName, last_name AS lastName, COUNT(name) AS credits FROM teacher " +
            "JOIN course c ON teacher.id = c.teacher_id GROUP BY teacher_id " +
            "UNION " +
            "SELECT first_name, last_name, year_of_study FROM student", //1
            nativeQuery = true)
    List<CombinedInt> findAllPeople();

    @Query(value = "SELECT teacher.first_name AS firstName, teacher.last_name AS lastName,\n" +
            "       c.name AS name, COUNT(s.id) AS students FROM teacher\n" +
            "     JOIN course c ON teacher.id = c.teacher_id\n" +
            "    JOIN course_student cs ON c.id = cs.course_id\n" +
            "    JOIN student s ON cs.student_id = s.id GROUP BY course_id;", //4
    nativeQuery = true)
    List<TeacherInt> findAllTeachersWithCoursesAndStudents();
}
