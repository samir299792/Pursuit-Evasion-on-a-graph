/**
 * Samir Bikram Dhami
 * File: HashMap.java
 * Date: Apr 20, 2024
 * Purpose: This file represents a hash map.
 */

//importing required libraries

import java.util.ArrayList;
/**
 * Implementation of a hash map using separate chaining method.
 * 
 * @param <K> the type of the keys stored in this map
 * @param <V> the type of the values stored in this map
 */
public class HashMap<K, V> implements MapSet<K, V> {
	private int size;
	private Node<K, V>[] nodes;
	private double maxLoadFactor;
	private static final int DEFAULT_INITIAL_CAPACITY = 16;
	private static final double DEFAULT_LOAD_FACTOR = .75f;

	/**
	 * A nested class representing a node in the map, which holds a key-value pair
	 * and a reference to the next node in the chain.
	 * 
	 * @param <K> the type of the keys stored in this node
	 * @param <V> the type of the values stored in this node
	 */
	private static class Node<K, V> extends KeyValuePair<K, V> {
		private Node<K, V> next;

		/**
		 * Constructs a new Node with the specified key and value.
		 * 
		 * @param key   the key for the node
		 * @param value the value for the node
		 */
		public Node(K key, V value) {
			super(key, value);
			this.next = null;
		}
	}

	/**
	 * Constructs a new HashMap with the default initial capacity and load factor.
	 */
	public HashMap() {
		this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
	}

	/**
	 * 
	 * Constructs a new HashMap with the specified initial capacity and default load
	 * factor.
	 * 
	 * @param capacity the initial capacity of the map
	 */
	public HashMap(int capacity) {
		this(capacity, DEFAULT_LOAD_FACTOR);
	}

	/**
	 * 
	 * Constructs a new HashMap with the specified initial capacity and load factor.
	 * 
	 * @param capacity   the initial capacity of the map
	 * @param loadFactor the load factor of the map
	 * @throws IllegalArgumentException if the capacity is negative or not a power
	 *                                  of 2, or if the load factor is less than or
	 *                                  equal to 0
	 */
	@SuppressWarnings("unchecked")
	public HashMap(int capacity, double loadFactor) {
		if (capacity < 0)
			throw new IllegalArgumentException("Illegal inital capacity: " + capacity);
		if (loadFactor <= 0)
			throw new IllegalArgumentException("Illegal load factor: " + loadFactor);
		this.size = 0;
		this.nodes = (Node<K, V>[]) new Node[capacity];
		this.maxLoadFactor = loadFactor;
	}

	/**
	 * 
	 * Returns whether the specified objects are equal or not.
	 * 
	 * @param x the first object to be compared
	 * @param y the second object to be compared
	 * @return true if the objects are equal or both null, false otherwise
	 */
	private static boolean equals(Object x, Object y) {
		return x == y || x != null && x.equals(y);
	}

    /**
	 * 
	 * Returns the current capacity of the map.
	 * 
	 * @return the current capacity of the map
	 */
	protected int capacity() {
		return this.nodes.length;
	}

    /**
	 * Computes the hash code for the specified key.
	 *
	 * @param key the key to compute the hash code for.
	 * @return the hash code for the specified key.
	 */
	private int hash(K key) {
		return Math.abs(key.hashCode() % capacity());
		//return Math.abs(fibonacciHash(key) % capacity());
		//return Math.abs(multiplicativeHash(key) % capacity());
    }

    /**
	 * 
	 * Returns the number of elements in the HashMap.
	 * 
	 * @return the number of elements in the HashMap
	 */
	public int size() {
		return this.size;
	}

	/**
	 * 
	 * Removes all elements from the HashMap.
	 */
	public void clear() {
		for (int i = 0; i < this.capacity(); i++) {
			this.nodes[i] = null;
		}
		this.size = 0;
		this.maxLoadFactor = 0.75;
	}

	
    /**
	 * Associates the specified value with the specified key in this map.
	 *
	 * @param key   the key with which the specified value is to be associated.
	 * @param value the value to be associated with the specified key.
	 * @return the previous value associated with the specified key, or {@code null}
	 *         if there was no mapping for the key.
	 * @throws RuntimeException if the specified key is {@code null}.
	 */
	public V put(K key, V value) {
		if (key == null)
			throw new RuntimeException("Illegal key: " + key);
		int index = this.hash(key);
		Node<K, V>[] buckets = getNodes();
		Node<K, V> entry = buckets[index];
		while (entry != null) {
			if (index == this.hash(entry.getKey()) && equals(key, entry.getKey())) {
				V old = entry.getValue();

				if (value != old)
					entry.setValue(value);

				return old;
			}
			entry = entry.next;
		}
		Node<K, V> newNode = new Node<>(key, value);
		newNode.next = buckets[index];
		buckets[index] = newNode;
		if (++size >= (this.maxLoadFactor * this.capacity()))
			this.resize(buckets.length * 2);
		return null;
	}

	/**
	 * 
	 * Returns the array of nodes in the hash table.
	 * 
	 * @return the array of nodes in the hash table
	 */
	private Node<K, V>[] getNodes() {
		return this.nodes;
	}

