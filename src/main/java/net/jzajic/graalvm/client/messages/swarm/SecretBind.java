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



@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class SecretBind {
  @JsonProperty("File")
  public SecretFile file;

  @JsonProperty("SecretID")
  public String secretId;

  @JsonProperty("SecretName")
  public String secretName;

  public static Builder builder() {
    return new SecretBind.Builder();
  }

  public  static class Builder extends SwarmAbstractBuilder {

    public  Builder file(SecretFile file) { build.file = file; return this; };

    public  Builder secretId(String secretId) { build.secretId = secretId; return this; };

    public  Builder secretName(String secretName) { build.secretName = secretName; return this; };

    public SecretBind build = new SecretBind();
  }

  @JsonCreator
  static SecretBind create(
         @JsonProperty("File") SecretFile file,
         @JsonProperty("SecretID") String secretId,
         @JsonProperty("SecretName") String secretName) {
    return builder()
        .file(file)
        .secretId(secretId)
        .secretName(secretName)
        .build;
  }
}
