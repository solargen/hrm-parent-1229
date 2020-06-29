package cn.itsource.hrm.client.impl;

import cn.itsource.hrm.client.CourseDocClient;
import cn.itsource.hrm.doc.CourseDoc;
import cn.itsource.hrm.query.CourseDocQuery;
import cn.itsource.hrm.util.AjaxResult;
import cn.itsource.hrm.util.PageList;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author solargen
 * @version v1.0.0
 * @date 2020-06-24
 */
@Component
public class CourseDocClientImpl implements CourseDocClient {
    @Override
    public AjaxResult add(List<CourseDoc> list) {
        return AjaxResult.me().setSuccess(false).setMessage("失败");
    }

    @Override
    public AjaxResult delete(List<Long> ids) {
        return AjaxResult.me().setSuccess(false).setMessage("失败!");
    }

    @Override
    public PageList<CourseDoc> page(CourseDocQuery docQuery) {
        return null;
    }
}
