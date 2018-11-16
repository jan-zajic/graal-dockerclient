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
import com.google.common.collect.ImmutableMap;
import net.jzajic.graalvm.client.messages.ContainerConfig;
import net.jzajic.graalvm.client.messages.mount.Mount;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;


@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class ContainerSpec {

  @JsonProperty("Image")
  public String image;

  /**
   * @since API 1.26
   */
  @Nullable
  @JsonProperty("Hostname")
  public String hostname;

  @Nullable
  @JsonProperty("Labels")
  public Map<String, String> labels;

  @Nullable
  @JsonProperty("Command")
  public ImmutableList<String> command;

  @Nullable
  @JsonProperty("Args")
  public ImmutableList<String> args;

  @Nullable
  @JsonProperty("Env")
  public ImmutableList<String> env;

  @Nullable
  @JsonProperty("Dir")
  public String dir;

  @Nullable
  @JsonProperty("User")
  public String user;

  @Nullable
  @JsonProperty("Groups")
  public ImmutableList<String> groups;

  @Nullable
  @JsonProperty("TTY")
  public Boolean tty;

  @Nullable
  @JsonProperty("Mounts")
  public ImmutableList<Mount> mounts;

  @Nullable
  @JsonProperty("StopGracePeriod")
  public Long stopGracePeriod;

  /**
   * @since API 1.26
   */
  @Nullable
  @JsonProperty("Healthcheck")
  public ContainerConfig.Healthcheck healthcheck;

  /**
   * @since API 1.26
   */
  @Nullable
  @JsonProperty("Hosts")
  public ImmutableList<String> hosts;

  /**
   * @since API 1.26
   */
  @Nullable
  @JsonProperty("Secrets")
  public ImmutableList<SecretBind> secrets;

  /**
   * @since API 1.30
   */
  @Nullable
  @JsonProperty("Configs")
  public ImmutableList<ConfigBind> configs;

  @Nullable
  @JsonProperty("DNSConfig")
  public DnsConfig dnsConfig;

  public  static class Builder extends SwarmAbstractBuilder {

    public  Builder image(String image) { build.image = image; return this; };
    
    private Map<String, String> labelsBuilder = new HashMap<>();

    public Builder addLabel(final String label, final String value) {
      labelsBuilder.put(label, value);
      return this;
    }

    public  Builder hostname(String hostname) { build.hostname = hostname; return this; };

    public  Builder labels(Map<String, String> labels) { build.labels = labels; return this; };

    public  Builder command(String... commands) { build.command = safeList(commands, true); return this; };

    public  Builder command(List<String> commands) { build.command = safeList(commands, true); return this; };

    public  Builder args(String... args) { build.args = safeList(args); return this; };

    public  Builder args(List<String> args) { build.args = safeList(args); return this; };

    public  Builder env(String... env) { build.env = safeList(env); return this; };

    public  Builder env(List<String> env) { build.env = safeList(env); return this; };

    public  Builder dir(String dir) { build.dir = dir; return this; };

    public  Builder user(String user) { build.user = user; return this; };

    public  Builder groups(String... groups) { build.groups = safeList(groups); return this; };

    public  Builder groups(List<String> groups) { build.groups = safeList(groups); return this; };

    public  Builder tty(Boolean tty) { build.tty = tty; return this; };

    public  Builder mounts(Mount... mounts) { build.mounts = safeList(mounts); return this; };

    public  Builder mounts(List<Mount> mounts) { build.mounts = safeList(mounts); return this; };

    public  Builder stopGracePeriod(Long stopGracePeriod) { build.stopGracePeriod = stopGracePeriod; return this; };

    public  Builder dnsConfig(DnsConfig dnsConfig) { build.dnsConfig = dnsConfig; return this; };

    public  Builder healthcheck(ContainerConfig.Healthcheck healthcheck) { build.healthcheck = healthcheck; return this; };

    public  Builder hosts(List<String> hosts){ build.hosts = safeList(hosts); return this; };

    public  Builder secrets(List<SecretBind> secrets) { build.secrets = safeList(secrets, true); return this; };

    public  Builder configs(List<ConfigBind> configs) { build.configs = safeList(configs, true); return this; };

    public ContainerSpec build;
    
    public Builder() {
			build = new ContainerSpec();
			build.labels = Collections.unmodifiableMap(labelsBuilder);
		}
    
  }

  public static ContainerSpec.Builder builder() {
    return new ContainerSpec.Builder();
  }

  @JsonCreator
  static ContainerSpec create(
      @JsonProperty("Image") final String image,
      @JsonProperty("Labels") final Map<String, String> labels,
      @JsonProperty("Hostname") final String hostname,
      @JsonProperty("Command") final List<String> command,
      @JsonProperty("Args") final List<String> args,
      @JsonProperty("Env") final List<String> env,
      @JsonProperty("Dir") final String dir,
      @JsonProperty("User") final String user,
      @JsonProperty("Groups") final List<String> groups,
      @JsonProperty("TTY") final Boolean tty,
      @JsonProperty("Mounts") final List<Mount> mounts,
      @JsonProperty("StopGracePeriod") final Long stopGracePeriod,
      @JsonProperty("Healthcheck") final ContainerConfig.Healthcheck healthcheck,
      @JsonProperty("Hosts") final List<String> hosts,
      @JsonProperty("Secrets") final List<SecretBind> secrets,
      @JsonProperty("DNSConfig") final DnsConfig dnsConfig,
      @JsonProperty("Configs") final List<ConfigBind> configs) {
    final Builder builder = builder()
        .image(image)
        .hostname(hostname)
        .args(args)
        .env(env)
        .dir(dir)
        .user(user)
        .groups(groups)
        .tty(tty)
        .mounts(mounts)
        .stopGracePeriod(stopGracePeriod)
        .healthcheck(healthcheck)
        .hosts(hosts)
        .dnsConfig(dnsConfig)
        .command(command)
        .secrets(secrets)
        .configs(configs);

    if (labels != null) {
      builder.labels(labels);
    }

    return builder.build;
  }
}
