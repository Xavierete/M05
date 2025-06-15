# EcoTracker - Seguiment d'Impacte Ambiental

## Descripció del Projecte
EcoTracker és una aplicació Java/JavaFX dissenyada per ajudar les empreses a fer el seguiment del seu impacte ambiental. L'aplicació permet registrar i gestionar activitats sostenibles, calculant automàticament la reducció d'emissions de CO₂ associada a aquestes accions.

## Característiques Principals
- Registre d'activitats sostenibles
- Visualització de dades en taules JavaFX
- Càlcul automàtic de CO₂ estalviat
- Exportació de dades a CSV/TXT
- Base de dades MySQL per persistència
- Interfície gràfica intuïtiva
- Proves unitàries amb JUnit i Mockito

## Requisits del Sistema
- Java JDK 17 o superior
- Maven 3.6 o superior
- MySQL 8.0 o superior
- JavaFX SDK

## Instal·lació i Configuració

1. Clonar el repositori:
```bash
git clone [URL_DEL_REPOSITORI]
```

2. Configurar la base de dades MySQL:
```sql
CREATE DATABASE ecotracker;
USE ecotracker;

CREATE TABLE activitats (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(100) NOT NULL,
    data DATE NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    descripcio TEXT,
    co2_estalviat DECIMAL(10,2) NOT NULL
);
```

3. Configurar les variables d'entorn:
- Crear un fitxer `config.properties` amb les credencials de la base de dades

4. Compilar el projecte:
```bash
mvn clean install
```

5. Executar l'aplicació:
```bash
mvn javafx:run
```

## Estructura del Projecte
```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── ecotracker/
│   │           ├── controllers/
│   │           ├── models/
│   │           ├── services/
│   │           ├── utils/
│   │           └── Main.java
│   └── resources/
│       ├── fxml/
│       └── styles/
└── test/
    └── java/
        └── com/
            └── ecotracker/
                └── tests/
```

## Funcionalitats
1. Registre d'Activitats
   - Nom de l'activitat
   - Data
   - Categoria
   - Descripció
   - CO₂ estalviat

2. Visualització
   - Taula amb totes les activitats
   - Total acumulat de CO₂ estalviat
   - Filtres per data i categoria

3. Exportació
   - Format CSV
   - Format TXT

4. Base de Dades
   - Persistència completa
   - Operacions CRUD

## Testing
- Proves unitàries amb JUnit
- Mocks amb Mockito
- Cobertura de codi > 80%

## Contribució
1. Fork el projecte
2. Crea una branca per la teva feature
3. Commit els teus canvis
4. Push a la branca
5. Obre un Pull Request

## Llicència
Aquest projecte està sota la llicència MIT.