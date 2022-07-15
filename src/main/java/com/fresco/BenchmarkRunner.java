package com.fresco;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class BenchmarkRunner {

	@Benchmark
	@OutputTimeUnit(TimeUnit.NANOSECONDS)
	@BenchmarkMode(Mode.AverageTime)
	public List<String> returnList() {
	    return new ArrayList<String>();
	}
	
	@Benchmark
	@OutputTimeUnit(TimeUnit.NANOSECONDS)
	@BenchmarkMode(Mode.AverageTime)
	public List<String> returnEmptyList() {
	    return Collections.emptyList();
	}
	
	public static void main(String[] args) throws IOException, RunnerException {
        Options opt = new OptionsBuilder()
                .forks(1)
                .warmupIterations(1)
                .measurementIterations(1)
                .build();

        new Runner(opt).run();
	}
}


