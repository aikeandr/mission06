package edu.isu.cs.cs3308.structures.impl;

import edu.isu.cs.cs3308.structures.Entry;
import java.util.ArrayList;

/**
 * A hash map using linear probing to handle collisions.
 *
 * @param <K>
 * @param <V>
 * @author Andrew Aikens
 */
public class ProbeHashMap<K,V> extends AbstractHashMap<K,V>{
    private MapEntry<K,V>[] table;
    private MapEntry<K,V> DEFUNCT = new MapEntry<>(null, null);

    /**
     * Constructor for a ProbeHashMap
     */
    public ProbeHashMap(){
        super();
    }

    /**
     * Constructor with a specified capacity.
     * @param cap capacity
     */
    public ProbeHashMap(int cap){ super(cap);}

    /**
     * Constructor with a specified capacity and prime factor.
     * @param cap capacity
     * @param p prime factor
     */
    public ProbeHashMap(int cap, int p) {super(cap, p);}

    /**
     * Creates a fixed array of entries(all start as null)
     */
    protected void createTable(){
        table = (MapEntry<K,V>[]) new MapEntry[capacity];
    }
    private boolean isAvailable(int j){
        return (table[j] == null || table[j] == DEFUNCT);
    }

    /**
     * Returns index with key k, or an index where k could be added.
     * @param h hashValue/index
     * @param k key
     * @return the index of k or -(a+1) such that k could be added at index a.
     */
    private int findSlot(int h, K k) {
        int avail = -1;
        int j = h;
        do {
            if (isAvailable(j)) {
                if (avail == -1) avail = j;
                if (table[j] == null) break;
            } else if (table[j].getKey().equals(k))
                return j;
            j = (j + 1) % capacity;
        } while (j != h);
        return -(avail + 1);
    }

    /**
     * Returns the value associated with the key at the hash value or null.
     * @param h hashValue
     * @param k key
     * @return value associated with key in the bucket matching the hash value or null
     */
    protected V bucketGet(int h, K k) {
        int j = findSlot(h, k);
        if(j<0) return null;
        return table[j].getValue();
    }

    /**
     * Puts the given value at the hashValue bucket associated with key and
     * returns the old value or null if nothing was found.
     * @param h hashValue
     * @param k key
     * @param v value
     * @return old value or null
     */
    protected V bucketPut(int h, K k, V v) {
        int j = findSlot(h,k);
        if (j >= 0)
            return table[j].setValue(v);
        table[-(j+1)] = new MapEntry<>(k, v);
        n++;
        return null;
    }

    /**
     * Removes the key at hashValue and returns the value.
     * @param h hashValue
     * @param k key
     * @return value
     */
    protected V bucketRemove(int h, K k) {
        int j = findSlot(h, k);
        if (j < 0) return null;
        V answer = table[j].getValue();
        table[j] = DEFUNCT;
        n--;
        return answer;
    }

    /**
     * Allows for the entrySet to be iterated
     * @return
     */
    public Iterable<Entry<K, V>> entrySet(){
        ArrayList<Entry<K,V>> buffer = new ArrayList<>();
        for(int h=0; h < capacity; h++) {
            if (!isAvailable(h))
                buffer.add(table[h]);
        }
        return buffer;
    }
}
