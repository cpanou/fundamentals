package com.company;

public class Person {
    private int age;
    private String firstName;
    private String lastName;
    private boolean alive;

    public Person() {
    }

    public Person(int age) {
        this.age = age;
    }

    public Person(int age, String firstName, String lastName, boolean isAlive) {
        this.age = age;
        this.firstName = firstName;
        this.lastName = lastName;
        this.alive = isAlive;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void speak() {
        System.out.println("My Name is " + this.firstName + " " + this.lastName + ", I am " + this.age + " years old! And i am alive: " + this.alive);
    }

    public void wave(String othersName) {
        System.out.println(this.firstName + " waves at " + othersName);
    }

}
