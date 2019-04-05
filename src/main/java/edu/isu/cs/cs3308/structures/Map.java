package edu.isu.cs.cs3308.structures;

/**
 * A basic Map ADT
 * @param <K> keys
 * @param <V> values
 */
public interface Map<K,V> {
    /**
     * Returns the number if entries in the map.
     * @return number of entries
     */
    int size();

    /**
     * A boolean whether the map is empty.
     * @return
     */
    boolean isEmpty();

    /**
     * Returns the value associated with the given key.
     * @param key
     * @return V value
     */
    V get(K key);

    /**
     * If there is not an entry with the <K,V> pair already, <K,V> is added to the map
     * and null is returned. Else replaces the old V with the new V and returns the old V.
     * @param key K
     * @param value V
     * @return null or the old value
     */
    V put(K key, V value);

    /**
     * Removes the entry with the key equal to K.
     * @param key
     * @return value or null if there was no such key
     */
    V remove(K key);
    Iterable<K> keySet();
    Iterable<V> values();
    Iterable<Entry<K,V>> entrySet();
}
