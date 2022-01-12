# Clinic Cloud

## Spis treści
* [Informacje o projekcie](#informacje-o-projekcie)
* [Technologie](#technologie)
* [Diagram architektury](#diagram-architektury)
* [Diagram przypadków użycia](#diagram-przypadków-użycia)
* [Autorzy](#autorzy)

## Informacje o projekcie
Tworzony w ramach realizowanego przedmiotu "Programowanie usług w chmurze" projekt to wirtualna przychodnia, czyli system, który umożliwia zarządzanie placówką medyczną.  
Pierwszym etapem projektu jest implementacja wcześniej ustalonych funkcjonalności lokalnie oraz przetestowanie ich. Następny etap to migracja systemu na platformę chmurową Amazon Web Services.
	
## Technologie
Projekt wykorzystuje następujące technologie:
* Java 11
* Spring Framework 2.4.3
* Vue 3.2.20

## Diagram architektury
Poniżej zaprezentowany został wstępny diagram architektury projektu. W przypadku potrzeby zmiany jego struktury zostanie on zaktualizowany.

![Alt text](utils/diagram-architektury.png?raw=true "Diagram architektury")

Jak można zauważyć backend, jak i serwis autoryzacyjny będą korzystały z zewnętrznej bazy danych, natomiast czat zostanie zintegrowany z inną, nierelacyjną bazą danych redis.

## Diagram przypadków użycia
Diagram przypadków użycia przedstawia wszystkie podstawowe możliwości, które zostaną zaimplementowane w projekcie.

![Alt text](utils/diagram-przypadkow-uzycia.png?raw=true "Diagram przypadków użycia")

Projekt zakłada istnienie dwóch rodzajów użytkowników - lekarz oraz recepcjonista. Każdy z nich posiada dostęp do różnych widoków oraz funkcjonalności systemu. Lekarz oraz recepcjonista będą w stanie porozumiewać się w czasie rzeczywistym za pomocą czatu. Oboje również mogą dodać ogłoszenie.  
Recepcjonista będzie w stanie dodatkowo utworzyć nową wizytę oraz dodać nowego pacjenta do systemu. Lekarz natomiast ma możliwość modyfikacji harmonogramu, przejrzenia listy wizyt czy listy pacjentów.

## Autorzy

Bartosz Siwiński  
Maciej Siara  
Krzysztof Móżdżyński  
Krzysztof Wyszyński  
Michał Jakóbczak

