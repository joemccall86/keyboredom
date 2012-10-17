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

// TODO This could probably be cleaner using html builder, but this works for
// now
println """
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	
	<title>Online Keyboard</title>
	<link rel="stylesheet" type="text/css" href="css/style.css" />
</head>
<body>
"""

// We assign a negative value to this to simplify sorting. We want the
// higher-values to show up first. The ideal scenario is to do a custom sort to
// reverse the list.
charlist.each {
    collectionCount = texts.count([text: it] as BasicDBObject)
    countmap[it] = -collectionCount
}

// sort by value
countmap = countmap.sort { it.value }

sortedChars = countmap.collect { it.key }

println """
<div id="container">
	<ul id="keyboard">
		<li class="symbol"><span class="off">${sortedChars[34]}</span><span class="on">~</span></li>
		<li class="symbol"><span class="off">${sortedChars[35]}</span><span class="on">!</span></li>
		<li class="symbol"><span class="off">${sortedChars[36]}</span><span class="on">@</span></li>
		<li class="symbol"><span class="off">${sortedChars[37]}</span><span class="on">#</span></li>
		<li class="symbol"><span class="off">${sortedChars[38]}</span><span class="on">\$</span></li>
		<li class="symbol"><span class="off">${sortedChars[39]}</span><span class="on">%</span></li>
		<li class="symbol"><span class="off">${sortedChars[40]}</span><span class="on">^</span></li>
		<li class="symbol"><span class="off">${sortedChars[41]}</span><span class="on">&amp;</span></li>
		<li class="symbol"><span class="off">${sortedChars[42]}</span><span class="on">*</span></li>
		<li class="symbol"><span class="off">${sortedChars[43]}</span><span class="on">(</span></li>
		<li class="symbol"><span class="off">${sortedChars[44]}</span><span class="on">)</span></li>
		<li class="symbol"><span class="off">${sortedChars[45]}</span><span class="on">_</span></li>
		<li class="symbol"><span class="off">${sortedChars[46]}</span><span class="on">+</span></li>
		<li class="delete lastitem">delete</li>
		<li class="tab">tab</li>
		<li class="letter">${sortedChars[23]}</li>
		<li class="letter">${sortedChars[21]}</li>
		<li class="letter">${sortedChars[19]}</li>
		<li class="letter">${sortedChars[17]}</li>
		<li class="letter">${sortedChars[15]}</li>
		<li class="letter">${sortedChars[10]}</li>
		<li class="letter">${sortedChars[12]}</li>
		<li class="letter">${sortedChars[16]}</li>
		<li class="letter">${sortedChars[18]}</li>
		<li class="letter">${sortedChars[20]}</li>
		<li class="symbol"><span class="off">${sortedChars[31]}</span><span class="on">{</span></li>
		<li class="symbol"><span class="off">${sortedChars[32]}</span><span class="on">}</span></li>
		<li class="symbol lastitem"><span class="off">${sortedChars[33]}</span><span class="on">|</span></li>
		<li class="capslock">caps lock</li>
		<li class="letter">${sortedChars[7]}</li>
		<li class="letter">${sortedChars[5]}</li>
		<li class="letter">${sortedChars[3]}</li>
		<li class="letter">${sortedChars[1]}</li>
		<li class="letter">${sortedChars[9]}</li>
		<li class="letter">${sortedChars[8]}</li>
		<li class="letter">${sortedChars[0]}</li>
		<li class="letter">${sortedChars[2]}</li>
		<li class="letter">${sortedChars[4]}</li>
		<li class="symbol"><span class="off">${sortedChars[6]}</span><span class="on">:</span></li>
		<li class="symbol"><span class="off">${sortedChars[30]}</span><span class="on">&quot;</span></li>
		<li class="return lastitem">return</li>
		<li class="left-shift">shift</li>
		<li class="letter">${sortedChars[29]}</li>
		<li class="letter">${sortedChars[27]}</li>
		<li class="letter">${sortedChars[25]}</li>
		<li class="letter">${sortedChars[13]}</li>
		<li class="letter">${sortedChars[11]}</li>
		<li class="letter">${sortedChars[14]}</li>
		<li class="letter">${sortedChars[22]}</li>
		<li class="symbol"><span class="off">${sortedChars[24]}</span><span class="on">&lt;</span></li>
		<li class="symbol"><span class="off">${sortedChars[26]}</span><span class="on">&gt;</span></li>
		<li class="symbol"><span class="off">${sortedChars[28]}</span><span class="on">?</span></li>
		<li class="right-shift lastitem">shift</li>
		<li class="space lastitem">&nbsp;</li>
	</ul>
</div>

"""

println "<strong>Alphabetical Order:</strong><br/>"
charlist.each {
    println "\'${it}\': ${countmap[it]}   "
}
println "<br/>"

println "<strong>Sorted:</strong><br/>"
countmap.each {
    println "\'${it.key}\': ${-it.value}   "
}

println """
</body>
</html>
"""

