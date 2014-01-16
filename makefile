bJCC = javac

CLASSPATH = libs/stdlib.jar

JAVA_MAIN_CLASS = Game
 
compile:
	mkdir -p binMap map
	javac -d bin -cp :./$(CLASSPATH) src/*.java
 
run:
	cd bin
	cd bin && java -cp :../$(CLASSPATH) $(JAVA_MAIN_CLASS)