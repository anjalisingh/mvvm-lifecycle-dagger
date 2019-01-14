# OpenWeatherApp
Features include
- Add favorite location
- Search weather for favorite cities
- Weather forecast for favorite cities
- Map view for favorite cities
- Map for maximum 10 cities around the favorite cities
- Weather info on map view
- Unit tests (viewmodel test coverage)

Built using:
- Android Studio
- Kotlin
- MVVM
- Dagger
- LiveData Android Lifeycle components
- Room Persistence
- RxJava, RXBinding
- Retrofit
- Picasso
- JUnit
- Gradle

Build Instructions :

Clone the source repository

On the command line, enter:
git clone https://rogue_anji@bitbucket.org/rogue_anji/openweatherapp.git

Android Studio
- Import the project cloned above
- Run the project
- For testing, go to the test directory com/solutions/openweatherapp and right click to run "Tests in openweatherapp"

Command Line - Go to the directory where you cloned the repo
- cd openweatherApp (Make sure you see "gradlew" in this directory)
- add the android sdk location by adding "local.properties" file at this location
- run the project from the terminal "./gradlew installDebug".
- test the project "./gradlew test".

Further Improvements:
- databinding view update from viewmodel
- progress state
- map improvements