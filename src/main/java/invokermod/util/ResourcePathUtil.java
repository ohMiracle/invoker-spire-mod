package invokermod.util;

import java.net.URL;
import java.util.logging.Logger;

public class ResourcePathUtil {

    static final Logger LOGGER = Logger.getLogger(ResourcePathUtil.class.getName());

    public static String getPath(String parentPath, String name) {
        String path = parentPath + "/" + name;
        boolean success = true;
        URL url = ResourcePathUtil.class.getClassLoader().getResource(path);
        if (url == null) {
            success = false;
            LOGGER.info("查找静态资源：" + path + "，结果：失败");
            throw new RuntimeException("找不到静态资源：" + path);
        }
        String file = url.getFile();
        success = file != null;
        LOGGER.info("查找静态资源：" + path + "，结果：" + (success ? "成功" : "失败"));
        return path;
    }

}

