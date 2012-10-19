import org.springframework.context.support.ClassPathXmlApplicationContext
import org.springframework.data.mongodb.MongoDbFactory

import com.mongodb.*

firstTime = false
mongoDbFactory = null

if (context.getAttribute('applicationContext') == null) {
    applicationContext = new ClassPathXmlApplicationContext('integration.xml')
    context.setAttribute('applicationContext', applicationContext)
    firstTime = true
} else {
    applicationContext = context.getAttribute('applicationContext')
}

mongoDbFactory = applicationContext.getBean('mongoDbFactory', MongoDbFactory.class)

if (firstTime) {
    // Initialize the database every time the app is run
    mongoDbFactory.db.getCollection('texts').drop()
}

texts = mongoDbFactory.db.getCollection('texts')

charlist = ['A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','1','2','3','4','5','6','7','8','9','0','`',',','.','/',';','\'','-','=','[',']','\\']
countmap = [:]

// Kind of a hack to avoid timeouts
if (texts.count > 30000) {
    texts.drop()
    texts = mongoDbFactory.db.getCollection('texts')
}

// We assign a negative value to this to simplify sorting. We want the
// higher-values to show up first. The ideal scenario is to do a custom sort to
// reverse the list.
charlist.each {
    collectionCount = texts.count([text: it] as BasicDBObject)
    countmap[it] = -collectionCount
}

// sort by value
countmap = countmap.sort { it.value }

// Collect the keys
sortedChars = countmap.collect { it.key }

// Render the HTML
html.html {
    head {
        title "Keyboredom Automated Keyboard Generator"
        link(rel: "stylesheet", type: "text/css", href: "css/style.css")
    }

    body {
        div (id: "Content") {
            h1 "Keyboredom Automatic Keyboard Generator"
                p """
                What if programmers could use a new keyboard layout and immediately
                became up-to-speed with it? In such a scenario the most used keys would
                need to be relocated to obtain optimum efficiency.
                """

                p """
                This webapp looks at the twitter feed from @SpringSource, GitHub, and
                the spring source blog and generates an optimal keyboard layout for
                that information to be reproduced with minimal finger movement. For
                example, the most used letter would be remapped to the right index
                finger in the home row.
                """

                p """
                Refresh your browser for updated data
                """

                // This is hard-coded here because order is important.
                // Otherwise I would loop over the list to create this. The
                // semi-colons are there for vim formatting
                ul (id: "keyboard") {
                    // Number Row
                    li (class: "symbol", sortedChars[34]);
                    li (class: "symbol", sortedChars[35]);
                    li (class: "symbol", sortedChars[36]);
                    li (class: "symbol", sortedChars[37]);
                    li (class: "symbol", sortedChars[38]);
                    li (class: "symbol", sortedChars[39]);
                    li (class: "symbol", sortedChars[40]);
                    li (class: "symbol", sortedChars[41]);
                    li (class: "symbol", sortedChars[42]);
                    li (class: "symbol", sortedChars[43]);
                    li (class: "symbol", sortedChars[44]);
                    li (class: "symbol", sortedChars[45]);
                    li (class: "symbol", sortedChars[46]);
                   
                    li (class:"delete lastitem", "delete");
                    li (class:"tab", "tab");
                    
                    // Top Row;
                    li (class: "letter", sortedChars[23]);
                    li (class: "letter", sortedChars[21]);
                    li (class: "letter", sortedChars[19]);
                    li (class: "letter", sortedChars[17]);
                    li (class: "letter", sortedChars[15]);
                    li (class: "letter", sortedChars[10]);
                    li (class: "letter", sortedChars[12]);
                    li (class: "letter", sortedChars[16]);
                    li (class: "letter", sortedChars[18]);
                    li (class: "letter", sortedChars[20]);
                    li (class: "symbol", sortedChars[31]);
                    li (class: "symbol", sortedChars[32]);
                    li (class: "symbol lastitem", sortedChars[33]);
                    li (class: "capslock", "caps lock");

                    // Home Row (note the low numbers);
                    li (class: "letter", sortedChars[7]);
                    li (class: "letter", sortedChars[5]);
                    li (class: "letter", sortedChars[3]);
                    li (class: "letter", sortedChars[1]);
                    li (class: "letter", sortedChars[9]);
                    li (class: "letter", sortedChars[8]);
                    li (class: "letter", sortedChars[0]);
                    li (class: "letter", sortedChars[2]);
                    li (class: "letter", sortedChars[4]);
                    li (class: "symbol", sortedChars[6]);
                    li (class: "symbol", sortedChars[30]);
                    li (class: "return lastitem", "return");

                    li (class: "left-shift", "shift");
                    li (class: "letter", sortedChars[29]);
                    li (class: "letter", sortedChars[27]);
                    li (class: "letter", sortedChars[25]);
                    li (class: "letter", sortedChars[13]);
                    li (class: "letter", sortedChars[11]);
                    li (class: "letter", sortedChars[14]);
                    li (class: "letter", sortedChars[22]);
                    li (class: "symbol", sortedChars[24]);
                    li (class: "symbol", sortedChars[26]);
                    li (class: "symbol", sortedChars[28]);
                    li (class: "right-shift lastitem", "shift");
                    li (class: "space lastitem", " ");
                }

            div {
                strong "Alphabetical Order:" ;
                p charlist.collect { "\'${it}\': ${countmap[it]}" }.join(" ")

                strong "Sorted:" 
                p countmap.collect { "\'${it.key}\': ${-it.value}" }.join(" ")

                p "The source code can be found here:"
                a(href: "https://github.com/joemccall86/keyboredom", "Keyboredom on GitHub")

                form(action: "reset.groovy", method: "post") {
                    input(type: "submit", value: "Reset the database")
                    input(type: "hidden", name: "really", value: "yesreally")
                }
            }
        }
    }
}

