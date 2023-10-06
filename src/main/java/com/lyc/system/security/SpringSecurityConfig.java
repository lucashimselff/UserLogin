package com.lyc.system.security;


import com.lyc.system.filter.JwtAuthenticationTokenFilter;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

//这个加不加无所谓
@Configuration
//开启security自定义配置
@EnableWebSecurity
////开启 Controller层的访问方法权限，与注解@PreAuthorize("hasRole('ROLE_admin')")配合，会拦截注解了@PreAuthrize注解的配置
//// 想要@PreAuthorize正确执行 ，权限关键字必须带前缀 ROLE_  ，后面的部分可以随便写！！！！靠，琢磨了4小时了 ，终于找到原因了
@EnableGlobalMethodSecurity(prePostEnabled = true)
//, securedEnabled = true
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //关闭csrf
                .csrf().disable()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 对于登录接口 允许匿名访问
                .antMatchers("/user/login").anonymous()
//                .antMatchers("/hello").permitAll()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
//    @Test
//    public void encode(){
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String code = passwordEncoder.encode("1234");
//        System.out.println(code);
//        System.out.println(passwordEncoder.matches("1234", "$2a$10$lJw2mqPSaKc9V32BWFjZQO4KZDdpB.HCThOSRNeg/2gEoIJ1u/aKq"));
//    }

    //实例自定义登录校验接口 【内部有 数据库查询】
//    @Autowired
//    private com.lyc.system.demoLogin.service.impl.UserService userService;
//
//    //忽略拦截的静态文件路径
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring()
//                .antMatchers(
//                        "/js/**",
//                        "/css/**",
//                        "/img/**",
//                        "/webjars/**");
//    }

    //拦截规则设置
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                //允许基于使用HttpServletRequest限制访问
//                //即授权请求设置
//                .authorizeRequests()
//                //设置不拦截页面，可直接通过，路径访问 "/", "/index",  则不拦截,
//                .antMatchers("/", "/home", "/login","/hello")
//                //是允许所有的意思
//                .permitAll()
////                //访问 /hai 需要admin权限 ，无权限则提示 403
//                .antMatchers("/insert").hasAuthority("ROLE_admin")
////                //访问 /kk 需要admin或user权限 ，无权限则提示 403
////                .antMatchers("/kk").hasAnyAuthority("admin", "user")
////                //路径/admin/**所有的请求都需要admin权限 ，无权限则提示 403
////                .antMatchers("/admin/**").hasAuthority("admin")
//                //其他页面都要拦截，【需要在最后设置这个】
//                .anyRequest().authenticated()
//                .and()
//                //设置自定义登录页面
//                //即开启登录设置
//                .formLogin()
//                //指定自定义登录页面的访问虚拟路径
//                .loginPage("/login")
//                .permitAll()
//                .and()
////        添加退出登录支持。当使用WebSecurityConfigurerAdapter时，这将自动应用。默认情况是，访问URL”/ logout”，使HTTP Session无效
////        来清除用户，清除已配置的任何#rememberMe()身份验证，清除SecurityContextHolder，然后重定向到”/login?success”
//                //即开启登出设置
//                .logout()
////                //指定的登出操作的虚拟路径，需要以post方式请求这个 http://localhost:5500/mylogout 才可以登出 ，也可以直接清除用户认证信息达到登出目的
////                .logoutUrl("/mylogout")
//                //使httpsession失效
//                .invalidateHttpSession(true)
//                //清除认证信息
//                .clearAuthentication(true)
//                //登出请求匹配器，新建一个蚂蚁路径请求匹配器 ,与 .logoutUrl("/mylogout")效果一样
//                .logoutRequestMatcher(new AntPathRequestMatcher("/mylogout"))
//                //登出成功后访问的地址
//                .logoutSuccessUrl("/home")
//                .permitAll()
//                .and()
//                //开启记住我设置，用于自动登录
//                .rememberMe()
//                //密钥
//                .key("unique-and-secret")
//                //存在cookie的用户名[用于cookie名]
//                .rememberMeCookieName("remember-me-cookie-name")
//                //生命周期，单位毫秒
//                .tokenValiditySeconds(24 * 60 * 60);
//        //登陆后"选择记住我" ,会生成cookie  ,登出则会自动删除该cookie  , 只要不登出且未超出生命周期 ,那么关闭浏览器后再次访问将自动登录
//        //  [name]                          [value]                                                     [domain]   [path]      [expires/max-age]     [size]   [httponly]   [priority]
//        //remember-me-cookie-name    eGk6MTU5MTIwODAzNDk5MTozZWUyN2FlMmEwMWQxNDczMDhhY2ZkYTAxZWQ5ZWQ5YQ    localhost    /       2020-06-03T18:13:54.992Z    89       ✓            Medium
//
//
//    }


    /**
     * 添加 UserDetailsService， 实现自定义登录校验，数据库查询
     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
//        //注入用户信息，每次登录都会来这查询一次信息，因此不建议每次都向mysql查询，应该使用redis
//        //密码加密
//        System.out.println("configure");
//        builder.userDetailsService(userService);
////                .passwordEncoder(passwordEncoder());
//    }
//    @Bean
//    AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider provider
//                = new DaoAuthenticationProvider();
//        provider.setUserDetailsService(userService);
////        provider.setPasswordEncoder(new BCryptPasswordEncoder());
//        return  provider;
//    }

    /**
     * BCryptPasswordEncoder相关知识：
     * 用户表的密码通常使用MD5等不可逆算法加密后存储，为防止彩虹表破解更会先使用一个特定的字符串（如域名）加密，然后再使用一个随机的salt（盐值）加密。
     * 特定字符串是程序代码中固定的，salt是每个密码单独随机，一般给用户表加一个字段单独存储，比较麻烦。
     * BCrypt算法将salt随机并混入最终加密后的密码，验证时也无需单独提供之前的salt，从而无需单独处理salt问题。
     */
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }


//    /**
//     * 选择加密方式 ，密码不加密的时候选择 NoOpPasswordEncoder,不可缺少，否则报错
//     * java.lang.IllegalArgumentException: There is no PasswordEncoder mapped for the id "null"
//     */
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}