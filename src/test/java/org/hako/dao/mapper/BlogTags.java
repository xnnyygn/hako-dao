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
package org.hako.dao.mapper;

/**
 * Table between blog and tag.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
// TODO how to prevent create these classes
@Entity(tableName = "blog_tags", tableAlias = "bt")
public class BlogTags {

  @Field
  public Long blogId;
  
  @Field
  public Long tagId;
  
}
