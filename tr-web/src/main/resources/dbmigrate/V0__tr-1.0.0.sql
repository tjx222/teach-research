DROP TABLE IF EXISTS `annunciate_punish_view`;
CREATE TABLE `annunciate_punish_view` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(8) DEFAULT NULL COMMENT '用户id',
  `annunciate_id` int(8) DEFAULT NULL COMMENT '通告id',
  `view_time` datetime DEFAULT NULL COMMENT '查看日期',
  `crt_id` int(11) DEFAULT NULL COMMENT '创建人',
  `crt_dttm` datetime DEFAULT NULL COMMENT '创建时间',
  `lastup_id` int(11) DEFAULT NULL COMMENT '最后更新用户',
  `lastup_dttm` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `annunciateId_userId_index` (`annunciate_id`,`user_id`) USING BTREE,
  KEY `user_id_index` (`user_id`) USING BTREE,
  KEY `annunciate_id_index` (`annunciate_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=843 DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS `bk_operate_log`;
CREATE TABLE `bk_operate_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createtime` datetime DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `type` varchar(16) DEFAULT NULL COMMENT '操作类型,枚举<新增，修改，删除，查找>',
  `message` varchar(512) DEFAULT NULL COMMENT '操作详情描述',
  `module` varchar(16) DEFAULT NULL COMMENT '所属模块名称',
  `ip` varchar(32) DEFAULT NULL COMMENT '操作时ip',
  `caller_class` varchar(128) DEFAULT NULL,
  `thread_name` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11612 DEFAULT CHARSET=utf8 COMMENT='后台操作日志';
DROP TABLE IF EXISTS `check_info`;
CREATE TABLE `check_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(128) DEFAULT NULL COMMENT '资源标题',
  `grade_id` int(11) DEFAULT NULL COMMENT '资源所属年级',
  `subject_id` int(11) DEFAULT NULL COMMENT '资源所属学科',
  `res_id` int(11) DEFAULT NULL COMMENT '被查阅资源id',
  `res_type` tinyint(4) DEFAULT NULL COMMENT '资源类型',
  `author` varchar(32) DEFAULT NULL COMMENT '资源作者',
  `author_id` int(11) DEFAULT NULL COMMENT '资源作者id',
  `username` varchar(32) DEFAULT NULL COMMENT '查阅者',
  `user_id` int(11) DEFAULT NULL COMMENT '查阅者id',
  `is_update` tinyint(1) DEFAULT NULL COMMENT '是否有更新,0 无更新  1 有更新',
  `createtime` datetime DEFAULT NULL,
  `school_year` int(11) DEFAULT NULL COMMENT '所属学年',
  `space_id` int(11) DEFAULT NULL COMMENT '查阅者用户空间id',
  `phase` int(11) DEFAULT NULL COMMENT '学段',
  `term` tinyint(2) DEFAULT NULL,
  `has_optinion` tinyint(1) DEFAULT NULL,
  `level` int(11) DEFAULT '2',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1564 DEFAULT CHARSET=utf8 COMMENT='查阅信息表。';


DROP TABLE IF EXISTS `check_opinion`;
CREATE TABLE `check_opinion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(2048) DEFAULT NULL COMMENT '意见内容',
  `author_id` int(11) DEFAULT NULL COMMENT '资源作者id',
  `check_info_id` int(11) DEFAULT NULL COMMENT '查阅信息id',
  `type` tinyint(4) DEFAULT NULL COMMENT '查阅意见类型： 0  普通意见， 1 回复',
  `parent_id` int(11) DEFAULT NULL COMMENT '回复父级id',
  `user_id` int(11) DEFAULT NULL COMMENT '查阅者id',
  `username` varchar(32) DEFAULT NULL COMMENT '查阅者',
  `is_delete` tinyint(1) DEFAULT NULL COMMENT '是否删除',
  `is_hidden` tinyint(1) DEFAULT NULL COMMENT '是否隐藏',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  `res_id` int(11) DEFAULT NULL COMMENT '资源id',
  `res_type` tinyint(4) DEFAULT NULL COMMENT '资源类型',
  `opinion_id` int(11) DEFAULT NULL COMMENT '原始意见id',
  `space_id` int(11) DEFAULT NULL COMMENT '用户空间id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2975 DEFAULT CHARSET=utf8 COMMENT='查阅意见表';

DROP TABLE IF EXISTS `comment_info`;
CREATE TABLE `comment_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(2048) DEFAULT NULL COMMENT '评论内容',
  `author_id` int(11) DEFAULT NULL COMMENT '作者id',
  `res_type` tinyint(4) DEFAULT NULL COMMENT '资源类型',
  `res_id` int(11) DEFAULT NULL COMMENT '被评论资源id',
  `type` tinyint(4) DEFAULT NULL COMMENT '评论类型： 0  普通评论 ， 1 回复',
  `parent_id` int(11) DEFAULT NULL COMMENT '回复父级id',
  `user_id` int(11) DEFAULT NULL COMMENT '当前评论者id',
  `username` varchar(32) DEFAULT NULL COMMENT '当前评论者',
  `is_delete` tinyint(1) DEFAULT NULL COMMENT '是否删除',
  `is_hidden` tinyint(1) DEFAULT NULL COMMENT '是否隐藏',
  `crt_dttm` datetime DEFAULT NULL COMMENT '创建时间',
  `opinion_id` int(11) DEFAULT NULL COMMENT '顶级评论id',
  `title` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3639 DEFAULT CHARSET=utf8 COMMENT='评论信息表';


DROP TABLE IF EXISTS `sys_book_sync`;
CREATE TABLE `sys_book_sync` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `com_id` varchar(32) NOT NULL,
  `com_name` varchar(128) NOT NULL COMMENT '商品名称',
  `phase` varchar(64) DEFAULT NULL COMMENT '学段',
  `phase_id` mediumint(9) DEFAULT NULL,
  `grade_level` varchar(64) DEFAULT NULL COMMENT '年级',
  `grade_level_id` mediumint(9) DEFAULT NULL,
  `subject` varchar(64) DEFAULT NULL COMMENT '学科',
  `subject_id` mediumint(9) DEFAULT NULL,
  `publisher` varchar(64) DEFAULT NULL COMMENT '出版社',
  `publisher_id` mediumint(9) DEFAULT NULL,
  `fascicule` varchar(64) DEFAULT NULL COMMENT '册别',
  `fascicule_id` mediumint(9) DEFAULT NULL,
  `book_edtion` varchar(32) DEFAULT NULL COMMENT '版次(元数据)',
  `book_edtion_id` mediumint(9) DEFAULT NULL,
  `book_in_time` datetime NOT NULL COMMENT '入库时间',
  `format_name` varchar(32) DEFAULT NULL,
  `relation_com_id` varchar(32) DEFAULT NULL,
  `com_order` smallint(6) DEFAULT NULL COMMENT '本多媒体书排序',
  `org_id` int(11) DEFAULT NULL,
  `area_id` int(11) DEFAULT NULL,
  `enable` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2402 DEFAULT CHARSET=utf8 COMMENT='书籍关联表';


DROP TABLE IF EXISTS `feedback_recieve`;
CREATE TABLE `feedback_recieve` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `org_id` int(11) DEFAULT NULL COMMENT '机构id',
  `user_id_sender` int(32) DEFAULT NULL COMMENT '反馈者id',
  `user_name_sender` varchar(32) DEFAULT NULL COMMENT '反馈者姓名',
  `message` varchar(500) DEFAULT NULL COMMENT '反馈内容',
  `attachment_1` varchar(256) DEFAULT NULL COMMENT '附件1',
  `attachment_2` varchar(256) DEFAULT NULL COMMENT '附件2',
  `attachment_3` varchar(256) DEFAULT NULL COMMENT '附件3',
  `attachment_1_name` varchar(256) DEFAULT NULL,
  `attachment_2_name` varchar(256) DEFAULT NULL,
  `attachment_3_name` varchar(256) DEFAULT NULL,
  `sender_time` timestamp NULL DEFAULT NULL COMMENT '反馈时间',
  `ishavareply` tinyint(1) DEFAULT '0' COMMENT '是否已回复',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `feedback_reply`;
CREATE TABLE `feedback_reply` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `org_id` int(11) DEFAULT NULL COMMENT '机构id',
  `user_id_receiver` int(32) DEFAULT NULL COMMENT '后台回复者id',
  `user_name_receiver` varchar(32) DEFAULT NULL COMMENT '后台回复者姓名',
  `content` varchar(500) DEFAULT NULL COMMENT '反馈内容',
  `attachment_1` varchar(256) DEFAULT NULL COMMENT '附件1',
  `attachment_2` varchar(256) DEFAULT NULL COMMENT '附件2',
  `attachment_3` varchar(256) DEFAULT NULL COMMENT '附件3',
  `attachment_1_name` varchar(256) DEFAULT NULL,
  `attachment_2_name` varchar(256) DEFAULT NULL,
  `attachment_3_name` varchar(256) DEFAULT NULL,
  `sender_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '回复时间',
  `pid` int(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `jy_activity`;
CREATE TABLE `jy_activity` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `type_id` tinyint(4) NOT NULL,
  `info_id` int(10) DEFAULT NULL COMMENT '课题信息id',
  `type_name` varchar(32) DEFAULT NULL,
  `activity_name` varchar(128) DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL COMMENT '活动状态，0：草稿，1：正式发文',
  `subject_ids` varchar(128) DEFAULT NULL,
  `subject_name` varchar(128) DEFAULT NULL,
  `grade_ids` varchar(128) DEFAULT NULL,
  `grade_name` varchar(128) DEFAULT NULL,
  `organize_user_id` int(10) DEFAULT NULL,
  `organize_user_name` varchar(32) DEFAULT NULL,
  `space_id` int(10) DEFAULT NULL COMMENT '用户空间id',
  `term` tinyint(1) DEFAULT NULL COMMENT '学期',
  `phase_id` int(10) DEFAULT NULL COMMENT '学段id',
  `school_year` int(4) DEFAULT NULL COMMENT '学年',
  `start_time` datetime DEFAULT NULL,
  `main_user_id` int(10) DEFAULT NULL,
  `main_user_name` varchar(32) DEFAULT NULL,
  `org_id` int(10) DEFAULT NULL COMMENT '机构id',
  `end_time` datetime DEFAULT NULL,
  `comments_num` int(10) DEFAULT '0',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `release_time` datetime DEFAULT NULL COMMENT '发布时间',
  `remark` varchar(1000) DEFAULT NULL,
  `is_over` tinyint(1) DEFAULT NULL COMMENT '是否结束 0：未  1：已',
  `is_submit` tinyint(1) DEFAULT NULL COMMENT '是否提交   0：未， 1：已',
  `audit_up` tinyint(1) DEFAULT NULL,
  `is_audit` tinyint(1) DEFAULT NULL COMMENT '是否查阅  0：未，1：已',
  `main_user_grade_id` int(10) DEFAULT NULL,
  `main_user_subject_id` int(10) DEFAULT NULL,
  `is_share` tinyint(1) DEFAULT '0' COMMENT '是否分享  0：未分享  1：已分享',
  `is_comment` tinyint(1) DEFAULT '0' COMMENT '是否评论  0：未评论  1：已评论 ',
  `is_send` tinyint(1) DEFAULT NULL COMMENT '整理教案是否发送  0：未发送，1：已发送',
  `share_time` datetime DEFAULT NULL COMMENT '分享时间',
  `url` varchar(256) DEFAULT '',
  `organize_subject_id` int(5) DEFAULT '0' COMMENT '发起者的学科id',
  `organize_grade_id` int(5) DEFAULT '0' COMMENT '发起者的年级id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1193 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `jy_activity_attach`;
CREATE TABLE `jy_activity_attach` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `activity_id` int(10) DEFAULT NULL COMMENT '集备id',
  `activity_type` tinyint(1) DEFAULT NULL COMMENT '活动类型，0：集体备课，1：区域教研，2：校际教研',
  `res_id` varchar(32) DEFAULT NULL COMMENT '资源存储id',
  `attach_name` varchar(256) DEFAULT NULL COMMENT '附件名称',
  `ext` varchar(10) DEFAULT NULL COMMENT '文件扩展名',
  `crt_id` int(10) DEFAULT NULL COMMENT '上传人id',
  `crt_dttm` datetime DEFAULT NULL COMMENT '上传时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=902 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `jy_activity_comment`;
CREATE TABLE `jy_activity_comment` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `activity_id` int(10) DEFAULT NULL,
  `sender_id` int(10) DEFAULT NULL,
  `sender_name` varchar(32) DEFAULT NULL,
  `sender_head_url` varchar(64) DEFAULT NULL,
  `content` varchar(500) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `reply_user_id` int(10) DEFAULT NULL,
  `reply_content` varchar(500) DEFAULT NULL,
  `reply_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `jy_activity_draft`;
CREATE TABLE `jy_activity_draft` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `type_id` tinyint(4) NOT NULL,
  `info_id` int(10) DEFAULT NULL COMMENT '课题信息id',
  `type_name` varchar(32) DEFAULT NULL,
  `activity_name` varchar(128) DEFAULT NULL,
  `subject_ids` varchar(64) DEFAULT NULL COMMENT '示例：\r\n            _11_\r\n            _11_12_',
  `subject_name` varchar(64) DEFAULT NULL COMMENT '示例：\r\n            语文、数学',
  `grade_ids` varchar(64) DEFAULT NULL COMMENT '示例：\r\n            _11_\r\n            _11_12_',
  `grade_name` varchar(64) DEFAULT NULL COMMENT '示例：\r\n            一、二',
  `organize_user_id` int(10) DEFAULT NULL,
  `organize_user_name` varchar(32) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `main_user_id` int(10) DEFAULT NULL,
  `main_user_name` varchar(32) DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `comments_num` int(10) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `release_time` datetime DEFAULT NULL,
  `remark` varchar(1000) DEFAULT NULL,
  `main_user_grade_id` int(10) DEFAULT NULL,
  `main_user_subject_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `jy_activity_res`;
CREATE TABLE `jy_activity_res` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `activity_id` int(10) DEFAULT NULL,
  `file_id` varchar(32) DEFAULT NULL,
  `is_activity` tinyint(3) unsigned DEFAULT '0' COMMENT '0.否   参与者提交的资源\r\n            1 是  活动资源， 每课时只一份。',
  `title` varchar(64) DEFAULT NULL COMMENT '资源显示标题',
  `res_type` tinyint(4) DEFAULT '0' COMMENT '1  教案\r\n            2  课件\r\n            3  习题 4  素材',
  `res_second_type` tinyint(4) DEFAULT '0' COMMENT '素材下的子分类 ： 1文档  2 图片  3 音频  4 视频',
  `sort` int(10) DEFAULT '0',
  `format_type` smallint(6) DEFAULT NULL COMMENT '格式id   元素据id\r\n            word excel  ppt  txt pdf  flash  mp3  avi  jpg  其他  ',
  `format_name` varchar(12) DEFAULT NULL,
  `class_code` varchar(64) DEFAULT NULL COMMENT '全课时：  _0_\r\n            第一课时 ： _1_\r\n            第一第二课时： _1_2_',
  `book_id` varchar(32) DEFAULT '0' COMMENT '书籍ID',
  `chapter_id` varchar(32) DEFAULT NULL COMMENT '章节ID',
  `publicsher_id` smallint(6) DEFAULT '0' COMMENT '出版社ID -  版本',
  `grade_level_id` smallint(6) DEFAULT '0' COMMENT '年级ID',
  `subject_id` smallint(6) DEFAULT '0' COMMENT '科目ID',
  `phase_id` smallint(6) DEFAULT '0' COMMENT '学段ID',
  `fascicule_id` smallint(6) DEFAULT '0' COMMENT '册别ID',
  `upload_user_id` int(10) unsigned DEFAULT '0' COMMENT '创建者ID',
  `upload_user_name` varchar(20) DEFAULT NULL COMMENT '创建者名称',
  `upload_time` datetime DEFAULT NULL COMMENT '创建时间',
  `size` int(10) DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL COMMENT '摘要',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `jy_activity_tracks`;
CREATE TABLE `jy_activity_tracks` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `activity_id` int(10) DEFAULT NULL COMMENT '集体备课id',
  `plan_id` int(10) DEFAULT NULL COMMENT '教案id',
  `plan_name` varchar(100) DEFAULT NULL COMMENT '教案名称',
  `edit_type` tinyint(2) DEFAULT NULL COMMENT '修改类型  0：教案意见，1：教案整理',
  `lesson_id` varchar(32) DEFAULT NULL COMMENT '课题id',
  `res_id` varchar(32) DEFAULT NULL COMMENT '资源id',
  `hours_id` varchar(10) DEFAULT NULL COMMENT '课时id',
  `order_value` tinyint(2) DEFAULT NULL COMMENT '课时排序值',
  `user_id` int(10) DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(20) DEFAULT NULL COMMENT '用户真实姓名',
  `subject_id` int(10) DEFAULT NULL COMMENT '科目id',
  `school_year` smallint(4) DEFAULT NULL COMMENT '学年',
  `crt_id` int(10) DEFAULT NULL COMMENT '创建人id',
  `crt_dttm` datetime DEFAULT NULL COMMENT '创建时间',
  `lastup_dttm` datetime DEFAULT NULL COMMENT '更新时间',
  `org_id` int(11) DEFAULT NULL COMMENT '����ID',
  `grade_id` int(10) DEFAULT NULL COMMENT '�꼶ID',
  `space_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=797 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `jy_activity_type`;
CREATE TABLE `jy_activity_type` (
  `id` tinyint(4) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `jy_annunciate`;
CREATE TABLE `jy_annunciate` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `annunciate_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '通知类型：0 学校通知 1 区域通知',
  `org_id` int(11) NOT NULL COMMENT '组织id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `orgs_join_ids` varchar(200) DEFAULT NULL,
  `orgs_join_count` tinyint(4) DEFAULT NULL COMMENT '参与学校的数量',
  `space_id` int(11) NOT NULL COMMENT '工作空间id',
  `red_title_id` int(11) NOT NULL COMMENT '红头文件标题id',
  `from_where` varchar(20) DEFAULT NULL,
  `title` varchar(200) NOT NULL COMMENT '通告标题',
  `type` varchar(2) NOT NULL COMMENT '通告类型：0、普通文件，1、红头文件',
  `content` text NOT NULL COMMENT '通告内容',
  `attachs` varchar(500) DEFAULT NULL COMMENT '附件列表，用#分割',
  `status` int(2) NOT NULL COMMENT '通告状态：0、草稿箱，1、已发布',
  `annunciate_range` int(2) NOT NULL DEFAULT '0' COMMENT '通告范围：0、学校，1、区域',
  `is_enable` int(2) NOT NULL COMMENT '是否可用：1、可用，0、不可用',
  `is_delete` int(2) NOT NULL COMMENT '是否已删除：1、已删除，0未删除',
  `is_display` int(2) DEFAULT NULL COMMENT '是否在学校首页显示：0 不显示，1 显示',
  `is_forward` int(2) NOT NULL DEFAULT '1' COMMENT '是否已转发：1、已转发，0未转发',
  `forward_description` varchar(500) DEFAULT '' COMMENT '转发说明',
  `crt_id` int(11) DEFAULT NULL COMMENT '创建人',
  `crt_dttm` datetime DEFAULT NULL COMMENT '创建时间',
  `lastup_id` int(11) DEFAULT NULL COMMENT '最后更新用户',
  `lastup_dttm` datetime DEFAULT NULL COMMENT '最后更新时间',
  `is_top` tinyint(1) DEFAULT '0' COMMENT '是否置顶：1、置顶，0未置顶',
  PRIMARY KEY (`id`),
  KEY `org_id_index` (`org_id`) USING BTREE,
  KEY `user_id_index` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=641 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `jy_browsing_count`;
CREATE TABLE `jy_browsing_count` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` tinyint(4) DEFAULT NULL COMMENT '资源类型',
  `child_type` tinyint(4) DEFAULT NULL COMMENT '子类型',
  `org_id` int(11) DEFAULT NULL COMMENT '资源所属的机构ID',
  `res_id` int(11) DEFAULT NULL COMMENT '资源ID',
  `res_share` tinyint(1) DEFAULT NULL COMMENT '资源是否分享',
  `count` int(5) DEFAULT NULL COMMENT '浏览的次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=424 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='分享资源浏览记录';


DROP TABLE IF EXISTS `jy_browsing_record`;
CREATE TABLE `jy_browsing_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` tinyint(4) DEFAULT NULL COMMENT '资源类型',
  `child_type` tinyint(4) DEFAULT NULL COMMENT '子类型',
  `org_id` int(11) DEFAULT NULL COMMENT '资源所属的机构ID',
  `res_id` int(11) DEFAULT NULL COMMENT '资源ID',
  `user_id` int(11) DEFAULT NULL COMMENT '浏览人ID',
  `res_share` tinyint(1) DEFAULT NULL COMMENT '资源是否分享',
  `count` int(5) DEFAULT NULL COMMENT '浏览的次数',
  `last_time` datetime DEFAULT NULL COMMENT '最后浏览时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=826 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='分享资源浏览记录';


DROP TABLE IF EXISTS `jy_class`;
CREATE TABLE `jy_class` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL COMMENT '名称',
  `grade_id` int(11) DEFAULT NULL COMMENT '年级id',
  `org_id` int(11) DEFAULT NULL COMMENT '学校id',
  `code` varchar(32) DEFAULT NULL COMMENT '班级编号',
  `sort` smallint(6) DEFAULT NULL COMMENT '排序',
  `enable` tinyint(4) DEFAULT NULL,
  `crt_dttm` datetime DEFAULT NULL,
  `crt_id` int(11) DEFAULT NULL,
  `lastup_id` int(11) DEFAULT NULL,
  `lastup_dttm` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=utf8 COMMENT='学校班级';


DROP TABLE IF EXISTS `jy_class_user`;
CREATE TABLE `jy_class_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class_id` int(11) DEFAULT NULL COMMENT '班级id',
  `subject_id` int(11) DEFAULT NULL COMMENT '科目id',
  `username` varchar(32) DEFAULT NULL COMMENT '用户姓名',
  `tch_id` int(11) DEFAULT NULL COMMENT '用户id',
  `school_year` int(11) DEFAULT NULL COMMENT '学年',
  `type` tinyint(4) DEFAULT NULL COMMENT '0  普通教师， 1 班主任，2 学生',
  `crt_dttm` datetime DEFAULT NULL,
  `crt_id` int(11) DEFAULT NULL,
  `lastup_id` int(11) DEFAULT NULL,
  `lastup_dttm` datetime DEFAULT NULL,
  `enable` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=191 DEFAULT CHARSET=utf8 COMMENT='班级教师用户';


DROP TABLE IF EXISTS `jy_companion`;
CREATE TABLE `jy_companion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(32) DEFAULT NULL COMMENT '用户名称',
  `user_nickname` varchar(32) DEFAULT NULL COMMENT '用户昵称',
  `user_id_companion` int(11) DEFAULT NULL COMMENT '同伴用户id',
  `user_name_companion` varchar(32) DEFAULT NULL COMMENT '同伴名称',
  `user_nickname_companion` varchar(32) DEFAULT NULL COMMENT '同伴昵称',
  `is_same_org` int(2) DEFAULT NULL COMMENT '是否时统一学校，1、是；0、否',
  `is_friend` int(2) DEFAULT NULL COMMENT '是否是朋友，1、是；0、否',
  `last_communicate_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2180 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `jy_companion_message`;
CREATE TABLE `jy_companion_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id_sender` int(11) DEFAULT NULL COMMENT '发送者id',
  `user_name_sender` varchar(32) DEFAULT NULL COMMENT '发送者姓名',
  `user_id_receiver` int(11) DEFAULT NULL COMMENT '接受者id',
  `user_name_receiver` varchar(32) DEFAULT NULL COMMENT '接受者姓名',
  `message` varchar(1024) DEFAULT NULL,
  `attachment_1` varchar(32) DEFAULT NULL COMMENT '附件1',
  `attachment_2` varchar(32) DEFAULT NULL COMMENT '附件2',
  `attachment_3` varchar(32) DEFAULT NULL COMMENT '附件3',
  `attachment_1_name` varchar(256) DEFAULT NULL,
  `attachment_2_name` varchar(256) DEFAULT NULL,
  `attachment_3_name` varchar(256) DEFAULT NULL,
  `sender_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '留言发送时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1272 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `jy_discuss`;
CREATE TABLE `jy_discuss` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '表ID',
  `type_id` int(11) DEFAULT NULL,
  `activity_id` int(10) DEFAULT NULL COMMENT '关联活动的ID',
  `crt_id` int(10) DEFAULT NULL COMMENT '讨论人ID',
  `space_id` int(11) DEFAULT NULL,
  `content` varchar(512) DEFAULT NULL,
  `crt_dttm` datetime DEFAULT NULL COMMENT '讨论发表的时间',
  `discuss_level` tinyint(1) DEFAULT NULL COMMENT '讨论层级 1：一层（直接对应活动的讨论） 2：二层（对一层的讨论进行的评论）',
  `parent_id` int(10) DEFAULT '0' COMMENT '讨论的父层ID',
  `is_voice` tinyint(1) DEFAULT '0' COMMENT '是否为语音信息：0-不是   1-是',
  `voice_length` double(6,2) DEFAULT '0.00' COMMENT '语音时长，长度单位 秒（s）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1858 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `jy_flatform_announcement`;
CREATE TABLE `jy_flatform_announcement` (
  `id` int(32) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `userId` int(32) DEFAULT NULL COMMENT '发布者id',
  `userName` varchar(32) DEFAULT NULL COMMENT '发布者姓名',
  `pictureId` varchar(132) DEFAULT NULL COMMENT '大图片id',
  `pictureName` varchar(32) DEFAULT NULL COMMENT '大图片名称',
  `littlepictureId` varchar(32) DEFAULT NULL COMMENT '小图片id',
  `littlepictureName` varchar(32) DEFAULT NULL COMMENT '小图片名字',
  `cdate` datetime DEFAULT NULL COMMENT '发布时间',
  `isview` tinyint(1) DEFAULT '0' COMMENT '是否显示 0:不显示 1:显示',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `jy_flatform_picturenews`;
CREATE TABLE `jy_flatform_picturenews` (
  `id` int(32) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `org_id` int(11) DEFAULT NULL COMMENT '机构id',
  `title` varchar(200) DEFAULT NULL COMMENT '新闻标题',
  `content` text COMMENT '新闻内容',
  `attachs` varchar(500) DEFAULT NULL,
  `istop` int(1) DEFAULT '0' COMMENT '是否置顶 0：非置顶 1：置顶',
  `isdelete` int(1) DEFAULT '0' COMMENT '是否删除 0：未删除 1：已删除',
  `status` int(1) DEFAULT NULL COMMENT '是否是草稿0：草稿 1：已发布',
  `is_display` int(1) DEFAULT NULL COMMENT '是否展示 0：不展示 1：展示',
  `crt_id` int(32) DEFAULT NULL COMMENT '创建人id',
  `crtname` varchar(32) DEFAULT NULL COMMENT '创建人姓名',
  `crt_dttm` datetime DEFAULT NULL COMMENT '创建时间',
  `lastup_id` int(32) DEFAULT NULL COMMENT '最后修改人id',
  `lastup_dttm` datetime DEFAULT NULL COMMENT '最后修改时间',
  `parentid` int(10) DEFAULT NULL COMMENT '��id',
  `sort` int(3) DEFAULT NULL COMMENT '��ʾ˳��',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=144 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `jy_friend`;
CREATE TABLE `jy_friend` (
  `id` int(11) NOT NULL DEFAULT '0',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(32) DEFAULT NULL COMMENT '用户名称',
  `user_nickname` varchar(32) DEFAULT NULL COMMENT '用户昵称',
  `user_id_friend` int(11) DEFAULT NULL COMMENT '好友用户id',
  `user_name_friend` varchar(32) DEFAULT NULL COMMENT '好友名称',
  `user_nickname_friend` varchar(32) DEFAULT NULL COMMENT '好友昵称',
  `last_communicate_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近一次联系时间',
  `is_same_org` int(2) DEFAULT NULL COMMENT '是否时统一学校，1、是；0、否',
  `is_deleted` int(2) DEFAULT NULL COMMENT '是否已删除',
  `crt_id` int(11) DEFAULT NULL COMMENT '创建人',
  `crt_dttm` datetime DEFAULT NULL COMMENT '创建时间',
  `lastup_id` int(11) DEFAULT NULL COMMENT '最后更新用户',
  `lastup_dttm` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `jy_friend_message`;
CREATE TABLE `jy_friend_message` (
  `id` int(11) NOT NULL DEFAULT '0',
  `user_id_sender` int(11) DEFAULT NULL COMMENT '发送者id',
  `user_name_sender` varchar(32) DEFAULT NULL COMMENT '发送者姓名',
  `user_id_receiver` int(11) DEFAULT NULL COMMENT '接受者id',
  `user_name_receiver` varchar(32) DEFAULT NULL COMMENT '接受者姓名',
  `message` varchar(500) DEFAULT NULL COMMENT '留言内容',
  `attachment_1` varchar(32) DEFAULT NULL COMMENT '附件1',
  `attachment_2` varchar(32) DEFAULT NULL COMMENT '附件2',
  `attachment_3` varchar(32) DEFAULT NULL COMMENT '附件3',
  `sender_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '留言发送时间',
  `crt_id` int(11) DEFAULT NULL COMMENT '创建人',
  `crt_dttm` datetime DEFAULT NULL COMMENT '创建时间',
  `lastup_id` int(11) DEFAULT NULL COMMENT '最后更新用户',
  `lastup_dttm` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `jy_letter`;
CREATE TABLE `jy_letter` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sender_id` int(11) DEFAULT NULL COMMENT '通知发送者id',
  `receiver_id` int(11) DEFAULT NULL COMMENT '通知接受者id',
  `send_date` datetime NOT NULL COMMENT '发送时间',
  `content` text COMMENT '内容',
  `sender_state` tinyint(4) DEFAULT '0' COMMENT '发送者状态,1：已删除，0：未删除',
  `sender_state_change_date` datetime NOT NULL COMMENT '发送者更改状态时间',
  `receiver_state` tinyint(4) DEFAULT '0' COMMENT '接受者状态，2：已阅读，1：已删除，0：未阅读',
  `receiver_state_change_date` datetime NOT NULL COMMENT '接受通知者更改状态时间',
  `parent_id` bigint(20) DEFAULT NULL,
  `parent_ids` varchar(200) DEFAULT NULL,
  `crt_id` int(11) DEFAULT NULL COMMENT '创建人',
  `crt_dttm` datetime DEFAULT NULL COMMENT '创建时间',
  `lastup_id` int(11) DEFAULT NULL COMMENT '最后更新用户',
  `lastup_dttm` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  KEY `serder_id_index` (`sender_id`) USING BTREE,
  KEY `receiver_id_index` (`receiver_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `jy_notice`;
CREATE TABLE `jy_notice` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sender_id` int(11) NOT NULL COMMENT '通知发送者id',
  `receiver_id` int(11) NOT NULL COMMENT '通知接受者id',
  `send_date` datetime NOT NULL COMMENT '发送时间',
  `title` varchar(200) NOT NULL COMMENT '标题',
  `detail` text NOT NULL COMMENT '通知详情',
  `sender_state` tinyint(4) NOT NULL DEFAULT '0' COMMENT '发送者状态,1：已删除，0：未删除',
  `sender_state_change_date` datetime NOT NULL COMMENT '发送者更改状态时间',
  `receiver_state` tinyint(4) NOT NULL DEFAULT '0' COMMENT '接受者状态，2：已阅读，1：已删除，0：未阅读',
  `receiver_state_change_date` datetime NOT NULL COMMENT '接受通知者更改状态时间',
  `business_type` int(2) NOT NULL COMMENT '业务类型',
  `detail_type` int(2) NOT NULL COMMENT '详情类型：0、弹出框；1、新页面打开；2、url跳转',
  `parent_id` bigint(20) DEFAULT NULL,
  `parent_ids` varchar(200) DEFAULT NULL,
  `crt_id` int(11) DEFAULT NULL COMMENT '创建人',
  `crt_dttm` datetime DEFAULT NULL COMMENT '创建时间',
  `lastup_id` int(11) DEFAULT NULL COMMENT '最后更新用户',
  `lastup_dttm` datetime DEFAULT NULL COMMENT '最后更新时间',
  `sender_user_name` varchar(32) DEFAULT NULL,
  `receiver_user_name` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `serder_id_index` (`sender_id`) USING BTREE,
  KEY `receiver_id_index` (`receiver_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12973 DEFAULT CHARSET=utf8 COMMENT='消息表';


DROP TABLE IF EXISTS `jy_notice_total`;
CREATE TABLE `jy_notice_total` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `type` int(2) NOT NULL COMMENT '类型',
  `unread_num` int(11) NOT NULL COMMENT '未读取数目',
  `crt_id` int(11) DEFAULT NULL COMMENT '创建人',
  `crt_dttm` datetime DEFAULT NULL COMMENT '创建时间',
  `lastup_id` int(11) DEFAULT NULL COMMENT '最后更新用户',
  `lastup_dttm` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  KEY `user_id_index` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `jy_plain_punish_view`;
CREATE TABLE `jy_plain_punish_view` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(8) DEFAULT NULL COMMENT '用户id',
  `plain_summary_id` int(8) DEFAULT NULL COMMENT '计划总结id',
  `view_time` datetime DEFAULT NULL COMMENT '查看日期',
  PRIMARY KEY (`id`),
  UNIQUE KEY `psId_userId_index` (`plain_summary_id`,`user_id`) USING BTREE,
  KEY `user_id_index` (`user_id`) USING BTREE,
  KEY `plain_summary_id_index` (`plain_summary_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1040 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `jy_plain_summary`;
CREATE TABLE `jy_plain_summary` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `title` varchar(31) NOT NULL COMMENT '标题',
  `category` tinyint(2) NOT NULL COMMENT '类别,1:个人计划；2：个人总结；3、当前身份计划；4、当前身份总结',
  `content_file_key` varchar(32) NOT NULL COMMENT '内容key',
  `content_file_name` varchar(100) NOT NULL COMMENT '内容文件的名称',
  `content_file_type` varchar(10) NOT NULL COMMENT '内容文件的类型',
  `is_submit` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否已提交，1：已提交，0未提交',
  `is_share` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否已分享，1：已分享，0未分享',
  `is_punish` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否已发布，1：已发布，0未发布',
  `is_check` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否已查阅，1：已查阅，0未查阅',
  `is_review` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否已评论，1：已评论，0未评论',
  `crt_id` int(11) DEFAULT NULL COMMENT '创建人',
  `crt_dttm` datetime DEFAULT NULL COMMENT '创建时间',
  `lastup_id` int(11) DEFAULT NULL COMMENT '最后更新用户',
  `lastup_dttm` datetime DEFAULT NULL COMMENT '最后更新时间',
  `subject_id` int(11) DEFAULT NULL,
  `grade_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  `term` int(2) DEFAULT NULL COMMENT '学期：0、上学期；1、下学期',
  `org_id` int(11) DEFAULT NULL COMMENT '组织机构id',
  `school_year` int(2) DEFAULT NULL COMMENT '学年',
  `phase_id` int(11) DEFAULT NULL COMMENT '学段id',
  `review_num` int(11) DEFAULT '0',
  `submit_time` datetime DEFAULT NULL,
  `punish_time` datetime DEFAULT NULL,
  `punish_range` int(2) DEFAULT NULL,
  `school_circle_id` int(8) DEFAULT NULL,
  `share_time` datetime DEFAULT NULL COMMENT '����ʱ��',
  `user_role_id` int(11) NOT NULL,
  `label` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1403 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `jy_plain_summary_check`;
CREATE TABLE `jy_plain_summary_check` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `check_space_id` int(11) NOT NULL COMMENT '审阅人用户空间',
  `checked_space_id` int(11) NOT NULL COMMENT '被审阅人用户空间',
  `plain_summary_id` int(11) NOT NULL COMMENT '被审阅计划总结id',
  `category` int(2) NOT NULL COMMENT '计划总结类型',
  `crt_id` int(11) DEFAULT NULL COMMENT '创建人',
  `crt_dttm` datetime DEFAULT NULL COMMENT '创建时间',
  `lastup_id` int(11) DEFAULT NULL COMMENT '最后更新用户',
  `lastup_dttm` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  KEY `check_space_id_index` (`check_space_id`) USING BTREE,
  KEY `checked_space_id_index` (`checked_space_id`) USING BTREE,
  KEY `plain_summary_id_index` (`plain_summary_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `jy_recommend_res`;
CREATE TABLE `jy_recommend_res` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `res_id` varchar(32) NOT NULL,
  `title` varchar(256) DEFAULT NULL COMMENT '资源显示标题',
  `dic_type` int(11) DEFAULT NULL,
  `res_type` int(11) DEFAULT '0' COMMENT '0教案\r\n  1课件 \r\n 2习题  3 素材',
  `res_second_type` int(11) DEFAULT '0' COMMENT '素材下的子分类 ：0文档  1图片  2 音频  3 视频',
  `ext` varchar(10) DEFAULT NULL COMMENT '格式id   元素据id\r\n            word excel  ppt  txt pdf  flash  mp3  avi  jpg  其他  ',
  `upload_user_id` int(10) unsigned DEFAULT '0' COMMENT '创建者ID',
  `upload_user_name` varchar(20) DEFAULT NULL COMMENT '创建者名称',
  `upload_time` datetime DEFAULT NULL COMMENT '创建时间',
  `book_id` varchar(32) DEFAULT '0' COMMENT '书籍ID',
  `lesson_id` varchar(32) DEFAULT NULL COMMENT '课题ID',
  `down_numb` smallint(6) DEFAULT '0' COMMENT '下载量',
  `upload_org_id` mediumint(8) unsigned DEFAULT '0',
  `upload_org_name` varchar(50) DEFAULT NULL,
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间  资源内容修改时间',
  `enable` tinyint(3) unsigned DEFAULT '0' COMMENT '0:失效，1：有效',
  `qualify` tinyint(4) DEFAULT NULL,
  `sort` int(5) DEFAULT NULL COMMENT '��ʾ˳��',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=114422 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `jy_red_title`;
CREATE TABLE `jy_red_title` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `org_id` int(11) NOT NULL COMMENT '组织id',
  `title` varchar(200) NOT NULL COMMENT '红头标题',
  `is_enable` int(2) NOT NULL COMMENT '是否可用：1、可用，0、不可用',
  `is_delete` int(2) NOT NULL COMMENT '是否已删除：1、已删除，0未删除',
  `crt_id` int(11) DEFAULT NULL COMMENT '创建人',
  `crt_dttm` datetime DEFAULT NULL COMMENT '创建时间',
  `lastup_id` int(11) DEFAULT NULL COMMENT '最后更新用户',
  `lastup_dttm` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  KEY `org_id_index` (`org_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=194 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `jy_school_banner`;
CREATE TABLE `jy_school_banner` (
  `id` int(32) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `orgid` int(32) DEFAULT NULL COMMENT '组织机构id',
  `schid` int(32) DEFAULT NULL COMMENT '学校id(暂未用)',
  `attachs` varchar(120) DEFAULT NULL COMMENT '附件id',
  `attachsname` varchar(32) DEFAULT NULL COMMENT '图片名称',
  `isview` int(1) DEFAULT '0' COMMENT '是否显示 0：不显示  1：显示',
  `crt_dttm` datetime DEFAULT NULL COMMENT '发布时间',
  `crt_id` int(32) DEFAULT NULL COMMENT '创建人id',
  `vieworder` int(32) DEFAULT NULL COMMENT '显示顺序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `jy_statistic_area`;
CREATE TABLE `jy_statistic_area` (
  `int` int(10) NOT NULL AUTO_INCREMENT,
  `org_id` int(10) DEFAULT NULL,
  `org_name` varchar(32) DEFAULT NULL,
  `org_type` tinyint(1) DEFAULT NULL COMMENT '0：学校 ， 1：区域机构',
  `area_id` int(10) DEFAULT NULL,
  `area_name` varchar(32) DEFAULT NULL,
  `phase_id` int(10) DEFAULT NULL,
  `subject_id` varchar(160) DEFAULT NULL,
  `jxyt_issue` int(10) DEFAULT '0' COMMENT '教研研讨发起数',
  `jxyt_join` int(10) DEFAULT '0' COMMENT '教学研讨参与数',
  `zxgk_issue` int(10) DEFAULT '0' COMMENT '在线观课发起数',
  `zxgk_join` int(10) DEFAULT '0' COMMENT '在线观课参与数',
  `zjzd_issue` int(10) DEFAULT '0' COMMENT '专家指导发起数',
  `zjzd_join` int(10) DEFAULT '0' COMMENT '专家指导参与数',
  `ktpj_issue` int(10) DEFAULT '0' COMMENT '课堂评价发起数',
  `ktpj_join` int(10) DEFAULT '0' COMMENT '课堂评价参与数',
  `xjjy_org_issue` int(10) DEFAULT NULL COMMENT '学校的校际教研发起数',
  `xjjy_org_join` int(10) DEFAULT NULL COMMENT '学校的校际教研参与数',
  `xjjy_area_issue` int(10) DEFAULT NULL COMMENT '区域的校际教研发起数',
  `xjjy_area_join` int(10) DEFAULT NULL COMMENT '区域的校际教研参与数',
  `create_date` date DEFAULT NULL COMMENT '生成日期',
  PRIMARY KEY (`int`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `jy_statistic_org`;
CREATE TABLE `jy_statistic_org` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `org_id` int(10) DEFAULT NULL,
  `org_name` varchar(32) DEFAULT NULL,
  `area_id` int(10) DEFAULT NULL,
  `area_name` varchar(32) DEFAULT NULL,
  `phase_id` int(10) DEFAULT NULL,
  `subject_id` int(10) DEFAULT NULL,
  `jiaoan_write` int(10) DEFAULT '0' COMMENT '教案撰写数',
  `jiaoan_scan` int(10) DEFAULT '0' COMMENT '教案查阅数',
  `kejian_write` int(10) DEFAULT '0' COMMENT '课件撰写数',
  `kejian_scan` int(10) DEFAULT '0' COMMENT '课件查阅数',
  `fansi_write` int(10) DEFAULT '0' COMMENT '反思撰写数',
  `fansi_scan` int(10) DEFAULT '0' COMMENT '反思查阅数',
  `listen_write` int(10) DEFAULT '0' COMMENT '听课记录撰写数',
  `listen_scan` int(10) DEFAULT '0' COMMENT '听课记录查阅数',
  `thesis_write` int(10) DEFAULT '0' COMMENT '教学文章撰写数',
  `thesis_scan` int(10) DEFAULT '0' COMMENT '教学文章查阅数',
  `plansummary_write` int(10) DEFAULT '0' COMMENT '计划总结撰写数',
  `plansummary_scan` int(10) DEFAULT '0' COMMENT '计划总结查阅数',
  `activity_issue` int(10) DEFAULT '0' COMMENT '集体备课发布数',
  `activity_scan` int(10) DEFAULT '0' COMMENT '集体备课查阅数',
  `activity_join` int(10) DEFAULT '0' COMMENT '集体备课参与数',
  `schoolactivity_circle` int(10) DEFAULT '0' COMMENT '校际教研圈数',
  `friend_message` int(10) DEFAULT '0' COMMENT '同伴互助留言数',
  `record_res` int(10) DEFAULT '0' COMMENT '成长档案袋精选资源数',
  `create_date` date DEFAULT NULL COMMENT '生成日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `lecture_records`;
CREATE TABLE `lecture_records` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID，自动自增',
  `res_type` int(11) DEFAULT NULL COMMENT '资源类型  6  ',
  `type` tinyint(1) DEFAULT NULL COMMENT '听课类型 0是校内听课 1是校外听课',
  `topic_id` int(11) DEFAULT NULL COMMENT '校内听课课题号,lesson_info里面的主键',
  `topic` varchar(50) DEFAULT NULL COMMENT '课题',
  `school_year` int(4) DEFAULT NULL COMMENT '当前学年',
  `term` tinyint(1) DEFAULT NULL COMMENT '当前学期',
  `org_id` int(11) DEFAULT NULL COMMENT '听课人所属机构',
  `grade_id` int(11) DEFAULT NULL COMMENT '年级ID',
  `grade` varchar(32) DEFAULT NULL COMMENT '年级',
  `subject_id` int(11) DEFAULT NULL COMMENT '学科ID',
  `subject` varchar(32) DEFAULT NULL COMMENT '学科',
  `teachingpeople_id` int(11) DEFAULT NULL COMMENT '授课人ID',
  `teaching_people` varchar(32) DEFAULT NULL COMMENT '授课人',
  `number_lectures` int(11) DEFAULT NULL COMMENT '听课节数',
  `lecturepeople_id` int(11) DEFAULT NULL COMMENT '听课人ID',
  `lecture_people` varchar(32) DEFAULT NULL COMMENT '听课人',
  `lecture_time` datetime DEFAULT NULL COMMENT '听课时间',
  `evaluation_rank` varchar(32) DEFAULT NULL COMMENT '评价等级',
  `lecture_content` text COMMENT '听课意见',
  `lecture_address` varchar(255) DEFAULT NULL COMMENT '听课地点',
  `lecture_company` varchar(255) DEFAULT NULL COMMENT '听课单位',
  `is_share` tinyint(1) DEFAULT NULL COMMENT '是否分享 0为不分享 1为分享 ',
  `share_time` datetime DEFAULT NULL COMMENT '分享时间',
  `is_delete` tinyint(1) DEFAULT NULL COMMENT '是否删除  0为不删除 1为删除',
  `is_epub` tinyint(1) DEFAULT NULL COMMENT '是否发布 0为存草稿 1为发布 ',
  `epub_time` datetime DEFAULT NULL COMMENT '发布时间,存草稿时间',
  `is_submit` tinyint(4) DEFAULT NULL COMMENT '是否提交  0为不提交  1为提交',
  `submit_time` datetime DEFAULT NULL COMMENT '提交时间',
  `is_scan` tinyint(4) DEFAULT NULL COMMENT '是否查阅 0：未查阅 1：已查阅',
  `scan_up` tinyint(4) DEFAULT NULL COMMENT '查阅记录是否更新 0：未更新，1：已更新',
  `is_reply` tinyint(4) DEFAULT NULL COMMENT '是否回复 0未 1已',
  `reply_up` tinyint(4) DEFAULT NULL COMMENT '回复是否更新  0未 1已',
  `is_comment` tinyint(4) DEFAULT NULL COMMENT '是否评论 0：未  1：已',
  `comment_up` tinyint(4) DEFAULT NULL COMMENT '评论意见已更新 0：未  1：已',
  `crt_dttm` datetime DEFAULT NULL COMMENT '创建时间(继承)',
  `lastup_dttm` datetime DEFAULT NULL COMMENT '最后修改时间（继承）',
  `phase_id` int(11) DEFAULT NULL COMMENT '学段ID',
  `grade_subject` varchar(30) DEFAULT NULL,
  `client_id` varchar(64) DEFAULT NULL COMMENT '客户端ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7410 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `lecture_reply`;
CREATE TABLE `lecture_reply` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `content` varchar(2048) DEFAULT NULL COMMENT '回复内容',
  `type` tinyint(4) DEFAULT NULL COMMENT '回复类型  0回复   1回复的回复',
  `author_id` int(11) DEFAULT NULL COMMENT '作者id(听课人ID)',
  `res_id` int(11) DEFAULT NULL COMMENT '资源id(听课记录主键)',
  `teacher_id` int(11) DEFAULT NULL COMMENT '授课人ID',
  `user_id` int(11) DEFAULT NULL COMMENT '回复者id',
  `username` varchar(11) DEFAULT '' COMMENT '回复者的名字',
  `parent_id` int(11) DEFAULT NULL COMMENT '回复父级',
  `is_delete` tinyint(1) DEFAULT NULL COMMENT '是否删除 0未  1已',
  `is_hidden` tinyint(1) DEFAULT NULL COMMENT '是否隐藏 0未 1已',
  `crt_dttm` datetime DEFAULT NULL COMMENT '创建时间(继承)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=402 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `lesson_info`;
CREATE TABLE `lesson_info` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `lesson_id` varchar(32) NOT NULL COMMENT '课题id(元数据id)',
  `lesson_name` varchar(100) DEFAULT NULL COMMENT '课题名称',
  `book_id` varchar(32) DEFAULT NULL COMMENT '书id',
  `book_shortname` varchar(50) DEFAULT NULL COMMENT '书的简称',
  `grade_id` int(10) DEFAULT NULL COMMENT '年级id',
  `subject_id` int(10) DEFAULT NULL COMMENT '学科id',
  `user_id` int(10) NOT NULL COMMENT '用户id',
  `org_id` int(10) DEFAULT NULL COMMENT '机构id',
  `school_year` int(4) DEFAULT NULL COMMENT '学年',
  `fascicule_id` mediumint(9) DEFAULT NULL COMMENT '册别id',
  `term_id` int(10) DEFAULT NULL COMMENT '学期id',
  `phase_id` int(10) DEFAULT NULL COMMENT '学段id',
  `submit_time` datetime DEFAULT NULL COMMENT '提交时间',
  `share_time` datetime DEFAULT NULL COMMENT '分享时间',
  `jiaoan_shareCount` int(10) DEFAULT NULL COMMENT '教案数',
  `kejian_shareCount` int(10) DEFAULT NULL COMMENT '课件数',
  `fansi_shareCount` int(10) DEFAULT NULL COMMENT '课后反思数',
  `jiaoan_submitCount` int(10) DEFAULT NULL COMMENT '提交的教案数量',
  `kejian_submitCount` int(10) DEFAULT NULL COMMENT '课件的提交数量',
  `fansi_submitCount` int(10) DEFAULT NULL COMMENT '反思的提交数量',
  `jiaoan_count` int(10) DEFAULT NULL COMMENT '教案数量',
  `kejian_count` int(10) DEFAULT NULL COMMENT '课件数量',
  `fansi_count` int(10) DEFAULT NULL COMMENT '反思数量',
  `scan_count` int(10) DEFAULT NULL COMMENT '审阅意见数量',
  `visit_count` int(10) DEFAULT NULL COMMENT '听课意见数量',
  `comment_count` int(10) DEFAULT NULL COMMENT '评论意见数量',
  `scan_up` tinyint(1) DEFAULT NULL COMMENT '审阅意见已更新',
  `visit_up` tinyint(1) DEFAULT NULL COMMENT '听课意见已更新',
  `comment_up` tinyint(1) DEFAULT NULL COMMENT '评论意见已更新',
  `crt_id` int(10) DEFAULT NULL COMMENT '创建人id',
  `crt_dttm` date DEFAULT NULL COMMENT '创建时间',
  `current_from` tinyint(2) DEFAULT '0' COMMENT '课题的来源  0：自发，1：来自接收集体备课 2：来自接收校际教研',
  PRIMARY KEY (`id`),
  KEY `userid` (`user_id`) USING BTREE,
  KEY `book` (`book_id`) USING BTREE,
  KEY `grade_subject` (`grade_id`,`subject_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=84608 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `lesson_plan`;
CREATE TABLE `lesson_plan` (
  `plan_id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `plan_name` varchar(100) NOT NULL COMMENT '备课资源名称',
  `info_id` int(10) DEFAULT NULL COMMENT 'lessinInfo的id',
  `res_id` varchar(32) NOT NULL COMMENT '资源id',
  `plan_type` tinyint(1) DEFAULT NULL COMMENT '资源类型（0：教案，1：课件，2：课后反思，3：其他反思）',
  `digest` varchar(200) DEFAULT NULL COMMENT '摘要',
  `user_id` int(10) NOT NULL COMMENT '用户id',
  `subject_id` int(10) DEFAULT NULL COMMENT '科目id',
  `grade_id` int(10) DEFAULT NULL COMMENT '年级id',
  `book_id` varchar(32) DEFAULT NULL COMMENT '书的id',
  `book_shortname` varchar(50) DEFAULT NULL COMMENT '书的简称',
  `lesson_id` varchar(32) DEFAULT NULL COMMENT '课题id',
  `hours_id` varchar(64) DEFAULT NULL COMMENT '课时id',
  `fascicule_id` mediumint(9) DEFAULT NULL COMMENT '册别id',
  `tp_id` int(10) DEFAULT NULL COMMENT '模板id',
  `org_id` int(10) DEFAULT NULL COMMENT '用户所在机构id',
  `school_year` smallint(4) DEFAULT NULL COMMENT '学年',
  `term_id` int(10) DEFAULT NULL COMMENT '学期id',
  `phase_id` int(10) unsigned DEFAULT NULL COMMENT '学段id',
  `is_submit` tinyint(1) DEFAULT NULL COMMENT '是否已提交（0：未提交，1：已提交）',
  `submit_time` datetime DEFAULT NULL COMMENT '提交时间',
  `is_share` tinyint(1) DEFAULT NULL COMMENT '是否已分享（0：未，1：已）',
  `share_time` datetime DEFAULT NULL COMMENT '分享时间',
  `is_scan` tinyint(1) DEFAULT NULL COMMENT '是否已查阅  0：未  1：已',
  `scan_up` tinyint(1) DEFAULT NULL COMMENT '查阅意见已更新  0：未  1：已',
  `is_comment` tinyint(1) DEFAULT NULL COMMENT '是否已评论  0：未  1：已',
  `comment_up` tinyint(1) DEFAULT NULL COMMENT '评论意见已更新 0：未  1：已',
  `down_num` int(10) DEFAULT NULL COMMENT '下载量',
  `order_value` tinyint(2) DEFAULT NULL COMMENT '排序值',
  `crt_id` int(10) DEFAULT NULL COMMENT '创建人id',
  `crt_dttm` datetime DEFAULT NULL COMMENT '创建时间',
  `lastup_id` int(10) DEFAULT NULL COMMENT '最后更新人id',
  `lastup_dttm` datetime DEFAULT NULL COMMENT '最后更新时间',
  `enable` tinyint(1) DEFAULT NULL COMMENT '有效性（0：无效，1：有效）',
  `origin` tinyint(1) DEFAULT NULL COMMENT '标识来源，0本地上传，1平台对接上传',
  PRIMARY KEY (`plan_id`),
  KEY `课题` (`info_id`) USING BTREE,
  KEY `用户` (`user_id`) USING BTREE,
  KEY `grade_subject` (`subject_id`,`grade_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=160135 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `lesson_plan_template`;
CREATE TABLE `lesson_plan_template` (
  `tp_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '模板id',
  `tp_name` varchar(50) NOT NULL COMMENT '模板名称',
  `org_id` int(10) DEFAULT NULL COMMENT '机构id',
  `org_name` varchar(50) DEFAULT NULL COMMENT '学校名称',
  `res_id` varchar(32) DEFAULT NULL COMMENT '对应资源id',
  `tp_isDefault` tinyint(1) DEFAULT NULL COMMENT '是否默认（1：默认，0：不默认）',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `tp_type` tinyint(1) DEFAULT NULL COMMENT '模板类型（0：系统提供，1：学校提供）',
  `phaseIds` varchar(50) DEFAULT NULL COMMENT '适用的学段id',
  `phaseNames` varchar(50) DEFAULT NULL COMMENT '适用的学段名称',
  `ico` varchar(256) DEFAULT NULL,
  `crt_id` int(10) DEFAULT NULL COMMENT '创建人id',
  `crt_dttm` datetime DEFAULT NULL COMMENT '创建时间',
  `lastup_id` int(10) DEFAULT NULL COMMENT '最后更新人id',
  `lastup_dttm` datetime DEFAULT NULL COMMENT '最后更新时间',
  `enable` int(2) DEFAULT NULL COMMENT '有效性',
  PRIMARY KEY (`tp_id`)
) ENGINE=InnoDB AUTO_INCREMENT=84 DEFAULT CHARSET=utf8 COMMENT='教案模板表';


DROP TABLE IF EXISTS `red_head_manage`;
CREATE TABLE `red_head_manage` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `school_id` int(11) DEFAULT NULL COMMENT '学校id',
  `crt_id` int(11) DEFAULT NULL COMMENT '创建者id',
  `red_head_title` varchar(128) DEFAULT NULL COMMENT '红头标题',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `region_activity`;
CREATE TABLE `region_activity` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `type_id` tinyint(4) NOT NULL COMMENT '活动类型，1：同备教案，2：主题研讨',
  `type_name` varchar(32) DEFAULT NULL,
  `activity_name` varchar(128) DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL COMMENT '活动状态，0：草稿，1：正式发文',
  `subject_ids` varchar(64) DEFAULT NULL COMMENT '示例：\r\n            _11_\r\n            _11_12_',
  `subject_name` varchar(64) DEFAULT NULL COMMENT '示例：\r\n            语文、数学',
  `grade_ids` varchar(64) DEFAULT NULL COMMENT '示例：\r\n            _11_\r\n            _11_12_',
  `grade_name` varchar(64) DEFAULT NULL COMMENT '示例：\r\n            一、二',
  `orgs_join_ids` varchar(64) DEFAULT NULL COMMENT '参与学校id连成的字符串 如：,1,2,3,4,',
  `orgs_join_count` tinyint(4) DEFAULT NULL COMMENT '参与学校的数量',
  `organize_user_id` int(10) DEFAULT NULL,
  `organize_user_name` varchar(32) DEFAULT NULL,
  `space_id` int(10) DEFAULT NULL COMMENT '用户空间id',
  `role_id` int(10) DEFAULT NULL COMMENT '角色id',
  `area_id` int(10) DEFAULT NULL COMMENT '地区id',
  `term` tinyint(1) DEFAULT NULL COMMENT '学期',
  `phase_id` int(10) DEFAULT NULL COMMENT '学段id',
  `school_year` int(4) DEFAULT NULL COMMENT '学年',
  `start_time` datetime DEFAULT NULL,
  `org_id` int(10) DEFAULT NULL COMMENT '机构id',
  `end_time` datetime DEFAULT NULL,
  `comments_num` int(10) DEFAULT '0',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `release_time` datetime DEFAULT NULL COMMENT '发布时间',
  `remark` varchar(1000) DEFAULT NULL,
  `is_over` tinyint(1) DEFAULT NULL COMMENT '是否结束 0：未  1：已',
  `is_submit` tinyint(1) DEFAULT NULL COMMENT '是否提交   0：未， 1：已',
  `is_audit` tinyint(1) DEFAULT NULL COMMENT '是否查阅  0：未，1：已',
  `is_share` tinyint(1) DEFAULT '0' COMMENT '是否分享  0：未分享  1：已分享',
  `is_comment` tinyint(1) DEFAULT '0' COMMENT '是否评论  0：未评论  1：已评论 ',
  `is_send` tinyint(1) DEFAULT '0' COMMENT '是否发送 0：未  1：已',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=440 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `region_activity_plan`;
CREATE TABLE `region_activity_plan` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `activity_id` int(10) DEFAULT NULL COMMENT '区域教研id',
  `res_id` varchar(32) DEFAULT NULL COMMENT '资源存储id',
  `plan_name` varchar(256) DEFAULT NULL COMMENT '教案文件名称',
  `ext` varchar(10) DEFAULT NULL COMMENT '文件扩展名',
  `crt_id` int(10) DEFAULT NULL COMMENT '上传人id',
  `crt_dttm` datetime DEFAULT NULL COMMENT '上传时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=584 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `region_activity_tracks`;
CREATE TABLE `region_activity_tracks` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `activity_id` int(10) DEFAULT NULL COMMENT '教研活动id',
  `plan_id` int(10) DEFAULT NULL COMMENT '教案id',
  `plan_name` varchar(100) DEFAULT NULL COMMENT '教案名称',
  `edit_type` tinyint(2) DEFAULT NULL COMMENT '修改类型  0：教案意见，1：教案整理',
  `res_id` varchar(32) DEFAULT NULL COMMENT '资源id',
  `user_id` int(10) DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(20) DEFAULT NULL COMMENT '用户真实姓名',
  `org_id` int(10) DEFAULT NULL COMMENT '机构id',
  `area_id` int(10) DEFAULT NULL COMMENT '地区id',
  `subject_id` int(10) DEFAULT NULL COMMENT '科目id',
  `school_year` smallint(4) DEFAULT NULL COMMENT '学年',
  `crt_id` int(10) DEFAULT NULL COMMENT '创建人id',
  `crt_dttm` datetime DEFAULT NULL COMMENT '创建时间',
  `lastup_dttm` datetime DEFAULT NULL COMMENT '更新时间',
  `grade_id` int(10) DEFAULT NULL COMMENT '�꼶ID',
  `space_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=188 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `regsch_activity_discuss`;
CREATE TABLE `regsch_activity_discuss` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '区域和校际讨论表ID',
  `type_id` tinyint(1) DEFAULT NULL COMMENT '讨论类型ID   1：区域教研讨论   2：校际教研讨论',
  `activity_id` int(10) DEFAULT NULL COMMENT '关联校际或者区域活动的ID',
  `crt_id` int(10) DEFAULT NULL COMMENT '讨论人ID',
  `space_id` int(10) DEFAULT NULL COMMENT '讨论人当时的身份空间ID',
  `content` varchar(300) DEFAULT NULL COMMENT '讨论的内容',
  `crt_dttm` datetime DEFAULT NULL COMMENT '讨论发表的时间',
  `discuss_level` tinyint(1) DEFAULT NULL COMMENT '讨论层级 1：一层（直接对应活动的讨论） 2：二层（对一层的讨论进行的评论）',
  `parent_id` int(10) DEFAULT '0' COMMENT '讨论的父层ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1258 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `school_activity`;
CREATE TABLE `school_activity` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '校际教研活动表id',
  `type_id` tinyint(4) DEFAULT NULL COMMENT '1:同备教案 2:主题研讨 3:视频研讨 4:直播课堂',
  `info_id` int(10) DEFAULT NULL COMMENT '类型为“同备教案”是的课题信息ID',
  `type_name` varchar(15) DEFAULT NULL COMMENT '活动类型名称“同备教案”、“主题研讨”',
  `activity_name` varchar(100) DEFAULT NULL COMMENT '活动名称（主题）',
  `remark` varchar(500) DEFAULT NULL COMMENT '活动要求 备注等信息',
  `status` tinyint(1) DEFAULT NULL COMMENT '0：活动草稿  1：活动正式发文',
  `subject_ids` varchar(128) DEFAULT NULL,
  `subject_name` varchar(128) DEFAULT NULL,
  `grade_ids` varchar(128) DEFAULT NULL,
  `grade_name` varchar(128) DEFAULT NULL,
  `phase_id` int(10) DEFAULT NULL COMMENT '学段id',
  `org_id` int(10) DEFAULT NULL COMMENT '发布人的机构ID',
  `organize_user_id` int(10) DEFAULT NULL COMMENT '组织者Id',
  `organize_user_name` varchar(10) DEFAULT NULL COMMENT '组织者名称',
  `organize_subject_id` int(5) DEFAULT '0' COMMENT '发起者学科ID',
  `organize_grade_id` int(5) DEFAULT '0' COMMENT '发起者年级ID',
  `space_id` int(10) DEFAULT NULL COMMENT '用户空间Id',
  `term` tinyint(1) DEFAULT NULL COMMENT '学期  0：上学期  1：下学期',
  `school_year` int(4) DEFAULT NULL COMMENT '学年',
  `start_time` datetime DEFAULT NULL COMMENT '活动开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '活动结束时间',
  `comments_num` int(10) DEFAULT NULL COMMENT '活动评论数',
  `create_time` datetime DEFAULT NULL COMMENT '活动创建时间',
  `release_time` datetime DEFAULT NULL COMMENT '发布时间',
  `is_over` tinyint(1) DEFAULT NULL COMMENT '是否结束 0：未结束  1：已结束',
  `is_submit` tinyint(1) DEFAULT NULL COMMENT '是否提交 0：未提交  1：已提交',
  `is_audit` tinyint(1) DEFAULT NULL COMMENT '是否查阅  0：未查阅   1：已查阅',
  `is_share` tinyint(1) DEFAULT NULL COMMENT '是否分享  0：未分享  1：已分享',
  `is_comment` tinyint(1) DEFAULT NULL COMMENT '是否评论  0：未评论  1：已评论',
  `is_send` tinyint(1) DEFAULT NULL COMMENT '整理教案是否发送  0：未发送，1：已发送',
  `main_user_id` int(10) DEFAULT NULL COMMENT '主备人id',
  `main_user_name` varchar(10) DEFAULT NULL COMMENT '主备人名称',
  `main_user_org_id` int(10) DEFAULT NULL COMMENT '主备人的学校机构ID',
  `main_user_subject_id` int(5) DEFAULT NULL COMMENT '主备人学科ID',
  `main_user_grade_id` int(5) DEFAULT NULL COMMENT '主备人年级ID',
  `school_teach_circle_id` int(10) DEFAULT NULL COMMENT '校际教研圈ID',
  `school_teach_circle_name` varchar(30) DEFAULT NULL COMMENT '校际教研圈名称',
  `area_ids` varchar(30) DEFAULT NULL COMMENT '所属区域父级节点集合',
  `expert_ids` varchar(100) DEFAULT NULL COMMENT '专家id字符串集合，也即用户id',
  `share_time` datetime DEFAULT NULL COMMENT '分享时间',
  `orgids` varchar(1024) DEFAULT '' COMMENT '发布时，可参与的机构ids',
  `video_url` varchar(100) DEFAULT NULL COMMENT '视频地址',
  `class_id` varchar(64) DEFAULT NULL COMMENT '直播课堂的id',
  `part_users` varchar(512) DEFAULT '' COMMENT '校级教研主要研讨人ids记录',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=919 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `school_activity_tracks`;
CREATE TABLE `school_activity_tracks` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `activity_id` int(10) DEFAULT NULL COMMENT '集体备课id',
  `plan_id` int(10) DEFAULT NULL COMMENT '教案id',
  `plan_name` varchar(100) DEFAULT NULL COMMENT '教案名称',
  `edit_type` tinyint(2) DEFAULT NULL COMMENT '修改类型  0：教案意见，1：教案整理',
  `lesson_id` varchar(32) DEFAULT NULL COMMENT '课题id',
  `res_id` varchar(32) DEFAULT NULL COMMENT '资源id',
  `hours_id` varchar(10) DEFAULT NULL COMMENT '课时id',
  `order_value` tinyint(2) DEFAULT NULL COMMENT '课时排序值',
  `user_id` int(10) DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(20) DEFAULT NULL COMMENT '用户真实姓名',
  `subject_id` int(10) DEFAULT NULL COMMENT '科目id',
  `school_year` smallint(4) DEFAULT NULL COMMENT '学年',
  `crt_id` int(10) DEFAULT NULL COMMENT '创建人id',
  `crt_dttm` datetime DEFAULT NULL COMMENT '创建时间',
  `lastup_dttm` datetime DEFAULT NULL COMMENT '更新时间',
  `org_id` int(11) DEFAULT NULL COMMENT '����ID',
  `grade_id` int(10) DEFAULT NULL COMMENT '�꼶ID',
  `space_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=536 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `school_show`;
CREATE TABLE `school_show` (
  `enable` int(11) DEFAULT NULL,
  `lastup_dttm` datetime DEFAULT NULL,
  `lastup_id` int(11) DEFAULT NULL,
  `crt_dttm` datetime DEFAULT NULL,
  `crt_id` int(11) DEFAULT NULL,
  `type` varchar(32) NOT NULL COMMENT '类型（master、overview、teacher）',
  `introduction` text NOT NULL COMMENT '介绍',
  `introduction_mini` varchar(1024) DEFAULT NULL COMMENT '简介',
  `title` varchar(512) DEFAULT NULL COMMENT '标题',
  `images` text COMMENT '图片资源（逗号分隔）',
  `parent_id` int(11) NOT NULL,
  `id` varchar(32) NOT NULL,
  `author` varchar(64) DEFAULT NULL COMMENT '作者',
  `top_tag` int(11) DEFAULT '0' COMMENT '置顶标示符',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `school_teach_circle`;
CREATE TABLE `school_teach_circle` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '教研圈ID',
  `name` varchar(30) DEFAULT NULL COMMENT '教研圈名称',
  `org_id` int(10) DEFAULT NULL COMMENT '创建机构ID',
  `crt_id` int(10) DEFAULT NULL COMMENT '创建者ID',
  `crt_dttm` datetime DEFAULT NULL COMMENT '创建时间',
  `school_year` int(4) DEFAULT NULL COMMENT '学年',
  `lastup_dttm` datetime DEFAULT NULL COMMENT '教研圈更新时间（只能有创建人更新）',
  `is_delete` tinyint(1) DEFAULT NULL COMMENT '0:可删除  1:不可删除',
  `area_ids` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=184 DEFAULT CHARSET=utf8 COMMENT='校际教研圈表 ，可以由多个学校组成的一个教研圈';


DROP TABLE IF EXISTS `school_teach_circle_org`;
CREATE TABLE `school_teach_circle_org` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '校际教研圈附属表ID',
  `stc_id` int(10) DEFAULT NULL COMMENT '校际教研圈ID',
  `org_id` int(10) DEFAULT NULL COMMENT '学校机构ID',
  `org_name` varchar(30) DEFAULT NULL COMMENT '学校机构名称',
  `school_year` int(4) DEFAULT NULL COMMENT '学年',
  `state` tinyint(4) DEFAULT NULL COMMENT '1：待接受  2：已接受  3：已拒绝  4：已退出  5：已恢复',
  `sort` tinyint(4) DEFAULT NULL COMMENT '机构排序',
  `lastup_id` int(10) DEFAULT NULL COMMENT '状态更新人',
  `lastup_dttm` datetime DEFAULT NULL COMMENT '圈中的机构状态更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=470 DEFAULT CHARSET=utf8 COMMENT='教研圈附属机构表 存放某个教研圈下面的机构以及机构状态';


DROP TABLE IF EXISTS `school_teach_schedule`;
CREATE TABLE `school_teach_schedule` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '教研进度ID',
  `name` varchar(30) DEFAULT NULL COMMENT '进度表名称',
  `org_id` int(10) DEFAULT NULL COMMENT '机构ID',
  `subject_id` int(4) DEFAULT NULL COMMENT '学科ID',
  `grade_id` int(4) DEFAULT NULL COMMENT '年级ID',
  `res_id` varchar(32) DEFAULT NULL COMMENT '文件资源ID',
  `crt_id` int(10) DEFAULT NULL COMMENT '发布人(作者)',
  `crt_dttm` datetime DEFAULT NULL COMMENT '发布时间',
  `school_teach_circle_id` int(10) DEFAULT NULL COMMENT '校际教研圈ID',
  `is_release` tinyint(1) DEFAULT NULL COMMENT '是否发布',
  `release_time` datetime DEFAULT NULL COMMENT '发布时间',
  `lastup_id` int(10) DEFAULT NULL COMMENT '最后修改人',
  `lastup_dttm` datetime DEFAULT NULL COMMENT '最后修改时间',
  `school_year` smallint(4) DEFAULT NULL COMMENT '学年',
  `file_suffix` varchar(10) DEFAULT NULL COMMENT '文件后缀',
  `area_ids` varchar(20) DEFAULT NULL COMMENT '所属地区id节点集合   示例 ： ,1,22,344,1000,',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=238 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `sys_app`;
CREATE TABLE `sys_app` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appname` varchar(128) DEFAULT NULL COMMENT '应用名称',
  `appid` varchar(64) DEFAULT NULL,
  `appkey` varchar(64) DEFAULT NULL COMMENT '应用访问密匙',
  `login_pre` varchar(16) DEFAULT '' COMMENT '登录名前缀',
  `login_url` varchar(256) DEFAULT '' COMMENT '登录地址',
  `url` varchar(128) DEFAULT NULL COMMENT '应用补充信息地址',
  `phone` varchar(16) DEFAULT NULL COMMENT '联系人电话',
  `contact` varchar(20) DEFAULT NULL COMMENT '联系人',
  `type` varchar(64) DEFAULT NULL COMMENT 'app类型，0 用户接入，1 用户接出 ，2 即可接入也可接出',
  `crt_id` int(11) DEFAULT NULL COMMENT '应用创建者',
  `crt_dttm` datetime DEFAULT NULL,
  `lastup_id` int(11) DEFAULT NULL,
  `lastup_dttm` datetime DEFAULT NULL,
  `enable` tinyint(4) DEFAULT NULL COMMENT '是否有效',
  PRIMARY KEY (`id`),
  UNIQUE KEY `app_id` (`appid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `sys_app_param`;
CREATE TABLE `sys_app_param` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `appid` char(64) NOT NULL COMMENT 'appid',
  `name` varchar(255) NOT NULL,
  `val` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `appid` (`appid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `sys_area`;
CREATE TABLE `sys_area` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL COMMENT '名称',
  `parent_id` smallint(6) DEFAULT NULL COMMENT '父区域',
  `postcode` varchar(32) DEFAULT NULL COMMENT '邮政编码',
  `code` varchar(32) DEFAULT NULL COMMENT '区域编码',
  `level` tinyint(4) DEFAULT NULL COMMENT '层级',
  `sort` smallint(6) DEFAULT NULL COMMENT '排序',
  `type` tinyint(4) DEFAULT NULL COMMENT '0  行政区域， 1 自定义区域',
  `crt_dttm` datetime DEFAULT NULL,
  `crt_id` int(11) DEFAULT NULL,
  `lastup_id` int(11) DEFAULT NULL,
  `lastup_dttm` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3228 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `sys_attach`;
CREATE TABLE `sys_attach` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `activity_id` int(10) DEFAULT NULL COMMENT '集备id',
  `activity_type` tinyint(1) DEFAULT NULL COMMENT '活动类型，0：集体备课，1：区域教研，2：校际教研，3：专家指导',
  `res_id` varchar(32) DEFAULT NULL COMMENT '资源存储id',
  `attach_name` varchar(256) DEFAULT NULL COMMENT '附件名称',
  `ext` varchar(10) DEFAULT NULL COMMENT '文件扩展名',
  `crt_id` int(10) DEFAULT NULL COMMENT '上传人id',
  `crt_dttm` datetime DEFAULT NULL COMMENT '上传时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1227 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) DEFAULT '0',
  `name` varchar(32) DEFAULT '' COMMENT '配置名称',
  `key` varchar(32) NOT NULL,
  `value` varchar(255) NOT NULL DEFAULT '',
  `store_dir` varchar(255) DEFAULT '',
  `store_range` varchar(255) DEFAULT '',
  `sort` smallint(5) DEFAULT '0',
  `scope_type` tinyint(4) DEFAULT '0' COMMENT '范围类型： 0 系统， 1 机构， 2 用户',
  `scope` int(255) DEFAULT '0' COMMENT '配置应用范围id： 0 系统，  其他为机构或用户id',
  `type` varchar(10) DEFAULT '' COMMENT '属性类型:group 分组，text 文本',
  `crt_id` int(11) DEFAULT NULL,
  `crt_dttm` datetime DEFAULT NULL,
  `lastup_id` int(11) DEFAULT NULL,
  `lastup_dttm` datetime DEFAULT NULL,
  `enable` tinyint(2) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `sys_dic`;
CREATE TABLE `sys_dic` (
  `dic_id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `dic_name` varchar(64) NOT NULL,
  `parent_id` smallint(5) unsigned NOT NULL,
  `dic_level` tinyint(3) unsigned NOT NULL,
  `dic_orderby` tinyint(3) unsigned NOT NULL,
  `dic_status` varchar(32) NOT NULL,
  `operator` varchar(32) NOT NULL,
  `cascade_dic_ids` varchar(64) NOT NULL,
  `child_count` smallint(5) unsigned NOT NULL,
  `standard_code` varchar(8) DEFAULT NULL,
  `org_id` int(11) DEFAULT '0',
  `area_id` int(11) DEFAULT '0',
  `scope` varchar(8) DEFAULT 'sys',
  PRIMARY KEY (`dic_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2064 DEFAULT CHARSET=utf8 COMMENT='元数据描述表';


DROP TABLE IF EXISTS `sys_icon`;
CREATE TABLE `sys_icon` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `identity` varchar(32) DEFAULT NULL,
  `css_class` varchar(64) DEFAULT NULL,
  `img_src` varchar(200) DEFAULT NULL,
  `width` smallint(6) DEFAULT NULL,
  `height` smallint(6) DEFAULT NULL,
  `left` smallint(6) DEFAULT NULL,
  `top` smallint(6) DEFAULT NULL,
  `type` varchar(30) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `description` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_sys_icon_identity` (`identity`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `sys_login`;
CREATE TABLE `sys_login` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `loginname` varchar(32) DEFAULT NULL,
  `password` varchar(32) NOT NULL DEFAULT '',
  `cellphone` varchar(11) DEFAULT '',
  `mail` varchar(128) DEFAULT '',
  `enable` tinyint(4) DEFAULT '0',
  `deleted` tinyint(1) DEFAULT NULL,
  `salt` char(8) DEFAULT '',
  `is_admin` tinyint(1) DEFAULT '0',
  `logincode` varchar(64) DEFAULT '' COMMENT '第三方登陆代码',
  PRIMARY KEY (`id`),
  UNIQUE KEY `loginname` (`loginname`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=18887 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `sys_login_log`;
CREATE TABLE `sys_login_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `space_id` int(11) DEFAULT NULL,
  `ip` varchar(32) DEFAULT NULL,
  `grade_id` int(11) DEFAULT NULL,
  `subject_id` int(11) DEFAULT NULL,
  `sys_role_id` int(11) DEFAULT NULL COMMENT '系统角色id',
  `login_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '登录时间或切换角色时间',
  `type` tinyint(2) DEFAULT NULL COMMENT '登录类型。 0 ：正常登录 ， 1  切换身份',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=77074 DEFAULT CHARSET=utf8 COMMENT='用户登录日志';


DROP TABLE IF EXISTS `sys_mail_verify_code`;
CREATE TABLE `sys_mail_verify_code` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) DEFAULT NULL,
  `verification_code` varchar(32) DEFAULT NULL,
  `creat_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  `parentid` int(11) DEFAULT NULL,
  `ico_id` int(11) DEFAULT NULL,
  `url` varchar(128) DEFAULT NULL,
  `code` varchar(32) NOT NULL COMMENT '权限码',
  `fixed` tinyint(1) DEFAULT NULL COMMENT '0 不固定\r\n            1 固定',
  `sort` int(11) DEFAULT NULL,
  `sys_role_id` int(11) DEFAULT NULL,
  `target` varchar(32) DEFAULT '' COMMENT '打开方式',
  `descs` varchar(128) DEFAULT NULL,
  `is_normal` tinyint(1) DEFAULT '1' COMMENT '是否支持PC',
  `is_tablet` tinyint(1) DEFAULT '0' COMMENT '是否支持平板',
  `is_mobile` tinyint(1) DEFAULT '0' COMMENT '是否支持移动端',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=228 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `sys_meta_relationship`;
CREATE TABLE `sys_meta_relationship` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL COMMENT '学制名称',
  `ids` varchar(128) DEFAULT NULL COMMENT '关联表字段id列表，使用‘，’ 分割',
  `type` tinyint(4) DEFAULT NULL COMMENT '类型， 0 -- 学制(id)<--->年级(ids)\r\n                        1 -- 学段(mid)<--->学科(ids)',
  `eid` int(11) DEFAULT NULL COMMENT '基础元素id',
  `sort` int(11) DEFAULT '0' COMMENT '排序等相关信息',
  `descs` varchar(256) DEFAULT '' COMMENT '描述等相关信息',
  `org_id` int(11) DEFAULT NULL,
  `area_id` int(11) DEFAULT NULL,
  `enable` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2570 DEFAULT CHARSET=utf8 COMMENT='基础元数据关系表';


DROP TABLE IF EXISTS `sys_organization`;
CREATE TABLE `sys_organization` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code` varchar(25) DEFAULT NULL COMMENT '机构编码',
  `parent_id` int(10) unsigned DEFAULT NULL COMMENT '父级机构',
  `short_name` varchar(32) DEFAULT NULL COMMENT '机构简称',
  `name` varchar(64) DEFAULT NULL COMMENT '机构全称',
  `area_ids` varchar(1024) DEFAULT NULL COMMENT '区域层级id 示例 ： ,1,10,190,378,',
  `area_id` int(11) DEFAULT NULL COMMENT '所属区域id',
  `area_name` varchar(256) DEFAULT NULL COMMENT '所属区域名称',
  `address` varchar(64) DEFAULT NULL COMMENT '机构地址',
  `contact_person` varchar(64) DEFAULT NULL COMMENT '联系人',
  `phone_number` varchar(64) DEFAULT NULL COMMENT '联系方式',
  `note` varchar(512) DEFAULT NULL COMMENT '简介',
  `level` tinyint(3) unsigned DEFAULT NULL COMMENT '机构级别',
  `org_level_name` varchar(128) DEFAULT NULL COMMENT '机构级别名称',
  `type` int(10) unsigned NOT NULL COMMENT '机构类型',
  `type_name` varchar(64) DEFAULT NULL COMMENT '机构类型名称',
  `sort` smallint(5) unsigned NOT NULL COMMENT '排序',
  `tree_level` tinyint(3) unsigned NOT NULL COMMENT '机构树的层级（从1开始）',
  `location_area_id` int(11) DEFAULT NULL COMMENT '所在区域id,如直属校所在区域',
  `phase_types` varchar(32) DEFAULT NULL,
  `schoolings` int(11) DEFAULT NULL COMMENT '学制',
  `logo` varchar(256) DEFAULT NULL COMMENT '机构logo',
  `category` tinyint(4) DEFAULT NULL COMMENT '机构类别：如学校：分直属校',
  `viplevel` tinyint(4) DEFAULT NULL COMMENT '会员级别：0 非会员，其他为会员不同级别',
  `lastup_dttm` datetime DEFAULT NULL,
  `lastup_id` int(11) DEFAULT NULL,
  `crt_id` int(11) DEFAULT NULL,
  `enable` tinyint(1) DEFAULT NULL,
  `crt_dttm` datetime DEFAULT NULL,
  `system_id` tinyint(4) DEFAULT NULL COMMENT 'ѧ',
  `org_type` int(4) DEFAULT NULL,
  `english_name` varchar(32) DEFAULT NULL COMMENT 'Ӣ',
  `email` varchar(32) DEFAULT NULL,
  `sch_website` varchar(32) DEFAULT NULL COMMENT 'ѧУ',
  `direct_area_id` varchar(32) DEFAULT NULL COMMENT 'ֱ',
  `dirlevel_id` varchar(32) DEFAULT NULL COMMENT 'ֱ',
  `trdparty_org_id` varchar(64) DEFAULT NULL COMMENT '对接机构ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=492 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `sys_org_relationship`;
CREATE TABLE `sys_org_relationship` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `org_id` int(11) NOT NULL,
  `phase_id` int(11) NOT NULL,
  `schooling` int(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=161 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `sys_org_solution`;
CREATE TABLE `sys_org_solution` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `org_id` int(11) DEFAULT NULL COMMENT '学校id',
  `solution_id` int(11) DEFAULT NULL,
  `is_delete` tinyint(1) DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8 COMMENT='学校方案表';


DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  `code` varchar(32) DEFAULT NULL COMMENT '操作码，以'' : ’ 分割的权限码',
  `remark` varchar(128) DEFAULT NULL,
  `sys_role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `sys_publish_relationship`;
CREATE TABLE `sys_publish_relationship` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '关系表主键',
  `phase_id` int(10) DEFAULT '0' COMMENT '学段ID',
  `subject_id` int(10) DEFAULT '0' COMMENT '学科ID',
  `name` varchar(32) DEFAULT '' COMMENT '出版社全称',
  `short_name` varchar(16) DEFAULT '' COMMENT '出版社简称',
  `eid` int(10) DEFAULT '0' COMMENT '出版社元数据ID',
  `sort` int(5) DEFAULT '0' COMMENT '排序',
  `crt_dttm` datetime DEFAULT NULL COMMENT '创建时间',
   `scope` varchar(8) DEFAULT 'sys',
  `org_id` int(11) DEFAULT NULL,
  `area_id` int(11) DEFAULT NULL,
  `enable` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=384 DEFAULT CHARSET=utf8 COMMENT='用于维护学段学科与出版社的关系！';


DROP TABLE IF EXISTS `sys_resources`;
CREATE TABLE `sys_resources` (
  `id` varchar(32) NOT NULL COMMENT '主键资源id',
  `path` varchar(256) DEFAULT NULL COMMENT '资源存储的相对路径（包含文件名和后缀）',
  `name` varchar(256) DEFAULT NULL COMMENT '资源的真实名字（不包含后缀）',
  `size` bigint(11) DEFAULT NULL COMMENT '资源大小（字节）',
  `ext` varchar(5) DEFAULT NULL COMMENT '后缀（如：doc，不含点）',
  `device_id` int(10) DEFAULT NULL COMMENT '服务器id',
  `device_name` varchar(50) DEFAULT NULL COMMENT '服务器名称',
  `state` tinyint(4) DEFAULT NULL COMMENT '资源状态',
  `crt_dttm` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `sys_res_synctask`;
CREATE TABLE `sys_res_synctask` (
  `id` varchar(32) NOT NULL COMMENT '主键，与sys_resource id 一致',
  `account` varchar(64) DEFAULT NULL,
  `res_id` int(11) DEFAULT NULL COMMENT '原资源id',
  `relative_path` varchar(255) DEFAULT NULL COMMENT '相对路径',
  `crt_dttm` datetime DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sys_role_id` int(11) DEFAULT NULL,
  `role_name` varchar(64) DEFAULT NULL,
  `role_code` varchar(10) DEFAULT NULL COMMENT '权限代码',
  `org_id` int(11) DEFAULT NULL COMMENT '组织机构',
  `remark` varchar(128) DEFAULT NULL,
  `role_desc` varchar(155) DEFAULT NULL,
  `solution_id` int(11) DEFAULT '0' COMMENT '方案id',
  `is_del` tinyint(1) DEFAULT '0' COMMENT '状态 0正常，1删除',
  `rel_id` int(11) DEFAULT NULL COMMENT '关联id 系统--方案',
  `use_position` tinyint(2) DEFAULT NULL COMMENT '应用方向-角色类型--冗余',
  PRIMARY KEY (`id`),
  KEY `sid` (`solution_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=593 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  `perm_bname` varchar(65) DEFAULT NULL COMMENT '功能权限别名',
  `is_del` tinyint(1) DEFAULT '0' COMMENT '状态  0正常 ，1删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5959 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL,
  `permission_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `sys_role_type`;
CREATE TABLE `sys_role_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_type_name` varchar(30) DEFAULT NULL,
  `role_type_desc` varchar(200) DEFAULT NULL,
  `sort` tinyint(4) DEFAULT NULL COMMENT '排序',
  `role_type_perm` varchar(250) DEFAULT NULL COMMENT '角色类型--功能权限',
  `use_position` tinyint(1) DEFAULT NULL COMMENT '应用方向。1区域、2学校、3系统',
  `is_no_bm_manager` tinyint(1) DEFAULT NULL COMMENT '1显示，0不显示',
  `is_no_xz` tinyint(1) DEFAULT NULL COMMENT '学段显示，1显示，0不显示',
  `is_no_xk` tinyint(1) DEFAULT NULL COMMENT '是否需要设置学科属性。1显示，0不显示',
  `is_no_nj` tinyint(1) DEFAULT NULL COMMENT '是否需要设置年级属性。1显示，0不显示',
  `code` int(11) DEFAULT NULL COMMENT 'dic_id',
  `home_url` varchar(255) NOT NULL DEFAULT '' COMMENT '入口地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `sys_solution`;
CREATE TABLE `sys_solution` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(155) DEFAULT NULL,
  `descs` varchar(250) DEFAULT NULL,
  `crt_dttm` date DEFAULT NULL,
  `code` varchar(205) DEFAULT NULL,
  `crt_id` int(11) DEFAULT NULL COMMENT '创建人id',
  `lastup_id` int(11) DEFAULT NULL COMMENT '最后修改人id',
  `lastup_dttm` date DEFAULT NULL COMMENT '最后修改时间',
  `enable` tinyint(4) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COMMENT='方案表';


DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL,
  `nickname` varchar(32) DEFAULT NULL,
  `name` varchar(32) DEFAULT NULL COMMENT '真实姓名',
  `photo` varchar(128) DEFAULT NULL COMMENT '用户头像',
  `original_photo` varchar(128) DEFAULT NULL COMMENT '缩略图',
  `org_id` int(11) DEFAULT NULL COMMENT '所属机构ID',
  `cellphone` varchar(32) DEFAULT NULL,
  `cellphone_view` tinyint(1) DEFAULT '0' COMMENT '手机是否可见',
  `cellphone_valid` tinyint(1) DEFAULT '0' COMMENT '手机是否验证',
  `mail` varchar(64) DEFAULT NULL,
  `mail_valid` tinyint(1) DEFAULT '0' COMMENT '邮箱是否验证',
  `mail_view` tinyint(1) DEFAULT '0' COMMENT '邮箱是否可见',
  `user_type` tinyint(4) DEFAULT '0' COMMENT '用户类别',
  `org_name` varchar(128) DEFAULT NULL,
  `is_famous_teacher` tinyint(4) DEFAULT '0' COMMENT '0非  1 是优秀教师',
  `idcard` varchar(18) DEFAULT NULL,
  `qq` varchar(15) DEFAULT NULL,
  `sex` tinyint(4) DEFAULT NULL COMMENT '性别   0男 1女',
  `birthday` datetime DEFAULT NULL COMMENT '出生日期',
  `school_age` tinyint(4) DEFAULT NULL COMMENT '教龄',
  `profession` varchar(32) DEFAULT NULL COMMENT '职称',
  `honorary` varchar(32) DEFAULT NULL COMMENT '荣誉称号',
  `explains` varchar(1000) DEFAULT NULL COMMENT '个人简介',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `crt_dttm` datetime DEFAULT NULL COMMENT '创建时间',
  `lastup_id` int(11) DEFAULT NULL COMMENT '最后更新用户',
  `lastup_dttm` datetime DEFAULT NULL COMMENT '最后更新时间',
  `crt_id` int(11) DEFAULT NULL COMMENT '创建者id',
  `app_id` int(1) DEFAULT '0',
  `teacher_level` varchar(20) DEFAULT '',
  `address` varchar(128) DEFAULT '',
  `postcode` varchar(8) DEFAULT '',
  `cercode` varchar(32) DEFAULT '',
  `last_login` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `sys_usermenu_history`;
CREATE TABLE `sys_usermenu_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `menus` varchar(1024) NOT NULL DEFAULT '' COMMENT '功能code 传递',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `school_year` int(11) NOT NULL COMMENT '学年',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user-year` (`user_id`,`school_year`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=20926 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `sys_user_managescope`;
CREATE TABLE `sys_user_managescope` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) DEFAULT NULL,
  `role_id` int(10) DEFAULT NULL COMMENT '角色id',
  `org_id` int(10) DEFAULT NULL COMMENT '机构id',
  `area_id` int(10) DEFAULT NULL COMMENT '区域id',
  `org_name` varchar(30) DEFAULT NULL COMMENT '机构名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=233 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `sys_user_menu`;
CREATE TABLE `sys_user_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sys_role_id` int(11) DEFAULT NULL,
  `menu_id` int(11) DEFAULT NULL,
  `name` varchar(16) DEFAULT NULL COMMENT '图标名称，支持个性化命名',
  `ico` varchar(128) DEFAULT NULL COMMENT '图标url，用于支持个性化',
  `display` tinyint(1) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL COMMENT '越小越靠前',
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id_role` (`sys_role_id`,`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=438209 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17614 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `sys_user_space`;
CREATE TABLE `sys_user_space` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(32) DEFAULT NULL COMMENT '真实姓名',
  `user_id` int(11) DEFAULT NULL,
  `book_id` char(32) DEFAULT NULL COMMENT '所教书籍',
  `space_home_url` varchar(64) DEFAULT NULL,
  `org_id` int(11) DEFAULT NULL COMMENT '所属机构',
  `phase_type` int(11) DEFAULT NULL,
  `phase_id` int(11) DEFAULT NULL,
  `grade_id` int(11) DEFAULT NULL,
  `subject_id` int(11) DEFAULT NULL,
  `sys_role_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT '0',
  `space_name` varchar(32) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL COMMENT '数字小优先',
  `is_default` tinyint(1) DEFAULT '0' COMMENT '0否   1 是',
  `subject_order` int(11) DEFAULT NULL,
  `enable` tinyint(4) DEFAULT '1',
  `school_year` int(11) DEFAULT '0' COMMENT '学年',
  `department_id` int(11) DEFAULT '0',
  `con_dep_ids` varchar(64) DEFAULT '',
  `crt_dttm` datetime DEFAULT NULL,
  `crt_id` int(11) DEFAULT NULL,
  `lastup_id` int(11) DEFAULT NULL,
  `lastup_dttm` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`) USING BTREE,
  KEY `org_id` (`org_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=33077 DEFAULT CHARSET=utf8 COMMENT='切换工作空间';


DROP TABLE IF EXISTS `teacher_curriculum`;
CREATE TABLE `teacher_curriculum` (
  `id` int(6) NOT NULL AUTO_INCREMENT,
  `userid` int(6) NOT NULL,
  `week` int(6) DEFAULT NULL,
  `lesson` int(6) DEFAULT NULL,
  `content` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=548 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `teacher_record`;
CREATE TABLE `teacher_record` (
  `record_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `record_name` varchar(255) NOT NULL COMMENT '档案名称',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '精选状态：0未精选、1已精选',
  `sort_desc` varchar(255) DEFAULT NULL COMMENT '微评',
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `modify_time` datetime DEFAULT NULL COMMENT '修改日期',
  `bag_id` int(11) NOT NULL COMMENT '档案包Id',
  `res_type` int(11) NOT NULL DEFAULT '0' COMMENT '资源类型：0 教学设计，1 自制课件，2 教学反思，3 教研活动，4 教学文章，5 计划总结，6 听课记录',
  `volume` int(11) NOT NULL DEFAULT '0' COMMENT '册别：0上册、1中册、2下册',
  `path` varchar(255) DEFAULT NULL COMMENT '文件所在的路径',
  `res_id` int(11) DEFAULT NULL COMMENT '资源原id',
  `term_id` int(2) DEFAULT NULL COMMENT '学期：0 上学期，1 下学期',
  `school_year` int(11) DEFAULT NULL COMMENT '学年',
  `share` int(11) DEFAULT '0',
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1469 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `teacher_record_bag`;
CREATE TABLE `teacher_record_bag` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(16) NOT NULL DEFAULT '自己创建的档案袋' COMMENT '档案袋的名称',
  `sort` int(11) DEFAULT NULL COMMENT '排序字段',
  `type` int(11) NOT NULL DEFAULT '1' COMMENT '区别系统档案袋（0）和自己创建的档案袋（1）',
  `share` int(11) NOT NULL DEFAULT '0' COMMENT '分享状态：未分享（0）、已分享（1）',
  `del` int(11) NOT NULL DEFAULT '0' COMMENT '是否已删除：未删除（0）、已删除（1）',
  `long_desc` varchar(255) DEFAULT NULL COMMENT '简介',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '查阅状态：0未查阅、1已查阅',
  `teacher_id` varchar(255) NOT NULL COMMENT '教师Id',
  `org_status` int(11) NOT NULL DEFAULT '0' COMMENT '上级是否可见：0不可见、1可见',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `short_desc` varchar(255) DEFAULT NULL COMMENT '微评',
  `menu_id` int(11) DEFAULT NULL COMMENT '对应菜单的id（用于权限的判断）',
  `submit` int(11) DEFAULT NULL COMMENT '是否提交  0 未提交  1已提交',
  `pinglun` int(11) DEFAULT NULL COMMENT '是否被评论过  0未评论 1已评论',
  `is_status` int(11) DEFAULT NULL COMMENT '是否已查看过评阅意见：0未查看，1已查看',
  `is_pinglun` int(11) DEFAULT NULL COMMENT '是否查看过评论状态：0未更新,1已更新',
  `grade_id` int(11) DEFAULT NULL,
  `subject_id` int(11) DEFAULT NULL,
  `grade` varchar(32) DEFAULT NULL,
  `subject` varchar(32) DEFAULT NULL,
  `res_count` int(11) DEFAULT NULL,
  `share_time` datetime DEFAULT NULL COMMENT '����ʱ��',
  `org_id` int(11) DEFAULT NULL COMMENT '机构ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2158 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `teacher_schedule`;
CREATE TABLE `teacher_schedule` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '日程表id',
  `userid` int(11) NOT NULL COMMENT '用户id',
  `summary` varchar(256) DEFAULT NULL COMMENT '日程主题',
  `start` bigint(20) DEFAULT NULL COMMENT '日程起始时间',
  `end` bigint(20) DEFAULT NULL COMMENT '日程结束时间',
  `isallday` tinyint(1) DEFAULT NULL COMMENT '是否全天',
  `location` varchar(128) DEFAULT NULL COMMENT '地点',
  `description` varchar(512) DEFAULT NULL COMMENT '描述',
  `color` char(7) DEFAULT NULL COMMENT '颜色',
  `crt_dttm` datetime DEFAULT NULL,
  `lastup_dttm` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=227 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `teaching_thesis`;
CREATE TABLE `teaching_thesis` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NOT NULL COMMENT '用户id',
  `subject_id` int(10) DEFAULT NULL COMMENT '学科id',
  `res_id` varchar(32) DEFAULT NULL COMMENT '资源id',
  `thesis_title` varchar(100) DEFAULT NULL COMMENT '论文标题',
  `thesis_type` varchar(20) DEFAULT NULL COMMENT '论文类型',
  `file_name` varchar(200) DEFAULT NULL COMMENT '文件名',
  `file_suffix` varchar(10) DEFAULT NULL COMMENT '文件后缀',
  `is_share` int(2) unsigned DEFAULT NULL COMMENT '是否已分享   0：未分享  1：已分享',
  `share_time` datetime DEFAULT NULL COMMENT '分享时间',
  `is_comment` int(2) DEFAULT NULL COMMENT '是否已评论   0：未评论  1：已评论未查看  2：已评论并已查看',
  `crt_id` int(10) DEFAULT NULL COMMENT '创建人id',
  `crt_dttm` datetime DEFAULT NULL COMMENT '创建时间',
  `lastup_id` int(10) DEFAULT NULL COMMENT '最后更新人id',
  `lastup_dttm` datetime DEFAULT NULL COMMENT '最后更新时间',
  `enable` tinyint(1) DEFAULT NULL COMMENT '有效性（0：无效，1：有效）',
  `school_term` int(2) DEFAULT NULL COMMENT '学期：0 上学期，1：下学期',
  `phase_id` int(10) DEFAULT NULL COMMENT '学段id',
  `org_id` int(10) DEFAULT NULL COMMENT '机构id',
  `school_year` smallint(4) DEFAULT NULL COMMENT '当前学年',
  `is_scan` tinyint(1) DEFAULT '0' COMMENT '是否已查阅  0：未  1：已',
  `scan_up` tinyint(1) DEFAULT '0' COMMENT '查阅意见已更新  0：未  1：已',
  `scan_count` int(16) DEFAULT '0' COMMENT '查阅数',
  `down_num` int(10) DEFAULT '0' COMMENT '下载量',
  `is_submit` tinyint(1) DEFAULT '0' COMMENT '是否已提交（0：未提交，1：已提交）',
  `submit_time` datetime DEFAULT NULL COMMENT '提交时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1034 DEFAULT CHARSET=utf8;


DROP PROCEDURE IF EXISTS `proc_solution_org_repair`;
DELIMITER ;;
CREATE PROCEDURE `proc_solution_org_repair`(IN `sid` int,IN `orgid` int)
BEGIN
  declare sysRoleid int;
  declare userId int;
  declare roleId int;
  declare spaceId int; 
  declare newRoleId int;
  declare updateDone int;
	declare done int;
	declare menuCursor CURSOR FOR SELECT `id`,`role_id`,`sys_role_id`,`user_id` FROM sys_user_space where `org_id` = orgid;
	-- 定义结束标记
	declare CONTINUE HANDLER FOR NOT FOUND SET done = 1;
	-- 打开游标
	OPEN menuCursor;
	-- 循环
	menuLoop:LOOP
	-- 取游标中的数据
	FETCH menuCursor INTO spaceId,roleId,sysRoleid,userId;
	IF done = 1 THEN
		LEAVE menuLoop;
	END IF;
		 update sys_user_space set `role_id` = (SELECT `id` from sys_role where `solution_id`=sid and `sys_role_id` = sysRoleid and `is_del`=0 LIMIT 0,1) where `id`=spaceId and  NOT EXISTS (SELECT `id` from sys_role where `solution_id`=sid and `id` = roleId and `is_del`=0);
     select row_count() into updateDone;
		 IF updateDone > 0 THEN
				SELECT `role_id` into newRoleId from sys_user_space where `id`=spaceId;
				update sys_user_menu set sys_role_id = newRoleId where `sys_role_id`=roleId and `user_id`=userId;
     END IF;

	END LOOP menuLoop;
	-- 关闭游标
	CLOSE menuCursor;
END
;;
DELIMITER ;


DROP PROCEDURE IF EXISTS `proc_update_schoolYear`;
DELIMITER ;;
CREATE PROCEDURE `proc_update_schoolYear`(`orgId` int,`schoolYear` int)
BEGIN
  DECLARE editYear INT;
  DECLARE toYear INT;
  set toYear = schoolYear;
	set editYear = toYear-1;
	
	-- 更新用户空间
	delete from sys_user_space where school_year = toYear and org_id = orgId;
	update sys_user_space set school_year = toYear where school_year = editYear and org_id = orgId;

	-- 更新相关表的学年和日期

	update lesson_plan set school_year = toYear,
	submit_time = DATE_ADD(submit_time,INTERVAL '1' YEAR),
	share_time = DATE_ADD(share_time,INTERVAL '1' YEAR),
	crt_dttm = DATE_ADD(crt_dttm,INTERVAL '1' YEAR),
	lastup_dttm = DATE_ADD(lastup_dttm,INTERVAL '1' YEAR)
	where school_year = editYear and org_id = orgId;

	update lesson_info set school_year = toYear,
	submit_time = DATE_ADD(submit_time,INTERVAL '1' YEAR),
	share_time = DATE_ADD(share_time,INTERVAL '1' YEAR),
	crt_dttm = DATE_ADD(crt_dttm,INTERVAL '1' YEAR)
	where school_year = editYear and org_id = orgId;

	update jy_activity set school_year = toYear,
	start_time = DATE_ADD(start_time,INTERVAL '1' YEAR),
	end_time = DATE_ADD(end_time,INTERVAL '1' YEAR),
	create_time = DATE_ADD(create_time,INTERVAL '1' YEAR),
	release_time = DATE_ADD(release_time,INTERVAL '1' YEAR),
	share_time = DATE_ADD(share_time,INTERVAL '1' YEAR)
	where school_year = editYear and org_id = orgId;

	update region_activity set school_year = toYear,
	start_time = DATE_ADD(start_time,INTERVAL '1' YEAR),
	end_time = DATE_ADD(end_time,INTERVAL '1' YEAR),
	create_time = DATE_ADD(create_time,INTERVAL '1' YEAR),
	release_time = DATE_ADD(release_time,INTERVAL '1' YEAR)
	where school_year = editYear and org_id = orgId;

	update school_activity set school_year = toYear,
	start_time = DATE_ADD(start_time,INTERVAL '1' YEAR),
	end_time = DATE_ADD(end_time,INTERVAL '1' YEAR),
	create_time = DATE_ADD(create_time,INTERVAL '1' YEAR),
	release_time = DATE_ADD(release_time,INTERVAL '1' YEAR),
	share_time = DATE_ADD(share_time,INTERVAL '1' YEAR)
	where school_year = editYear and org_id = orgId;

	update lecture_records set school_year = toYear,
	lecture_time = DATE_ADD(lecture_time,INTERVAL '1' YEAR),
	epub_time = DATE_ADD(epub_time,INTERVAL '1' YEAR),
	crt_dttm = DATE_ADD(crt_dttm,INTERVAL '1' YEAR),
	lastup_dttm = DATE_ADD(lastup_dttm,INTERVAL '1' YEAR),
	share_time = DATE_ADD(share_time,INTERVAL '1' YEAR),
	submit_time = DATE_ADD(submit_time,INTERVAL '1' YEAR)
	where school_year = editYear and org_id = orgId;

	update jy_plain_summary set school_year = toYear,
	crt_dttm = DATE_ADD(crt_dttm,INTERVAL '1' YEAR),
	lastup_dttm = DATE_ADD(lastup_dttm,INTERVAL '1' YEAR),
	submit_time = DATE_ADD(submit_time,INTERVAL '1' YEAR),
	punish_time = DATE_ADD(punish_time,INTERVAL '1' YEAR),
	share_time = DATE_ADD(share_time,INTERVAL '1' YEAR)
	where school_year = editYear and org_id = orgId;

	update teaching_thesis set school_year = toYear,
	crt_dttm = DATE_ADD(crt_dttm,INTERVAL '1' YEAR),
	lastup_dttm = DATE_ADD(lastup_dttm,INTERVAL '1' YEAR),
	share_time = DATE_ADD(share_time,INTERVAL '1' YEAR)
	where school_year = editYear and org_id = orgId;

	update school_teach_schedule set school_year = toYear,
	crt_dttm = DATE_ADD(crt_dttm,INTERVAL '1' YEAR),
	lastup_dttm = DATE_ADD(lastup_dttm,INTERVAL '1' YEAR),
	release_time = DATE_ADD(release_time,INTERVAL '1' YEAR)
	where school_year = editYear and org_id = orgId;

	update teacher_record a set a.school_year = toYear,
	a.create_time = DATE_ADD(a.create_time,INTERVAL '1' YEAR),
	a.modify_time = DATE_ADD(a.modify_time,INTERVAL '1' YEAR)
	where a.school_year = editYear and a.user_id in (select b.id from sys_user b where b.org_id = orgId);

	update check_info a set a.school_year = toYear,
	a.createtime = DATE_ADD(a.createtime,INTERVAL '1' YEAR)
	where a.school_year = editYear and a.user_id in (select b.id from sys_user b where b.org_id = orgId);

	update jy_activity_tracks a set a.school_year = toYear,
	a.crt_dttm = DATE_ADD(a.crt_dttm,INTERVAL '1' YEAR),
	a.lastup_dttm = DATE_ADD(a.lastup_dttm,INTERVAL '1' YEAR)
	where a.activity_id in (select b.id from jy_activity b where b.school_year = toYear and b.org_id = orgId );

	update jy_activity_discuss a set a.create_time = DATE_ADD(a.create_time,INTERVAL '1' YEAR) 
	where a.activity_id in (select b.id from jy_activity b where b.school_year = toYear and b.org_id = orgId );

	update region_activity_tracks a set a.school_year = toYear,
	a.crt_dttm = DATE_ADD(a.crt_dttm,INTERVAL '1' YEAR),
	a.lastup_dttm = DATE_ADD(a.lastup_dttm,INTERVAL '1' YEAR)
	where a.activity_id in (select b.id from region_activity b where b.school_year = toYear and b.org_id = orgId );

	update regsch_activity_discuss a set a.crt_dttm = DATE_ADD(a.crt_dttm,INTERVAL '1' YEAR) 
	where a.type_id = 1 and a.activity_id in (select b.id from region_activity b where b.school_year = toYear and b.org_id = orgId );

	update school_activity_tracks a set a.school_year = toYear,
	a.crt_dttm = DATE_ADD(a.crt_dttm,INTERVAL '1' YEAR),
	a.lastup_dttm = DATE_ADD(a.lastup_dttm,INTERVAL '1' YEAR)
	where a.activity_id in (select b.id from school_activity b where b.school_year = toYear and b.org_id = orgId );

	update regsch_activity_discuss a set a.crt_dttm = DATE_ADD(a.crt_dttm,INTERVAL '1' YEAR) 
	where a.type_id = 2 and a.activity_id in (select b.id from school_activity b where b.school_year = toYear and b.org_id = orgId );

END
;;
DELIMITER ;




