/*-
 * -\-\-
 * docker-client
 * --
 * Copyright (C) 2018 Spotify AB
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



@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = ANY)
public class Descriptor {

  @JsonProperty("MediaType")
  public String mediaType;

  @JsonProperty("Digest")
  public String digest;

  @JsonProperty("Size")
  public Long size;

  @JsonProperty("URLs")
  public ImmutableList<String> urls;

  @JsonCreator
  static Descriptor create(
            @JsonProperty("MediaType") String mediaType,
            @JsonProperty("Digest") String digest,
            @JsonProperty("Size") Long size,
            @JsonProperty("URLs") ImmutableList<String> urls) {
    return new Descriptor(mediaType, digest, size, urls);
  }

	public Descriptor(String mediaType, String digest, Long size, ImmutableList<String> urls) {
		super();
		this.mediaType = mediaType;
		this.digest = digest;
		this.size = size;
		this.urls = urls;
	}
  
}
