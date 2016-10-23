package lab.mars.m2m.rxjavalearn;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by zk.
 * Date: 2016/10/23
 * Email: zhkai0427@gmail.com
 */

public class Test {
    public static void main(String[] args) {

        Classroom classroom = new Classroom();
        Student student1 = new Student();
        Student student2 = new Student();
        Student student3 = new Student();
        student1.name = "A";
        student2.name = "B";
        student3.name = "C";

        List<Student> list = new ArrayList<>();
        list.add(student1);
        list.add(student2);
        list.add(student3);
        classroom.student = list;
        classroom.name = "ClassA";


        Observable.just(classroom)
                .flatMap(new Func1<Classroom, Observable<Student>>() {
                    @Override
                    public Observable<Student> call(Classroom classroom) {
                        return Observable.from(classroom.student);
                    }
                })
                .subscribe(new Action1<Student>() {
                    @Override
                    public void call(Student student) {
                        System.out.println(1);
                        System.out.println(student.name);
                    }
                });



    }
}
