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


import com.google.common.collect.ImmutableMap;
import java.util.Map;
import javax.annotation.Nullable;

@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class Volume {

  @Nullable
  @JsonProperty("Name")
  public String name;

  @Nullable
  @JsonProperty("Driver")
  public String driver;

  @Nullable
  @JsonProperty("DriverOpts")
  public ImmutableMap<String, String> driverOpts;

  @Nullable
  @JsonProperty("Options")
  public ImmutableMap<String, String> options;

  @Nullable
  @JsonProperty("Labels")
  public ImmutableMap<String, String> labels;

  @Nullable
  @JsonProperty("Mountpoint")
  public String mountpoint;

  @Nullable
  @JsonProperty("Scope")
  public String scope;

  @Nullable
  @JsonProperty("Status")
  public ImmutableMap<String, String> status;

  @JsonCreator
  static Volume create(
      @JsonProperty("Name") final String name,
      @JsonProperty("Driver") final String driver,
      @JsonProperty("DriverOpts") final Map<String, String> driverOpts,
      @JsonProperty("Options") final Map<String, String> options,
      @JsonProperty("Labels") final Map<String, String> labels,
      @JsonProperty("Mountpoint") final String mountpoint,
      @JsonProperty("Scope") final String scope,
      @JsonProperty("Status") final Map<String, String> status) {
    return builder()
        .name(name)
        .driver(driver)
        .driverOpts(driverOpts)
        .options(options)
        .labels(labels)
        .mountpoint(mountpoint)
        .scope(scope)
        .status(status)
        .build;
  }

  public static Builder builder() {
    return new Volume.Builder();
  }

  public static class Builder extends AbstractBuilder {
  	
    public  Builder name(String name) { build.name = name; return this; };

		public  Builder driver(String driver) { build.driver = driver; return this; };

    public  Builder driverOpts(Map<String, String> driverOpts) { build.driverOpts = safeMap(driverOpts); return this; };

    public  Builder options(Map<String, String> options) { build.options = safeMap(options); return this; };

    public  Builder labels(Map<String, String> labels) { build.labels = safeMap(labels); return this; };

    public  Builder mountpoint(String mountpoint) { build.mountpoint = mountpoint; return this; };

    public  Builder scope(String scope) { build.scope = scope; return this; };

    public  Builder status(Map<String, String> status) { build.status = safeMap(status); return this; };

    public  Volume build = new Volume();

  }
}
