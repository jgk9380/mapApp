package com.config.jpa.datasouce;


import org.springframework.beans.factory.annotation.Value;

/**
 * Created by jianggk on 2017/1/20.
 */
//@Configuration
public class ThirdDataSourceConfig {

    @Value("${spring.datasource.third.data-source-properties.INITIAL_CONTEXT_FACTORY}")
    String INITIAL_CONTEXT_FACTORY;
    @Value("${spring.datasource.third.data-source-properties.PROVIDER_URL}")
    String PROVIDER_URL;
    @Value("${spring.datasource.third.jndi-name}")
    String JNDI_NAME;
//    //TODO 网络不通
//    @Bean(name = "thirdDataSource")
//    public DataSource dataSource(@Value("${spring.datasource.third.data-source-properties.INITIAL_CONTEXT_FACTORY}") String INITIAL_CONTEXT_FACTORY,
//                                  @Value("${spring.datasource.third.data-source-properties.PROVIDER_URL}")     String PROVIDER_URL,
//                                  @Value("${spring.datasource.third.jndi-name}")     String JNDI_NAME )
//    {
//        Hashtable ht = new Hashtable();
//        ht.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
//        ht.put(Context.PROVIDER_URL, PROVIDER_URL);
//        Context context = null;
//        DataSource ds = null;
//        try {
//            context = new InitialContext(ht);
//            ds = (DataSource) context.lookup(JNDI_NAME);
//            return ds;
//        } catch (NamingException e) {
//            e.printStackTrace();
//        }
//        return ds;
//    }
//
//
//    @Bean(name = "thirdJdbcTemplate")
//    public JdbcTemplate thirdJdbcTemplate(
//            @Qualifier("thirdDataSource") DataSource dataSource) {
//        return new JdbcTemplate(dataSource);
//    }
}


//
//SpringBoot读取application.properties文件，通常有3种方式
//        1. @Value  例如：
//@Value("${spring.profiles.active}")
//private String profileActive;------相当于把properties文件中的spring.profiles.active注入到变量profileActive中
//        2. @ConfigurationProperties  例如：
//@Component
//@ConfigurationProperties(locations = "classpath:application.properties",prefix="test")
//public class TestProperties {
//    String url;
//    String key;
//}
//其他类中使用时，就可以直接注入该TestProperties 进行访问相关的值
//        3. 使用Enviroment   例如：
//private Enviroment env;
//        env.getProperty("test.url");
//        而env方式效率较低

//todo 远程配置weblogic数据源
//<bean id="DataSource" class="org.springframework.jndi.JndiObjectFactoryBean">
//<property name="jndiName">
//<value>此处为weblogic   jndi的名字</value>
//</property>
//
//<property name="resourceRef">
//<value>false</value>
//</property>
//<property name="jndiEnvironment">
//<props>
//
//<prop key="java.naming.provider.url">t3://服务器IP:端口号</prop>
//<prop key="java.naming.factory.initial">weblogic.jndi.WLInitialContextFactory</prop>
//</props>
//</property>
//</bean>
//todo 远程配置weblogic数据源



    //todo 连接调用远程weblogic JNDI数据源 含用户名密码

//<bean id="dataSource" class="org.springframework.jndi.JndiObjectFactoryBean">
//<property name="jndiName">
//<value>jdbc/cdcrip</value>
//</property>
//<!--必须配置jndiEnvironment，否者spring无法找到JNDI连接数据源 -->
//<property name="jndiEnvironment">
//<props>
//<!-- The value of Context.PROVIDER_URL -->
//<prop key="java.naming.provider.url">t3://远程IP:7001</prop>
//<prop key="java.naming.factory.initial">weblogic.jndi.WLInitialContextFactory</prop>
//<prop key="java.naming.security.principal">weblogic</prop>
//<prop key="java.naming.security.credentials">weblogic</prop>
//</props>
//</property>
//</bean>