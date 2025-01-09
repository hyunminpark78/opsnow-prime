package opsnow.service.common.httpServicestack.command;

import opsnow.service.common.httpServicestack.dto.APIResult;

public abstract class CommandBase<TRequest, TResponse> {
    protected void init() {
        System.out.println("ActionBase init");
    }

    protected abstract TResponse execute();

    protected abstract APIResult<TResponse> executeCore();
}
