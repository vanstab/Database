
--SQLite inventoryManager

--DROP ALL TABLES
DROP TABLE IF EXISTS Item;
DROP TABLE IF EXISTS Bin;
DROP TABLE IF EXISTS User;
DROP TABLE IF EXISTS Team;
DROP TABLE IF EXISTS DeletedItem;
--CREATE DATABASE TABLES
--=======================

create table if not exists Item(
      IdTag integer primary key AUTOINCREMENT,
      BinNum integer not null,
      ModelNumber text,
      Department text, 
      Description text,
      ReplacementCost float, 
      Price float, 
      Dimensions text,
      Length int,
      Weight float,
      PurchaseDate int, --yyyymmdd
      WarrantyEndDate int,--yyyymmdd
      Category text,
      FOREIGN KEY (BinNum) REFERENCES Bin(BinNum)
      );

create table if not exists Bin(
      BinNum integer primary key not null,
      Description text, 
      Dimensions text
      );

create table if not exists User(
      UserName text primary key not null,
      Password text,
      Admin boolean  --0 or 1
      );

create table if not exists DeletedItem(
      Id integer primary key AUTOINCREMENT,
      IdTag int,
      BinNum integer not null,
      ModelNumber text,
      Department text, 
      Description text,
      ReplacementCost float, 
      Price float, 
      Dimensions text,
      Length int,
      Weight float,
      PurchaseDate int, --yyyymmdd
      WarrantyEndDate int,--yyyymmdd
      Category text
      );
--INSERT DATA
--=======================

begin transaction;

--Insert songs
--UCTY15 
insert into User(UserName, Password,  Admin) values ('BenV', 'hi',1);
insert into User(UserName, Password,  Admin) values ('John', 'hi', 0);

insert into Bin(BinNum, Description , Dimensions) values (704, 'Bin0','10x10x2');
insert into Bin(BinNum, Description , Dimensions) values (111, 'Bin1','10x10x5');
insert into Bin(BinNum, Description , Dimensions) values (904, 'Bin2','10x10x7');
insert into Bin(BinNum, Description , Dimensions) values (302, 'Bin3','10x10x9');
insert into Bin(BinNum, Description , Dimensions) values (402, 'Bin4','10x10x2');
insert into Bin(BinNum, Description , Dimensions) values (113, 'Bin0','10x10x2');
insert into Bin(BinNum, Description , Dimensions) values (701, 'Bin1','10x10x5');
insert into Bin(BinNum, Description , Dimensions) values (900, 'Bin2','10x10x7');
insert into Bin(BinNum, Description , Dimensions) values (703, 'Bin3','10x10x9');

end transaction;