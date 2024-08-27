Config Docker Compose for running SQL Server

1. Pulling images (mcr.microsoft.com/mssql/server:2019-latest)
2. Define Docker compose file
    - -d(detach): background mode
    - -e: environment variables
    - --name: container's name
    - -p: port - that maps from ip local's port and ip of docker container's port
