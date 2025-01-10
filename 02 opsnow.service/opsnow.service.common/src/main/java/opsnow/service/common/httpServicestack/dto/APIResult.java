package opsnow.service.common.httpServicestack.dto;

/**
 * 1. Create Date: 2025-01-04
 * 2. Creator: hyunmin.park@opsnow.com
 * 3. Description:
 */
public class APIResult<T> {
    public T data;
    public PageOption pageOption;

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setPageOption(PageOption pageOption) {
        this.pageOption = pageOption;
    }

    public PageOption getPageOption() {
        return pageOption;
    }
}
