package opsnow.service.common.httpServicestack.command;

import opsnow.service.common.httpServicestack.dto.PageOption;

public class DefaultModelBase {
    private PageOption pageOption;
    public PageOption getPageOption() {
        return pageOption;
    }
    public void setPageOption(PageOption pageOption) {
        this.pageOption = pageOption;
    }
}
