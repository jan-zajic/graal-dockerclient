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
import com.fasterxml.jackson.annotation.JsonProperty;


import com.google.common.collect.ImmutableMap;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;


@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class NetworkConfig {

  @JsonProperty("Name")
  public String name;

  @Nullable
  @JsonProperty("Driver")
  public String driver;

  @Nullable
  @JsonProperty("IPAM")
  public Ipam ipam;

  @JsonProperty("Options")
  public Map<String, String> options;

  @Nullable
  @JsonProperty("CheckDuplicate")
  public Boolean checkDuplicate;
  
  @Nullable
  @JsonProperty("Internal")
  public Boolean internal;
  
  @Nullable
  @JsonProperty("EnableIPv6")
  public Boolean enableIPv6;

  @Nullable
  @JsonProperty("Attachable")
  public Boolean attachable;

  @Nullable
  @JsonProperty("Labels")
  public ImmutableMap<String, String> labels;

  public static Builder builder() {
    return new NetworkConfig.Builder()
        .options(new HashMap<String, String>());
  }

  public static class Builder extends AbstractBuilder {

  	private Map<String,String> optionsBuilder = new HashMap<>();
  	
    public  Builder name(final String name) { build.name = name; return this; };

    public Builder addOption(final String key, final String value) {
      optionsBuilder.put(key, value);
      return this;
    }

    public  Builder options(Map<String, String> options) { build.options = safeMap(options); return this; };

    public  Builder ipam(final Ipam ipam) { build.ipam = ipam; return this; };

    public  Builder driver(final String driver) { build.driver = driver; return this; };

    public  Builder checkDuplicate(Boolean check) { build.checkDuplicate = check; return this; };
    
    public  Builder internal(Boolean internal) { build.internal = internal; return this; };
    
    public  Builder enableIPv6(Boolean ipv6) { build.enableIPv6 = ipv6; return this; };

    public  Builder attachable(Boolean attachable) { build.attachable = attachable; return this; };

    public  Builder labels(Map<String, String> labels) { build.labels = safeMap(labels); return this; };
    
    public NetworkConfig build;
    
    public Builder() {
    	build = new NetworkConfig();
    	build.options = Collections.unmodifiableMap(optionsBuilder);
		}
    
  }

}
