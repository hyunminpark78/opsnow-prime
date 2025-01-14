package opsnow.service.common.httpServicestack.command;

import opsnow.service.common.httpServicestack.dto.APIResult;

/**
 * 1. Create Date: 2025-01-04
 * 2. Creator: hyunmin.park@opsnow.com
 * 3. Description:
 * @param <TRequest>
 * @param <TResponse>
 */
public abstract class CommandBase<TRequest, TResponse> {

    protected abstract TResponse execute();


}
