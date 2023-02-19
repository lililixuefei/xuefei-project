package com.xuefei.log.response;

/**
 * @version 1.0
 * @author: xuefei
 * @date: 2021/4/15 7:15 下午
 */
public class PagingResp {
    private Integer offset;
    private Integer limit;
    private Integer total;
    private Boolean hasMore;
    private Long timestamp;


    public PagingResp() {
        // 默认构造函数也给出来
    }

    public PagingResp(Integer offset, Integer limit) {
        this.offset = offset;
        this.limit = limit;
    }

    public PagingResp(Integer offset, Integer limit, Integer total, Boolean hasMore) {
        this.offset = offset;
        this.limit = limit;
        this.total = total;
        this.hasMore = hasMore;
        this.timestamp = timestamp;
    }

    public PagingResp(Integer offset, Integer limit, Integer total, Boolean hasMore, Long timestamp) {
        this.offset = offset;
        this.limit = limit;
        this.total = total;
        this.hasMore = hasMore;
        this.timestamp = timestamp;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getOffset() {
        return this.offset;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getLimit() {
        return this.limit;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getTotal() { return this.total; }

    public void setHasMore(Boolean hasMore) {
        this.hasMore = hasMore;
    }

    public Boolean getHasMore() {
        return this.hasMore;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getTimestamp() {
        return this.timestamp;
    }


}
