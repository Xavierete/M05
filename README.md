# EcoTracker - Seguiment d'Impacte Ambiental

## Autor
Xavier Moreno Navarro

## Descripció del Projecte
EcoTracker és una eina senzilla i pràctica per portar el control de les activitats sostenibles que realitzes al teu dia a dia, pensada per a empreses i particulars conscients del seu impacte ambiental. L'objectiu és facilitar el registre d'accions i veure de manera clara la reducció de CO₂ aconseguida.

## Gestió del Projecte
Aquest projecte utilitza GitHub Projects per organitzar i fer seguiment de les tasques. Pots consultar el tauler aquí: [GitHub Projects Board](https://github.com/users/xaviermoreno/projects/1)

El tauler està dividit en:
- 📋 Backlog: Idees i tasques pendents
- 🚀 To Do: Tasques a fer
- ⏳ In Progress: En procés
- ✅ Done: Fet!

## Característiques Principals
- Registre d'activitats sostenibles amb camps bàsics
- Visualització de totes les activitats en una taula
- Càlcul automàtic del total de CO₂ estalviat
- Exportació de dades a CSV o TXT
- Dades guardades a MySQL
- Interfície gràfica amb JavaFX
- Proves unitàries amb JUnit i Mockito

## Requisits del Sistema
- Java JDK 17 o superior
- Maven 3.6 o superior
- MySQL 8.0 o superior
- JavaFX SDK

## Instal·lació i Configuració

1. Clona el repositori:
```bash
git clone [URL_DEL_REPOSITORI]
```

2. Crea la base de dades MySQL:
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

3. Configura el fitxer `config.properties` amb les teves credencials de MySQL.

4. Compila el projecte:
```bash
mvn clean install
```

5. Executa l'aplicació:
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
1. **Registre d'Activitats**
   - Nom de l'activitat
   - Data
   - Categoria
   - Descripció
   - CO₂ estalviat

2. **Visualització**
   - Taula amb totes les activitats
   - Total acumulat de CO₂ estalviat
   - Filtres per data i categoria (millorable)

3. **Exportació**
   - Format CSV
   - Format TXT

4. **Base de Dades**
   - Persistència completa
   - Operacions CRUD bàsiques

## Testing
- Proves unitàries amb JUnit
- Mocks amb Mockito
- Cobertura de codi > 80% (aprox.)

## Manual d'Usuari
1. Obre l'aplicació i omple el formulari per afegir una nova activitat sostenible.
2. Fes clic a "Guardar" per registrar-la.
3. Consulta totes les activitats a la taula central.
4. Pots eliminar activitats si t'has equivocat.
5. Exporta les dades a CSV o TXT per compartir-les o fer informes.

## Notes i Consells
- Si tens qualsevol problema amb la connexió a la base de dades, revisa el fitxer `config.properties`.
- Pots modificar les categories d'activitat directament al codi si vols adaptar-ho a la teva realitat.
- El projecte està pensat per ser senzill i fàcil d'ampliar.

## Contribució
1. Fes un fork del projecte
2. Crea una branca per la teva millora
3. Fes commit dels teus canvis
4. Puja la branca
5. Obre un Pull Request

## Llicència
Projecte sota llicència MIT.