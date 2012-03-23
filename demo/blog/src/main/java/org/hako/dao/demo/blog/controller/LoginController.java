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
package org.hako.dao.demo.blog.controller;

import javax.servlet.http.HttpServletRequest;

import org.hako.Option;
import org.hako.dao.demo.blog.domain.User;
import org.hako.dao.demo.blog.filter.SecurityFilter;
import org.hako.dao.demo.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Controller for login.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
@Controller
public class LoginController {

  private UserService userService;

  @RequestMapping("/login")
  public ModelAndView login() {
    return new ModelAndView("login");
  }

  @RequestMapping("/handle_login")
  public ModelAndView handleLogin(HttpServletRequest request) {
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    Option<User> userOption =
        userService.findByNameAndPassword(username, password);
    if (userOption.hasValue()) {
      request.getSession().setAttribute(SecurityFilter.KEY_USER,
          userOption.get());
      return new ModelAndView(new RedirectView("/index.htm", true));
    }
    return new ModelAndView("login", "incorrect", Boolean.TRUE);
  }

  /**
   * Setter method for property <tt>userService</tt>.
   * 
   * @param userService value to be assigned to property userService
   */
  @Autowired
  public void setUserService(UserService userService) {
    this.userService = userService;
  }

}
