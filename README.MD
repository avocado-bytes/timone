<h2>Command line utility for parcel tracking.</h2>

<h4>Installation</h4>

To build the project use the maven command: 
<br>
<b>mvn package</b>

After it is built run it with java (version at least 11):<br>
<b>java -jar target/Timone.jar</b>

<h4>Options</h4>

<i>jar cli parameters</i>
<ul>
<li><b>-f</b> file path to initial parcels loading</li>
<li><b>-p</b> file with weight fees</li>
<li><b>unknown param</b> help is printed</li>
</ul>

After program is running it is expecting input.
If an invalid value is input an error message
 is printed informing the user about what is wrong and what input is expected.
 
<i>while running options</i>
<ul>
<li><b>quit</b> exits the program execution</li>
<li><b>list</b> shows all persisted parcel information</li>
</ul>

No other assumptions were made.
