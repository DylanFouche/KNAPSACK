# D FOUCHE
# UCT CS HONS
# FCHDYL001

JCC = javac

SRCDIR = src
BINDIR = bin
DOCDIR = doc

SRC = $(shell find ./$(SRCDIR) -type f)
OBJ = $(SRC:.java=.class)

default : $(OBJ)

%.class: %.java
		$(JCC) -sourcepath $(SRCDIR) -d $(BINDIR) -cp $(BINDIR) $*.java

clean:
	rm $(BINDIR)/*.class

docs:
	javadoc -d $(DOCDIR) $(SRCDIR)/*.java

cleandocs:
	rm -rf $(DOCDIR)/*
