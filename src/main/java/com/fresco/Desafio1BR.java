package com.fresco;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summarizingDouble;

import java.util.DoubleSummaryStatistics;
import java.util.Map.Entry;
import java.util.function.BinaryOperator;

public class Desafio1BR {

    public record Medicion(String estacion, double temperatura) {
    }

    public static Medicion strToMed(String s) {
        var arr = s.split(";");
        var estacion = arr[0];
        var temperatura = Double.parseDouble(arr[1]);
        return new Medicion(estacion, temperatura);
    }

    public static BinaryOperator<Entry<String, DoubleSummaryStatistics>> compare = (a, b) -> {
        if (a.getValue().getCount() > b.getValue().getCount()) {
            return a;
        } else {
            return b;
        }
    };

    public static void main(String[] args) {
        var res = mediciones.lines()
                .map(Desafio1BR::strToMed)
                .collect(groupingBy(Medicion::estacion, summarizingDouble(Medicion::temperatura)));

        var max = res.entrySet()
                .stream()
                .reduce(compare);

        res.entrySet()
                .forEach(e -> {
                    System.out.println(e.getKey() + " " + e.getValue());
                });
        System.out.println(max);

    }

    private static String mediciones = """
            Anadyr;-0.7
            Ulaanbaatar;-19.3
            Entebbe;36.1
            Nouakchott;24.1
            Yaoundé;23.9
            Tbilisi;11.4
            Fairbanks;-2.2
            Lhasa;5.6
            Tirana;28.2
            Tampa;30.8
            Da Lat;25.0
            Virginia Beach;19.3
            Miami;24.6
            Anadyr;3.1
            San Diego;29.6
            Hanga Roa;33.3
            Karachi;28.1
            Palermo;19.2
            Belize City;27.9
            Dunedin;30.9
            Seattle;11.1
            Vladivostok;6.7
            Praia;22.9
            Makurdi;32.0
            Kansas City;11.4
            Baguio;21.6
            Detroit;14.1
            Hiroshima;25.3
            Bishkek;21.6
            Pointe-Noire;30.7
            Tijuana;4.8
            Karonga;18.8
            Dallas;20.1
            Tehran;26.7
            Skopje;20.3
            Douala;30.4
            Philadelphia;19.0
            Minsk;-3.8
            Kano;18.8
            Salt Lake City;23.2
            Montreal;3.7
            Harbin;-4.8
            Assab;23.2
            Bamako;15.6
            Charlotte;10.9
            Christchurch;37.1
            Andorra la Vella;9.2
            Harbin;8.5
            Palermo;24.6
            Guatemala City;22.3
            Albuquerque;22.1
            Vladivostok;33.4
            Flores,  Petén;29.1
            Ndola;20.9
            San Juan;22.8
            İzmir;4.5
            Palmerston North;1.6
            Baguio;30.2
            Mek'ele;41.9
            Kyiv;5.9
            Winnipeg;19.4
            Upington;23.7
            Valencia;15.7
            Petropavlovsk-Kamchatsky;4.3
            Wichita;15.0
            Valletta;6.5
            Perth;17.2
            Prague;-5.4
            Kunming;18.6
            Kingston;33.9
            Bujumbura;5.3
            Bridgetown;17.5
            Gaborone;28.0
            Aden;45.1
            Karonga;45.3
            Mango;42.8
            Seoul;17.6
            Hamburg;18.0
            Yellowknife;-8.2
            Skopje;-1.7
            Garoua;20.2
            Honolulu;30.0
            Washington, D.C.;5.3
            Paris;17.4
            Sana'a;18.3
            Valletta;14.0
            Indianapolis;18.5
            Valencia;14.7
            Baghdad;14.8
            Warsaw;5.0
            Ouagadougou;24.3
            Harare;2.0
            Antananarivo;42.6
            Kampala;11.6
            Moscow;7.3
            Aden;28.1
            Yaoundé;31.6
            Singapore;22.0
            Fresno;10.7
            Saint-Pierre;-4.9
            George Town;9.7
            Halifax;31.7
            Vladivostok;5.3
            Albuquerque;8.4
            Chihuahua;18.8
            Reggane;48.5
            Almaty;3.8
            Yangon;13.1
            Hobart;11.2
            İzmir;16.1
            Bujumbura;25.8
            Maputo;20.2
            Petropavlovsk-Kamchatsky;-7.1
            Vancouver;14.5
            Baghdad;20.6
            Durban;30.8
            Tripoli;32.8
            Philadelphia;16.7
            Chihuahua;37.3
            Odienné;34.1
            Oulu;23.8
            Valletta;23.1
            Brisbane;23.3
            Bridgetown;27.5
            Charlotte;30.7
            Minsk;14.6
            Las Palmas de Gran Canaria;16.8
            Minsk;-7.5
            Bordeaux;0.1
            Zanzibar City;30.9
            La Ceiba;23.6
            Lodwar;19.3
            Vilnius;-4.6
            Iqaluit;11.6
            Niamey;25.4
            Singapore;33.9
            Irkutsk;26.6
            Madrid;5.8
            Da Nang;30.7
            Novosibirsk;-1.8
            Jerusalem;19.4
            Anadyr;-8.9
            Nakhon Ratchasima;40.5
            Kabul;10.5
            Kuwait City;32.2
            San Francisco;18.3
            Kabul;10.6
            Durban;9.0
            Bujumbura;28.4
            Cotonou;29.2
            Halifax;1.2
            Accra;33.8
            Vaduz;3.3
            Boise;6.8
            Douala;28.0
            Almaty;20.5
            San Salvador;45.2
            George Town;38.9
            Vilnius;-2.1
            Saint Petersburg;-4.8
            Marseille;23.2
            Bangkok;47.4
            Tallinn;25.8
            Kano;33.9
            Tabora;28.1
            Porto;9.3
            Memphis;16.0
            Denver;3.1
            Douala;25.6
            Irkutsk;-2.3
            Dodoma;15.6
            Tabora;29.9
            Ulaanbaatar;3.7
            Gagnoa;26.0
            Bilbao;23.9
            Jos;5.6
            Virginia Beach;20.7
            Tbilisi;5.1
            Portland (OR);21.1
            Guatemala City;8.5
            Anchorage;-15.5
            Ahvaz;34.5
            El Paso;23.9
            Cabo San Lucas;35.6
            Rostov-on-Don;8.0
            Monaco;9.1
            Lomé;35.1
            Chicago;8.7
            Timbuktu;38.8
            Bloemfontein;7.9
            Lhasa;-1.0
            Abéché;44.4
            Denpasar;31.7
            Ouarzazate;26.2
            Detroit;2.2
            Abha;1.0
            Ahvaz;23.7
            Portland (OR);14.2
            Livingstone;12.3
            La Paz;13.4
            Santo Domingo;25.6
            Karonga;12.6
            Nassau;21.1
            Gjoa Haven;-4.1
            Changsha;23.9
            Odienné;16.8
            Dublin;-10.5
            Podgorica;7.6
            Stockholm;1.2
            Manila;47.9
            Virginia Beach;14.9
            Rabat;30.7
            Kabul;4.3
            Winnipeg;7.1
            Heraklion;17.5
            Brussels;7.3
            Timbuktu;27.0
            Pyongyang;21.9
            Gagnoa;30.9
            Mandalay;33.0
            Austin;19.5
            Suva;12.8
            Upington;21.6
            Perth;9.1
            Hiroshima;24.6
            Vladivostok;-5.9
            Bucharest;4.2
            Fukuoka;37.7
            Melbourne;7.4
            Mexico City;23.9
            Upington;15.0
            Luanda;17.3
            Melbourne;11.2
            Medan;45.6
            Toamasina;33.6
            Palembang;38.1
            Tegucigalpa;26.0
            Tauranga;20.0
            Ouagadougou;36.8
            Antsiranana;18.9
            Mango;44.1
            Ngaoundéré;20.6
            Melbourne;42.4
            Thiès;15.4
            San José;36.9
            Dubai;13.9
            Santo Domingo;40.0
            Lake Tekapo;21.2
            Irkutsk;4.8
            Accra;41.3
            Houston;8.7
            Damascus;10.6
            Addis Ababa;37.0
            Praia;46.0
            Frankfurt;-7.4
            San Jose;-3.5
            Dakar;19.5
            Palmerston North;22.8
            Alexandra;13.5
            Ho Chi Minh City;17.6
            Bouaké;28.8
            Addis Ababa;1.4
            Montreal;14.7
            Nassau;11.3
            Hobart;16.3
            Lake Tekapo;27.8
            Minsk;3.7
            Ouahigouya;31.9
            Nouakchott;29.1
            Milwaukee;12.7
            Canberra;4.7
            Dodoma;19.0
            Gagnoa;21.6
            Canberra;18.2
            Gangtok;22.1
            Veracruz;16.9
            Podgorica;-0.4
            Ürümqi;21.3
            Kunming;7.9
            Oklahoma City;16.1
            Nouakchott;33.9
            Ghanzi;41.6
            Seville;11.0
            Nouakchott;15.6
            Tehran;19.3
            Riyadh;28.6
            Vientiane;30.2
            Warsaw;22.8
            Kinshasa;4.4
            Napoli;24.2
            Oulu;4.3
            Tamanrasset;8.6
            Assab;18.4
            Karachi;36.2
            Istanbul;23.4
            Cabo San Lucas;16.8
            Nassau;18.8
            Rabat;21.2
            San Salvador;7.4
            Bouaké;12.5
            Mexico City;19.6
            Alexandra;18.4
            Rabat;7.5
            Niamey;26.6
            Vladivostok;14.0
            Saint Petersburg;-2.1
            Reggane;33.7
            Jayapura;27.5
            Charlotte;14.6
            Tabriz;-3.4
            Ifrane;13.8
            Tabriz;12.5
            Minneapolis;-14.0
            Napoli;3.1
            Edmonton;2.6
            San José;13.3
            Xi'an;19.7
            Hamburg;8.3
            Lisbon;25.0
            Ségou;28.8
            Helsinki;2.2
            Brussels;6.1
            Ouagadougou;23.5
            Lubumbashi;17.5
            St. John's;8.3
            Abéché;45.7
            City of San Marino;28.9
            Bangkok;38.2
            Dar es Salaam;22.6
            Tijuana;6.1
            London;-3.2
            Bouaké;17.3
            Bosaso;27.2
            Saskatoon;6.2
            Hamburg;-2.0
            Skopje;22.3
            Nuuk;-7.8
            Las Vegas;37.8
            Belize City;29.5
            Tauranga;11.2
            Batumi;19.0
            Tampa;16.2
            Manama;31.2
            Saint Petersburg;16.8
            Mumbai;24.8
            Hobart;19.3
            Sydney;21.9
            Houston;9.6
            Erzurum;6.1
            Toamasina;40.0
            Ottawa;-14.5
            Flores,  Petén;13.6
            Erbil;9.0
            Novosibirsk;-8.1
            Virginia Beach;8.8
            Heraklion;22.1
            Gabès;25.0
            Suva;34.8
            Omaha;12.6
            Vienna;-12.6
            Bloemfontein;21.3
            Sochi;-0.3
            Madrid;8.6
            Sapporo;23.1
            Roseau;25.7
            Karonga;30.0
            Santo Domingo;34.1
            Bulawayo;18.2
            Copenhagen;-2.9
            Bordeaux;9.4
            San Francisco;16.7
            Ho Chi Minh City;21.5
            Antananarivo;24.1
            Taipei;4.7
            San Francisco;17.5
            Tehran;22.8
            Austin;12.7
            Tokyo;17.9
            Hobart;10.5
            Khartoum;18.5
            Port-Gentil;27.7
            Port Moresby;35.5
            Astana;6.2
            Marseille;33.6
            Yakutsk;4.1
            Lyon;38.5
            San Salvador;27.5
            Dushanbe;0.7
            Changsha;33.4
            Seattle;15.2
            Luxembourg City;-4.9
            Kolkata;19.1
            Ngaoundéré;15.4
            Addis Ababa;18.5
            Jakarta;14.2
            Kyoto;21.6
            Vladivostok;-0.7
            Medan;25.8
            Berlin;1.4
            Lviv;10.9
            Fresno;35.5
            Livingstone;23.2
            Blantyre;10.5
            Tabriz;5.3
            St. Louis;11.2
            Kumasi;20.5
            Dampier;17.9
            Sacramento;1.7
            Ankara;-2.0
            Gaborone;13.4
            Yaoundé;46.8
            San Antonio;16.9
            Karonga;33.9
            San Jose;11.9
            Djibouti;6.5
            Dhaka;31.7
            Ifrane;-4.4
            Juba;18.1
            Moscow;2.1
            Bratislava;14.2
            Mzuzu;4.8
            Ahvaz;29.5
            Berlin;1.8
            Manama;23.4
            Madrid;1.6
            Cairo;28.7
            Kinshasa;-9.7
            Luanda;23.3
            Petropavlovsk-Kamchatsky;-2.5
            Hargeisa;24.1
            Ifrane;15.4
            Sofia;9.0
            Asmara;32.4
            Toliara;25.4
            Honiara;31.0
            Naha;9.9
            Pretoria;21.8
            Atlanta;10.2
            Libreville;41.3
            Bergen;12.3
            La Ceiba;43.4
            City of San Marino;26.0
            Birao;27.0
            Monaco;13.0
            Wrocław;3.3
            Hargeisa;22.0
            Ankara;8.6
            Istanbul;15.8
            El Paso;25.5
            Harbin;8.7
            London;13.7
            Portland (OR);7.9
            Gabès;27.9
            Tucson;29.8
            Marrakesh;26.7
            Valencia;12.8
            Hargeisa;29.2
            Sarajevo;27.4
            Tamanrasset;23.7
            Gagnoa;19.8
            Chișinău;17.3
            Kuopio;-30.2
            Sydney;-3.6
            La Ceiba;14.3
            Suva;31.7
            Amsterdam;10.0
            Baguio;33.4
            Brisbane;10.7
            Winnipeg;-8.0
            Hobart;-4.1
            Stockholm;0.6
            Cracow;9.5
            San Francisco;19.1
            London;2.2
            New Delhi;28.6
            Ouagadougou;8.9
            Napier;5.6
            Bata;16.1
            Bangkok;17.3
            Zagreb;19.0
            Beirut;35.5
            Pyongyang;24.9
            Rabat;29.7
            Burnie;6.7
            Bata;32.0
            Baltimore;10.4
            Singapore;42.1
            Naha;35.2
            Baghdad;48.5
            Mombasa;28.7
            London;20.3
            Memphis;-1.4
            Napier;-13.2
            Saint Petersburg;8.9
            Guatemala City;1.8
            Monterrey;15.8
            Bouaké;26.7
            Rostov-on-Don;-1.1
            Ouahigouya;47.5
            Istanbul;20.3
            Bergen;-8.6
            Bouaké;24.4
            Douala;21.1
            Palm Springs;14.4
            Anchorage;-9.8
            Accra;28.5
            Banjul;21.9
            Whitehorse;17.9
            Nairobi;10.0
            Bishkek;4.2
            Tirana;-4.5
            Accra;19.6
            Frankfurt;16.4
            San Diego;13.9
            Boise;4.9
            Lake Tekapo;10.9
            Kano;37.0
            Khartoum;26.8
            Iqaluit;-19.0
            Erzurum;12.8
            Pyongyang;13.3
            Odienné;11.0
            Kampala;19.2
            Cotonou;20.8
            Lake Tekapo;23.1
            Benghazi;39.6
            San Francisco;5.1
            Melbourne;17.7
            Oslo;8.0
            Rostov-on-Don;29.9
            Lyon;-5.7
            Kansas City;16.4
            Baguio;18.4
            Vilnius;7.7
            Antsiranana;20.4
            Bloemfontein;21.1
            Oranjestad;51.5
            Las Vegas;-1.7
            Tashkent;13.1
            Kuwait City;46.5
            Rostov-on-Don;15.0
            Baghdad;21.0
            Aden;24.3
            Ndola;25.4
            Manama;20.0
            Surabaya;37.8
            Warsaw;19.6
            Guadalajara;21.2
            Valencia;26.5
            Marseille;19.9
            Hamburg;15.7
            Maputo;39.5
            La Ceiba;27.9
            Louisville;22.4
            Oranjestad;25.0
            Beirut;27.6
            Erbil;38.4
            Dushanbe;20.5
            Fairbanks;-14.0
            Riyadh;15.5
            Ifrane;39.1
            Khartoum;35.1
            Port Vila;32.7
            Sana'a;30.7
            Benghazi;34.7
            Minsk;-2.2
            Muscat;29.8
            Bangkok;25.8
            Accra;23.2
            La Ceiba;33.1
            Mumbai;9.4
            Brazzaville;35.2
            Beijing;19.7
            Ljubljana;7.9
            Kingston;13.6
            Reykjavík;13.7
            Niamey;26.7
            Mumbai;45.2
            Portland (OR);11.1
            Nicosia;22.4
            Philadelphia;11.6
            Damascus;27.9
            Boston;8.7
            Yangon;34.4
            Abéché;39.4
            Ouarzazate;19.8
            Petropavlovsk-Kamchatsky;3.9
            Luanda;42.5
            Rostov-on-Don;17.8
            Anadyr;-8.3
            Tauranga;12.9
            Minneapolis;9.4
            Bissau;36.8
            Cape Town;11.4
            Hat Yai;24.3
            Anadyr;-5.0
            Naha;35.0
            Garissa;32.2
            Philadelphia;13.5
            Niamey;28.1
            Abidjan;15.6
            Halifax;11.0
            Split;15.7
            Reykjavík;1.9
            San Antonio;15.3
            Burnie;6.3
            Podgorica;13.1
            Los Angeles;24.6
            Durban;4.5
            Rostov-on-Don;4.4
            Darwin;23.8
            Santo Domingo;33.7
            Marrakesh;22.4
            Louisville;8.9
            Riga;-2.2
            Monaco;28.0
            Chișinău;26.5
            Niamey;18.8
            Murmansk;-15.2
            Chicago;2.4
            Toronto;8.8
            Fairbanks;4.7
            Gaborone;28.3
            Sacramento;8.7
            Riga;8.8
            Amsterdam;16.8
            Ottawa;16.0
            Durban;25.6
            Cabo San Lucas;22.4
            Darwin;21.6
            Addis Ababa;10.0
            Kansas City;24.7
            Tallinn;3.4
            Launceston;5.3
            New Orleans;12.7
            Accra;34.3
            Sapporo;12.4
            Thiès;39.7
            Cabo San Lucas;6.0
            Tamale;28.2
            Bamako;29.3
            Cotonou;24.0
            Yangon;51.4
            Managua;55.7
            Jacksonville;13.2
            Bulawayo;15.0
            Ljubljana;3.7
            Dushanbe;9.5
            Amsterdam;7.2
            Dampier;8.0
            Porto;0.5
            Port-Gentil;30.0
            Valencia;17.7
            Portland (OR);18.1
            La Paz;10.5
            Gagnoa;32.1
            San Francisco;-1.7
            Bata;42.4
            Dushanbe;-13.4
            Las Vegas;12.3
            Maputo;39.0
            Riyadh;30.0
            Port Moresby;23.0
            Lviv;12.5
            Lagos;29.4
            Zürich;10.9
            Birao;23.6
            Brussels;16.3
            Ouagadougou;49.6
            Singapore;44.1
            Beijing;24.4
            Sydney;17.0
            Denver;17.1
            San Jose;19.0
            Yakutsk;-13.7
            Valletta;11.0
            Mek'ele;30.0
            Amsterdam;-5.0
            Tromsø;2.2
            Ahvaz;14.2
            Athens;18.8
            Palmerston North;29.2
            Chișinău;21.3
            Lake Havasu City;27.4
            Stockholm;15.1
            Antananarivo;27.1
            Cracow;25.0
            Zürich;21.2
            Barcelona;29.1
            Surabaya;22.5
            Zagreb;15.9
            Heraklion;11.7
            New Delhi;33.2
            Batumi;21.1
            Vladivostok;-8.5
            Changsha;11.4
            Omaha;13.4
            Nairobi;21.6
            Washington, D.C.;6.4
            Shanghai;12.9
            Minsk;10.6
            Suwałki;11.5
            Jerusalem;16.2
            Luxembourg City;6.6
            Ghanzi;29.5
            Istanbul;15.5
            Niigata;15.1
            Rostov-on-Don;18.5
            Valencia;13.3
            Harbin;-10.5
            Abha;14.2
            Lviv;9.8
            Tijuana;31.6
            Erbil;34.6
            Yaoundé;10.5
            Istanbul;18.1
            Bordeaux;8.9
            Maun;21.6
            Chicago;-12.5
            Palermo;37.7
            Louisville;11.0
            Tashkent;8.7
            Kabul;13.2
            Helsinki;2.1
            San Diego;21.5
            Hargeisa;18.8
            Istanbul;21.5
            Toliara;17.3
            Algiers;39.6
            Abéché;29.4
            Nairobi;25.7
            Damascus;8.2
            Phnom Penh;31.5
            Mzuzu;3.0
            Santo Domingo;13.9
            Stockholm;0.5
            Kumasi;42.3
            Athens;27.5
            Libreville;17.8
            İzmir;21.2
            Palembang;24.6
            Bratislava;32.2
            Karachi;23.0
            Bata;52.9
            Mek'ele;15.5
            Baltimore;7.6
            La Paz;43.5
            San Jose;9.4
            Winnipeg;21.2
            Malé;17.5
            Hanga Roa;36.7
            Louisville;10.9
            Ulaanbaatar;-10.1
            Boston;-4.7
            Gjoa Haven;-12.8
            Kyoto;20.2
            Hargeisa;25.2
            Palermo;27.1
            Tbilisi;11.3
            Saint-Pierre;5.9
            Wau;33.2
            Podgorica;20.5
            Detroit;-5.3
            Astana;6.6
            Bridgetown;18.4
            Mango;22.4
            Napoli;0.6
            Abéché;27.3
            Cabo San Lucas;29.6
            Makurdi;16.5
            Cape Town;31.2
            San Diego;25.1
            Chittagong;30.6
            Jacksonville;5.2
            Bergen;14.3
            La Ceiba;34.1
            Whitehorse;-13.8
            Jerusalem;14.9
            Lisbon;24.6
            Garoua;24.9
            Fresno;17.7
            Athens;16.0
            Dhaka;37.4
            San Francisco;16.6
            Cape Town;10.4
            Antananarivo;15.4
            Tamale;30.6
            Managua;29.4
            Flores,  Petén;28.4
            Saint Petersburg;12.3
            Havana;8.9
            Las Vegas;27.3
            Budapest;24.5
            Tehran;23.1
            Makassar;25.5
            Kumasi;17.1
            Dubai;36.1
            Douala;20.8
            Cairo;27.0
            San José;31.6
            Reggane;17.7
            Hamilton;19.0
            Warsaw;13.1
            Birao;20.7
            Nuuk;-9.5
            Malé;20.9
            Belize City;25.2
            Palmerston North;8.3
            Kathmandu;21.8
            Odesa;21.6
            Odienné;20.6
            Barcelona;21.3
            Yellowknife;-26.0
            Memphis;19.2
            Abidjan;23.9
            Ghanzi;40.1
            Tehran;21.4
            Almaty;14.1
            Panama City;27.2
            Montreal;-6.4
            Manama;32.6
            Lisbon;20.4
            Sydney;34.9
            Tripoli;22.7
            Vilnius;6.7
            Budapest;8.3
            Las Vegas;-2.2
            London;8.5
            Wellington;16.3
            Napoli;26.9
            Hiroshima;5.6
            Virginia Beach;11.8
            La Ceiba;11.0
            San Juan;27.0
            Los Angeles;22.3
            Xi'an;22.9
            Tehran;14.2
            Cairo;21.7
            Chittagong;36.8
            Yinchuan;9.3
            Palm Springs;28.7
            Saskatoon;-9.0
            Dublin;6.6
            Bucharest;25.5
            Alexandra;4.3
            Gangtok;35.3
            Mogadishu;16.2
            Ashgabat;2.8
            Malé;48.3
            Sana'a;17.3
            Ashgabat;3.7
            Ndola;30.7
            Washington, D.C.;15.9
            Denver;13.0
            Bergen;13.8
            Calgary;18.8
            Yakutsk;-12.6
            Honiara;14.9
            Seattle;19.1
            Kingston;33.3
            Seville;15.2
            Sarajevo;0.1
            Honolulu;24.1
            Juba;27.6
            Odienné;20.0
            Thessaloniki;37.5
            Yakutsk;-17.9
            Milwaukee;21.5
            Dili;4.9
            Split;7.9
            Hamilton;-1.1
            Kyoto;13.6
            Villahermosa;40.7
            Virginia Beach;23.3
            San Francisco;29.9
            San Diego;17.7
            Kuopio;-9.6
            Hong Kong;7.5
            Thessaloniki;19.6
            Changsha;6.3
            Chișinău;15.1
            Cotonou;39.4
            Djibouti;43.5
            Durban;24.7
            Oklahoma City;16.3
            Stockholm;5.0
            Kano;28.5
            Santo Domingo;18.7
            Gabès;8.0
            Algiers;8.9
            Minneapolis;2.7
            Nairobi;26.3
            Bujumbura;34.0
            Djibouti;37.6
            Kampala;33.9
            Livingstone;24.8
            Cairo;32.9
            San Salvador;27.0
            Oranjestad;21.0
            Shanghai;36.1
            Antsiranana;30.5
            Burnie;13.9
            Yaoundé;23.1
            Juba;25.0
            Flores,  Petén;3.3
            Bangui;23.3
            Boise;17.1
            Pointe-Noire;22.1
            San Antonio;37.8
            Yerevan;1.8
            Indianapolis;12.1
            Da Lat;23.4
            Los Angeles;22.5
            Wellington;7.8
            Ashgabat;19.6
            Harare;16.7
            Launceston;1.5
            El Paso;20.7
            Sacramento;11.4
            Pyongyang;7.8
            Monaco;23.7
            Bangkok;27.0
            Austin;32.1
            Jacksonville;41.9
            Johannesburg;5.5
            Reykjavík;0.0
            Monaco;30.8
            Minneapolis;18.1
            Charlotte;27.8
            Helsinki;0.9
            Kathmandu;39.5
            Rome;10.0
            Dili;23.7
            Tunis;20.3
            Minneapolis;23.9
            Timbuktu;41.2
            Nicosia;16.9
            Brazzaville;39.4
            Palermo;5.4
            Berlin;9.8
            Chittagong;28.4
            Cabo San Lucas;29.3
            Kuopio;2.1
            Jayapura;4.9
            Sapporo;-1.4
            Garissa;28.5
            Rangpur;3.5
            Mombasa;22.1
            Ghanzi;7.8
            Zagreb;10.1
            Banjul;7.9
            Odesa;2.6
            Toliara;28.8
            London;5.2
            Luanda;26.3
            Tbilisi;27.9
            Mogadishu;34.5
            Anadyr;-19.6
            San Jose;10.1
            Dubai;24.7
            Da Lat;5.1
            Mexicali;11.1
            Riga;24.9
            Dallas;13.2
            Ottawa;16.8
            Pretoria;21.2
            Washington, D.C.;30.3
            Bloemfontein;27.8
            Sacramento;17.4
            Ulaanbaatar;6.5
            Denver;20.1
            Brussels;9.0
            Belgrade;8.9
            Upington;23.4
            Mexico City;32.9
            Luxembourg City;27.7
            Dampier;2.1
            Dakar;30.7
            Pyongyang;18.0
            Kumasi;21.2
            Andorra la Vella;14.6
            Bangui;45.8
            Seoul;21.6
            Ndola;29.8
            Moscow;27.4
            Vaduz;1.4
            Novosibirsk;-15.0
            Maputo;15.2
            Sarajevo;14.8
            Bouaké;24.2
            Oslo;9.6
            Ankara;16.9
            Pontianak;31.6
            Bishkek;1.4
            Las Vegas;20.7
            Pittsburgh;21.6
            Khartoum;30.9
            Antananarivo;34.9
            Nouadhibou;34.3
            Honolulu;43.9
                """;
}
