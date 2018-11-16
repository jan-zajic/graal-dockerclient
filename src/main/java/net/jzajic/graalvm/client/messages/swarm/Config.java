/*-
 * -\-\-
 * docker-client
 * --
 * Copyright (C) 2016 - 2017 Spotify AB
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

package net.jzajic.graalvm.client.messages.swarm;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import javax.annotation.Nullable;


@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class Config {

  @JsonProperty("ID")
  public String id;

  @JsonProperty("Version")
  public Version version;

  @JsonProperty("CreatedAt")
  public Date createdAt;

  @JsonProperty("UpdatedAt")
  public Date updatedAt;

  @JsonProperty("Spec")
  public ConfigSpec configSpec;

  
  public  static class Criteria {
    /**
     * Filter by config id.
     */
    @Nullable
    public String configId;

    /**
     * Filter by label.
     */
    @Nullable
    public String label;

    /**
     * Filter by config name.
     */
    @Nullable
    public String name;

    public static Config.Criteria.Builder builder() {
      return new Criteria.Builder();
    }

    public static class Builder extends SwarmAbstractBuilder {
      public  Builder configId(String configId) { build.configId = configId; return this; };

      public  Builder label(String label) { build.label = label; return this; };

      public  Builder name(String name) { build.name = name; return this; };

      public Config.Criteria build = new Config.Criteria();
    }
  }

  public static Config.Criteria.Builder find() {
    return Criteria.builder();
  }

  @JsonCreator
  static Config create(
      @JsonProperty("ID") final String id,
      @JsonProperty("Version") final Version version,
      @JsonProperty("CreatedAt") final Date createdAt,
      @JsonProperty("UpdatedAt") final Date updatedAt,
      @JsonProperty("Spec") final ConfigSpec secretSpec) {
    return new Config(id, version, createdAt, updatedAt, secretSpec);
  }

	public Config(String id, Version version, Date createdAt, Date updatedAt, ConfigSpec configSpec) {
		super();
		this.id = id;
		this.version = version;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.configSpec = configSpec;
	}
  
  

}
