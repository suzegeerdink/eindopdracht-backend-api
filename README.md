# Eindopdracht Backend API

Dit is een API voor het beheren van een streaming-platform met films en series. Gebruikers kunnen meerdere kijkprofielen aanmaken en content 'lenen'. De contentmanager heeft de bevoegdheid om content aan te passen, toe te voegen of verwijderen. Admins hebben toegang tot gebruikers profielen. Deze applicatie is gebouwd met Spring Boot, Java, PostgreSQL en Keycloak.

## Inhoudsopgave

- [Vereisten](#vereisten)
- [Installatie](#installatie)
- [Database opzetten](#database-opzetten)
- [Keycloak opzetten](#keycloak-opzetten)
- [Applicatie starten](#applicatie-starten)
- [Testdata](#testdata)
- [API-documentatie](#api-documentatie)
- [Rollen](#rollen)
- [Bestandsuploads](#bestandsuploads)
- [Testen](#testen)
- [Bekende beperkingen](#bekende-beperkingen)
## Vereisten

- Java (long term support versie, bijv. 17 of 21)
- Maven
- PostgreSQL (lokaal, bijv. via pgAdmin)
- Keycloak (lokaal draaiend, bijv. via Docker of standalone)
- Postman (voor het testen van endpoints)
## Installatie

1. Clone deze repository:
```bash
   git clone git@github.com:suzegeerdink/eindopdracht-backend-api.git
   cd eindopdracht-backend-api
```
2. Installeer de dependencies:
```bash
   ./mvnw clean install
```

## Database opzetten

1. Maak een lokale PostgreSQL-database aan genaamd `eindopdracht-backend-api`.
2. Controleer de database-instellingen in `src/main/resources/application.properties`:
```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/eindopdracht-backend-api
   spring.datasource.username=postgres
   spring.datasource.password=password
```
Pas gebruikersnaam/wachtwoord aan indien nodig voor jouw lokale installatie.
3. De applicatie gebruikt `spring.jpa.hibernate.ddl-auto=create-drop` in combinatie met `data.sql` — dit zorgt dat bij elke opstart het schema opnieuw wordt aangemaakt en gevuld met consistente testdata. **Let op:** dit betekent dat alle data verloren gaat bij het stoppen van de applicatie. Deze configuratie is bedoeld voor deze opdracht/testdoeleinden, niet voor productiegebruik.

## Keycloak opzetten (via import)

In plaats van de realm handmatig op te bouwen, kun je de meegeleverde configuratie importeren:

1. Start Keycloak lokaal op poort 9090.
2. Ga naar de Keycloak Admin Console → **Create Realm** → **Browse** → selecteer `keycloak/eindopdracht-api-realm.json`.
3. Klik op **Create**.
4. Dit importeert automatisch de realm, de client (`eindopdracht-backend-api`), alle rollen (USER, CONTENT_MANAGER, ADMIN), en de testgebruikers. Zie de Auth-map in de Postman-documentatie voor de exacte gebruikersnamen en wachtwoorden (wachtwoord = gebruikersnaam).

## Applicatie starten

```bash
./mvnw spring-boot:run
```

De applicatie start op `http://localhost:8080`. Bij het opstarten wordt het databaseschema aangemaakt en gevuld met testdata via `data.sql` (zie hieronder).

## Testdata

Bij elke opstart wordt de database gevuld met consistente testdata via `src/main/resources/data.sql`, waaronder:

- 3 gebruikers (gekoppeld aan Keycloak via `keycloak_id`)
- 6 genres
- 3 content-items (films en series)
- 4 profielen (inclusief profielinstellingen)
- Enkele leningen en kijkgeschiedenis-items. Zie 'Keycloak opzetten (via import)' hierboven — na het importeren van de realm matchen de keycloak_id-waarden in data.sql automatisch met de meegeleverde testgebruikers, dus je hoeft niets handmatig aan te passen.

## API-documentatie

De volledige API-documentatie (alle endpoints, vereiste rollen, voorbeeldrequests en -responses) is beschikbaar via de meegeleverde Postman-collectie:

- `Eindopdracht.postman_collection.json`
- Of via de gepubliceerde documentatie: `https://documenter.getpostman.com/view/52064877/2sBY4LSN7q`
### Tokens ophalen voor testen

1. Importeer de Postman-collectie en het bijbehorende environment.
2. Open de request **Auth → Get Token (any role)**.
3. Ga naar het tabblad **Authorization** → **Get New Access Token**.
4. Log in met een van de Keycloak-testgebruikers.
5. Kopieer de opgehaalde token naar de juiste environment-variabele (`user_token`, `content_manager_token`, of `admin_token`).
   Access tokens zijn 30 minuten geldig (geconfigureerd in Keycloak Realm Settings → Tokens).

## Rollen

| Rol | Rechten |
|---|---|
| **USER** | Eigen profiel aanmaken, content bekijken en lenen |
| **CONTENT_MANAGER** | Films, series en genres beheren, bestanden uploaden |
| **ADMIN** | Gebruikers en overige data volledig beheren |

Elk endpoint vereist minimaal een geldig token. Sommige endpoints vereisen daarnaast een specifieke rol (zie de Postman-documentatie per endpoint).

## Bestandsuploads

Content-items (films/series) kunnen een bijbehorend bestand hebben: afbeeldingen, muziek, of pdf's. Bestanden worden als binaire data (BLOB) in de database opgeslagen, samen met bestandsnaam en MIME-type.

- **Upload**: `POST /content/{id}/file` (multipart/form-data, key `file`) — vereist rol `CONTENT_MANAGER`
- **Download**: `GET /content/{id}/file`
  Toegestane bestandstypen: `image/jpeg`, `image/png`, `image/gif`, `image/webp`, `audio/mpeg`, `audio/wav`, `audio/ogg`, `application/pdf`.

## Testen

```bash
./mvnw test
```

Dit voert alle unit- en integratietests uit. De testsuite dekt:
- Unit tests voor de service-laag (line coverage 100% op ContentService en UserService)
- Integratietests met Spring Boot Test, MockMvc en JUnit

## Bekende beperkingen

De volgende punten zijn bewuste keuzes voor deze opdracht, en mogelijke uitbreidingen voor de toekomst:

- Gebruikers kunnen hun eigen e-mailadres niet zelf aanpassen (alleen `ADMIN` kan dit).
- Eigenaarschap-validatie ontbreekt bij enkele endpoints (bijv. `loans/profile/{id}`, `updateProfile`, `deleteProfile`) — een `USER` kan deze technisch gezien voor elk profiel aanroepen, niet alleen het eigen profiel. Er is bewust gekozen voor een eenvoudiger rolgebaseerd model.
- De leeftijdscontrole bij het lenen van content (`Loan`) geldt alleen bij het aanmaken van een lening, niet bij het bijwerken ervan.
