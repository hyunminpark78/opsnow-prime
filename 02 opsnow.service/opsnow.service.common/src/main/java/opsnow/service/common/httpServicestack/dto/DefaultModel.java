package opsnow.service.common.httpServicestack.dto;

import opsnow.service.common.httpServicestack.command.DefaultModelBase;

public class DefaultModel extends DefaultModelBase {
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

}