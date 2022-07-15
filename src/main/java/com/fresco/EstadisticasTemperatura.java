package com.fresco;

import java.util.List;
import java.util.function.BinaryOperator;

public class EstadisticasTemperatura {
		
	record Estadistica(int count, int sum, int min, int max){
		
		public double average(){
			return count == 0 
					? 0 
				    : (double) sum / (double) count;
		}
		
		@Override
		public String toString() {
			return "Estadisticas [count=" + count + ", sum=" + sum + ", min=" + min + ", max=" + max
					+ ", average=" + average() + "]";
		}
	}
	/*
	 * 
	 */
	public static void main(String[] args) {

		BinaryOperator<Estadistica> operadorEstadistica = (sub, elem) -> 
		{
			int count = sub.count + 1;
			int sum = sub.sum + elem.sum;
			int min = elem.sum < sub.min ? elem.sum : sub.min;
			int max = elem.sum > sub.max ? elem.sum : sub.max;
			return new Estadistica(count, sum, min, max);
		};

		var temperaturas = List.of(25, 29, 33, 33, 30, 29, 31, 30, 30, 30, 30, 28);

		var estadistica = temperaturas.stream()
				           .map(i -> new Estadistica(1, i, i, i))
				           .reduce(operadorEstadistica);

		if (estadistica.isPresent()) {
			System.out.println(estadistica.get());
			//Estadisticas [count=12, sum=358, min=25, max=33, average=29.833333333333332]
		}
	}
}

