package opsnow.service.common.httpServicestack.command;

import opsnow.service.common.httpServicestack.annotation.DefaultModelAnnotation;
import opsnow.service.common.httpServicestack.dto.DefaultModel;
import opsnow.service.common.httpServicestack.dto.DemoResult;

/**
 * 1. Create Date: 2025-01-04
 * 2. Creator: hyunmin.park@opsnow.com
 * 3. Description:
 */
@DefaultModelAnnotation(defaultClass = DefaultModel.class)
public class ModelAction extends ActionBase<DefaultModel, DemoResult> {
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
    protected DemoResult execute() {
        String name = this.model.getName();

        DemoResult result = new DemoResult();
        result.setMessage(name);
        return result;
    }
}
