package rhea.demo.utility;

import org.springframework.context.support.GenericXmlApplicationContext;

/**
 * 描述 ：加载spring配置文件以及获取bean对象
 *
 * @author : mzb
 * @version : v1.00
 * @CreationDate : 16-5-30 下午12:05
 * @Description :
 * @update : 修改人，修改时间，修改内容
 * @see :[相关类/方法]
 */
public class SpringContextUtil {
    private static final String STATION_CONTEXT = "applicationContext.xml";
    private static GenericXmlApplicationContext context;
    private static SpringContextUtil springContextUtil = null;

    public static SpringContextUtil getInstance() {
        if (springContextUtil == null) {
            springContextUtil = new SpringContextUtil();
        }
        return springContextUtil;
    }

    /**
     * 获取bean对象
     *
     * @param beanId bean的id
     * @return bean对象
     */
    public Object getBean(String beanId) {
        if (beanId == null) {
            throw new NullPointerException();
        }
        return context.getBean(beanId);
    }

    /**
     * 加载Spring配置文件
     */
    public void getContext() {
        context = new GenericXmlApplicationContext();
        context.setValidating(false);
        context.load("classpath:" + STATION_CONTEXT);
        context.refresh();
    }
}
