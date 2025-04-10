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
	java -cp ".;mssql-jdbc-11.2.0.jre11.jar" DatabaseLoader

# main file loop
Main.class: InputHandler.class QueryManager.class DatabaseLoader.class
	javac Main.java

# executable
run: Main.class
	java -cp ".;mssql-jdbc-11.2.0.jre11.jar" Main

# remove all .class & .db files from porject
clean:
	rm *.class