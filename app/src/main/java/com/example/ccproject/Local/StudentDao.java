package com.example.ccproject.Local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ccproject.Model.Student;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface StudentDao {

    @Query("SELECT * FROM students WHERE id=:studentId")
    Flowable<Student> getStudentById(int studentId);

    @Query("SELECT * FROM students")
    Flowable<List<Student>> getAllStudents();

    @Insert
    void insertStud(Student...students);

    @Update
    void updateStud(Student...students);

    @Delete
    void deleteStud(Student student);

    @Query("DELETE FROM students")
    void deleteAllStudents();


}
