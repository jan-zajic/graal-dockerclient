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

import static net.jzajic.graalvm.FixtureUtil.fixture;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.jzajic.graalvm.client.ObjectMapperProvider;
import org.junit.Test;


public class DistributionTest {

  private final ObjectMapper mapper = ObjectMapperProvider.objectMapper();

  @Test
  public void test1_30() throws Exception {
    mapper.readValue(fixture("fixtures/1.30/distribution.json"), Distribution.class);
  }
}
