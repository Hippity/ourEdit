create database if not exists usersinfo;
 
use usersinfo;
drop table if exists passwordsandnames;
create table passwordsandnames (
username varchar(50),
passwords varchar(50),
primary key (username));
 
insert into passwordsandnames values ('user1', '1234');
insert into passwordsandnames values ('user2', 'pass');
insert into passwordsandnames values ('user3', 'word');
insert into passwordsandnames values ('user4', '350');
insert into passwordsandnames values ('user5', 'eece');
