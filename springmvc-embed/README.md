# SpringMVC零XML配置

`Servlet 3.0` 规范定义了`javax.servlet.ServletContainerInitializer`接口，允许第三方库在 Web 容器启动时初始化代码。服务发现机制自动检测到实现类以后执行对应的`onStartup`的方法。

Spring 遵循 `Servlet 3.0` 规范，在 `spring-web` 模块的 JAR 文件中提供了配置文件，文件指向`org.springframework.web.SpringServletContainerInitializer`，通过@HandlesTypes注解扫描所有`WebApplicationInitializer`实现类，调用他们的`onStartup`方法，完成初始化。以编程方式配置 Servlet、Filter、Listener 等，替代传统的 web.xml。

> 为什么不是Tomcat 规范而是Servlet 规范？
>
> 因为市面上有很多web容器，例如jetty。Servlet 规范是 Java EE（现在为 Jakarta EE）的标准，确保了跨容器的兼容性。如果依赖 Tomcat 特定特性，切换容器时代码可能无法工作。



## 定义Controller的三种方式

1. 使用@Controller注解配合扫描器。

2. 实现Controller接口，重写handleRequest方法。

3. 实现HttpRequestHandler接口，重写 handleRequest方法，这个实现方式与servlet 基本一致。

   > 后面两种方式都需要在mvc中配置映射地址。

   

## ModelMap与ModelAndView区别

| 特性         | ModelMap                                           | ModelAndView                                                 |
| :----------- | :------------------------------------------------- | :----------------------------------------------------------- |
| **本质**     | 一个纯粹的**数据模型（Model）**，本质上是一个Map。 | 一个**封装体**，同时包含了**视图（View）** 和**数据模型（Model）**。 |
| **职责**     | 仅用于存放需要渲染到视图上的数据。                 | 既负责指定跳转的视图，也负责存放数据。                       |
| **视图返回** | 需要与**方法返回String（视图名）** 配合使用。      | 它自己就是返回值，**方法返回ModelAndView对象**。             |
| **灵活性**   | 更轻量、更常用，特别是在仅需要传递数据时。         | 更传统、更全面，可以在返回前对视图和模型进行集中设置。       |

```java
@GetMapping("/index")
public String index(ModelMap modelMap) { // Spring注入ModelMap
    // 向数据模型中添加数据
    modelMap.addAttribute("name", "admin");
    modelMap.addAttribute("message", "Hello, World!");
    // 返回视图名，DispatcherServlet会根据视图解析器找到真正的页面，如 /WEB-INF/views/index.jsp
    return "index";
}
```

```java
@GetMapping("/index")
public String index(Model model) { // 使用Model接口，更为通用
    model.addAttribute("name", "admin");
    return "index";
}
```

