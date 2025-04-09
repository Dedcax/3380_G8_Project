run: Main

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
Main: InputHandler.class QueryManager.class DatabaseLoader.class
	javac Main.java
	java -cp ".;sqlite-jdbc-3.39.3.0.jar" Main


# remove all .class & .db files from porject
clean:
	rm *.class *.db