This app reads the local database and provides them globally to all eureka registered services
So we dont need to hardcode jdbcurl or the host name . 
Dynamically picks properties from the side car app and gives it.

This application must run in the master host the database server.
