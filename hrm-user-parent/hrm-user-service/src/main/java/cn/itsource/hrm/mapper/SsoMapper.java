package cn.itsource.hrm.mapper;

import cn.itsource.hrm.domain.Sso;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员登录账号 Mapper 接口
 * </p>
 *
 * @author solargen
 * @since 2020-06-29
 */
public interface SsoMapper extends BaseMapper<Sso> {

    /**
     * 用户根据name查询
     * @param name
     */
    Sso selectByName(String name);
}
