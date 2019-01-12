# flash2019

Project for the 2019 FIRST Robotics Competition.

# Building

To build the code run the gradle wrapper: `./gradlew build` for UNIX systems and `gradlew.bat build` for Windows systems.

# Configuration with Intellij IDEA

Intellij, by default, works with gradle, which means that all that is required, is to open the directory using `File->Open`.

Aftering opening the directory, Intellij will automatically sync with gradle. Once finished, you will be able to start working.

## Configurating FlashLib with Sources and Javadoc

To attach sources and javadoc to Flashlib, write click on `libs/flashlib.jar` from the project menu, and click `Open Library Settings`.

Press `Insert` (`+`) and navigate to the `libs/flashlib-extra/flashlib-sources.jar` file and select it to add sources.

Press `Insert` (`+`) and navigate to the `libs/flashlib-extra/flashlib-javadoc.jar` file and select it to add javadoc.

Press `Apply` to apply changes.

You may now view documentation by clicking `Ctrl-Q` on flashlib artifacts, and view the source code by pressing `Ctrl+B` on flashlib artifacts.