package com.uni.serverapp.backend.service;

import com.uni.serverapp.backend.entity.CombinedInt;
import com.uni.serverapp.backend.entity.TeacherInt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.uni.serverapp.backend.entity.Teacher;
import com.uni.serverapp.backend.repository.TeacherRepository;

import java.util.Collection;
import java.util.List;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;


    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public Teacher save(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    public List<Teacher> saveAll(List<Teacher> teachers) {
        return teacherRepository.saveAll(teachers);
    }

    public Teacher getOne(Long id) {
        return teacherRepository.getOne(id);
    }

    public List<Teacher> getAll() {
        return teacherRepository.findAll();
    }

    public Page<Teacher> getAll(int offset, int limit) {
        Pageable pagination = PageRequest.of(Math.floorDiv(offset, limit), limit);

        return teacherRepository.findAll(pagination);
    }

    public void deleteOne(Long id) {
        teacherRepository.deleteById(id);
    }

    public void deleteOne(Teacher teacher) {
        teacherRepository.delete(teacher);
    }

    public void deleteAll(Collection<Teacher> teachers) {
        for (Teacher teacher : teachers) {
            teacherRepository.deleteById(teacher.getId());
        }
    }

    public long count() {
        return teacherRepository.count();
    }

    public List<CombinedInt> getTopTeachers() {
        return teacherRepository.findTopTeachers();
    }

    public List<CombinedInt> getAllPeople() {
        return teacherRepository.findAllPeople();
    }
    
    public List<TeacherInt> getAllTeachersWithCoursesAndStudents() {
        return teacherRepository.findAllTeachersWithCoursesAndStudents();
    }

}

