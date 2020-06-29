package cn.itsource.hrm.controller;

import cn.itsource.hrm.doc.CourseDoc;
import cn.itsource.hrm.query.CourseDocQuery;
import cn.itsource.hrm.repository.CourseDocRepository;
import cn.itsource.hrm.util.AjaxResult;
import cn.itsource.hrm.util.PageList;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author solargen
 * @version v1.0.0
 * @date 2020-06-24
 */
@RestController
@RequestMapping("/course")
public class CourseDocController {

    @Autowired
    private CourseDocRepository repository;

    /**
     * 添加课程到es中
     * @param list
     * @return
     */
    @PostMapping("/addBatch")
    public AjaxResult add(@RequestBody List<CourseDoc> list){
        try {
            repository.saveAll(list);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("失败!"+e.getMessage());
        }
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @PostMapping("/deleteBatch")
    public AjaxResult delete(@RequestBody List<Long> ids){
        try {
            for (Long id : ids) {
                repository.deleteById(id);
            }
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("失败!"+e.getMessage());
        }
    }

    @PostMapping("/page")
    public PageList<CourseDoc> page(@RequestBody CourseDocQuery docQuery){

        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        //查询
        if(StringUtils.isNotEmpty(docQuery.getKeyword())){
            boolQueryBuilder.must(new MatchQueryBuilder("all",docQuery.getKeyword()));
        }
        if(docQuery.getCourseType()!=null){
            boolQueryBuilder.must(new TermQueryBuilder("courseTypeId",docQuery.getCourseType()));
        }
        if(docQuery.getTenantId()!=null){
            boolQueryBuilder.must(new TermQueryBuilder("tenantId",docQuery.getTenantId()));
        }
        //过滤
        List<QueryBuilder> filter = boolQueryBuilder.filter();
        RangeQueryBuilder rangeQueryBuilder = new RangeQueryBuilder("price");
        if(docQuery.getMaxPrice()!=null){
            rangeQueryBuilder.lte(docQuery.getMaxPrice());
        }
        if(docQuery.getMinPrice()!=null){
            rangeQueryBuilder.gte(docQuery.getMinPrice());
        }
        filter.add(rangeQueryBuilder);

        builder.withQuery(boolQueryBuilder);
        //排序
        String fieldName = "price";
        SortOrder order = SortOrder.ASC;

        if(StringUtils.isNotEmpty(docQuery.getColumnName())){
            switch (docQuery.getColumnName()){
                case "xp":
                    fieldName = "startTime";
                    break;
                case "jg":
                    fieldName = "price";
                    break;
            }
        }
        if(StringUtils.isNotEmpty(docQuery.getOrderType())){
            switch (docQuery.getOrderType()){
                case "asc":
                    order = SortOrder.ASC;
                    break;
                case "desc":
                    order = SortOrder.DESC;
                    break;
            }
        }

        builder.withSort(new FieldSortBuilder(fieldName).order(order));
        //分页
        builder.withPageable(PageRequest.of(docQuery.getPageNum()-1,docQuery.getPageSize()));

        Page<CourseDoc> docPage = repository.search(builder.build());
        PageList<CourseDoc> pageList = new PageList<>();
        pageList.setTotal(docPage.getTotalElements());
        pageList.setRows(docPage.getContent());

        return pageList;
    }


}
