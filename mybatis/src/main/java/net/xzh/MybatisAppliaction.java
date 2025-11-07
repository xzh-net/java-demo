package net.xzh;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

import net.xzh.mbg.mapper.UmsDemoMapper;
import net.xzh.mbg.model.UmsDemo;
import net.xzh.mbg.model.UmsDemoExample;
import net.xzh.mbg.model.UmsDemoExample.Criteria;

/*
 CREATE TABLE `ums_demo`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `version` int(255) NOT NULL DEFAULT 1 COMMENT '版本号',
  PRIMARY KEY (`id`) USING BTREE
)
*/
public class MybatisAppliaction {

	private static Logger log = Logger.getLogger(MybatisAppliaction.class);

	public static void main(String[] args) throws IOException {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		// 解析xml
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		// 解析获取具体使用哪个执行器
		SqlSession sqlSession = sqlSessionFactory.openSession();
//		1 namespace xml 调用
//		UmsAdmin umsAdmin = sqlSession.selectOne("com.xuzhihao.mbg.mapper.UmsAdminMapper.selectByPrimaryKey", 1L);

//		2 Mapper 调用
//		UmsAdminMapper umsAdminMapper = sqlSession.getMapper(UmsAdminMapper.class);
//		umsAdminMapper.selectByPrimaryKey(3L);

//		3 Annotation注解调用
//		UmsAdminAnnotationMapper umsAdminAnnotationMapper = sqlSession.getMapper(UmsAdminAnnotationMapper.class);
//		System.out.println(umsAdminAnnotationMapper.selectByPrimaryKey(4L));

		UmsDemoMapper umsDemoMapper = sqlSession.getMapper(UmsDemoMapper.class);
		UmsDemoExample example = new UmsDemoExample();
//		4 Example用法
		Criteria criteria = example.createCriteria();
		criteria.andIdLessThan(2000L);
		List<UmsDemo> selectByExample = umsDemoMapper.selectByExample(example);
		System.out.println(selectByExample);

//		5 Ex
//		UmsDemo umsDemo = umsDemoMapper.selectByPrimaryKey(955L);
//		System.out.println(umsDemo);
//		int rtn = umsDemoMapper.updateByPrimaryKey(umsDemo);
//		log.info("更新结果--->" + rtn);

		sqlSession.commit();
		sqlSession.flushStatements();
		sqlSession.close();

	}

	/**
	{
		UmsDemoExample example = new UmsDemoExample();
		Criteria criteria = new UmsDemoExample().createCriteria();
		example.setOrderByClause("字段名 asc"); // 添加升序排列条件，DESC为降序
		example.setDistinct(false); // 去除重复，boolean型，true为选择不重复的记录。
		criteria.andPasswordIsNull(); // 添加字段xxx为null的条件
		criteria.andUsernameIsNotNull(); // 添加字段xxx不为null的条件
		criteria.andUsernameEqualTo("admin"); // 添加xxx字段等于value条件
		criteria.andUsernameNotEqualTo("admin"); // 添加xxx字段不等于value条件
		criteria.andIdGreaterThan(500L); // 添加xxx字段大于value条件
		criteria.andIdGreaterThanOrEqualTo(50L); // 添加xxx字段大于等于value条件
		criteria.andIdLessThan(100L); // 添加xxx字段小于value条件
		criteria.andIdLessThanOrEqualTo(100L); // 添加xxx字段小于等于value条件
		List<String> lst = Collections.emptyList();
		criteria.andUsernameIn(lst); // 添加xxx字段值在List<？>条件
		criteria.andUsernameNotIn(lst); // 添加xxx字段值不在List<？>条件
		criteria.andUsernameLike("%" + "admin" + "%"); // 添加xxx字段值为value的模糊查询条件
		criteria.andUsernameNotLike("%" + "admin" + "%"); // 添加xxx字段值不为value的模糊查询条件
		criteria.andIdBetween(1L, 100L); // 添加xxx字段值在value1和value2之间条件
		criteria.andIdNotBetween(1L, 100L); // 添加xxx字段值不在value1和value2之间条件
	}
	**/

}
