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

package net.jzajic.graalvm.client;

import java.io.Closeable;
import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;

import net.jzajic.graalvm.client.messages.Event;

public class EventReader implements Closeable {

  private final JsonFactory factory;
  private final CloseableHttpResponse response;
  private JsonParser parser;

  public EventReader(final CloseableHttpResponse response, final JsonFactory factory) {
    this.response = response;
    this.factory = factory;
  }

  public Event nextMessage() throws IOException {
    if (this.parser == null) {
      this.parser = factory.createParser(response.getEntity().getContent());
    }

    // If the parser is closed, there's no new event
    if (this.parser.isClosed()) {
      return null;
    }

    // Read tokens until we get a start object
    if (parser.nextToken() == null) {
      return null;
    }

    return parser.readValueAs(Event.class);
  }

  @Override
  public void close() throws IOException {
    response.close();
  }

}
