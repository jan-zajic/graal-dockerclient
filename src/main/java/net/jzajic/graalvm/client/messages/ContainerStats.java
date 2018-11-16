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


import com.google.common.collect.ImmutableMap;
import java.util.Date;
import java.util.Map;
import javax.annotation.Nullable;


@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class ContainerStats {

  @JsonProperty("read")
  public Date read;

  @Nullable
  @JsonProperty("network")
  public NetworkStats network;

  @Nullable
  @JsonProperty("networks")
  public ImmutableMap<String, NetworkStats> networks;

  @JsonProperty("memory_stats")
  public MemoryStats memoryStats;

  @JsonProperty("blkio_stats")
  public BlockIoStats blockIoStats;

  @JsonProperty("cpu_stats")
  public CpuStats cpuStats;

  @JsonProperty("precpu_stats")
  public CpuStats precpuStats;

  @JsonCreator
  static ContainerStats create(
      @JsonProperty("read") final Date read,
      @JsonProperty("network") final NetworkStats networkStats,
      @JsonProperty("networks") final Map<String, NetworkStats> networks,
      @JsonProperty("memory_stats") final MemoryStats memoryStats,
      @JsonProperty("blkio_stats") final BlockIoStats blockIoStats,
      @JsonProperty("cpu_stats") final CpuStats cpuStats,
      @JsonProperty("precpu_stats") final CpuStats precpuStats) {
    final ImmutableMap<String, NetworkStats> networksCopy = AbstractBuilder.safeMap(networks);
    return new ContainerStats(read, networkStats, networksCopy,
        memoryStats, blockIoStats, cpuStats, precpuStats);
  }

	public ContainerStats(Date read, NetworkStats network, ImmutableMap<String, NetworkStats> networks, MemoryStats memoryStats, BlockIoStats blockIoStats, CpuStats cpuStats, CpuStats precpuStats) {
		super();
		this.read = read;
		this.network = network;
		this.networks = networks;
		this.memoryStats = memoryStats;
		this.blockIoStats = blockIoStats;
		this.cpuStats = cpuStats;
		this.precpuStats = precpuStats;
	}
  
}
