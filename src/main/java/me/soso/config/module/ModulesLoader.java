package me.soso.config.module;

import java.util.List;

public interface ModulesLoader {
    public List<Module> loadModules(String location) throws Exception;
}
