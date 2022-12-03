/*
Navicat MySQL Data Transfer

Source Server         : 8.0
Source Server Version : 80022
Source Host           : localhost:3307
Source Database       : db_time_attendance

Target Server Type    : MYSQL
Target Server Version : 80022
File Encoding         : 65001

Date: 2021-01-23 16:05:55
*/
CREATE DATABASE db_time_attendance;
USE db_time_attendance;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_emp`
-- ----------------------------
DROP TABLE IF EXISTS `t_emp`;
CREATE TABLE `t_emp` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `code` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of t_emp
-- ----------------------------
INSERT INTO `t_emp` VALUES ('1', '张三', '42c68589b0c642f699c4da379613079a');
INSERT INTO `t_emp` VALUES ('2', '李四', '1a4f5496a8ca4cdf9bfb71bb18748ea6');
INSERT INTO `t_emp` VALUES ('3', '王五', '9ecb3912c4df4a86a9a67dc02fea56e8');
INSERT INTO `t_emp` VALUES ('4', '赵六', '6b94bf8c4ddb4b2ca97ce63ff8d14103');

-- ----------------------------
-- Table structure for `t_lock_in_record`
-- ----------------------------
DROP TABLE IF EXISTS `t_lock_in_record`;
CREATE TABLE `t_lock_in_record` (
  `emp_id` int NOT NULL,
  `lock_in_time` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of t_lock_in_record
-- ----------------------------
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-01 08:33:00');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-01 17:29:20');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-02 09:16:37');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-02 17:29:37');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-03 08:55:59');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-03 17:15:24');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-04 08:11:50');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-04 17:50:19');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-05 08:28:55');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-05 17:15:30');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-06 08:49:58');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-06 17:38:25');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-07 08:11:21');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-07 17:37:54');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-08 08:19:36');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-08 17:18:11');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-09 08:15:31');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-09 17:13:28');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-10 08:43:33');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-10 17:36:10');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-11 08:45:24');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-11 17:46:47');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-12 08:30:56');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-12 17:53:50');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-13 08:38:41');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-13 17:40:38');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-14 08:30:57');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-14 17:14:42');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-15 08:21:59');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-15 17:43:15');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-16 08:29:54');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-16 17:22:49');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-17 08:38:38');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-17 17:45:24');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-18 08:58:42');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-18 17:42:21');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-19 08:28:35');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-19 17:47:21');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-20 08:55:41');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-20 17:40:46');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-21 08:45:31');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-21 17:31:27');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-22 08:11:12');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-22 17:50:51');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-23 08:42:21');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-23 17:20:21');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-24 08:27:30');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-24 17:41:57');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-25 08:11:31');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-25 17:28:29');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-26 08:47:13');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-26 17:21:55');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-27 08:54:54');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-27 17:58:31');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-28 08:10:23');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-28 17:45:33');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-29 08:16:14');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-29 17:34:48');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-30 08:40:23');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-30 17:12:10');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-31 08:42:39');
INSERT INTO `t_lock_in_record` VALUES ('1', '2021-01-31 17:13:20');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-01 08:28:03');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-01 17:17:46');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-02 08:15:12');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-02 17:37:24');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-03 08:56:11');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-03 17:49:58');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-04 08:47:19');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-04 17:34:27');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-05 08:59:16');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-05 17:28:32');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-06 08:48:12');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-06 17:48:11');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-07 08:23:42');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-07 17:59:15');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-08 08:57:48');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-08 14:37:49');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-09 08:10:24');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-09 17:45:58');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-10 08:21:49');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-10 17:54:29');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-11 08:18:36');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-11 17:15:39');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-12 08:50:16');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-12 17:18:28');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-13 08:54:16');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-13 17:40:43');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-14 08:14:18');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-14 17:34:21');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-15 08:24:41');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-15 17:23:14');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-16 08:40:13');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-16 17:55:33');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-17 08:31:47');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-17 17:47:51');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-18 08:39:51');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-18 17:12:32');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-19 08:15:44');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-19 17:45:40');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-20 08:41:22');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-20 17:19:58');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-21 08:57:43');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-21 17:41:45');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-23 08:15:22');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-23 17:48:34');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-24 08:30:45');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-24 17:57:49');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-25 08:55:52');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-25 17:20:58');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-26 08:19:43');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-26 17:42:51');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-27 08:16:56');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-27 17:56:38');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-28 08:53:34');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-28 17:58:36');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-29 08:59:47');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-29 17:34:43');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-30 08:50:57');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-30 17:26:46');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-31 08:13:33');
INSERT INTO `t_lock_in_record` VALUES ('2', '2021-01-31 17:45:53');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-01 08:44:00');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-01 17:38:19');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-02 08:14:33');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-02 17:41:42');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-03 08:39:17');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-03 17:46:54');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-04 08:50:29');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-04 17:26:49');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-05 08:59:51');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-05 17:20:27');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-06 08:10:58');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-06 17:28:52');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-07 08:19:52');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-07 17:40:15');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-08 08:24:23');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-08 17:44:10');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-09 08:10:17');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-09 17:20:37');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-10 08:25:15');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-10 17:57:54');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-11 08:25:24');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-11 17:22:23');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-12 08:11:46');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-12 17:55:26');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-13 08:57:46');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-13 17:31:58');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-14 08:39:36');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-14 17:47:44');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-15 08:38:30');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-15 17:47:16');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-16 08:51:57');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-16 17:15:55');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-17 08:49:23');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-17 17:53:34');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-18 08:52:33');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-18 17:47:47');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-19 08:33:16');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-19 17:40:13');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-20 08:47:32');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-20 17:41:33');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-21 08:31:19');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-21 17:15:59');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-22 08:32:33');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-22 17:17:58');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-23 08:30:54');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-23 17:10:54');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-24 08:35:10');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-24 17:18:53');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-25 08:46:49');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-25 17:54:30');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-26 08:56:52');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-26 17:50:40');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-27 08:13:11');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-27 17:32:16');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-28 08:40:37');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-28 17:57:52');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-29 08:51:31');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-29 17:55:24');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-30 08:34:16');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-30 17:16:11');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-31 08:14:36');
INSERT INTO `t_lock_in_record` VALUES ('3', '2021-01-31 17:11:20');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-01 08:54:00');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-01 17:43:37');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-02 08:27:59');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-02 17:26:30');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-03 08:12:32');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-03 17:46:38');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-04 08:56:54');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-04 17:47:55');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-05 08:53:52');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-05 17:55:10');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-06 08:39:13');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-06 17:43:36');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-07 08:54:37');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-07 17:56:27');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-08 08:13:51');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-08 17:14:40');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-09 08:36:58');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-09 17:29:18');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-10 08:47:21');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-10 17:31:59');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-11 08:14:54');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-11 17:27:50');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-12 08:13:12');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-13 08:36:36');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-13 17:46:15');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-14 08:56:33');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-14 17:27:38');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-15 08:17:57');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-15 17:21:32');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-16 08:18:32');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-16 17:39:58');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-17 08:13:18');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-17 17:35:31');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-18 08:35:22');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-18 17:13:29');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-19 08:49:12');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-19 17:36:54');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-20 08:28:43');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-20 17:43:14');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-21 08:38:45');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-21 17:56:44');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-22 08:22:28');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-22 17:10:21');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-23 08:52:53');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-23 17:14:46');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-24 08:27:31');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-24 17:35:19');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-25 08:24:49');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-25 17:22:35');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-26 08:42:31');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-26 17:38:39');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-27 08:13:40');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-27 17:10:47');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-28 08:23:19');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-28 17:48:10');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-29 08:59:10');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-29 17:39:47');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-30 08:51:42');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-30 17:19:16');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-31 08:15:21');
INSERT INTO `t_lock_in_record` VALUES ('4', '2021-01-31 17:33:22');

-- ----------------------------
-- Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', 'mr', 'mrsoft');
INSERT INTO `t_user` VALUES ('2', 'admin', 'admin');

-- ----------------------------
-- Table structure for `t_work_time`
-- ----------------------------
DROP TABLE IF EXISTS `t_work_time`;
CREATE TABLE `t_work_time` (
  `start` time NOT NULL,
  `end` time NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of t_work_time
-- ----------------------------
INSERT INTO `t_work_time` VALUES ('09:00:00', '17:00:00');
