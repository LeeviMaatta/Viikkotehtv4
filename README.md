# Viikkotehtävä 4 – TODO-lista

## Yleiskuvaus

Tämä Android-sovellus on toteutettu Jetpack Composella ilman XML-layoutteja ja noudattaa MVVM-arkkitehtuuria. Sovellus esittää yksinkertaisen TODO-listan, jossa tehtäviä voidaan lisätä, poistaa, merkitä valmiiksi, muokata ja järjestää. Käyttöliittymä reagoi automaattisesti tilamuutoksiin ViewModelin kautta.

---

## Datamalli

### Task

`Task`-data class kuvaa yksittäistä tehtävää.

Kentät:

* **id (Int)**: Tehtävän yksilöllinen tunniste
* **title (String)**: Tehtävän otsikko
* **description (String)**: Tehtävän kuvaus
* **priority (Int)**: Tehtävän prioriteetti
* **dueDate (String)**: Tehtävän deadline (merkkijonona)
* **done (Boolean)**: Tehtävän tila (valmis / ei valmis)

```kotlin
data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val priority: Int,
    val dueDate: String,
    val done: Boolean
)
```

---

## Mock-data

Sovellus käyttää mock-dataa (5 tehtävää), jotka alustetaan TaskViewModel-luokan init-lohkossa. Mock-data mahdollistaa sovelluksen toiminnallisuuksien testaamisen.

---

## ViewModel ja tilanhallinta

### TaskViewModel
**TaskViewModel** vastaa sovelluksen liiketoimintalogiikasta ja tehtävälistan tilanhallinnasta. Tehtävät säilytetään StateFlow-tilassa, jota käyttöliittymä kuuntelee reaktiivisesti Jetpack Composen avulla.

ViewModel tarjoaa seuraavat toiminnot:
* **addTask(task: Task)** – lisää uuden tehtävän listaan
* **toggleDone(id: Int)** – vaihtaa tehtävän valmiustilan
* **removeTask(id: Int)** – poistaa tehtävän listasta
* **sortByDueDate()** – järjestää tehtävät deadlinen mukaan

---

## MVVM arkkitehtuuri

Sovellus noudattaa MVVM-arkkitehtuuria, jossa käyttöliittymä (UI) ja sovelluslogiikka on selkeästi erotettu toisistaan. Navigaatio määritellään NavHostissa, ja molemmat näkymät käyttävät samaa TaskViewModel-instanssia.
Sekä Home- että Calendar-näkymä:
* hakevat tehtävät ViewModelista (collectAsState)
* näyttävät saman tehtävälistan eri esitystavoilla
* käyttävät samoja ViewModelin funktioita tehtävien muokkaamiseen

---

## Navigointi

Navigointi Jetpack Composessa tarkoittaa eri Composable-näkymien (ruutujen) välistä siirtymistä sovelluksessa. Navigointi hoidetaan Navigation Compose -kirjastolla, jossa jokainen ruutu määritellään reittinä (route).

NavHost on Composable, joka määrittelee:
* mitkä ruudut sovelluksessa on
* mikä ruutu näytetään milläkin reitillä
* mikä on aloitusruutu

NavController vastaa navigoinnin ohjaamisesta:
* siirrytään ruudusta toiseen (navigate)
* palataan takaisin (popBackStack)

Sovelluksessa on kaksi reittiä: Home ja Calendar.
Navigaatio on määritelty MainActivityssa NavHostin avulla.
* Home-näkymä näyttää tehtävälistan.
* Calendar-näkymä näyttää samat tehtävät kalenterimaisesti ryhmiteltynä päivämäärän mukaan.
* Home-näkymästä siirrytään Calendar-näkymään painikkeella navController.navigate("calendar").
* Calendar-näkymästä palataan Home-näkymään navController.popBackStack()-kutsulla.

---

## CalendarScreen

CalendarScreen näyttää tehtävät kalenterimaisesti ryhmittelemällä ne deadlinen (dueDate) perusteella. Näin käyttäjä näkee selkeästi:
* mille päivälle tehtävä kuuluu
* mitä tehtäviä on samana päivänä

Kalenterinäkymä ei käytä erillistä kalenterikomponenttia, vaan yksinkertaista listarakennetta, joka havainnollistaa kalenterin idean selkeästi.

---

## Dialogit

### AlertDialog addTask- ja editTask-toiminnoissa

Tehtävien lisääminen ja muokkaaminen on toteutettu AlertDialogilla, ei erillisinä navigaationäkymiinä.

**addTask**:
Kun käyttäjä painaa lisäyspainiketta, avautuu AlertDialog, jossa on tekstikentät tehtävän tiedoille. Tallennuspainike kutsuu ViewModelin addTask-funktiota ja lisää tehtävän listaan.

**editTask**:
Kun käyttäjä painaa olemassa olevaa tehtävää listassa tai kalenterissa, avautuu AlertDialog, jossa kentät on esitäytetty valitun tehtävän tiedoilla.
Tallennus päivittää tehtävän updateTask-funktiolla ja poistopainike poistaa tehtävän removeTask-funktiolla.

---

## Reaktiivisuus ja StateFlow

Jetpack Compose -käyttöliittymä kuuntelee ViewModelin tilaa
Kun **_tasks-StateFlow** päivittyy:
* Compose havaitsee muutoksen
* vain tarvittavat UI-osat piirretään uudelleen
* muutokset näkyvät heti listassa ilman erillistä päivityslogiikkaa

### StateFlow

**StateFlow** on tilavirta, joka säilyttää aina yhden nykyisen arvon. Se lähettää uuden arvon kuuntelijalle heti kun tila muuttuu. StateFlow toimii hyvin Jetpack Composen kanssa. Se on turvallinen vaihtoehto, jota voi käyttää myös hyvin monimutkaisissa sovelluksissa.

---

## Käyttöliittymä (Compose)

* **HomeScreen** näyttää tehtävälistan
* Tehtävät esitetään **LazyColumn** korttina
* Jokaisella tehtävällä on:
    * Checkbox valmiustilan vaihtamiseen
    * Otsikko ja kuvaus
* Tehtävää klikkaamalla avautuu DetailScreen-dialogi
* Dialogista voi:
    * Poistaa tehtävän
    * Muokata tehtävää
* Tehtäviä voi listä lomakkeesta

---

## Teknologiat

* Kotlin
* Jetpack Compose
* Android Studio (Compose Template)
* Android ViewModel (MVVM)
* StateFlow
