# SpringMVC的零配置（embed）

原理就是tomcat的SPI的技术。通过spi机制，tomcat会扫描所有jar下的/META-INF/services/javax.servlet.ServletContainerInitializer文件， tomcat 启动的时候会调用 onStartup方法，因为servlet 3.0的一个新规范，跟tomcat没关系，tomcat是规范的实现者之一，为什么不是tomcat规范而是servlet规范？因为市面上有很多web容器，例如jetty。如果你是web容器的规范，如果换了容器，代码将不再适用。

## controller定义三种方式

1. 实现Controller接口,重写handleRequest方法

2. 实现HttpRequestHandler接口，重写 handleRequest方法。这个实现方式与servlet 基本一致

3. 使用@Controller注解配合扫描器