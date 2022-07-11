drop table if exists promotions;
create table promotions (
    id int primary key auto_increment,
    user_id int not null,
    promotion_code varchar(30) not null,
    discount_amount int not null check (discount_amount > 0),
    start_date datetime not null,
    end_date datetime not null,
    constraint fk_promotion_user foreign key (user_id) references users (id),
    constraint uq_promotion_code unique (promotion_code)
);