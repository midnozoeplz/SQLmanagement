package com.jianz.SQLmanagement.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jianz.SQLmanagement.pojo.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jianz
 * @since 2022-05-27
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<String> selectPermsByUserId(@Param("userid") Long userid);
}
