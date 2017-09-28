package com.config.jpa.datasouce;



/**
 * Created by jianggk on 2017/1/20.
 */
//@Configuration
public class ThirdDataSourceConfig {

//    @Value("${spring.datasource.third.data-source-properties.INITIAL_CONTEXT_FACTORY}")
//    String INITIAL_CONTEXT_FACTORY;
//    @Value("${spring.datasource.third.data-source-properties.PROVIDER_URL}")
//    String PROVIDER_URL;
//    @Value("${spring.datasource.third.jndi-name}")
//    String JNDI_NAME;
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