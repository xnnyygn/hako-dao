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

import java.sql.Timestamp;

/**
 * Domain comment.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
@Entity(tableName = "comment", tableAlias = "c")
public class Comment {

  @Id
  @Field
  public Long id;
  
  @Field
  public String content;
  
  @Field
  public Long blogId;
  
  @Field
  public Long userId;
  
  @Field
  public Timestamp dateCreated;
  
}
