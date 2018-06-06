CREATE DATABASE seckill;

use seckill;

CREATE TABLE seckill(
    seckill_id  int(11) NOT NULL AUTO_INCREMENT COMMENT 'product id',
    name VARCHAR (120) NOT NULL COMMENT 'product name',
    number int(11) NOT NULL COMMENT 'stock',
    start_time TIMESTAMP NOT NULL COMMENT 'seckill start time',
    end_time TIMESTAMP NOT NULL COMMENT 'seckill end time',
    create_time TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(seckill_id),
    key idx_start_time(start_time),
    key idx_end_time(end_time),
    key idx_create_time(create_time)
) ENGINE = InnoDB AUTO_INCREMENT = 1000 DEFAULT CHARSET=utf8 COMMENT='秒杀库存表';

INSERT INT seckill(name, number, start_time, end_time)
VALUES
    ('1000元秒杀IPHONE', 100, '2018-6-04 00:00:00', '2018-6-05 00:00:00'),
    ('500元秒杀扫地机器人', 100, '2018-6-03 00:00:00', '2018-6-04 00:00:00'),
    ('1000元秒杀Intel 10', 100, '2018-6-03 00:00:00', '2018-6-04 00:00:00'),
    ('1000元秒杀Mac Pro', 100, '2018-6-05 00:00:00', '2018-6-06 00:00:00');

CREATE TABLE success_killed (
    seckill_id int(11) NOT NULL COMMENT 'product id',
    user_phone VARCHAR(11) NOT NULL  COMMENT 'user phone',
    state TINYINT NOT NULL DEFAULT -1 COMMENT 'state:-1:invalid 0:success 1:paid',
    create_time TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(seckill_id, user_phone),
    key idx_create_time(create_time)
) ENGINE = InnoDB  DEFAULT CHARSET=utf8 COMMENT='秒杀成功明细表';