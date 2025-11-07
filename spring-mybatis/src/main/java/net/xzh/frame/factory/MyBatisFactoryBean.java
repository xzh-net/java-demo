package net.xzh.frame.factory;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.FactoryBean;

/**
 * 对标MyBatis的MapperFactoryBean
 * @author xzh
 *
 */
public class MyBatisFactoryBean implements FactoryBean<Object> {
	private Class<?> mapperClass;

	public MyBatisFactoryBean(Class<?> mapperClass) {
		this.mapperClass = mapperClass;
	}

	private SqlSession sqlSession;
	
	@Override
	public Object getObject() throws Exception {
		Object mapper =sqlSession.getMapper(mapperClass);
		return mapper;
		
	}

	@Override
	public Class<?> getObjectType() {
		// TODO Auto-generated method stub
		return mapperClass;
	}

	public SqlSession getSqlSession() {
		return sqlSession;
	}

	public void setSqlSession(SqlSessionFactory sqlSessionFactory) {
		sqlSessionFactory.getConfiguration().addMapper(mapperClass);
		this.sqlSession = sqlSessionFactory.openSession();
	}

}
