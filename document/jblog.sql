/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 80026
Source Host           : localhost:3306
Source Database       : jblog

Target Server Type    : MYSQL
Target Server Version : 80026
File Encoding         : 65001

Date: 2021-10-25 18:03:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for bgms_blog
-- ----------------------------
DROP TABLE IF EXISTS `bgms_blog`;
CREATE TABLE `bgms_blog` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `appreciation` bit(1) NOT NULL COMMENT '是否需要赞赏：0-否，1-是',
  `commentabled` bit(1) NOT NULL COMMENT '是否推荐：默认1，0-否，1-是',
  `title` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL COMMENT '摘要',
  `first_picture` varchar(255) DEFAULT NULL COMMENT '摘要图片',
  `content` text,
  `create_time` datetime(6) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL COMMENT '最后一次更新日期',
  `flag` bit(1) NOT NULL COMMENT '原创还是转载',
  `flag_url` varchar(255) DEFAULT NULL COMMENT '转载地址',
  `recommend` bit(1) NOT NULL COMMENT '是否可以评论：0-否，1-是',
  `share_statement` bit(1) NOT NULL COMMENT '是否可以分享',
  `state` int NOT NULL COMMENT '状态：0-草稿，1-审核中，2-已发布，3-未通过，4-已删除',
  `ums_id` bigint DEFAULT NULL COMMENT 'ums_member的ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of bgms_blog
