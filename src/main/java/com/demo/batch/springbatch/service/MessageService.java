package com.demo.batch.springbatch.service;

import java.util.Locale;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author kaihe
 *
 */

@Component
@RequiredArgsConstructor
public class MessageService {

  @NonNull
  private final ResourceBundleMessageSource messageSource;

  public String getMessage(String msgCode) {
    Locale locale = LocaleContextHolder.getLocale();
    return messageSource.getMessage(msgCode, null, locale);
  }

  // Get single parameter message
  public String getMessage(String msgCode, String param) {
    String[] params = new String[] {param};
    Locale locale = LocaleContextHolder.getLocale();
    return messageSource.getMessage(msgCode, params, locale);
  }

  // Get multiple parameters message
  public String getMessage(String msgCode, Object[] params) {
    Locale locale = LocaleContextHolder.getLocale();
    return messageSource.getMessage(msgCode, params, locale);
  }
}
