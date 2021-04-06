# Swat-Intel Backend Application
This is an application for searching the route of supplies

**Quick Start:**
Consider already having the docker installed on your machine

If you don’t have, watch this tutorial: https://runnable.com/docker/install-docker-on-linux

Clone this project using the git clone command on your terminal

In the project's root folder, execute:

docker-compose up -d --no-recreate;

check http://localhost:8080/swagger-ui.html for documentation

**Endpoints:**

to get all available stations do a get in http://localhost:8080/station

to get all available routes do a get in http://localhost:8080/routes

to get the routes between stations do a get in http://localhost:8080?origin=plu&destiny=FLN

are mandatory parameters:

- origin
- destiny

are optional parameters

- dateExit

**Extras:**

Travel connections will not exceed 12 hours

Expensive or time-consuming journeys are not exclusion criteria
