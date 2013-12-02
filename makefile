JCC = javac

CLASSPATH = libs/stdlib.jar

JAVA_MAIN_CLASS = Game
 
compile:
	mkdir -p bin
	javac -d bin -cp :./$(CLASSPATH) src/*.java
 
run:
	cd bin
	cd bin && java -cp :../$(CLASSPATH) $(JAVA_MAIN_CLASS)
