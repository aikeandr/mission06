package edu.isu.cs.cs3308.structures.impl;

import edu.isu.cs.cs3308.structures.Entry;
import java.util.ArrayList;
import java.util.Random;

/**
 * @param <K>
 * @param <V>
 * @author Andrew Aikens
 */
public abstract class AbstractHashMap<K,V> extends AbstractMap<K,V> {
    protected int n = 0;                  //number of entries in the map
    protected int capacity;               //length of the table
    private int prime;                    //prime factor
    private long scale, shift;            // the shift and scaling factors

    /**
     * Constructor for the AbstractHashMap
     * @param cap capacity for the table
     * @param p prime factor
     */
    public AbstractHashMap(int cap, int p){
        prime = p;
        capacity = cap;
        Random rand = new Random();
        scale = rand.nextInt(prime-1) + 1;
        shift = rand.nextInt(prime);
        createTable();
    }

    /**
     * Constructor with a default prime.
     * @param cap capacity for the table
     */
    public AbstractHashMap(int cap){
        this(cap, 109345121);
    }

    /**
     * Constructor with a default capacity.
     */
    public AbstractHashMap(){
        this(17);
    }

    /**
     * Size of the map.
     * @return number of entities in the map
     */
    public int size() {
        return n;
    }

    /**
     * Finds the value associated with the given key or returns null.
     * @param key
     * @return value or null
     */
    public V get(K key){
        return bucketGet(hashValue(key),key);
    }

    /**
     * Removes the given key and returns the value
     * @param key
     * @return value associated with the key
     */
    public V remove(K key) {
        return bucketRemove(hashValue(key), key);
    }


    /**
     * If there is not an entry with the <K,V> pair already, <K,V> is added to the map
     * and null is returned. Else replaces the old V with the new V and returns the old V.
     * @param key K
     * @param value V
     * @return null or the old value
     */
    public V put(K key, V value){
        V answer = bucketPut(hashValue(key),key, value);
        if(n > capacity / 2)
            resize(2*capacity -1);
        return answer;
    }

    /**
     * Creates a hash code using the Mulitply-Add-and Divide method.
     * @param key
     * @return
     */
    public int hashValue(K key){
        return (int) ((Math.abs(key.hashCode()*scale + shift) % prime) % capacity);
    }

    /**
     * Extends the current table by 2*cap - 1
     * @param newCap
     */
    private void resize(int newCap){
        ArrayList<Entry<K,V>> buffer = new ArrayList<>(n);
        for(Entry<K,V> e : entrySet())
            buffer.add(e);
        capacity = newCap;
        createTable();
        n = 0;
        for(Entry<K,V> e : buffer)
            put(e.getKey(),e.getValue());
    }

    /**
     * Creates a fixed array of entries(all start as null)
     */
    protected abstract void createTable();

    /**
     * Returns the value associated with the key at the hash value or null.
     * @param h hashValue
     * @param k key
     * @return value associated with key in the bucket matching the hash value or null
     */
    protected abstract V bucketGet(int h, K k);

    /**
     * Puts the given value at the hashValue bucket associated with key and
     * returns the old value or null if nothing was found.
     * @param h hashValue
     * @param k key
     * @param v value
     * @return old value or null
     */
    protected abstract V bucketPut(int h, K k, V v);

    /**
     * Removes the key at hashValue and returns the value.
     * @param h hashValue
     * @param k key
     * @return value
     */
    protected abstract V bucketRemove(int h, K k);
}
