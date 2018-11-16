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

import com.google.common.collect.ImmutableList;

import java.util.List;
import javax.annotation.Nullable;


@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class ContainerCreation {

  @Nullable
  @JsonProperty("Id")
  public String id;

  @Nullable
  @JsonProperty("Warnings")
  public ImmutableList<String> warnings;

  public static Builder builder() {
    return new ContainerCreation.Builder();
  }

  public  static class Builder extends AbstractBuilder {

    public  Builder id(String id) { build.id = id; return this; };

    public  Builder warnings(List<String> warnings) { build.warnings = safeList(warnings); return this; };

    public ContainerCreation build = new ContainerCreation();
  }

  @JsonCreator
  static ContainerCreation create(
      @JsonProperty("Id") final String id,
      @JsonProperty("Warnings") final List<String> warnings) {
    return builder()
        .id(id)
        .warnings(warnings)
        .build;
  }
}
