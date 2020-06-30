package cn.itsource.hrm.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import cn.itsource.hrm.controller.vo.RegisterVo;
import cn.itsource.hrm.domain.Sso;
import cn.itsource.hrm.domain.VipBase;
import cn.itsource.hrm.domain.VipRealinfo;
import cn.itsource.hrm.mapper.SsoMapper;
import cn.itsource.hrm.mapper.VipBaseMapper;
import cn.itsource.hrm.mapper.VipRealinfoMapper;
import cn.itsource.hrm.service.ISsoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 会员登录账号 服务实现类
 * </p>
 *
 * @author solargen
 * @since 2020-06-29
 */
@Service
public class SsoServiceImpl extends ServiceImpl<SsoMapper, Sso> implements ISsoService {
    @Autowired
    private VipBaseMapper vipBaseMapper;
    @Autowired
    private VipRealinfoMapper vipRealinfoMapper;

    /**
     * 手机号码注册
     * @param vo
     */
    @Override
    @Transactional
    public void telephoneReg(RegisterVo vo) {
        //sso表添加数据
        Sso sso = new Sso();
        sso.setPhone(vo.getTelephone());
        sso.setCreateTime(System.currentTimeMillis());
        //密码加密
        int length = RandomUtil.randomInt(10);
        String salt = RandomUtil.randomString(length);
        String password = SecureUtil.md5(vo.getPassword() + salt);
        sso.setPassword(password);
        sso.setSalt(salt);
        baseMapper.insert(sso);
        //用户基本信息表初始化数据
        VipBase vipBase = new VipBase();
        vipBase.setSsoId(sso.getId());
        vipBase.setCreateTime(System.currentTimeMillis());
        vipBase.setRegTime(sso.getCreateTime());
        vipBaseMapper.insert(vipBase);

        //用户实名认证表初始化数据
        VipRealinfo vipRealinfo = new VipRealinfo();
        vipRealinfo.setSsoId(sso.getId());
        vipRealinfo.setCreateTime(System.currentTimeMillis());
        vipRealinfo.setState(0);
        vipRealinfoMapper.insert(vipRealinfo);

     }

    /**
     * 用户登录
     * @param name 用户的邮箱或者手机号码
     * @return
     */
    @Override
    public Sso getByName(String name) {
        return baseMapper.selectByName(name);
    }

}
