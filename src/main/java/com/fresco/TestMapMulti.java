package com.fresco;

import java.util.Optional;

public class TestMapMulti {

	public static void test1() {
		numbers.lines()//
				.map(s -> {
					try {
						Double.parseDouble(s);
						return Optional.of(s);
					} catch (NumberFormatException e) {
						return Optional.<String>empty();
					}
				})//
				.filter(Optional::isPresent).map(Optional::get)//
				.forEach(d -> {
				});
	}

	public static void test2() {
		numbers.lines()//
				.mapMultiToDouble((n, consumer) -> {
					try {
						var d = Double.parseDouble(n);
						consumer.accept(d);
					} catch (NumberFormatException e) {
					}
				})//
				.forEach(d -> {
				});
	}

	public static void test3() {
		numbers.lines()//
				.<String>mapMulti((n, consumer) -> {
					try {
						Double.parseDouble(n);
						consumer.accept(n);
					} catch (NumberFormatException e) {
					}
				})//
				.forEach(d -> {
				});
	}

	public static void main(String[] args) {
		System.out.println();
		test1();
		test2();
		test3();
	}

	public static String numbers = """
			774.6417808970579
			692.9566752905712
			248.99141679126984
			592.0602701678838
			756.1973524743929
			267.3314128098442
			833.4157201371812
			929.886447245547
			897.4328766992372
			900.7548342189964
			279.43528577452264
			853.8779114359556
			127.17093776076177
			704.0715111004196
			258.1703068210039
			665.8545303339611
			842.1186402940015
			847.1234368287504
			470.17866002246575
			462.7219057071056i
			915.7933953345404
			589.5307171645209
			469.8028746058787
			164.62257343371869
			714.9487215115153
			299.42971306521383
			796.1945705527475
			439.2185756334174
			706.7431858060348
			102.22508681936849
			490.98826430940875
			445.6563149775961
			618.508208749903
			705.8062246718636
			694.0209858260605
			xxxxxxxxxxxxxxxxx
			281.20948818271114
			268.69862794300644
			760.5461245099552
			351.01814679625977
			147.55924954984602
			813.2650954159352
			636.0647768457573
			134.98567466621017
			626.225721737874
			537.3410560590694
			258.1993601702335
			227.10295429849458
			214.69851780284222
			244.58293109788045
			828.95944837033
			116.39484276061818
			145.10788567645912
			396.3281360408305
			915.3009559809603
			711.1406025517588
			979.6583031201565
			785.9096059457128
			991.0553379660837
			824.1651107987951
			805.3255742676141
			750.1373590585645
			429.1066440121106
			268.1684954830613
			681.079658383454
			762.054189225408
			889.1194859430349
			194.5078508873564
			685.1340917303997
			994.0921498115001
			723.1720419619799
			749.1771023311173
			394.5854949980254
			601.4007393984432
			639.324327171376
			525.687197629486
			105.29573241348378
			551.3877353831799
			121.45200651803626
			766.13196574157
			165.98192105362426
			117.40756822824389
			902.60253522647
			798.9311784510038
			157.2181476409683
			362.05205181061166
			528.680673892739
			104.57473024353833
			993.4158393187533
			658.6428429825966
			629.4465596571571
			369.1487652532956
			563.8381799247844
			938.2942497366106
			834.6100192201011
			819.506859726301
			357.3169996905833
			304.3096396306521
			134.23083726103093
			233.65587395812707
						""";
}
