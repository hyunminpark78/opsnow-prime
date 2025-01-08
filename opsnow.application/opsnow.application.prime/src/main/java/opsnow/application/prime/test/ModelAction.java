package opsnow.application.prime.test;

@DefaultModelAnnotation(defaultClass = DefaultModel.class)
public class ModelAction<T> extends ActionBase<T> {
    @Override
    public void init() {
        System.out.println("ModelAction init");
    }
}
