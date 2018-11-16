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

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import net.jzajic.graalvm.client.messages.AbstractBuilder;


@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class Task {

  @JsonProperty("ID")
  public String id;

  @JsonProperty("Version")
  public Version version;

  @JsonProperty("CreatedAt")
  public Date createdAt;

  @JsonProperty("UpdatedAt")
  public Date updatedAt;

  @Nullable
  @JsonProperty("Name")
  public String name;

  @Nullable
  @JsonProperty("Labels")
  public ImmutableMap<String, String> labels;

  @JsonProperty("Spec")
  public TaskSpec spec;

  @JsonProperty("ServiceID")
  public String serviceId;

  @Nullable
  @JsonProperty("Slot")
  public Integer slot;

  @Nullable
  @JsonProperty("NodeID")
  public String nodeId;

  @JsonProperty("Status")
  public TaskStatus status;

  @JsonProperty("DesiredState")
  public String desiredState;

  @Nullable
  @JsonProperty("NetworksAttachments")
  public ImmutableList<NetworkAttachment> networkAttachments;

  @JsonCreator
  static Task create(
      @JsonProperty("ID") final String id,
      @JsonProperty("Version") final Version version,
      @JsonProperty("CreatedAt") final Date createdAt,
      @JsonProperty("UpdatedAt") final Date updatedAt,
      @JsonProperty("Name") final String name,
      @JsonProperty("Labels") final Map<String, String> labels,
      @JsonProperty("Spec") final TaskSpec spec,
      @JsonProperty("ServiceID") final String serviceId,
      @JsonProperty("Slot") final Integer slot,
      @JsonProperty("NodeID") final String nodeId,
      @JsonProperty("Status") final TaskStatus status,
      @JsonProperty("DesiredState") final String desiredState,
      @JsonProperty("NetworksAttachments") final List<NetworkAttachment> networkAttachments) {
    final ImmutableMap<String, String> labelsT = AbstractBuilder.safeMap(labels);
    final ImmutableList<NetworkAttachment> networkAttachmentsT = AbstractBuilder.safeList(networkAttachments);
    return new Task(id, version, createdAt, updatedAt, name, labelsT,
        spec, serviceId, slot, nodeId, status, desiredState, networkAttachmentsT);
  }
  
  public Task(String id, Version version, Date createdAt, Date updatedAt, String name, ImmutableMap<String, String> labels, TaskSpec spec, String serviceId, Integer slot, String nodeId, TaskStatus status, String desiredState, ImmutableList<NetworkAttachment> networkAttachments) {
		super();
		this.id = id;
		this.version = version;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.name = name;
		this.labels = labels;
		this.spec = spec;
		this.serviceId = serviceId;
		this.slot = slot;
		this.nodeId = nodeId;
		this.status = status;
		this.desiredState = desiredState;
		this.networkAttachments = networkAttachments;
	}

	public  static class Criteria {

    /**
     * Filter by task id.
     */
    @Nullable
    public String taskId;

    /**
     * Filter by task name.
     */
    @Nullable
    public String taskName;

    /**
     * Filter by service name.
     */
    @Nullable
    public String serviceName;

    /**
     * Filter by node id.
     */
    @Nullable
    public String nodeId;

    /**
     * Filter by label.
     */
    @Nullable
    public String label;

    /**
     * Filter by desired state.
     */
    @Nullable
    public String desiredState;

    public static Builder builder() {
      return new Criteria.Builder();
    }

    public  static class Builder extends SwarmAbstractBuilder {

      public  Builder taskId(final String taskId) { build.taskId = taskId; return this; };

      public  Builder taskName(final String taskName) { build.taskName = taskName; return this; };

      public  Builder serviceName(final String serviceName) { build.serviceName = serviceName; return this; };


      public  Builder nodeId(final String nodeId) { build.nodeId = nodeId; return this; };

      public  Builder label(final String label) { build.label = label; return this; };


      public  Builder desiredState(final String desiredState) { build.desiredState = desiredState; return this; };

      public Criteria build = new Criteria();
    }
  }

  public static Criteria.Builder find() {
    return Criteria.builder();
  }
}
