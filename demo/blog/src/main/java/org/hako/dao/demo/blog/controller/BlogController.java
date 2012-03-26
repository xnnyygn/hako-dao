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

import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.hako.None;
import org.hako.Option;
import org.hako.Some;
import org.hako.dao.demo.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Controller for blog.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
@Controller
public class BlogController {

  private BlogService blogService;

  @RequestMapping("/blog/list")
  public ModelAndView list(HttpServletRequest request) {
    return new ModelAndView("blog-list", "blogs", blogService.listRecent(0, 10));
  }

  @RequestMapping("/blog/create")
  public ModelAndView create() {
    return new ModelAndView("blog-create");
  }

  @RequestMapping(value = "/blog/save", method = RequestMethod.POST)
  public ModelAndView save(HttpServletRequest request) {
    String title = request.getParameter("title");
    String content = request.getParameter("content");
    if (StringUtils.isNotBlank(title) && StringUtils.isNotBlank(content)) {
      blogService.save(title, content);
      return new ModelAndView(new RedirectView("/blog/list.htm", true));
    }
    return new ModelAndView("blog-create");
  }

  @RequestMapping("/blog/show")
  public ModelAndView show(HttpServletRequest request) {
    Option<Long> idOption = getLongParam(request, "id");
    if (idOption.hasValue()) {
      Option<Map<String, Object>> blogOption =
          blogService.getForShow(idOption.get());
      if (blogOption.hasValue()) {
        return new ModelAndView("blog-show", "blog", blogOption.get());
      }
    }
    return new ModelAndView(new RedirectView("/blog/list.htm", true));
  }

  @RequestMapping(value = "/blog/save_comment", method = RequestMethod.POST)
  public ModelAndView saveComment(HttpServletRequest request) {
    Option<Long> blogIdOption = getLongParam(request, "blogId");
    String content = request.getParameter("content");
    String username = request.getParameter("username");
    if (blogIdOption.hasValue() && isNotBlank(content) && isNotBlank(username)) {
      Long blogId = blogIdOption.get();
      blogService.saveComment(content, username, blogId);
      return new ModelAndView(new RedirectView("/blog/show.htm?id=" + blogId,
          true));
    }
    return new ModelAndView(new RedirectView("/blog/list.htm", true));
  }

  private Option<Long> getLongParam(HttpServletRequest request, String name) {
    String value = request.getParameter(name);
    if (value != null) {
      try {
        return new Some<Long>(Long.valueOf(value));
      } catch (NumberFormatException e) {
        // omit format exception
      }
    }
    return new None<Long>();
  }

  /**
   * Setter method for property <tt>blogService</tt>.
   * 
   * @param blogService value to be assigned to property blogService
   */
  @Autowired
  public void setBlogService(BlogService blogService) {
    this.blogService = blogService;
  }

}
