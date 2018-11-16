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

import com.google.common.collect.ImmutableList;

import java.util.List;
import javax.annotation.Nullable;


@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class ImageHistory {

  @JsonProperty("Id")
  public String id;

  @JsonProperty("Created")
  public Long created;

  @JsonProperty("CreatedBy")
  public String createdBy;

  @Nullable
  @JsonProperty("Tags")
  public ImmutableList<String> tags;

  @JsonProperty("Size")
  public Long size;

  @Nullable
  @JsonProperty("Comment")
  public String comment;

  @JsonCreator
  static ImageHistory create(
      @JsonProperty("Id") final String id,
      @JsonProperty("Created") final Long created,
      @JsonProperty("CreatedBy") final String createdBy,
      @JsonProperty("Tags") final List<String> tags,
      @JsonProperty("Size") final Long size,
      @JsonProperty("Comment") final String comment) {
    final ImmutableList<String> tagsCopy = AbstractBuilder.safeList(tags);
    return new ImageHistory(id, created, createdBy, tagsCopy, size, comment);
  }

	public ImageHistory(String id, Long created, String createdBy, ImmutableList<String> tags, Long size, String comment) {
		super();
		this.id = id;
		this.created = created;
		this.createdBy = createdBy;
		this.tags = tags;
		this.size = size;
		this.comment = comment;
	}
  
}
