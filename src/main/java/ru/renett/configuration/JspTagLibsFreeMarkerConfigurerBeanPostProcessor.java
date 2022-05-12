package ru.renett.configuration;

import freemarker.ext.jsp.TaglibFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

// https://stackoverflow.com/questions/33755964/spring-boot-with-jsp-tag-libs-in-embedded-tomcat
//@Component
public class JspTagLibsFreeMarkerConfigurerBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof FreeMarkerConfigurer) {
            System.out.println("FreeMarkerConfigurer");
            FreeMarkerConfigurer freeMarkerConfigurer = (FreeMarkerConfigurer) bean;
            TaglibFactory taglibFactory = freeMarkerConfigurer.getTaglibFactory();

            TaglibFactory.ClasspathMetaInfTldSource classpathMetaInfTldSource =
                    new TaglibFactory.ClasspathMetaInfTldSource(Pattern.compile(".*"));

            taglibFactory.setMetaInfTldSources(List.of(classpathMetaInfTldSource));
            taglibFactory.setClasspathTlds(List.of("/META-INF/tld/common.tld"));
            taglibFactory.setClasspathTlds(List.of("/META-INF/security.tld"));


            System.out.println(taglibFactory.getMetaInfTldSources());
            System.out.println(taglibFactory.getClasspathTlds());
            System.out.println(taglibFactory.getObjectWrapper());
            System.out.println(classpathMetaInfTldSource.getRootContainerPattern());
        }
        return bean;
    }
}
