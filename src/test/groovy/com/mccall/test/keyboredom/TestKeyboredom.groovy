package com.mccall.test.keyboredom

import org.junit.Test
import org.springframework.context.support.ClassPathXmlApplicationContext

class TestKeyboredom {
    @Test
    void entryPoint() {
        println 'Loading Application Context XML...'
        new ClassPathXmlApplicationContext("integration.xml")
        Thread.sleep(5000)
    }
}

