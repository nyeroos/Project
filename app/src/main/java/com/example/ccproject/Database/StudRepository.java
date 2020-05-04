package com.example.ccproject.Database;

import com.example.ccproject.Model.Student;

import java.util.List;

import io.reactivex.Flowable;

public class StudRepository implements DataSource {

    private DataSource locDataSource;

    private static StudRepository Instance;

    public StudRepository(DataSource locDataSource){
        this.locDataSource=locDataSource;
    }

    public static StudRepository getInstance(DataSource locDataSource){
        if(Instance==null){
            Instance=new StudRepository(locDataSource);
        }
        return Instance;
    }

    @Override
    public Flowable<Student> getStudentById(int studentId) {
        return locDataSource.getStudentById(studentId);
    }

    @Override
    public Flowable<List<Student>> getAllStudents() {
        return locDataSource.getAllStudents();
    }

    @Override
    public void insertStud(Student... students) {
        locDataSource.insertStud(students);
    }

    @Override
    public void updateStud(Student... students) {
        locDataSource.updateStud(students);
    }

    @Override
    public void deleteStud(Student student) {
        locDataSource.deleteStud(student);
    }

    @Override
    public void deleteAllStudents() {
        locDataSource.deleteAllStudents();
    }
}
