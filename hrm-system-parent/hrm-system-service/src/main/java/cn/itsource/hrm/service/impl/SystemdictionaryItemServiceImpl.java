package cn.itsource.hrm.service.impl;

import cn.itsource.hrm.domain.Systemdictionary;
import cn.itsource.hrm.domain.SystemdictionaryItem;
import cn.itsource.hrm.mapper.SystemdictionaryItemMapper;
import cn.itsource.hrm.mapper.SystemdictionaryMapper;
import cn.itsource.hrm.service.ISystemdictionaryItemService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author solargen
 * @since 2020-06-19
 */
@Service
public class SystemdictionaryItemServiceImpl extends ServiceImpl<SystemdictionaryItemMapper, SystemdictionaryItem> implements ISystemdictionaryItemService {

    @Autowired
    private SystemdictionaryMapper systemdictionaryMapper;

    /**
     * 根据目录查询明细
     * @param sn
     * @return
     */
    @Override
    public List<SystemdictionaryItem> getBySn(String sn) {
        Systemdictionary systemdictionary = systemdictionaryMapper.selectOne(new QueryWrapper<Systemdictionary>()
                .eq("sn", sn));
        //通过业务代码规避空指针异常，运行时异常，通常不捕获
        if(systemdictionary==null){
            return null;
        }
        return baseMapper.selectList(new QueryWrapper<SystemdictionaryItem>()
                .eq("parent_id",systemdictionary.getId()));
    }
}
