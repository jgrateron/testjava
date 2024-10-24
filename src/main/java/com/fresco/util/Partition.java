package com.fresco.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class Partition<T> {
	private List<List<T>> partitions;
	private List<T> currentPartition;
	private int sizePartition;

	public Partition() {
		this.partitions = new ArrayList<>();
		this.sizePartition = 5;
	}

	public Partition(int sizePartition) {
		this.partitions = new ArrayList<>();
		this.sizePartition = sizePartition;
	}

	public Partition<T> add(T i) {
		if (currentPartition == null) {
			currentPartition = new ArrayList<>();
			partitions.add(currentPartition);
		}
		currentPartition.add(i);
		if (currentPartition.size() == sizePartition) {
			currentPartition = null;
		}
		return this;
	}

	public List<List<T>> partitioning(List<T> list) {
		for (var e : list) {
			add(e);
		}
		return Collections.unmodifiableList(partitions);
	}

	public Partition<T> add(Partition<T> o) {
		for (var p : o.partitions) {
			for (var e : p) {
				add(e);
			}
		}
		return this;
	}

	public List<List<T>> getPartitions() {
		return Collections.unmodifiableList(partitions);
	}

	public void forEach(Consumer<List<T>> consumerPartition) {
		partitions.forEach(consumerPartition);
	}

	public static <T> List<List<T>> of(List<T> list, int sizePartition) {
		var partition = new Partition<T>(sizePartition);
		return Collections.unmodifiableList(partition.partitioning(list));
	}
}
