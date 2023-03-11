package com.kpi.multithreading.schooljounal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ElectronicJournalTest {

    private Random random = new Random();

    private List<Student> students;

    @BeforeEach
    void generateStudents() {
        students = List.of(
                new Student("Sarra Mahmoudi", 28, "IT-03"),
                new Student("John Hertz", 30, "IT-01"),
                new Student("Jorge Nour", 17, "IT-02"),
                new Student("Christophe Rouge", 30, "IT-04"),
                new Student("Sólveig Zdislav", 41, "IT-01"),
                new Student("Krishna Günter", 21, "IT-02"),
                new Student("Menachem Evyatar", 23, "IT-03"),
                new Student("Yuliya Kelsie", 18, "IT-01"),
                new Student("Amie Sharyn", 20, "IT-03"),
                new Student("Erramun Teódulo", 25, "IT-02"),
                new Student("Tadg Alf", 28, "IT-04")
        );
    }

    @RepeatedTest(100)
    void addGradeForStudentParallel() throws InterruptedException {
        final int people = 4;
        final List<Thread> threads = new ArrayList<>();
        final ElectronicJournal journal = new ElectronicJournal();

        for (int i = 0; i < people; i++) {
            final Thread t = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    journal.addGradeForStudent(students.get(0), this.getGrade());
                }
            });
            t.start();
            threads.add(t);
        }

        for (Thread thread : threads) {
            thread.join();
        }

        assertEquals(people * 1000, journal.getGrades().get(students.get(0)).size());
    }

    private int getGrade() {
        return random.nextInt(1, 13);
    }
}