# Application supporting scrum methodology

## Brief description

The purpose of the application is to support the organisation of the project according to the SCRUM methodology.

Main functions:
* creating a new project,
* adding stories to product backlog,
* creating a sprint and assigning stories to it,
* managing sprints in a kanban board,
* inviting users to the project. 

## Configuration

Fill in the configuration file with your email and database connection details.

Example file for gmail:

```properties
spring.mail.host = smtp.gmail.com
spring.mail.port = 587
spring.mail.properties.mail.smtp.starttls.enable = true
spring.mail.username = username
spring.mail.password = password
spring.mail.properties.mail.smtp.starttls.required = true
spring.mail.properties.mail.smtp.auth = true
spring.mail.properties.mail.smtp.connectiontimeout = 5000
spring.mail.properties.mail.smtp.timeout = 5000
spring.mail.properties.mail.smtp.writetimeout = 5000
```

## Screenshots

Screen with all user projects.

![User projects](https://github.com/mat1911/Screenshots/blob/main/Scrum/scrum_screenshot.PNG?raw=true)

<br />

Screen with kanban board.

![Kanban board](https://github.com/mat1911/Screenshots/blob/main/Scrum/scrum_screenshot_2.PNG?raw=true)

<br />

Screen with product backlog.

![Product backlog](https://github.com/mat1911/Screenshots/blob/main/Scrum/scrum_screenshot_3.PNG?raw=true)

