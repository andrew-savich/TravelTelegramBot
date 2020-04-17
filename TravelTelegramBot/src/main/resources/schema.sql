create table cities(
city_id int(10) primary key auto_increment,
city_title varchar(50) not null unique,
description text not null
);