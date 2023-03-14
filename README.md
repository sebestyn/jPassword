# JPassword

### PROGRAMOZÁS ALAPJAI 3. – HÁZI FELADAT

#### ZSOLT SEBESTYÉN


## Rövid leírás

JPassword egy grafikus felülettel rendelkező jelszókezelő applikáció. Az alkalmazás első indításánál a
felhasználónak meg kell adnia egy Mester-jelszót. Az adatok titkosítása ezzel a jelszó segítségével történik, így
minden belépésnél meg kell először adnia. Opcionálisan beállítható, hogy a megadott számú sikertelen
próbálkozások után az adatok törlődjenek biztonsági okokból.

Sikeres regisztráció vagy belépés után a főoldal jelenik meg. Itt a felhasználó ki tudja listázni az elmentett jelszavait
és egyéb típusú adatait (pl.: felhasználónév, oldal neve). Ezek között tud a felhasználó keresni.

Lehetőség van mappákat létrehozni és ezek segítségével kategorizálni a különböző adatokat. Egy új mappa
létrehozásánál meg kell adni a mappa nevét majd ebbe elmenteni az új jelszavakat. Egy új jelszó létrehozásánál
vannak opcionális és kötelező mezők. Például kötelező megadni egy nevet (ami praktikusan az oldal/applikáció
neve, ahol a jelszót használja) és egy jelszót. Opcionálisan meg lehet adni felhasználónevet, leírást és a titkosítás
típusát.

A jelszó mellett létre lehet hozni egyszerű szöveg típusú adatot is ami titkosítható. Ennek a típusnak 2 kötelező
mezője van: a név és a leírás. Opcionálisan itt is ki lehet választani a titkosítás típusát.

A titkosított adatokat az alkalmazás egy fájlba menti. Az adatokat a felhasználó tudja kimenteni és betölteni is,
azaz a mentett adatok hordozhatók, de azokat csak a megfelelő Mester-jelszó birtokában lehet visszafejteni.

Az alkalmazás futtatható parancssorból és grafikus felületen is.

## Use-case

#### o Jelszó hozzáadása

- Aktorok: Felhasználó, alkalmazás
- Főforgatókönyv:
    1. A felhasználó megnyitja az egyik mappában lévő jelszavakat
    2. A felhasználó megnyomja az „Új elem” feliratú gombot
    3. A felhasználó kitölti a jelszóhoz tartozó valamennyi mezőjét (név, felhasználónév, jelszó,
       leírás, mappája, titkosítás típus)
    4. Az alkalmazás ellenőrzi, hogy az összes kötelező mező ki van-e töltve és addig nem
       engedi elmenteni (név, jelszó)
    5. Az alkalmazás elmenti az adatok
    6. Az alkalmazás visszalép a főoldalra

#### o Jelszavak kilistázása

- Aktorok: Felhasználó, alkalmazás
- Főforgatókönyv:
    1. A felhasználó megadja a Mester-jelszavát
    2. Az alkalmazás megfelelő jelszó esetén beolvassa a fájlokból az adatokat és kikódolja
       azokat
    3. Az alkalmazás megnyitja a főoldalt
    4. A felhasználó kiválaszt egy mappa jelszavait és rákattint
    5. Az alkalmazás kilistázza a jelszavakat


#### o Jelszó keresés

- Aktorok: Felhasználó, alkalmazás
- Főforgatókönyv:
    1. A felhasználó kiválaszt egy mappa jelszavait és rákattint
    2. A felhasználó a keresőmezőbe beír egy szöveget
    3. Az alkalmazás megkeresi azokat az adatokat, amiben szerepel ez a szövegrész és
       kilistázza azokat

#### o Jelszó törlése

- Aktorok: Felhasználó, alkalmazás
- Főforgatókönyv:
    1. A felhasználó kiválaszt egy mappa jelszavait és rákattint
    2. A felhasználó rákattint az egyik jelszóra majd a „Elem törlése” gombra
    3. Az alkalmazás kitörli a jelszó adatait

#### o Mappa hozzáadás

- Aktorok: Felhasználó, alkalmazás
- Főforgatókönyv:
    1. A felhasználó rákattint az „Új mappa” gombra
    2. A felhasználó beírja a mappa nevét és rámegy a „Ok” gombra
    3. Az alkalmazás megjeleníti a mappát a többi mappák között

#### o Mappa törlés

- Aktorok: Felhasználó, alkalmazás
- Főforgatókönyv:
    1. A felhasználó rákattint az egyik mappára
    2. A felhasználó rákattint a „Mappa törlése” gombra
    3. Az alkalmazás kitörli a mappát és a benne lévő elemeket

## Megoldási ötlet

Az alkalmazást java nyelven írom. A grafikus felülethez Swing GUI csomagot használom. A beállítások és az
adatok exportálás/importálása a menüsorban valósítom meg a JMenuBar segítségével. A mappákat JTree a
jelszavak kilistázását pedig JTable alkalmazásával oldom meg. A gombok JButton, a szöveges bemenetek
JTextField típusúak lesznek.

Az alkalmazás „.crypted” formátumú fájlokkal dolgozik amiben minden esetben csak titkosított adatok lehetnek.
A fájlok az alkalmazás indításánál betöltődnek és bezárásánál kimentődnek. A Mester-jelszó egy külön fájlba lesz
elmentve másfajta titkosítással.
