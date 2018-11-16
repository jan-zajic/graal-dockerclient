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
import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;


@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class Image {

  @JsonProperty("Created")
  public String created;

  @JsonProperty("Id")
  public String id;

  @JsonProperty("ParentId")
  public String parentId;

  @Nullable
  @JsonProperty("RepoTags")
  public ImmutableList<String> repoTags;

  @Nullable
  @JsonProperty("RepoDigests")
  public ImmutableList<String> repoDigests;

  @JsonProperty("Size")
  public Long size;

  @JsonProperty("VirtualSize")
  public Long virtualSize;

  @Nullable
  @JsonProperty("Labels")
  public ImmutableMap<String, String> labels;

  @JsonCreator
  static Image create(
      @JsonProperty("Created") final String created,
      @JsonProperty("Id") final String id,
      @JsonProperty("ParentId") final String parentId,
      @JsonProperty("RepoTags") final List<String> repoTags,
      @JsonProperty("RepoDigests") final List<String> repoDigests,
      @JsonProperty("Size") final Long size,
      @JsonProperty("VirtualSize") final Long virtualSize,
      @JsonProperty("Labels") final Map<String, String> labels) {
    final ImmutableList<String> repoTagsCopy = AbstractBuilder.safeList(repoTags);
    final ImmutableList<String> repoDigestsCopy = AbstractBuilder.safeList(repoDigests);
    final ImmutableMap<String, String> labelsCopy = AbstractBuilder.safeMap(labels);
    return new Image(created, id, parentId, repoTagsCopy, repoDigestsCopy, size,
        virtualSize, labelsCopy);
  }

	public Image(String created, String id, String parentId, ImmutableList<String> repoTags, ImmutableList<String> repoDigests, Long size, Long virtualSize, ImmutableMap<String, String> labels) {
		super();
		this.created = created;
		this.id = id;
		this.parentId = parentId;
		this.repoTags = repoTags;
		this.repoDigests = repoDigests;
		this.size = size;
		this.virtualSize = virtualSize;
		this.labels = labels;
	}
  
}
