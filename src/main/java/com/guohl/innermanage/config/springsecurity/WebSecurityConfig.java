package com.guohl.innermanage.config.springsecurity;

import com.guohl.innermanage.dao.RoleDao;
import com.guohl.innermanage.dao.UserDao;
import com.guohl.innermanage.dao.UserRoleDao;
import com.guohl.innermanage.entity.UserEntity;
import com.guohl.innermanage.entity.UserRoleEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import javax.annotation.Resource;
import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Resource
    UserDao userDao;
    @Resource
    UserRoleDao userRoleDao;
    @Resource
    RoleDao roleDao;

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        List<UserEntity> user = userDao.getAllUser();
        for(UserEntity entity: user){
            List<UserRoleEntity> role = userRoleDao.getRole(entity.getUserName());
            String[] arr= new String[role.size()];
            int index=0;
            for (UserRoleEntity userRoleEntity : role){
                arr[index]=userRoleEntity.getRole();
                index++;
            }
            PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
            String passwordAfterEncoder = passwordEncoder.encode(entity.getPassWord());
            manager.createUser(User.withUsername(entity.getUserName()).password(passwordAfterEncoder).roles(arr).build());
        }
        return manager;
    }
    @Bean
    public WebSecurityConfigurerAdapter webSecurityConfigurerAdapter() {
        return new WebSecurityConfigurerAdapter() {
            @Override
            public void configure(HttpSecurity httpSecurity) throws Exception {
                httpSecurity.
                        authorizeRequests().antMatchers("/guest/**").permitAll().
                        and().authorizeRequests().antMatchers("/admin/**").hasRole("admin").
                        and().authorizeRequests().antMatchers("/authenticated/**").authenticated().
                        and().authorizeRequests().antMatchers("/permission1/**").hasAuthority("permission1").
                        and().authorizeRequests().antMatchers("/permission2/**").hasAuthority("permission2").
                        and().authorizeRequests().antMatchers("/permission3/**").hasAuthority("permission3").
                        and().authorizeRequests().antMatchers("/permission4/**").hasAuthority("permission4").
                        and().formLogin().
                        and().authorizeRequests().anyRequest().permitAll();
            }
        };
    }
}