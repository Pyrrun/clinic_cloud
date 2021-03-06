# Clinic Cloud - Sprawozdanie

## Spis treści

* [Wstęp](#wstęp)
* [Informacje o projekcie](#informacje-o-projekcie)
* [Technologie](#technologie)
* [Projekt systemu](#projekt-systemu)
* [Sposób realizacji](#sposób-realizacji)
* [Infrastruktura Azure cloud](#infrastruktura-azure-cloud)
* [Klaster Kubernetes](#klaster-kubernetes)
* [CI](#ci)
* [Aplikacja w chmurze](#aplikacja-w-chmurze)
* [Napotkane problemy](#napotkane-problemy)
* [Wnioski](#wnioski)
* [Autorzy](#autorzy)
* [Wideo prezentacyjne](#wideo-prezentacyjne)

## Wstęp
Sprawozdanie to jest podsumowaniem projektu realizowanego na Wydziale Elektrycznym Politechniki Warszawskiej w ramach części projektowej przedmiotu "Programowanie usług w chmurze" pod kierownictwem dr inż. Radosława Roszczyka.  
Zadaniem do wykonania w ramach projektu było stworzenie prostego systemu, który miał sę składać z zastosowanych gotowych usług, usług napisanych przez studentów oraz aplikacji, wykorzystującej usługi w chmurze. Gotowy system powinien być uruchomiony na wybranej przez studentów platformie chmurowej (PaaS).

## Informacje o projekcie
Tworzony w ramach realizowanego przedmiotu projekt to wirtualna przychodnia, czyli system, który umożliwia zarządzanie placówką medyczną w sieci. Przewiduje on istnienie dwóch rodzajów użytkownika - lekarz oraz recepcjonista. Zgodnie z założeniami recepcjonista powinien mieć możliwość dodania pacjenta do systemu oraz utworzenia wizyty pacjenta z lekarzem, natomiast lekarz możliwość przeglądania oraz edycji harmonogramu i przeglądania listy wizyt. Ponadto obydwaj użytkownicy powinni mieć możliwość przeglądania listy pacjentów, dodania ogłoszenia oraz uruchomienia czatu.

## Technologie
Projekt wykorzystuje następujące technologie:
* Java 11
* Spring Framework 2.4.3
* Vue 3.2.20
* Terraform 1.1.3
* Helm 3.7.1

## Projekt systemu
### Diagram architektury
Poniżej zaprezentowany został wstępny diagram architektury projektu. W przypadku potrzeby zmiany jego struktury zostanie on zaktualizowany.

![Alt text](utils/diagram-architektury.png?raw=true "Diagram architektury")

Jak można zauważyć backend, jak i serwis autoryzacyjny będą korzystały z zewnętrznej bazy danych, natomiast czat zostanie zintegrowany z inną, nierelacyjną bazą danych redis.

### Diagram przypadków użycia
Diagram przypadków użycia przedstawia wszystkie podstawowe możliwości, które zostaną zaimplementowane w projekcie.

![Alt text](utils/diagram-przypadkow-uzycia.png?raw=true "Diagram przypadków użycia")

Projekt zakłada istnienie dwóch rodzajów użytkowników - lekarz oraz recepcjonista. Każdy z nich posiada dostęp do różnych widoków oraz funkcjonalności systemu. Lekarz oraz recepcjonista będą w stanie porozumiewać się w czasie rzeczywistym za pomocą czatu. Oboje również mogą dodać ogłoszenie.  
Recepcjonista będzie w stanie dodatkowo utworzyć nową wizytę oraz dodać nowego pacjenta do systemu. Lekarz natomiast ma możliwość modyfikacji harmonogramu, przejrzenia listy wizyt czy listy pacjentów.

## Sposób realizacji

Wykonanie projektu podzieliliśmy na cztery etapy:
* Wybór tematu projektu, stosu technologicznego, platformy chmurowej oraz utworzenie zadań i ich podział pomiędzy członków zespołu.
* Realizacja przydzielonych zadań, utworzenie aplikacji wraz z wcześniej ustalonymi funkcjonalnościami lokalnie, przetestowanie ich.
* Migracja działającego systemu na platformę chmurową Microsoft Azure.
* Nagranie filmu wideo prezentującego funkcjonalności systemu oraz uzupełnienie repozytorium o sprawozdanie i dokumentację.

Podział zadań, jak i wybór stosu technologicznego zostały wykonane w ten sposób, aby każdy z członków zespołu otrzymał satysfakcjonujące go zadania oraz technologię, w której jest doświadczony.  
Realizacja zadań odbyła się sprawnie - ustaliliśmy, że priorytetem będzie implementacja najważniejszych wewnętrznych funkcji systemu opisanych w diagramie przypadków użycia, czyli zarządzanie wizytami, harmonogramem, ogłoszeniami oraz pacjentami. Następnie wykonywane były zadania związane z autoryzacją użytkowników oraz czatem, który finalnie miał być podpięty do innej bazy danych niż pozostałe funkcjonalności systemu.  
Po przetestowaniu wszystkich zaimplenetowanych funkcji aplikacji rozpoczął się etap migracji na chmurę.  

## Infrastruktura Azure cloud

Za pomocą programi terraform utworzone zostały wszystkie serwisy potrzebne do uruchomienia aplikacji w chmurze.

- baza danych - używana do przetrzymywania danych o użytkownikach itp.
- storage account - do przechowywania zdjęć
- Azure Container Registry - przechowuje zbudowane obrazy dockerowe
- AKS - klaster kubernetes, na nim uruchamiane są pody z aplikacją oraz ingress pozwalający dostać się do aplikacji z sieci zewnętrznej

![Alt text](utils/azure_services.png?raw=true "elementy stworzone na Azure za pomocą terraforma")

## Klaster Kubernetes

Do uruchomienia aplikacji użyty został Helm Chart. Serwisy są podzielone na Deployment, Service i ConfigMap. Do wystawienia aplikacji na świat użyty został ingress-nginx. Z powodu budżetu na Azure Cloud musieliśmy zrezygnować z zakupienia domeny i certyfikatu, dlatego aplikacja jest wystawiona na adresie IP i protokole http, co w prawdziwym produkcyjnym systemie byłoby niewskazane. Dla tego projektu wszystkie dane w systemie są przykładowe i nie niosą ze soba niebezpieczeństwa gdyby dostały się w niepowołane ręce.

## CI

Za pomocą Github actions wszystkie obrazy są budowane i wysyłanie do Azure Container Registry przy każdym pushu na branch `master`

## Aplikacja w chmurze
Aplikacja została uruchomiona w chmurze. Dostęp do niej jest pod adresem:  
`http://20.76.242.22/  `  

W celu zalogowania się na panel lekarza należy podać następujące dane:  
`E-mail: codeka1516@incoware.com`   
`Password: Test1234@`  

W celu zalogowania się na panel recepcjonisty należy podać następujące dane:  
`E-mail: natokax221@healteas.com`  
`Password: Test1234@`

## Napotkane problemy

Najwięcej problemów sprawił etap realizacji zadań - w szczególności tych związanych z interfejsem użytkownika. Zaprojektowanie, poprawne zaimplementowanie każdego z okien oraz podłączenie ich do logiki aplikacji było dość zaawansowane i wymagało dużej liczby poprawek i testów.  
Inne napotkane problemy zdarzały się przy migracji systemu na chmurę. Często okazywało się, że funkcjonalność, która prawidłowo działała uruchomiona lokalnie, w chmurze ulegała błędom. Sporym wyzwaniem okazało się również podłączenie serwisu autoryzacyjnego do systemu oraz poprawne funkcjonowanie czatu przy zastosowaniu bazy danych redis - finalnie baza ta nie została zastosowana, natomiast serwis czatu podłączony został do bazy danych, z której korzysta klinika.
## Wnioski

Realizacja projektu pozwoliła nam zaznajomić się z platformą chmurową Microsoft Azure od strony praktycznej - dowiedzieliśmy się, jakie udogodnienia oferują rozwiązania chmurowe oraz jakie problemy generuje proces migracji lokalnej aplikacji na taką platformę. Problemy, z którym się borykaliśmy wynikały głównie z braku obycia z programowaniem usług w chmurze.  
Platforma chmurowa Microsoft Azure okazała się być intuicyjna oraz przyjazna nowym użytkownikom. W przypadku napotkania problemów służyła przejrzystą dokumentacją oraz samouczkami, które prezentowały krok po kroku jak wykonać określoną czynność.

## Wideo prezentacyjne

link: https://www.youtube.com/watch?v=brdG5onsCfs&ab_channel=MichalJak%C3%B3bczak

## Autorzy

Bartosz Siwiński  
Maciej Siara  
Krzysztof Móżdżyński  
Krzysztof Wyszyński  
Michał Jakóbczak

