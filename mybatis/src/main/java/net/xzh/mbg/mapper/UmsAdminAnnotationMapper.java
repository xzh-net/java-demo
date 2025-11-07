package net.xzh.mbg.mapper;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import net.xzh.mbg.model.UmsAdmin;

public interface UmsAdminAnnotationMapper {
	@Select("select * from ums_admin where id = #{id}")
	@Results({ @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
			@Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
			@Result(column = "class_id", property = "classId", jdbcType = JdbcType.INTEGER) })
	UmsAdmin selectByPrimaryKey(Long id);
}