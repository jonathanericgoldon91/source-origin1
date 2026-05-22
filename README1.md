# Source Origin

Application de vérification d'authenticité d'images
par recherche inversée et scoring de confiance.

## Membres du groupe
- Jonathan — Architecture + Backend
- Djonrebah — Modèles + Service de recherche
- Ngatoumar — Frontend React
- Aching — Score de confiance + Configuration

## Technologies
- Backend : Java Spring Boot
- Frontend : React.js
- API : SerpAPI (Google Reverse Image Search)

## Lancer le projet
cd backend
mvnw.cmd spring-boot:run

## Score de confiance
Le score est calculé sur 4 critères :
- Nombre de sources trouvées        (max 30%)
- Sources bien identifiées           (max 25%)
- Occurrences avec date connue       (max 25%)
- Diversité des domaines             (max 20%)
