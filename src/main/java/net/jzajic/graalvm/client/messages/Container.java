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

import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class Container {

  @JsonProperty("Id")
  public String id;

  @Nullable
  @JsonProperty("Names")
  public ImmutableList<String> names;

  @JsonProperty("Image")
  public String image;

  @Nullable
  @JsonProperty("ImageID")
  public String imageId;

  @JsonProperty("Command")
  public String command;

  @JsonProperty("Created")
  public Long created;

  @Nullable
  @JsonProperty("State")
  public String state;

  @JsonProperty("Status")
  public String status;

  @Nullable
  @JsonProperty("Ports")
  public ImmutableList<PortMapping> ports;

  @Nullable
  @JsonProperty("Labels")
  public ImmutableMap<String, String> labels;

  @Nullable
  @JsonProperty("SizeRw")
  public Long sizeRw;

  @Nullable
  @JsonProperty("SizeRootFs")
  public Long sizeRootFs;

  @Nullable
  @JsonProperty("NetworkSettings")
  public NetworkSettings networkSettings;

  @Nullable
  @JsonProperty("Mounts")
  public ImmutableList<ContainerMount> mounts;

  /**
   * Returns port information the way that <code>docker ps</code> does.
   * <code>0.0.0.0:5432-&gt;5432/tcp</code> or <code>6379/tcp</code>.
   *
   * <p>It should not be used to extract detailed information of ports. To do so, please refer to
   * {@link net.jzajic.graalvm.client.messages.PortBinding}.
   *
   * @return port information as docker ps does.
   * @see net.jzajic.graalvm.client.messages.PortBinding
   */
  public String portsAsString() {
    final StringBuilder sb = new StringBuilder();
    if (ports != null) {
      for (final PortMapping port : ports) {
        if (sb.length() > 0) {
          sb.append(", ");
        }
        if (port.ip != null) {
          sb.append(port.ip).append(":");
        }
        if (port.publicPort > 0) {
          sb.append(port.privatePort).append("->").append(port.publicPort);
        } else {
          sb.append(port.privatePort);
        }
        sb.append("/").append(port.type);
      }
    }

    return sb.toString();
  }

  @JsonCreator
  static Container create(
      @JsonProperty("Id") final String id,
      @JsonProperty("Names") final List<String> names,
      @JsonProperty("Image") final String image,
      @JsonProperty("ImageID") final String imageId,
      @JsonProperty("Command") final String command,
      @JsonProperty("Created") final Long created,
      @JsonProperty("State") final String state,
      @JsonProperty("Status") final String status,
      @JsonProperty("Ports") final List<PortMapping> ports,
      @JsonProperty("Labels") final Map<String, String> labels,
      @JsonProperty("SizeRw") final Long sizeRw,
      @JsonProperty("SizeRootFs") final Long sizeRootFs,
      @JsonProperty("NetworkSettings") final NetworkSettings networkSettings,
      @JsonProperty("Mounts") final List<ContainerMount> mounts) {
    final ImmutableMap<String, String> labelsT = AbstractBuilder.safeMap(labels);
    final ImmutableList<ContainerMount> mountsT = AbstractBuilder.safeList(mounts);
    final ImmutableList<String> namesT = AbstractBuilder.safeList(names);
    final ImmutableList<PortMapping> portsT = AbstractBuilder.safeList(ports);

    return new Container(id, namesT, image, imageId, command,
        created, state, status, portsT, labelsT, sizeRw,
        sizeRootFs, networkSettings, mountsT);
  }
  
  public Container(String id, ImmutableList<String> names, String image, String imageId, String command, Long created, String state, String status, ImmutableList<PortMapping> ports, ImmutableMap<String, String> labels, Long sizeRw, Long sizeRootFs, NetworkSettings networkSettings, ImmutableList<ContainerMount> mounts) {
		super();
		this.id = id;
		this.names = names;
		this.image = image;
		this.imageId = imageId;
		this.command = command;
		this.created = created;
		this.state = state;
		this.status = status;
		this.ports = ports;
		this.labels = labels;
		this.sizeRw = sizeRw;
		this.sizeRootFs = sizeRootFs;
		this.networkSettings = networkSettings;
		this.mounts = mounts;
	}

	public  static class PortMapping {

    @JsonProperty("PrivatePort")
    public Integer privatePort;

    @JsonProperty("PublicPort")
    public Integer publicPort;

    @JsonProperty("Type")
    public String type;

    @Nullable
    @JsonProperty("IP")
    public String ip;

    @JsonCreator
    static PortMapping create(
        @JsonProperty("PrivatePort") final int privatePort,
        @JsonProperty("PublicPort") final int publicPort,
        @JsonProperty("Type") final String type,
        @JsonProperty("IP") final String ip) {
      return new PortMapping(privatePort, publicPort, type, ip);
    }

		public PortMapping(Integer privatePort, Integer publicPort, String type, String ip) {
			super();
			this.privatePort = privatePort;
			this.publicPort = publicPort;
			this.type = type;
			this.ip = ip;
		}
		
		@Override
		public String toString() {
			StringBuilder b = new StringBuilder();
			if(ip != null) {
				b.append(ip);
			}
			if(b.length() > 0) {
				b.append(":");
			}
			if(publicPort != null && publicPort > 0)
				b.append(publicPort);
			if(b.length() > 0) {
				b.append("->");
			}
			if(b.length() > 0) {
				b.append(privatePort);
				b.append("/");
				b.append(type);
			}
			return b.toString();
		}
    
  }
}