-- ----------------------------
INSERT INTO `bgms_blog` VALUES ('14', '\0', '\0', 'MyBatis高级结果映射【一对一映射（4种方式）】', '概述\n需求\n方式一：使用自动映射处理一对一映射\n实体类改造\nUserMapper接口增加接口方法\nUserMapper.xml增加SQL\n单元测试\n方式二：使用resultMap配置一对一映射\nUserMapper接口增加接口方法\nUserMapper.xml增加SQL\n单元测试\n方式三：使用resultMap的asscociation标签配置一对一映射\nUserMapper接口增加接口方法\nUserMapper.xml增加SQL\n', null, '\n# 概述\n我们还是继续使用我们的RBAC权限系统来演示。如果不了解RBAC权限系统，可以看看这个学习链接：[RBAC权限系统](https://blog.csdn.net/lvshuocool/article/details/93968143)\n\n假设在RBAC权限系统中，一个用户只能拥有一个角色。\n> SysUser：用户类\n> SysRole：角色类\n> SysRole：用户类\n\n# 需求\n实现在查询用户信息的同时获取用户拥有的角色信息\n\n# 方式一：使用自动映射处理一对一映射\n## 实体类改造\n一个用户拥有一个角色，因此现在SysUser类中增加SysRole字段\n```\n/**\n * \n * \n * @ClassName: SysUser\n * \n * @Description: 用户表\n * \n * @author: Mr.Yang\n * \n * @date: 2018年4月13日 下午9:24:21\n */\npublic class SysUser  {\n\n	//其他原有字段以及setter getter\n\n	/**\n	 * 用户角色\n	 */\n	private SysRole sysRole;\n	\n	// setter getter\n	public SysRole getSysRole() {\n		return sysRole;\n	}\n\n	public void setSysRole(SysRole sysRole) {\n		this.sysRole = sysRole;\n	}\n\n	\n}\n\n```\n使用自动映射就是通过别名让MyBatis自动将值匹配到对应的子弹上，简单的别名映射如user_name 对应userName .\n\n除此之外MyBatis还支持复杂的属性映射，可以多层嵌套。 比如将role.role_name 映射到 role.roleName上。 MyBatis首先会查找role属性，如果存在role属性就创建role对象，然后在role对象中继续查找roleName， 将role_name的值绑定到role对象的roleName属性上 。\n## UserMapper接口增加接口方法\n```\n/**\n	 * \n	 * \n	 * @Title: selectSysUserAndSysRoleById\n	 * \n	 * @Description: 根据Id查询用户信息的同时获取用户拥有的角色信息\n	 * \n	 * @param id\n	 * @return\n	 * \n	 * @return: SysUser\n	 */\n	SysUser selectSysUserAndSysRoleById(Long id);\n\n```\n## UserMapper.xml增加SQL\n```\n<select id=\"selectSysUserAndSysRoleById\" resultType=\"com.artisan.mybatis.xml.domain.SysUser\">\n		SELECT\n			u.id,\n			u.user_name userName,\n			u.user_password userPassword,\n			u.user_email userEmail,\n			u.user_info userInfo,\n			u.create_time createTime,\n			u.head_img headImg,\n			r.id \"sysRole.id\",\n			r.role_name \"sysRole.roleName\",\n			r.enabled \"sysRole.enabled\",\n			r.create_by \"sysRole.createBy\",\n			r.create_time \"sysRole.createTime\"\n		FROM\n			sys_user u\n		INNER JOIN sys_user_role ur ON u.id = ur.user_id\n		INNER JOIN sys_role r ON ur.role_id = r.id\n		WHERE\n			u.id = #{id}\n	</select>\n```\n注意上述SQL中 sys_role查询的列的别名都是 “sysRole.”前缀，这和SysUser实体类中SysRole属性的名称保持一致，通过这种方式将sysRole的属性都映射到了SysUser的sysRole属性上\n。\n\n## 单元测试\n```\n@Test\n	public void selectSysUserAndSysRoleByIdTest() {\n		logger.info(\"selectSysUserAndSysRoleByIdTest\");\n		// 获取SqlSession\n		SqlSession sqlSession = getSqlSession();\n		try {\n			// 获取UserMapper接口\n			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);\n			// 调用selectSysUserAndSysRoleById方法，查询id=1001的用户及其角色\n			SysUser sysUser = userMapper.selectSysUserAndSysRoleById(1001L);\n			// 期望用户不为空\n			Assert.assertNotNull(sysUser);\n			// 期望角色不为空\n			Assert.assertNotNull(sysUser.getSysRole());\n\n			logger.info(sysUser);\n		} catch (Exception e) {\n			e.printStackTrace();\n		} finally {\n			sqlSession.close();\n			logger.info(\"sqlSession close successfully \");\n		}\n	}\n\n```\n通过SQL日志可以看到已经查询出的一条数据，MyBatis将这条数据映射到了两个类中，像这种通过一次查询将结果映射到不同对象的方式，称之为关联的嵌套结果查询。\n\n==关联的嵌套结果映射需要关联多个表将所有需要的值一次性查询出来， 这种方式的好处是减少数据库的查询次数，减轻数据库的压力。 缺点是需要些很复杂的SQL，并且当嵌套结果更负载时，不容易一次写正确。 由于要在服务器上将结果映射到不同的类上，因此也会增加应用服务器的压力。 当一定会使用到嵌套查询，并且整个复杂的SQL执行速度很快时，建议使用关联的其那套结果查询。==\n# 方式二：使用resultMap配置一对一映射\n##  UserMapper接口增加接口方法\n```\n// 使用resultMap配置一对一映射\nSysUser selectSysUserAndSysRoleById2(Long id);\n\n\n```\n## UserMapper.xml增加SQL\n```\n<!-- 使用resultMap配置一对一映射 -->\n	<resultMap id=\"userRoleMap\" \n			   type=\"com.artisan.mybatis.xml.domain.SysUser\">\n		<id column=\"id\" property=\"id\" />\n		<result property=\"userName\" column=\"user_name\" />\n		<result property=\"userPassword\" column=\"user_password\" />\n		<result property=\"userEmail\" column=\"user_email\" />\n		<result property=\"userInfo\" column=\"user_info\" />\n		<result property=\"headImg\" column=\"head_img\" jdbcType=\"BLOB\" />\n		<result property=\"createTime\" column=\"create_time\" jdbcType=\"TIMESTAMP\" />\n		<!-- sysRole相关的属性 -->\n		<result property=\"sysRole.id\" column=\"role_id\"/>\n		<result property=\"sysRole.roleName\" column=\"role_name\"/>\n		<result property=\"sysRole.enabled\" column=\"enabled\"/>\n		<result property=\"sysRole.createBy\" column=\"create_by\"/>\n		<result property=\"sysRole.createTime\" column=\"role_create_time\" jdbcType=\"TIMESTAMP\"/>\n	</resultMap>\n\n\n```\n这种配置和上一个配置相似的地方在于，sysRole中的property配置部分使用“sysRole.”前缀，在column部分，为了避免不同表中存在相同的的字段，所有可能重名的列都加了 “role_”前缀。\n\n这种方式配置的时候，还需要再查询时设置不同的列名，别名和resultMap配置的colunm一致。 然后使用resultMap配置映射。例如下方：r.create_time role_create_time\n```\n<!-- 使用resultMap配置一对一映射 -->\n	<select id=\"selectSysUserAndSysRoleById2\" resultMap=\"userRoleMap\">\n		SELECT\n			u.id,\n			u.user_name ,\n			u.user_password ,\n			u.user_email ,\n			u.user_info ,\n			u.create_time ,\n			u.head_img ,\n			r.id role_id,\n			r.role_name ,\n			r.enabled ,\n			r.create_by ,\n			r.create_time role_create_time \n		FROM\n			sys_user u\n		INNER JOIN sys_user_role ur ON u.id = ur.user_id\n		INNER JOIN sys_role r ON ur.role_id = r.id\n		WHERE\n			u.id = #{id}\n	</select>\n\n\n```\n\n这种方法是不是和第一种使用自动映射处理一对一映射相比起来，resultMap相当麻烦？\n\n事实上 ，resultMap映射是可以被继承的，因此要先简化上面的resultMap的配置。 因为我们这个映射文件中本来就存在一个userMap的映射配置，改造如下：\n```\n<!-- 使用resultMap配置一对一映射  继承原有的resultMap -->\n	<resultMap id=\"userRoleMap_byExtends\"  extends=\"userMap\"\n			   type=\"com.artisan.mybatis.xml.domain.SysUser\">\n		<!-- sysRole相关的属性 -->\n		<result property=\"sysRole.id\" column=\"role_id\"/>\n		<result property=\"sysRole.roleName\" column=\"role_name\"/>\n		<result property=\"sysRole.enabled\" column=\"enabled\"/>\n		<result property=\"sysRole.createBy\" column=\"create_by\"/>\n		<result property=\"sysRole.createTime\" column=\"role_create_time\" jdbcType=\"TIMESTAMP\"/>\n	</resultMap>\n\n```\n使用继承不仅可以简化配置，而且当对主表userMap进行修改时也只需要修改一处。 虽然还不是太方便，至少简洁了一点。 要想更加简洁，只有派上asscociation了，请往下看\n\n# 方式三：使用resultMap的asscociation标签配置一对一映射\n在resultMap中，association标签用于和一个复杂的类型进行关联，即用于一对一的关联配置。\n## UserMapper接口增加接口方法\n```\n// 使用resultMap配置一对一映射 resultMap association\nSysUser selectSysUserAndSysRoleById4(Long id);\n\n```\n## UserMapper.xml增加SQL\n在上面的基础上，再做修改，改成association标签的配置方式。\n```\n	<!-- 使用resultMap配置一对一映射  使用association -->\n	<resultMap id=\"userRoleMap_byExtendsAndAssociation\"  extends=\"userMap\"\n			   type=\"com.artisan.mybatis.xml.domain.SysUser\">\n		<association property=\"sysRole\" columnPrefix=\"sysRole_\"\n			javaType=\"com.artisan.mybatis.xml.domain.SysRole\">\n			<result property=\"id\" column=\"id\"/>\n			<result property=\"roleName\" column=\"role_name\"/>\n			<result property=\"enabled\" column=\"enabled\"/>\n			<result property=\"createBy\" column=\"create_by\"/>\n			<result property=\"createTime\" column=\"create_time\" jdbcType=\"TIMESTAMP\"/>\n		</association>	   \n	</resultMap>\n	\n\n\n<!-- 使用resultMap配置一对一映射 result中Map中使用association  -->\n	<select id=\"selectSysUserAndSysRoleById4\" resultMap=\"userRoleMap_byExtendsAndAssociation\">\n		SELECT\n			u.id,\n			u.user_name ,\n			u.user_password ,\n			u.user_email ,\n			u.user_info ,\n			u.create_time ,\n			u.head_img ,\n			r.id sysRole_id,\n			r.role_name sysRole_role_name,\n			r.enabled sysRole_enabled,\n			r.create_by sysRole_create_by,\n			r.create_time sysRole_create_time\n		FROM\n			sys_user u\n		INNER JOIN sys_user_role ur ON u.id = ur.user_id\n		INNER JOIN sys_role r ON ur.role_id = r.id\n		WHERE\n			u.id = #{id}\n	</select>\n\n```\n因为配置了列的前缀，因此还需要SQL，如上所示。\nassociation标签包含如下主要属性：\n- property:对应实体列中的属性名，必填\n- javaType:属性对应的Java类型\n- resultMap：可以直接使用现有的resultMap,从而不需要在这里配置\n- colunmPrefix: 查询列的前缀，配置前缀后，在子标签配置result的colunm时，可以省略前缀\n- 其他属性，略…\n\n使用association配置还可以使用resultMap属性配置成一个已经存在的resultMap映射。 我们吧sys_role相关的映射提取出来，改造如下：\n```\n	<!-- 使用resultMap配置一对一映射  使用association -->\n	<resultMap id=\"roleMap\"  type=\"com.artisan.mybatis.xml.domain.SysRole\" >\n		<id  property=\"id\" column=\"id\"/>\n		<result property=\"roleName\" column=\"role_name\"/>\n		<result property=\"enabled\" column=\"enabled\"/>\n		<result property=\"createBy\" column=\"create_by\"/>\n		<result property=\"createTime\" column=\"create_time\" jdbcType=\"TIMESTAMP\"/>\n	</resultMap>\n	\n	<resultMap id=\"userRoleMap_byExtendsAndAssociation_ResultMap\"  extends=\"userMap\"\n			   type=\"com.artisan.mybatis.xml.domain.SysUser\">\n		<association property=\"sysRole\" columnPrefix=\"sysRole_\"\n			javaType=\"com.artisan.mybatis.xml.domain.SysRole\" resultMap=\"roleMap\">\n		</association>	   \n	</resultMap>\n\n\n```\n到这里，是不是没有这么麻烦了？ 还有一个需要注意的地方：roleMap我们目前是写在UserMapper.xml中，更合理的应该在RoleMapper.xml中。如果真的在RoleMapper.xml中的话，，通过resultMap来引用的话，就必须要加上命名空间了。 如果不加的话，MyBatis会默认添加调用者当前命名空间的前缀。\n\n```\n<resultMap id=\"userRoleMap_byExtendsAndAssociation_ResultMap\"  extends=\"userMap\"\n			   type=\"com.artisan.mybatis.xml.domain.SysUser\">\n		<association property=\"sysRole\" columnPrefix=\"sysRole_\"\n			javaType=\"com.artisan.mybatis.xml.domain.SysRole\" resultMap=\"com.artisan.mybatis.xml.mapper.RoleMapper.roleMap\">\n		</association>	   \n	</resultMap>\n\n\n```\n# 方式四：asscociation标签的嵌套查询\n前面三种方式通过负载的SQL查询获取结果，其实还可以利用简单的SQL通过多次查询转换为我们需要的结果，这种方式与根据业务逻辑手动执行多次SQL的方式很像，最后将结果组成一个对象。\n\n场景和情况比较复杂，后续单独阐述\n\n\n', '2021-10-04 15:31:22.642000', '2021-10-04 15:47:48.849000', '\0', 'https://artisan.blog.csdn.net/article/details/80147013', '\0', '', '2', '1');
INSERT INTO `bgms_blog` VALUES ('15', '\0', '\0', 'Mybatis中的 ${} 和 #{}区别与用法', 'Mybatis 的Mapper.xml语句中parameterType向SQL语句传参有两种方式：#{}和${}\n我们经常使用的是#{},一般解说是因为这种方式可以防止SQL注入，简单的说#{}这种方式SQL语句是经过预编译的，它是把#{}中间的参数转义成字符串\n', null, 'Mybatis 的Mapper.xml语句中parameterType向SQL语句传参有两种方式：#{}和${}\n\n我们经常使用的是#{},一般解说是因为这种方式可以防止SQL注入，简单的说#{}这种方式SQL语句是经过预编译的，它是把#{}中间的参数转义成字符串，举个例子：\n\nselect * from student where student_name = #{name} \n\n预编译后,会动态解析成一个参数标记符?：\n\nselect * from student where student_name = ?\n\n而使用${}在动态解析时候，会传入参数字符串\n\nselect * from student where student_name = \'lyrics\'\n\n总结：\n> #{} 这种取值是编译好SQL语句再取值\n> ${} 这种是取值以后再去编译SQL语句\n\n- #{}方式能够很大程度防止sql注入。\n- $方式无法防止Sql注入。\n- $方式一般用于传入数据库对象，例如传入表名.\n- 一般能用#的就别用$.\n\n例如：\n```\n举个例子：\nselect * from ${prefix} ACT_HI_PROCINST where PROC_INST_ID_ = #{processInstanceId}\n\n```\n\n\n', '2021-10-04 15:53:25.474000', '2021-10-04 15:53:25.474000', '\0', 'https://blog.csdn.net/j04110414/article/details/78914787', '\0', '', '2', '1');
INSERT INTO `bgms_blog` VALUES ('16', '\0', '\0', 'MyBatis动态SQL之【foreach】', '概述\nforeach实现in集合\n1.需求\n2.UserMapper接口增加接口方法\n3.UserMapper.xml增加动态SQL\n4.单元测试\n', null, 'SQL语句中有时候会使用IN关键字，比如 id in (1,2,3,4)。\n\n虽然可以使用${ids}方式直接获取值，但${ids}不能防止SQL注入， 想要避免SQL注入就需要用#{}的方式，这时就要配合使用foreach标签来满足需求.\n\nforeach可以对数组、Map或者实现了Iterable接口（比如List、Set）的对象进行遍历。 数组在处理的时候可以转换为List对象。 因此foreach遍历的对象可以分为两大类\n\n- Iterable类型\n- Map类型。\n\n这两种类型在遍历循环时情况是不一样的，我们通过如下3个示例来讲解foreach的用法\n\n# foreach实现in集合\nforeach实现in集合（或者数组）是最简单和常见的一种情况\n## 1.需求\n根据id集合查出所有符合条件的用户\n## 2.UserMapper接口增加接口方法\n```\n/**\n     * \n     * \n     * @Title: selectSysUserByIdList\n     * \n     * @Description: 根据用户ID集合查询用户\n     * \n     * @param ids\n     * @return\n     * \n     * @return: List<SysUser>\n     */\n    List<SysUser> selectSysUserByIdList(List<Long> ids);\n\n```\n## 3.UserMapper.xml增加动态SQL\n```\n    <select id=\"selectSysUserByIdList\" resultType=\"com.artisan.mybatis.xml.domain.SysUser\">\n        SELECT\n            a.id,\n            a.user_name userName,\n            a.user_password userPassword,\n            a.user_email userEmail,\n            a.user_info userInfo,\n            a.head_img headImg,\n            a.create_time createTime\n        FROM\n            sys_user a\n        WHERE id in \n            <foreach collection=\"list\" item=\"userId\" open=\"(\" close=\")\" separator=\",\" index=\"i\">\n                #{userId}\n            </foreach>\n    </select>\n\n```\nforeach的属性\n\n- collection 必填，值为要迭代循环的属性名。 情况有很多种\n- item 变量名，值为从迭代对象中取出的每一个值\n- index 索引的属性名，在集合数组请鲁昂下为当前索引值，的那个迭代循环的对象是Map类型时，这个值为Map的key(键值）\n- open 整个循环内容开头的字符串\n- close 整个循环内容结尾的字符串\n- separator 每次循环的分隔符\n\n## 4.单元测试\n```\n    @Test\n    public void selectSysUserByIdListTest() {\n        logger.info(\"selectSysUserByIdListTest\");\n        // 获取SqlSession\n        SqlSession sqlSession = getSqlSession();\n        try {\n            // 获取UserMapper接口\n            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);\n            // 模拟idList\n            List<Long> idList = new ArrayList<Long>();\n            idList.add(1L);\n            idList.add(1001L);\n            // 调用接口方法\n            List<SysUser> userList = userMapper.selectSysUserByIdList(idList);\n            // userList不为空\n            Assert.assertNotNull(userList);\n            // userList > 0\n            Assert.assertTrue(userList.size() > 0);\n            // 期望返回2条数据，符合数据库中记录\n            Assert.assertEquals(2, userList.size());\n            logger.info(userList);\n        } catch (Exception e) {\n            e.printStackTrace();\n        } finally {\n            sqlSession.close();\n            logger.info(\"sqlSession close successfully \");\n        }\n    }\n\n\n```\n\n\n# foreach实现批量插入\n## 前提\n如果数据库支持批量插入，就可以通过foreach实现。 批量插入是SQL-92新增的特性，目前支持的数据库有DB2、SQL Server 2008+、PostgreSql8.2+、MySQL、SQLite3.7.11+ 以及H2.\n\n语法\n```\ninsert into tablename(column-a,[column-b,....])\n    values(\'value-1a\',[\'value-1b\',...]),\n    (\'value-2a\',[\'value-2b\',...]),\n    (\'value-3a\',[\'value-3b\',...]),\n    ......\n\n```\n从上述语法部分可以看到，后面是一个值的循环，因此可以通过foreach来实现循环插入。\n## 1.需求\n批量插入用户\n## 2.UserMapper接口增加接口方法\n```\n/**\n     * \n     * \n     * @Title: insertSysUserList\n     * \n     * @Description: 批量新增用户\n     * \n     * @param sysUserList\n     * @return\n     * \n     * @return: int\n     */\n    int insertSysUserList(List<SysUser> sysUserList);\n\n```\n\n## 3.UserMapper.xml增加动态SQL\n```\n<insert id=\"insertSysUserList\"  keyProperty=\"id\" useGeneratedKeys=\"true\">\n        insert into sys_user(\n            user_name, \n            user_password, \n            user_email, \n            user_info, \n            head_img, \n            create_time)\n        values\n        <foreach collection=\"list\" item=\"sysUser\" separator=\",\">\n            (\n                #{sysUser.userName}, \n                #{sysUser.userPassword}, \n                #{sysUser.userEmail}, \n                #{sysUser.userInfo}, \n                #{sysUser.headImg, jdbcType=BLOB},\n                #{sysUser.createTime, jdbcType=TIMESTAMP}\n            )\n        </foreach>\n    </insert>\n\n```\n通过item指定了循环变量名后，在引用值的时候使用的是“属性.属性”的方式，如上所示sysUser.userName。\n\n## 4.单元测试\n```\n@Test\n    public void insertSysUserListTest() {\n        logger.info(\"insertSysUserListTest\");\n        // 获取SqlSession\n        SqlSession sqlSession = getSqlSession();\n        try {\n            // 获取UserMapper接口\n            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);\n\n            // 模拟userList\n            List<SysUser> userList = new ArrayList<SysUser>();\n\n            for (int i = 0; i < 5; i++) {\n                SysUser sysUser = new SysUser();\n                sysUser.setUserName(\"artisanTest_\" + i);\n                sysUser.setUserPassword(\"123456_\" + i);\n                sysUser.setUserEmail(\"artisan_\" + i + \"@artisan.com\");\n                sysUser.setUserInfo(\"测试用户\" + i);\n                // 模拟头像\n                sysUser.setHeadImg(new byte[] { 1, 2, 3 });\n                sysUser.setCreateTime(new Date());\n\n                // 添加到SysUser\n                userList.add(sysUser);\n            }\n\n            // 新增用户 ,返回受影响的行数\n            int result = userMapper.insertSysUserList(userList);\n            // 返回批量的自增主键 配合 keyProperty=\"id\" useGeneratedKeys=\"true\" 这两个属性\n            for (SysUser sysUser : userList) {\n                logger.info(sysUser.getId());\n            }\n            // 只插入一条数据 ,期望是5\n            Assert.assertEquals(5, result);\n\n            // 重新查询\n            List<SysUser> sysUserList = userMapper.selectAll();\n            // 根据数据库之前的2条记录，加上本次新增的5条（虽未提交但还是在一个会话中，所以可以查询的到）\n            Assert.assertNotNull(sysUserList);\n            Assert.assertEquals(7, sysUserList.size());\n\n        } catch (Exception e) {\n            e.printStackTrace();\n        } finally {\n            // 为了保持测试数据的干净，这里选择回滚\n            // 由于默认的sqlSessionFactory.openSession()是不自动提交的\n            // 除非显式的commit，否则不会提交到数据库\n            sqlSession.rollback();\n            logger.info(\"为了保持测试数据的干净，这里选择回滚,不写入mysql,请观察日志，回滚完成\");\n\n            sqlSession.close();\n            logger.info(\"sqlSession close successfully \");\n        }\n    }\n\n```\n\n\n# foreach实现动态update\n这部分我们主要介绍当参数类型是Map的时候，foreach如何实现动态UPDATE\n\n当参数是Map类型的时候，foreach标签的index属性值对应的不是索引值，而是Map中的key, 利用这个key就可以动态实现UPDATE了。\n例如：Map中的key对应标的字段名称，value对应表中当前字段的数据，通过动态的sql实现update\n## 不使用@Param注解指定参数名的情况\n### 1.UserMapper接口\n```\nvoid updateSysUserByMap(Map<String, Object> map);\n\n```\n这里没有使用@Parma注解指定参数名，因而MyBatis在内部的上线文中使用默认值 _parameter 最为该参数的key ,所以xml中也必须使用_parameter。\n###  2.UserMapper.xml动态SQL\n```\n<update id=\"updateSysUserByMap\">\n        update sys_user \n        set \n        <foreach collection=\"_parameter\"  item=\"value\"  index=\"key\"  separator=\",\">\n            ${key} = #{value}\n        </foreach>\n        where id = #{id}\n    </update>\n\n```\n这里的key作为列名，对应的值作为该列的值，通过foreach将需要更新的字段拼接在SQL语句中。\n\n## 使用@Param注解指定参数名的情况\n### 1.UserMapper接口\n```\nvoid updateSysUserByMapWithParam(@Param(\"userMap\") Map<String, Object> map);\n\n```\n### 2.UserMapper.xml动态SQL\n```\n<update id=\"updateSysUserByMapWithParam\">\n        update sys_user \n        set \n        <foreach collection=\"userMap\"  item=\"value\"  index=\"key\"  separator=\",\">\n            ${key} = #{value}\n        </foreach>\n        where id = #{userMap.id}\n    </update>\n\n```\n### 3.单元测试\n```\n@Test\n    public void updateSysUserByMapTest() {\n        logger.info(\"updateSysUserByMapTest\");\n        // 获取SqlSession\n        SqlSession sqlSession = getSqlSession();\n        try {\n            // 获取UserMapper接口\n            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);\n\n            // 模拟Map\n            Map<String, Object> userMap = new HashMap<String, Object>();\n            // 查询条件，同时也是where后面的更新字段， 必须存在\n            userMap.put(\"id\", 1L);\n            // 更新其他字段\n            userMap.put(\"user_email\", \"map@artisan.com\");\n            userMap.put(\"user_name\", \"ARTISAN_ADMIN\");\n\n            // 调用接口,更新数据\n            // userMapper.updateSysUserByMap(userMap);\n            // 或者\n            userMapper.updateSysUserByMapWithParam(userMap);\n\n            // 根据当前id 查询用户\n            SysUser sysUser = userMapper.selectSysUserById(1L);\n            Assert.assertEquals(\"map@artisan.com\", sysUser.getUserEmail());\n            Assert.assertEquals(\"ARTISAN_ADMIN\", sysUser.getUserName());\n\n        } catch (Exception e) {\n            e.printStackTrace();\n        } finally {\n            // 为了保持测试数据的干净，这里选择回滚\n            // 由于默认的sqlSessionFactory.openSession()是不自动提交的\n            // 除非显式的commit，否则不会提交到数据库\n            sqlSession.rollback();\n            logger.info(\"为了保持测试数据的干净，这里选择回滚,不写入mysql,请观察日志，回滚完成\");\n\n            sqlSession.close();\n            logger.info(\"sqlSession close successfully \");\n        }\n    }\n\n```\n\n', '2021-10-04 16:06:06.552000', '2021-10-04 16:06:06.552000', '\0', 'https://artisan.blog.csdn.net/article/details/80045288', '\0', '', '2', '1');
INSERT INTO `bgms_blog` VALUES ('17', '\0', '\0', 'MyBatis代码生成器（逆向工程）MBG使用', '', null, '# 概述\n我们前面的博文中了解了MyBatis的基本用法，也写了很多单表的CRUD方法，基本上每个表都需要用到这些方法，这些方法都很规范而且比较类似。\n\n当数据库表的字段较少时，写起来还能接受，一旦字段过多或者需要在很多个表中写这些基本方法的时候，是不是很崩溃？\n\nMyBatis开发团队提供了一个很强大的代码生成器—MyBatis Generator (MBG).\n\nMBG通过丰富的配置可以生成不同类型的代码，代码包含了数据库表对应的实体类、Mapper接口类、Mapper XML文件和 Example对象等。 这些代码文件几乎包含了全部的单表操作方法。\n\n使用MBG可以极大程度上方便我们使用MyBatis，减少很多重复操作。\n\n# 参考配置实例\n在项目的src/main/resources中创建一个generator目录，在该目录下创建一个generatorConfig.xml文件\n\n内容如下：\n```\n<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE generatorConfiguration\n        PUBLIC \"-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN\"\n        \"http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd\">\n\n<!-- 配置生成器 -->\n<generatorConfiguration> \n     <!-- 引用外部配置文件 -->\n     <properties resource=\"db.properties\" />\n    <!-- \n        在MBG工作的时候，需要额外加载的依赖包location属性指明加载jar/zip包的全路径 -->\n    <!--\n        <classPathEntry location=\"F:\\.m2\\repository\\mysql\\mysql-connector-java\\5.1.38\\mysql-connector-java-5.1.38.jar\"/>\n     -->\n\n    <!-- \n        id:必选，上下文id，用于在生成错误时提示\n        targetRuntime:如果设置为MyBatis3，会自动生成Example相关的代码，如果不需要可以设置为MyBatis3Simple\n             -MyBatis3：默认的值，生成基于MyBatis3.x以上版本的内容，包括XXXBySample；\n             -MyBatis3Simple：类似MyBatis3，只是不生成XXXBySample；\n        defaultModelType:指定生成对象的样式设置为flat,目的是只使一个表生成一个实体类。 当没有复杂的继承的时候，使用比较方便。选项如下\n            -conditional：类似hierarchical；\n            -flat：所有内容（主键，blob）等全部生成在一个对象中；\n            -hierarchical：主键生成一个XXKey对象(key class)，Blob等单独生成一个对象，其他简单属性在一个对象中(record class)\n     -->\n    <context id=\"MySqlContext\" targetRuntime=\"MyBatis3\" defaultModelType=\"flat\">\n        <!-- beginningDelimiter/endingDelimiter: 指明数据库的用于标记数据库对象名的符号，比如ORACLE是双引号，MYSQL默认是`反引号  -->\n        <property name=\"beginningDelimiter\" value=\"`\"/>\n        <property name=\"endingDelimiter\" value=\"`\"/>\n        <!-- 生成的Java文件的编码 -->\n        <property name=\"javaFileEncoding\" value=\"UTF-8\"/>\n        <!-- 格式化java代码 \n            <property name=\"javaFormatter\" value=\"org.mybatis.generator.api.dom.DefaultJavaFormatter\"/>\n        -->\n        <!-- 格式化XML代码\n            <property name=\"xmlFormatter\" value=\"org.mybatis.generator.api.dom.DefaultXmlFormatter\"/>\n         -->\n        <!-- commentGenerator还可以配置一个type，设置自己的注解生成器，\n            默认使用的是org.mybatis.generator.internal.DefaultCommentGenerator -->\n        <commentGenerator>\n            <!-- suppressDate是去掉生成日期那行注释，suppressAllComments是去掉所有的注解 -->\n            <property name=\"suppressDate\" value=\"true\"/>\n            <!-- 在生成的实体类中附带表字段的注释  MBG1.3.3中新增的功能 -->\n            <property name=\"addRemarkComments\" value=\"true\"/>\n        </commentGenerator>\n\n\n        <!-- 必须存在，使用这个配置链接数据库-->\n        <jdbcConnection driverClass=\"${jdbc.driver}\"\n                        connectionURL=\"${jdbc.url}\"\n                        userId=\"${jdbc.username}\"\n                        password=\"${jdbc.password}\">\n              <!-- 这里面可以设置property属性，每一个property属性都设置到配置的Driver上 -->\n        </jdbcConnection>\n\n        <!-- 默认false，把JDBC DECIMAL 和 NUMERIC 类型解析为 Integer，为 true时把JDBC DECIMAL 和 \n            NUMERIC 类型解析为java.math.BigDecimal \n        <javaTypeResolver>\n            <property name=\"forceBigDecimals\" value=\"false\" />\n        </javaTypeResolver>\n        -->\n\n        <!-- java模型创建器，是必须要的元素\n                   负责：1，key类（见context的defaultModelType）；2，java类；3，查询类\n                    targetPackage：生成的类要放的包，真实的包受enableSubPackages属性控制；\n                    targetProject：目标项目，指定一个存在的目录下，生成的内容会放到指定目录中，如果目录不存在，MBG不会自动建目录\n        -->\n        <javaModelGenerator targetPackage=\"test.model\" targetProject=\"src\\main\\java\">\n            <!-- 设置是否在getter方法中，对String类型字段调用trim()方法 -->\n            <property name=\"trimStrings\" value=\"true\" />\n            <!-- 设置一个根对象， 如果设置了这个根对象，那么生成的keyClass或者recordClass会继承这个类；在Table的rootClass属性中可以覆盖该选项 \n                注意：如果在key class或者record class中有root class相同的属性，MBG就不会重新生成这些属性了，包括： 1，属性名相同，类型相同，有相同的getter/setter方法；\n                如果生成对象生成的类型或者getter和setter方法在RootClass中存在,则不会自动生成和覆盖 -->\n            <property name=\"rootClass\" value=\"com.artisan.mybatis.simple.model.BaseEntity\" />\n\n            <!-- for MyBatis3/MyBatis3Simple 自动为每一个生成的类创建一个构造方法，构造方法包含了所有的field；而不是使用setter； \n                <property name=\"constructorBased\" value=\"false\"/> -->\n            <!-- 在targetPackage的基础上，根据数据库的schema再生成一层package，最终生成的类放在这个package下，默认为false \n                <property name=\"enableSubPackages\" value=\"true\"/> -->\n            <!-- for MyBatis3 / MyBatis3Simple 是否创建一个不可变的类，如果为true， 那么MBG会创建一个没有setter方法的类，取而代之的是类似constructorBased的类 \n                <property name=\"immutable\" value=\"false\"/> -->\n        </javaModelGenerator>\n\n\n        <!-- 生成SQL map的XML文件生成器，\n         注意，在Mybatis3之后，我们可以使用mapper.xml文件+Mapper接口（或者不用mapper接口），\n              或者只使用Mapper接口+Annotation，所以，如果 javaClientGenerator配置中配置了需要生成XML的话，这个元素就必须配置\n             targetPackage/targetProject:同javaModelGenerator\n         -->\n        <sqlMapGenerator targetPackage=\"test.xml\"  targetProject=\"src\\main\\resources\">\n            <!-- 在targetPackage的基础上，根据数据库的schema再生成一层package，最终生成的类放在这个package下，默认为false \n                <property name=\"enableSubPackages\" value=\"true\"/> -->\n        </sqlMapGenerator>\n\n        <!-- 对于mybatis来说，即生成Mapper接口，注意，如果没有配置该元素，那么默认不会生成Mapper接口 \n        targetPackage/targetProject:同javaModelGenerator\n        type：选择怎么生成mapper接口（在MyBatis3/MyBatis3Simple下）：\n            1，ANNOTATEDMAPPER：会生成使用Mapper接口+Annotation的方式创建（SQL生成在annotation中），不会生成对应的XML；\n            2，MIXEDMAPPER：使用混合配置，会生成Mapper接口，并适当添加合适的Annotation，但是XML会生成在XML中；\n            3，XMLMAPPER：会生成Mapper接口，接口完全依赖XML；\n            注意，如果context是MyBatis3Simple：只支持ANNOTATEDMAPPER和XMLMAPPER\n          -->\n        <javaClientGenerator type=\"XMLMAPPER\" targetPackage=\"test.dao\"  targetProject=\"src\\main\\java\">\n            <!-- 在targetPackage的基础上，根据数据库的schema再生成一层package，最终生成的类放在这个package下，默认为false \n                <property name=\"enableSubPackages\" value=\"true\"/> -->\n            <!-- 可以为所有生成的接口添加一个父接口，但是MBG只负责生成，不负责检查 <property name=\"rootInterface\" value=\"\"/> -->\n        </javaClientGenerator>\n\n\n\n        <!-- 选择一个table来生成相关文件，可以有一个或多个table，必须要有table元素\n                选择的table会生成一下文件：\n                1，SQL map文件\n                2，生成一个主键类；\n                3，除了BLOB和主键的其他字段的类；\n                4，包含BLOB的类；\n                5，一个用户生成动态查询的条件类（selectByExample, deleteByExample），可选；\n                6，Mapper接口（可选）\n\n                tableName（必要）：要生成对象的表名；\n                    注意：大小写敏感问题。正常情况下，MBG会自动的去识别数据库标识符的大小写敏感度，在一般情况下，MBG会\n                    根据设置的schema，catalog或tablename去查询数据表，按照下面的流程：\n                    1，如果schema，catalog或tablename中有空格，那么设置的是什么格式，就精确的使用指定的大小写格式去查询；\n                    2，否则，如果数据库的标识符使用大写的，那么MBG自动把表名变成大写再查找；\n                    3，否则，如果数据库的标识符使用小写的，那么MBG自动把表名变成小写再查找；\n                    4，否则，使用指定的大小写格式查询；\n                另外的，如果在创建表的时候，使用的\"\"把数据库对象规定大小写，就算数据库标识符是使用的大写，在这种情况下也会使用给定的大小写来创建表名；\n                这个时候，请设置delimitIdentifiers=\"true\"即可保留大小写格式；\n\n                可选：\n                1，schema：数据库的schema；\n                2，catalog：数据库的catalog；\n                3，alias：为数据表设置的别名，如果设置了alias，那么生成的所有的SELECT SQL语句中，列名会变成：alias_actualColumnName\n                4，domainObjectName：生成的domain类的名字，如果不设置，直接使用表名作为domain类的名字；可以设置为somepck.domainName，那么会自动把domainName类再放到somepck包里面；\n                5，enableInsert（默认true）：指定是否生成insert语句；\n                6，enableSelectByPrimaryKey（默认true）：指定是否生成按照主键查询对象的语句（就是getById或get）；\n                7，enableSelectByExample（默认true）：MyBatis3Simple为false，指定是否生成动态查询语句；\n                8，enableUpdateByPrimaryKey（默认true）：指定是否生成按照主键修改对象的语句（即update)；\n                9，enableDeleteByPrimaryKey（默认true）：指定是否生成按照主键删除对象的语句（即delete）；\n                10，enableDeleteByExample（默认true）：MyBatis3Simple为false，指定是否生成动态删除语句；\n                11，enableCountByExample（默认true）：MyBatis3Simple为false，指定是否生成动态查询总条数语句（用于分页的总条数查询）；\n                12，enableUpdateByExample（默认true）：MyBatis3Simple为false，指定是否生成动态修改语句（只修改对象中不为空的属性）；\n                13，modelType：参考context元素的defaultModelType，相当于覆盖；\n                14，delimitIdentifiers：参考tableName的解释，注意，默认的delimitIdentifiers是双引号，如果类似MYSQL这样的数据库，使用的是`（反引号，那么还需要设置context的beginningDelimiter和endingDelimiter属性）\n                15，delimitAllColumns：设置是否所有生成的SQL中的列名都使用标识符引起来。默认为false，delimitIdentifiers参考context的属性\n\n                注意，table里面很多参数都是对javaModelGenerator，context等元素的默认属性的一个复写；\n        -->\n\n        <!-- % 通配符 匹配数据库中所有的表 -->\n        <table tableName=\"%\">\n            <!-- generatedKey用于生成生成主键的方法，\n                    如果设置了该元素，MBG会在生成的<insert>元素中生成一条正确的<selectKey>元素，该元素可选\n                    column:主键的列名；\n                    sqlStatement：要生成的selectKey语句，有以下可选项：\n                        Cloudscape:相当于selectKey的SQL为： VALUES IDENTITY_VAL_LOCAL()\n                        DB2       :相当于selectKey的SQL为： VALUES IDENTITY_VAL_LOCAL()\n                        DB2_MF    :相当于selectKey的SQL为：SELECT IDENTITY_VAL_LOCAL() FROM SYSIBM.SYSDUMMY1\n                        Derby     :相当于selectKey的SQL为：VALUES IDENTITY_VAL_LOCAL()\n                        HSQLDB    :相当于selectKey的SQL为：CALL IDENTITY()\n                        Informix  :相当于selectKey的SQL为：select dbinfo(\'sqlca.sqlerrd1\') from systables where tabid=1\n                        MySql     :相当于selectKey的SQL为：SELECT LAST_INSERT_ID()\n                        SqlServer :相当于selectKey的SQL为：SELECT SCOPE_IDENTITY()\n                        SYBASE    :相当于selectKey的SQL为：SELECT @@IDENTITY\n                        JDBC      :相当于在生成的insert元素上添加useGeneratedKeys=\"true\"和keyProperty属性\n             -->\n            <generatedKey column=\"id\" sqlStatement=\"MySql\"/>\n\n            <!-- 参考 javaModelGenerator 的 constructorBased属性\n            <property name=\"constructorBased\" value=\"false\"/>-->\n\n            <!-- 默认为false，如果设置为true，在生成的SQL中，table名字不会加上catalog或schema；\n            <property name=\"ignoreQualifiersAtRuntime\" value=\"false\"/> -->\n\n            <!-- 参考 javaModelGenerator 的 immutable 属性 \n            <property name=\"immutable\" value=\"false\"/>-->\n\n            <!-- 指定是否只生成domain类，如果设置为true，只生成domain类，如果还配置了sqlMapGenerator，那么在mapper XML文件中，只生成resultMap元素\n            <property name=\"modelOnly\" value=\"false\"/> -->\n\n            <!-- 参考 javaModelGenerator 的 rootClass 属性 \n            <property name=\"rootClass\" value=\"\"/>\n             -->\n\n            <!-- 参考javaClientGenerator 的  rootInterface 属性\n            <property name=\"rootInterface\" value=\"\"/>\n            -->\n\n            <!-- 如果设置了runtimeCatalog，那么在生成的SQL中，使用该指定的catalog，而不是table元素上的catalog \n            <property name=\"runtimeCatalog\" value=\"\"/>\n            -->\n\n            <!-- 如果设置了runtimeSchema，那么在生成的SQL中，使用该指定的schema，而不是table元素上的schema \n            <property name=\"runtimeSchema\" value=\"\"/>\n            -->\n\n            <!-- 如果设置了runtimeTableName，那么在生成的SQL中，使用该指定的tablename，而不是table元素上的tablename \n            <property name=\"runtimeTableName\" value=\"\"/>\n            -->\n\n            <!-- 注意，该属性只针对MyBatis3Simple有用；\n                    如果选择的runtime是MyBatis3Simple，那么会生成一个SelectAll方法，如果指定了selectAllOrderByClause，那么会在该SQL中添加指定的这个order条件；\n\n            <property name=\"selectAllOrderByClause\" value=\"age desc,username asc\"/>-->\n\n            <!-- 如果设置为true，生成的model类会直接使用column本身的名字，而不会再使用驼峰命名方法，比如BORN_DATE，生成的属性名字就是BORN_DATE,而不会是bornDate\n            <property name=\"useActualColumnNames\" value=\"false\"/> -->\n\n            <!-- ignoreColumn设置一个MGB忽略的列，如果设置了改列，那么在生成的domain中，生成的SQL中，都不会有该列出现 \n            column:指定要忽略的列的名字；\n            delimitedColumnName：参考table元素的delimitAllColumns配置，默认为false\n\n               注意，一个table元素中可以有多个ignoreColumn元素\n            <ignoreColumn column=\"deptId\" delimitedColumnName=\"\"/>\n            -->\n        </table>\n     </context>\n</generatorConfiguration>\n\n```\n# 运行 MyBatis Generator\n常用的有如下方式：\n\n- 使用Java编写运行代码\n- 从命令提示符运行\n- 使用Maven Plugin运行\n- 使用Eclipse插件运行\n我们这里使用 第一种方式 Java编写代码运行\n\n第一步： 添加Maven依赖\n```\n<dependency>\n   <groupId>org.mybatis.generator</groupId>\n    <artifactId>mybatis-generator-core</artifactId>\n    <version>${mybatis-generator-core.version}</version>\n</dependency>\n\n```\n第二步： 编写Java代码\n```\npackage com.mybatis.generator;\n\nimport java.io.InputStream;\nimport java.util.ArrayList;\nimport java.util.List;\n\nimport org.mybatis.generator.api.MyBatisGenerator;\nimport org.mybatis.generator.config.Configuration;\nimport org.mybatis.generator.config.xml.ConfigurationParser;\nimport org.mybatis.generator.internal.DefaultShellCallback;\n\n/**\n * \n * \n * @ClassName: Generator\n * \n * @Description: 读取 MBG 配置生成代码\n * \n * @author: Mr.Yang\n * \n * @date: 2018年4月27日 下午4:31:42\n */\npublic class Generator {\n\n    public static void main(String[] args) throws Exception {\n        // MBG 执行过程中的警告信息\n        List<String> warnings = new ArrayList<String>();\n        // 当生成的代码重复时，覆盖原代码\n        boolean overwrite = true;\n        // 读取MBG配置文件\n        InputStream is = Generator.class.getResourceAsStream(\"/generator/generatorConfig.xml\");\n        ConfigurationParser cp = new ConfigurationParser(warnings);\n        Configuration config = cp.parseConfiguration(is);\n        is.close();\n\n        DefaultShellCallback callback = new DefaultShellCallback(overwrite);\n        // 创建 MBG\n        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);\n        // 执行生成代码\n        myBatisGenerator.generate(null);\n        // 输出警告信息\n        for (String warning : warnings) {\n            System.out.println(warning);\n        }\n    }\n\n}\n\n\n```\n使用Java编码方式运行的好处是，generatorConfig.xml配置的一些特殊的类（比如commentGenerator标签中type属性配置的MyCommentGenerator）只要在当前项目中，或者在当前项目的Classpath中，就可以直接使用。 使用其他方式的时候都需要特别配置才能在MBG执行过程中找到MyCommentGenerator类并实例化，否则会抛出异常。\n\n不便之处在于，它和当前项目是绑定在一起的， 唉maven多子模块的情况下，可能需要增加代码量和配置量，配置多个，管理不方便。\n\n但是总和来说，这种方式出现的问题最少，配置最为容易，因此推荐使用。\n\n运行后生成的代码如下结构：\n![mybatis的MGB结构描述](https://img-blog.csdn.net/20180428091508259?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3lhbmdzaGFuZ3dlaQ==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)', '2021-10-04 16:11:34.761000', '2021-10-04 16:11:34.761000', '\0', 'https://artisan.blog.csdn.net/article/details/80115361', '\0', '\0', '2', '1');
INSERT INTO `bgms_blog` VALUES ('19', '\0', '\0', 'Tomcat部署Java服务', '在eclipse上开发好之后，如何将服务部署到Tomcat上呢？', null, '在eclipse上开发好之后，如何将服务部署到Tomcat上呢？\n\r\n在web项目的WEB-INF目录下，创建sun-jaxws.xml配置文件，添加内容如下:相关类名及引用，根据自己项目的名称进行修改 \n\r\n\r\n<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\r\n    <endpoints xmlns=\"http://java.sun.com/xml/ns/jax-ws/ri/runtime\" version=\"2.0\">\n\r\n    <endpoint name=\"TestWebService\" implementation=\"com.lex.webservice.TestWebService\" url-pattern=\"/TestWebService\" />\n\r\n</endpoints>\n\r\n\r\n3.修改web.xml配置文件\n\r\n修改web.xml文件，如下图所示，注意listener监听器 及servlet引用，来自我们添加的jar包，如果jar包没有引用，则启动服务时，会报错。\n\r\n5.放入tomcat的tomcat7.0.88webapps文件夹中\n\r\n6.将相关引用的jar包，放入tomcat服务器的tomcat7.0.88lib 文件夹中\n\r\n7.启动tomcat服务器，通过tomcat配置的端口，即可访问服务；我配置的端口为8081\n', '2021-10-04 15:31:22.642000', '2021-10-04 19:47:48.000000', '\0', null, '\0', '\0', '2', '0');
INSERT INTO `bgms_blog` VALUES ('20', '\0', '\0', '一、java语言的特点', '一、java语言的特点', null, '一、java语言的特点\n\r\n1、面向对象\n\r\njava语言是面向对象语言，他和面向过程C不同点是，有封装、继承、多态，万物皆是对象，但是执行效率面向过程稍微快些。\n\r\n2、垃圾回收\n\r\nJVM垃圾自动回收，即GC操作（后面会细讲）\n\r\n3、跨平台性\n\r\n一次编译，到处运行，其实JVM会针对不同不同有不同的实现，我们编写的代码会通过JVM转为.class字节码文件，字节码通过JVM可以在不同平台上运行，转化为能被不同平台识别的机器码。\n', '2021-10-04 15:31:22.642000', '2021-10-04 20:47:48.000000', '\0', null, '\0', '\0', '2', '0');
INSERT INTO `bgms_blog` VALUES ('21', '\0', '\0', '十二、泛型的上界和下界', '十二、泛型的上界和下界', null, '十二、泛型的上界和下界\n\r\n<?>: 无限制通配符 <? extends E>: extends 关键字声明了类型的上界,表示参数化的类型可能是所指定的类型,或者是此类型的子类 <? super E>: super关键字声明了类型的下界,表示参数化的类型可能是指定的类型,或者是此类型的父类 它们的目的都是为了使方法接口更为灵活,可以接受更为广泛的类型. < ? extends E>: 用于灵活读取，使得方法可以读取 E 或 E 的任意子类型的容器对象。 < ? super E>: 用于灵活写入或比较,使得对象可以写入父类型的容器,使得父类型的比较方法可以应用于子类对象。 用简单的一句话来概括就是为了获得最大限度的灵活性,要在表示生产者或者消费者的输入参数上使用通配符,使用的规则就是:生产者有上限(读操作使用extends),消费者有下限(写操作使用super).\n\r\n\r\n', '2021-10-04 15:31:22.642000', '2021-10-05 03:47:48.000000', '\0', null, '\0', '\0', '2', '0');
INSERT INTO `bgms_blog` VALUES ('22', '\0', '\0', '十、String对象的intern()', '十、String对象的intern()', null, '十、String对象的intern()\n\r\nStirng中的intern()是个Native方法,它会首先从常量池中查找是否存在该常量值的字符串,若不存在则先在常量池中创建,否则直接返回常量池已经存在的字符串的引用\n', '2021-10-04 15:31:22.642000', '2021-10-05 06:47:48.000000', '\0', null, '\0', '\0', '2', '0');
INSERT INTO `bgms_blog` VALUES ('23', '\0', '\0', '十一、String和StringBuffer的区别', '十一、String和StringBuffer的区别', null, '十一、String和StringBuffer的区别\n\r\nString和StringBuffer主要区别是性能:String是不可变对象,每次对String类型进行操作都等同于产生了一个新的String对象,然后指向新的String对象.所以尽量不要对String进行大量的拼接操作,否则会产生很多临时对象,导致GC开始工作,影响系统性能.\n\r\n\r\nStringBuffer是对象本身操作,而不是产生新的对象,因此在有大量拼接的情况下,我们建议使用StringBuffer(线程安全).\n', '2021-10-04 15:31:22.642000', '2021-10-05 15:47:48.000000', '\0', null, '\0', '\0', '2', '0');
INSERT INTO `bgms_blog` VALUES ('24', '\0', '\0', '二、HashMap的底层原理是什么？', '二、HashMap的底层原理是什么？', null, '二、HashMap的底层原理是什么？\n\r\n1、JDK7扩容时候多线程情况下可能会出现死循坏，根本原因是头插法导致\n\r\n2、hash种子默认0，可以配置变量来使得hash值更散列一些。\n\r\n3、modCount++每一次修改都会加一\n\r\n容错快速失败机制（fail fast，并发时候出现问题\n\r\n4、1.8hashmap是尾插法，链表长度大于8会转为红黑树，即第九个来的时候，数组是空的或者数组长度小于64不会树化\n\r\n5、hashmap1.7扩容条件多了一个判断当前数组节点不为空，均要判断是否大于阈值\n\r\n6、红黑树节点个数小于6个会转为链表\n\r\n', '2021-10-04 15:31:22.642000', '2021-10-05 17:47:48.000000', '\0', null, '\0', '\0', '2', '0');
INSERT INTO `bgms_blog` VALUES ('25', '\0', '\0', '九、JDK7中ConcurrentHashmap是怎么保证并发安全的？', '九、JDK7中ConcurrentHashmap是怎么保证并发安全的？', null, '九、JDK7中ConcurrentHashmap是怎么保证并发安全的？\n\r\n主要是利用Unsafe操作+ReentrantLock+分段思想\n\r\n\r\nUnsafe：通过CAS修改对象属性，并发安全的给数组的某个位置赋值以及获取元素；\n\r\n分段思想是为了提高并发量，分段数可以通过参数控制\n\r\n', '2021-10-04 15:31:22.642000', '2021-10-06 17:47:48.000000', '\0', null, '\0', '\0', '2', '0');
INSERT INTO `bgms_blog` VALUES ('26', '\0', '\0', '十一、JDK8中ConcurrentHashmap是怎么保证线程安全的？', '十一、JDK8中ConcurrentHashmap是怎么保证线程安全的？', null, '十一、JDK8中ConcurrentHashmap是怎么保证线程安全的？\n\r\n主要是unsafe操作+synchronized\n\r\n对table[i]进行加锁\n', '2021-10-04 15:31:22.642000', '2021-10-06 21:47:48.000000', '\0', null, '\0', '\0', '2', '0');
INSERT INTO `bgms_blog` VALUES ('27', '\0', '\0', '三、重量级锁和轻量级锁', '三、重量级锁和轻量级锁', null, '三、重量级锁和轻量级锁\n\r\n重量级就是要通过操作系统来进行锁的操作，轻量级直接在JVM中就可以进行\n', '2021-10-04 15:31:22.642000', '2021-10-07 18:47:48.000000', '\0', null, '\0', '\0', '2', '0');
INSERT INTO `bgms_blog` VALUES ('28', '\0', '\0', '九、偏向锁、轻量级锁、重量级锁什么区别', '九、偏向锁、轻量级锁、重量级锁什么区别', null, '九、偏向锁、轻量级锁、重量级锁什么区别\n\r\n1、对应不同的锁状态\n\r\n2、偏向锁就是类似一个门，请大家有序访问，并说获得该锁的线程是\n\r\n3、轻量级锁也叫自旋锁，就是不断循坏看是否能获得锁\n\r\n4、重量级锁：由操作系统进行统一管理\n', '2021-10-04 15:31:22.642000', '2021-10-07 23:47:48.000000', '\0', null, '\0', '\0', '2', '0');
INSERT INTO `bgms_blog` VALUES ('29', '\0', '\0', '十一、AQS', '十一、AQS', null, '十一、AQS\n\r\n1、是一个java线程同步的框架，是JDK中很多锁工具的核心实现框架\n\r\n2、在AQS中维护了一个信号量state，和一个线程组成的双向链表队列，其中这个队列就是用来给线程排队的，而state就像是一个红绿灯，用来控制线程排队或放行，在不同场景下有不同的意义\n', '2021-10-04 15:31:22.642000', '2021-10-08 15:47:48.000000', '\0', null, '\0', '\0', '2', '0');
INSERT INTO `bgms_blog` VALUES ('30', '\0', '\0', '十三、三个并发工具', '十三、三个并发工具', null, '十三、三个并发工具\n\r\n1、CountDownLatch\n\r\n保证三个线程同时执行，可以让我们模拟高并发的场景\n\r\n2、CylicBarrier\n\r\n并发情况下三个线程依次执行\n\r\n3、Semaphore\n\r\n三个线程有序交错执行\n', '2021-10-04 15:31:22.642000', '2021-10-08 16:47:48.000000', '\0', null, '\0', '\0', '2', '0');
INSERT INTO `bgms_blog` VALUES ('31', '\0', '\0', '十五、synchronized', '十五、synchronized', null, '十五、synchronized\n\r\n一、对象头\n\r\n1、存储对象自身的运行时数据\n\r\n哈希码\n\r\nGC分代年龄\n\r\n锁状态标识\n\r\n线程持有的锁\n\r\n偏向线程id\n\r\n偏向时间戳\n\r\n2、类型指针\n\r\n3、若为对象数组，还有记录数组长度的数据\n\r\n\r\nJava对象头一般占有2个机器码（在32位虚拟机中，1个机器码等于4字节，也就是32bit，在64位虚拟机中，1个机器码是8个字节，也就是64bit），但是 如果对象是数组类型，则需要3个机器码，因为JVM虚拟机可以通过Java对象的元数据信息确定Java对象的大小，但是无法从数组的元数据来确认数组的大小，所以用一块来记录数组长度。\n\r\n\r\n二、synchronized和Lock的区别（\n\r\n1、synchronized是java关键字，是在JVM层面上，lock是一个接口，是JDK提供的\n\r\n2、synchronized自动释放，lock需要手动释放\n\r\n3、synchronized其他线程获取不到锁智能等待，lock提供了trylock方法可以不阻塞等待，尝试获得锁\n\r\n\r\n4、synchronized可重入、不可中断，非公平，lock可重入、可判断、可公平\n\r\n5、synchronized原始采用悲观锁，lock采用乐观锁的方式\n', '2021-10-04 15:31:22.642000', '2021-10-08 17:47:48.000000', '\0', null, '\0', '\0', '2', '0');
INSERT INTO `bgms_blog` VALUES ('32', '\0', '\0', '3、JVM生命周期', '3、JVM生命周期', null, '3、JVM生命周期\n\r\n1、虚拟机的启动\n\r\n是通过引导类加载器创建一个初始类来完成的，这个类是由虚拟机的具体实现指定的\n\r\n2、虚拟机的执行\n\r\n程序开始执行，程序结束后就结束，\n\r\n执行一个所谓的java程序，真正在执行的是一个叫java虚拟机的进程\n\r\n3、虚拟机的退出\n\r\n程序正常结束、异常、操作系统出现错误，线程调用System.exit()方法\n', '2021-10-04 15:31:22.642000', '2021-10-09 15:47:48.000000', '\0', null, '\0', '\0', '2', '0');
INSERT INTO `bgms_blog` VALUES ('33', '\0', '\0', '二、运行时数据区整体概架构', '二、运行时数据区整体概架构', null, '二、运行时数据区整体概架构\n\r\n以下是自己的一句话总结：\n\r\n分为线程私有和线程共享的两大类，其中程序计数器、虚拟机栈、本地方法栈是属于线程私有的，堆内存及方法区内存是线程共享的。程序计数器主要是记录字节码指令，CPU上下文切换线程，从一个线程切换到另一个线程，需要知道线程执行到哪一步，所以记录这个指令就是很有必要的，程序计数器无OOM和GC的发生。虚拟机栈里面是一个个栈帧，每一个栈帧对应着每一个方法，栈帧又是由局部变量表、操作数栈、方法返回值地址、动态链接组成。虚拟机栈可能会发生栈溢出异常，即starkoverflow本地方法栈是存放本地方法相关的东西；堆是一块很大的空间，整体分为2大块，新生代和老年代，新生代又分了Eden区、S0区、S1区，垃圾回收主要发生在新生代，每一个区对应不同的垃圾回收算法；方法区保存的是一些常量、类的基本信息等，方法区对应的实现在JDK7中是永久代，在JDK8中是元空间。\n\r\n\n\r\n', '2021-10-04 15:31:22.642000', '2021-10-09 19:47:53.000000', '\0', null, '\0', '\0', '2', '0');
INSERT INTO `bgms_blog` VALUES ('34', '\0', '\0', '六、堆', '六、堆', null, '六、堆\n\r\n堆是内存管理的核心区域，是线程共享的，属于JVM级别，也就是一个JVM实例就会有一个堆空间，注意的是虽然堆整体上是线程共享的，但是在内部有一小块空间是线程私有的缓存区TLAB。\n\r\n\r\n几乎所有的对象实例都是在堆中，堆是GC垃圾回收的重点区域。堆整体可以分为新生代和老年代，新生代又分为Eden区和S0和S1区。\n\r\n新生代和老年代的比例是1：2，Eden区和s0，s1区所占空间比例是8：1：1\n\r\n\r\n1、设置堆大小的参数\n\r\n-Xms：用于表示堆区的起始内存，默认情况下，占物理内存大小的64分之一。\n\r\n-Xmx用于表示堆区的最大内存，默认情况下，占物理内存的四分之一。\n\r\n\r\n这里s0和s1谁是空的谁就是to，年龄计数器阈值是15，YGC是在Eden区满的时候会触发，s0和s1满的时候不会触发YGC，YGC会将s区以及伊甸园区一起GC\n\r\n关于垃圾回收，频繁在新生区收集，很少在养老区收集，几乎不在永久区/元空间收集。\n', '2021-10-04 15:31:22.642000', '2021-10-10 15:47:48.000000', '\0', null, '\0', '\0', '2', '0');
INSERT INTO `bgms_blog` VALUES ('35', '', '', '111', '', null, '111', '2021-10-25 10:14:29.650000', '2021-10-25 10:14:29.650000', '', null, '', '', '0', '1');
INSERT INTO `bgms_blog` VALUES ('36', '', '', '11', '', null, '111', '2021-10-25 10:21:16.500000', '2021-10-25 10:21:16.500000', '', null, '', '', '0', '1');

