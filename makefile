# D FOUCHE
# UCT CS HONS
# FCHDYL001

JCC = javac
CCFLAGS = -implicit:none -nowarn

SRCDIR = src
BINDIR = bin
DOCDIR = doc
INCDIR = include

SRC = $(shell find ./$(SRCDIR) -type f)
JAR = $(shell find ./$(INCDIR) -type f)
OBJ = $(addprefix $(BINDIR)/,$(notdir $(SRC:.java=.class)))

default : $(OBJ)

$(BINDIR)/%.class: $(SRCDIR)/%.java
		$(JCC) $(CCFLAGS) -sourcepath $(SRCDIR) -d $(BINDIR) -cp $(JAR) "$<"

clean:
	rm $(BINDIR)/*.class

docs:
	javadoc -d $(DOCDIR) $(SRC)

cleandocs:
	rm -rf $(DOCDIR)/*
