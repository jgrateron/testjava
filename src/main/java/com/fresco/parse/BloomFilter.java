package com.fresco.parse;

import java.util.BitSet;
import java.util.function.Function;

public class BloomFilter<T> {
	private BitSet bitSet;
    private int size;
    private Function<T, Integer>[] hashFunctions;

    @SuppressWarnings("unchecked")
    public BloomFilter(int size, Function<T, Integer>... hashFunctions) {
        this.size = size;
        this.bitSet = new BitSet(size);
        this.hashFunctions = hashFunctions;
    }

    public void add(T element) {
        for (Function<T, Integer> hashFunction : hashFunctions) {
            int hash = Math.abs(hashFunction.apply(element) % size);
            bitSet.set(hash, true);
        }
    }

    public boolean contains(T element) {
        for (Function<T, Integer> hashFunction : hashFunctions) {
            int hash = Math.abs(hashFunction.apply(element) % size);
            if (!bitSet.get(hash)) {
                return false; // Any bit being 0 means the element is definitely not in the set
            }
        }
        return true; // Possibly in the set
    }
}
