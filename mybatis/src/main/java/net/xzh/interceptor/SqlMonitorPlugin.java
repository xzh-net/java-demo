package net.xzh.interceptor;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.apache.log4j.Logger;

/**
 * 慢sql分析插件
 * 
 * @author Administrator
 *
 */
@Intercepts({
		@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
				RowBounds.class, ResultHandler.class }),
		@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
				RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class }),
		@Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })
public class SqlMonitorPlugin implements Interceptor {

	private static Logger log = Logger.getLogger(SqlMonitorPlugin.class);

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
		Object parameter = null;
		if (invocation.getArgs().length > 1) {
			parameter = invocation.getArgs()[1];
		}
		String sqlId = mappedStatement.getId();
		BoundSql boundSql = mappedStatement.getBoundSql(parameter);
		Configuration configuration = mappedStatement.getConfiguration();
		long startTime = System.currentTimeMillis();
		Object result = null;
		try {
			result = invocation.proceed();
		} finally {
			long timing = System.currentTimeMillis() - startTime;
			String sql = getSql(boundSql, parameter, configuration);
			log.info("执行sql耗时：" + timing + "ms -id：" + sqlId + " -sql:" + sql);
		}
		return result;
	}

	private String getSql(BoundSql boundSql, Object paramerObject, Configuration configuration) {
		String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
		if (parameterMappings != null) {
			for (int i = 0; i < parameterMappings.size(); i++) {
				ParameterMapping parameterMapping = parameterMappings.get(i);
				if (parameterMapping.getMode() != ParameterMode.OUT) {
					Object value;
					String propertyName = parameterMapping.getProperty();
					if (boundSql.hasAdditionalParameter(propertyName)) {
						value = boundSql.getAdditionalParameter(propertyName);
					} else if (paramerObject == null) {
						value = null;
					} else if (typeHandlerRegistry.hasTypeHandler(paramerObject.getClass())) {
						value = paramerObject;
					} else {
						MetaObject metaObject = configuration.newMetaObject(paramerObject);
						value = metaObject.getValue(propertyName);
					}
					sql = replacePlaceholder(sql, value);
				}
			}
		}
		return sql;
	}

	/**
	 * 填充占位符?
	 *
	 * @param sql
	 * @param parameterObject
	 * @return
	 */
	private String replacePlaceholder(String sql, Object parameterObject) {
		String result;
		if (parameterObject instanceof String) {
			result = "'" + parameterObject.toString() + "'";
		} else if (parameterObject instanceof Date) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			result = "'" + simpleDateFormat.format(parameterObject) + "'";
		} else {
			result = parameterObject.toString();
		}
		return sql.replaceFirst("\\?", result);
	}

	@Override
	public Object plugin(Object target) {
		if (target instanceof Executor) {
			return Plugin.wrap(target, this);
		}
		return target;
	}

	@Override
	public void setProperties(Properties properties) {

	}

}