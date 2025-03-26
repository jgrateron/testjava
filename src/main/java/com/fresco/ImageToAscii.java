package com.fresco;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.stream.IntStream;

import javax.imageio.ImageIO;

public class ImageToAscii {

	private static final List<String> ASCII_CHARS = List.of("@#$%+:;,. ", "ABCDabcd#$%&*@! ", "@%#*+=-:. ", "█▓▒░ ",
			"$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/\\\\|()1{}[]?-_+~<>i!lI;:,\\\"^`'. ",
			"██▓▒░ ", " ░▒▓██", "┼╬╫┤├┴┬│─ ", "♠♣♥♦♤♧♡♢☀★☆☁ ",
			".'`^\\\",:;Il!i><~+_-?][}{1)(|\\\\/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$ ","#+-. ","\\|/ ");

	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Usage: java -jar imageToAscii.jar <image-path>");
			return;
		}

		try {
			var image = ImageIO.read(new File(args[0]));
			var newWidth = 100;
			var newHeight = (image.getHeight()) * 50 / image.getWidth();
			image = resize(image, newWidth, newHeight);
			convertToASCII(image);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static BufferedImage resize(BufferedImage img, int width, int height) {
		var tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		var resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		var g2d = resized.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();
		return resized;
	}

	private static void convertToASCII(BufferedImage image) {
		var allImages = ASCII_CHARS.stream()
				.map(ASCII -> {
					var imgAscii = IntStream.range(0, image.getHeight())
							.mapToObj(y -> {
								var sb = new StringBuilder();
								for (var x = 0; x < image.getWidth(); x++) {
									var color = new Color(image.getRGB(x, y));
									var brightness = (int) (0.299 * color.getRed() + 0.587 * color.getGreen()
											+ 0.114 * color.getBlue());
									var charIndex = (brightness * (ASCII.length() - 1)) / 255;
									sb.append(ASCII.charAt(charIndex));
								}
								return sb.toString();
							})
							.toList();
					return imgAscii;
				})
				.toList();
		for (var i = 0; i < allImages.size(); i += 2) {
			var l1 = allImages.get(i);
			var l2 = allImages.get(i + 1);
			for (int j = 0; j < l1.size(); j++) {
				System.out.println(l1.get(j) + "    " + l2.get(j));
			}
			System.out.println();
		}
	}
}
