package cn.itsource.hrm.service.impl;

import cn.itsource.hrm.domain.Permission;
import cn.itsource.hrm.mapper.PermissionMapper;
import cn.itsource.hrm.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author solargen
 * @since 2020-06-19
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

}
