/*
Navicat MySQL Data Transfer

Source Server         : 123.15.58.214
Source Server Version : 50621
Source Host           : 123.15.58.214:3307
Source Database       : sundyn

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2017-03-08 17:03:52
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for monitor_exception
-- ----------------------------
DROP TABLE IF EXISTS `monitor_exception`;
CREATE TABLE `monitor_exception` (
  `eid` varchar(50) NOT NULL,
  `exceptionCause` varchar(3000) DEFAULT NULL,
  `callTime` datetime DEFAULT NULL,
  PRIMARY KEY (`eid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for monitor_method
-- ----------------------------
DROP TABLE IF EXISTS `monitor_method`;
CREATE TABLE `monitor_method` (
  `uuid` varchar(50) NOT NULL,
  `eid` varchar(50) NOT NULL,
  `className` varchar(100) DEFAULT NULL,
  `methodName` varchar(50) DEFAULT NULL,
  `argJson` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`eid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for monitor_statistic
-- ----------------------------
DROP TABLE IF EXISTS `monitor_statistic`;
CREATE TABLE `monitor_statistic` (
  `uuid` varchar(50) NOT NULL,
  `className` varchar(255) DEFAULT NULL,
  `methodName` varchar(255) DEFAULT NULL,
  `author` varchar(50) DEFAULT NULL,
  `callNum` int(11) DEFAULT NULL,
  `exceptionNum` int(11) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
