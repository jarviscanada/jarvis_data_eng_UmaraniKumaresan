# SQL and RDBMS by solving SQL queries

## Introduction
The scope of the project is to go through the fundamentals of Relational Database Management system (RDBMS) and Structured Query Language (SQL). By installing PGAdmin in a Docker container helped to setup client. DDL (Data Definition Language) and DML (Data Manipulation Language) are two key subsets of SQL (Structured Query Language) used for managing and interacting with databases. They serve different purposes in handling data and database structures.

## Table Setup (DDL)
```
  CREATE TABLE cd.members
    (
       memid integer NOT NULL, 
       surname character varying(200) NOT NULL, 
       firstname character varying(200) NOT NULL, 
       address character varying(300) NOT NULL, 
       zipcode integer NOT NULL, 
       telephone character varying(20) NOT NULL, 
       recommendedby integer,
       joindate timestamp NOT NULL,
       CONSTRAINT members_pk PRIMARY KEY (memid),
       CONSTRAINT fk_members_recommendedby FOREIGN KEY (recommendedby)
            REFERENCES cd.members(memid) ON DELETE SET NULL
    );
```

```
  CREATE TABLE cd.facilities
    (
       facid integer NOT NULL, 
       name character varying(100) NOT NULL, 
       membercost numeric NOT NULL, 
       guestcost numeric NOT NULL, 
       initialoutlay numeric NOT NULL, 
       monthlymaintenance numeric NOT NULL, 
       CONSTRAINT facilities_pk PRIMARY KEY (facid)
    );
```

```
CREATE TABLE cd.bookings
    (
       bookid integer NOT NULL, 
       facid integer NOT NULL, 
       memid integer NOT NULL, 
       starttime timestamp NOT NULL,
       slots integer NOT NULL,
       CONSTRAINT bookings_pk PRIMARY KEY (bookid),
       CONSTRAINT fk_bookings_facid FOREIGN KEY (facid) REFERENCES cd.facilities(facid),
       CONSTRAINT fk_bookings_memid FOREIGN KEY (memid) REFERENCES cd.members(memid)
    );
```

## Scripts
##### Link to Load sample data into your database
- [clubdata.sql](https://github.com/jarviscanada/jarvis_data_eng_UmaraniKumaresan/blob/develop/sql/clubdata.sql)

##### Link for SQL Queries
- [queries.sql](https://github.com/jarviscanada/jarvis_data_eng_UmaraniKumaresan/blob/develop/sql/queries.sql)
 
## Database and Tables
![image]
     
