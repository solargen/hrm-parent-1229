package cn.itsource.hrm.query;

/**
 * @author solargen
 * @version v1.0.0
 * @date 2020-06-16
 */
public class BaseQuery {

    private Integer pageNum;
    private Integer pageSize;

    private String keyword;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
