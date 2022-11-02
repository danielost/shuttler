create table role (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB;
create table route (id bigint not null auto_increment, number integer, type integer, user_id bigint, primary key (id)) engine=InnoDB;
create table route_stop (route_id bigint not null, stop_id bigint not null, primary key (route_id, stop_id)) engine=InnoDB;
create table saved_route (user_id bigint not null, route_id bigint not null, primary key (user_id, route_id)) engine=InnoDB;
create table stop (id bigint not null auto_increment, number integer, street varchar(255), primary key (id)) engine=InnoDB;
create table user (id bigint not null auto_increment, first_name varchar(255), last_name varchar(255), password varchar(255), username varchar(255), primary key (id)) engine=InnoDB;
create table user_roles (user_id bigint not null, role_id bigint not null) engine=InnoDB;
create table vehicle (vin varchar(255) not null, current_capacity integer, max_capacity integer, user_id bigint, route_id bigint, primary key (vin)) engine=InnoDB;

alter table route add constraint FK86h3h3q4xcl9oils4g347hut2 foreign key (user_id) references user (id);
alter table route_stop add constraint FKmu1b9fkhu2d4982t14wc4wjg9 foreign key (stop_id) references stop (id);
alter table route_stop add constraint FKrah0j8khs716aqhsqt3x5yxbw foreign key (route_id) references route (id);
alter table saved_route add constraint FK7aqpuye88x3o7qh0jnrlvu0bp foreign key (route_id) references route (id);
alter table saved_route add constraint FKemgafxxsbvqvxpwek4e6pcu93 foreign key (user_id) references user (id);
alter table user_roles add constraint FKrhfovtciq1l558cw6udg0h0d3 foreign key (role_id) references role (id);
alter table user_roles add constraint FK55itppkw3i07do3h7qoclqd4k foreign key (user_id) references user (id);
alter table vehicle add constraint FKrmyxjc1r0nxymg692mq9emy56 foreign key (user_id) references user (id);
alter table vehicle add constraint FKcfjetj3hsyed753xtecuxv8c3 foreign key (route_id) references route (id);