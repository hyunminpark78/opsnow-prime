package opsnow.service.common.httpServicestack.command;

public abstract class ActionBase<TRequest, TResponse> extends HttpActionBase<TRequest, TResponse> {
    protected  TRequest model;

    public void setModel(TRequest model) {
        this.model = model;
    }


}
