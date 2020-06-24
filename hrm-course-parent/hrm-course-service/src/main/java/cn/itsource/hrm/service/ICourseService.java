package cn.itsource.hrm.service;

import cn.itsource.hrm.controller.vo.CourseAddVo;
import cn.itsource.hrm.domain.Course;
import cn.itsource.hrm.query.CourseQuery;
import cn.itsource.hrm.util.PageList;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author solargen
 * @since 2020-06-17
 */
public interface ICourseService extends IService<Course> {

    /**
     * 课程分页高级查询
     * @param query
     * @return
     */
    PageList<Course> page(CourseQuery query);

    /**
     * 添加课程的基本信息
     * @param courseAddVo
     */
    void add(CourseAddVo courseAddVo);

    /**
     * 上线
     * @param ids
     */
    void online(List<Long> ids);

    /**
     * 下线
     * @param ids
     */
    void offline(List<Long> ids);
}
