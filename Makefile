ARCHIVE := submission.zip

# Parametri pentru compilare.
JFLAGS := -Xlint:unchecked

# Directorul care conține sursele voastre, și cel unde punem binarele.
# Cel mai safe e să le lăsați așa. Dacă schimbați, folosiți path-uri relative!
SRC_DIR := .
OUT_DIR := .

# Compilăm toate sursele găsite în $(SRC_DIR).
# Modificați doar dacă vreți să compilați alte surse.
JAVA_SRC := $(wildcard $(SRC_DIR)/*.java)

JAVA_CLASSES := $(JAVA_SRC:$(SRC_DIR)/%.java=$(OUT_DIR)/%.class)
TARGETS := $(JAVA_CLASSES)

.PHONY: build clean pack

build: $(TARGETS)

clean:
	rm -f $(TARGETS) $(OUT_DIR)/*.class $(ARCHIVE)

pack:
	@find $(SRC_DIR) \
		\( -path "./_utils/*" -prune \) -o \
		-regex ".*\.\(cpp\|h\|hpp\|java\)" -exec zip $(ARCHIVE) {} +
	@zip $(ARCHIVE) Makefile
	@[ -f README.md ] && zip $(ARCHIVE) README.md \
		|| echo "You should write README.md!"
	@echo "Created $(ARCHIVE)"


# TODO: Apelați soluția fiecărei probleme. Puteți completa o regulă așa:
# Pentru C++
#	$(OUT_DIR)/<nume_problemă>
# Pentru Java
#	java -cp $(OUT_DIR) <NumeProblemă>

build: P1.class P2.class P3.class P4.class P5.class

# Nu compilați aici, nici măcar ca dependențe de reguli.
run-p1:
	java Servere
run-p2:
	java Colorare
run-p3:
	java Compresie
run-p4:
	java Criptat
run-p5:
	java Oferta

# Schimbați numele surselor și ale binarelor (peste tot).
P1.class: Servere.java
	javac $^
P2.class: Colorare.java
	javac $^
P3.class: Compresie.java
	javac $^
P4.class: Criptat.java
	javac $^
P5.class: Oferta.java
	javac $^

# Reguli pentru compilare. Probabil nu e nevoie să le modificați.

$(CC_EXECS): $(OUT_DIR)/%: $(SRC_DIR)/%.cpp
	g++ -o $@ $^ $(CCFLAGS)

$(JAVA_CLASSES): $(OUT_DIR)/%.class: $(SRC_DIR)/%.java
	javac $< -d $(OUT_DIR) -cp $(SRC_DIR) $(JFLAGS)