-- ----------------------------
-- Table structure for bgms_blogstat
-- ----------------------------
DROP TABLE IF EXISTS `bgms_blogstat`;
CREATE TABLE `bgms_blogstat` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `blog_id` bigint NOT NULL COMMENT '博文id',
  `views` int DEFAULT NULL COMMENT '浏览量',
  `likes` int DEFAULT NULL COMMENT '点赞量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of bgms_blogstat
-- ----------------------------
INSERT INTO `bgms_blogstat` VALUES ('1', '14', '12', '13');
INSERT INTO `bgms_blogstat` VALUES ('2', '15', '0', '0');
INSERT INTO `bgms_blogstat` VALUES ('3', '16', '0', '0');
INSERT INTO `bgms_blogstat` VALUES ('4', '17', '0', '0');
INSERT INTO `bgms_blogstat` VALUES ('5', '18', '0', '0');
INSERT INTO `bgms_blogstat` VALUES ('6', '19', '0', '0');
INSERT INTO `bgms_blogstat` VALUES ('7', '20', '0', '0');
INSERT INTO `bgms_blogstat` VALUES ('8', '21', '0', '0');
INSERT INTO `bgms_blogstat` VALUES ('9', '22', '0', '0');
INSERT INTO `bgms_blogstat` VALUES ('10', '23', '0', '0');
INSERT INTO `bgms_blogstat` VALUES ('11', '24', '0', '0');
INSERT INTO `bgms_blogstat` VALUES ('12', '25', '0', '0');
INSERT INTO `bgms_blogstat` VALUES ('13', '26', '0', '0');
INSERT INTO `bgms_blogstat` VALUES ('14', '27', '0', '0');
INSERT INTO `bgms_blogstat` VALUES ('15', '28', '0', '0');
INSERT INTO `bgms_blogstat` VALUES ('16', '29', '0', '0');
INSERT INTO `bgms_blogstat` VALUES ('17', '30', '0', '0');
INSERT INTO `bgms_blogstat` VALUES ('18', '31', '0', '0');
INSERT INTO `bgms_blogstat` VALUES ('19', '32', '0', '0');
INSERT INTO `bgms_blogstat` VALUES ('20', '33', '0', '0');
INSERT INTO `bgms_blogstat` VALUES ('21', '34', '0', '0');
INSERT INTO `bgms_blogstat` VALUES ('22', '35', '0', '0');
INSERT INTO `bgms_blogstat` VALUES ('23', '36', '0', '0');

