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
public class TmpfsOptions {

  @Nullable
  @JsonProperty("SizeBytes")
  public Long sizeBytes;

  /**
   * The mode and permission bits.
   */
  @Nullable
  @JsonProperty("Mode")
  public Integer mode;

  public  static class Builder extends AbstractBuilder {

    public  Builder sizeBytes(Long sizeBytes) { build.sizeBytes = sizeBytes; return this; };;

    public  Builder mode(Integer mode) { build.mode = mode; return this; };

    public TmpfsOptions build = new TmpfsOptions();
  }

  public static TmpfsOptions.Builder builder() {
    return new TmpfsOptions.Builder();
  }

  @JsonCreator
  static TmpfsOptions create(
      @JsonProperty("SizeBytes") final Long sizeBytes,
      @JsonProperty("Labels") final Integer mode) {
    return builder()
        .sizeBytes(sizeBytes)
        .mode(mode)
        .build;
  }
}
