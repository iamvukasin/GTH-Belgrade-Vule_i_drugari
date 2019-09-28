# GTH-Location-TeamName
Project submission for the `Global Travel Hackathon in Belgrade, by Vule i drugari team`.

**Our application gathers the data about flight expenses when traveling with pets for several airline companies and compares prices when traveling with a pet or without.**

![Add a screenshot from your project. For example the main website page.](https://raw.githubusercontent.com/Global-Travel-Hackathon/GTH-Location-TeamName/master/screenshots/Global-Travel-Hackathon-image.png)

## :books: Description

One of the defects of flight search services and APIs is that they do not provide the information about prices for bringing a pet on the flight. The demo of our applications gathers this data from several specific airlines and provides service of calculating the cumulative price for traveling with animals as well as comparing it to the price of travel without a pet. The app is created for the Android operating system.

**Hackaton theme applied: Accessibility**

This project was implemented with the idea to make the travel more accessible to people that travel with their pets.

**Development tools used in the project**
* Languages: Kotlin, Python
* IDEs: AndroidStudio, PyCharm

**SDKs used in the project**
* Android SDK

**APIs used in the project**
* Tequila by Kiwi.com

**Libraries used in the project**
* AndroidX
* Material components for Android
* Python's Requests and flusk libraries 

## :hugs: Maintainers

List all the team members. For example:
* [Aleksandar Cvetić](https://github.com/acac97)
* [Vukašin Manojlović](https://github.com/iamvukasin)
* [Sanja Mijović](https://github.com/sanjamijovic)

## :tada: Why is this so awesome?

* It makes travel with pets more accessible.
* It calculates the price for as many pets as the owner wants to bring with him.
* It saves time and effort of contacting the airline to get the needed information or dealing with several different airline policies.
* It is scalable and can be a great addition to any existing flight search service.

## :hammer_and_wrench: Installation

### Python server
* Install following packages via pip: flusk, requests, datetime.
* Navigate to server folder of this repo
* Run server using following commands(assumes that you use UNIX type system):
```
$ export FLASK_APP=main.py
$ flask run --host=0.0.0.0 --port=5001
```
### Petspensive (Android app)

* Make sure that server and mobile phone you are going to run app on are on same network
* Get ip address of server
* In `android/app/src/main/java/io/github/iamvukasin/hacktravel/api/FlightService.kt` change variable `SERVER_ADDRESS` to server's IP address
* Open android project in AndroidStudio
* Build and deploy app
* Success!

## :bulb: Devstack

* Python
* Kotlin
* Java

## :warning: Licence
The code in this project is licensed under MIT license. By contributing to this project, you agree that your contributions will be licensed under its MIT license.
