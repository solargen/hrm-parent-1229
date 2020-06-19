package cn.itsource.hrm.service;

import cn.itsource.hrm.controller.vo.RegisterVo;
import cn.itsource.hrm.domain.Tenant;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author solargen
 * @since 2020-06-19
 */
public interface ITenantService extends IService<Tenant> {
    /**
     * 租户注册
     * @param registerVo
     */
    void register(RegisterVo registerVo);
}
