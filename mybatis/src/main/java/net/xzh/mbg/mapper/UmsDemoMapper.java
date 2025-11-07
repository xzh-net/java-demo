package net.xzh.mbg.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import net.xzh.interceptor.encrpyt.SensitiveConverter;
import net.xzh.interceptor.encrpyt.SensitiveType;
import net.xzh.interceptor.locker.VersionLocker;
import net.xzh.mbg.model.UmsDemo;
import net.xzh.mbg.model.UmsDemoExample;

public interface UmsDemoMapper {
	long countByExample(UmsDemoExample example);

	int deleteByExample(UmsDemoExample example);

	int deleteByPrimaryKey(Long id);

	int insert(UmsDemo record);

	int insertSelective(UmsDemo record);

	List<UmsDemo> selectByExample(UmsDemoExample example);

//	@SensitiveConverter(value = SensitiveType.SELECT)
	UmsDemo selectByPrimaryKey(Long id);

	int updateByExampleSelective(@Param("record") UmsDemo record, @Param("example") UmsDemoExample example);

	int updateByExample(@Param("record") UmsDemo record, @Param("example") UmsDemoExample example);

	
	int updateByPrimaryKeySelective(UmsDemo record);

	@VersionLocker()
	@SensitiveConverter(value = SensitiveType.UPDATE)
	int updateByPrimaryKey(UmsDemo record);
}