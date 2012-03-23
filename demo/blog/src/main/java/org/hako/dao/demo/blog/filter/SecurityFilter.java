/**
 * Copyright 2012 XnnYygn
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.hako.dao.demo.blog.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hako.dao.demo.blog.domain.User;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Filter for security.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
public class SecurityFilter extends OncePerRequestFilter {

  private static final Log logger = LogFactory.getLog(SecurityFilter.class);
  public static final String KEY_USER = "user";

  /**
   * @see OncePerRequestFilter#doFilterInternal(HttpServletRequest,
   *      HttpServletResponse, FilterChain)
   */
  @Override
  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response, FilterChain chain) throws ServletException,
      IOException {
    if (!isSubSetOf(getUserPermissions(request.getSession()),
        getRequiredPermissions(request.getRequestURI()))) {
      response.sendRedirect(request.getContextPath() + "/login.htm");
    }
    chain.doFilter(request, response);
  }

  private <T> boolean isSubSetOf(Set<T> a, Set<T> b) {
    for (T element : b) {
      if (!a.contains(element)) {
        return false;
      }
    }
    return true;
  }

  // TODO get required permissions from configuration
  private Set<String> getRequiredPermissions(String uri) {
    if (logger.isDebugEnabled()) {
      logger.debug("uri [" + uri + "]");
    }
    if (uri.startsWith("/hako-dao-demo-blog/blog/create.htm")) {
      return new HashSet<String>(Arrays.asList("USER"));
    }
    return Collections.emptySet();
  }

  private Set<String> getUserPermissions(HttpSession session) {
    Object userObject = session.getAttribute(KEY_USER);
    if (userObject != null) {
      if (userObject instanceof User) {
        return new HashSet<String>(Arrays.asList("USER"));
      }
    }
    return Collections.emptySet();
  }

}
