package com.kpi.multithreading.schooljounal;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class ElectronicJournal {

    private final  Map<Student, List<Integer>> grades;

    public ElectronicJournal(Set<Student> students) {
        this.grades = students
                .stream()
                .collect(Collectors.toMap(student -> student, student -> new ArrayList<>()));
    }

    public void addGradeForStudent(Student s, Integer grade) {
        final List<Integer> studentGrades = grades.get(s);

        if (studentGrades == null) {
            throw new IllegalArgumentException("Unknown Student: " + s.getName());
        } else {
            synchronized (studentGrades) {
                studentGrades.add(grade);
            }
        }
    }

    public Map<Student, List<Integer>> getGrades() {
        return grades;
    }
}
