package opsnow.service.common.httpServicestack.command;

import jakarta.servlet.http.HttpServletRequest;
import opsnow.service.common.httpServicestack.annotation.DefaultModelAnnotation;
import opsnow.service.common.httpServicestack.dto.*;
import opsnow.service.common.httpServicestack.dto.SampleResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

/**
 * 1. Create Date: 2025-01-04
 * 2. Creator: hyunmin.park@opsnow.com
 * 3. Description:
 */
@Component
@DefaultModelAnnotation(defaultClass = SampleModel.class)
public class SampleAction extends HttpActionBase<SampleModel, SampleResult> {
    @Autowired
    private HttpServletRequest request;

    @Override
    protected void init() {
//        String q = request.getQueryString();
//        String a = ";";
    }

    @Override
    protected boolean preExecute() {
        // Adding Conditions to the execute Method
        return true;
    }

    @Override
    protected void postExecute() {
        // Post-Execution Processing for execute
    }

    @Override
    protected SampleResult execute() {
        String name = this.model.getName();

        SampleResult result = new SampleResult();
        result.setMessage(name);

        String queryA = this.getQueryMap().get("a");

        return result;
    }
}
