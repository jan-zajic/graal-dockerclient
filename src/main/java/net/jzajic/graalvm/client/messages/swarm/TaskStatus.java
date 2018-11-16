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


import java.util.Date;
import javax.annotation.Nullable;


@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class TaskStatus {

  public static final String TASK_STATE_NEW = "new";
  public static final String TASK_STATE_ALLOCATED = "allocated";
  public static final String TASK_STATE_PENDING = "pending";
  public static final String TASK_STATE_ASSIGNED = "assigned";
  public static final String TASK_STATE_ACCEPTED = "accepted";
  public static final String TASK_STATE_PREPARING = "preparing";
  public static final String TASK_STATE_READY = "ready";
  public static final String TASK_STATE_STARTING = "starting";
  public static final String TASK_STATE_RUNNING = "running";
  public static final String TASK_STATE_COMPLETE = "complete";
  public static final String TASK_STATE_SHUTDOWN = "shutdown";
  public static final String TASK_STATE_FAILED = "failed";
  public static final String TASK_STATE_REJECTED = "rejected";

  @JsonProperty("Timestamp")
  public Date timestamp;

  @JsonProperty("State")
  public String state;

  @JsonProperty("Message")
  public String message;

  @Nullable
  @JsonProperty("Err")
  public String err;

  @Nullable
  @JsonProperty("ContainerStatus")
  public ContainerStatus containerStatus;

  @JsonCreator
  static TaskStatus create(
      @JsonProperty("Timestamp") final Date timestamp,
      @JsonProperty("State") final String state,
      @JsonProperty("Message") final String message,
      @JsonProperty("Err") final String err,
      @JsonProperty("ContainerStatus") final ContainerStatus containerStatus) {
    return new TaskStatus(timestamp, state, message, err, containerStatus);
  }

	public TaskStatus(Date timestamp, String state, String message, String err, ContainerStatus containerStatus) {
		super();
		this.timestamp = timestamp;
		this.state = state;
		this.message = message;
		this.err = err;
		this.containerStatus = containerStatus;
	}
  
  
  
}
