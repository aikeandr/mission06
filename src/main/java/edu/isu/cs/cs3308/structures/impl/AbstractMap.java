package edu.isu.cs.cs3308.structures.impl;

import edu.isu.cs.cs3308.structures.Entry;
import edu.isu.cs.cs3308.structures.Map;
import java.util.Iterator;

/**
 *
 * @param <K>
 * @param <V>
 * @author Andrew Aikens
 */
public abstract class AbstractMap<K,V> implements Map<K,V> {
    /**
     * Nested class implementing the Entry interface.
     * Handles Storing key-value pairs for the map.
     *
     * @param <K> key
     * @param <V> value
     */
    protected static class MapEntry<K,V> implements Entry<K,V> {
        private K key;
        private V value;
        public MapEntry(K _key, V _value){
            key = _key;
            value = _value;
        }

        /**
         * Returns a key.
         * @return key
         */
        @Override
        public K getKey() {
            return key;
        }

        /**
         * Returns a value.
         * @return value
         */
        @Override
        public V getValue() {
            return value;
        }

        /**
         * Sets the key for the entry.
         *
         * @param _key replaces the previous key
         */
        protected void setKey(K _key){
            key = _key;
        }

        /**
         * Replaces and returns the old value.
         *
         * @param _value new value for the entry
         * @return the previous value of the entry
         */
        protected V setValue(V _value){
            V old = value;
            value = _value;
            return old;
        }
    }

    /**
     * Makes keySet() iterable.
     */
    private class KeyIterator implements Iterator<K> {

        private Iterator<Entry<K,V>> entries = entrySet().iterator();

        @Override
        public boolean hasNext() {
            return entries.hasNext();
        }

        @Override
        public K next() {
            return entries.next().getKey();
        }
    }

    private class KeyIterable implements Iterable<K> {
        @Override
        public Iterator<K> iterator() {
            return new KeyIterator();
        }
    }

    public Iterable<K> keySet(){
        return new KeyIterable();
    }

    /**
     * Makes values() iterable.
     */
    private class ValueIterator implements Iterator<V> {
        private Iterator<Entry<K,V>> entries = entrySet().iterator();

        @Override
        public boolean hasNext() {
            return entries.hasNext();
        }

        @Override
        public V next() {
            return entries.next().getValue();
        }
    }

    private class ValueIterable implements Iterable<V> {
        public Iterator<V> iterator() {
            return new ValueIterator();
        }
    }

    public Iterable<V> values(){
        return new ValueIterable();
    }

    /**
     * Boolean whether the map is empty.
     * @return true if the map has 0 size. or false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }
}
