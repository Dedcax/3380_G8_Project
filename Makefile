run: Main

# compile InputHandler
InputHandler.class: 
	javac InputHandler.java

Main: InputHandler.class
	javac Main.java
	java Main

# remove all .class files from porject
clean:
	rm *.class