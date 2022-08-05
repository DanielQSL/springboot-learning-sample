CREATE
DATABASE bigfile;
use
bigfile;
DROP TABLE IF EXISTS `web_request_multiple`;
CREATE TABLE `web_request_multiple`
(
    `time`        bigint(20) DEFAULT NULL,
    `src_ip`      varchar(15)  DEFAULT NULL,
    `request_url` varchar(255) DEFAULT NULL,
    `dest_ip`     varchar(15)  DEFAULT NULL,
    `dest_port`   int(11) DEFAULT NULL,
    `method`      varchar(32)  DEFAULT NULL,
    `user_agent`  varchar(22)  DEFAULT NULL,
    `connection`  varchar(32)  DEFAULT NULL,
    `server`      varchar(32)  DEFAULT NULL,
    `status`      varchar(20)  DEFAULT NULL,
    `protocol`    varchar(32)  DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;