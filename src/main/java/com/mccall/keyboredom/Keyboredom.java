package com.mccall.keyboredom;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Keyboredom {
    public static void main(String args[]) {
        System.out.println("Loading application context...");
        new ClassPathXmlApplicationContext("integration.xml");
    }
}

