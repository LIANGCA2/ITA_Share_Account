/*
Navicat MySQL Data Transfer

Source Server         : home
Source Server Version : 50527
Source Host           : localhost:3306
Source Database       : account

Target Server Type    : MYSQL
Target Server Version : 50527
File Encoding         : 65001

Date: 2019-03-09 20:03:58
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `account`
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `user_id` int(255) NOT NULL,
  `type_id` int(255) NOT NULL,
  `amount` double(255,2) NOT NULL,
  `date` date DEFAULT NULL,
  `account_kind` varchar(255) DEFAULT NULL,
  `is_delete` varchar(255) NOT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `type_id` (`type_id`),
  CONSTRAINT `account_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `account_ibfk_2` FOREIGN KEY (`type_id`) REFERENCES `type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES ('1', '1', '1', '12.00', '2019-03-06', '1', '1', 'hahhah');
INSERT INTO `account` VALUES ('2', '1', '1', '12.00', '2019-03-08', '1', '0', 'hahahah');
INSERT INTO `account` VALUES ('3', '1', '1', '11.00', '2019-03-09', '1', '0', 'ahaha');
INSERT INTO `account` VALUES ('4', '1', '1', '12.00', '2019-03-08', '1', '0', 'haha');
INSERT INTO `account` VALUES ('5', '1', '1', '12.00', '2019-03-05', '1', '0', 'aha');
INSERT INTO `account` VALUES ('6', '1', '1', '12.00', '2019-03-09', '1', '0', 'hah');

-- ----------------------------
-- Table structure for `type`
-- ----------------------------
DROP TABLE IF EXISTS `type`;
CREATE TABLE `type` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `type` varchar(255) NOT NULL,
  `account_kind` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of type
-- ----------------------------
INSERT INTO `type` VALUES ('1', '餐饮', '0');
INSERT INTO `type` VALUES ('2', '零食', '0');
INSERT INTO `type` VALUES ('3', '交通', '0');
INSERT INTO `type` VALUES ('4', '工资', '1');
INSERT INTO `type` VALUES ('5', '收红包', '1');
INSERT INTO `type` VALUES ('6', '生活费', '1');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `uid` varchar(255) NOT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '2', '2019-03-08 20:02:57');
INSERT INTO `user` VALUES ('2', 'odGry5JBwd0OvQ3BpxSQGhwQdMZE', '2019-03-05 20:03:01');
INSERT INTO `user` VALUES ('3', 'odGry5KZ7EtA_MffXzfesqJ03Knw', '2019-03-06 20:03:07');
INSERT INTO `user` VALUES ('4', 'odGry5EDkC317H7EChIWwQUN8kuE', '2019-03-05 20:03:12');
