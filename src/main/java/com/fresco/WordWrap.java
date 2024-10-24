package com.fresco;

import java.io.IOException;
import java.io.StringReader;

public class WordWrap {
	public static char enterEmoji = 8629;

	public static String text = """
			Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent non enim vel neque fringilla lobortis. Phasellus feugiat purus sapien, at mattis lectus tincidunt id. Ut id tempor arcu. In sit amet risus ut est blandit lobortis. Pellentesque consectetur commodo diam, vel ultrices tellus ullamcorper sed. Praesent sem justo, finibus eu volutpat eu, suscipit at erat. Proin fringilla lacus id nisl venenatis, ut congue quam ultricies. Donec consectetur facilisis odio, id placerat lectus fermentum sed.
			Praesent sem ipsum, pulvinar et tellus quis, congue placerat massa. Mauris varius velit metus, a pulvinar sapien sollicitudin ut. Maecenas gravida nec augue in porttitor. Curabitur nec libero nunc. Maecenas auctor rhoncus tempus. Phasellus egestas aliquet justo, at lobortis neque placerat vitae. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque et magna elit. Donec quam eros, porta in dolor et, tincidunt iaculis lectus. Pellentesque eleifend faucibus nunc eget luctus. Phasellus rhoncus, nulla vel congue volutpat, lorem leo egestas lorem, nec dapibus metus ante vel dolor. Nam et dolor turpis. Fusce ornare quam nec lectus posuere, nec dapibus augue sodales. In non ante mauris.
			""";

	public static void main(String[] args) throws IOException {
		var buffer = new char[100];
		var reader = new StringReader(text);
		for (var i = reader.read(buffer); i != -1; i = reader.read(buffer)) {
			var cad = new String(buffer, 0, i);
			System.out.println(cad.replace('\n', enterEmoji));
		}
	}
}

