CREATE TABLE `t_following_group` (
                                     `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
                                     `userId` bigint DEFAULT NULL COMMENT '用户id',
                                     `name` varchar(50) DEFAULT NULL COMMENT '关注分组名称',
                                     `type` varchar(5) DEFAULT NULL COMMENT '关注分组类型：0特别关注1悄悄关注2默认分组3用户自定义分组',
                                     `createTime` datetime DEFAULT NULL COMMENT '创建时间',
                                     `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
                                     PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户关注分组表';

-- 这里的 userId 是不需要添加的。
insert into `t_following_group` (name, type)
values ("特别关注", 0), ("悄悄关注", 1), ("默认分组", 2);