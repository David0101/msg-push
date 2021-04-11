/*
Navicat MySQL Data Transfer

Source Server         : 本地电脑
Source Server Version : 50713
Source Host           : localhost:3306


Target Server Type    : MYSQL
Target Server Version : 50713
File Encoding         : 65001

Date: 2021-04-11 17:33:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `oauth_client_details`
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details` (
  `client_id` varchar(255) NOT NULL,
  `resource_ids` varchar(255) DEFAULT NULL,
  `client_secret` varchar(255) DEFAULT NULL,
  `scope` varchar(255) DEFAULT NULL,
  `authorized_grant_types` varchar(255) DEFAULT NULL,
  `web_server_redirect_uri` varchar(255) DEFAULT NULL,
  `authorities` varchar(255) DEFAULT NULL,
  `access_token_validity` tinyint(4) DEFAULT NULL,
  `refresh_token_validity` tinyint(4) DEFAULT NULL,
  `additional_information` varchar(255) DEFAULT NULL,
  `autoapprove` varchar(255) DEFAULT NULL,
  `available` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------

INSERT INTO `oauth_client_details` VALUES ('android', null, 'UixD3r2xs2RLcY00TU5vfvnBXRUnqrkh', 'login', 'password', null, null, '1', '1', null, null, '1');
INSERT INTO `oauth_client_details` VALUES ('ios', null, 'UixD3r2xs2RLcY00TU5vfvnBXRUnqrka', 'login', 'password', null, null, '1', '1', null, null, '1');
INSERT INTO `oauth_client_details` VALUES ('iPad', null, 'UixD3r2xs2RLcY00TU5vfvnBXRUnqrkh', 'login', 'password', null, null, '1', '1', null, null, '1');
INSERT INTO `oauth_client_details` VALUES ('wechatHFive', null, 'UixD3r2xs2RLcY00TU5vfvnBXRUnqrkh', 'login', 'password', null, null, '1', '1', null, null, '1');
INSERT INTO `oauth_client_details` VALUES ('windows', null, 'UixD3r2xs2RLcY00TU5vfvnBXRUnqrkq', 'login', 'password', null, null, '1', '1', null, null, '1');
INSERT INTO `oauth_client_details` VALUES ('web', null, 'UixD3r2xs2RLcY00TU5vfvnBXRUnqrkk', 'login', 'password', null, null, '1', '1', null, null, '1');

