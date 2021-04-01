SET FOREIGN_KEY_CHECKS=0;

DROP DATABASE IF EXISTS `mbg_example`;

CREATE DATABASE `mbg_example` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE `mbg_example`;

-- ----------------------------
-- Table structure for mbg_test
-- ----------------------------
DROP TABLE IF EXISTS `mbg_test`;
CREATE TABLE `mbg_test` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) DEFAULT NULL COMMENT '姓名',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='mybatis-generator插件测试表';

-- ----------------------------
-- Records of mbg_test
-- ----------------------------
INSERT INTO `mbg_test` VALUES ('1', 'tom', '20');
