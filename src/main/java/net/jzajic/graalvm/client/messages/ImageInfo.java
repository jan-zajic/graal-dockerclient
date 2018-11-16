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


import java.util.Date;
import javax.annotation.Nullable;


@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class ImageInfo {

  @JsonProperty("Id")
  public String id;

  @JsonProperty("Parent")
  public String parent;

  @JsonProperty("Comment")
  public String comment;

  @JsonProperty("Created")
  public Date created;

  @JsonProperty("Container")
  public String container;

  @JsonProperty("ContainerConfig")
  public ContainerConfig containerConfig;

  @JsonProperty("DockerVersion")
  public String dockerVersion;

  @JsonProperty("Author")
  public String author;

  @JsonProperty("Config")
  public ContainerConfig config;

  @JsonProperty("Architecture")
  public String architecture;

  @JsonProperty("Os")
  public String os;

  @JsonProperty("Size")
  public Long size;

  @JsonProperty("VirtualSize")
  public Long virtualSize;

  @Nullable
  @JsonProperty("RootFS")
  public RootFs rootFs;

  @JsonCreator
  static ImageInfo create(
      @JsonProperty("Id") final String id,
      @JsonProperty("Parent") final String parent,
      @JsonProperty("Comment") final String comment,
      @JsonProperty("Created") final Date created,
      @JsonProperty("Container") final String container,
      @JsonProperty("ContainerConfig") final ContainerConfig containerConfig,
      @JsonProperty("DockerVersion") final String dockerVersion,
      @JsonProperty("Author") final String author,
      @JsonProperty("Config") final ContainerConfig config,
      @JsonProperty("Architecture") final String architecture,
      @JsonProperty("Os") final String os,
      @JsonProperty("Size") final Long size,
      @JsonProperty("VirtualSize") final Long virtualSize,
      @JsonProperty("RootFS") final RootFs rootFs) {
    return new ImageInfo(id, parent, comment, created, container, containerConfig,
        dockerVersion, author, config, architecture, os, size, virtualSize, rootFs);
  }

	public ImageInfo(String id, String parent, String comment, Date created, String container, ContainerConfig containerConfig, String dockerVersion, String author, ContainerConfig config, String architecture, String os, Long size, Long virtualSize, RootFs rootFs) {
		super();
		this.id = id;
		this.parent = parent;
		this.comment = comment;
		this.created = created;
		this.container = container;
		this.containerConfig = containerConfig;
		this.dockerVersion = dockerVersion;
		this.author = author;
		this.config = config;
		this.architecture = architecture;
		this.os = os;
		this.size = size;
		this.virtualSize = virtualSize;
		this.rootFs = rootFs;
	}
  
}
