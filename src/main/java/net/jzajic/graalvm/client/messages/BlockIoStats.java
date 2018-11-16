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
public class BlockIoStats {

  @Nullable
  @JsonProperty("io_service_bytes_recursive")
  public ImmutableList<Object> ioServiceBytesRecursive;

  @Nullable
  @JsonProperty("io_serviced_recursive")
  public ImmutableList<Object> ioServicedRecursive;

  @Nullable
  @JsonProperty("io_queue_recursive")
  public ImmutableList<Object> ioQueueRecursive;

  @Nullable
  @JsonProperty("io_service_time_recursive")
  public ImmutableList<Object> ioServiceTimeRecursive;

  @Nullable
  @JsonProperty("io_wait_time_recursive")
  public ImmutableList<Object> ioWaitTimeRecursive;

  @Nullable
  @JsonProperty("io_merged_recursive")
  public ImmutableList<Object> ioMergedRecursive;

  @Nullable
  @JsonProperty("io_time_recursive")
  public ImmutableList<Object> ioTimeRecursive;

  @Nullable
  @JsonProperty("sectors_recursive")
  public ImmutableList<Object> sectorsRecursive;

  public  static class Builder extends AbstractBuilder {

    public  Builder ioServiceBytesRecursive(final List<Object> ioServiceBytesRecursive) { build.ioServiceBytesRecursive = safeList(ioServiceBytesRecursive); return this; };

    public  Builder ioServicedRecursive(final List<Object> ioServicedRecursive) { build.ioServicedRecursive = safeList(ioServicedRecursive); return this; };

    public  Builder ioQueueRecursive(final List<Object> ioQueueRecursive){ build.ioQueueRecursive = safeList(ioQueueRecursive); return this; };

    public  Builder ioServiceTimeRecursive(final List<Object> ioServiceTimeRecursive){ build.ioServiceTimeRecursive = safeList(ioServiceTimeRecursive); return this; };

    public  Builder ioWaitTimeRecursive(final List<Object> ioWaitTimeRecursive){ build.ioWaitTimeRecursive = safeList(ioWaitTimeRecursive); return this; };

    public  Builder ioMergedRecursive(final List<Object> ioMergedRecursive){ build.ioMergedRecursive = safeList(ioMergedRecursive); return this; };

    public  Builder ioTimeRecursive(final List<Object> ioTimeRecursive){ build.ioTimeRecursive = safeList(ioTimeRecursive); return this; };

    public  Builder sectorsRecursive(final List<Object> sectorsRecursive){ build.sectorsRecursive = safeList(sectorsRecursive); return this; };

    public BlockIoStats build = new BlockIoStats();
  }

  @JsonCreator
  static BlockIoStats create(
      @JsonProperty("io_service_bytes_recursive") final List<Object> ioServiceBytesRecursive,
      @JsonProperty("io_serviced_recursive") final List<Object> ioServicedRecursive,
      @JsonProperty("io_queue_recursive") final List<Object> ioQueueRecursive,
      @JsonProperty("io_service_time_recursive") final List<Object> ioServiceTimeRecursive,
      @JsonProperty("io_wait_time_recursive") final List<Object> ioWaitTimeRecursive,
      @JsonProperty("io_merged_recursive") final List<Object> ioMergedRecursive,
      @JsonProperty("io_time_recursive") final List<Object> ioTimeRecursive,
      @JsonProperty("sectors_recursive") final List<Object> sectorsRecursive) {
    return new BlockIoStats.Builder()
        .ioServiceBytesRecursive(ioServiceBytesRecursive)
        .ioServicedRecursive(ioServicedRecursive)
        .ioQueueRecursive(ioQueueRecursive)
        .ioServiceTimeRecursive(ioServiceTimeRecursive)
        .ioWaitTimeRecursive(ioWaitTimeRecursive)
        .ioMergedRecursive(ioMergedRecursive)
        .ioTimeRecursive(ioTimeRecursive)
        .sectorsRecursive(sectorsRecursive)
        .build;
  }
}
