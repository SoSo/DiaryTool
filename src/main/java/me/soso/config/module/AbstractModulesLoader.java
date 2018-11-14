package me.soso.config.module;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractModulesLoader implements ModulesLoader {
    private List<Module> moduleList;

    protected AbstractModulesLoader() {
        this.moduleList = new ArrayList<Module>();
    }

    public List<Module> getModuleList() {
        return moduleList;
    }
}
