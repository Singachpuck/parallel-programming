package com.kpi.multithreading.schooljounal;

public class Student {

    private final String name;

    private final int age;

    private final String group;

    public Student(String name, int age, String group) {
        this.name = name;
        this.age = age;
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGroup() {
        return group;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s(%d)", group, name, age);
    }
}
