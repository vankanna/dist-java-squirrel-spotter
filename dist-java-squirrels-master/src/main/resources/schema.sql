create table Squirrel (
      squirrel_id int primary key,
      species varchar(200) not null,
      common_name varchar(200) not null,
      habitat varchar(200) not null,
      image_file_name varchar(200)
);

create table Location (
    location_id int primary key,
    name varchar(200) not null,
    country varchar(50) not null
);

create table Sighting (
    sighting_id identity,
    squirrel_id int not null,
    spotter_name varchar(200) not null,
    location_id int not null,
    count int not null default 0,
    spotted_at timestamp not null,
    temperature int default 70
);

alter table Sighting
    add foreign key (squirrel_id) references Squirrel(squirrel_id);

alter table Sighting
    add foreign key (location_id) references Location(location_id);