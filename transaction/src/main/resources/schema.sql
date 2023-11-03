CREATE TABLE IF NOT EXISTS `transaction` (
    `trx_ref_no` int not null PRIMARY KEY,
    `version` int not null,
    `account_number` int not null,
    `trx_amount` number (18,2) not null,
    `description` varchar(100) not null,
    `trx_date` timestamp not null,
    `trx_time` timestamp not null,
    `customer_id` varchar(40) not null,
    `created_date` timestamp not null,
    `created_by` varchar(40) not null,
    `updated_date` timestamp default null,
    `updated_by` varchar(40) default null
);

CREATE TABLE IF NOT EXISTS `customer` (
    `customer_id` varchar(40) PRIMARY KEY,
    `version` int not null,
    `name` varchar(100) not null,
    `email` varchar(100) not null,
    `phone` varchar(100) not null,
    `created_date` timestamp not null,
    `created_by` varchar(40) not null,
    `updated_date` timestamp default null,
    `updated_by` varchar(40) default null
);

INSERT INTO `customer` values ( '222', 0, 'Customer 222', 'customer222@email.com', '017111000222' , CURRENT_TIMESTAMP, 'System', null, null);
INSERT INTO `customer` values ( '333', 0, 'Customer 333', 'customer333@email.com', '017111000333' , CURRENT_TIMESTAMP, 'System', null, null);

CREATE TABLE IF NOT EXISTS `account` (
    `account_no` varchar(100) PRIMARY KEY,
    `version` int not null,
    `customer_id` varchar(40) not null,
    `account_type` varchar(100) not null,
    `balance` number(18,2) not null,
    `created_date` timestamp not null,
    `created_by` varchar(40) not null,
    `updated_date` timestamp default null,
    `updated_by` varchar(40) default null
);

INSERT INTO `account` values ( '8872838283', 0, '222', 'SA', 1000 , CURRENT_TIMESTAMP, 'System', null, null);
INSERT INTO `account` values ( '8872838299', 0, '222', 'SA', 1500 , CURRENT_TIMESTAMP, 'System', null, null);
INSERT INTO `account` values ( '6872838260', 0, '333', 'SA', 1500 , CURRENT_TIMESTAMP, 'System', null, null);