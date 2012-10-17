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
        // This is hard-coded here because order is important. Otherwise I
        // would loop over the list to create this.
        ul (id: "keyboard") {
            // Number Row
            li (class: "symbol", sortedChars[34])
            li (class: "symbol", sortedChars[35])
            li (class: "symbol", sortedChars[36])
            li (class: "symbol", sortedChars[37])
            li (class: "symbol", sortedChars[38])
            li (class: "symbol", sortedChars[39])
            li (class: "symbol", sortedChars[40])
            li (class: "symbol", sortedChars[41])
            li (class: "symbol", sortedChars[42])
            li (class: "symbol", sortedChars[43])
            li (class: "symbol", sortedChars[44])
            li (class: "symbol", sortedChars[45])
            li (class: "symbol", sortedChars[46])

            li (class:"delete lastitem", "delete")
            li (class:"tab", "tab")

            // Top Row
            li (class: "letter", sortedChars[23])
            li (class: "letter", sortedChars[21])
            li (class: "letter", sortedChars[19])
            li (class: "letter", sortedChars[17])
            li (class: "letter", sortedChars[15])
            li (class: "letter", sortedChars[10])
            li (class: "letter", sortedChars[12])
            li (class: "letter", sortedChars[16])
            li (class: "letter", sortedChars[18])
            li (class: "letter", sortedChars[20])
            li (class: "symbol", sortedChars[31])
            li (class: "symbol", sortedChars[32])
            li (class: "symbol lastitem", sortedChars[33])
            li (class: "capslock", "caps lock")

            // Home Row (note the low numbers)
            li (class: "letter", sortedChars[7])
            li (class: "letter", sortedChars[5])
            li (class: "letter", sortedChars[3])
            li (class: "letter", sortedChars[1])
            li (class: "letter", sortedChars[9])
            li (class: "letter", sortedChars[8])
            li (class: "letter", sortedChars[0])
            li (class: "letter", sortedChars[2])
            li (class: "letter", sortedChars[4])
            li (class: "symbol", sortedChars[6])
            li (class: "symbol", sortedChars[30])
            li (class: "return lastitem", "return")

            li (class: "left-shift", "shift")
            li (class: "letter", sortedChars[29])
            li (class: "letter", sortedChars[27])
            li (class: "letter", sortedChars[25])
            li (class: "letter", sortedChars[13])
            li (class: "letter", sortedChars[11])
            li (class: "letter", sortedChars[14])
            li (class: "letter", sortedChars[22])
            li (class: "symbol", sortedChars[24])
            li (class: "symbol", sortedChars[26])
            li (class: "symbol", sortedChars[28])
            li (class: "right-shift lastitem", "shift")
            li (class: "space lastitem", " ")
        }

        strong "Alphabetical Order:" 
        p charlist.collect { "\'${it}\': ${countmap[it]}" }.join(" ")
        
        strong "Sorted:" 
        p countmap.collect { "\'${it.key}\': ${-it.value}" }.join(" ")
    }
}

//println """
//<div id="container">
	//<ul id="keyboard">
		//<li class="symbol"><span class="off">${sortedChars[34]}</span><span class="on">~</span></li>
		//<li class="symbol"><span class="off">${sortedChars[35]}</span><span class="on">!</span></li>
		//<li class="symbol"><span class="off">${sortedChars[36]}</span><span class="on">@</span></li>
		//<li class="symbol"><span class="off">${sortedChars[37]}</span><span class="on">#</span></li>
		//<li class="symbol"><span class="off">${sortedChars[38]}</span><span class="on">\$</span></li>
		//<li class="symbol"><span class="off">${sortedChars[39]}</span><span class="on">%</span></li>
		//<li class="symbol"><span class="off">${sortedChars[40]}</span><span class="on">^</span></li>
		//<li class="symbol"><span class="off">${sortedChars[41]}</span><span class="on">&amp;</span></li>
		//<li class="symbol"><span class="off">${sortedChars[42]}</span><span class="on">*</span></li>
		//<li class="symbol"><span class="off">${sortedChars[43]}</span><span class="on">(</span></li>
		//<li class="symbol"><span class="off">${sortedChars[44]}</span><span class="on">)</span></li>
		//<li class="symbol"><span class="off">${sortedChars[45]}</span><span class="on">_</span></li>
		//<li class="symbol"><span class="off">${sortedChars[46]}</span><span class="on">+</span></li>
		//<li class="delete lastitem">delete</li>
		//<li class="tab">tab</li>
		//<li class="letter">${sortedChars[23]}</li>
		//<li class="letter">${sortedChars[21]}</li>
		//<li class="letter">${sortedChars[19]}</li>
		//<li class="letter">${sortedChars[17]}</li>
		//<li class="letter">${sortedChars[15]}</li>
		//<li class="letter">${sortedChars[10]}</li>
		//<li class="letter">${sortedChars[12]}</li>
		//<li class="letter">${sortedChars[16]}</li>
		//<li class="letter">${sortedChars[18]}</li>
		//<li class="letter">${sortedChars[20]}</li>
		//<li class="symbol"><span class="off">${sortedChars[31]}</span><span class="on">{</span></li>
		//<li class="symbol"><span class="off">${sortedChars[32]}</span><span class="on">}</span></li>
		//<li class="symbol lastitem"><span class="off">${sortedChars[33]}</span><span class="on">|</span></li>
		//<li class="capslock">caps lock</li>
		//<li class="letter">${sortedChars[7]}</li>
		//<li class="letter">${sortedChars[5]}</li>
		//<li class="letter">${sortedChars[3]}</li>
		//<li class="letter">${sortedChars[1]}</li>
		//<li class="letter">${sortedChars[9]}</li>
		//<li class="letter">${sortedChars[8]}</li>
		//<li class="letter">${sortedChars[0]}</li>
		//<li class="letter">${sortedChars[2]}</li>
		//<li class="letter">${sortedChars[4]}</li>
		//<li class="symbol"><span class="off">${sortedChars[6]}</span><span class="on">:</span></li>
		//<li class="symbol"><span class="off">${sortedChars[30]}</span><span class="on">&quot;</span></li>
		//<li class="return lastitem">return</li>
		//<li class="left-shift">shift</li>
		//<li class="letter">${sortedChars[29]}</li>
		//<li class="letter">${sortedChars[27]}</li>
		//<li class="letter">${sortedChars[25]}</li>
		//<li class="letter">${sortedChars[13]}</li>
		//<li class="letter">${sortedChars[11]}</li>
		//<li class="letter">${sortedChars[14]}</li>
		//<li class="letter">${sortedChars[22]}</li>
		//<li class="symbol"><span class="off">${sortedChars[24]}</span><span class="on">&lt;</span></li>
		//<li class="symbol"><span class="off">${sortedChars[26]}</span><span class="on">&gt;</span></li>
		//<li class="symbol"><span class="off">${sortedChars[28]}</span><span class="on">?</span></li>
		//<li class="right-shift lastitem">shift</li>
		//<li class="space lastitem">&nbsp;</li>
	//</ul>
//</div>

//"""

