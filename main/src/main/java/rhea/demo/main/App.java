package rhea.demo.main;

import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.spi.container.servlet.ServletContainer;
import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.tanukisoftware.wrapper.WrapperListener;
import org.tanukisoftware.wrapper.WrapperManager;
import rhea.demo.utility.SpringContextUtil;

/**
 * 描述 ：系统数据初始化
 *
 * @author : maozebing
 * @version : v1.00
 * @CreationDate : 2016/10/11 13:41
 * @Description :
 * @update : 修改人，修改时间，修改内容
 * @see :[相关类/方法]
 */
public class App implements WrapperListener {
    private static Logger logger = Logger.getLogger(App.class);

    public static void main(String[] args) {
        try {
            //加载spring配置文件
            SpringContextUtil.getInstance().getContext();

            //启动restful服务
            Server restServer = new Server(8090);
            ServletContextHandler restHandler = new ServletContextHandler();
            restServer.setHandler(restHandler);
            ResourceConfig resourceConfig = new PackagesResourceConfig("rhea.demo.api");
            resourceConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, true);
            resourceConfig.getProperties().put(
                    "com.sun.jersey.spi.container.ContainerResponseFilters",
                    "rhea.demo.utility.CrossFilter"
            );
            ServletHolder sh = new ServletHolder(new ServletContainer(resourceConfig));
            restHandler.addServlet(sh, "/api/v1.0/*");

            HandlerList handlers = new HandlerList();
            handlers.setHandlers(new Handler[]{restHandler});
            restServer.setHandler(handlers);

            //启动restful服务
            restServer.start();

            //启动自启动服务
            WrapperManager.start(new App(),args);
        } catch (Exception e) {
            logger.error("系统启动异常", e);
        }

    }

    @Override
    public Integer start(String[] strings) {
        System.out.println("start()");
        return null;
    }

    @Override
    public int stop(int i) {
        System.out.println("stop()");
        return 0;
    }

    @Override
    public void controlEvent(int i) {

    }
}
