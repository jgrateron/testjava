package com.fresco;

public class EstadisticasMayusculasMinusculas {

	public static void main(String[] args) {
		var ini = System.currentTimeMillis();
		var result = texto.chars()//
				.collect(ResumenCaracteres::new, ResumenCaracteres::accumulator, ResumenCaracteres::combine);
		System.out.println(result.toString());
		var end = System.currentTimeMillis();
		System.out.println("time " + (end - ini) + "ms");
	}

	public static class ResumenCaracteres {
		private int count;
		private int countUpper;
		private int countLower;

		public ResumenCaracteres() {
			count = 0;
			countUpper = 0;
			countLower = 0;
		}

		public void accumulator(int c) {
			count++;
			countUpper = Character.isUpperCase(c) ? countUpper + 1 : countUpper;
			countLower = Character.isLowerCase(c) ? countLower + 1 : countLower;
		}

		public void combine(ResumenCaracteres other) {
			count += other.count;
			countUpper += other.countUpper;
			countLower += other.countLower;
		}

		@Override
		public String toString() {
			return "ResumenCaracteres [count=" + count + ", countUpper=" + countUpper + ", countLower=" + countLower
					+ "]";
		}
	}

	public static String texto = """
			Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam eu est a purus scelerisque varius sit amet non sapien. Interdum et malesuada fames ac ante ipsum primis in faucibus. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Donec ultricies fermentum posuere. Duis viverra accumsan auctor. Praesent tempor, neque sed ultrices sagittis, est arcu efficitur turpis, et commodo eros neque a nibh. Nunc dapibus feugiat iaculis. Cras cursus luctus nisi, id ullamcorper ex. Etiam mi ipsum, tincidunt vel libero in, blandit vehicula nisi. Aliquam erat volutpat. Integer lectus odio, fringilla vitae tincidunt non, tincidunt vel urna. Aliquam pulvinar urna vel quam pretium placerat vel eu mauris. Morbi magna odio, pretium eu auctor eget, lacinia in enim. Etiam vestibulum lorem vel nisl commodo iaculis.
			Aenean nulla eros, mattis vel auctor vitae, efficitur eget sapien. Proin non varius nibh, a bibendum est. Vestibulum sed egestas mauris, molestie porta orci. Nunc efficitur ipsum augue, vel pharetra nibh sollicitudin ut. Sed tempus odio ac gravida laoreet. Phasellus aliquam, purus in luctus maximus, mi urna faucibus eros, sed iaculis enim ante et purus. Nunc congue tristique maximus. Aliquam vehicula sit amet sapien ac ultrices. Suspendisse leo est, pellentesque eu tortor quis, varius iaculis metus. In egestas mi ut tincidunt suscipit. Pellentesque egestas pulvinar enim, in interdum neque congue nec.
			Nullam elementum eu nunc ac sollicitudin. Fusce erat justo, posuere id lorem sed, lobortis tincidunt sapien. Curabitur condimentum mauris at ligula malesuada, tincidunt laoreet arcu commodo. Nullam rutrum sodales leo, et eleifend nisl ultrices nec. Vivamus pellentesque commodo tellus, ac lacinia nisi porta sed. Pellentesque a nisi non ex congue iaculis. Nulla euismod pharetra lacus, vel consequat nunc tempor eu. Quisque non dui accumsan, condimentum libero nec, faucibus tellus.
			Nulla feugiat aliquam urna, non ornare enim feugiat quis. In et magna in libero iaculis euismod vitae id arcu. Cras accumsan massa purus, id maximus justo faucibus non. Morbi id sapien tellus. Curabitur id nisl ac lacus placerat semper vitae nec lacus. Duis ut diam nec ligula tristique maximus. Pellentesque pulvinar egestas quam eget aliquam. Pellentesque placerat elit vitae libero tincidunt, vitae vulputate ex dictum. Quisque eget bibendum metus, sit amet accumsan nulla.
			Suspendisse nulla risus, rhoncus quis lacinia non, vulputate in risus. Aenean tincidunt arcu vel ex convallis facilisis. Curabitur finibus in velit sit amet pharetra. In molestie lorem sed velit tincidunt rutrum. Pellentesque quis faucibus felis. In hendrerit in dui eu semper. Morbi quis pulvinar ex. Phasellus ornare libero quis risus scelerisque, et malesuada odio viverra.
			Aliquam mi massa, volutpat eu ullamcorper a, sodales quis sem. Aliquam vitae bibendum nisl. Pellentesque a risus convallis, pretium metus ac, rutrum libero. Nulla sollicitudin non velit id euismod. Sed orci felis, laoreet vel metus eget, tempor elementum orci. Duis mollis at nisl sed luctus. Pellentesque mollis molestie elementum. Proin dictum turpis at erat aliquam, scelerisque aliquam elit scelerisque. Donec augue leo, bibendum eu gravida in, convallis sed metus. Mauris sed nisi et massa accumsan rutrum at quis urna. Curabitur a molestie mi, in ultrices erat. Sed molestie felis quis sapien tincidunt fermentum.
			Aliquam auctor leo massa, non faucibus lacus feugiat eget. In porttitor ac elit vitae tempus. Nunc sit amet tortor vestibulum eros interdum sagittis et eget quam. Maecenas nec lorem orci. Morbi commodo id nunc eu tempor. Vivamus lobortis mattis massa, vel luctus purus placerat in. Mauris sit amet dolor hendrerit, pharetra tortor a, rhoncus magna. Morbi ornare ipsum facilisis metus convallis cursus. Suspendisse consequat vestibulum luctus. Vestibulum sed dignissim nulla, quis facilisis ex. Duis sagittis turpis a orci dignissim venenatis.
			Nullam odio ante, condimentum vitae est in, eleifend hendrerit nibh. Morbi elementum ligula quis elit imperdiet, in dignissim nibh posuere. Integer vitae lectus et ante feugiat maximus id nec magna. Sed elementum, ex vitae accumsan laoreet, lacus libero pellentesque lectus, sed pulvinar nibh nunc eu libero. Quisque tincidunt placerat nisl, ac egestas dui interdum sed. Phasellus eget eros a est sollicitudin feugiat et nec eros. Sed faucibus leo non libero suscipit suscipit. Vivamus aliquet, leo in porta auctor, justo quam porttitor felis, ut dignissim leo diam ac odio.
			Cras porta felis sit amet felis malesuada, accumsan porttitor leo vehicula. Fusce vulputate sit amet nisi eu rutrum. Phasellus nec faucibus libero. Donec feugiat in quam nec tincidunt. Fusce lacinia ut lectus et elementum. Quisque a porttitor mauris, nec pellentesque sem. Donec vitae semper risus, at vehicula turpis. Aenean vulputate, libero sed aliquam gravida, nunc dui interdum ligula, eu finibus purus lectus eget dui. Etiam vulputate sed enim vitae venenatis. Sed libero turpis, pulvinar id interdum sed, pharetra quis neque. Duis consequat facilisis est, vel mollis massa aliquet vel. Suspendisse laoreet tincidunt massa. Morbi consequat arcu sit amet dui ultricies tincidunt. Nunc condimentum vulputate odio, vel condimentum erat porttitor et.
			Maecenas feugiat nibh at ligula ornare, ac pellentesque ipsum interdum. Phasellus commodo, tortor eu posuere vestibulum, lorem purus auctor felis, eu efficitur est orci sed nisi. Morbi tempus magna in sodales molestie. Praesent non sem metus. Donec dictum ipsum eget massa tempor, at pretium est faucibus. Proin a pulvinar lorem, ut placerat lacus. Vivamus tristique neque ac magna consectetur tincidunt. Etiam feugiat varius erat, id ultrices lorem accumsan vitae.
			Nulla ac dui quis ipsum scelerisque maximus. Maecenas eu arcu vitae augue lacinia fringilla sed ut sem. Donec eget magna eu ante auctor cursus. Maecenas justo erat, ornare ut sodales ut, convallis quis mauris. Ut scelerisque quam sed felis efficitur mollis. Cras pharetra ornare ante vel semper. Praesent tincidunt purus placerat lorem vulputate, ut pretium ipsum maximus. Morbi eu vulputate sem, in pharetra quam. Praesent sed massa in purus luctus ultricies. Pellentesque et condimentum sapien. Quisque facilisis eget ex a porta. Aliquam bibendum nulla eros, ut gravida risus dignissim eu.
			Duis aliquam augue bibendum, placerat nunc a, commodo ipsum. Etiam congue fermentum dui at porttitor. Fusce tempor sollicitudin nunc, nec tristique velit consequat eu. Suspendisse consequat tortor eu tortor fringilla suscipit. Nullam hendrerit gravida urna, sit amet pretium dolor mattis vel. Maecenas cursus dictum ultricies. Nulla viverra ullamcorper turpis, quis tincidunt tortor imperdiet ut. Donec lobortis turpis ut aliquam vestibulum. Donec sit amet nibh nulla. In semper risus quis tellus tristique, a ultrices ligula scelerisque. Aenean gravida elit at elit ullamcorper vestibulum. Quisque euismod turpis eu massa vehicula, in bibendum nisi ullamcorper.
			Praesent aliquam quam sit amet est pretium viverra. Duis nec commodo tortor. Vestibulum laoreet interdum fermentum. Praesent vestibulum, nisi non tincidunt lobortis, ligula lorem mollis arcu, ac placerat enim nisl vel nisl. Curabitur lobortis orci sed neque sagittis rhoncus. Quisque massa sapien, accumsan eu volutpat posuere, finibus at est. Nam congue metus eu odio consequat, eget consectetur ex ullamcorper. Phasellus et ullamcorper nibh. Cras non tortor mattis, dictum leo ut, vehicula diam. Nullam egestas nisl a sapien porttitor, a efficitur ipsum dapibus. Nam et est sed quam posuere vestibulum nec vitae sem.
			Cras vestibulum dolor et diam suscipit laoreet sit amet nec lacus. Donec elementum nisl at molestie eleifend. Quisque porttitor augue vitae diam pharetra, nec semper nisi malesuada. Curabitur vehicula nunc a fermentum vulputate. Maecenas sollicitudin, enim ac consectetur lacinia, purus nisl facilisis sapien, in posuere sem nisl vitae elit. Mauris feugiat, metus blandit imperdiet tincidunt, sem felis aliquet massa, egestas semper eros tortor viverra dolor. Praesent tristique mi vitae urna ullamcorper luctus.
			Sed tellus metus, rhoncus egestas ultrices nec, feugiat nec augue. Praesent vitae orci rutrum nulla varius eleifend. Duis metus justo, vestibulum ac porta in, interdum non est. Morbi dolor dolor, finibus et mollis vitae, efficitur eget dolor. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nulla pretium arcu at turpis tristique, pulvinar viverra elit interdum. Fusce mauris sem, tempor non malesuada consectetur, ultricies ac massa. Morbi non malesuada quam, ullamcorper ullamcorper velit. Donec sollicitudin nulla dui, mollis hendrerit nulla euismod vel. Proin in diam sagittis ligula malesuada eleifend. Sed ac nulla ut enim aliquet luctus eget id nisi.
			Sed semper quam et nunc semper, eu porta lacus faucibus. Proin vel mauris vulputate, bibendum neque sed, dictum mauris. Ut sagittis bibendum enim quis egestas. Suspendisse at est urna. Suspendisse pharetra felis ac placerat accumsan. Proin eu tortor a velit ornare bibendum a sed metus. Duis tempor venenatis malesuada. Suspendisse facilisis, orci ut sodales scelerisque, arcu leo scelerisque tortor, a auctor neque nibh pellentesque erat. Morbi a lorem ac orci sodales vestibulum. Donec rhoncus posuere massa. Vivamus scelerisque lectus eu viverra luctus. Suspendisse hendrerit venenatis erat, et sollicitudin risus ultrices eu. Suspendisse suscipit sagittis sodales. Etiam aliquet libero urna, sit amet egestas ipsum rutrum eu. Sed ac odio mauris.
			Pellentesque metus nibh, maximus rhoncus enim eu, lacinia pretium dolor. Praesent tincidunt semper tortor, non porta tellus gravida ut. Etiam convallis fermentum ex sed commodo. Etiam semper ut ex in posuere. Sed et sollicitudin augue. Donec euismod urna tempus diam mattis, non aliquam dui ultrices. Quisque aliquet sem quis felis pretium aliquam. Proin consectetur maximus gravida. Suspendisse potenti.
			Aliquam lectus augue, finibus vitae arcu molestie, varius venenatis ex. Interdum et malesuada fames ac ante ipsum primis in faucibus. Proin efficitur mattis nibh facilisis scelerisque. Sed eu leo volutpat, congue lorem et, aliquam quam. Aliquam rutrum nibh mi, quis lacinia ipsum pellentesque nec. Ut varius volutpat mi tincidunt pretium. Suspendisse fermentum eu augue sed ornare. Morbi in luctus ipsum. Integer porta non velit eget cursus. Morbi commodo viverra velit. Vestibulum semper tempor volutpat.
			Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Sed elementum neque vel leo pulvinar, et hendrerit nulla auctor. In hac habitasse platea dictumst. Proin a sagittis dolor. Phasellus ac massa cursus, tincidunt felis in, euismod tortor. Curabitur euismod suscipit elit, at lobortis justo ornare nec. Fusce fermentum, libero quis iaculis aliquam, ligula massa molestie nisl, a pulvinar odio erat quis ex. Ut rutrum purus enim, a blandit ligula vestibulum sit amet.
			Ut malesuada non leo vitae vestibulum. Aliquam facilisis lectus quis erat ultricies, vel rhoncus elit blandit. In id imperdiet libero. Nulla facilisi. Ut malesuada dolor non sapien consectetur convallis. Nunc a felis ex. Proin sit amet elementum nisi. Nulla facilisi. Ut gravida elit ac lacus accumsan volutpat. Proin sed risus luctus, finibus lorem posuere, venenatis enim. Phasellus nec eros cursus, viverra eros viverra, elementum nulla. Quisque rutrum ullamcorper sapien, in dignissim libero mollis a. Suspendisse potenti. Proin luctus lacus eu ante condimentum, vulputate fermentum turpis vestibulum. Quisque mauris sapien, lobortis vitae leo in, facilisis tincidunt justo. Fusce eu quam tincidunt ipsum pharetra vulputate.
															""";
}
