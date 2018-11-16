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

import javax.annotation.Nullable;



@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class ManagerStatus {

  @Nullable
  @JsonProperty("Leader")
  public Boolean leader;

  @JsonProperty("Reachability")
  public String reachability;

  @JsonProperty("Addr")
  public String addr;

  @JsonCreator
  static ManagerStatus create(@JsonProperty("Leader") final Boolean leader,
                              @JsonProperty("Reachability") final String reachability,
                              @JsonProperty("Addr") final String address) {
    return new ManagerStatus(leader, reachability, address);
  }

	public ManagerStatus(Boolean leader, String reachability, String addr) {
		super();
		this.leader = leader;
		this.reachability = reachability;
		this.addr = addr;
	}
  
}
