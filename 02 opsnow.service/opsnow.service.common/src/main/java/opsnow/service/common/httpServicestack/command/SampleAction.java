package opsnow.service.common.httpServicestack.command;

import opsnow.service.common.httpServicestack.annotation.DefaultModelAnnotation;
import opsnow.service.common.httpServicestack.dto.*;
import opsnow.service.common.httpServicestack.dto.SampleResult;

/**
 * 1. Create Date: 2025-01-04
 * 2. Creator: hyunmin.park@opsnow.com
 * 3. Description:
 */
@DefaultModelAnnotation(defaultClass = SampleModel.class)
public class SampleAction extends HttpActionBase<SampleModel, SampleResult> {
    @Override
    protected void init() {
    }

    @Override
    protected boolean preExecute() {
        return true;
    }

    @Override
    protected void postExecute() {}

    @Override
    protected SampleResult execute() {
        String name = this.model.getName();

        SampleResult result = new SampleResult();
        result.setMessage(name);

        String queryA = this.getQueryMap().get("a");

        return result;
    }
}
