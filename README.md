# Project name: Cinema Management Application

## Current deployment model

The presentation-ready version uses 5 Docker modules:

- Nginx reverse proxy with HTTPS and rate limiting
- React frontend served as a static Docker image
- Spring Boot backend with authentication, REST API, WebSocket, and file proxying
- PostgreSQL database with persistent storage
- MinIO object storage for movie images

### Network layout

- `public-network`: client can reach only Nginx
- `private-network`: frontend, backend, PostgreSQL and MinIO stay isolated from the host

### Main endpoints

Below is a concise reference of public endpoints (method, path, purpose):

- **POST /auth/login**: authenticate user, returns `jwt` on success. Rate-limited (429) for demo — see `nginx` config.
- **POST /auth/register**: create a new user (public registrations are always created with role `USER`).
- **GET /api/movies/getAll**: list all movies.
- **GET /api/movies/{movieId}**: get movie details.
- **GET /api/movies/search?query=...**: search movies by name.
- **POST /api/movies/create**: (admin) create a new movie.
- **POST /api/movies/uploadImage**: upload movie image (multipart/form-data).
- **GET /api/showTimes/movie/{movieId}**: get showtimes for a movie.
- **GET /api/seats/showtime/{showTimeId}**: list seats for a showtime.
- **POST /api/reservations/create**: create a reservation (requires auth)
- **GET /api/files/{bucketName}/{objectName}**: serve file/object (proxied to MinIO).
- **WebSocket /ws**: STOMP websocket endpoint for live seat updates (wss://localhost/ws in demo).

Notes:
- The only nginx-applied rate limiter in the demo is on `/auth/login` (see [CinemaBackend/nginx.conf](CinemaBackend/nginx.conf)).
- WebSocket endpoint is registered in the backend at `registerStompEndpoints("/ws")` ([WebSocketConfig.java](CinemaBackend/src/main/java/kamylo/CinemaBackend/config/WebSocketConfig.java)).

### Demo checklist for class

- Show `docker compose up`
- Open the app through `https://localhost/login`
- Book a seat and show the WebSocket update in another browser tab
- Repeat login requests and show the 429 rate-limit response
- Open a movie image and confirm it is served through `/api/files/`
- Show the network separation in Docker Compose

## Legacy text-mode description
## Project goal:
The application enables cinema management in text mode. Users can browse films, display available screenings, choose seats, make reservations and view the history of their transactions. Administrators have the ability to add new films, screenings.
## Architecture:
The application is based on the MVC (Model-View-Controller) pattern, which allows for easy transfer of logic from text mode to graphic mode in the future.
The user interface will be based on the Lanterna library, enabling a visually friendly text menu.
Responsiveness and efficiency: the application will refresh only those elements that have changed, which will increase efficiency and comfort of use.
## Functionalities:
### Registration and login of users (including assigning roles: user, administrator).
### Movie management (for administrators):
- Adding, removing films.
- Management of screenings, including times, dates, halls.
### Reservations (for users):
- Browsing films and available screenings.
- Ability to search for a movie by name
- Selecting seats at a screening.
- Making reservations.
- Managing reservations (history, cancellation).
### User interface:
- Keyboard navigation (arrows, enter).
- Visually friendly menu.
- Interactive transitions between screens.
### Database integration:
PostgreSQL on Docker for storing movies, screenings, reservations and users.
### Interface optimization: 
Minimizing full screen refreshes.

# Project Name: Cinema Management Application (Graphical Mode with React)
## Project Goal:
Develop a graphical version of the Cinema Management Application, maintaining the same core logic as the text-based application, with the presentation layer adapted to a React-based graphical interface. The focus is on seamlessly transitioning from text to a graphical interface while prioritizing user experience, responsiveness, and visual appeal.

## Architecture:
The application follows the MVC (Model-View-Controller) pattern, ensuring clear separation between the business logic and the graphical interface. This design enables the reuse of core logic with minimal changes, as only the presentation layer shifts from text-based to graphical.

## Functionality:

### User Management:

- User registration and login, with role assignment (user, admin).

### Admin Features:
- Film Management: Adding and removing movies.
- Show Management: Scheduling showtimes, setting dates, times.

### User Features:
- Viewing available movies and showtimes.
- Movie search functionality by name.
- Seat selection for chosen shows.
- Booking management (viewing history, canceling reservations).

### User Interface:
- Interactive graphical menu with intuitive navigation using both keyboard and mouse.
- Smooth transitions between screens and detailed visual elements for enhanced user experience.

## Database Integration:
The PostgreSQL database on Docker is used to store information on movies, showtimes, reservations, and users.

## Interface Optimization:
Only the changed elements refresh, ensuring a smooth and efficient user experience.

## Note:
The implementation should focus primarily on the presentation layer, leaving the core logic unchanged to showcase an efficient, modular design that maintains consistency across different interface modes.


## Sample Screenshots
![image1](https://github.com/user-attachments/assets/a3390803-dbc0-4e4a-9622-7e110bb75cfd)
![image2](https://github.com/user-attachments/assets/15d1cb0c-ad45-40ee-97f2-c339e44de94b)
![image3](https://github.com/user-attachments/assets/1b66aa26-b8c8-468f-be8b-788f91aaa688)
![image4](https://github.com/user-attachments/assets/da0cbe2e-3f84-4123-ae3c-ed414d8cbf6f)
![image5](https://github.com/user-attachments/assets/d34c1685-6bf0-4981-b10b-177244e441bd)
![image6](https://github.com/user-attachments/assets/9c989438-bf99-4652-8f71-6f59a5acb93e)

## How to run the final Docker demo
### Start the full stack:
```bash
cd CinemaBackend
docker compose up --build
```

### Open the app:
```text
https://localhost/login
```

### Local development mode:
- Backend in IntelliJ: `mvn spring-boot:run` inside `CinemaBackend`
- Frontend locally: `npm install` then `npm run dev` inside `CinemaFrontend`

## Module Diagram
The demo stack is organized into five main modules. The public entrypoint is Nginx (TLS + reverse proxy), the frontend is served as static files, and backend + data stores are on a private network.

```mermaid
flowchart LR
	A[Nginx public reverse proxy\n(HTTPS, rate-limiting)] --> B[Frontend (nginx serving /dist)]
	A --> C[Backend (Spring Boot)]
	C --> D[PostgreSQL]
	C --> E[MinIO (object storage)]
	B ---|WS via /ws| C
```

## Quick test recipes

- Check login rate limit (expect 429 after a few attempts):

```powershell
for ($i=1; $i -le 12; $i++) {
	$code = curl.exe -k -s -o NUL -w "%{http_code}" -X POST https://localhost/auth/login -H "Content-Type: application/json" -d "{\"email\":\"x@x.com\",\"password\":\"bad\"}";
	Write-Host "$i -> $code"
}
```

- Check an API endpoint (should return 200/401/403 but NOT 429):

```powershell
for ($i=1; $i -le 8; $i++) {
	$code = curl.exe -k -s -o NUL -w "%{http_code}" https://localhost/api/movies/getAll;
	Write-Host "$i -> $code"
}
```

- Test websocket connection (from browser or STOMP client): connect to `wss://localhost/ws` and subscribe to `/topic/showtimes/{id}/seats` to receive live seat updates.

## Files of interest
- Nginx configuration: [CinemaBackend/nginx.conf](CinemaBackend/nginx.conf)
- Docker Compose: [CinemaBackend/compose.yaml](CinemaBackend/compose.yaml)
- WebSocket endpoint: [CinemaBackend/src/main/java/kamylo/CinemaBackend/config/WebSocketConfig.java](CinemaBackend/src/main/java/kamylo/CinemaBackend/config/WebSocketConfig.java)
- Auth action handling (frontend): [CinemaFrontend/src/Redux/AuthService/Action.js](CinemaFrontend/src/Redux/AuthService/Action.js)

If you want, I can also add an OpenAPI/Swagger spec for the backend to generate a machine-readable endpoints list. That would take an extra step (add `springdoc-openapi` and a small config). 
