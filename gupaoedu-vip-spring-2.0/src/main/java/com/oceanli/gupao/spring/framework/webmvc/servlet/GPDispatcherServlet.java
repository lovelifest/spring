package com.oceanli.gupao.spring.framework.webmvc.servlet;

import com.oceanli.gupao.spring.framework.annotation.GPController;
import com.oceanli.gupao.spring.framework.annotation.GPRequestMapping;
import com.oceanli.gupao.spring.framework.context.support.GPApplicationContext;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Pattern;

@Slf4j
public class GPDispatcherServlet extends HttpServlet {

    private static final String INIT_PARAM = "contextConfigLocation";

    private List<GPHandlerMapping> handlerMappings = new ArrayList<>();

    private Map<GPHandlerMapping, GPHandlerAdapter> handlerAdapters = new HashMap<>();

    private List<GPViewResolver> viewResolvers = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            doDispatch(req, resp);
        } catch (Exception e) {
            resp.getWriter().write("<font size='25' color='blue'>500 Exception</font><br/>Details:<br/>" + Arrays.toString(e.getStackTrace()).replaceAll("\\[|\\]","")
                    .replaceAll("\\s","\r\n") + "<fontcolor='green'><i>Copyright@GupaoEDU</i></font>");
            e.printStackTrace();
        }
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception{

        //根据用户的URL获取一个handler
        GPHandlerMapping handlerMapping = getHandler(req);
        if (handlerMapping == null) {
            return;
        }
        //跟进handlermapping找到对应的adapter
        GPHandlerAdapter ha = getHandlerAdapter(handlerMapping);
        if (ha == null) {
            return;
        }
        //执行调用方法，返回modelandview
        GPModelAndView mv = ha.handle(req, resp, handlerMapping);
        //输出结果
        processDispatchResult(req, resp, handlerMapping, mv);

    }

    private void processDispatchResult(HttpServletRequest req, HttpServletResponse resp, GPHandlerMapping handlerMapping, GPModelAndView mv)
    throws Exception{

        //调用 viewResolver 的 resolveView 方法
        if(null == mv){ return;}
        if (this.viewResolvers.isEmpty()) {
            return;
        }
        for (GPViewResolver viewResolver: viewResolvers) {
            String viewName = viewResolver.getViewName();
            if (viewName.equals(mv.getViewName())) {
                GPView gpView = viewResolver.resolveViewName(viewName, null);
                gpView.render(mv, req, resp);
            }


        }
    }

    private GPHandlerAdapter getHandlerAdapter(GPHandlerMapping handlerMapping) {
        return this.handlerAdapters.get(handlerMapping);
    }

    private GPHandlerMapping getHandler(HttpServletRequest req) {

        if (this.handlerMappings.isEmpty()) return null;

        String url = req.getRequestURI();
        url = url.replaceAll(req.getContextPath(), "")
                .replaceAll("/+", "/");
        for (GPHandlerMapping handlerMapping : this.handlerMappings) {
            if (handlerMapping.getPattern().matcher(url).matches()) {
                return handlerMapping;
            }
        }
        return null;
    }

    @Override
    public void init(ServletConfig config) throws ServletException {

        try {
            //相当于把 IOC 容器初始化了
            GPApplicationContext context = new GPApplicationContext(config.getInitParameter(INIT_PARAM));
            initStrategies(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initStrategies(GPApplicationContext context) {

        //有九种策略
        // 针对于每个用户请求，都会经过一些处理的策略之后，最终才能有结果输出
        // 每种策略可以自定义干预，但是最终的结果都是一致

        // ============= 这里说的就是传说中的九大组件 ================
        //文件上传解析，如果请求类型是 multipart 将通过MultipartResolver 进行文件上传解析
        initMultipartResolver(context);
        //本地化解析
        initLocaleResolver(context);
        //主题解析
        initThemeResolver(context);
        /** 我们自己会实现 */
        //GPHandlerMapping 用来保存 Controller 中配置的 RequestMapping 和 Method 的一个对应关系
        //通过 HandlerMapping，将请求映射到处理器
        initHandlerMappings(context);
        /** 我们自己会实现 */
        //HandlerAdapters 用来动态匹配 Method 参数，包括类转换，动态赋值
        //通过 HandlerAdapter 进行多类型的参数动态匹配
        initHandlerAdapters(context);
        //如果执行过程中遇到异常，将交给HandlerExceptionResolver 来解析
        initHandlerExceptionResolvers(context);
        //直接解析请求到视图名
        initRequestToViewNameTranslator(context);
        /** 我们自己会实现 */
        //通过 ViewResolvers 实现动态模板的解析
        //自己解析一套模板语言
        //通过 viewResolver 解析逻辑视图到具体视图实现
        initViewResolvers(context);
        //flash 映射管理器
        initFlashMapManager(context);
    }

    private void initMultipartResolver(GPApplicationContext context) {

    }

    private void initLocaleResolver(GPApplicationContext context) {

    }

    private void initThemeResolver(GPApplicationContext context) {

    }

    //将 Controller 中配置的 RequestMapping 和 Method 进行一一对应
    private void initHandlerMappings(GPApplicationContext context) {

        String[] beanDefinitionNames = context.getBeanDefinitionNames();

        try {

            for (String beanName : beanDefinitionNames) {

                Object controller = context.getBean(beanName);
                Class<?> clazz = controller.getClass();
                if (!clazz.isAnnotationPresent(GPController.class)) {
                    continue;
                }
                String baseUrl = "";
                if (clazz.isAnnotationPresent(GPRequestMapping.class)) {
                    GPRequestMapping annotation = clazz.getAnnotation(GPRequestMapping.class);
                    baseUrl = annotation.value();
                }
                Method[] methods = clazz.getMethods();
                for (Method m: methods) {
                    if (! m.isAnnotationPresent(GPRequestMapping.class)) {
                        continue;
                    }
                    GPRequestMapping mAnnotation = m.getAnnotation(GPRequestMapping.class);
                    String regex = ("/" + baseUrl + "/" + mAnnotation.value().
                            replaceAll("\\*", "\\.*")).replaceAll("/+", "/");
                    Pattern pattern = Pattern.compile(regex);
                    GPHandlerMapping handlerMapping = new GPHandlerMapping(controller, m, pattern);
                    handlerMappings.add(handlerMapping);
                    log.info("Mapping: " + regex + " , " + m);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initHandlerAdapters(GPApplicationContext context) {

        if (this.handlerMappings.isEmpty()) return;
        for (GPHandlerMapping gphandlerMapping : this.handlerMappings) {
            handlerAdapters.put(gphandlerMapping, new GPHandlerAdapter());
        }

    }

    private void initHandlerExceptionResolvers(GPApplicationContext context) {

    }

    private void initRequestToViewNameTranslator(GPApplicationContext context) {
    }

    private void initViewResolvers(GPApplicationContext context) {
        //在页面敲一个 http://localhost/first.html
        //解决页面名字和模板文件关联的问题
        String templateRoot =context.getConfig().getProperty("templateRoot");
        String templateRootPath = this.getClass().getClassLoader().getResource(templateRoot).getFile();
        File templateRootDir = new File(templateRootPath);
        for (File f : templateRootDir.listFiles()) {

            viewResolvers.add(new GPViewResolver(templateRoot, f.getName()));
        }
    }

    private void initFlashMapManager(GPApplicationContext context) {
    }
}
