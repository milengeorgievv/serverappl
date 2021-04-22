package com.uni.serverapp.backend.service;

import com.uni.serverapp.backend.entity.CombinedInt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.uni.serverapp.backend.entity.Student;
import com.uni.serverapp.backend.repository.StudentRepository;

import java.util.Collection;
import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;


    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student save(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> saveAll(List<Student> students) {
        return studentRepository.saveAll(students);
    }

    public Student getOne(Long id) {
        return studentRepository.getOne(id);
    }

    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    public Page<Student> getAll(int offset, int limit) {
        Pageable pagination = PageRequest.of(Math.floorDiv(offset, limit), limit);

        return studentRepository.findAll(pagination);
    }

    public void deleteOne(Long id) {
        studentRepository.deleteById(id);
    }

    public void deleteOne(Student student) {
        studentRepository.delete(student);
    }

    public void deleteAll(Collection<Student> students) {
        for (Student student : students) {
            studentRepository.deleteById(student.getId());
        }
    }

    public long count() {
        return studentRepository.count();
    }

    public List<CombinedInt> getAllStudentsWithCredits() {
        return studentRepository.findAllStudentsWithCredits();
    }
}

