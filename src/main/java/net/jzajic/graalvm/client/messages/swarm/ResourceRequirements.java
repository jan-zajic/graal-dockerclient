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
public class ResourceRequirements {

  @Nullable
  @JsonProperty("Limits")
  public Resources limits;

  @Nullable
  @JsonProperty("Reservations")
  public Resources reservations;

  public  static class Builder extends SwarmAbstractBuilder {

    public  Builder limits(Resources limits) { build.limits = limits; return this; };

    public  Builder reservations(Resources reservations) { build.reservations = reservations; return this; };

    public ResourceRequirements build = new ResourceRequirements();
  }

  public static ResourceRequirements.Builder builder() {
    return new ResourceRequirements.Builder();
  }

  @JsonCreator
  static ResourceRequirements create(
      @JsonProperty("Limits") final Resources limits,
      @JsonProperty("Reservations") final Resources reservations) {
    return builder()
        .limits(limits)
        .reservations(reservations)
        .build;
  }
}
