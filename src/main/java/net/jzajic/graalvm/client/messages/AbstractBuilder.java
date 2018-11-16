package net.jzajic.graalvm.client.messages;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import net.jzajic.graalvm.client.MapBuilder;

public abstract class AbstractBuilder {

	public static <X,Y> ImmutableMap<X,Y> safeMap(Map<X,Y> inputMap) {
		return safeMap(inputMap, false);
	}
	
	public static <X,Y> ImmutableMap<X,Y> safeMap(Map<X,Y> inputMap, boolean nullable) {
		if(inputMap == null)
			return nullable ? null : ImmutableMap.of();
		else
			return ImmutableMap.copyOf(inputMap);
	}
	
	public static <X> ImmutableList<X> safeList(X... inputArray) {
		return safeList(inputArray, false);
	}
	
	public static <X> ImmutableList<X> safeList(X[] inputArray, boolean nullable) {
		if(inputArray == null)
			return nullable ? null : ImmutableList.of();
		else
			return ImmutableList.copyOf(inputArray);
	}
	
	public static <X> ImmutableList<X> safeList(List<X> inputMap) {
		return safeList(inputMap, false);
	}
	
	public static <X> ImmutableList<X> safeList(List<X> inputMap, boolean nullable) {
		if(inputMap == null)
			return nullable ? null : ImmutableList.of();
		else
			return ImmutableList.copyOf(inputMap);
	}
	
	public static <X> ImmutableSet<X> safeSet(X... inputArray) {
		return safeSet(inputArray, false);
	}
	
	public static <X> ImmutableSet<X> safeSet(X[] inputArray, boolean nullable) {
		if(inputArray == null)
			return nullable ? null : ImmutableSet.of();
		else
			return ImmutableSet.copyOf((X[])inputArray);
	}
	
	public static <X> ImmutableSet<X> safeSet(Set<X> inputMap) {
		return safeSet(inputMap, false);
	}
	
	public static <X> ImmutableSet<X> safeSet(Set<X> inputMap, boolean nullable) {
		if(inputMap == null)
			return nullable ? null : ImmutableSet.of();
		else
			return ImmutableSet.copyOf(inputMap);
	}
	
	public static <X,Y> void safeAppend(Map<X,Y> to, Map<X,Y> values) {
		if(values != null && !values.isEmpty()) {
			to.putAll(values);
		}
	}
	
	public static <K, V> MapBuilder<K, V> mapBuilder() {
		final Map<K, V> map = new HashMap<>();
		return new MapBuilder<K, V>() {

			@Override
			public void put(K key, V value) {
				map.put(key, value);
			}

			@Override
			public Map<K, V> immutableView() {
				return Collections.unmodifiableMap(map);
			}

			@Override
			public void putAll(Map<K, V> items) {
				map.putAll(items);
			}
		};
	}
	
	public static <K, V> MapBuilder<K, V> nullableMapBuilder(Consumer<MapBuilder<K, V>> onFirstItem) {
		return new MapBuilder<K, V>() {

			private Map<K, V> map;
			
			@Override
			public void put(K key, V value) {
				if(key != null) {
					if(map == null)
						map = new HashMap<>();
					onFirstItem.accept(this);
					map.put(key, value);
				}
			}

			@Override
			public Map<K, V> immutableView() {
				if(map == null)
					return null;
				else
					return Collections.unmodifiableMap(map);
			}

			@Override
			public void putAll(Map<K, V> items) {
				if(items != null) {
					if(map == null)
						map = new HashMap<>();
					onFirstItem.accept(this);
					map.putAll(items);
				}
			}
		};
	}
		
}
