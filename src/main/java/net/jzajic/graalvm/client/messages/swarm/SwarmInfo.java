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
import javax.annotation.Nullable;


@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class SwarmInfo {

  @Nullable
  @JsonProperty("Cluster")
  public SwarmCluster cluster;
  
  @JsonProperty("ControlAvailable")
  public boolean controlAvailable;

  @JsonProperty("Error")
  public String error;

  @JsonProperty("LocalNodeState")
  public String localNodeState;

  @JsonProperty("NodeAddr")
  public String nodeAddr;

  @JsonProperty("NodeID")
  public String nodeId;

  @Nullable
  @JsonProperty("Nodes")
  public Integer nodes;

  @Nullable
  @JsonProperty("Managers")
  public Integer managers;
  
  @Nullable
  @JsonProperty("RemoteManagers")
  public ImmutableList<RemoteManager> remoteManagers;

  @JsonCreator
  static SwarmInfo create(
      @JsonProperty("Cluster") final SwarmCluster cluster,
      @JsonProperty("ControlAvailable") final boolean controlAvailable,
      @JsonProperty("Error") final String error,
      @JsonProperty("LocalNodeState") final String localNodeState,
      @JsonProperty("NodeAddr") final String nodeAddr,
      @JsonProperty("NodeID") final String nodeId,
      @JsonProperty("Nodes") final Integer nodes,
      @JsonProperty("Managers") final Integer managers,
      @JsonProperty("RemoteManagers") final ImmutableList<RemoteManager> remoteManagers) {
    return new SwarmInfo(cluster, controlAvailable, error, localNodeState, nodeAddr, 
        nodeId, nodes, managers, remoteManagers);
  }

	public SwarmInfo(SwarmCluster cluster, boolean controlAvailable, String error, String localNodeState, String nodeAddr, String nodeId, Integer nodes, Integer managers, ImmutableList<RemoteManager> remoteManagers) {
		super();
		this.cluster = cluster;
		this.controlAvailable = controlAvailable;
		this.error = error;
		this.localNodeState = localNodeState;
		this.nodeAddr = nodeAddr;
		this.nodeId = nodeId;
		this.nodes = nodes;
		this.managers = managers;
		this.remoteManagers = remoteManagers;
	}
  
  
  
}
