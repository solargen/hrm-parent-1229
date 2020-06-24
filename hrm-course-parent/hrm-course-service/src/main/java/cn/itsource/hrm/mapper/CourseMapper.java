package cn.itsource.hrm.mapper;

import cn.itsource.hrm.domain.Course;
import cn.itsource.hrm.query.CourseQuery;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author solargen
 * @since 2020-06-17
 */
public interface CourseMapper extends BaseMapper<Course> {

    /**
     * 分页高级查询
     * 只要第一个参数是Page，mybatisplus会自动分页
     * 我们只需要玩高级查询
     * @param page
     * @param query
     * @return
     */
    IPage<Course> selectPageByQuery(Page<?> page, @Param("query") CourseQuery query);

    /**
     * 上线
     * @param ids
     * @param time
     */
    void online(@Param("ids") List<Long> ids, @Param("time") long time);

    /**
     * 下线
     * @param ids
     * @param time
     */
    void offline(@Param("ids") List<Long> ids, @Param("time") long time);
}
