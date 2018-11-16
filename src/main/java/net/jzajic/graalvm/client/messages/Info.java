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
import com.google.common.collect.ImmutableMap;
import net.jzajic.graalvm.client.messages.swarm.SwarmInfo;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;


@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class Info {

  @Nullable
  @JsonProperty("Architecture")
  public String architecture;

  @Nullable
  @JsonProperty("ClusterStore")
  public String clusterStore;

  @Nullable
  @JsonProperty("CgroupDriver")
  public String cgroupDriver;

  @JsonProperty("Containers")
  public Integer containers;

  @Nullable
  @JsonProperty("ContainersRunning")
  public Integer containersRunning;

  @Nullable
  @JsonProperty("ContainersStopped")
  public Integer containersStopped;

  @Nullable
  @JsonProperty("ContainersPaused")
  public Integer containersPaused;

  @Nullable
  @JsonProperty("CpuCfsPeriod")
  public Boolean cpuCfsPeriod;

  @Nullable
  @JsonProperty("CpuCfsQuota")
  public Boolean cpuCfsQuota;

  @JsonProperty("Debug")
  public Boolean debug;

  @JsonProperty("DockerRootDir")
  public String dockerRootDir;

  @JsonProperty("Driver")
  public String storageDriver;

  @JsonProperty("DriverStatus")
  public ImmutableList<ImmutableList<String>> driverStatus;

  /**
   * @return Execution Driver
   * @deprecated Removed in API 1.24 https://github.com/docker/docker/pull/24501
   */
  @SuppressWarnings("DeprecatedIsStillUsed")
  @Deprecated
  @Nullable
  @JsonProperty("ExecutionDriver")
  public String executionDriver;

  @Nullable
  @JsonProperty("ExperimentalBuild")
  public Boolean experimentalBuild;

  @Nullable
  @JsonProperty("HttpProxy")
  public String httpProxy;

  @Nullable
  @JsonProperty("HttpsProxy")
  public String httpsProxy;

  @JsonProperty("ID")
  public String id;

  @JsonProperty("IPv4Forwarding")
  public Boolean ipv4Forwarding;

  @JsonProperty("Images")
  public Integer images;

  @JsonProperty("IndexServerAddress")
  public String indexServerAddress;

  @Nullable
  @JsonProperty("InitPath")
  public String initPath;

  @Nullable
  @JsonProperty("InitSha1")
  public String initSha1;

  @Nullable
  @JsonProperty("KernelMemory")
  public Boolean kernelMemory;

  @JsonProperty("KernelVersion")
  public String kernelVersion;

  @JsonProperty("Labels")
  public ImmutableList<String> labels;

  @JsonProperty("MemTotal")
  public Long memTotal;

  @JsonProperty("MemoryLimit")
  public Boolean memoryLimit;

  @JsonProperty("NCPU")
  public Integer cpus;

  @JsonProperty("NEventsListener")
  public Integer eventsListener;

  @JsonProperty("NFd")
  public Integer fileDescriptors;

  @JsonProperty("NGoroutines")
  public Integer goroutines;

  @JsonProperty("Name")
  public String name;

  @Nullable
  @JsonProperty("NoProxy")
  public String noProxy;

  @Nullable
  @JsonProperty("OomKillDisable")
  public Boolean oomKillDisable;

  @JsonProperty("OperatingSystem")
  public String operatingSystem;

  @Nullable
  @JsonProperty("OSType")
  public String osType;

  @Nullable
  @JsonProperty("Plugins")
  public Plugins plugins;

  @JsonProperty("RegistryConfig")
  public RegistryConfig registryConfig;

  @Nullable
  @JsonProperty("ServerVersion")
  public String serverVersion;

  @JsonProperty("SwapLimit")
  public Boolean swapLimit;
  
  @Nullable
  @JsonProperty("Swarm")
  public SwarmInfo swarm;

  @Nullable
  @JsonProperty("SystemStatus")
  public ImmutableList<ImmutableList<String>> systemStatus;

  @JsonProperty("SystemTime")
  public Date systemTime;

  @JsonCreator
  static Info create(
      @JsonProperty("Architecture") final String architecture,
      @JsonProperty("ClusterStore") final String clusterStore,
      @JsonProperty("CgroupDriver") final String cgroupDriver,
      @JsonProperty("Containers") final Integer containers,
      @JsonProperty("ContainersRunning") final Integer containersRunning,
      @JsonProperty("ContainersStopped") final Integer containersStopped,
      @JsonProperty("ContainersPaused") final Integer containersPaused,
      @JsonProperty("CpuCfsPeriod") final Boolean cpuCfsPeriod,
      @JsonProperty("CpuCfsQuota") final Boolean cpuCfsQuota,
      @JsonProperty("Debug") final Boolean debug,
      @JsonProperty("DockerRootDir") final String dockerRootDir,
      @JsonProperty("Driver") final String storageDriver,
      @JsonProperty("DriverStatus") final List<List<String>> driverStatus,
      @JsonProperty("ExecutionDriver") final String executionDriver,
      @JsonProperty("ExperimentalBuild") final Boolean experimentalBuild,
      @JsonProperty("HttpProxy") final String httpProxy,
      @JsonProperty("HttpsProxy") final String httpsProxy,
      @JsonProperty("ID") final String id,
      @JsonProperty("IPv4Forwarding") final Boolean ipv4Forwarding,
      @JsonProperty("Images") final Integer images,
      @JsonProperty("IndexServerAddress") final String indexServerAddress,
      @JsonProperty("InitPath") final String initPath,
      @JsonProperty("InitSha1") final String initSha1,
      @JsonProperty("KernelMemory") final Boolean kernelMemory,
      @JsonProperty("KernelVersion") final String kernelVersion,
      @JsonProperty("Labels") final List<String> labels,
      @JsonProperty("MemTotal") final Long memTotal,
      @JsonProperty("MemoryLimit") final Boolean memoryLimit,
      @JsonProperty("NCPU") final Integer cpus,
      @JsonProperty("NEventsListener") final Integer eventsListener,
      @JsonProperty("NFd") final Integer fileDescriptors,
      @JsonProperty("NGoroutines") final Integer goroutines,
      @JsonProperty("Name") final String name,
      @JsonProperty("NoProxy") final String noProxy,
      @JsonProperty("OomKillDisable") final Boolean oomKillDisable,
      @JsonProperty("OperatingSystem") final String operatingSystem,
      @JsonProperty("OSType") final String osType,
      @JsonProperty("Plugins") final Plugins plugins,
      @JsonProperty("RegistryConfig") final RegistryConfig registryConfig,
      @JsonProperty("ServerVersion") final String serverVersion,
      @JsonProperty("SwapLimit") final Boolean swapLimit,
      @JsonProperty("Swarm") final SwarmInfo swarm,
      @JsonProperty("SystemStatus") final List<List<String>> systemStatus,
      @JsonProperty("SystemTime") final Date systemTime) {
    final ImmutableList.Builder<ImmutableList<String>> driverStatusB = ImmutableList.builder();
    if (driverStatus != null) {
      for (final List<String> ds : driverStatus) {
        driverStatusB.add(AbstractBuilder.safeList(ds));
      }
    }
    final ImmutableList<String> labelsT = AbstractBuilder.safeList(labels);
    final ImmutableList.Builder<ImmutableList<String>> systemStatusB = ImmutableList.builder();
    if (systemStatus != null) {
      for (final List<String> ss : systemStatus) {
        systemStatusB.add(AbstractBuilder.safeList(ss));
      }
    }
    return new Info(architecture, clusterStore, cgroupDriver, containers,
        containersRunning, containersStopped, containersPaused, cpuCfsPeriod, cpuCfsQuota, debug,
        dockerRootDir, storageDriver, driverStatusB.build(), executionDriver, experimentalBuild,
        httpProxy, httpsProxy, id, ipv4Forwarding, images, indexServerAddress, initPath, initSha1,
        kernelMemory, kernelVersion, labelsT, memTotal, memoryLimit, cpus, eventsListener,
        fileDescriptors, goroutines, name, noProxy, oomKillDisable, operatingSystem, osType,
        plugins, registryConfig, serverVersion, swapLimit, swarm, systemStatusB.build(), 
        systemTime);
  }
  
  public Info(String architecture, String clusterStore, String cgroupDriver, Integer containers, Integer containersRunning, Integer containersStopped, Integer containersPaused, Boolean cpuCfsPeriod, Boolean cpuCfsQuota, Boolean debug, String dockerRootDir, String storageDriver, ImmutableList<ImmutableList<String>> driverStatus, String executionDriver, Boolean experimentalBuild, String httpProxy, String httpsProxy, String id, Boolean ipv4Forwarding, Integer images, String indexServerAddress,
			String initPath, String initSha1, Boolean kernelMemory, String kernelVersion, ImmutableList<String> labels, Long memTotal, Boolean memoryLimit, Integer cpus, Integer eventsListener, Integer fileDescriptors, Integer goroutines, String name, String noProxy, Boolean oomKillDisable, String operatingSystem, String osType, Plugins plugins, RegistryConfig registryConfig, String serverVersion, Boolean swapLimit, SwarmInfo swarm, ImmutableList<ImmutableList<String>> systemStatus, Date systemTime) {
		super();
		this.architecture = architecture;
		this.clusterStore = clusterStore;
		this.cgroupDriver = cgroupDriver;
		this.containers = containers;
		this.containersRunning = containersRunning;
		this.containersStopped = containersStopped;
		this.containersPaused = containersPaused;
		this.cpuCfsPeriod = cpuCfsPeriod;
		this.cpuCfsQuota = cpuCfsQuota;
		this.debug = debug;
		this.dockerRootDir = dockerRootDir;
		this.storageDriver = storageDriver;
		this.driverStatus = driverStatus;
		this.executionDriver = executionDriver;
		this.experimentalBuild = experimentalBuild;
		this.httpProxy = httpProxy;
		this.httpsProxy = httpsProxy;
		this.id = id;
		this.ipv4Forwarding = ipv4Forwarding;
		this.images = images;
		this.indexServerAddress = indexServerAddress;
		this.initPath = initPath;
		this.initSha1 = initSha1;
		this.kernelMemory = kernelMemory;
		this.kernelVersion = kernelVersion;
		this.labels = labels;
		this.memTotal = memTotal;
		this.memoryLimit = memoryLimit;
		this.cpus = cpus;
		this.eventsListener = eventsListener;
		this.fileDescriptors = fileDescriptors;
		this.goroutines = goroutines;
		this.name = name;
		this.noProxy = noProxy;
		this.oomKillDisable = oomKillDisable;
		this.operatingSystem = operatingSystem;
		this.osType = osType;
		this.plugins = plugins;
		this.registryConfig = registryConfig;
		this.serverVersion = serverVersion;
		this.swapLimit = swapLimit;
		this.swarm = swarm;
		this.systemStatus = systemStatus;
		this.systemTime = systemTime;
	}



	public  static class Plugins {

    @JsonProperty("Volume")
    public ImmutableList<String> volumes;

    /**
     * Return the value of the `network` json path.
     * todo this method should be renamed to network
     */
    @JsonProperty("Network")
    public ImmutableList<String> networks;

    @JsonCreator
    static Plugins create(
        @JsonProperty("Volume") final List<String> volumes,
        @JsonProperty("Network") final List<String> networks) {
      final ImmutableList<String> volumesT = AbstractBuilder.safeList(volumes);
      final ImmutableList<String> networksT =
      		AbstractBuilder.safeList(networks);
      return new Plugins(volumesT, networksT);
    }

		public Plugins(ImmutableList<String> volumes, ImmutableList<String> networks) {
			super();
			this.volumes = volumes;
			this.networks = networks;
		}
    
  }

  
  public  static class RegistryConfig {

    @JsonProperty("IndexConfigs")
    public ImmutableMap<String, IndexConfig> indexConfigs;

    @JsonProperty("InsecureRegistryCIDRs")
    public ImmutableList<String> insecureRegistryCidrs;

    @JsonCreator
    static RegistryConfig create(
        @JsonProperty("IndexConfigs") final Map<String, IndexConfig> indexConfigs,
        @JsonProperty("InsecureRegistryCIDRs") final List<String> insecureRegistryCidrs) {
      final ImmutableMap<String, IndexConfig> indexConfigsT = AbstractBuilder.safeMap(indexConfigs);
      final ImmutableList<String> insecureRegistryCidrsT = AbstractBuilder.safeList(insecureRegistryCidrs);
      return new RegistryConfig(indexConfigsT, insecureRegistryCidrsT);
    }

		public RegistryConfig(ImmutableMap<String, IndexConfig> indexConfigs, ImmutableList<String> insecureRegistryCidrs) {
			super();
			this.indexConfigs = indexConfigs;
			this.insecureRegistryCidrs = insecureRegistryCidrs;
		}
    
  }
  
  public  static class IndexConfig {

    @JsonProperty("Name")
    public String name;

    @JsonProperty("Mirrors")
    public ImmutableList<String> mirrors;

    @JsonProperty("Secure")
    public Boolean secure;

    @JsonProperty("Official")
    public Boolean official;

    @JsonCreator
    static IndexConfig create(
        @JsonProperty("Name") final String name,
        @JsonProperty("Mirrors") final List<String> mirrors,
        @JsonProperty("Secure") final Boolean secure,
        @JsonProperty("Official") final Boolean official) {
      final ImmutableList<String> mirrorsT =
      		AbstractBuilder. safeList(mirrors);
      return new IndexConfig(name, mirrorsT, secure, official);
    }

		public IndexConfig(String name, ImmutableList<String> mirrors, Boolean secure, Boolean official) {
			super();
			this.name = name;
			this.mirrors = mirrors;
			this.secure = secure;
			this.official = official;
		}
    
  }
}
