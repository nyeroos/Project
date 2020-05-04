package com.example.ccproject.Database;

import com.example.ccproject.Model.Student;

import java.util.List;

import io.reactivex.Flowable;

public interface DataSource {
    Flowable<Student> getStudentById(int studentId);
    Flowable<List<Student>> getAllStudents();
    void insertStud(Student...students);
    void updateStud(Student...students);
    void deleteStud(Student student);
    void deleteAllStudents();
}
