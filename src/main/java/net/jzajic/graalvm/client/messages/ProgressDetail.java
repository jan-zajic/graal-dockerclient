/*-
 * -\-\-
 * docker-client
 * --
 * Copyright (C) 2016 Spotify AB
 * --
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * -/-/-
 */

package net.jzajic.graalvm.client.messages;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


import javax.annotation.Nullable;


@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class ProgressDetail {

  @Nullable
  @JsonProperty("current")
  public Long current;

  @Nullable
  @JsonProperty("start")
  public Long start;

  @Nullable
  @JsonProperty("total")
  public Long total;

  @JsonCreator
  static ProgressDetail create(
      @JsonProperty("current") final Long current,
      @JsonProperty("start") final Long start,
      @JsonProperty("total") final Long total) {
    return new ProgressDetail(current, start, total);
  }

	public ProgressDetail(Long current, Long start, Long total) {
		super();
		this.current = current;
		this.start = start;
		this.total = total;
	}
  
}
