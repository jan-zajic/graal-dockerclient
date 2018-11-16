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

package net.jzajic.graalvm.client.messages.swarm;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;



@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class ConfigBind {
  @JsonProperty("File")
  public ConfigFile file;

  @JsonProperty("ConfigID")
  public String configId;

  @JsonProperty("ConfigName")
  public String configName;

  public static Builder builder() {
    return new ConfigBind.Builder();
  }

  public  static class Builder extends SwarmAbstractBuilder {

    public  Builder file(ConfigFile file) { build.file = file; return this; };

    public  Builder configId(String configId) { build.configId = configId; return this; };

    public  Builder configName(String configName) { build.configName = configName; return this; };

    public ConfigBind build = new ConfigBind();
  }

  @JsonCreator
  static ConfigBind create(
         @JsonProperty("File") ConfigFile file,
         @JsonProperty("ConfigID") String configId,
         @JsonProperty("ConfigName") String configName) {
    return builder()
        .file(file)
        .configId(configId)
        .configName(configName)
        .build;
  }
}
