package com.tutort.assignments.Practice;

class Parent {
    static {
        System.out.println("static block of Parent");
    }

    {
        System.out.println("parent initialization block");
    }

    public Parent() {
        System.out.println("yoyo");
    }

    public void M1() {
        System.out.println("inside m1 of class P");
    }
}

class Child extends Parent {
    static {
        System.out.println("static block of Child");
    }

    {
        System.out.println("child initialization block");
    }

    public void M2() {
        System.out.println("inside m2 of class C");
    }
//    @Override
//    public void M1(){
//        System.out.println("inside m1 of class C");
//    }
}

class Aa {
    public void print() {
        System.out.println("printing A");
    }
}

class Bb extends Aa {
    public void print() {
        System.out.println("printing B");
    }
}

class Cc extends Bb {
    public void print() {
        super.print();
        System.out.println("printing C");
    }
}

public class InheritanceIQ {
    public static void main(String[] args) {
//        Parent p = new Parent(); // creating a object of P class
//        p.M1(); // inside m1 of class P
//        p.M2(); // error
        Child c = new Child(); //creating an object of C class
//        c.M1(); // inside m1 of class P
//        c.M2(); // inside m2 of class C
//        Parent p1 = new Child(); // p1 is an object reference of class Child
//        p1.M1(); // inside m1 of class P -> before overriding
        // inside m1 of class C -> after overriding
//        p1.M2(); // error
//        ((Child) p1).M2(); // inside m2 of class C using typecasting

//        Child c1 = (Child) new Parent(); // Exception in thread "main" java.lang.ClassCastException: Parent cannot be cast to Child
//        System.out.println("implementing c1");
//        c1.M1();
//        c1.M2();
        System.out.println("-----------------------------");
        int num = 0;
        System.out.println(num);
        Cc cc = new Cc();
        cc.print();
    }
}
