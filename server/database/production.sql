drop database if exists running;
create database running;
use running;

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

insert into app_role (`name`)
	values
    ("runner"),
    ("coach");

create table app_user_role (
	app_user_id int not null,
    app_role_id int not null,
    constraint pk_app_user_role
		primary key (app_user_id, app_role_id),
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
		distance int not null,
        unit varchar(50) not null,
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
		parent_comment_id int,
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
	`time` varchar(50) not null,
    `date` date not null
);