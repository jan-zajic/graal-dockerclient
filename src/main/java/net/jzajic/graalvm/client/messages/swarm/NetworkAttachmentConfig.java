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

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.*;

import java.util.List;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;


@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class NetworkAttachmentConfig {

  @Nullable
  @JsonProperty("Target")
  public String target;

  @Nullable
  @JsonProperty("Aliases")
  public ImmutableList<String> aliases;

  public static Builder builder() {
    return new NetworkAttachmentConfig.Builder();
  }

  public  static class Builder extends SwarmAbstractBuilder {

    public  Builder target(String target) { build.target = target; return this; };

    public  Builder aliases(String... aliases) { build.aliases = safeList(aliases); return this; };

    public  Builder aliases(List<String> aliases) { build.aliases = safeList(aliases); return this; };

    public NetworkAttachmentConfig build = new NetworkAttachmentConfig();
  }

  @JsonCreator
  static NetworkAttachmentConfig create(
      @JsonProperty("Target") final String target,
      @JsonProperty("Aliases") final List<String> aliases) {
    return builder()
        .target(target)
        .aliases(aliases)
        .build;
  }
}
