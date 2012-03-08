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
package org.hako.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Map utilties.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class MapUtils {

  /**
   * Create map from variable arguments.
   * 
   * @param keyClass key class
   * @param keyOrValues key or values
   * @return map
   * @throws IllegalArgumentException if some key does not have value
   * @see #apply(Class, Class, Object...)
   */
  @SuppressWarnings("unchecked")
  public static <K, V> Map<K, V> apply(Class<K> keyClass, Object... keyOrValues)
      throws IllegalArgumentException {
    return (Map<K, V>) apply(keyClass, Object.class, keyOrValues);
  }

  /**
   * Create map from variable arguments.
   * 
   * @param keyClass key class
   * @param valueClass value class
   * @param keyOrValues key or values
   * @return map
   * @throws IllegalArgumentException if some key does not have value
   */
  @SuppressWarnings("unchecked")
  public static <K, V> Map<K, V> apply(Class<K> keyClass, Class<V> valueClass,
      Object... keyOrValues) throws IllegalArgumentException {
    int length = keyOrValues.length;
    if (length % 2 != 0) {
      throw new IllegalArgumentException(keyOrValues[length - 1]
          + " must has value");
    }
    Map<K, V> map = new HashMap<K, V>();
    for (int i = 0; i < length; i += 2) {
      map.put((K) keyOrValues[i], (V) keyOrValues[i + 1]);
    }
    return map;
  }

  /**
   * Merge map.
   * 
   * @param left 
   * @param right
   * @return merged map
   */
  public static <K, V> Map<K, V> merge(Map<K, V> left, Map<K, V> right) {
    Map<K, V> resultMap = new HashMap<K, V>();
    resultMap.putAll(left);
    resultMap.putAll(right);
    return resultMap;
  }

  /**
   * Merge map with key and value. Mutable means add to same map, not create new map.
   * 
   * @param left
   * @param key
   * @param value
   * @return map
   */
  public static <K, V> Map<K, V> merge(Map<K, V> left, K key, V value){
    left.put(key, value);
    return left;
  }
  
}
