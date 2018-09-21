package com.master.mybatis.utils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 查询参数
 */
public class Query extends LinkedHashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    // 每页条数
    private int limit;
    // 当前页码
    private int page;

    public Query(Map<String, Object> params) {

        if (!params.containsKey("page")) {
            params.put("page", 1);
        }

        if (!params.containsKey("limit")) {
            params.put("limit", 2);
        }

        this.putAll(params);

        // 分页参数
        this.limit = Integer.parseInt(params.get("limit").toString());
        this.page = Integer.parseInt(params.get("page").toString());

        this.put("offset", (page - 1) * limit);
        this.put("page", page);
        this.put("limit", limit);
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "Query{" +
                ", limit=" + limit +
                ", page=" + page +
                '}';
    }
}
