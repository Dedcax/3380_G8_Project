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

# main file loop
Main.class: InputHandler.class QueryManager.class DatabaseLoader.class
	javac Main.java

# executable
Main: Main.class
	java -cp ".;sqlite-jdbc-3.39.3.0.jar" Main

# command to run the application
run: Main

# remove all .class & .db files from porject
clean:
	rm *.class