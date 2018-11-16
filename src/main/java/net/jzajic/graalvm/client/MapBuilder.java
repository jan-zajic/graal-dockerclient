package net.jzajic.graalvm.client;

import java.util.Map;

public interface MapBuilder<K,V> {
	
	void put(K key, V value);
	
	Map<K,V> immutableView();

	void putAll(Map<K, V> labels);
	
	
}
