# Project name: Cinema Management TextApplication
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

## How to run the backend
### Install Maven dependencies:
- In the terminal in the project directory run the command:
```
mvn clean install
```
### Run the Spring Boot application:
- In the terminal, run the command:
```
mvn spring-boot:run
```
- make sure you have jdk 19 installed
## How to run the Frontend
### Install node Modules:
- In the terminal in the project directory run the command:
```
npm install
```
### Run the React application:
- In the terminal, run the command:
```
npm run dev
```