	/**
	 * 
	 * Resizes the hash table to the specified new capacity.
	 * 
	 * @param newCapacity the new capacity for the hash table
	 */
	@SuppressWarnings("unchecked")
	private void resize(int newCapacity) {
		Node<K, V>[] oldBuckets = getNodes();
		nodes = (Node<K, V>[]) new Node[newCapacity];
		size = 0;
		for (Node<K, V> bucket : oldBuckets) {
			for (Node<K, V> curNode = bucket; curNode != null; curNode = curNode.next) {
				put(curNode.getKey(), curNode.getValue());
			}
		}
	}

	/**
	 * Returns the value to which the specified key is mapped, or {@code null} if
	 * this map contains no mapping for the key.
	 *
	 * @param key the key whose associated value is to be returned.
	 * @return the value to which the specified key is mapped, or {@code null} if
	 *         this map contains no mapping for the key.
	 */
	public V get(K key) {
		if (key == null)
			return null;
		int index = this.hash(key);
		Node<K, V>[] buckets = getNodes();
		Node<K, V> cur = buckets[index];
		while (cur != null) {
			if (equals(key, cur.getKey()))
				return cur.getValue();

			cur = cur.next;
		}
		return null;
	}

	/**
	 * Returns true if this map contains a mapping for the specified key to a value.
	 * 
	 * @param key the key used for checking.
	 */
	public boolean containsKey(K key){
		int index = this.hash(key);
		Node<K, V>[] buckets = getNodes();
		Node<K, V> cur = buckets[index];
		while (cur != null) {
			if (equals(key, cur.getKey()))
				return true;

			cur = cur.next;
		}
		return false;
	}

	/**
	 * Removes the mapping for the specified key from this map if present.
	 *
	 * @param key the key whose mapping is to be removed from the map.
	 * @return the previous value associated with the specified key, or {@code null}
	 *         if there was no mapping for the key.
	 */
	public V remove(K key) {
		int index = hash(key);
		Node<K, V>[] buckets = getNodes();
		Node<K, V> prev = null;
		Node<K, V> cur = buckets[index];
		while (cur != null) {
			if (equals(key, cur.getKey())) {
				size--;
				if (prev == null) {
					// If the node to be removed is the first node in the linked list
					buckets[index] = cur.next;
				} else {
					// If the node to be removed is somewhere in the middle
					prev.next = cur.next;
				}
				return cur.getValue();
			}
			prev = cur;
			cur = cur.next;
		}
		if (size-- < capacity() * maxLoadFactor / 4)
			resize(capacity() / 2);
		return null;
	}

	/**
	 * 
	 * Returns an ArrayList of all keys in the HashMap.
	 * 
	 * @return an ArrayList containing all the keys in the HashMap
	 */
	public ArrayList<K> keySet() {
		ArrayList<K> keys = new ArrayList<>();
		Node<K, V>[] buckets = getNodes();
		for (int i = 0; i < buckets.length; i++) {
			if (buckets[i] != null)
				keys.add(buckets[i].getKey());
		}
		return keys;
	}

	/**
	 * 
	 * Returns an ArrayList of all values in the HashMap.
	 * 
	 * @return an ArrayList containing all the values in the HashMap
	 */
	public ArrayList<V> values() {
		ArrayList<V> values = new ArrayList<>();
		Node<K, V>[] buckets = getNodes();
		for (int i = 0; i < buckets.length; i++) {
			if (buckets[i] != null)
				values.add(buckets[i].getValue());
		}
		return values;
	}

	/**
	 * 
	 * Returns an ArrayList of all key-value pairs in the HashMap.
	 * 
	 * @return an ArrayList containing all the key-value pairs in the HashMap
	 */
	public ArrayList<KeyValuePair<K, V>> entrySet() {
		ArrayList<KeyValuePair<K, V>> pairs = new ArrayList<>();
		Node<K, V>[] buckets = getNodes();
		for (int i = 0; i < buckets.length; i++) {
			Node<K, V> cur = buckets[i];
			while (cur != null) {
				pairs.add(new KeyValuePair<>(cur.getKey(), cur.getValue()));
				cur = cur.next;
			}
		}
		return pairs;
	}
	

	/**
	 * 
	 * Returns the maximum depth of any bucket in the HashMap.
	 * 
	 * @return the maximum depth of any bucket in the HashMap
	 */
	public int maxDepth() {
		int max = 0;
		Node<K, V>[] buckets = getNodes();
		for (int i = 0; i < buckets.length; i++) {
			if (buckets[i] != null && this.getDepth(buckets[i]) > max) {
				max = this.getDepth(buckets[i]);
			}
		}
		return max;
	}

	/**
	 * 
	 * Returns the depth of the given node.
	 * 
	 * @param node the node whose depth is to be calculated
	 * @return the depth of the given node
	 */
	private int getDepth(Node<K, V> node) {
		int depth = 0;
		Node<K, V> cur = node;
		while (cur != null) {
			depth++;
			cur = cur.next;
		}
		return depth;
	}

	/**
	 * String representation of the Hash Map.
	 */
    public String toString() {
        String output = "" ;
        for ( int i = 0 ; i < this.capacity() ; i ++ ) {
           Node<K,V>  node = this.nodes[ i ] ;
           output += "bin " + i + ": " ;
           while ( node != null ) {
               output += node.toString() + " | " ;
               node = node.next ;
           }
           output += "\n" ;
        }
       return output ;
   }


}

