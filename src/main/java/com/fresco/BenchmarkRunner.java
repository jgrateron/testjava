package com.fresco;

import static java.text.MessageFormat.format;

import java.io.IOException;
import java.io.Serializable;
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

	// @Benchmark
	@OutputTimeUnit(TimeUnit.NANOSECONDS)
	@BenchmarkMode(Mode.AverageTime)
	public List<String> returnList() {
		return new ArrayList<String>();
	}

	// @Benchmark
	@OutputTimeUnit(TimeUnit.NANOSECONDS)
	@BenchmarkMode(Mode.AverageTime)
	public List<String> returnEmptyList() {
		return Collections.emptyList();
	}

	//@Benchmark
	@OutputTimeUnit(TimeUnit.NANOSECONDS)
	@BenchmarkMode(Mode.AverageTime)
	public MiExcepcion1 returnMiExcepcion1() {
		return new MiExcepcion1();			
	}

	//@Benchmark
	@OutputTimeUnit(TimeUnit.NANOSECONDS)
	@BenchmarkMode(Mode.AverageTime)
	public MiExcepcion2 returnMiExcepcion2() {
		return new MiExcepcion2();			
	}

	//@Benchmark
	@OutputTimeUnit(TimeUnit.NANOSECONDS)
	@BenchmarkMode(Mode.AverageTime)
	public MiObjeto returnMiObjeto() {
		return new MiObjeto();
	}
	
	@Benchmark
	@OutputTimeUnit(TimeUnit.NANOSECONDS)
	@BenchmarkMode(Mode.AverageTime)
	public String formatString() {
		return format("{0} {1}","Maria", "Jose");
	}

	@Benchmark
	@OutputTimeUnit(TimeUnit.NANOSECONDS)
	@BenchmarkMode(Mode.AverageTime)
	public String formattedString() {
		return "%s %s".formatted("Maria", "Jose");
	}
	
	@Benchmark
	@OutputTimeUnit(TimeUnit.NANOSECONDS)
	@BenchmarkMode(Mode.AverageTime)
	public String plusString() {
		return "Maria" + "Jose";
	}
	
	public static void main(String[] args) throws IOException, RunnerException {
		Options opt = new OptionsBuilder().forks(1).warmupIterations(1).measurementIterations(1).build();

		new Runner(opt).run();
	}

	class MiExcepcion1 extends Exception {

		private static final long serialVersionUID = 1L;

	}

	class MiExcepcion2 extends Exception {

		private static final long serialVersionUID = 1L;

		public Throwable fillInStackTrace() {
			return this;
		}
	}

	class MiObjeto implements Serializable
	{
		private static final long serialVersionUID = 1L;
	}
}
