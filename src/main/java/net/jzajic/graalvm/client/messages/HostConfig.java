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
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.isNullOrEmpty;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;


@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class HostConfig {

  @Nullable
  @JsonProperty("Binds")
  public ImmutableList<String> binds;

  @Nullable
  @JsonProperty("BlkioWeight")
  public Integer blkioWeight;

  @Nullable
  @JsonProperty("BlkioWeightDevice")
  public ImmutableList<BlkioWeightDevice> blkioWeightDevice;

  @Nullable
  @JsonProperty("BlkioDeviceReadBps")
  public ImmutableList<BlkioDeviceRate> blkioDeviceReadBps;

  @Nullable
  @JsonProperty("BlkioDeviceWriteBps")
  public ImmutableList<BlkioDeviceRate> blkioDeviceWriteBps;

  @Nullable
  @JsonProperty("BlkioDeviceReadIOps")
  public ImmutableList<BlkioDeviceRate> blkioDeviceReadIOps;

  @Nullable
  @JsonProperty("BlkioDeviceWriteIOps")
  public ImmutableList<BlkioDeviceRate> blkioDeviceWriteIOps;

  @Nullable
  @JsonProperty("ContainerIDFile")
  public String containerIdFile;

  @Nullable
  @JsonProperty("LxcConf")
  public ImmutableList<LxcConfParameter> lxcConf;

  @Nullable
  @JsonProperty("Privileged")
  public Boolean privileged;

  @Nullable
  @JsonProperty("PortBindings")
  public ImmutableMap<String, List<PortBinding>> portBindings;

  @Nullable
  @JsonProperty("Links")
  public ImmutableList<String> links;

  @Nullable
  @JsonProperty("PublishAllPorts")
  public Boolean publishAllPorts;

  @Nullable
  @JsonProperty("Dns")
  public ImmutableList<String> dns;

  @Nullable
  @JsonProperty("DnsOptions")
  public ImmutableList<String> dnsOptions;

  @Nullable
  @JsonProperty("DnsSearch")
  public ImmutableList<String> dnsSearch;

  @Nullable
  @JsonProperty("ExtraHosts")
  public ImmutableList<String> extraHosts;
  
  @Nullable
  @JsonProperty("GroupAdd")
  public ImmutableList<String> groupAdd;
  
  @Nullable
  @JsonProperty("VolumesFrom")
  public ImmutableList<String> volumesFrom;

  @Nullable
  @JsonProperty("CapAdd")
  public ImmutableList<String> capAdd;

  @Nullable
  @JsonProperty("CapDrop")
  public ImmutableList<String> capDrop;

  @Nullable
  @JsonProperty("NetworkMode")
  public String networkMode;

  @Nullable
  @JsonProperty("SecurityOpt")
  public ImmutableList<String> securityOpt;

  @Nullable
  @JsonProperty("Devices")
  public ImmutableList<Device> devices;

  @Nullable
  @JsonProperty("Memory")
  public Long memory;

  @Nullable
  @JsonProperty("MemorySwap")
  public Long memorySwap;

  @Nullable
  @JsonProperty("KernelMemory")
  public Long kernelMemory;

  @Nullable
  @JsonProperty("MemorySwappiness")
  public Integer memorySwappiness;

  @Nullable
  @JsonProperty("MemoryReservation")
  public Long memoryReservation;

  @Nullable
  @JsonProperty("NanoCpus")
  public Long nanoCpus;

  @Nullable
  @JsonProperty("CpuPeriod")
  public Long cpuPeriod;

  @Nullable
  @JsonProperty("CpuShares")
  public Long cpuShares;

  @Nullable
  @JsonProperty("CpusetCpus")
  public String cpusetCpus;

  @Nullable
  @JsonProperty("CpusetMems")
  public String cpusetMems;

  @Nullable
  @JsonProperty("CpuQuota")
  public Long cpuQuota;

  @Nullable
  @JsonProperty("CgroupParent")
  public String cgroupParent;

  @Nullable
  @JsonProperty("RestartPolicy")
  public RestartPolicy restartPolicy;

  @Nullable
  @JsonProperty("LogConfig")
  public LogConfig logConfig;

  @Nullable
  @JsonProperty("IpcMode")
  public String ipcMode;

  @Nullable
  @JsonProperty("Ulimits")
  public ImmutableList<Ulimit> ulimits;

  @Nullable
  @JsonProperty("PidMode")
  public String pidMode;

  @Nullable
  @JsonProperty("ShmSize")
  public Long shmSize;

  @Nullable
  @JsonProperty("OomKillDisable")
  public Boolean oomKillDisable;

  @Nullable
  @JsonProperty("OomScoreAdj")
  public Integer oomScoreAdj;

  @Nullable
  @JsonProperty("AutoRemove")
  public Boolean autoRemove;

  /**
   * Tune container pids limit (set -1 for unlimited).
   * Only works for kernels &gt;= 4.3
   * @return An integer indicating the pids limit.
   */
  @Nullable
  @JsonProperty("PidsLimit")
  public Integer pidsLimit;

  @Nullable
  @JsonProperty("Tmpfs")
  public ImmutableMap<String, String> tmpfs;

  @Nullable
  @JsonProperty("ReadonlyRootfs")
  public Boolean readonlyRootfs;
  
  @Nullable
  @JsonProperty("StorageOpt")
  public ImmutableMap<String, String> storageOpt;

  @Nullable
  @JsonProperty("Runtime")
  public String runtime;


  @JsonCreator
  static HostConfig create(
      @JsonProperty("Binds") final List<String> binds,
      @JsonProperty("BlkioWeight") final Integer blkioWeight,
      @JsonProperty("BlkioWeightDevice") final List<BlkioWeightDevice> blkioWeightDevice,
      @JsonProperty("BlkioDeviceReadBps") final List<BlkioDeviceRate> blkioDeviceReadBps,
      @JsonProperty("BlkioDeviceWriteBps") final List<BlkioDeviceRate> blkioDeviceWriteBps,
      @JsonProperty("BlkioDeviceReadIOps") final List<BlkioDeviceRate> blkioDeviceReadIOps,
      @JsonProperty("BlkioDeviceWriteIOps") final List<BlkioDeviceRate> blkioDeviceWriteIOps,
      @JsonProperty("ContainerIDFile") final String containerIdFile,
      @JsonProperty("LxcConf") final List<LxcConfParameter> lxcConf,
      @JsonProperty("Privileged") final Boolean privileged,
      @JsonProperty("PortBindings") final Map<String, List<PortBinding>> portBindings,
      @JsonProperty("Links") final List<String> links,
      @JsonProperty("PublishAllPorts") final Boolean publishAllPorts,
      @JsonProperty("Dns") final List<String> dns,
      @JsonProperty("DnsOptions") final List<String> dnsOptions,
      @JsonProperty("DnsSearch") final List<String> dnsSearch,
      @JsonProperty("ExtraHosts") final List<String> extraHosts,
      @JsonProperty("GroupAdd") final List<String> groupAdd,
      @JsonProperty("VolumesFrom") final List<String> volumesFrom,
      @JsonProperty("CapAdd") final List<String> capAdd,
      @JsonProperty("CapDrop") final List<String> capDrop,
      @JsonProperty("NetworkMode") final String networkMode,
      @JsonProperty("SecurityOpt") final List<String> securityOpt,
      @JsonProperty("Devices") final List<Device> devices,
      @JsonProperty("Memory") final Long memory,
      @JsonProperty("MemorySwap") final Long memorySwap,
      @JsonProperty("MemorySwappiness") final Integer memorySwappiness,
      @JsonProperty("MemoryReservation") final Long memoryReservation,
      @JsonProperty("KernelMemory") final Long kernelMemory,
      @JsonProperty("NanoCpus") final Long nanoCpus,
      @JsonProperty("CpuPeriod") final Long cpuPeriod,
      @JsonProperty("CpuShares") final Long cpuShares,
      @JsonProperty("CpusetCpus") final String cpusetCpus,
      @JsonProperty("CpusetMems") final String cpusetMems,
      @JsonProperty("CpuQuota") final Long cpuQuota,
      @JsonProperty("CgroupParent") final String cgroupParent,
      @JsonProperty("RestartPolicy") final RestartPolicy restartPolicy,
      @JsonProperty("LogConfig") final LogConfig logConfig,
      @JsonProperty("IpcMode") final String ipcMode,
      @JsonProperty("Ulimits") final List<Ulimit> ulimits,
      @JsonProperty("PidMode") final String pidMode,
      @JsonProperty("ShmSize") final Long shmSize,
      @JsonProperty("OomKillDisable") final Boolean oomKillDisable,
      @JsonProperty("OomScoreAdj") final Integer oomScoreAdj,
      @JsonProperty("AutoRemove") final Boolean autoRemove,
      @JsonProperty("PidsLimit") final Integer pidsLimit,
      @JsonProperty("Tmpfs") final Map<String, String> tmpfs,
      @JsonProperty("ReadonlyRootfs") final Boolean readonlyRootfs,
      @JsonProperty("Runtime") final String runtime,
      @JsonProperty("StorageOpt") final Map<String, String> storageOpt) {
    return builder()
        .binds(binds)
        .blkioWeight(blkioWeight)
        .blkioWeightDevice(blkioWeightDevice)
        .blkioDeviceReadBps(blkioDeviceReadBps)
        .blkioDeviceWriteBps(blkioDeviceWriteBps)
        .blkioDeviceReadIOps(blkioDeviceReadIOps)
        .blkioDeviceWriteIOps(blkioDeviceWriteIOps)
        .containerIdFile(containerIdFile)
        .lxcConf(lxcConf)
        .privileged(privileged)
        .portBindings(portBindings)
        .links(links)
        .publishAllPorts(publishAllPorts)
        .dns(dns)
        .dnsOptions(dnsOptions)
        .dnsSearch(dnsSearch)
        .extraHosts(extraHosts)
        .groupAdd( groupAdd )
        .volumesFrom(volumesFrom)
        .capAdd(capAdd)
        .capDrop(capDrop)
        .networkMode(networkMode)
        .securityOpt(securityOpt)
        .devices(devices)
        .memory(memory)
        .memorySwap(memorySwap)
        .memorySwappiness(memorySwappiness)
        .memoryReservation(memoryReservation)
        .kernelMemory(kernelMemory)
        .nanoCpus(nanoCpus)
        .cpuPeriod(cpuPeriod)
        .cpuShares(cpuShares)
        .cpusetCpus(cpusetCpus)
        .cpusetMems(cpusetMems)
        .cpuQuota(cpuQuota)
        .cgroupParent(cgroupParent)
        .restartPolicy(restartPolicy)
        .logConfig(logConfig)
        .ipcMode(ipcMode)
        .ulimits(ulimits)
        .pidMode(pidMode)
        .shmSize(shmSize)
        .oomKillDisable(oomKillDisable)
        .oomScoreAdj(oomScoreAdj)
        .autoRemove(autoRemove)
        .pidsLimit(pidsLimit)
        .tmpfs(tmpfs)
        .readonlyRootfs(readonlyRootfs)
        .storageOpt(storageOpt)
        .runtime(runtime)
        .build();
  }

  
  public  static class LxcConfParameter {

    @JsonProperty("Key")
    public String key;

    @JsonProperty("Value")
    public String value;

    @JsonCreator
    static LxcConfParameter create(
        @JsonProperty("Key") final String key,
        @JsonProperty("Value") final String value) {
      return new LxcConfParameter(key, value);
    }

		public LxcConfParameter(String key, String value) {
			super();
			this.key = key;
			this.value = value;
		}
    
  }
  
  public  static class RestartPolicy {

    @JsonProperty("Name")
    public String name;

    @Nullable
    @JsonProperty("MaximumRetryCount")
    public Integer maxRetryCount;

    public static RestartPolicy always() {
      return new RestartPolicy("always", null);
    }

    public static RestartPolicy unlessStopped() {
      return new RestartPolicy("unless-stopped", null);
    }

    public static RestartPolicy onFailure(Integer maxRetryCount) {
      return new RestartPolicy("on-failure", maxRetryCount);
    }

    @JsonCreator
    static RestartPolicy create(
        @JsonProperty("Name") final String name,
        @JsonProperty("MaximumRetryCount") final Integer maxRetryCount) {
      return new RestartPolicy(name, maxRetryCount);
    }

		public RestartPolicy(String name, Integer maxRetryCount) {
			super();
			this.name = name;
			this.maxRetryCount = maxRetryCount;
		}
    
  }

  public Builder toBuilder;

  public static Builder builder() {
    return new HostConfig.Builder();
  }

  public static class Builder extends AbstractBuilder {

    /**
     * Set the list of binds to the parameter, replacing any existing value.
     * <p>To append to the list instead, use one of the appendBinds() methods.</p>
     *
     * @param binds A list of volume bindings for this container. Each volume binding is a string.
     * @return {@link Builder}
     */
    public  Builder binds(List<String> binds) { hostConfig.binds = safeList(binds); return this; }

    /**
     * Set the list of binds to the parameter, replacing any existing value.
     * <p>To append to the list instead, use one of the appendBinds() methods.</p>
     *
     * @param binds An array of volume bindings for this container. Each volume binding is a
     *              string.
     * @return {@link Builder}
     */
    public  Builder binds(String... binds) { hostConfig.binds = safeList(binds); return this; }

    /**
     * Set the list of binds to the parameter, replacing any existing value.
     * <p>To append to the list instead, use one of the appendBinds() methods.</p>
     *
     * @param binds An array of volume bindings for this container. Each volume binding is a {@link
     *              Bind} object.
     * @return {@link Builder}
     */
    public Builder binds(final Bind... binds) {
      if (binds == null || binds.length == 0) {
        return this;
      }

      return binds(toStringList(binds));
    }

    private static List<String> toStringList(final Bind[] binds) {
      final List<String> bindStrings = Lists.newArrayList();
      for (final Bind bind : binds) {
        bindStrings.add(bind.toString());
      }
      return bindStrings;
    }

    /**
     * Append binds to the existing list in this builder. Duplicates are discarded.
     *
     * @param newBinds An iterable of volume bindings for this container. Each volume binding is a
     *                 String.
     * @return {@link Builder}
     */
    public Builder appendBinds(final Iterable<String> newBinds) {
      final List<String> list = new ArrayList<>();
      if (hostConfig.binds != null) {
        list.addAll(hostConfig.binds);
      }
      list.addAll(Lists.newArrayList(newBinds));
      binds(copyWithoutDuplicates(list));
      return this;
    }

    /**
     * Append binds to the existing list in this builder.
     *
     * @param binds An array of volume bindings for this container. Each volume binding is a {@link
     *              Bind} object.
     * @return {@link Builder}
     */
    public Builder appendBinds(final Bind... binds) {
      appendBinds(toStringList(binds));
      return this;
    }

    /**
     * Append binds to the existing list in this builder.
     *
     * @param binds An array of volume bindings for this container. Each volume binding is a
     *              String.
     * @return {@link Builder}
     */
    public Builder appendBinds(final String... binds) {
      appendBinds(Lists.newArrayList(binds));
      return this;
    }

    private static <T> ImmutableList<T> copyWithoutDuplicates(final List<T> input) {
      final List<T> list = new ArrayList<>(input.size());
      for (final T element : input) {
        if (!list.contains(element)) {
          list.add(element);
        }
      }
      return safeList(list);
    }

    public  Builder blkioWeight(Integer blkioWeight){hostConfig.blkioWeight = blkioWeight; return this;};

    public  Builder blkioWeightDevice(List<BlkioWeightDevice> blkioWeightDevice){hostConfig.blkioWeightDevice = safeList(blkioWeightDevice); return this;};

    public  Builder blkioDeviceReadBps(List<BlkioDeviceRate> blkioDeviceReadBps){hostConfig.blkioDeviceReadBps = safeList(blkioDeviceReadBps); return this;};

    public  Builder blkioDeviceWriteBps(List<BlkioDeviceRate> blkioDeviceWriteBps){hostConfig.blkioDeviceWriteBps = safeList(blkioDeviceWriteBps); return this;};

    public  Builder blkioDeviceReadIOps(List<BlkioDeviceRate> blkioDeviceReadIOps){hostConfig.blkioDeviceReadIOps = safeList(blkioDeviceReadIOps); return this;};

    public  Builder blkioDeviceWriteIOps(List<BlkioDeviceRate> blkioDeviceWriteIOps){hostConfig.blkioDeviceWriteIOps = safeList(blkioDeviceWriteIOps); return this;};

    public  Builder containerIdFile(String containerIdFile){hostConfig.containerIdFile = containerIdFile; return this;};

    public  Builder lxcConf(List<LxcConfParameter> lxcConf){hostConfig.lxcConf = safeList(lxcConf); return this;};

    public  Builder lxcConf(LxcConfParameter... lxcConf){hostConfig.lxcConf = safeList(lxcConf); return this;};

    public  Builder privileged(Boolean privileged){hostConfig.privileged = privileged; return this;};

    public  Builder portBindings(Map<String, List<PortBinding>> portBindings){hostConfig.portBindings = safeMap(portBindings); return this;};

    public  Builder links(List<String> links){hostConfig.links = safeList(links); return this;};

    public  Builder links(String... links){hostConfig.links = safeList(links); return this;};

    public  Builder publishAllPorts(Boolean publishAllPorts){hostConfig.publishAllPorts = publishAllPorts; return this;};

    public  Builder dns(List<String> dns){hostConfig.dns = safeList(dns); return this;};

    public  Builder dns(String... dns){hostConfig.dns = safeList(dns); return this;};

    public  Builder dnsOptions(List<String> dnsOptions){hostConfig.dnsOptions = safeList(dnsOptions); return this;};

    public  Builder dnsOptions(String... dnsOptions){hostConfig.dnsOptions = safeList(dnsOptions); return this;};

    public  Builder dnsSearch(List<String> dnsSearch){hostConfig.dnsSearch = safeList(dnsSearch); return this;};

    public  Builder dnsSearch(String... dnsSearch){hostConfig.dnsSearch = safeList(dnsSearch); return this;};

    public  Builder extraHosts(List<String> extraHosts){hostConfig.extraHosts = safeList(extraHosts); return this;};

    public  Builder extraHosts(String... extraHosts){hostConfig.extraHosts = safeList(extraHosts); return this;};
    
    public  Builder groupAdd(List<String> groupAdd){hostConfig.groupAdd = safeList(groupAdd); return this;};

    public  Builder groupAdd(String... groupAdd){hostConfig.groupAdd = safeList(groupAdd); return this;};

    public  Builder volumesFrom(List<String> volumesFrom){hostConfig.volumesFrom = safeList(volumesFrom); return this;};

    public  Builder volumesFrom(String... volumesFrom){hostConfig.volumesFrom = safeList(volumesFrom); return this;};

    public  Builder capAdd(List<String> capAdd){hostConfig.capAdd = safeList(capAdd); return this;};

    public  Builder capAdd(String... capAdd) {hostConfig.capAdd = safeList(capAdd); return this;};

    public  Builder capDrop(List<String> capDrop){hostConfig.capDrop = safeList(capDrop); return this;};

    public  Builder capDrop(String... capDrop){hostConfig.capDrop = safeList(capDrop); return this;};

    public  Builder networkMode(String networkMode){hostConfig.networkMode = networkMode; return this;};

    public  Builder securityOpt(List<String> securityOpt){hostConfig.securityOpt = safeList(securityOpt); return this;};

    public  Builder securityOpt(String... securityOpt){hostConfig.securityOpt = safeList(securityOpt); return this;};

    public  Builder devices(List<Device> devices){hostConfig.devices = safeList(devices); return this;};

    public  Builder devices(Device... devices){hostConfig.devices = safeList(devices); return this;};

    public  Builder memory(Long memory){hostConfig.memory = memory; return this;};

    public  Builder memorySwap(Long memorySwap){hostConfig.memorySwap = memorySwap; return this;};

    public  Builder memorySwappiness(Integer memorySwappiness){hostConfig.memorySwappiness = memorySwappiness; return this;};

    public  Builder kernelMemory(Long kernelMemory){hostConfig.kernelMemory = kernelMemory; return this;};

    public  Builder memoryReservation(Long memoryReservation){hostConfig.memoryReservation = memoryReservation; return this;};

    public  Builder nanoCpus(Long nanoCpus){hostConfig.nanoCpus = nanoCpus; return this;};

    public  Builder cpuPeriod(Long cpuPeriod){hostConfig.cpuPeriod = cpuPeriod; return this;};

    public  Builder cpuShares(Long cpuShares){hostConfig.cpuShares = cpuShares; return this;};

    public  Builder cpusetCpus(String cpusetCpus){hostConfig.cpusetCpus = cpusetCpus; return this;};

    public  Builder cpusetMems(String cpusetMems){hostConfig.cpusetMems = cpusetMems; return this;};

    public  Builder cpuQuota(Long cpuQuota){hostConfig.cpuQuota = cpuQuota; return this;};

    public  Builder cgroupParent(String cgroupParent){hostConfig.cgroupParent = cgroupParent; return this;};

    public  Builder restartPolicy(RestartPolicy restartPolicy){hostConfig.restartPolicy = restartPolicy; return this;};

    public  Builder logConfig(LogConfig logConfig){hostConfig.logConfig = logConfig; return this;};

    public  Builder ipcMode(String ipcMode){hostConfig.ipcMode = ipcMode; return this;};

    public  Builder ulimits(List<Ulimit> ulimits){hostConfig.ulimits = safeList(ulimits); return this;};

    public  Builder pidMode(String pidMode) {hostConfig.pidMode = pidMode; return this;};

    /**
     * Set the PID (Process) Namespace mode for the container.
     * Use this method to join another container's PID namespace. To use the host
     * PID namespace, use {@link #hostPidMode()}.
     *
     * @param container Join the namespace of this container (Name or ID)
     * @return Builder
     */
    public Builder containerPidMode(final String container) {
      pidMode("container:" + container);
      return this;
    }

    /**
     * Set the PID (Process) Namespace mode for the container.
     * Use this method to use the host's PID namespace. To use another container's
     * PID namespace, use {@link #containerPidMode(String)}.
     *
     * @return {@link Builder}
     */
    public Builder hostPidMode() {
      pidMode("host");
      return this;
    }

    public  Builder shmSize(Long shmSize) {hostConfig.shmSize = shmSize; return this;};

    public  Builder oomKillDisable(Boolean oomKillDisable) {hostConfig.oomKillDisable = oomKillDisable; return this;};

    public  Builder oomScoreAdj(Integer oomScoreAdj) {hostConfig.oomScoreAdj = oomScoreAdj; return this;};

    /**
     * Only works for Docker API version &gt;= 1.25.
     * @param autoRemove Whether to automatically remove the container when it exits
     * @return {@link Builder}
     */
    public  Builder autoRemove(Boolean autoRemove) {hostConfig.autoRemove = autoRemove; return this;};

    public  Builder pidsLimit(Integer pidsLimit) {hostConfig.pidsLimit = pidsLimit; return this;};

    public  Builder tmpfs(Map<String, String> tmpfs) {hostConfig.tmpfs = safeMap(tmpfs); return this;};

    public  Builder readonlyRootfs(Boolean readonlyRootfs) {hostConfig.readonlyRootfs = readonlyRootfs; return this;};
    
    public  Builder storageOpt(Map<String, String> tmpfs) {hostConfig.tmpfs = safeMap(tmpfs); return this;};

    public  Builder runtime(String runtime) {hostConfig.runtime = runtime; return this;};

    final HostConfig hostConfig = new HostConfig();
    
    public HostConfig build() {      
      validateExtraHosts(hostConfig.extraHosts);
      return hostConfig;
    }
  }

  private static void validateExtraHosts(final List<String> extraHosts) {
    if (extraHosts != null) {
      for (final String extraHost : extraHosts) {
        checkArgument(extraHost.contains(":"),
            "extra host arg '%s' must contain a ':'", extraHost);
      }
    }
  }

  
  public  static class Bind {

    public String to;

    public static BuilderTo to(final String to) {
      return BuilderTo.create(to);
    }

    public String from;

    public static BuilderFrom from(final String from) {
      return BuilderFrom.create(from);
    }

    public static BuilderFrom from(final Volume volumeFrom) {
      return BuilderFrom.create(volumeFrom);
    }

    public Boolean readOnly;

    @Nullable
    public Boolean noCopy;

    @Nullable
    public Boolean selinuxLabeling;

    public static Builder builder() {
      return new Bind.Builder().readOnly(false);
    }

    public String toString() {
      if (isNullOrEmpty(to)) {
        return "";
      } else if (isNullOrEmpty(from)) {
        return to;
      }

      final String bind = from + ":" + to;

      final List<String> options = new ArrayList<>();
      if (readOnly) {
        options.add("ro");
      }
      //noinspection ConstantConditions
      if (noCopy != null && noCopy) {
        options.add("nocopy");
      }

      if (selinuxLabeling != null) {
        // shared
        if (Boolean.TRUE.equals(selinuxLabeling)) {
          options.add("z");
        } else {
          options.add("Z");
        }
      }

      final String optionsValue = Joiner.on(',').join(options);

      return (optionsValue.isEmpty()) ? bind : bind + ":" + optionsValue;
    }

    
    public  static class BuilderTo {

      public String to;

      public static BuilderTo create(final String to) {
        return new BuilderTo(to);
      }

      public Builder from(final String from) {
        return builder().to(to).from(from);
      }

      public Builder from(final Volume volumeFrom) {
        return builder().to(to).from(volumeFrom);
      }

			public BuilderTo(String to) {
				super();
				this.to = to;
			}
      
    }

    
    public  static class BuilderFrom {

      public String from;

      public static BuilderFrom create(final String from) {
        return new BuilderFrom(from);
      }

      public static BuilderFrom create(final Volume volumeFrom) {
        return new BuilderFrom(
            checkNotNull(volumeFrom.name, "Volume name"));
      }

      public Builder to(final String to) {
        return builder().to(to).from(from);
      }

			public BuilderFrom(String from) {
				super();
				this.from = from;
			}
      
    }

    public  static class Builder extends AbstractBuilder {

      public  Builder to(String to) { build.to = to; return this; };

      public  Builder from(String from) { build.from = from; return this; };

      public Builder from(final Volume volumeFrom) {
        from(checkNotNull(volumeFrom.name, "Volume name"));
        return this;
      }

      public  Builder readOnly(Boolean readOnly) { build.readOnly = readOnly; return this; };

      public  Builder noCopy(Boolean noCopy) { build.noCopy = noCopy; return this; };

      /**
       * Turn on automatic SELinux labeling of the host file or directory being
       * mounted into the container.
       * @param sharedContent True if this bind mount content is shared among multiple 
       *     containers (mount option "z"); false if private and unshared (mount option "Z")
       * @return {@link Builder}
       */
      public  Builder selinuxLabeling(Boolean sharedContent) { build.selinuxLabeling = sharedContent; return this; };

      public Bind build = new Bind();
    }
  }

  
  public  static class Ulimit {

    @JsonProperty("Name")
    public String name;

    @JsonProperty("Soft")
    public Long soft;

    @JsonProperty("Hard")
    public Long hard;

    public static Builder builder() {
      return new Ulimit.Builder();
    }

    public  static class Builder extends AbstractBuilder {

      public  Builder name(String name) { build.name = name; return this; };

      public  Builder soft(Long soft) { build.soft = soft; return this; };

      public  Builder hard(Long hard) { build.hard = hard; return this; };

      public Ulimit build = new Ulimit();
    }

    @JsonCreator
    public static Ulimit create(
        @JsonProperty("Name") final String name,
        @JsonProperty("Soft") final Long soft,
        @JsonProperty("Hard") final Long hard) {
      return builder()
          .name(name)
          .soft(soft)
          .hard(hard)
          .build;
    }
  }
  
  public  static class BlkioWeightDevice {

    @JsonProperty("Path")
    public String path;

    @JsonProperty("Weight")
    public Integer weight;

    public static Builder builder() {
      return new BlkioWeightDevice.Builder();
    }

    @JsonCreator
    static BlkioWeightDevice create(
        @JsonProperty("Path") final String path,
        @JsonProperty("Weight") final Integer weight) {
      return builder().path(path).weight(weight).build;
    }

    public  static class Builder extends AbstractBuilder {

      public  Builder path(final String path) { build.path = path; return this; };

      public  Builder weight(final Integer weight) { build.weight = weight; return this; };

      public BlkioWeightDevice build = new BlkioWeightDevice();
    }
  }

  
  public  static class BlkioDeviceRate {

    @JsonProperty("Path")
    public String path;

    @JsonProperty("Rate")
    public Integer rate;

    @JsonCreator
    static BlkioDeviceRate create(
        @JsonProperty("Path") final String path,
        @JsonProperty("Rate") final Integer rate) {
      return builder().path(path).rate(rate).build;
    }

    public static Builder builder() {
      return new BlkioDeviceRate.Builder();
    }

    public  static class Builder extends AbstractBuilder {

      public  Builder path(final String path) { build.path = path; return this; };

      public  Builder rate(final Integer rate) { build.rate = rate; return this; };

      public BlkioDeviceRate build = new BlkioDeviceRate();
    }
  }
}
