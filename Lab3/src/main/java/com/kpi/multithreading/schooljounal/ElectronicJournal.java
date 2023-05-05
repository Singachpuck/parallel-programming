package com.kpi.multithreading.schooljounal;

import java.util.*;
import java.util.stream.Collectors;

public class ElectronicJournal {

    private final Map<Student, List<Integer>> grades;

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
            System.out.println("Added grade " + grade + " to student " + s);
        }
    }

    public Map<Student, List<Integer>> getGrades() {
        return grades;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        grades.keySet()
                .stream()
                .collect(Collectors.groupingBy(Student::getGroup))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .forEachOrdered((groupEntry) -> {
            final String studentByGroup = groupEntry
                    .getValue()
                    .stream()
                    .sorted(Comparator.comparing(Student::getName))
                    .map((s) -> s + ": " + grades.get(s).toString())
                    .collect(Collectors.joining("\n", "", "\n\n"));

            sb.append(studentByGroup);
        });
        return sb.toString();
    }
}
