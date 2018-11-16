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
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.DEFAULT;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.google.common.collect.ImmutableList;
import javax.annotation.Nullable;


@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = DEFAULT)
public class Distribution {

  @JsonProperty("Descriptor")
  public Descriptor descriptor;

  @Nullable
  @JsonProperty("Platforms")
  public ImmutableList<Platform> platforms;

  public  static class Builder extends AbstractBuilder {
    public  Builder descriptor(Descriptor descriptor) { build.descriptor = descriptor; return this; };

    public  Builder platforms(ImmutableList<Platform> platforms) { build.platforms = safeList(platforms); return this; };

    public Distribution build = new Distribution();
  }

  public static Builder builder() {
    return new Distribution.Builder();
  }

  @JsonCreator
  static Distribution create(
          @JsonProperty("Descriptor") Descriptor descriptor,
          @JsonProperty("Platforms") ImmutableList<Platform> platforms) {
    return builder()
            .descriptor(descriptor)
            .platforms(platforms)
            .build;
  }
}
