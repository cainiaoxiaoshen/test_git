/*
Navicat MySQL Data Transfer

Source Server         : sst_report
Source Server Version : 50621
Source Host           : localhost:3306
Source Database       : fss

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2017-07-27 14:04:02
*/
CREATE DATABASE IF NOT EXISTS `fss` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;

DROP USER 'fss'@'localhost';
create user 'fss'@'localhost' identified by '0okm@WSX';
grant all on `fss`.* to 'fss'@'localhost';

SET FOREIGN_KEY_CHECKS=0;

USE `fss`;
-- ----------------------------
-- Table structure for `annotate`
-- ----------------------------
DROP TABLE IF EXISTS `annotate`;
CREATE TABLE `annotate` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `docId` bigint(20) DEFAULT NULL COMMENT '文档ID',
  `product` varchar(255) COLLATE utf8_bin NOT NULL,
  `factor` varchar(255) COLLATE utf8_bin NOT NULL,
  `max` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `min` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `unit` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `adi` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `adiWeb` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `cns` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ins` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `cas` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `struc` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `mole` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `property` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `toxico` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `biological` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `funct` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `disease` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `proremark` text COLLATE utf8_bin,
  `facremark` text COLLATE utf8_bin,
  `createDate` datetime NOT NULL,
  `user` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `page` int(11) NOT NULL,
  `testsId` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `prostd` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `product` (`product`,`factor`)
) ENGINE=InnoDB AUTO_INCREMENT=1174 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for `annotate_check`
-- ----------------------------
DROP TABLE IF EXISTS `annotate_check`;
CREATE TABLE `annotate_check` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `docId` varchar(255) NOT NULL COMMENT '文档id',
  `antId` bigint(20) NOT NULL COMMENT '达标id',
  `createUser` varchar(64) NOT NULL,
  `createTime` datetime NOT NULL,
  `operation` tinyint(4) NOT NULL COMMENT '1：增加；2：删除；3：修改',
  `description` text,
  `grade` tinyint(4) NOT NULL COMMENT '1：一级审核；2：二级审核',
  PRIMARY KEY (`id`),
  UNIQUE KEY `antId` (`antId`)
) ENGINE=InnoDB AUTO_INCREMENT=143 DEFAULT CHARSET=utf8 COMMENT='审核记录表';


-- ----------------------------
-- Table structure for `annotate_doc`
-- ----------------------------
DROP TABLE IF EXISTS `annotate_doc`;
CREATE TABLE `annotate_doc` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createUser` varchar(64) COLLATE utf8_bin NOT NULL,
  `createTime` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `modiUser` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `modiTime` datetime DEFAULT NULL,
  `type` tinyint(1) DEFAULT NULL COMMENT '1标准/2法规/3研究报告',
  `fileName` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `reqUrl` text COLLATE utf8_bin NOT NULL,
  `format` varchar(16) COLLATE utf8_bin NOT NULL,
  `docName` text COLLATE utf8_bin,
  `proSystem` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `language` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `country` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `pubTime` datetime DEFAULT NULL,
  `impTime` datetime DEFAULT NULL,
  `description` text COLLATE utf8_bin,
  `replace` tinyint(11) NOT NULL COMMENT '文档代替关系，0:没有代替文档；1：有代替文档',
  `tagState` tinyint(11) NOT NULL COMMENT '打标状态，1：未打标；2：打标中；3：打标完成',
  `annotateUser` varchar(64) COLLATE utf8_bin DEFAULT '',
  `firstCheckUser` varchar(64) COLLATE utf8_bin DEFAULT '',
  `secondCheckUser` varchar(64) COLLATE utf8_bin DEFAULT '',
  `keyWord` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `version` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '同步版本',
  `standardNo` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '标准文档编号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `docNo` (`reqUrl`(255)),
  UNIQUE KEY `standardNo` (`standardNo`)
) ENGINE=InnoDB AUTO_INCREMENT=2698 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for `annotate_word`
-- ----------------------------
DROP TABLE IF EXISTS `annotate_word`;
CREATE TABLE `annotate_word` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `annotateWord` varchar(255) COLLATE utf8_bin NOT NULL,
  `version` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `annotateWord` (`annotateWord`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


-- ----------------------------
-- Table structure for `annotate_xpath`
-- ----------------------------
DROP TABLE IF EXISTS `annotate_xpath`;
CREATE TABLE `annotate_xpath` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `docId` bigint(20) NOT NULL,
  `product` varchar(255) COLLATE utf8_bin NOT NULL,
  `factor` varchar(255) COLLATE utf8_bin NOT NULL,
  `text` varchar(255) COLLATE utf8_bin NOT NULL,
  `xpath` text COLLATE utf8_bin NOT NULL,
  `start` varchar(255) COLLATE utf8_bin NOT NULL,
  `length` int(11) NOT NULL,
  `type` int(11) NOT NULL COMMENT '1：产品，2：物质，3：其他',
  `index` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '-1',
  `updatedText` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `updated` varchar(16) COLLATE utf8_bin NOT NULL DEFAULT 'false',
  PRIMARY KEY (`id`),
  UNIQUE KEY `docId` (`docId`,`product`,`factor`,`type`)
) ENGINE=InnoDB AUTO_INCREMENT=6911 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


