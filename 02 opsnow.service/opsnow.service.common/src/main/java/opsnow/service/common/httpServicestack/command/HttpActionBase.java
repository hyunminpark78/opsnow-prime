package opsnow.service.common.httpServicestack.command;

import jakarta.servlet.http.HttpServletRequest;
import opsnow.framework.core.text.Encoder;
import opsnow.service.common.httpServicestack.dto.APIResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.HttpInputMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

/**
 * 1. Create Date: 2025-01-04
 * 2. Creator: hyunmin.park@opsnow.com
 * 3. Description:
 */
@Component
public abstract class HttpActionBase<TRequest, TResponse> extends ActionBase<TRequest, TResponse> {

    @Autowired
    private HttpServletRequest request;

    protected TRequest model;

    private Map<String, String> queryMap;

    public void initAction(TRequest model) {
        this.model = model;
    }

    protected Map<String, String> getQueryMap() {
        if (queryMap == null) {
            this.queryMap = Encoder.fromQueryString(this.request.getQueryString());
        }
        return queryMap;
    }

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

        return buildResult(apiResult);
    }

    private APIResult<TResponse> buildResult(APIResult<TResponse> result) {
        return result;
    }
}
