package net.xzh.interceptor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import cn.hutool.core.util.ObjectUtil;
import net.xzh.interceptor.encrpyt.SensitiveConverter;
import net.xzh.interceptor.encrpyt.SensitiveFormat;
import net.xzh.interceptor.encrpyt.SensitiveType;

/**
 * 字段加解脱敏拦截器
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
public class FieldEncryptInterceptor implements Interceptor {

	private static Logger log = Logger.getLogger(FieldEncryptInterceptor.class);

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		Object[] args = invocation.getArgs();
		MappedStatement ms = (MappedStatement) args[0];// MappedStatement
		Object parameter = args[1];// 参数
		SensitiveConverter sensitiveConverter = getMethodAnnotations(ms);
		if (Objects.isNull(sensitiveConverter)) {
			return invocation.proceed();
		} else {
			SqlCommandType sqlCommandType = ms.getSqlCommandType();
			if (SqlCommandType.UPDATE.equals(sqlCommandType)
					&& SensitiveType.UPDATE.equals(sensitiveConverter.value())) {
				updateParameters(sensitiveConverter, ms, parameter);
				log.info("数据入库敏感词过滤");
			}
			Object object = invocation.proceed();
			if (SqlCommandType.SELECT.equals(sqlCommandType)
					&& SensitiveType.SELECT.equals(sensitiveConverter.value())) {
				log.info("数据显示脱敏");
				decrypt(object);
			}
			return object;
		}
	}

	/**
	 * 对象脱敏
	 * 
	 * @param object
	 * @return
	 */
	private Object decrypt(Object object) {
		if (Objects.nonNull(object)) {
			if (object instanceof List) {
				encryptByAnnByList((List<Object>) object);
			} else {
				encryptByAnnByList(Collections.singletonList(object));
			}
		}
		return object;
	}

	/**
	 * 修改入参信息
	 * 
	 * @param sensitiveConverter
	 * @param ms
	 * @param parameter
	 */
	private void updateParameters(SensitiveConverter sensitiveConverter, MappedStatement ms, Object parameter) {
		if (!(parameter instanceof Map)) {
			encryptByAnnByList(Collections.singletonList(parameter));
		} else {
			Map<String, Object> map = getParameterMap(parameter);
			map.forEach((k, v) -> {
				if (v instanceof Collection) {
					encryptByAnnByList((List<Object>) v);
				} else {
					encryptByAnnByList(Collections.singletonList(v));
				}
			});
		}
	}

	/**
	 * 获取参数的map 集合
	 * 
	 * @param parameter
	 * @return
	 */
	private Map<String, Object> getParameterMap(Object parameter) {
		Set<Integer> hashCodeSet = new HashSet<>();
		return ((Map<String, Object>) parameter).entrySet().stream().filter(e -> Objects.nonNull(e.getValue()))
				.filter(r -> {
					if (!hashCodeSet.contains(r.getValue().hashCode())) {
						hashCodeSet.add(r.getValue().hashCode());
						return true;
					}
					return false;
				}).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	private void encryptByAnnByList(List<Object> list) {
		// 获取字段上的注解值
		if (CollectionUtils.isEmpty(list)) {
			return;
		}
		Set<Field> annotationFields = this.getFields(list.get(0)).stream()
				.filter(field -> field.isAnnotationPresent(SensitiveFormat.class)).collect(Collectors.toSet());

		ConversionService sharedInstance = DefaultConversionService.getSharedInstance();// 待整合到@Bean

		list.forEach(obj -> {
			getFields(obj).stream().filter(annotationFields::contains).forEach(field -> {
				Object value = this.getField(field, obj);
				String annovalue = field.getDeclaredAnnotation(SensitiveFormat.class).value();
				if (Objects.nonNull(value) & ObjectUtil.isNotEmpty(annovalue)) {
					ReflectionUtils.setField(field, obj,
							sharedInstance.convert(tuomin(value.toString(), annovalue), field.getType()));
				}
			});
		});
	}

	private String tuomin(String submsg, String key) {
		if ("realName".equals(key)) {
			return SensitiveInfoUtils.chineseName(submsg);// 姓名
		}
		if ("idCard".equals(key)) {
			return SensitiveInfoUtils.idCard(submsg);// 身份证号
		}
		if ("fixedPhone".equals(key)) {
			return SensitiveInfoUtils.fixedPhone(submsg);// 固定电话
		}
		if ("bankCard".equals(key)) {
			return SensitiveInfoUtils.bankCard(submsg);// 银行卡号
		}
		if ("mobilePhone".equals(key)) {
			return SensitiveInfoUtils.mobilePhone(submsg);// 手机号
		}
		if ("address".equals(key)) {
			return SensitiveInfoUtils.address(submsg, 6);// 地址
		}
		if ("email".equals(key)) {
			return SensitiveInfoUtils.email(submsg);// email
		}
		if ("cnapsCode".equals(key)) {
			return SensitiveInfoUtils.cnapsCode(submsg);// 公司开户银行联号
		}
		return submsg;
	}

	/**
	 * 获取字段
	 * 
	 * @since 2018-09-28 16:49:46
	 * @param
	 * @return
	 */
	private Object getField(Field field, Object obj) {
		ReflectionUtils.makeAccessible(field);
		return ReflectionUtils.getField(field, obj);
	}

	private List<Field> getFields(Object obj) {
		List<Field> fieldList = new ArrayList<>();
		Class tempClass = obj.getClass();
		// 当父类为null的时候说明到达了最上层的父类(Object类).
		while (tempClass != null) {
			fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
			// 得到父类,然后赋给自己
			tempClass = tempClass.getSuperclass();
		}
		return fieldList;
	}

	/**
	 * 得到方法上面设置的脱敏规则
	 * 
	 * @param ms
	 * @return
	 */
	private SensitiveConverter getMethodAnnotations(MappedStatement ms) {
		String namespace = ms.getId();
		String className = namespace.substring(0, namespace.lastIndexOf("."));
		String methodName = ms.getId().substring(ms.getId().lastIndexOf(".") + 1);
		Method[] mes;
		try {
			mes = Class.forName(className).getMethods();
			for (Method m : mes) {
				if (m.getName().equals(methodName)) {
					if (m.isAnnotationPresent(SensitiveConverter.class)) {
						return m.getAnnotation(SensitiveConverter.class);
					}
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {

	}

}
