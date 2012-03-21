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

import org.hako.dao.demo.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller of blog list.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
@Controller
public class BlogListController {

  private BlogService blogService;

  @RequestMapping("/blog/list")
  public ModelAndView list(HttpServletRequest request) {
    return new ModelAndView("blog-list", "blogs", blogService.listRecent(0, 10));
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
