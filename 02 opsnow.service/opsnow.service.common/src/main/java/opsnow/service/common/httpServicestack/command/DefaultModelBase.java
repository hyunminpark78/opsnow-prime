package opsnow.service.common.httpServicestack.command;

import opsnow.service.common.httpServicestack.dto.PageOption;

/**
 * 1. Create Date: 2025-01-04
 * 2. Creator: hyunmin.park@opsnow.com
 * 3. Description:
 */
public class DefaultModelBase {
    private PageOption pageOption;
    public PageOption getPageOption() {
        return pageOption;
    }
    public void setPageOption(PageOption pageOption) {
        this.pageOption = pageOption;
    }
}
