CLASSES_DIR = ./classes
BIN_DIR = ./bin
SRC_DIR = ./src

JAVA_FILES = $(wildcard $(SRC_DIR)/*.java)
CLASSES_FILES = $(JAVA_FILES:$(SRC_DIR)/%.java=$(CLASSES_DIR)/%.class)
OUT = $(BIN_DIR)/Snake.jar
MAIN_CLASS = UI

$(OUT): $(CLASSES_FILES)
	jar --create --file $@ --main-class $(MAIN_CLASS) -C $(CLASSES_DIR) .

$(CLASSES_FILES): $(JAVA_FILES)
	javac -cp $(SRC_DIR) -d $(CLASSES_DIR) $^

.PHONY: clean

clean:
	rm -f $(BIN_DIR)/* $(CLASSES_DIR)/*
