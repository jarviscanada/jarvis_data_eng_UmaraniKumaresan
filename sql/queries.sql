--1--add new facility to the facilities table
insert into cd.facilities
    (facid, name, membercost, guestcost, initialoutlay, monthlymaintenance)
    values (9, 'Spa', 20, 30, 100000, 800);

--2--creating a sequence to create facid sequence automatically 
create sequence cd.facid_seq start with 10;

insert into cd.facilities
    (facid, name, membercost, guestcost, initialoutlay, monthlymaintenance)
    values(nextval('cd.facid_seq'),'Spa', 20, 30, 100000, 800);

--3-- Update initialoutlay to 10000
update cd.facilities set initialoutlay = 10000 where name = 'Tennis Court 2';

--4--increase member cost 10%
update cd.facilities set membercost = membercost * 1.10, guestcost = guestcost * 1.10 where name = 'Tennis Court 2';

--5-- delete data from cd.bookings
delete from cd.bookings;

--6--delete memid 37 from cd.bookings
delete from cd.members where memid = 37;

--7--find membercost greater than 1/50th of maintenance
select facid, name, membercost, monthlymaintenance from cd.facilities where membercost< monthlymaintenance/50 and membercost>0;

--8.--display rows with name Tennis
select * from cd.facilities where name like '%Tennis%';

--9--display rows with facid 1 and 5
select * from cd.facilities where facid in (1,5);

--10 display rows of members who joined after sep 12
select memid, surname, firstname, joindate from cd.members where joindate >= to_date('sep-2012','mon-yyyy');

--11--combining 2 results with union
select surname from cd.members 
union
select name from cd.facilities;

--12--display starttime with any of the below queries
select booking.starttime from cd.bookings booking, cd.members mem where mem.firstname = 'David' and mem.surname = 'Farrell' and mem.memid=booking.memid;

select b.starttime from cd.bookings b inner join cd.members m on b.memid=m.memid where m.firstname = 'David' and m.surname = 'Farrell';

select starttime from cd.bookings where memid in (select memid from cd.members where firstname = 'David' and surname = 'Farrell');

--13--display starttime and name 
select b.starttime,f.name from cd.bookings b inner join cd.facilities f on b.facid=f.facid where starttime between '21-sep-2012 00:00:00' and  '21-sep-2012 23:59:59' order by starttime;

--14--join tables to display names with recommendedy names
select m1.firstname as memfname, m1.surname as memsname, m2.firstname as recfname, m2.surname as recsname from cd.members m1 left outer join cd.members m2 on m1.recommendedby=m2.memid order by memid,memsname,memfname;

--15--display recommended names without duplicate
select distinct m2.firstname,m2.surname from cd.members m1 join cd.members m2 on m1.recommendedby=m2.memid order by m2.surname, m2.firstname;

--16--display recommended names without join
select distinct m.firstname|| ' ' ||m.surname as member, 
(select r.firstname|| ' '||r.surname as recommender from cd.members r where m.recommendedby=r.memid)  
from cd.members m;

--17--count the recommenders
select recommendedby, count(*) from cd.members where recommendedby is not null group by recommendedby order by recommendedby;

--18--sum of slots 
select facid, sum(slots) from cd.bookings group by facid order by facid;

--19--sum of slots in september
select facid, sum(slots) from cd.bookings where starttime >= '2012-09-01' and starttime < '2012-10-01' group by facid order by facid;

--20--sum of slots in 2012
select facid, sum(slots) , to_char(starttime, 'mon-yyyy') from cd.bookings where to_char(starttime, 'yyyy')='2012' group by facid, to_char(starttime, 'mon-yyyy') order by facid;

--21--count of members
select count(distinct memid) from cd.bookings;

--22--list members booked after september 2012
select m.surname,m.firstname,m.memid,min(b.starttime) from cd.members m join cd.bookings b on m.memid=b.memid 
where to_char(b.starttime,'dd-mon-yyyy')>='01-sep-2012' group by m.surname,m.firstname,m.memid  order by memid;

--23--list of members count
select (select count(*) from cd.members), firstname,surname from cd.members order by joindate;

--24--row number
select row_number() over (order by joindate) as rownum, firstname,surname from cd.members;

--25--rank total slots descending
select facid,total_slots as total from (
select facid, rank() over (order by sum(slots) desc) rank,  sum(slots) as total_slots 
from cd.bookings group by facid order by sum(slots) desc)a where rank=1;

--26--concate string
select surname || ',' || firstname from cd.members;

--27--find formatted telephone number
select memid, telephone from cd.members where telephone like '(%';

--28--surname string count
select substr(surname,1,1) as letter, count(*) from cd.members group by substr(surname,1,1);





