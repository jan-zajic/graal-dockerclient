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


import javax.annotation.Nullable;


@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class Resources {

  @Nullable
  @JsonProperty("NanoCPUs")
  public Long nanoCpus;

  @Nullable
  @JsonProperty("MemoryBytes")
  public Long memoryBytes;

  public  static class Builder extends SwarmAbstractBuilder {

    public  Builder nanoCpus(Long nanoCpus) { build.nanoCpus = nanoCpus; return this; };

    public  Builder memoryBytes(Long memoryBytes) { build.memoryBytes = memoryBytes; return this; };

    public Resources build = new Resources();
  }

  public static Resources.Builder builder() {
    return new Resources.Builder();
  }

  @JsonCreator
  static Resources create(
      @JsonProperty("NanoCPUs") final Long nanoCpus,
      @JsonProperty("MemoryBytes") final Long memoryBytes) {
    return builder()
        .nanoCpus(nanoCpus)
        .memoryBytes(memoryBytes)
        .build;
  }
}
