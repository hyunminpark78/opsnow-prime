package opsnow.applcation.prime.test;

public class DefaultModel {
    private String name;
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void excuteCore() {
        System.out.println("Excute Core");
    }
}