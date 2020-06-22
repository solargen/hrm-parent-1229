package cn.itsource.hrm.service;

import cn.itsource.hrm.domain.SystemdictionaryItem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author solargen
 * @since 2020-06-19
 */
public interface ISystemdictionaryItemService extends IService<SystemdictionaryItem> {

    /**
     * 根据目录查询明细
     * @param course_level
     * @return
     */
    List<SystemdictionaryItem> getBySn(String course_level);
}
