package opsnow.service.common.httpServicestack.dto;

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
