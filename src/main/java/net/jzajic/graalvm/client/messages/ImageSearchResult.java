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




@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class ImageSearchResult {

  @JsonProperty("description")
  public String description;

  @JsonProperty("is_official")
  public boolean official;

  @JsonProperty("is_automated")
  public boolean automated;

  @JsonProperty("name")
  public String name;

  @JsonProperty("star_count")
  public int starCount;

  @JsonCreator
  static ImageSearchResult create(
      @JsonProperty("description") final String description,
      @JsonProperty("is_official") final boolean official,
      @JsonProperty("is_automated") final boolean automated,
      @JsonProperty("name") final String name,
      @JsonProperty("star_count") final int starCount) {
    return new ImageSearchResult(description, official, automated, name, starCount);
  }

	public ImageSearchResult(String description, boolean official, boolean automated, String name, int starCount) {
		super();
		this.description = description;
		this.official = official;
		this.automated = automated;
		this.name = name;
		this.starCount = starCount;
	}
  
}
