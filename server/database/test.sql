drop database if exists running_test;
create database running_test;
use running_test;

create table app_user (
	app_user_id int primary key auto_increment,
    email varchar(50) not null unique,
    username varchar(32) not null unique,
    password_hash varchar (2048) not null,
    enabled bit not null default(1)
);

create table app_role (
	app_role_id int primary key auto_increment,
    `name` varchar(50) not null unique
);

create table app_user_role (
	app_user_role_id int primary key auto_increment,
	app_user_id int not null,
    app_role_id int not null,
--     constraint pk_app_user_role
-- 		primary key (app_user_id, app_role_id),
	constraint fk_app_user_role_user_id
		foreign key (app_user_id)
        references app_user(app_user_id),
	constraint fk_app_user_role_role_id
		foreign key (app_role_id)
        references app_role(app_role_id)
    );
    
create table training_plan (
	training_plan_id int primary key auto_increment,
	app_user_id int not null,
	constraint fk_training_plan_user_id
		foreign key (app_user_id)
		references app_user(app_user_id),
	`name` varchar(50) not null,
	start_date date not null,
	end_date date not null,
	`description` varchar(240)
);

create table workout_type (
	workout_type_id int primary key auto_increment,
	`name` varchar(50) not null,
	`description` varchar(240)
);

create table workout (
	workout_id int primary key auto_increment,
	app_user_id int not null,
	constraint fk_workout_user_id
		foreign key (app_user_id)
		references app_user(app_user_id),
	workout_type_id int not null,
	constraint fk_workout_workout_type_id
		foreign key (workout_type_id)
		references workout_type(workout_type_id),
	`date` date not null,
	distance double,
	unit varchar(50),
	`description` varchar(240),
	effort varchar(240),
	training_plan_id int not null,
	constraint fk_workout_training_plan_id
		foreign key (training_plan_id)
		references training_plan(training_plan_id)
);

create table `comment` (
	comment_id int primary key auto_increment,
	workout_id int not null,
	constraint fk_comment_workout_id
		foreign key (workout_id)
		references workout(workout_id),
	app_user_id int not null,
	constraint fk_comment_user_id
		foreign key (app_user_id)
		references app_user(app_user_id),
	parent_comment_id int not null,
	`comment` varchar(1000) not null,
	`date` date not null
);

create table distance (
	distance_id int primary key auto_increment,
    distance varchar(50)
);

create table personal_best (
	personal_best_id int primary key auto_increment,
    app_user_id int not null,
    constraint fk_personal_best_user_id
		foreign key (app_user_id)
        references app_user(app_user_id),
	distance_id int not null,
    constraint fk_personal_best_distance_id
		foreign key (distance_id)
        references distance(distance_id),
	`time` time not null,
    `date` date not null
);
    
delimiter //
create procedure set_known_good_state()
begin

delete from app_user_role;
alter table app_user_role auto_increment = 1;

delete from personal_best;
alter table personal_best auto_increment = 1;

delete from distance;
alter table distance auto_increment = 1;

delete from `comment`;
alter table `comment` auto_increment = 1;

delete from workout;
alter table workout auto_increment = 1;

delete from workout_type;
alter table workout_type auto_increment = 1;

delete from training_plan;
alter table training_plan auto_increment = 1;

delete from app_role;
alter table app_role auto_increment = 1;

delete from app_user;
alter table app_user auto_increment = 1;

insert into app_user (email, username, password_hash)
	values
    ("nutellapatella@gmail.com", "NutellaPatella", "jellylegs2024"),
    ("coachnutellapatella@gmail.com", "CoachNutellaPatella", "jellylegs2024");
    
insert into app_role (`name`)
	values
    ("runner"),
    ("coach");
    
insert into app_user_role (app_user_id, app_role_id)
	values
    (1, 1),
    (2, 2);
    
insert into training_plan (app_user_id, `name`, start_date, end_date, `description`)
	values
	(1, "2024 NYC Marathon", "2024-07-01", "2024-11-03", "A 16-week training plan for the New York City Marathon on November 3, 2024.");

insert into workout_type (`name`, `description`)
	values
	("Intervals", null),
	("Hill Repeats", null),
	("Long Run", "A long, Zone 1-2 run to improve endurance and prompt musculoskeletal adaptations."),
	("Tempo", null),
	("Recovery Run", "A short, easy-effort run completed in Zone 1."),
	("Fartlek", null),
	("Base", null);
    
insert into workout (app_user_id, workout_type_id, `date`, distance, unit, `description`, effort, training_plan_id)
	values
	(1, 7, "2024-07-01", 3, "miles", null, "Zone 1", 1),
	(1, 3, "2024-07-07", 12, "miles", "Practice race-day nutrition by taking a gel every 5KM.", "Zone 2", 1);
    
insert into `comment` (workout_id, app_user_id, parent_comment_id, `comment`, `date`)
	values
	(1, 2, 1, "Great job on this one! How did you feel in the morning before the run?", "2024-07-01"),
	(1, 1, 1, "All right. A little tired in the legs, and I think it showed in my HR.", "2024-07-01");
    
insert into distance (distance)
	values
    ("1K"),
    ("1 mile"),
    ("5K"),
    ("10K"),
    ("Half Marathon"),
    ("Marathon");
    
insert into personal_best (app_user_id, distance_id, `time`, `date`)
	values
    (1, 6, "3:24:55", "2023-11-05");

end //
delimiter ;
