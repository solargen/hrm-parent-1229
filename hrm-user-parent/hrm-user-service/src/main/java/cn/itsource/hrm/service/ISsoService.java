package cn.itsource.hrm.service;

import cn.itsource.hrm.controller.vo.LoginVo;
import cn.itsource.hrm.controller.vo.RegisterVo;
import cn.itsource.hrm.domain.Sso;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员登录账号 服务类
 * </p>
 *
 * @author solargen
 * @since 2020-06-29
 */
public interface ISsoService extends IService<Sso> {

    /**
     * 手机号码注册
     * @param vo
     */
    void telephoneReg(RegisterVo vo);

    /**
     * 根据name查询用户信息
     * @param name 用户的邮箱或者手机号码
     * @return
     */
    Sso getByName(String name);
}
