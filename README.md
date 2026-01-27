# The Knife

The Knife è un'applicazione che mira ad aiutare a tenere traccia e trovare diversi ristoranti in tutto il mondo, fornisce anche numerosi strumenti ai proprietari per aiutarli a gestire meglio il loro ristorante

## Struttura del progetto

- <b>src/</b> — sorgenti Java e risorse (FXML, immagini, CSV di dati).

- <b>bin/</b> — output di compilazione; qui può trovarsi il .jar eseguibile.

- <b>lib/</b> — dipendenze esterne (se non incluse nel jar).

- <b>data/</b> — CSV e risorse runtime (datasetRistoranti.csv, tipiCucine.csv, statiCitta.csv).

- <b>docs/</b> — documentazione generata (manuali e documentazione).

## Requisiti e verifiche
### Prerequisiti

- JDK 11+ (installato e su PATH).

- Maven o Gradle (se vuoi ricompilare).

- JavaFX runtime se non incluso nel jar (per alcune distribuzioni di Java).

### Verifica 
```bash
java -version
mvn -v # se usi Maven
gradle -v # se usi Gradle
```

## Compilazione e creazione del jar

### Con Maven
```bash 
mvn clean package 
# jar standard in target/theknife-1.0-SNAPSHOT.jar
```
Per ottenere tutte le dipendenze incluse, usa il plugin Shade:

```Xml
<!-- pom.xml: aggiungi sotto <plugins> -->
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-shade-plugin</artifactId>
  <version>3.4.1</version>
  <executions>
    <execution>
      <phase>package</phase>
      <goals><goal>shade</goal></goals>
      <configuration>
        <createDependencyReducedPom>false</createDependencyReducedPom>
        <transformers>
          <transformer implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
            <mainClass>com.mycompany.theknife.App</mainClass>
          </transformer>
        </transformers>
      </configuration>
    </execution>
  </executions>
</plugin>
```

### Con Gradle
```bash
./gradlew clean build
```
Per ottenere tutte le dipendenze incluse, usa il plugin Shadow:

```Groovy
plugins { id 'com.github.johnrengelman.shadow' version '8.1.1' }
shadowJar {
  archiveBaseName.set('theknife')
  manifest { attributes 'Main-Class': 'com.mycompany.theknife.App' }
}
```

### Metodo manuale con Java

```bash
javac -d bin $(find src -name "*.java")
jar --create --file bin/theknife.jar -C bin .

```

## Esecuzione del file jar

#### 1) Jar con dipendenze incluse
```bash
java -jar bin/theknife-1.0-SNAPSHOT.jar
```
#### 2) Jar senza dipendenze incluse — classpath con cartella lib/ (Windows)
```bash
java -cp "bin;lib/*" com.mycompany.theknife.App
```
#### (Linux / macOS)
```bash
java -cp "bin:lib/*" com.mycompany.theknife.App
```
#### 3) Se JavaFX non è nel JDK (modular runtime)
- Scarica JavaFX SDK e avvia con --module-path e --add-modules:
```bash
java --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml -jar bin/theknife-1.0-SNAPSHOT.jar
```

## Debug e problemi comuni
- <b>FXML LoadException / risorse non trovate:</b> verifica che i file .fxml siano nel jar e che i percorsi in FXMLLoader.getResource(...) siano corretti.

- <b>NullPointerException su utenteLoggato:</b> assicurati di inizializzare lo stato dell’app (login) prima di aprire controller che si aspettano un utente.

- <b>NumberFormatException in calcStelle:</b> dati CSV malformati (es. intestazioni o valori non numerici) — pulisci il CSV o rendi il parser più tollerante.

- <b>Problemi JavaFX:</b> se ricevi errori relativi a JavaFX, prova ad eseguire con --module-path come sopra o usa un fat-jar che include JavaFX.
<br><br>

### Generazione Javadoc (opzionale)
```bash
mvn javadoc:javadoc
# output in target/site/apidocs
```
#### Oppure con Gradle
```bash
./gradlew javadoc
```





