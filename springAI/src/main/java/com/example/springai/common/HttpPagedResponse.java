package com.example.springai.common;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Paged Restful API 返回结果
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class HttpPagedResponse<T> extends HttpResponse<List<T>> {

    /**
     * 数据总数（仅适用数据分页查询场景）
     */
    private Long total;

    /**
     * 当前页，大于等于1（仅适用数据分页查询场景）
     */
    private Integer page;

    /**
     * 页大小，大于等于1（仅适用数据分页查询场景）
     */
    private Integer pageSize;

    public Long getTotal() {
        return total;
    }

    public Integer getPage() {
        return page;
    }

    public Integer getPageSize() {
        return pageSize;
    }


    public HttpPagedResponse(String code, PagedResult<T> data, String message, int httpStatus) {
        super(code, message, httpStatus);

        if (data != null) {
            this.setData(data.getData());
            this.total = data.getTotal();
            this.page = data.getPage();
            this.pageSize = data.getPageSize();
        }
    }

}
