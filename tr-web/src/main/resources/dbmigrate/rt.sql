/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : tr-sys

Target Server Type    : MYSQL
Target Server Version : 50599
File Encoding         : 65001

Date: 2018-03-15 14:51:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_icon
-- ----------------------------
DROP TABLE IF EXISTS `sys_icon`;
CREATE TABLE `sys_icon` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`identity`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`css_class`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`img_src`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`width`  smallint(6) NULL DEFAULT NULL ,
`height`  smallint(6) NULL DEFAULT NULL ,
`left`  smallint(6) NULL DEFAULT NULL ,
`top`  smallint(6) NULL DEFAULT NULL ,
`type`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`user_id`  int(11) NULL DEFAULT NULL ,
`description`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`),
UNIQUE INDEX `unique_sys_icon_identity` (`identity`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=50

;

-- ----------------------------
-- Records of sys_icon
-- ----------------------------
BEGIN;
INSERT INTO `sys_icon` VALUES ('1', 'ico-zxja', null, 'static/common/icon/teachingplan.png', '70', '69', null, null, 'image', '0', '撰写教案'), ('2', 'ico-wdbkb', null, 'static/common/icon/mybook.png', '70', '69', null, null, 'image', '0', '我的教案'), ('3', 'ico-sckj', null, 'static/common/icon/upload.png', '70', '69', null, null, 'image', '0', '上传课件'), ('4', 'ico-jxfs', null, 'static/common/icon/reflection.png', '70', '69', null, null, 'image', '0', '教学反思'), ('5', 'ico-tbzy', null, 'static/common/icon/resources.png', '70', '69', null, null, 'image', '0', '同伴资源'), ('6', 'ico-tbhz', null, 'static/common/icon/mutual.png', '70', '69', null, null, 'image', '0', '同伴互助'), ('7', 'ico-jtbk', null, 'static/common/icon/preparation.png', '70', '69', null, null, 'image', '0', '集体备课'), ('8', 'ico-jxlw', null, 'static/common/icon/schoo.png', '70', '69', null, null, 'image', '0', '教学论文'), ('9', 'ico-tkjl', null, 'static/common/icon/affair.png', '70', '69', null, null, 'image', '0', '听课记录'), ('10', 'ico-bkgj', null, 'static/common/icon/preparation.png', '70', '69', null, null, 'image', '0', '备课工具'), ('11', 'ico-100', null, 'static/common/icon/sub/1.png', '22', '23', null, null, 'image', '0', '语文'), ('12', 'ico-103', null, 'static/common/icon/sub/2.png', '22', '23', null, null, 'image', '0', '数学'), ('13', 'ico-106', null, null, '22', '23', null, null, 'image', '0', '物理'), ('14', 'ico-104', null, 'static/common/icon/sub/3.png', '22', '23', null, null, 'image', '0', '英语'), ('15', 'ico-107', null, 'static/common/icon/sub/4.png', '22', '23', null, null, 'image', '0', '化学'), ('16', 'ico-165', null, null, '22', '23', null, null, 'image', '0', '生物'), ('17', 'ico-265', null, null, '22', '23', null, null, 'image', '0', '历史'), ('18', 'ico-105', null, 'static/common/icon/sub/5.png', '22', '23', null, null, 'image', '0', '思想政治'), ('19', 'ico-166', null, null, '22', '23', null, null, 'image', '0', '地理'), ('20', 'ico-170', null, 'static/common/icon/sub/9.png', '22', '23', null, null, 'image', '0', '信息技术'), ('21', 'ico-171', null, 'static/common/icon/sub/4.png', '22', '23', null, null, 'image', '0', '科学'), ('22', 'ico-172', null, 'static/common/icon/sub/6.png', '22', '23', null, null, 'image', '0', '音乐'), ('23', 'ico-167', null, 'static/common/icon/sub/5.png', '22', '23', null, null, 'image', '0', '思想品德'), ('24', 'ico-173', null, 'static/common/icon/sub/7.png', '22', '23', null, null, 'image', '0', '美术'), ('25', 'ico-174', null, 'static/common/icon/sub/8.png', '22', '23', null, null, 'image', '0', '体育与健康'), ('26', 'ico-cyja', '', 'static/common/icon/look_lesson.png', '70', '69', null, null, 'image', '0', '查阅教案'), ('27', 'ico-cykj', '', 'static/common/icon/look_course.png', '70', '69', null, null, 'image', '0', '查阅课件'), ('28', 'ico-cyfs', '', 'static/common/icon/look_Reflect.png', '70', '69', null, null, 'image', '0', '查阅反思'), ('29', 'ico-fqjb', '', 'static/common/icon/Launch_Collective.png', '70', '69', null, null, 'image', '0', '发起集背'), ('30', 'ico-cyjb', '', 'static/common/icon/look_Collective.png', '70', '69', null, null, 'image', '0', '查阅集背'), ('31', 'ico-cytkjl', '', 'static/common/icon/Check_lecture_records.png', '70', '69', null, null, 'image', '0', '查阅听课记录'), ('32', 'ico-cygljl', '', 'static/common/icon/inspection_records.png', '70', '69', null, null, 'image', '0', '查阅管理记录'), ('33', 'ico-jyyl', '', 'static/common/icon/teching.png', '70', '69', null, null, 'image', '0', '教研一览'), ('34', 'ico-jhzj', '', 'static/common/icon/Plan_summary.png', '70', '69', null, null, 'image', '0', '计划总结'), ('36', 'ico-czda', null, 'static/common/icon/Growth_portfolio.png', '70', '69', null, null, 'image', '0', '成长档案袋'), ('37', 'ico-tzgg', null, 'static/common/icon/notice.png', '70', '69', null, null, 'image', '0', '通知公告'), ('38', 'ico-xjjy', null, 'static/common/icon/interscholastic_teaching.png', '70', '69', null, null, 'image', '0', '校际教研'), ('39', 'ico-qyjy', null, 'static/common/icon/regional_research.png', '70', '69', null, null, 'image', '0', '区域教研'), ('40', 'ico-qyyl', null, 'static/common/icon/regional_overview.png', '70', '69', null, null, 'image', '0', '区域一览'), ('41', 'ico-gljl', null, 'static/common/icon/record.png', '70', '69', null, null, 'image', '0', '管理记录'), ('42', 'ico-tphz', null, 'static/common/icon/tphz.png', '70', '69', null, null, 'image', '0', '同屏互助'), ('43', 'ico-kpi', null, 'static/common/icon/kpi.png', '70', '69', null, null, 'image', '0', '绩效考核'), ('44', 'ico-evl', '', 'static/common/icon/evl.png', '70', '69', null, null, 'image', '0', '评教系统'), ('45', 'ico-zjzd', '', 'static/common/icon/expert_guidance.png', '70', '69', null, null, 'image', '0', '专家指导'), ('46', 'ico-ktpj', '', 'static/common/icon/ktpj.png', '70', '69', null, null, 'image', '0', '课堂评价'), ('47', 'ico_summary', '', 'static/common/icon/area_summary.png', '70', '69', null, null, 'image', '0', '区域一览'), ('48', 'regional_research', '', 'static/common/icon/research_topic.png', '70', '69', null, null, 'image', '0', '课题研究'), ('49', 'ico-zxgk', '', 'static/common/icon/online_courses.png', '70', '69', null, null, 'image', '0', '在线观课');
COMMIT;

-- ----------------------------
-- Auto increment value for sys_icon
-- ----------------------------
ALTER TABLE `sys_icon` AUTO_INCREMENT=50;
