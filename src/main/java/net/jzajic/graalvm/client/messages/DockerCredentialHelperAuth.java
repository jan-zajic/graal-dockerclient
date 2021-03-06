/*-
 * -\-\-
 * docker-client
 * --
 * Copyright (C) 2016 - 2018 Spotify AB
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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Nullable;

/**
 * Represents the auth response received from a docker credential helper
 * on a "get" operation, or sent to a credential helper on a "store".
 *
 * <p>See {@link net.jzajic.graalvm.client.DockerCredentialHelper}.</p>
 */

public class DockerCredentialHelperAuth {
  @JsonProperty("Username")
  public String username;

  @JsonProperty("Secret")
  public String secret;

  @Nullable
  @JsonProperty("ServerURL")
  public String serverUrl;

  @JsonCreator
  public static DockerCredentialHelperAuth create(
        @JsonProperty("Username") final String username,
        @JsonProperty("Secret") final String secret,
        @JsonProperty("ServerURL") final String serverUrl) {
    return new DockerCredentialHelperAuth(username, secret, serverUrl);
  }

  public DockerCredentialHelperAuth(String username, String secret, String serverUrl) {
		super();
		this.username = username;
		this.secret = secret;
		this.serverUrl = serverUrl;
	}

	@JsonIgnore
  public RegistryAuth toRegistryAuth() {
    return RegistryAuth.builder()
        .username(username)
        .password(secret)
        .serverAddress(serverUrl)
        .build;
  }
}
