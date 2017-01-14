package org.bala.binding;

public class Main {
    public static void main(String[] argv) {
        System.out.println("Hello, World!");
        ClassA a = new ClassA();
        ClassB b = new ClassB();
        ClassA c = new ClassB();
        
        // all static, private and final methods are statically bound (like overloaded methods)
        // all other methods are dynamically bound (like overridden methods)
        a.method();
        b.method();
        c.method();
    }
}
