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
public class CpuStats {

  @JsonProperty("cpu_usage")
  public CpuUsage cpuUsage;

  @Nullable
  @JsonProperty("system_cpu_usage")
  public Long systemCpuUsage;

  @JsonProperty("throttling_data")
  public ThrottlingData throttlingData;

  public  static class Builder extends AbstractBuilder {

    public  Builder cpuUsage(final CpuUsage cpuUsage){ build.cpuUsage = cpuUsage; return this; };

    public  Builder systemCpuUsage(final Long systemCpuUsage){ build.systemCpuUsage = systemCpuUsage; return this; };

    public  Builder throttlingData(final ThrottlingData throttlingData){ build.throttlingData = throttlingData; return this; };

    public CpuStats build = new CpuStats();
  }

  @JsonCreator
  static CpuStats create(
      @JsonProperty("cpu_usage") final CpuUsage cpuUsage,
      @JsonProperty("system_cpu_usage") final Long systemCpuUsage,
      @JsonProperty("throttling_data") final ThrottlingData throttlingData) {
    return new CpuStats.Builder()
        .cpuUsage(cpuUsage)
        .systemCpuUsage(systemCpuUsage)
        .throttlingData(throttlingData)
        .build;
  }
  
  public  static class CpuUsage {

    @JsonProperty("total_usage")
    public Long totalUsage;

    @Nullable
    @JsonProperty("percpu_usage")
    public ImmutableList<Long> percpuUsage;

    @JsonProperty("usage_in_kernelmode")
    public Long usageInKernelmode;

    @JsonProperty("usage_in_usermode")
    public Long usageInUsermode;

    public  static class Builder extends AbstractBuilder {

      public  Builder totalUsage(final Long totalUsage){ build.totalUsage = totalUsage; return this; };

      public  Builder percpuUsage(final List<Long> percpuUsage){ build.percpuUsage = safeList(percpuUsage); return this; };

      public  Builder usageInKernelmode(final Long usageInKernelmode){ build.usageInKernelmode = usageInKernelmode; return this; };

      public  Builder usageInUsermode(final Long usageInUsermode){ build.usageInUsermode = usageInUsermode; return this; };

      public CpuUsage build = new CpuUsage();
    }

    @JsonCreator
    static CpuUsage create(
        @JsonProperty("total_usage") final Long totalUsage,
        @JsonProperty("percpu_usage") final List<Long> perCpuUsage,
        @JsonProperty("usage_in_kernelmode") final Long usageInKernelmode,
        @JsonProperty("usage_in_usermode") final Long usageInUsermode) {
      return new CpuUsage.Builder()
          .totalUsage(totalUsage)
          .percpuUsage(perCpuUsage)
          .usageInKernelmode(usageInKernelmode)
          .usageInUsermode(usageInUsermode)
          .build;
    }
  }
  
  public  static class ThrottlingData {

    @JsonProperty("periods")
    public Long periods;

    @JsonProperty("throttled_periods")
    public Long throttledPeriods;

    @JsonProperty("throttled_time")
    public Long throttledTime;

    @JsonCreator
    static ThrottlingData create(
        @JsonProperty("periods") final Long periods,
        @JsonProperty("throttled_periods") final Long throttledPeriods,
        @JsonProperty("throttled_time") final Long throttledTime) {
      return new ThrottlingData(periods, throttledPeriods, throttledTime);
    }

		public ThrottlingData(Long periods, Long throttledPeriods, Long throttledTime) {
			super();
			this.periods = periods;
			this.throttledPeriods = throttledPeriods;
			this.throttledTime = throttledTime;
		}
    
  }
}
