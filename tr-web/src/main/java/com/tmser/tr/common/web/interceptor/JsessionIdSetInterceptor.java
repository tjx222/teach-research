/**
 * Tmser.com Inc. Copyright (c) 2015-2017 All Rights Reserved.
 */

package com.tmser.tr.common.web.interceptor;

import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UrlPathHelper;

import com.tmser.tr.common.utils.CookieUtils;

/**
 * <pre>
 *  设置cookie 过滤器
 * </pre>
 *
 * @author tmser
 * @version $Id: AvoidDuplicateSubmissionInterceptor.java, v 1.0 2015年4月6日 下午9:25:50 tmser Exp $
 */
public class JsessionIdSetInterceptor extends HandlerInterceptorAdapter implements InitializingBean {
  private static final Logger logger = LoggerFactory.getLogger(JsessionIdSetInterceptor.class);

  public static final String DEFAULT_SESSION_KEY = "JSESSIONID";

  private String sessionCookieName = DEFAULT_SESSION_KEY;

  private String paramName = "nocookie";

  private UrlPathHelper urlPathHelper = new UrlPathHelper();

  private PathMatcher pathMatcher = new AntPathMatcher();

  private Set<String> officeViewPathPattern;

  private static final Set<String> defaultPattern;

  private static final String NO_OFFICE = "_no_office_";

  static {
    defaultPattern = new HashSet<>();
    defaultPattern.add("/jy/check/lesson/*/tch/*/view");
    defaultPattern.add("/jy/check/lesson/*/tch/other/*/view");
    defaultPattern.add("/jy/activity/joinTbjaActivity");
    defaultPattern.add("/jy/activity/viewTbjaActivity");
    defaultPattern.add("/jy/activity/scanLessonPlanTrack");

    defaultPattern.add("/jy/teachschedule/view");
    defaultPattern.add("/jy/check/thesis/tch/*/view");

    defaultPattern.add("/jy/comres/viewlesson");
    defaultPattern.add("/jy/comres/view");

    defaultPattern.add("/jy/managerecord/check/1/kejianView");
    defaultPattern.add("/jy/managerecord/check/2-3/fansiView");
    defaultPattern.add("/jy/managerecord/check/0/viewLessonPlanCheckInfo");
    defaultPattern.add("/jy/managerecord/check/10/thesisView");
    defaultPattern.add("/jy/managerecord/check/planSummaryView");

    defaultPattern.add("/jy/lessonplan/toEditLessonPlan");
    defaultPattern.add("/jy/lessonplan/scanLessonPlan");

    defaultPattern.add("/jy/planSummaryCheck/userSpace/*/planSummary/*");
    defaultPattern.add("/jy/planSummaryCheck/role/*/planSummary/*");

    defaultPattern.add("/jy/planSummary/*/viewFile");
    defaultPattern.add("/jy/planSummary/viewOthers");

    defaultPattern.add("/jy/teachingView/view/lesson");
    defaultPattern.add("/jy/teachingView/view/other/lesson");
    defaultPattern.add("/jy/teachingView/view/planSummary/*");
    defaultPattern.add("/jy/teachingView/view/thesisview");
    defaultPattern.add("/jy/teachingView/view/view_schActivity_jibei1");

    defaultPattern.add("/jy/thesis/thesisview");

    defaultPattern.add("/jy/toWriteLessonPlan");
    defaultPattern.add("/jy/lecturerecords/seelesson");

    defaultPattern.add("/jy/check/thesis/tch/other");

  }

  private String officePage = "/WEB-INF/views/resview/pageofficeOpenWindow.jsp";

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

    if (request.getParameter(paramName) != null) {
      CookieUtils.setJsessionIdCookie(request, response, sessionCookieName);
      logger.debug("set cookie for path: {}", request.getRequestURI());
    } else if (request.getParameter(NO_OFFICE) == null) {
      String lookupPath = this.urlPathHelper.getLookupPathForRequest(request);
      UriComponentsBuilder rb = UriComponentsBuilder.fromUriString(lookupPath);
      Map<String, String[]> params = request.getParameterMap();
      if (params != null) {
        for (Entry<String, String[]> key : params.entrySet()) {
          rb.queryParam(key.getKey(), key.getValue());
        }
      }

      for (String pattern : officeViewPathPattern) {
        if (pathMatcher.match(pattern, lookupPath)) {
          request.setAttribute("url", rb.build().toString());
          request.getRequestDispatcher(officePage).forward(request, response);
          return false;
        }
      }
    }

    return true;

  }

  public void setSessionCookieName(String sessionCookieName) {
    this.sessionCookieName = sessionCookieName;
  }

  public void setParamName(String paramName) {
    this.paramName = paramName;
  }

  /**
   * @throws Exception
   * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
   */
  @Override
  public void afterPropertiesSet() throws Exception {
    if (officeViewPathPattern == null) {
      officeViewPathPattern = new HashSet<String>(defaultPattern);
    } else {
      officeViewPathPattern.addAll(defaultPattern);
    }
  }

  /**
   * Setter method for property <tt>urlPathHelper</tt>.
   *
   * @param urlPathHelper UrlPathHelper value to be assigned to property urlPathHelper
   */
  public void setUrlPathHelper(UrlPathHelper urlPathHelper) {
    this.urlPathHelper = urlPathHelper;
  }

  /**
   * Setter method for property <tt>pathMatcher</tt>.
   *
   * @param pathMatcher PathMatcher value to be assigned to property pathMatcher
   */
  public void setPathMatcher(PathMatcher pathMatcher) {
    this.pathMatcher = pathMatcher;
  }

  /**
   * Setter method for property <tt>officeViewPathPattern</tt>.
   *
   * @param officeViewPathPattern Set<String> value to be assigned to property officeViewPathPattern
   */
  public void setOfficeViewPathPattern(Set<String> officeViewPathPattern) {
    this.officeViewPathPattern = officeViewPathPattern;
  }

  /**
   * Setter method for property <tt>officePage</tt>.
   *
   * @param officePage String value to be assigned to property officePage
   */
  public void setOfficePage(String officePage) {
    this.officePage = officePage;
  }

}