-- ----------------------------
-- Table structure for bgms_blog_classify_relation
-- ----------------------------
DROP TABLE IF EXISTS `bgms_blog_classify_relation`;
CREATE TABLE `bgms_blog_classify_relation` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `blog_id` bigint DEFAULT NULL,
  `classify_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8mb3 COMMENT='博文与博文分类关系表';

-- ----------------------------
-- Records of bgms_blog_classify_relation
-- ----------------------------
INSERT INTO `bgms_blog_classify_relation` VALUES ('54', '14', '10');
INSERT INTO `bgms_blog_classify_relation` VALUES ('55', '15', '10');
INSERT INTO `bgms_blog_classify_relation` VALUES ('56', '16', '10');
INSERT INTO `bgms_blog_classify_relation` VALUES ('57', '17', '10');

-- ----------------------------
-- Table structure for bgms_classify
-- ----------------------------
DROP TABLE IF EXISTS `bgms_classify`;
CREATE TABLE `bgms_classify` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `ums_id` bigint NOT NULL COMMENT 'ums_member的ID',
  `name` varchar(255) DEFAULT NULL COMMENT '分类名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of bgms_classify
-- ----------------------------
INSERT INTO `bgms_classify` VALUES ('10', '0', 'Mybatis');
INSERT INTO `bgms_classify` VALUES ('11', '1', 'java');
INSERT INTO `bgms_classify` VALUES ('12', '1', 'springboot');

-- ----------------------------
-- Table structure for bgms_tag
-- ----------------------------
DROP TABLE IF EXISTS `bgms_tag`;
CREATE TABLE `bgms_tag` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `blog_id` bigint NOT NULL COMMENT '博文id',
  `name` varchar(255) DEFAULT NULL COMMENT '标签内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=105 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of bgms_tag
-- ----------------------------

-- ----------------------------
-- Table structure for ums_admin
-- ----------------------------
DROP TABLE IF EXISTS `ums_admin`;
CREATE TABLE `ums_admin` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(64) DEFAULT NULL,
  `password` varchar(64) DEFAULT NULL,
  `icon` varchar(500) DEFAULT NULL COMMENT '头像',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `nick_name` varchar(200) DEFAULT NULL COMMENT '昵称',
  `note` varchar(500) DEFAULT NULL COMMENT '备注信息',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `status` int DEFAULT '1' COMMENT '帐号启用状态：0->禁用；1->启用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COMMENT='后台用户表';

-- ----------------------------
-- Records of ums_admin
-- ----------------------------
INSERT INTO `ums_admin` VALUES ('1', 'admin', '$2a$10$bje8m5P.aitwEcgvYD4qnOw8jvUHPFgjoeUfN0f1c/Xq0qx1yVN7a', 'https://img0.baidu.com/it/u=2039975083,449836607&fm=26&fmt=auto&gp=0.jpg', 'string', 'admin', 'admin', '2021-08-29 10:04:32', null, '1');
INSERT INTO `ums_admin` VALUES ('2', 'umsgl', '$2a$10$9eULkk9lLrnXLV2xm8ZMWeoBO0o.1EP4y2ziCDPvjYcdGF0DGmjT2', 'string', 'string', 'umsgl', 'umsgl', '2021-08-29 15:02:44', null, '1');
INSERT INTO `ums_admin` VALUES ('3', 'string', '$2a$10$suAvMXOhiPVA0bkoviQD3udWVGE9rQKKB2zwlrufUJ5bqiSBQTE/6', 'string', 'string', 'string', 'string', '2021-08-29 16:26:11', null, '1');

-- ----------------------------
-- Table structure for ums_admin_role_relation
-- ----------------------------
DROP TABLE IF EXISTS `ums_admin_role_relation`;
CREATE TABLE `ums_admin_role_relation` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `admin_id` bigint DEFAULT NULL,
  `role_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3 COMMENT='后台用户和角色关系表';

