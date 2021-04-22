package com.uni.serverapp.backend.repository;

import com.uni.serverapp.backend.entity.TopCoursesInt;
import org.springframework.data.jpa.repository.JpaRepository;
import com.uni.serverapp.backend.entity.Course;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
        @Query(value = "SELECT name AS name, credits AS credits, COUNT(student_id) AS students FROM course\n" +
                "    JOIN course_student cs ON course.id = cs.course_id\n" +
                "GROUP BY course_id ORDER BY COUNT(student_id) DESC LIMIT 3", //5
                nativeQuery = true)
        List<TopCoursesInt> findTopCourses();
}

