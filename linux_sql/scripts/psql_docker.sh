#!/bin/bash

#This script is used to create / start / stop psql database using docker service
#Usually the creation of database is executed only once
#the command to create/stop/start is passed as first parameter to this script.
#Username and password is passed as second and third parameter.

cmd=$1
db_username=$2
db_password=$3

# check if Docker is already running, if not start the Docker
sudo systemctl status docker ||  systemctl start docker

#check container status 
docker container inspect jrvs-psql
container_status=$?

#use switch case to handle create

case $cmd in
	create)

# if the container is already created exit with message
  if [ $container_status -eq 0 ]; then
		echo 'Container already exists'
		exit 1	
	fi

  # Check number of arguments for create command, if username and password not available print error message

  if [ $# -ne 3 ]; then
    echo 'Create requires username and password'
    exit 1
  fi

# create storage in the computer
docker volume create pgdata

#Create the jrvs-psql database with below command by passing password, data storage, port number and psql version.
docker run --name jrvs-psql -e POSTGRES_PASSWORD=$PGPASSWORD -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 postgres:9.6-alpine

exit $?
;;

#if the input command is to start or stop the below code will be executed.
start|stop)
	if [ $container_status -eq 1 ]; then
		exit 1
	fi

docker container $cmd jrvs-psql
exit $?
;;

*)
echo 'Illegal command'
echo 'Commands: start\stop\create'
exit 1
;;
esac





