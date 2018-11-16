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
import com.google.common.collect.ImmutableSet;



import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;


@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class ContainerConfig {

  @Nullable
  @JsonProperty("Hostname")
  public String hostname;

  @Nullable
  @JsonProperty("Domainname")
  public String domainname;

  @Nullable
  @JsonProperty("User")
  public String user;

  @Nullable
  @JsonProperty("AttachStdin")
  public Boolean attachStdin;

  @Nullable
  @JsonProperty("AttachStdout")
  public Boolean attachStdout;

  @Nullable
  @JsonProperty("AttachStderr")
  public Boolean attachStderr;

  @Nullable
  @JsonProperty("PortSpecs")
  public ImmutableList<String> portSpecs;

  @Nullable
  @JsonProperty("ExposedPorts")
  public ImmutableSet<String> exposedPorts;

  @Nullable
  @JsonProperty("Tty")
  public Boolean tty;

  @Nullable
  @JsonProperty("OpenStdin")
  public Boolean openStdin;

  @Nullable
  @JsonProperty("StdinOnce")
  public Boolean stdinOnce;

  @Nullable
  @JsonProperty("Env")
  public ImmutableList<String> env;

  @Nullable
  @JsonProperty("Cmd")
  public ImmutableList<String> cmd;

  @Nullable
  @JsonProperty("Image")
  public String image;

  @Nullable
  @JsonProperty("Volumes")
  public Set<String> volumes;

  @Nullable
  @JsonProperty("WorkingDir")
  public String workingDir;

  @Nullable
  @JsonProperty("Entrypoint")
  public ImmutableList<String> entrypoint;

  @Nullable
  @JsonProperty("NetworkDisabled")
  public Boolean networkDisabled;

  @Nullable
  @JsonProperty("OnBuild")
  public ImmutableList<String> onBuild;

  @Nullable
  @JsonProperty("Labels")
  public ImmutableMap<String, String> labels;

  @Nullable
  @JsonProperty("MacAddress")
  public String macAddress;

  @Nullable
  @JsonProperty("HostConfig")
  public HostConfig hostConfig;

  @Nullable
  @JsonProperty("StopSignal")
  public String stopSignal;

  @Nullable
  @JsonProperty("Healthcheck")
  public Healthcheck healthcheck;

  @Nullable
  @JsonProperty("NetworkingConfig")
  public NetworkingConfig networkingConfig;

  @JsonCreator
  static ContainerConfig create(
      @JsonProperty("Hostname") final String hostname,
      @JsonProperty("Domainname") final String domainname,
      @JsonProperty("User") final String user,
      @JsonProperty("AttachStdin") final Boolean attachStdin,
      @JsonProperty("AttachStdout") final Boolean attachStdout,
      @JsonProperty("AttachStderr") final Boolean attachStderr,
      @JsonProperty("PortSpecs") final List<String> portSpecs,
      @JsonProperty("ExposedPorts") final Set<String> exposedPorts,
      @JsonProperty("Tty") final Boolean tty,
      @JsonProperty("OpenStdin") final Boolean openStdin,
      @JsonProperty("StdinOnce") final Boolean stdinOnce,
      @JsonProperty("Env") final List<String> env,
      @JsonProperty("Cmd") final List<String> cmd,
      @JsonProperty("Image") final String image,
      @JsonProperty("Volumes") final Set<String> volumes,
      @JsonProperty("WorkingDir") final String workingDir,
      @JsonProperty("Entrypoint") final List<String> entrypoint,
      @JsonProperty("NetworkDisabled") final Boolean networkDisabled,
      @JsonProperty("OnBuild") final List<String> onBuild,
      @JsonProperty("Labels") final Map<String, String> labels,
      @JsonProperty("MacAddress") final String macAddress,
      @JsonProperty("HostConfig") final HostConfig hostConfig,
      @JsonProperty("StopSignal") final String stopSignal,
      @JsonProperty("Healthcheck") final Healthcheck healthcheck,
      @JsonProperty("NetworkingConfig") final NetworkingConfig networkingConfig) {
    return builder()
        .hostname(hostname)
        .domainname(domainname)
        .user(user)
        .attachStdin(attachStdin)
        .attachStdout(attachStdout)
        .attachStderr(attachStderr)
        .tty(tty)
        .openStdin(openStdin)
        .stdinOnce(stdinOnce)
        .image(image)
        .workingDir(workingDir)
        .networkDisabled(networkDisabled)
        .macAddress(macAddress)
        .hostConfig(hostConfig)
        .stopSignal(stopSignal)
        .networkingConfig(networkingConfig)
        .volumes(volumes)
        .portSpecs(portSpecs)
        .exposedPorts(exposedPorts)
        .env(env)
        .cmd(cmd)
        .entrypoint(entrypoint)
        .onBuild(onBuild)
        .labels(labels)
        .healthcheck(healthcheck)
        .build;
  }

  public Builder toBuilder;

  public static Builder builder() {
    return new ContainerConfig.Builder();
  }

  public  static class Builder extends AbstractBuilder {

    public  Builder hostname(final String hostname) {build.hostname = hostname; return this;};

    public  Builder domainname(final String domainname){build.domainname = domainname; return this;};

    public  Builder user(final String user){build.user = user; return this;};

    public  Builder attachStdin(final Boolean attachStdin){build.attachStdin = attachStdin; return this;};

    public  Builder attachStdout(final Boolean attachStdout){build.attachStdout = attachStdout; return this;};

    public  Builder attachStderr(final Boolean attachStderr){build.attachStderr = attachStderr; return this;};

    public  Builder portSpecs(final List<String> portSpecs){build.portSpecs = safeList(portSpecs, true); return this;};

    public  Builder portSpecs(final String... portSpecs){build.portSpecs = safeList(portSpecs, true); return this;};

    public  Builder exposedPorts(final Set<String> exposedPorts){build.exposedPorts = safeSet(exposedPorts, true); return this;};

    public  Builder exposedPorts(final String... exposedPorts){build.exposedPorts = safeSet(exposedPorts, true); return this;};

    public  Builder tty(final Boolean tty){build.tty = tty; return this;};

    public  Builder openStdin(final Boolean openStdin){build.openStdin = openStdin; return this;};

    public  Builder stdinOnce(final Boolean stdinOnce){build.stdinOnce = stdinOnce; return this;};

    public  Builder env(final List<String> env){build.env = safeList(env, true); return this;};

    public  Builder env(final String... env){build.env = safeList(env, true); return this;};

    public  Builder cmd(final List<String> cmd){build.cmd = safeList(cmd, true); return this;};

    public  Builder cmd(final String... cmd){build.cmd = safeList(cmd, true); return this;};

    public  Builder image(final String image){build.image = image; return this;};

    private Set<String> volumesBuilder = new HashSet<String>();

    public Builder addVolume(final String volume) {
      volumesBuilder.add(volume);
      return this;
    }

    public Builder addVolumes(final String... volumes) {
      for (final String volume : volumes) {
        volumesBuilder.add(volume);
      }
      return this;
    }

    public  Builder volumes(final Set<String> volumes) {build.volumes = safeSet(volumes); return this;};

    public  Builder volumes(final String... volumes) {build.volumes = safeSet(volumes); return this;};

    public  Builder workingDir(final String workingDir) {build.workingDir = workingDir; return this;};

    public  Builder entrypoint(final List<String> entrypoint) {build.entrypoint = safeList(entrypoint, true); return this;};

    public  Builder entrypoint(final String... entrypoint) {build.entrypoint = safeList(entrypoint, true); return this;};

    public  Builder networkDisabled(final Boolean networkDisabled) {build.networkDisabled = networkDisabled; return this;};

    public  Builder onBuild(final List<String> onBuild) {build.onBuild = safeList(onBuild, true); return this;};

    public  Builder onBuild(final String... onBuild) {build.onBuild = safeList(onBuild, true); return this;};

    public  Builder labels(final Map<String, String> labels) {build.labels = safeMap(labels, true); return this;};

    public  Builder macAddress(final String macAddress) {build.macAddress = macAddress; return this;};

    public  Builder hostConfig(final HostConfig hostConfig) {build.hostConfig = hostConfig; return this;};

    public  Builder stopSignal(final String stopSignal) {build.stopSignal = stopSignal; return this;};

    public  Builder healthcheck(final Healthcheck healthcheck) {build.healthcheck = healthcheck; return this;};

    public  Builder networkingConfig(final NetworkingConfig networkingConfig) {build.networkingConfig = networkingConfig; return this;};

    public ContainerConfig build;
    
    public Builder() {
    	build = new ContainerConfig();
    	build.volumes = Collections.unmodifiableSet(volumesBuilder);
		}
    
  }

  
  public  static class Healthcheck {
    @Nullable
    @JsonProperty("Test")
    public ImmutableList<String> test;

    /**
     * In nanoseconds.
     */
    @Nullable
    @JsonProperty("Interval")
    public Long interval;

    /**
     * In nanoseconds.
     */
    @Nullable
    @JsonProperty("Timeout")
    public Long timeout;

    @Nullable
    @JsonProperty("Retries")
    public Integer retries;

    /**
     * In nanoseconds.
     * @since API 1.29
     */
    @Nullable
    @JsonProperty("StartPeriod")
    public Long startPeriod;

    public static Healthcheck create(
            @JsonProperty("Test") final List<String> test,
            @JsonProperty("Interval") final Long interval,
            @JsonProperty("Timeout") final Long timeout,
            @JsonProperty("Retries") final Integer retries) {
      return create(test, interval, timeout, retries, null);
    }

    @JsonCreator
    public static Healthcheck create(
            @JsonProperty("Test") final List<String> test,
            @JsonProperty("Interval") final Long interval,
            @JsonProperty("Timeout") final Long timeout,
            @JsonProperty("Retries") final Integer retries,
            @JsonProperty("StartPeriod") final Long startPeriod) {
      return builder()
          .test(test)
          .interval(interval)
          .timeout(timeout)
          .retries(retries)
          .startPeriod(startPeriod)
          .build;
    }

    public static Builder builder() {
      return new Healthcheck.Builder();
    }

    public  static class Builder extends AbstractBuilder {
      public  Builder test(final List<String> test) { build.test = safeList(test); return this; };

      public  Builder interval(final Long interval) { build.interval = interval; return this; };

      public  Builder timeout(final Long timeout) { build.timeout = timeout; return this; };

      public  Builder retries(final Integer retries) { build.retries = retries; return this; };

      public  Builder startPeriod(final Long startPeriod) { build.startPeriod = startPeriod; return this; };

      public Healthcheck build = new Healthcheck();
    }
  }
  
  public  static class NetworkingConfig {
    @JsonProperty("EndpointsConfig")
    public ImmutableMap<String, EndpointConfig> endpointsConfig;

    @JsonCreator
    public static NetworkingConfig create(
            @JsonProperty("EndpointsConfig") final Map<String, EndpointConfig> endpointsConfig) {
      final ImmutableMap<String, EndpointConfig> endpointsConfigCopy = AbstractBuilder.safeMap(endpointsConfig);
      return new NetworkingConfig(endpointsConfigCopy);
    }

		public NetworkingConfig(ImmutableMap<String, EndpointConfig> endpointsConfig) {
			super();
			this.endpointsConfig = endpointsConfig;
		}
    
  }
}
