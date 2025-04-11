build: Main.class

# compile InputHandler
InputHandler.class: 
	javac InputHandler.java

# compile query manger 
QueryManager.class:
	javac QueryManager.java

# compile database loader 
DatabaseLoader.class:
	javac DatabaseLoader.java

# initilise the databse
initdb: DatabaseLoader.class
	java -cp ".;mssql-jdbc-8.2.2.jre8.jar" DatabaseLoader

# linux command
initdb_aviary: DatabaseLoader.class
	java -cp .:mssql-jdbc-8.2.2.jre8.jar DatabaseLoader

# main file loop
Main.class: InputHandler.class QueryManager.class DatabaseLoader.class
	javac Main.java

# executable
run: Main.class
	java -cp ".;mssql-jdbc-8.2.2.jre8.jar" Main
	
# linux executable
run_aviary: Main.class
	java -cp .:mssql-jdbc-8.2.2.jre8.jar Main

# Script for generating insert statements
script: 
	javac DatabaseLoadTool.java
	java  DatabaseLoadTool

# remove all .class & .db files from porject
clean:
	rm *.class
