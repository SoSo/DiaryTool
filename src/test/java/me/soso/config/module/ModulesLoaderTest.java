package me.soso.config.module;

import org.junit.Test;

public class ModulesLoaderTest {
    @Test
    public void test() throws Exception {
        XmlModulesLoader loader = new XmlModulesLoader();
        loader.loadModules("config/moduleConfig.xml");
    }
}
