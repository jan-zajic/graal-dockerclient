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
public class RestartPolicy {

  public static final String RESTART_POLICY_NONE = "none";
  public static final String RESTART_POLICY_ON_FAILURE = "on-failure";
  public static final String RESTART_POLICY_ANY = "any";

  @Nullable
  @JsonProperty("Condition")
  public String condition;

  @Nullable
  @JsonProperty("Delay")
  public Long delay;

  @Nullable
  @JsonProperty("MaxAttempts")
  public Integer maxAttempts;

  @Nullable
  @JsonProperty("Window")
  public Long window;

  public  static class Builder extends SwarmAbstractBuilder {

    public  Builder condition(String condition) { build.condition = condition; return this; };

    /**
     * @deprecated  As of release 7.0.0, replaced by {@link #condition(String)}.
     */
    @Deprecated
    public Builder withCondition(final String condition) {
      condition(condition);
      return this;
    }

    public  Builder delay(Long delay) { build.delay = delay; return this; };

    /**
     * @deprecated  As of release 7.0.0, replaced by {@link #delay(Long)}.
     */
    @Deprecated
    public Builder withDelay(final Long delay) {
      delay(delay);
      return this;
    }

    public  Builder maxAttempts(Integer maxAttempts) { build.maxAttempts = maxAttempts; return this; };

    /**
     * @deprecated  As of release 7.0.0, replaced by {@link #maxAttempts(Integer)}.
     */
    @Deprecated
    public Builder withMaxAttempts(final Integer maxAttempts) {
      maxAttempts(maxAttempts);
      return this;
    }

    public  Builder window(Long window) { build.window = window; return this; };

    /**
     * @deprecated  As of release 7.0.0, replaced by {@link #window(Long)}.
     */
    @Deprecated
    public Builder withWindow(long window) {
      window(window);
      return this;
    }

    public RestartPolicy build  =new RestartPolicy();
  }

  public static RestartPolicy.Builder builder() {
    return new RestartPolicy.Builder();
  }

  @JsonCreator
  static RestartPolicy create(
      @JsonProperty("Condition") final String condition,
      @JsonProperty("Delay") final Long delay,
      @JsonProperty("MaxAttempts") final Integer maxAttempts,
      @JsonProperty("Window") final Long window) {
    return builder()
        .condition(condition)
        .delay(delay)
        .maxAttempts(maxAttempts)
        .window(window)
        .build;
  }
}
