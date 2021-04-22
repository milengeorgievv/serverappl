package com.uni.serverapp.backend.service;


import com.uni.serverapp.backend.entity.Course;
import com.uni.serverapp.backend.entity.TopCoursesInt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.uni.serverapp.backend.repository.CourseRepository;

import java.util.Collection;
import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;


    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course save(Course course) {
        return courseRepository.save(course);
    }

    public List<Course> saveAll(List<Course> courses) {
        return courseRepository.saveAll(courses);
    }

    public Course getOne(Long id) {
        return courseRepository.getOne(id);
    }

    public List<Course> getAll() {
        return courseRepository.findAll();
    }

    public Page<Course> getAll(int offset, int limit) {
        Pageable pagination = PageRequest.of(Math.floorDiv(offset, limit), limit);

        return courseRepository.findAll(pagination);
    }

    public void deleteOne(Long id) {
        courseRepository.deleteById(id);
    }

    public void deleteOne(Course course) {
        courseRepository.delete(course);
    }

    public void deleteAll(Collection<Course> courses) {
        for (Course course : courses) {
            courseRepository.deleteById(course.getId());
        }
    }

    public long count() {
        return courseRepository.count();
    }
    
    public List<TopCoursesInt> getTopCourses() {
        return courseRepository.findTopCourses();
    }
}

