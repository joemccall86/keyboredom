import org.springframework.context.support.ClassPathXmlApplicationContext
import org.springframework.data.mongodb.MongoDbFactory

import com.mongodb.*

if (params.really == "yesreally") {
    applicationContext = context.getAttribute('applicationContext')
    mongoDbFactory = applicationContext.getBean('mongoDbFactory', MongoDbFactory.class)
    mongoDbFactory.db.getCollection('texts').drop()

    html.html{
        head {
            title "Keyboredom Automated Keyboard Generator"
            link(rel: "stylesheet", type: "text/css", href: "css/style.css")
        }

        body {
            div (id: "Content") {
                h1 "Database reset"
                a(href:"index.groovy", "Click here to go back")
            }
        }
    }
}

