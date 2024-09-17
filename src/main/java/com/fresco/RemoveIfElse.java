package com.fresco;

public class RemoveIfElse {

	public static double calculateShippingCost1(String shippingType, double weight) {
		if (shippingType.equals("STANDARD")) {
			return weight * 5.0;
		} else if (shippingType.equals("EXPRESS")) {
			return weight * 10.0;
		} else if (shippingType.equals("SAME_DAY")) {
			return weight * 20.0;
		} else if (shippingType.equals("INTERNATIONAL")) {
			return weight * 50.0;
		} else if (shippingType.equals("OVERNIGHT")) {
			return weight * 30.0;
		}
		return 0;
	}

	public static double calculateShippingCost2(String shippingType, double weight) {
		return switch (shippingType) {
		case null -> throw new IllegalArgumentException();
		case "STANDARD" -> weight * 5.0;
		case "EXPRESS" -> weight * 10.0;
		case "SAME_DAY" -> weight * 20.0;
		case "INTERNATIONAL" -> weight * 50.0;
		case "OVERNIGHT" -> weight * 30.0;
		default -> 0;
		};
	}

	public static String getRating1(int val) {
		if (val >= 9 && val <= 10) {
			return "Excellent";
		} else if (val >= 7 && val <= 8) {
			return "Good";
		} else if (val >= 5 && val <= 6) {
			return "Regular";
		} else if (val >= 3 && val <= 4) {
			return "Deficient";
		} else if (val >= 0 && val <= 2) {
			return "Bad";
		} else {
			return "None";
		}
	}

	public static String getRating2(int val) {
		return switch (val) {
		case 9, 10 -> "Excellent";
		case 7, 8 -> "Good";
		case 5, 6 -> "Regular";
		case 3, 4 -> "Deficient";
		case 0, 1, 2 -> "Bad";
		default -> "None";
		};
	}

	public static String getTemperature1(double val) {
		if (val >= 41) {
			return "Hyper";
		} else if (val >= 39.1 && val < 41) {
			return "High";
		} else if (val >= 38 && val < 39) {
			return "Moderate";
		} else if (val >= 37.3 && val < 38) {
			return "Low";
		} else {
			return "None";
		}
	}

	public static String getTemperature2(Double val) {
		return switch(val) {
		case null -> throw new IllegalArgumentException("Unexpected value: " + val);
		case Double v when v >= 41 -> "Hyper";
		case Double v when v >= 39.1 && v < 41 -> "High";
		case Double v when v >= 38 && v < 39 -> "Moderate";
		case Double v when v >= 37.3 && v < 38 -> "Low";
		default -> "None";
		};
	}

	public static void main(String[] args) {
		double weight = 10.0;

		String shippingType1 = "STANDARD";
		double cost1 = calculateShippingCost2(shippingType1, weight);
		System.out.println("Shipping cost for " + shippingType1 + ": " + cost1);

		String shippingType2 = "EXPRESS";
		double cost2 = calculateShippingCost2(shippingType2, weight);
		System.out.println("Shipping cost for " + shippingType2 + ": " + cost2);

		String shippingType3 = "SAME_DAY";
		double cost3 = calculateShippingCost2(shippingType3, weight);
		System.out.println("Shipping cost for " + shippingType3 + ": " + cost3);

		String shippingType4 = "INTERNATIONAL";
		double cost4 = calculateShippingCost2(shippingType4, weight);
		System.out.println("Shipping cost for " + shippingType4 + ": " + cost4);

		String shippingType5 = "OVERNIGHT";
		double cost5 = calculateShippingCost2(shippingType5, weight);
		System.out.println("Shipping cost for " + shippingType5 + ": " + cost5);

		String invalidType = "INVALID";
		double invalidCost = calculateShippingCost2(invalidType, weight);
		System.out.println("Shipping cost for " + invalidType + ": " + invalidCost);

		String nullType = null;
		try {
			calculateShippingCost2(nullType, weight);
		} catch (IllegalArgumentException e) {
			System.out.println("Shipping cost for " + nullType + ": " + e.getClass());
		}
	}

}
