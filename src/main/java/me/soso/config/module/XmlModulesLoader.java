package me.soso.config.module;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XmlModulesLoader extends AbstractModulesLoader {

    @Override
    public List<Module> loadModules(String location) throws Exception {
        SAXReader reader = new SAXReader();
        Document document = reader.read(this.getClass().getClassLoader().getResourceAsStream(location));
        Element root = document.getRootElement();

        Iterator it = root.elementIterator();
        while (it.hasNext()) {
            Element element = (Element) it.next();

            String id = element.attributeValue("id");
            String title = element.attributeValue("title");
            String className = element.attributeValue("class");

            if (id == null || id.length() == 0)
                throw new IllegalArgumentException("解析模块xml错误，" + element.getName() + " id 参数不能为空");
            if (title == null || title.length() == 0)
                throw new IllegalArgumentException("解析模块xml错误，" + element.getName() + " title 参数不能为空");
            if (className == null || className.length() == 0)
                throw new IllegalArgumentException("解析模块xml错误，" + element.getName() + " class 参数不能为空");

            Module module = new Module();
            module.setId(id);
            module.setTitle(title);

            Class cls = Class.forName(className);
            module.setControllerClass(cls);

            this.getModuleList().add(module);
        }

        return getModuleList();
    }
}