-- ----------------------------
-- Records of ums_admin_role_relation
-- ----------------------------
INSERT INTO `ums_admin_role_relation` VALUES ('3', '1', '1');
INSERT INTO `ums_admin_role_relation` VALUES ('4', '1', '2');

-- ----------------------------
-- Table structure for ums_member
-- ----------------------------
DROP TABLE IF EXISTS `ums_member`;
CREATE TABLE `ums_member` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(64) NOT NULL COMMENT '用户名',
  `password` varchar(64) NOT NULL COMMENT '密码',
  `nickname` varchar(64) NOT NULL COMMENT '昵称',
  `phone` varchar(64) NOT NULL COMMENT '手机号码',
  `status` int DEFAULT '1' COMMENT '帐号启用状态:0->禁用；1->启用',
  `create_time` datetime DEFAULT NULL COMMENT '注册时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `icon` varchar(500) DEFAULT NULL COMMENT '头像',
  `gender` int DEFAULT '0' COMMENT '性别：0->未知；1->男；2->女',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `city` varchar(64) DEFAULT NULL COMMENT '所做城市',
  `job` varchar(100) DEFAULT NULL COMMENT '职业',
  `personalized_signature` varchar(300) DEFAULT NULL COMMENT '个性签名',
  `integration` int DEFAULT NULL COMMENT '积分',
  `growth` int DEFAULT NULL COMMENT '成长值',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_username` (`username`),
  UNIQUE KEY `idx_phone` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COMMENT='会员表';

-- ----------------------------
-- Records of ums_member
-- ----------------------------
INSERT INTO `ums_member` VALUES ('0', 'jzone', '$2a$10$GTaQEuzw2nTv/6P9l5zpIuNdcu9ZhKztTVESgpwCLFAFUJAnZkTpW', 'JZone', '18803260897', '1', '2021-10-04 14:53:31', null, 'https://img2.baidu.com/it/u=2204821660,181269186&fm=26&fmt=auto', '0', null, null, null, null, '0', '1');
INSERT INTO `ums_member` VALUES ('1', 'ceshi1', '$2a$10$BU5KghBWrmCbNVwHLdAaa.Uq.tAYvj8bnmjg0kYGr8fTPw0MB9j2a', '测试1', '18803260895', '1', '2021-09-09 21:25:00', null, 'https://img2.baidu.com/it/u=871084579,2353160254&fm=26&fmt=auto&gp=0.jpg', '0', '2021-09-05', '测试1测试1', '测试1测试1测试1测试1', '测试1测试1测试1', '0', '1');

-- ----------------------------
-- Table structure for ums_menu
-- ----------------------------
DROP TABLE IF EXISTS `ums_menu`;
CREATE TABLE `ums_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `parent_id` bigint DEFAULT NULL COMMENT '父级ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `title` varchar(100) DEFAULT NULL COMMENT '菜单名称',
  `level` int DEFAULT NULL COMMENT '菜单级数',
  `sort` int DEFAULT NULL COMMENT '菜单排序',
  `name` varchar(100) DEFAULT NULL COMMENT '前端名称',
  `icon` varchar(200) DEFAULT NULL COMMENT '前端图标',
  `hidden` int DEFAULT NULL COMMENT '前端隐藏',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3 COMMENT='后台菜单表';

