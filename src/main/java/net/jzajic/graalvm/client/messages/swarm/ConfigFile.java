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

import javax.annotation.Nullable;


@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class ConfigFile {
  @JsonProperty("Name")
  public String name;

  @Nullable
  @JsonProperty("UID")
  public String uid;

  @Nullable
  @JsonProperty("GID")
  public String gid;

  @Nullable
  @JsonProperty("Mode")
  public Long mode;

  public static Builder builder() {
    return new ConfigFile.Builder();
  }

  public  static class Builder extends SwarmAbstractBuilder {

    public  Builder name(String name) { build.name = name; return this; };

    public  Builder uid(String uid) { build.uid = uid; return this; };

    public  Builder gid(String gid) { build.gid = gid; return this; };

    public  Builder mode(Long mode) { build.mode = mode; return this; };

    public ConfigFile build = new ConfigFile();
  }

  @JsonCreator
  static ConfigFile create(
      @JsonProperty("Name") String name,
      @JsonProperty("UID") String uid,
      @JsonProperty("GID") String gid,
      @JsonProperty("Mode") Long mode) {
    return builder()
        .name(name)
        .uid(uid)
        .gid(gid)
        .mode(mode)
        .build;
  }
}
