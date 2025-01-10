package opsnow.service.common.httpServicestack.command;

import opsnow.service.common.httpServicestack.dto.APIResult;

/**
 * 1. Create Date: 2025-01-04
 * 2. Creator: hyunmin.park@opsnow.com
 * 3. Description:
 */
public abstract class HttpActionBase<TRequest, TResponse> extends CommandBase<TRequest, TResponse> {
    @Override
    protected void init() {
        System.out.println("ActionBase init");
    }

    @Override
    protected boolean preExecute() {
        return true;
    }

    @Override
    protected TResponse execute() {
        return null;
    }

    @Override
    protected void postExecute() {}

    @Override
    public APIResult<TResponse> executeCore() {
        APIResult<TResponse> apiResult = new APIResult<>();

        init();

        if (preExecute()) {
            apiResult.data = execute();
        }
        postExecute();

        return apiResult;
    }
}
