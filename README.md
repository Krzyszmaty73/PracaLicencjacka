# Split2Go

## Informacje o aplikacji
Celem pracy licencjackiej jest zaprojektowanie i zaimplementowanie aplikacji webowej, która pomoże w sprawnym rozliczaniu wspólnych wydatków. Użytkownicy będą mogli tworzyć pokoje rozliczeniowe w których możliwe będzie dodawanie wspólnych kosztów. W tym celu zostanie zaprojektowany algorytm, który będzie podawał informacje zwrotne w postaci wyliczonych długów i naddatków poszczególnych uczestników. Szczególna uwaga zostanie poświęcona bezpieczeństwu użytkowników aplikacji. Komunikacja wewnątrz aplikacji będzie odbywała się w architekturze klient-serwer. 

## Wykorzystane technologie:
* Angular - Angular CLI: 10.0.3
* Spring Boot - Java 1.8 SDK 8

## Architektura aplikacji:
![Example screenshot](./img/architektura.png)

## Uruchomienie
Proces uruchamiania aplikacji należy zacząć od pobrania kodu z repozytorium (git clone https://github.com/Krzyszmaty73/PracaLicencjacka.git) Aby uruchmić back-end należy użyć komendy (mvnw spring-boot:run) w pliku o nazwie SpringBoot.
Aby uruchomić front-end z wykorzystaniem klienta HTTPS należy wejść do pliku o nazwie AngularSpringboot. Podczas uruchamiania należy podać poprawną ścieżkę, gdzie znajdują się pobrane wygenerowane certyfikaty (ng serve --ssl --ssl-key c:\...\angularProject\certificates\localhost.key  --ssl-cert c:\...\angularProject\certificates\localhost.crt)
$ git clone https://github.com/Krzyszmaty73/PracaLicencjacka.git
Następnie należy wpisać w przeglądarkę adres https://localhost:4200

## Status projektu
Projekt jest skończony w pewnym etapie. Planowana jest rozbudowa w późniejszym czasie.
