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

import com.google.common.collect.ImmutableMap;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;


@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class Service {

  @JsonProperty("ID")
  public String id;

  @JsonProperty("Version")
  public Version version;

  @JsonProperty("CreatedAt")
  public Date createdAt;

  @JsonProperty("UpdatedAt")
  public Date updatedAt;

  @JsonProperty("Spec")
  public ServiceSpec spec;

  @JsonProperty("Endpoint")
  public Endpoint endpoint;

  @Nullable
  @JsonProperty("UpdateStatus")
  public UpdateStatus updateStatus;

  
  public  static class Criteria {

    /**
     * Filter by service id.
     */
    @Nullable
    public String serviceId;

    /**
     * Filter by service name.
     */
    @Nullable
    public String serviceName;

    /**
     * Filter by label.
     */
    @Nullable
    public Map<String, String> labels;
    
    public static Builder builder() {
      return new Criteria.Builder();
    }

    public  static class Builder extends SwarmAbstractBuilder {

      public  Builder serviceId(final String serviceId) { build.serviceId = serviceId; return this; };

      /**
       * @deprecated  As of release 7.0.0, replaced by {@link #serviceId(String)}.
       */
      @Deprecated
      public Builder withServiceId(final String serviceId) {
        serviceId(serviceId);
        return this;
      }

      public  Builder serviceName(final String serviceName) { build.serviceName = serviceName; return this; };

      /**
       * @deprecated  As of release 7.0.0, replaced by {@link #serviceName(String)}.
       */
      @Deprecated
      public Builder withServiceName(final String serviceName) {
        serviceName(serviceName);
        return this;
      }

      public  Builder labels(final Map<String, String> labels) { build.labels = safeMap(labels); return this; };
      
      Map<String, String> labelsBuilder = new HashMap<>();

      public Builder addLabel(final String label, final String value) {
        labelsBuilder.put(label, value);
        return this;
      }
      
      public Criteria build;
      
      public Builder() {
				build = new Criteria();
				build.labels = Collections.unmodifiableMap(labelsBuilder); 
			}
      
    }
  }

  public static Criteria.Builder find() {
    return Service.Criteria.builder();
  }

  @JsonCreator
  static Service create(
      @JsonProperty("ID") final String id,
      @JsonProperty("Version") final Version version,
      @JsonProperty("CreatedAt") final Date createdAt,
      @JsonProperty("UpdatedAt") final Date updatedAt,
      @JsonProperty("Spec") final ServiceSpec spec,
      @JsonProperty("Endpoint") final Endpoint endpoint,
      @JsonProperty("UpdateStatus") final UpdateStatus updateStatus) {
    return new Service(id, version, createdAt, updatedAt, spec, endpoint, updateStatus);
  }

	public Service(String id, Version version, Date createdAt, Date updatedAt, ServiceSpec spec, Endpoint endpoint, UpdateStatus updateStatus) {
		super();
		this.id = id;
		this.version = version;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.spec = spec;
		this.endpoint = endpoint;
		this.updateStatus = updateStatus;
	}
  
}
