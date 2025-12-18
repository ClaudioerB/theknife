# The Knife

The Knife è un'applicazione che mira ad aiutare a tenere traccia e trovare diversi ristoranti in tutto il mondo, fornisce anche numerosi strumenti ai proprietari per aiutarli a gestire meglio il loro ristorante

## Struttura del progetto

- <b><i>src/:</i></b> …
- 

## Installazione 

### Prerequisiti  
- <b>Java Development Kit (JDK)</b> versione 11 o superiore (scaricabile dal sito [Oracle](https://www.java.com/it/download/))
- <b>Maven</b> o <b>Gradle</b> 

### Verifica dei prerequisiti
```bash
# Controllare versione di java
java -version

# Controllare versione di maven
mvn -v

# Controllare versione di Gradle
brew info gradle
```

## Compilazione 

<b>Avviso:</b> aprire la directory principale del progetto nel <b>cmd</b> prima di eseguire i seguenti comandi

### Metodo con Maven
```bash
# Pulire eventuali build precedenti
mvn clean

# Compilare il progetto
mvn package

# Generare il file .jar
mvn package
```
### Metodo con Gradle
```bash
# Pulire eventuali build precedenti
gradle clean

# Compilare il progetto
gradle build

# Generare il file .jar
gradle jar
```

### Metodo manuale con Java
```bash
# Compilare i file sorgenti
javac -d bin src/*.java

# Creare il file .jar
jar cvf NomeProgramma.jar -C bin
```

## Esecuzione dell'applicazione
```bash
# Eseguire il .jar generato
java -jar target/NomeProgramma.jar
```
