use usersinfo;
drop table if exists filenamesandcreators;
create table filenamesandcreators (
filename varchar(50),
filecreator varchar(50),
primary key (filename));
 
insert into filenamesandcreators values ('file1','user1');
insert into filenamesandcreators values ('file2','user2');
insert into filenamesandcreators values ('file3','user3');
insert into filenamesandcreators values ('file4','user4');
insert into filenamesandcreators values ('file5','user5');
