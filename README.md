# DatabaseManagement_Spring2026_N-GramAnalysis_Page_Healy
Database Management, Spring 2026, Project 2; Ryan Page, Michael Healy

Instructions to Run Program: 

Download Executable JAR Files from Repository 

Download Compressed ZIP File containing the Input Text File 

Add Input File into an Empty Directory on the HDFS 

Commands to Execute Program: 

“hadoop jar /path/to/JARFile.jar nGramAnalysis.MainDriver /path/to/input/directory /temporary /output“ 

“hdfs dfs –ls /output” 

“hdfs dfs –cat /output/part-r-00000” 
(Or similar file name containing the output Top-K Ranking) 
