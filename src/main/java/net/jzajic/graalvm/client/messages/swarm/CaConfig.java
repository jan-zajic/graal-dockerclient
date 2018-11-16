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


import com.google.common.collect.ImmutableList;
import java.util.List;
import javax.annotation.Nullable;


@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class CaConfig {

  @Nullable
  @JsonProperty("NodeCertExpiry")
  public Long nodeCertExpiry;

  @Nullable
  @JsonProperty("ExternalCAs")
  public ImmutableList<ExternalCa> externalCas;

  @JsonCreator
  static CaConfig create(
      @JsonProperty("NodeCertExpiry") final Long nodeCertExpiry,
      @JsonProperty("ExternalCAs") final List<ExternalCa> externalCas) {
    return builder()
        .nodeCertExpiry(nodeCertExpiry)
        .externalCas(externalCas)
        .build;
  }

  public  static class Builder extends SwarmAbstractBuilder {

    public  Builder nodeCertExpiry(Long nodeCertExpiry) { build.nodeCertExpiry = nodeCertExpiry; return this; };

    public  Builder externalCas(List<ExternalCa> externalCas) { build.externalCas = safeList(externalCas); return this; };

    public CaConfig build = new CaConfig();
  }

  public static CaConfig.Builder builder() {
    return new CaConfig.Builder();
  }
}
