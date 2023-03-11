package com.kpi.multithreading.schooljounal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ElectronicJournal {

    private final  Map<Student, List<Integer>> grades = new HashMap<>();

    private final Lock lock = new ReentrantLock();

    public void addGradeForStudent(Student s, Integer grade) {
        try {
            lock.lock();
            List<Integer> studentGrades = grades.get(s);

            if (studentGrades == null) {
                final List<Integer> g = new ArrayList<>();
                g.add(grade);
                grades.put(s, g);
            } else {
                studentGrades.add(grade);
            }
        } finally {
            lock.unlock();
        }
    }

    public Map<Student, List<Integer>> getGrades() {
        return grades;
    }
}
