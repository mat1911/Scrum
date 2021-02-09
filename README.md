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

![User projects](https://drive.google.com/uc?export=view&id=1z6NUCgRbaOicPPDBFymSRXYZK64JvDhV)

<br />

Screen with kanban board.

![User projects](https://drive.google.com/uc?export=view&id=1GAQmAyYu9sLEYWAQRroCF-N3w23VDPKy)

<br />

Screen with product backlog.

![User projects](https://drive.google.com/uc?export=view&id=1zLq2y5SdA6UERXkmkuS7Tt_0V-smWUzp)

