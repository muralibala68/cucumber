package org.bala.time;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;

public class Main {
    public static void main(String[] argv) {
        System.out.println("Hello, World!");
        
        Period p = Period.of(1, 2, 3);
        System.out.println("Period:" + p.toString());

        Duration d = Duration.ofSeconds(5, 2);
        System.out.println("Duration:" + d.toString());
        
        LocalDateTime ldt = LocalDateTime.now();
        System.out.println("Now:" + ldt.toString());
        System.out.println("Now.plus(p):" + ldt.plus(p).toString());
        System.out.println("Now.plus(d):" + ldt.plus(d).toString());
    }
}
