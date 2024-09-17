# Linux Cluster Monitoring Agent

 ## Introduction

The scope of the Linux cluster monitoring project is to collect hardware specifications of the machine and automate to store data usage into psql database. This data helps to Jarvis Linux Cluster Administrator team (LCA) to monitor the real-time data  in server, perform data analytics and manage servers. This project can be deployed in any host machines for gathering real-time data. The target users for this project are system administrators or IT professionals responsible for managing servers.The technologies used in this project are Git, GitHub, Docker, Bash scripts,  PostgreSQL and Rocky Linux.
 ## Quick Start
### Start a psql instance using psql_docker.sh
If container not created, create a docker container by typing
```bash
  ./scripts/psql_docker.sh create [database_name] [database_password]. 
   ```
Once the container is created, run it by command ./scripts/psql_docker.sh start which will start the container and connect to database.

### Create tables using ddl.sql

Once the database is created, create a file ```ddl.sql``` to create the tables. Two tables are created names ```host_info.sh``` and ```host_usage.sh```
````bash
psql -h localhost -U postgres -d [database_name] -f sql/ddl.sql
````
### Insert hardware specs data into the DB using host_info.sh
Run the file ```host_info.sh``` to gather the hardware specifications of the host machine and store it inside the ```host_info``` table
````bash
./host_info.sh localhost 5432 [database_name] postgres [database_password]
````
### Insert hardware usage data into the DB using ```host_usage.sh```
Run the file ```host_usage.sh``` to gather the real-time hardware usage data of the host machine and store inside ```host_usage``` table
````bash
./host_usage.sh localhost 5432 [database_name] postgres [database_password]
````
### Crontab setup
Run the command ```crontab -e``` to open cron editor.
````bash * * * * * bash /home/rocky/dev/jarvis_data_eng_UmaraniKumaresan/linux_sql/scripts/host_usage.sh localhost 5432 host_agentpostgres rocky1234 > /tmp/host_usage.log
````
## Implemenation
### Docker Setup:
Download Docker and write bash script to create, start and stop docker container with PSQL image.
### GitHub Repository:
Create three branches such as ```feature/psql_docker```, develop and master branches following gitflow branching strategies and setup SSH.
### Database Creation:
A database named ```host_agent``` is created for this project. Two tables named ```host_info``` to store machine specifications and ```host_usage``` to store CPU usages.
### Bash Scripts:
The bash scripts host_info.sh inserts CPU specifications in host info table and host_usage.sh inserts CPU usages into host_usage table. The scripts gather data from host machine and insert data into database interacting with psql instance running inside the Docker container.
### Crontab Automation:
Crontab script runs every minute and inserts host_usage data and inserts into host_usage table.
## Architecture
![image](assets/linux_architecture.jpg)

## Database Modeling
### host_info table schema


### host_usage table schema


## Test
The testing of the scripts was done manually by starting, stopping ./psql_docker.sh to make sure runs properly. I wrote query in the terminal and made sure machine configurations are captured and inserted in host_info table. I also checked host_usage table and crontab -l if inserting machine usage information is inserted every minute automatically using crontab.

## Deployment
The application was deployed via this GitHub repository and is hosted in a Docker container. A Docker volume is utilized for persistent data storage, and crontab is used to automate data collection tasks.
## Improvements



