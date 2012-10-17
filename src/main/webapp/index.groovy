import org.springframework.context.support.ClassPathXmlApplicationContext
import org.springframework.data.mongodb.MongoDbFactory

firstTime = false

if (context.getAttribute('applicationContext') == null) {
    applicationContext = new ClassPathXmlApplicationContext('integration.xml')
    context.setAttribute('applicationContext', applicationContext)
    firstTime = true

    // Initialize the database every time the app is run
    applicationContext.getBean('mongoDbFactory', MongoDbFactory.class).db.getCollection('texts').drop()
}

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

<div id="container">
	<ul id="keyboard">
		<li class="symbol"><span class="off">35</span><span class="on">~</span></li>
		<li class="symbol"><span class="off">36</span><span class="on">!</span></li>
		<li class="symbol"><span class="off">37</span><span class="on">@</span></li>
		<li class="symbol"><span class="off">38</span><span class="on">#</span></li>
		<li class="symbol"><span class="off">39</span><span class="on">\$</span></li>
		<li class="symbol"><span class="off">40</span><span class="on">%</span></li>
		<li class="symbol"><span class="off">41</span><span class="on">^</span></li>
		<li class="symbol"><span class="off">42</span><span class="on">&amp;</span></li>
		<li class="symbol"><span class="off">43</span><span class="on">*</span></li>
		<li class="symbol"><span class="off">44</span><span class="on">(</span></li>
		<li class="symbol"><span class="off">45</span><span class="on">)</span></li>
		<li class="symbol"><span class="off">46</span><span class="on">_</span></li>
		<li class="symbol"><span class="off">47</span><span class="on">+</span></li>
		<li class="delete lastitem">delete</li>
		<li class="tab">tab</li>
		<li class="letter">24</li>
		<li class="letter">22</li>
		<li class="letter">20</li>
		<li class="letter">18</li>
		<li class="letter">16</li>
		<li class="letter">11</li>
		<li class="letter">13</li>
		<li class="letter">17</li>
		<li class="letter">19</li>
		<li class="letter">21</li>
		<li class="symbol"><span class="off">32</span><span class="on">{</span></li>
		<li class="symbol"><span class="off">33</span><span class="on">}</span></li>
		<li class="symbol lastitem"><span class="off">34</span><span class="on">|</span></li>
		<li class="capslock">caps lock</li>
		<li class="letter">8</li>
		<li class="letter">6</li>
		<li class="letter">4</li>
		<li class="letter">2</li>
		<li class="letter">10</li>
		<li class="letter">9</li>
		<li class="letter">1</li>
		<li class="letter">3</li>
		<li class="letter">5</li>
		<li class="symbol"><span class="off">7</span><span class="on">:</span></li>
		<li class="symbol"><span class="off">31</span><span class="on">&quot;</span></li>
		<li class="return lastitem">return</li>
		<li class="left-shift">shift</li>
		<li class="letter">30</li>
		<li class="letter">28</li>
		<li class="letter">26</li>
		<li class="letter">14</li>
		<li class="letter">12</li>
		<li class="letter">15</li>
		<li class="letter">23</li>
		<li class="symbol"><span class="off">25</span><span class="on">&lt;</span></li>
		<li class="symbol"><span class="off">27</span><span class="on">&gt;</span></li>
		<li class="symbol"><span class="off">29</span><span class="on">?</span></li>
		<li class="right-shift lastitem">shift</li>
		<li class="space lastitem">0</li>
	</ul>
</div>

<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js"></script>
<script type="text/javascript" src="js/keyboard.js"></script>
</body>
</html>
"""

