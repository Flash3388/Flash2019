# flash2019

Project for the 2019 FIRST Robotics Competition.

# Building

To build the code run the gradle wrapper: `./gradlew build` for UNIX systems and `gradlew.bat build` for Windows systems.

# Configuration with Intellij IDEA

Intellij, by default, works with gradle, which means that all that is required, is to open the directory using `File->Open`.

You can probably used the default suggested configuration, so simply accept it and let Intellij open the project.

Aftering opening the directory, Intellij will automatically sync with gradle. Once finished, you will be able to start working.

# Configuring with VS Code

All the instructions for installing and setupping on WPI lib site by the link below :
http://wpilib.screenstepslive.com/s/currentCS/m/java/l/1027503-installing-c-and-java-development-tools-for-frc

Then you need to open the directory using `File->Open Folder...`
and after openning the directory try running Gradle build command to check if you miss anything by pressing `Ctrl+Shift+P`typing `>WPILib: Run a command in Gradle` and then typing `build`

## Version

Because of the gradle version used, it is required to use at least Intellij IDEA `version 2018.3.2`.

## Configurating FlashLib with Sources and Javadoc

To attach sources and javadoc to Flashlib, write click on `libs/flashlib.jar` from the project menu, and click `Open Library Settings`.

Press `Insert` (`+`) and navigate to the `libs/flashlib-extra/flashlib-sources.jar` file and select it to add sources.

Press `Insert` (`+`) and navigate to the `libs/flashlib-extra/flashlib-javadoc.jar` file and select it to add javadoc.

Press `Apply` to apply changes.

You may now view documentation by clicking `Ctrl-Q` on flashlib artifacts, and view the source code by pressing `Ctrl+B` on flashlib artifacts.