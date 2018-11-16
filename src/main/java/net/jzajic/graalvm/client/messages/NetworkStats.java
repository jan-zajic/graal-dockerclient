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




@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class NetworkStats {

  @JsonProperty("rx_bytes")
  public Long rxBytes;

  @JsonProperty("rx_packets")
  public Long rxPackets;

  @JsonProperty("rx_dropped")
  public Long rxDropped;

  @JsonProperty("rx_errors")
  public Long rxErrors;

  @JsonProperty("tx_bytes")
  public Long txBytes;

  @JsonProperty("tx_packets")
  public Long txPackets;

  @JsonProperty("tx_dropped")
  public Long txDropped;

  @JsonProperty("tx_errors")
  public Long txErrors;

  @JsonCreator
  static NetworkStats create(
      @JsonProperty("rx_bytes") final Long rxBytes,
      @JsonProperty("rx_packets") final Long rxPackets,
      @JsonProperty("rx_dropped") final Long rxDropped,
      @JsonProperty("rx_errors") final Long rxErrors,
      @JsonProperty("tx_bytes") final Long txBytes,
      @JsonProperty("tx_packets") final Long txPackets,
      @JsonProperty("tx_dropped") final Long txDropped,
      @JsonProperty("tx_errors") final Long txErrors) {
    return new NetworkStats(rxBytes, rxPackets, rxDropped, rxErrors, txBytes, txPackets,
        txDropped, txErrors);
  }

	public NetworkStats(Long rxBytes, Long rxPackets, Long rxDropped, Long rxErrors, Long txBytes, Long txPackets, Long txDropped, Long txErrors) {
		super();
		this.rxBytes = rxBytes;
		this.rxPackets = rxPackets;
		this.rxDropped = rxDropped;
		this.rxErrors = rxErrors;
		this.txBytes = txBytes;
		this.txPackets = txPackets;
		this.txDropped = txDropped;
		this.txErrors = txErrors;
	}
  
}
