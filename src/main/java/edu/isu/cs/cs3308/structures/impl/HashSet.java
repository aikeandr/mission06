package edu.isu.cs.cs3308.structures.impl;

import edu.isu.cs.cs3308.structures.Map;
import edu.isu.cs.cs3308.structures.Set;
import java.util.Iterator;

/**
 * A Set which uses a ProbHashMap as the data structure.
 *
 * @param <T>
 * @author Andrew Aikens
 */
public class HashSet<T> implements Set<T> {
    private Map<T,T> map;

    /**
     * Creates a new HashSet using a ProbeHashMap<T,T>
     */
    public HashSet(){
        map = new ProbeHashMap<>();
    }

    /**
     * Adds the given item to the map.
     * @param e Item to add to the set
     */
    @Override
    public void add(T e) {
        map.put(e, e);
    }

    /**
     * Removes the given item from the map.
     * @param e Item to remove from the set
     */
    @Override
    public void remove(T e) {
        map.remove(e);
    }

    /**
     * Checks if the given item is in the map.
     * @param e item to check membership of
     * @return true if the item is found or false if the item isn't in the map
     */
    @Override
    public boolean contains(T e) {
        return map.get(e) != null;
    }

    /**
     * Makes sure that the values in the map are iterable.
     * @return
     */
    @Override
    public Iterator<T> iterator() {
        return (Iterator<T>) map.values();
    }

    /**
     * Checks if the map is empty.
     * @return true if the map is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    /**
     * Adds all elements of the given set to this map.
     * @param s Set containing the items to be added to this set.
     */
    @Override
    public void addAll(Set<T> s) {
        T element;
        while(s.iterator().hasNext()){
            element = s.iterator().next();
            map.put(element,element);
        }
    }

    /**
     * Removes all values in this map that aren't found in the given set.
     * @param s The set defining which items are to be kept in this set.
     */
    @Override
    public void retainAll(Set<T> s) {
        T value;
        while(map.values().iterator().hasNext()){
            value = map.values().iterator().next();
            if(!s.contains(value))
                map.remove(value);
        }
    }

    /**
     * Removes any value in this map that is found in the given set.
     * @param s Set defining the items to be removed from this set.
     */
    @Override
    public void removeAll(Set<T> s) {
        T value;
        while(s.iterator().hasNext()){
            value = s.iterator().next();
            if(map.get(value) != null)
                map.remove(value);
        }
    }
}
