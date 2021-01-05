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
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;

import java.util.Date;
import java.util.List;

import javax.annotation.Nullable;

@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class ContainerState {

	@Nullable
	@JsonProperty("Status")
	public String status;

	@JsonProperty("Running")
	public Boolean running;

	@JsonProperty("Paused")
	public Boolean paused;

	@Nullable
	@JsonProperty("Restarting")
	public Boolean restarting;

	@JsonProperty("Pid")
	public Integer pid;

	@JsonProperty("ExitCode")
	public Long exitCode;

	@JsonProperty("StartedAt")
	public Date startedAt;

	@JsonProperty("FinishedAt")
	public Date finishedAt;

	@Nullable
	@JsonProperty("Error")
	public String error;

	@Nullable
	@JsonProperty("OOMKilled")
	public Boolean oomKilled;

	@Nullable
	@JsonProperty("Health")
	public Health health;

	@Nullable
	@JsonProperty("Ghost")
	public Boolean ghost;

	@JsonCreator
	static ContainerState create(
			@JsonProperty("Status") final String status,
			@JsonProperty("Running") final Boolean running,
			@JsonProperty("Paused") final Boolean addr,
			@JsonProperty("Restarting") final Boolean restarting,
			@JsonProperty("Pid") final Integer pid,
			@JsonProperty("ExitCode") final Long exitCode,
			@JsonProperty("StartedAt") final Date startedAt,
			@JsonProperty("FinishedAt") final Date finishedAt,
			@JsonProperty("Error") final String error,
			@JsonProperty("OOMKilled") final Boolean oomKilled,
			@JsonProperty("Health") final Health health,
			@JsonProperty("Ghost") final Boolean ghost) {
		return new ContainerState(
				status,
					running,
					addr,
					restarting,
					pid,
					exitCode,
					startedAt,
					finishedAt,
					error,
					oomKilled,
					health,
					ghost);
	}

	public ContainerState(String status, Boolean running, Boolean paused, Boolean restarting, Integer pid, Long exitCode, Date startedAt, Date finishedAt, String error, Boolean oomKilled, Health health, Boolean ghost) {
		super();
		this.status = status;
		this.running = running;
		this.paused = paused;
		this.restarting = restarting;
		this.pid = pid;
		this.exitCode = exitCode;
		this.startedAt = startedAt;
		this.finishedAt = finishedAt;
		this.error = error;
		this.oomKilled = oomKilled;
		this.health = health;
		this.ghost = ghost;
	}

	public static class HealthLog {

		@JsonProperty("Start")
		public Date start;

		@JsonProperty("End")
		public Date end;

		@JsonProperty("ExitCode")
		public Long exitCode;

		@JsonProperty("Output")
		public String output;

		@JsonCreator
		static HealthLog create(
				@JsonProperty("Start") final Date start,
				@JsonProperty("End") final Date end,
				@JsonProperty("ExitCode") final Long exitCode,
				@JsonProperty("Output") final String output) {
			return new HealthLog(start, end, exitCode, output);
		}

		public HealthLog(Date start, Date end, Long exitCode, String output) {
			super();
			this.start = start;
			this.end = end;
			this.exitCode = exitCode;
			this.output = output;
		}

	}

	public static class Health {

		@JsonProperty("Status")
		public String status;

		@JsonProperty("FailingStreak")
		public Integer failingStreak;

		@JsonProperty("Log")
		public ImmutableList<HealthLog> log;

		@JsonCreator
		static Health create(
				@JsonProperty("Status") final String status,
				@JsonProperty("FailingStreak") final Integer failingStreak,
				@JsonProperty("Log") final List<HealthLog> log) {
			final ImmutableList<HealthLog> logT = AbstractBuilder.safeList(log);
			return new Health(status, failingStreak, logT);
		}

		public Health(String status, Integer failingStreak, ImmutableList<HealthLog> log) {
			super();
			this.status = status;
			this.failingStreak = failingStreak;
			this.log = log;
		}

	}

	public String humanReadableState() {
		if (Boolean.TRUE.equals(paused))
			return "Paused";
		if (Boolean.TRUE.equals(restarting))
			return "Restarting";
		if (Boolean.TRUE.equals(running))
			return Boolean.TRUE.equals(ghost) ? "Ghost" : humanReadableHealthStatus();
		else
			return String.format("Exit %s", exitCode);
	}

	/**
	 * Generate UP status string with up time and health
	 * 
	 * @return
	 */
	private String humanReadableHealthStatus() {
		String statusString = "Up";
		String containerStatus = this.health != null ? this.health.status : null;
		if ("starting".equals(containerStatus))
			statusString += " (health: starting)";
		else if (!Strings.isNullOrEmpty(containerStatus))
			statusString += String.format(" (%s)", containerStatus);
		return statusString;
	}

	@Override
	public String toString() {
		return humanReadableState();
	}

	public String basicState() {
		if (Boolean.TRUE.equals(paused))
			return "Paused";
		if (Boolean.TRUE.equals(restarting))
			return "Restarting";
		if (Boolean.TRUE.equals(running))
			return "Running";
		else
			return "Exited";
	}

}