-- ----------------------------
-- Table structure for `AUTHORITY`
-- ----------------------------
DROP TABLE IF EXISTS `AUTHORITY`;
CREATE TABLE `AUTHORITY` (
  `AUTHORITY_ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_ID` int(11) NOT NULL,
  `ROLE` varchar(32) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`AUTHORITY_ID`),
  UNIQUE KEY `USER_ID` (`USER_ID`,`ROLE`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for `country`
-- ----------------------------
DROP TABLE IF EXISTS `country`;
CREATE TABLE `country` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `country` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `country` (`country`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for `doc_replace`
-- ----------------------------
DROP TABLE IF EXISTS `doc_replace`;
CREATE TABLE `doc_replace` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createUser` bigint(20) DEFAULT NULL COMMENT '创建者',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `modiUser` bigint(20) DEFAULT NULL COMMENT '修改者',
  `modiTime` datetime DEFAULT NULL COMMENT '修改时间',
  `newDoc` varchar(255) DEFAULT NULL COMMENT '新文档编号',
  `oldDoc` varchar(255) DEFAULT NULL COMMENT '旧文档编号',
  `relation` tinyint(1) DEFAULT NULL COMMENT '1：完全代替；0：部分代替',
  `remark` text COMMENT '备注',
  `isDel` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除；0：未删除，1：已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=201 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `pageinclude`
-- ----------------------------
DROP TABLE IF EXISTS `pageinclude`;
CREATE TABLE `pageinclude` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `docNo` varchar(64) COLLATE utf8_bin NOT NULL,
  `includes` varchar(255) COLLATE utf8_bin NOT NULL,
  `page` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `docNo` (`docNo`,`page`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3812 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for `preselection_words`
-- ----------------------------
DROP TABLE IF EXISTS `preselection_words`;
CREATE TABLE `preselection_words` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `word` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '关键词',
  `state` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0表示未被选择，1表示已经被选择',
  `frequency` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `word` (`word`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for `persistent_logins`
-- ----------------------------
DROP TABLE IF EXISTS `persistent_logins`;
CREATE TABLE `persistent_logins` (
  `username` varchar(64) COLLATE utf8_bin NOT NULL,
  `series` varchar(64) COLLATE utf8_bin NOT NULL,
  `token` varchar(64) COLLATE utf8_bin NOT NULL,
  `last_used` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for `synonym`
-- ----------------------------
DROP TABLE IF EXISTS `synonym`;
CREATE TABLE `synonym` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createUser` varchar(100) DEFAULT NULL COMMENT '创建者',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `mainWord` varchar(500) DEFAULT NULL COMMENT '核心词汇',
  `synWord` varchar(500) DEFAULT NULL COMMENT '同义词',
  `isDel` tinyint(2) DEFAULT '0' COMMENT '是否删除；0：未删除，1：已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COMMENT='同义词表';


-- ----------------------------
-- Table structure for `url_role`
-- ----------------------------
DROP TABLE IF EXISTS `url_role`;
CREATE TABLE `url_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(255) COLLATE utf8_bin NOT NULL,
  `role` varchar(16) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `url` (`url`,`role`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `USER`;
CREATE TABLE `USER` (
  `USER_ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(64) COLLATE utf8_bin NOT NULL,
  `PASSWORD` varchar(32) COLLATE utf8_bin NOT NULL,
  `EMAIL` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `LOCKED` bit(1) NOT NULL,
  `ENABLED` bit(1) NOT NULL,
  `EXPIRATION` datetime NOT NULL,
  `CREATEDATE` datetime NOT NULL,
  PRIMARY KEY (`USER_ID`),
  UNIQUE KEY `NAME` (`NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


-- ----------------------------
-- View structure for `marknum`
-- ----------------------------
DROP VIEW IF EXISTS `marknum`;
CREATE ALGORITHM=UNDEFINED DEFINER=`fss`@`localhost` SQL SECURITY DEFINER VIEW `marknum` AS select `ant`.`docId` AS `docId`,count(`ant`.`docId`) AS `antNum` from `annotate` `ant` group by `ant`.`docId` ;


-- ----------------------------
-- View structure for `checkinfo`
-- ----------------------------
DROP VIEW IF EXISTS `checkinfo`;
CREATE ALGORITHM=UNDEFINED DEFINER=`fss`@`localhost` SQL SECURITY DEFINER VIEW `checkinfo` AS select `chk`.`docId` AS `docId` from `annotate_check` `chk` group by `chk`.`antId` ;

-- ----------------------------
-- View structure for `checkrecord`
-- ----------------------------
DROP VIEW IF EXISTS `checkrecord`;
CREATE ALGORITHM=UNDEFINED DEFINER=`fss`@`localhost` SQL SECURITY DEFINER VIEW `checkrecord` AS select `antchk`.`docId` AS `docId`,count(`antchk`.`docId`) AS `checkRecordNum` from `annotate_check` `antchk` group by `antchk`.`docId` ;


-- ----------------------------
-- View structure for `doccheckinfo`
-- ----------------------------
DROP VIEW IF EXISTS `doccheckinfo`;
CREATE ALGORITHM=UNDEFINED DEFINER=`fss`@`localhost` SQL SECURITY DEFINER VIEW `doccheckinfo` AS select `antchk`.`docId` AS `docId`,count(`antchk`.`docId`) AS `antChkNum` from `checkinfo` `antchk` group by `antchk`.`docId` ;


-- ----------------------------
-- View structure for `markmonitor`
-- ----------------------------
DROP VIEW IF EXISTS `markmonitor`;
CREATE ALGORITHM=UNDEFINED DEFINER=`fss`@`localhost` SQL SECURITY DEFINER VIEW `markmonitor` AS select `doc`.`id` AS `id`,`doc`.`docName` AS `docName`,`doc`.`type` AS `type`,`doc`.`tagState` AS `tagState`,`doc`.`country` AS `country`,`doc`.`annotateUser` AS `annotateUser`,`doc`.`firstCheckUser` AS `firstCheckUser`,`doc`.`secondCheckUser` AS `secondCheckUser`,`doc`.`standardNo` AS `standardNo`,`docantnum`.`antNum` AS `antNum`,`docchknum`.`antChkNum` AS `antChkNum`,`chkrecord`.`checkRecordNum` AS `checkRecordNum`,format(((`docchknum`.`antChkNum` / `docantnum`.`antNum`) * 100),2) AS `errorRate` from (((`annotate_doc` `doc` left join `marknum` `docantnum` on((`doc`.`id` = `docantnum`.`docId`))) left join `doccheckinfo` `docchknum` on((`doc`.`id` = `docchknum`.`docId`))) left join `checkrecord` `chkrecord` on((`doc`.`id` = `chkrecord`.`docId`))) ;
