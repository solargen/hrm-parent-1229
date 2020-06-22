package cn.itsource.hrm.client;

import cn.itsource.hrm.client.impl.SystemdictionaryItemClientImpl;
import cn.itsource.hrm.domain.SystemdictionaryItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * 如果接口对应的Controller中类上有@RequestMapping
 * 我们
 *  1、@FeignClient中的path属性描述Controller上配置的路径
 *  2、不使用Controller上配置的路径，添加到每个方法匹配的路径上
 *
 */
@FeignClient(name = "system-service",path = "/systemdictionaryItem",fallback = SystemdictionaryItemClientImpl.class)
public interface SystemdictionaryItemClient {

    /**
     * 根据id查询数据字典明细
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    SystemdictionaryItem get(@PathVariable("id")Long id);

}
