-- mobile forum mall
-- create database if not exists mfmall default charset utf8 collate utf8_general_ci;

drop table if exists forum;
create table forum (
	id mediumint primary key,

	visits bigint not null default 0 comment '访问量',

	topics int not null default 0 comment '主题数',
	posts_today int not null default 0 comment '今日帖数',		-- 即时累加，定时清0
	posts_yesterday int not null default 0 comment '昨日帖数',	-- 定时更新
	posts int not null default 0 comment '帖子数',
	users int not null default 0 comment '会员数（user.size）',

	newest_user_id int comment '最新会员ID',
	newest_user_name varchar(20) comment '最新会员名称',

	newest_post json comment '最新回复信息'	-- 用于展示与跳转
) comment '论坛统计信息';

drop table if exists `user`;
create table `user` (
	id int auto_increment primary key,

	account varchar(50) not null unique comment '登录帐号',
	psw char(32) not null comment '密码',

	name varchar(20) not null unique comment '名称',	-- 名称不可修改
	sex enum('M', 'F') not null default 'M' comment '性别',
	mobile varchar(20) unique comment '手机号码',
	email varchar(50) unique comment '电子邮箱',
	-- ico varchar(50) not null default '' comment '头像',		-- 图片文件以id命名
	sign varchar(100) comment '签名',

	role enum('ADMIN', 'MEMBER') not null default 'MEMBER' comment '角色：MEMBER=网站会员',
	status enum('NORMAL', 'REMOVED') not null default 'NORMAL' comment '帐号状态',
	created_time datetime not null default current_timestamp comment '注册时间'
) auto_increment = 10000000 comment '用户';

drop table if exists user_statis;
create table user_statis (
	user_id int primary key,

	integral int not null default 0 comment '积分',
	`level` smallint not null default 1 comment '级别',

	topics int not null default 0 comment '主题数量',
	posts int not null default 0 comment '帖子数量'
) comment '用户统计数据';

drop table if exists dict;
create table dict (
	id int primary key,	-- 同一类型下id自增
	type varchar(20) not null comment '字典类型：10000=版块类型',
	name varchar(20) not null default '' comment '名称',

	`index` int not null default 0 comment '顺序索引',

	user_id int not null comment '创建人ID',
	created_time datetime not null default current_timestamp comment '创建时间'
) comment '数据字典';

drop table if exists board;
create table board (
	id mediumint auto_increment primary key,
	name varchar(50) not null comment '名称',
	-- ico varchar(50) not null comment '图标',
	brief varchar(200) not null default '' comment '简介',

	category_id int not null comment '分类',

	topics int not null default 0 comment '主题数',
	posts int not null default 0 comment '帖子数',

	`index` int not null default 0 comment '顺序索引',

	user_id int not null comment '创建人ID',
	user_name varchar(20) not null comment '创建人名称',
	created_time datetime not null default current_timestamp comment '创建时间'
) auto_increment = 10000 comment '版块';

drop table if exists board_master;
create table board_master (
	board_id int primary key comment '版块ID',
	`master` json not null comment '版主信息',	-- {"10000000":"PRIMARY","10000001":"VICE"}
	user_id int not null comment '创建人ID',
	created_time datetime not null default current_timestamp comment '创建时间'
) comment '版主';	-- 一个版块可以有一个版主，多个副版主

drop table if exists board_hot;
create table board_hot (
	board_id int not null comment '版块ID',

	-- 展示效果等配置
	`index` int not null default 0 comment '顺序索引',

	user_id int not null comment '创建人ID',
	created_time datetime not null default current_timestamp comment '创建时间'
) comment '热门版块';

drop table if exists topic;
create table topic (
	id int auto_increment primary key,
	title varchar(50) not null comment '标题',
	content varchar(10000) not null comment '内容',

	visits int not null default 0 comment '查看次数',
	last_visited_time datetime not null default '1970-01-01' comment '最后查看时间',
	posts int not null default 0 comment '回复数量',

	`index` int not null default 0 comment '顺序索引',	-- 用于置顶等

	user_id int not null comment '创建人ID',
	user_name varchar(20) not null comment '创建人名称',
	created_time datetime not null default current_timestamp comment '创建时间',
	last_modified_time datetime not null default current_timestamp comment '最后修改时间'
) auto_increment = 10000000 comment '主题';