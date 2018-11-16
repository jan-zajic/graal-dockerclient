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


import javax.annotation.Nullable;



@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class ContainerMount {

  @Nullable
  @JsonProperty("Type")
  public String type;

  @Nullable
  @JsonProperty("Name")
  public String name;

  @JsonProperty("Source")
  public String source;

  @JsonProperty("Destination")
  public String destination;

  @Nullable
  @JsonProperty("Driver")
  public String driver;

  @JsonProperty("Mode")
  public String mode;

  @JsonProperty("RW")
  public Boolean rw;

  @Nullable
  @JsonProperty("Propagation")
  public String propagation;

  @JsonCreator
  static ContainerMount create(
      @JsonProperty("Type") final String type,
      @JsonProperty("Name") final String name,
      @JsonProperty("Source") final String source,
      @JsonProperty("Destination") final String destination,
      @JsonProperty("Driver") final String driver,
      @JsonProperty("Mode") final String mode,
      @JsonProperty("RW") final Boolean rw,
      @JsonProperty("Propagation") final String propagation) {
    return new ContainerMount(type, name, source, destination,
            driver, mode, rw, propagation);
  }

	public ContainerMount(String type, String name, String source, String destination, String driver, String mode, Boolean rw, String propagation) {
		super();
		this.type = type;
		this.name = name;
		this.source = source;
		this.destination = destination;
		this.driver = driver;
		this.mode = mode;
		this.rw = rw;
		this.propagation = propagation;
	}
  
}
