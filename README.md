# Viikkotehtävä 3 – TODO-lista

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
**TaskViewModel** vastaa sovelluksen tilasta ja tehtävälistan hallinnasta. Tehtävät säilytetään StateFlow-tilassa, jota UI kuuntelee.

ViewModel tarjoaa seuraavat toiminnot:
* **addTask(task: Task)** – lisää uuden tehtävän listaan
* **toggleDone(id: Int)** – vaihtaa tehtävän valmiustilan
* **removeTask(id: Int)** – poistaa tehtävän listasta
* **sortByDueDate()** – järjestää tehtävät deadlinen mukaan

---

## Miksi MVVM on hyödyllinen Compose-sovelluksissa?

* UI ja logiikka on erotettu → koodi on selkeämpää
* ViewModel säilyy konfiguraatiomuutoksissa (esim. näytön kääntö)
* Compose toimii luontevasti reaktiivisen tilan kanssa
* Sovellusta on helpompi testata ja laajentaa
* UI ei muokkaa dataa suoraan, vaan käyttää ViewModelin funktioita

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
