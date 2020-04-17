# D FOUCHE
# UCT CS HONS
# FCHDYL001

JCC = javac

SRCDIR = src
BINDIR = bin
DOCDIR = doc
INCLDIR = include

SRC = $(shell find ./$(SRCDIR) -type f)
JAR = $(shell find ./$(INCLDIR) -type f)
OBJ = $(SRC:.java=.class)

default : $(OBJ)

%.class: %.java
		$(JCC) -sourcepath $(SRCDIR) -d $(BINDIR) -cp $(JAR) $*.java

clean:
	rm $(BINDIR)/*.class

docs:
	javadoc -d $(DOCDIR) $(SRC)

cleandocs:
	rm -rf $(DOCDIR)/*