-- ----------------------------
-- Records of ums_menu
-- ----------------------------
INSERT INTO `ums_menu` VALUES ('1', '0', '2021-08-27 22:02:40', '权限', '0', '0', 'ums', 'ums', '0');
INSERT INTO `ums_menu` VALUES ('2', '1', '2021-08-27 22:04:28', '人员列表', '1', '0', 'admin', 'ums-admin', '0');
INSERT INTO `ums_menu` VALUES ('3', '1', '2021-08-27 22:06:18', '角色列表', '1', '0', 'role', 'ums-role', '0');
INSERT INTO `ums_menu` VALUES ('4', '1', '2021-08-27 22:06:54', '菜单列表', '1', '0', 'menu', 'ums-menu', '0');
INSERT INTO `ums_menu` VALUES ('5', '1', '2021-08-27 22:07:30', '资源列表', '1', '0', 'resource', 'ums-resource', '0');
INSERT INTO `ums_menu` VALUES ('6', '0', '2021-08-29 19:20:54', '博文', '0', '0', 'bgms', 'bgms', '0');
INSERT INTO `ums_menu` VALUES ('7', '1', '2021-08-29 19:20:54', '博文列表', '1', '0', 'bgblogs', 'bgblogs', '0');
INSERT INTO `ums_menu` VALUES ('8', '1', '2021-08-29 19:20:54', '博文分类', '1', '0', 'bgclassify', 'bgclassify', '0');

