# Search Tool using Java RMI 
## Synopsis
A simple search tool that can look for Music Artist Location, and Songs using Java RMI and Java Swings.
## Specifications
+ JDK 1.8 
+ JRE 1.8 
## Input data-set (Table2.in) format 
<artistname><SEP><artist_id><I>.....<artist_id><SEP><location><I>......<location><SEP><song><I>......<song>
## How to run ? 
Make sure java is configured in your system, download the files to a folder and proceed with the following commands from your terminal/command prompt.
~~~~sh
javac Server.java
javac Client.java
~~~~
Run the Server with the following command from your terminal/command prompt, very important - The Table.in file has to be near/adjacent to the Server
~~~~
java Server
~~~~
On a separate terminal/command line window run the Client using the following command,
~~~~
java Client
~~~~
You will be able to see the GUI now, enter any artist name to get songs/locations accordingly. 

![GUISearch](https://bytebucket.org/vaikunthsridharan/search-tool-rmi/raw/4c04d27d42b72894b0dfcb68c2eae8b51e3d996e/images/GUI.png?token=2681f32c3740251234c971cbb280af4ee9b02e7b)


##### Copyright © 2015 Vaikunth Sridharan