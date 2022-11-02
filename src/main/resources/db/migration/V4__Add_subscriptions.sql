create table subscription (id bigint not null auto_increment, duration bigint, name varchar(255), price double precision, primary key (id)) engine=InnoDB;
create table user_subscriptions (user_id bigint not null, subscription_id bigint not null) engine=InnoDB;
alter table user_subscriptions add constraint FKb5sq3r6j9httcp67kf6cxrcon foreign key (subscription_id) references subscription (id);
alter table user_subscriptions add constraint FKj9lcudpdt2qgdjorv2jqchgd foreign key (user_id) references user (id);

INSERT INTO subscription (name, price, duration) VALUES ("monthly", 5, 43800);