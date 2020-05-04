package com.example.ccproject.Local;

import com.example.ccproject.Database.DataSource;
import com.example.ccproject.Model.Student;

import java.util.List;

import io.reactivex.Flowable;

public class StuDataSource implements DataSource {

    private StudentDao studentDao;
    private static StuDataSource Instance;

    public StuDataSource(StudentDao studentDao){
        this.studentDao=studentDao;
    }

    public static StuDataSource getInstance(StudentDao studentDao){
        if (Instance==null){
            Instance=new StuDataSource(studentDao);
        }
        return Instance;
    }

    @Override
    public Flowable<Student> getStudentById(int studentId) {
        return studentDao.getStudentById(studentId);
    }

    @Override
    public Flowable<List<Student>> getAllStudents() {
        return studentDao.getAllStudents();
    }

    @Override
    public void insertStud(Student... students) {
        studentDao.insertStud(students);
    }

    @Override
    public void updateStud(Student... students) {
        studentDao.updateStud(students);
    }

    @Override
    public void deleteStud(Student student) {
        studentDao.deleteStud(student);
    }

    @Override
    public void deleteAllStudents() {
        studentDao.deleteAllStudents();
    }
}
