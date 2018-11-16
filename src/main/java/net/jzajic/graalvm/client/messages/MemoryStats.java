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


import java.math.BigInteger;
import javax.annotation.Nullable;


@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class MemoryStats {

  @Nullable
  @JsonProperty("stats")
  public Stats stats;

  @Nullable
  @JsonProperty("max_usage")
  public Long maxUsage;

  @Nullable
  @JsonProperty("usage")
  public Long usage;

  @Nullable
  @JsonProperty("failcnt")
  public Long failcnt;

  @Nullable
  @JsonProperty("limit")
  public Long limit;

  @JsonCreator
  static MemoryStats create(
      @JsonProperty("stats") final Stats stats,
      @JsonProperty("max_usage") final Long maxUsage,
      @JsonProperty("usage") final Long usage,
      @JsonProperty("failcnt") Long failcnt,
      @JsonProperty("limit") Long limit
  ) {
    return new MemoryStats(stats, maxUsage, usage, failcnt, limit);
  }
  
  public MemoryStats(Stats stats, Long maxUsage, Long usage, Long failcnt, Long limit) {
		super();
		this.stats = stats;
		this.maxUsage = maxUsage;
		this.usage = usage;
		this.failcnt = failcnt;
		this.limit = limit;
	}

	public  static class Stats {

    @JsonProperty("active_file")
    public Long activeFile;

    @JsonProperty("total_active_file")
    public Long totalActiveFile;

    @JsonProperty("inactive_file")
    public Long inactiveFile;

    @JsonProperty("total_inactive_file")
    public Long totalInactiveFile;

    @JsonProperty("cache")
    public Long cache;

    @JsonProperty("total_cache")
    public Long totalCache;

    @JsonProperty("active_anon")
    public Long activeAnon;

    @JsonProperty("total_active_anon")
    public Long totalActiveAnon;

    @JsonProperty("inactive_anon")
    public Long inactiveAnon;

    @JsonProperty("total_inactive_anon")
    public Long totalInactiveAnon;

    @JsonProperty("hierarchical_memory_limit")
    public BigInteger hierarchicalMemoryLimit;

    @JsonProperty("mapped_file")
    public Long mappedFile;

    @JsonProperty("total_mapped_file")
    public Long totalMappedFile;

    @JsonProperty("pgmajfault")
    public Long pgmajfault;

    @JsonProperty("total_pgmajfault")
    public Long totalPgmajfault;

    @JsonProperty("pgpgin")
    public Long pgpgin;

    @JsonProperty("total_pgpgin")
    public Long totalPgpgin;

    @JsonProperty("pgpgout")
    public Long pgpgout;

    @JsonProperty("total_pgpgout")
    public Long totalPgpgout;

    @JsonProperty("pgfault")
    public Long pgfault;

    @JsonProperty("total_pgfault")
    public Long totalPgfault;

    @JsonProperty("rss")
    public Long rss;

    @JsonProperty("total_rss")
    public Long totalRss;

    @JsonProperty("rss_huge")
    public Long rssHuge;

    @JsonProperty("total_rss_huge")
    public Long totalRssHuge;

    @JsonProperty("unevictable")
    public Long unevictable;

    @JsonProperty("total_unevictable")
    public Long totalUnevictable;

    @Nullable
    @JsonProperty("total_writeback")
    public Long totalWriteback;

    @Nullable
    @JsonProperty("writeback")
    public Long writeback;

    @JsonCreator
    static Stats create(
        @JsonProperty("active_file") final Long activeFile,
        @JsonProperty("total_active_file") final Long totalActiveFile,
        @JsonProperty("inactive_file") final Long inactiveFile,
        @JsonProperty("total_inactive_file") final Long totalInactivefile,
        @JsonProperty("cache") final Long cache,
        @JsonProperty("total_cache") final Long totalCache,
        @JsonProperty("active_anon") final Long activeAnon,
        @JsonProperty("total_active_anon") final Long totalActiveAnon,
        @JsonProperty("inactive_anon") final Long inactiveAnon,
        @JsonProperty("total_inactive_anon") final Long totalInactiveAnon,
        @JsonProperty("hierarchical_memory_limit") final BigInteger hierarchicalMemoryLimit,
        @JsonProperty("mapped_file") final Long mappedFile,
        @JsonProperty("total_mapped_file") final Long totalMappedFile,
        @JsonProperty("pgmajfault") final Long pgmajfault,
        @JsonProperty("total_pgmajfault") final Long totalPgmajfault,
        @JsonProperty("pgpgin") final Long pgpgin,
        @JsonProperty("total_pgpgin") final Long totalPgpgin,
        @JsonProperty("pgpgout") final Long pgpgout,
        @JsonProperty("total_pgpgout") final Long totalPgpgout,
        @JsonProperty("pgfault") final Long pgfault,
        @JsonProperty("total_pgfault") final Long totalPgfault,
        @JsonProperty("rss") final Long rss,
        @JsonProperty("total_rss") final Long totalRss,
        @JsonProperty("rss_huge") final Long rssHuge,
        @JsonProperty("total_rss_huge") final Long totalRssHuge,
        @JsonProperty("unevictable") final Long unevictable,
        @JsonProperty("total_unevictable") final Long totalUnevictable,
        @JsonProperty("writeback") final Long writeback,
        @JsonProperty("total_writeback") final Long totalWriteback
    ) {
      return new Stats(
          activeFile, totalActiveFile, inactiveFile, totalInactivefile, cache, totalCache,
          activeAnon, totalActiveAnon, inactiveAnon, totalInactiveAnon, hierarchicalMemoryLimit,
          mappedFile, totalMappedFile, pgmajfault, totalPgmajfault, pgpgin, totalPgpgin, pgpgout,
          totalPgpgout, pgfault, totalPgfault, rss, totalRss, rssHuge, totalRssHuge, unevictable,
          totalUnevictable, writeback, totalWriteback);
    }

		public Stats(Long activeFile, Long totalActiveFile, Long inactiveFile, Long totalInactiveFile, Long cache, Long totalCache, Long activeAnon, Long totalActiveAnon, Long inactiveAnon, Long totalInactiveAnon, BigInteger hierarchicalMemoryLimit, Long mappedFile, Long totalMappedFile, Long pgmajfault, Long totalPgmajfault, Long pgpgin, Long totalPgpgin, Long pgpgout, Long totalPgpgout, Long pgfault, Long totalPgfault, Long rss, Long totalRss, Long rssHuge, Long totalRssHuge, Long unevictable,
				Long totalUnevictable, Long totalWriteback, Long writeback) {
			super();
			this.activeFile = activeFile;
			this.totalActiveFile = totalActiveFile;
			this.inactiveFile = inactiveFile;
			this.totalInactiveFile = totalInactiveFile;
			this.cache = cache;
			this.totalCache = totalCache;
			this.activeAnon = activeAnon;
			this.totalActiveAnon = totalActiveAnon;
			this.inactiveAnon = inactiveAnon;
			this.totalInactiveAnon = totalInactiveAnon;
			this.hierarchicalMemoryLimit = hierarchicalMemoryLimit;
			this.mappedFile = mappedFile;
			this.totalMappedFile = totalMappedFile;
			this.pgmajfault = pgmajfault;
			this.totalPgmajfault = totalPgmajfault;
			this.pgpgin = pgpgin;
			this.totalPgpgin = totalPgpgin;
			this.pgpgout = pgpgout;
			this.totalPgpgout = totalPgpgout;
			this.pgfault = pgfault;
			this.totalPgfault = totalPgfault;
			this.rss = rss;
			this.totalRss = totalRss;
			this.rssHuge = rssHuge;
			this.totalRssHuge = totalRssHuge;
			this.unevictable = unevictable;
			this.totalUnevictable = totalUnevictable;
			this.totalWriteback = totalWriteback;
			this.writeback = writeback;
		}
    
  }
}
