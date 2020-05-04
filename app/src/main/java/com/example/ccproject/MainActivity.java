package com.example.ccproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ccproject.Database.StudRepository;
import com.example.ccproject.Local.StuDataBase;
import com.example.ccproject.Local.StuDataSource;
import com.example.ccproject.Model.Student;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private ListView firstStudent;
    private FloatingActionButton fab;

    //Adapter
    List<Student> studentList = new ArrayList<>();
    ArrayAdapter adapter;

    //Database
    private CompositeDisposable compositeDisposable;
    private StudRepository studRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init
        compositeDisposable = new CompositeDisposable();

        //initView
        firstStudent=(ListView)findViewById(R.id.firstStudent);
        fab=(FloatingActionButton)findViewById(R.id.fab);

        adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,studentList);
        registerForContextMenu(firstStudent);
        firstStudent.setAdapter(adapter);

        //Database
        StuDataBase stuDataBase = StuDataBase.getInstance(this); //Create Database
        studRepository = StudRepository.getInstance(StuDataSource.getInstance(stuDataBase.studentDao()));

        //load from Database
        loadData();

        //event
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add new student
                //random email
                Disposable disposable = Observable.create(new ObservableOnSubscribe<Object>() {
                    @Override
                    public void subscribe(ObservableEmitter<Object> e) throws Exception{
                            Student student = new Student("Elisabeth",
                                    UUID.randomUUID().toString()+"@mail.com");
                            studRepository.insertStud(student);
                            e.onComplete();
                    }
                })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Consumer() {
                                       @Override
                                       public void accept(Object o) throws Exception {
                                           Toast.makeText(MainActivity.this, "Student added", Toast.LENGTH_SHORT).show();
                                       }
                                   }, new Consumer<Throwable>() {
                                       @Override
                                       public void accept(Throwable throwable) throws Exception {
                                           Toast.makeText(MainActivity.this, "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                       }
                                   }, new Action() {
                                       @Override
                                       public void run() throws Exception {
                                           loadData(); //refresh Data
                                       }
                                   }
                        );

            }
        });


    }

    private void loadData() {
        //RxJava
        Disposable disposable =studRepository.getAllStudents()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<Student>>() {
                               @Override
                               public void accept(List<Student> students) throws Exception {
                                   onGetAllStudSuccess(students);
                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                                   Toast.makeText(MainActivity.this, ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                               }
                           }
                );
        compositeDisposable.add(disposable);


    }

    private void onGetAllStudSuccess(List<Student> students) {
        studentList.clear();
        studentList.addAll(students);
        adapter.notifyDataSetChanged();
    }
}
