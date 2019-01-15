package pl.onsight.wypozyczalnia;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.onsight.wypozyczalnia.model.entity.*;
import pl.onsight.wypozyczalnia.repository.NewsRepository;
import pl.onsight.wypozyczalnia.repository.ProductOrderRepository;
import pl.onsight.wypozyczalnia.repository.ProductRepository;
import pl.onsight.wypozyczalnia.service.UserService;

import javax.annotation.PostConstruct;
import java.util.*;

@SpringBootApplication
public class Application {

    @Autowired
    private UserService userService;

    @Autowired
    NewsRepository newsRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductOrderRepository productOrderRepository;

    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "mysql");

        SpringApplication.run(Application.class, args);
    }

    @PostConstruct
    public void initDatabase() {

        List<UserEntity> mockUsers = new ArrayList<>();

        UserEntity u1 = new UserEntity();
        AddressEntity address1 = new AddressEntity();
        address1.setCity("Poznan");
        address1.setZipcode("61-142");
        address1.setFlatNumber("15");
        address1.setStreet("Maltanska");
        u1.setAddress(address1);

        u1.setFirstName("Andrzej");
        u1.setLastName("Kowalski");
        u1.setAdmin(true);
        u1.setEmail("email");
        u1.setPassword("haslo");
        u1.setPhoneNumber("666 746 666");

        Set<UserRoleEntity> roles = new HashSet<>();
        UserRoleEntity role1 = new UserRoleEntity();
        role1.setRole("ROLE_ADMIN");
        roles.add(role1);
        u1.setRoles(roles);

        u1.setAdmin(true);
        mockUsers.add(u1);

        UserEntity u2 = new UserEntity();
        AddressEntity address = new AddressEntity();
        address.setCity("Poznan");
        address.setZipcode("60-100");
        address.setFlatNumber("8");
        address.setStreet("Baraniaka");
        u2.setAddress(address);

        u2.setFirstName("Janina");
        u2.setLastName("Nowak");
        u2.setEmail("janina@gmail.com");
        u2.setPassword("haslo");
        u2.setPhoneNumber("123 456 789");

        Set<UserRoleEntity> roles2 = new HashSet<>();
        UserRoleEntity role2 = new UserRoleEntity();
        role2.setRole("ROLE_USER");
        roles2.add(role2);
        u2.setRoles(roles2);

        u2.setAdmin(false);
        mockUsers.add(u2);
        userService.saveUsers(mockUsers);
        productRepository.save(new ProductEntity("Kask Venus", 5.50, "Kask Venus jest idealnym wyborem dla osób ceniących siłę, wytrzymałość i długotrwałe użytkowanie. Venus zapewni najwyższy możliwy stopień bezpieczeństwa Tobie oraz Twoim podopiecznym przy bezkonkurencyjnej cenie. Łatwo i szybko się go zakłada, jest możliwość wyprania wewnętrznych gąbek, a dzięki specjalnemu systemowi regulacji, pasuje na każdą głowę. Wszystko to sprawia, że Venus jest doskonałym wyborem dla szkół wspinaczkowych i parków linowych.", "https://8a.pl/product_picture/fit_in_900x1224/kask-climbing-technology-venus-plus-white.jpg", "https://8a.pl/product_picture/fit_in_900x1224/kask-climbing-technology-venus-plus-white.jpg", "kask, asekuracja, wspinaczka", 4));

        productRepository.save(new ProductEntity("Climbing Technology Karabinek", 2.50, "Karabinek CT niezastąpiony przy asekuracji i niezbędny przy mocowaniu sprzętów. Wykonany z lekkiego stopu, wytrzymały zarówno przy zamkniętym jak i otwartym zamku. Karabinek K5N marki Grivel zaopatrzony został w zamek typu Key Lock, który nie haczy. Karabinek jest zakręcany. Typ HMS- charakteryzuje się niską wagą, bardzo dużą wytrzymałością oraz zwiększonym prześwitem zamka.", "https://taternik-sklep.pl/media/products/883e23cc429f86ea6cf57425a23583a0/images/thumbnail/large_karabinek-climbing-technology-concept-hms.jpg?lm=1541590418", "https://taternik-sklep.pl/media/products/883e23cc429f86ea6cf57425a23583a0/images/thumbnail/large_karabinek-climbing-technology-concept-hms.jpg?lm=1541590418", "karabinek, asekuracja, wspinaczka", 1));
        productRepository.save(new ProductEntity("GRIVEL Karabinek HMS", 2.50, "Karabinek HMS niezastąpiony przy asekuracji i niezbędny przy mocowaniu sprzętów. Wykonany z lekkiego stopu, wytrzymały zarówno przy zamkniętym jak i otwartym zamku. Karabinek K5N marki Grivel zaopatrzony został w zamek typu Key Lock, który nie haczy. Karabinek jest zakręcany. Typ HMS- charakteryzuje się niską wagą, bardzo dużą wytrzymałością oraz zwiększonym prześwitem zamka.", "http://www.climbshop.pl/product_picture/fit_in_480x400/a0f95789f46fed371115408c157f7782.jpg", "http://www.climbshop.pl/product_picture/fit_in_480x400/a0f95789f46fed371115408c157f7782.jpg", "karabinek, asekuracja, wspinaczka", 2));
        productRepository.save(new ProductEntity("GRIVEL Karabinek K5N", 2.50, "Karabinek K5N niezastąpiony przy asekuracji i niezbędny przy mocowaniu sprzętów. Wykonany z lekkiego stopu, wytrzymały zarówno przy zamkniętym jak i otwartym zamku. Karabinek K5N marki Grivel zaopatrzony został w zamek typu Key Lock, który nie haczy. Karabinek jest zakręcany. Typ HMS- charakteryzuje się niską wagą, bardzo dużą wytrzymałością oraz zwiększonym prześwitem zamka.", "http://www.outdoorzy.pl/21814-84232/grivel-czekan-tech-machine.jpg", "http://www.outdoorzy.pl/21814-84232/grivel-czekan-tech-machine.jpg", "karabinek, asekuracja, wspinaczka", 11));
        productRepository.save(new ProductEntity("EKSPRES LIME DYNEEMA 12CM ", 2.00, "Lime Dyneema to lekki, doskonale wykonany ekspres wspinaczkowy Climbing Technology, który spełni wymagania nawet najbardziej doświadczonych poskramiaczy pionu. Ultralekka i wytrzymała tasiemka łączy dwa bezząbkowe, kute na gorąco karabinki Lime. Bez względu więc na to czy decydujesz się na szybkie, sportowe wejścia czy walkę z ogromną ścianą zapewnisz sobie pożądany komfort manipulacji sprzętowej. Ergonomiczny kształt karabinków Lime sprawia, że znakomicie leżą w dłoni. Ponadto dolny karabinek z giętym zamkiem wzbogacony jest o gumowy string stabilizujący jego pozycję podczas wpinania liny.", "https://f.allegroimg.com/s512/03727e/f4be41b84c76a243580c0b8ae9af", "https://f.allegroimg.com/s512/03727e/f4be41b84c76a243580c0b8ae9af", "karabinek, express, wspinaczka", 19));

        productRepository.save(new ProductEntity("PETZL ASCENSION P B17WRA", 3.00, "Ergonomiczny przyrząd zaciskowy do wychodzenia po linie. Parametry ASCENSION zostały zmodyfikowane - zwiększając  komfort, skuteczność i łatwość użytkowania.\n" +
                "\n" +
                "Ergonomiczna i szeroka rączka pokryta elastomerem zapewnia jednocześnie wygodny i mocny uchwyt. Powierzchnia uchwytu rozszerza się na dole by zmniejszyć ucisk na mały palec przy obciążaniu przyrządu. Ulepszono ergonomię na wysokości kciuka by ułatwić przesuwanie ASCENSION po linie.\n" +
                "\n" +
                "Język z zębami o większym niż dotychczas nachyleniu zapewnia działanie przyrządu w złych warunkach, zmniejszając tarcie w czasie przesuwania. Geometria uchwytu, zrobionego z jednego kawałka aluminium, eliminuje zjawisko niechcianej i utrudniającej przesuwanie dźwigni.", "https://www.polarsport.pl/media/catalog/product/cache/926507dc7f93631a094422215b778fe0/p/r/prod_large_29_8_2013_975_1_.jpg", "https://www.polarsport.pl/media/catalog/product/cache/926507dc7f93631a094422215b778fe0/p/r/prod_large_29_8_2013_975_1_.jpg", "malpa, petzl, asekuracja, wspinaczka", 3));


        productRepository.save(new ProductEntity("PETZL GRIGRI II", 5.00, "rzyrząd asekuracyjny ze wspomaganym hamowaniem GRIGRI 2 został opracowany by ułatwiać asekurację.\n" +
                "\n" +
                "Przyrząd Grigri 2 jest przeznaczony do asekuracji dolnej, górnej i na wędkę. Może być używany ze wszystkimi pojedynczymi linami dynamicznymi dostępnymi na rynku, o średnicy 8,9 do 11 mm  (zoptymalizowany dla lin o średnicy 9,4 mm do 10,3 mm).\n" +
                "\n" +
                "Grigri 2 ma nowy design umożliwiający doskonałą kontrolę zjazdu. Jedna ręka trzyma linę druga odblokowuję krzywkę, przy pomocy uchwytu. Uchwyt, o opatentowanej konstrukcjiumożliwia stopniowe odblokowanie. W połączeniu z mocnym tarciem krzywki daje duże poczucie bezpieczeństwa podczas zjazdu czy opuszczania.\n" +
                "\n" +
                "Bardzo lekki i zwarty, Grigri 2 będzie z wami we wszystkich skałkach na świecie.\n" +
                "\n" +
                "obsługa identyczna jak w klasycznym systemie asekuracyjnym: obydwie ręce na linie\n" +
                "\n" +
                "odpadnięcie jest zatrzymywane przez zaciśnięcie ręki na wolnym końcu liny\n" +
                "\n" +
                "wspomagane hamowanie: podczas zatrzymywania odpadnięcia, asekurujący trzyma wolny koniec linykrzywka obraca się zaciskając linę, zwiększając hamowanie aż do całkowitego zatrzymania liny\n" +
                "\n" +
                "Grigri 2 jest bardzo zwarte i lekkie (25% mniejsze i 20 % lżejsze od GRIGRI)\n" +
                "\n" +
                "używany na linach pojedynczych o średnicy 8,9- 11 mm (zoptymalizowany dla lin o średnicy 9,4 mm do 10,3 mm)\n" +
                "\n" +
                "na okładce wygrawerowany schemat użycia", "https://www.polarsport.pl/media/catalog/product/d/1/d14bg-grigri-focus-2_lowres.jpeg", "https://www.polarsport.pl/media/catalog/product/d/1/d14bg-grigri-gris_lowres.jpeg", "gri gri, asekuracja, wspinaczka", 2));
        

        productRepository.save(new ProductEntity("Lina 80m Tendon 9,8 (ż/cz)", 20.00, "Lina sportowa o klasycznej konstrukcji dla wszystkich, którzy chcą być lepsi. Mimo swej małej średnicy lina zaskakuje długą żywotnością i odpornością na otarcia. Niska waga, wyższa ilość odpadnięć i znakomita manipulacja to parametry, dzięki którym zaczniesz przekraczać swoje limity.", "http://crossline.pl/2784-large_default/ambition-85-bicolour-complete-shield-60-m.jpg", "http://crossline.pl/2784-large_default/ambition-85-bicolour-complete-shield-60-m.jpg", "lina, asekuracja, wspinaczka", 1));
        productRepository.save(new ProductEntity("Lina 60m Tendon 10,0 (cze/nie)", 20.00, "Klasyk w nowej odsłonie: Prezentowany na zdjęciu oplot jest nowym wzorem oplotu zastrzeżonym na terenie Polski. Od 2014 roku tylko liny z tym wzorem oplotu będą objęte gwarancją polskiego importera.\n" +
                "Lina statyczna tendon speleo, produkowana z myślą o ekstremalnych warunkach pracy podczas eksploracji jaskiń. Dzięki swoim parametrom i budowie oplotu z dużym powodzeniem znajduje zastosowanie w pracach wysokościowych. Oznacza się niską rozciągliwością, wysoką trwałością statyczną i ponadstandardową wytrzymałością na otarcia. Jej atrakcyjna cena jest dodatkowym atutem. Lina spełna wymogi normy EN 1891 w klasie A .", "http://ratwork.pl/media/BEAL/Liny/BEAL_Lina_Dynamiczna_Zenith_9,5mm_50m_.jpg", "http://ratwork.pl/media/BEAL/Liny/BEAL_Lina_Dynamiczna_Zenith_9,5mm_50m_.jpg", "lina, asekuracja, wspinaczka", 1));

        NewsEntity news = new NewsEntity();
        news.setTitle("Test");
        news.setDescription("Sprawdzenie czy dodałem newsa");
        news.setLink("https://forums.penny-arcade.com/discussion/209346/i-dont-know-what-im-doing-chat");
        news.setTag("lala");

        NewsEntity news2 = new NewsEntity();
        news2.setTitle("Test2");
        news2.setDescription("Sprawdzenie czy dodałem newsa2");
        news2.setLink("https://forums.penny-arcade.com/discussion/209346/i-dont-know-what-im-doing-chat");
        news2.setTag("looooooo");

        NewsEntity news3 = new NewsEntity();
        news3.setTitle("Test");
        news3.setDescription("Sprawdzenie czy dodałem newsafsgdsfdsfsdfdsfdsffdsfdsgfsggfgfdfasdgfrhgthjtjrtjytjgfhfnjfnfnjgfngfnhdshdfdbfdx3Sprawdzenie czy dodałem newsafsgdsfdsfsdfdsfdsffdsfdsgfsggfgfdfasdgfrhgthjtjrtjytjgfhfnjfnfnjgfngfnhdshdfdbfdx3");
        news3.setLink("https://forums.penny-arcade.com/discussion/209346/i-dont-know-what-im-doing-chat");
        news3.setTag("looooooo");


        newsRepository.save(news);
        newsRepository.save(news2);
        newsRepository.save(news3);

        ProductOrderEntity order1 = new ProductOrderEntity();
        order1.setUser(userService.getUserById(0L));
        order1.setOrderStart(new Date());
        order1.setOrderEnd(new Date());

        productOrderRepository.save(order1);

        ProductOrderEntity order2 = new ProductOrderEntity();
        order2.setUser(new UserEntity());
        order2.setOrderStart(new Date());
        order2.setOrderEnd(new Date());

        productOrderRepository.save(order2);

    }
}



