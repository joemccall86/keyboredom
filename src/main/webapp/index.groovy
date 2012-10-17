import org.springframework.context.support.ClassPathXmlApplicationContext

firstTime = false

if (context.getAttribute('applicationContext') == null) {
    context.setAttribute('applicationContext', new ClassPathXmlApplicationContext('integration.xml'))
    firstTime = true
}

html.html {
    head {
        title("Groovy Serlet")
    }

    body {
        p("firstTime = ${firstTime}")
    }
}

