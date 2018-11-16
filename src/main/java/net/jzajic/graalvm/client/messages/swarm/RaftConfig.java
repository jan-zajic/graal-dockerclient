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
public class RaftConfig {

  @Nullable
  @JsonProperty("SnapshotInterval")
  public Integer snapshotInterval;

  @Nullable
  @JsonProperty("KeepOldSnapshots")
  public Integer keepOldSnapshots;

  @Nullable
  @JsonProperty("LogEntriesForSlowFollowers")
  public Integer logEntriesForSlowFollowers;

  @Nullable
  @JsonProperty("ElectionTick")
  public Integer electionTick;

  @Nullable
  @JsonProperty("HeartbeatTick")
  public Integer heartbeatTick;

  @JsonCreator
  static RaftConfig create(
      @JsonProperty("SnapshotInterval") final Integer snapshotInterval,
      @JsonProperty("KeepOldSnapshots") final Integer keepOldSnapshots,
      @JsonProperty("LogEntriesForSlowFollowers") final Integer logEntriesForSlowFollowers,
      @JsonProperty("ElectionTick") final Integer electionTick,
      @JsonProperty("HeartbeatTick") final Integer heartbeatTick) {
    return builder()
        .snapshotInterval(snapshotInterval)
        .keepOldSnapshots(keepOldSnapshots)
        .logEntriesForSlowFollowers(logEntriesForSlowFollowers)
        .electionTick(electionTick)
        .heartbeatTick(heartbeatTick)
        .build;
  }

  public  static class Builder extends SwarmAbstractBuilder {

    public  Builder snapshotInterval(Integer snapshotInterval) { build.snapshotInterval = snapshotInterval; return this; };

    public  Builder keepOldSnapshots(Integer keepOldSnapshots) { build.keepOldSnapshots = keepOldSnapshots; return this; };

    public  Builder logEntriesForSlowFollowers(Integer logEntriesForSlowFollowers) { build.logEntriesForSlowFollowers = logEntriesForSlowFollowers; return this; };

    public  Builder electionTick(Integer electionTick) { build.electionTick = electionTick; return this; };

    public  Builder heartbeatTick(Integer heartbeatTick) { build.heartbeatTick = heartbeatTick; return this; };

    public RaftConfig build = new RaftConfig();
  }

  public static RaftConfig.Builder builder() {
    return new RaftConfig.Builder();
  }
}
