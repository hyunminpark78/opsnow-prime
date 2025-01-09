package opsnow.service.common.httpServicestack.command;

import opsnow.service.common.httpServicestack.dto.APIResult;

public abstract class HttpActionBase<TRequest, TResponse> extends CommandBase<TRequest, TResponse> {
    @Override
    public APIResult<TResponse> executeCore() {
        init();

        APIResult<TResponse> apiResult = new APIResult<>();
        apiResult.data = execute();
        return apiResult;
    }
}
