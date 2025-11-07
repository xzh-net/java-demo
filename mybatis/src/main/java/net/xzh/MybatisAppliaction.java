package net.xzh;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.xzh.mbg.mapper.UmsAdminAnnotationMapper;
import net.xzh.mbg.mapper.UmsAdminMapper;
import net.xzh.mbg.mapper.UmsDemoMapper;
import net.xzh.mbg.model.UmsAdmin;
import net.xzh.mbg.model.UmsDemo;
import net.xzh.mbg.model.UmsDemoExample;
import net.xzh.mbg.model.UmsDemoExample.Criteria;


public class MybatisAppliaction {

	
	 private static final Logger logger = LogManager.getLogger(MybatisAppliaction.class);

	public static void main(String[] args) throws IOException {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		// 解析xml
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		// 解析获取具体使用哪个执行器
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		//namespace调用
		UmsAdmin umsAdmin = sqlSession.selectOne("net.xzh.mbg.mapper.UmsAdminMapper.selectByPrimaryKey", 1L);
		logger.info("namespace调用，{}",umsAdmin);
		System.out.println("------------------------------------------");
		
		//Mapper调用
		UmsAdminMapper umsAdminMapper = sqlSession.getMapper(UmsAdminMapper.class);
		UmsAdmin umsAdmin2 = umsAdminMapper.selectByPrimaryKey(1L);
		logger.info("Mapper调用，{}",umsAdmin2);
		System.out.println("------------------------------------------");

		//Annotation调用
		UmsAdminAnnotationMapper umsAdminAnnotationMapper = sqlSession.getMapper(UmsAdminAnnotationMapper.class);
		UmsAdmin umsAdmin3 = umsAdminAnnotationMapper.selectByPrimaryKey(1L);
		logger.info("Annotation调用，{}",umsAdmin3);
		System.out.println("------------------------------------------");
		
		//Example调用示例
		UmsDemoMapper umsDemoMapper = sqlSession.getMapper(UmsDemoMapper.class);
		UmsDemoExample example = new UmsDemoExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdLessThan(2000L);
		List<UmsDemo> demos = umsDemoMapper.selectByExample(example);
		logger.info("Example调用示例，{}",demos);
		System.out.println("------------------------------------------");
		
		//乐观锁
		UmsDemo umsDemo = umsDemoMapper.selectByPrimaryKey(1L);
		umsDemo.setUsername("zhangsan1");
		int updateByPrimaryKey = umsDemoMapper.updateByPrimaryKey(umsDemo);
		logger.info("有锁更新结果--->" + updateByPrimaryKey);
		//无锁
		UmsDemo umsDemo2 = umsDemoMapper.selectByPrimaryKey(2L);
		umsDemo2.setUsername("test");
		int updateByPrimaryKeySelective = umsDemoMapper.updateByPrimaryKeySelective(umsDemo2);
		logger.info("无锁更新结果--->" + updateByPrimaryKeySelective);

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
