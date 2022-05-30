package ru.renett.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import ru.renett.StringToTagConverter;
import ru.renett.interceptor.AuthenticationInterceptor;

import java.util.Locale;

import static ru.renett.configuration.Constants.*;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    private final AuthenticationInterceptor interceptor;

    private final StringToTagConverter stringToTagConverter;

    @Autowired
    public WebMvcConfiguration(AuthenticationInterceptor interceptor, StringToTagConverter stringToTagConverter) {
        this.interceptor = interceptor;
        this.stringToTagConverter = stringToTagConverter;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor);
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(stringToTagConverter);
    }

    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
        cookieLocaleResolver.setCookieName(COOKIE_LOCALE);
        cookieLocaleResolver.setCookieMaxAge(COOKIE_LOCALE_MAX_AGE);
        cookieLocaleResolver.setDefaultLocale(Locale.ENGLISH);
        return cookieLocaleResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName(LOCALE_CHANGE_PARAM);
        return localeChangeInterceptor;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(MESSAGES_SOURCE);
        messageSource.setDefaultEncoding(CHAR_ENCODING);
        return messageSource;
    }
//
//    @Override
//    public MessageCodesResolver getMessageCodesResolver() {
//        DefaultMessageCodesResolver codesResolver = new DefaultMessageCodesResolver();
//        codesResolver.setMessageCodeFormatter(DefaultMessageCodesResolver.Format.POSTFIX_ERROR_CODE);
//        return codesResolver;
//    }
}
