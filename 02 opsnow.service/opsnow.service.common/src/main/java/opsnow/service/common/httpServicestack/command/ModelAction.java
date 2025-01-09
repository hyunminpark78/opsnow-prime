package opsnow.service.common.httpServicestack.command;

import opsnow.service.common.httpServicestack.annotation.DefaultModelAnnotation;
import opsnow.service.common.httpServicestack.dto.DefaultModel;
import opsnow.service.common.httpServicestack.dto.DemoResult;

@DefaultModelAnnotation(defaultClass = DefaultModel.class)
public class ModelAction extends ActionBase<DefaultModel, DemoResult> {
    @Override
    public void init() {
        System.out.println("ModelAction init");
    }

    @Override
    protected DemoResult execute() {
        DemoResult result = new DemoResult();
        return result;
    }
}
