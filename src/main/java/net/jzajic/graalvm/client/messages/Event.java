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
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import com.google.common.collect.ImmutableMap;
import net.jzajic.graalvm.client.jackson.UnixTimestampDeserializer;
import net.jzajic.graalvm.client.jackson.UnixTimestampSerializer;

import java.util.Date;
import java.util.Map;

import javax.annotation.Nullable;


@JsonAutoDetect(fieldVisibility = ANY, setterVisibility = NONE, getterVisibility = NONE)
public class Event {

  @Nullable
  @JsonProperty("Type")
  public Type type;

  /**
   * Event action.
   * @return action
   * @since API 1.22
   */
  @Nullable
  @JsonProperty("Action")
  public String action;

  /**
   * Event actor.
   * @return {@link Actor}
   * @since API 1.22
   */
  @Nullable
  @JsonProperty("Actor")
  public Actor actor;

  @JsonProperty("time")
  @JsonDeserialize(using = UnixTimestampDeserializer.class)
  @JsonSerialize(using = UnixTimestampSerializer.class)
  public Date time;

  @Nullable
  @JsonProperty("timeNano")
  public Long timeNano;

  @JsonCreator
  static Event create(
      @JsonProperty("Type") final Type type,
      @JsonProperty("Action") final String action,
      @JsonProperty("Actor") final Actor actor,
      @JsonProperty("time") final Date time,
      @JsonProperty("timeNano") final Long timeNano) {
    return new Event(type, action, actor, time, timeNano);
  }
  
  public Event(Type type, String action, Actor actor, Date time, Long timeNano) {
		super();
		this.type = type;
		this.action = action;
		this.actor = actor;
		this.time = time;
		this.timeNano = timeNano;
	}

	public  static class Actor {

    @JsonProperty("ID")
    public String id;

    @Nullable
    @JsonProperty("Attributes")
    public ImmutableMap<String, String> attributes;

    @JsonCreator
    static Actor create(
        @JsonProperty("ID") final String id,
        @JsonProperty("Attributes") final Map<String, String> attributes) {
      final ImmutableMap<String, String> attributesT = AbstractBuilder.safeMap(attributes);
      return new Actor(id, attributesT);
    }

		public Actor(String id, ImmutableMap<String, String> attributes) {
			super();
			this.id = id;
			this.attributes = attributes;
		}
    
  }

  public enum Type {
    CONTAINER("container"),
    IMAGE("image"),
    VOLUME("volume"),
    NETWORK("network"),
    DAEMON("daemon"),
    PLUGIN("plugin"),
    NODE("node"),
    SERVICE("service"),
    SECRET("secret");

    private final String name;
    
    Type(final String name) {
      this.name = name;
    }

    @JsonValue
    public String getName() {
      return name;
    }
    
    @JsonCreator
    public static Type fromName(String name) {
    	for (Type typ : Type.values()) {
				if(typ.name.equals(name))
					return typ;
			}
    	return null;
    }
    
  }
}
