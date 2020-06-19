package cn.itsource.hrm.mapper;

import cn.itsource.hrm.domain.RolePermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author solargen
 * @since 2020-06-19
 */
@Component
public interface RolePermissionMapper extends BaseMapper<RolePermission> {
    /**
     * 批量插入
     * @param id
     * @param permissionIds
     */
    void insertBatch(@Param("roleId") Long id, @Param("permissionIds") List<Long> permissionIds);
}