-- ----------------------------
-- Table structure for ums_resource
-- ----------------------------
DROP TABLE IF EXISTS `ums_resource`;
CREATE TABLE `ums_resource` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `name` varchar(200) DEFAULT NULL COMMENT '资源名称',
  `url` varchar(200) DEFAULT NULL COMMENT '资源URL',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  `category_id` bigint DEFAULT NULL COMMENT '资源分类ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3 COMMENT='后台资源表';

-- ----------------------------
-- Records of ums_resource
-- ----------------------------
INSERT INTO `ums_resource` VALUES ('1', '2021-08-27 21:38:44', '后台人员管理', '/admin/**', '后台增删改查人员账号', '1');
INSERT INTO `ums_resource` VALUES ('2', '2021-08-27 21:40:09', '后台菜单管理', '/menu/**', '后台增删改查菜单', '1');
INSERT INTO `ums_resource` VALUES ('3', '2021-08-27 21:45:37', '后台资源管理', '/resource/**', '后台增删改查资源账号', '1');
INSERT INTO `ums_resource` VALUES ('4', '2021-08-27 21:46:09', '后台资源分类管理', '/resourceCategory/**', '后台资源分类管理', '1');
INSERT INTO `ums_resource` VALUES ('5', '2021-08-29 19:32:47', '后台博文管理', '/bolg/**', '可以管理所有博文', '2');
INSERT INTO `ums_resource` VALUES ('6', '2021-08-29 19:32:47', '后台博文分类管理', '/classify/**', '可以管理所有博文分类', '2');
INSERT INTO `ums_resource` VALUES ('7', '2021-08-29 22:07:40', '后台角色管理', '/role/**', '后台增删改查角色', '1');

