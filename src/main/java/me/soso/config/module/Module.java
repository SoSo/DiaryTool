package me.soso.config.module;

public class Module {

    private String id;

    private String title;

    private Class<?> controllerClass;

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setControllerClass(Class<?> controllerClass) {
        this.controllerClass = controllerClass;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }
}
