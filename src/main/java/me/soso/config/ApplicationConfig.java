package me.soso.config;

import me.soso.config.database.Database;
import me.soso.config.database.LeanCloud;
import me.soso.config.module.Module;
import me.soso.config.module.XmlModulesLoader;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class ApplicationConfig {

    private static class SingletonHolder {
        private static final ApplicationConfig SINGLETON = new ApplicationConfig();
    }
    private ApplicationConfig() {}
    public static ApplicationConfig shareInstance(){
        return SingletonHolder.SINGLETON;
    }

    private List<Module> modules;

    public static void load() throws Exception {
        load("applicationConfig.xml");
    }

    public static void load(String location) throws Exception {
        SAXReader reader = new SAXReader();
        URL resource = shareInstance().getClass().getClassLoader().getResource(location);
        File file = new File(resource.getFile());
        Document document = reader.read(file);
        Element root = document.getRootElement();

        Iterator it = root.elementIterator();
        while (it.hasNext()) {
            Element element = (Element) it.next();

            switch (element.getName()) {
                case "modules": {
                    loadModules(element);
                    break;
                }
                case "database": {
                    connectToDatabase(element);
                    break;
                }
            }
        }
    }

    private static void loadModules(Element element) throws Exception {
        String path = element.attributeValue("path");
        if (path != null && path.length() > 0) {
            XmlModulesLoader loader = new XmlModulesLoader();
            shareInstance().modules = loader.loadModules(path);
        }
    }

    private static void connectToDatabase(Element element) throws Exception {
        String path = element.attributeValue("path");
        String type = element.attributeValue("type");

        Properties properties = new Properties();
        if (path != null && path.length() > 0) {
            properties.load(shareInstance().getClass().getClassLoader().getResourceAsStream(path));
        }

        switch (type) {
            case "leancloud": {
                String appID = properties.get("appID").toString();
                String appKey = properties.get("appKey").toString();
                String masterKey = properties.get("masterKey").toString();

                if (appID == null || appKey == null || masterKey == null) {
                    throw new IllegalArgumentException("数据库参数配置错误");
                }

                Database database = new LeanCloud(appID, appKey, masterKey);
                database.connect();
                break;
            }
        }
    }

    public List<Module> getModules() {
        return modules;
    }
}