-- ----------------------------
-- Table structure for ums_resource_category
-- ----------------------------
DROP TABLE IF EXISTS `ums_resource_category`;
CREATE TABLE `ums_resource_category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `name` varchar(200) DEFAULT NULL COMMENT '分类名称',
  `sort` int DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COMMENT='资源分类表';

-- ----------------------------
-- Records of ums_resource_category
-- ----------------------------
INSERT INTO `ums_resource_category` VALUES ('1', '2021-08-27 21:34:40', '权限模块', '1');
INSERT INTO `ums_resource_category` VALUES ('2', '2021-08-29 19:26:06', '博文模块', '0');

-- ----------------------------
-- Table structure for ums_role
-- ----------------------------
DROP TABLE IF EXISTS `ums_role`;
CREATE TABLE `ums_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL COMMENT '名称',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  `admin_count` int DEFAULT NULL COMMENT '后台用户数量',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `status` int DEFAULT '1' COMMENT '启用状态：0->禁用；1->启用',
  `sort` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COMMENT='后台用户角色表';

-- ----------------------------
-- Records of ums_role
-- ----------------------------
INSERT INTO `ums_role` VALUES ('1', '后台权限管理员', '后台权限管理员', '0', '2021-08-27 22:08:38', '1', '0');
INSERT INTO `ums_role` VALUES ('2', '后台博文管理员', '后台博文管理员', '0', '2021-08-29 21:59:54', '1', '0');

-- ----------------------------
-- Table structure for ums_role_menu_relation
-- ----------------------------
DROP TABLE IF EXISTS `ums_role_menu_relation`;
CREATE TABLE `ums_role_menu_relation` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_id` bigint DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3 COMMENT='后台角色菜单关系表';

-- ----------------------------
-- Records of ums_role_menu_relation
-- ----------------------------
INSERT INTO `ums_role_menu_relation` VALUES ('1', '1', '1');
INSERT INTO `ums_role_menu_relation` VALUES ('2', '1', '2');
INSERT INTO `ums_role_menu_relation` VALUES ('3', '1', '3');
INSERT INTO `ums_role_menu_relation` VALUES ('4', '1', '4');
INSERT INTO `ums_role_menu_relation` VALUES ('5', '1', '5');
INSERT INTO `ums_role_menu_relation` VALUES ('6', '2', '6');
INSERT INTO `ums_role_menu_relation` VALUES ('7', '2', '7');
INSERT INTO `ums_role_menu_relation` VALUES ('8', '2', '8');

-- ----------------------------
-- Table structure for ums_role_resource_relation
-- ----------------------------
DROP TABLE IF EXISTS `ums_role_resource_relation`;
CREATE TABLE `ums_role_resource_relation` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_id` bigint DEFAULT NULL COMMENT '角色ID',
  `resource_id` bigint DEFAULT NULL COMMENT '资源ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3 COMMENT='后台角色资源关系表';

-- ----------------------------
-- Records of ums_role_resource_relation
-- ----------------------------
INSERT INTO `ums_role_resource_relation` VALUES ('1', '1', '1');
INSERT INTO `ums_role_resource_relation` VALUES ('2', '1', '2');
INSERT INTO `ums_role_resource_relation` VALUES ('3', '1', '3');
INSERT INTO `ums_role_resource_relation` VALUES ('4', '1', '4');
INSERT INTO `ums_role_resource_relation` VALUES ('5', '2', '5');
INSERT INTO `ums_role_resource_relation` VALUES ('6', '2', '6');
INSERT INTO `ums_role_resource_relation` VALUES ('7', '1', '7');
