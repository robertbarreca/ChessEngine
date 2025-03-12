SRC_DIR=src
BIN_DIR=bin
LIB_DIR=lib
JUNIT_JAR=$(LIB_DIR)/junit-4.13.2.jar
HAMCREST_JAR=$(LIB_DIR)/hamcrest-core-1.3.jar

CLASSPATH=$(JUNIT_JAR):$(HAMCREST_JAR):$(BIN_DIR)

SOURCES=$(shell find $(SRC_DIR) -name "*.java")
CLASSES=$(SOURCES:$(SRC_DIR)/%.java=$(BIN_DIR)/%.class)

.PHONY: all run test clean

# Compile all Java files at once
all:
	@mkdir -p $(BIN_DIR)
	javac -cp "$(CLASSPATH)" -d $(BIN_DIR) $(SOURCES)

# Run tests after compilation
test: all
	java -cp "$(CLASSPATH)" org.junit.runner.JUnitCore com.tests.chess.engine.TestChessEngineSuite

# Run the main application
run:
	java -cp "$(CLASSPATH)" com.chess.ChessEngine

# Clean up compiled files
clean:
	rm -rf $(BIN_DIR)/*
