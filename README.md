# vintage-for-life-routing
Repositorie voor schoolproject op Windesheim

When running the service application you will need to have a .env file in the root of the project with the following content:

MYSQL_DATABASE=vintageforlife
MYSQL_HOST=
MYSQL_USER=admin
MYSQL_PASSWORD=
JWT_SECRET_KEY=
COMPOSE_PROJECT_NAME=vintageforlife
MYSQL_PORT=3306

When starting the service application it will start up a Swagger UI on http://hostname:port/swagger-ui.html which by default is http://localhost:8080/swagger-ui.html

Generate a jwt key with `openssl rand -base64 32`