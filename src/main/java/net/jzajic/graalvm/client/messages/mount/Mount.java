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

package net.jzajic.graalvm.client.messages.mount;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import net.jzajic.graalvm.client.messages.AbstractBuilder;

import javax.annotation.Nullable;


@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class Mount {

  @Nullable
  @JsonProperty("Type")
  public String type;

  @Nullable
  @JsonProperty("Source")
  public String source;

  @Nullable
  @JsonProperty("Target")
  public String target;

  @Nullable
  @JsonProperty("ReadOnly")
  public Boolean readOnly;

  @Nullable
  @JsonProperty("BindOptions")
  public BindOptions bindOptions;

  @Nullable
  @JsonProperty("VolumeOptions")
  public VolumeOptions volumeOptions;

  @Nullable
  @JsonProperty("TmpfsOptions")
  public TmpfsOptions tmpfsOptions;

  public  static class Builder extends AbstractBuilder {

    public  Builder type(String type) { build.type = type; return this; };

    public  Builder source(String source) { build.source = source; return this; };

    public  Builder target(String target) { build.target = target; return this; };

    public  Builder readOnly(Boolean readOnly) { build.readOnly = readOnly; return this; };

    public  Builder bindOptions(BindOptions bindOptions) { build.bindOptions = bindOptions; return this; };

    public  Builder volumeOptions(VolumeOptions volumeOptions) { build.volumeOptions = volumeOptions; return this; };

    public  Builder tmpfsOptions(TmpfsOptions tmpfsOptions){ build.tmpfsOptions = tmpfsOptions; return this; };

    public Mount build = new Mount();
  }

  public static Mount.Builder builder() {
    return new Mount.Builder();
  }

  @JsonCreator
  static Mount create(
      @JsonProperty("Type") final String type,
      @JsonProperty("Source") final String source,
      @JsonProperty("Target") final String target,
      @JsonProperty("ReadOnly") final Boolean readOnly,
      @JsonProperty("BindOptions") final BindOptions bindOptions,
      @JsonProperty("VolumeOptions") final VolumeOptions volumeOptions,
      @JsonProperty("TmpfsOptions") final TmpfsOptions tmpfsOptions) {
    return builder()
        .type(type)
        .source(source)
        .target(target)
        .readOnly(readOnly)
        .bindOptions(bindOptions)
        .volumeOptions(volumeOptions)
        .tmpfsOptions(tmpfsOptions)
        .build;
  }
}
