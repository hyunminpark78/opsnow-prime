package opsnow.applcation.prime.test;

import org.springframework.http.HttpInputMessage;

public class ActionBase<T> {
    protected  T model;

    public void setModel(HttpInputMessage inputMessage) {
        //this.model = model;
        String a = "";
    }

    protected void executeCore() {

    }
}
