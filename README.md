# Player RPG Classes 
======
A plugin for [Minecraft](https://minecraft.net/) servers, currently maintained by [Saltyy](https://www.saltyy.at).
This plugin adds classes with special powers to Minecraft!

This Plugin was originally forked from [Xemor](https://github.com/Xemorr/superheroes)
#### Index
Build

* [Building](#building)
* [Bug Reporting](#bug-reporting)
* [Contributing](#contributing)
* [Code Requirements](#code-requirements)
* [Plans](#plans)



Building
-------------
There are some ways to Build the plugin. Here is my way:
The development team is very open to both bug and feature requests / suggestions. You can submit these on the 
### Buildtools
1. `Download Buildtools`

    Download Buildtools from [spigot](https://www.spigotmc.org/wiki/buildtools/).

2. `Move the Jar file`

    Move the downloaded Buildtool.jar file into the plugins folder

3. `Build with BuildTools on 1.16.4 (with 1024Memory)`
    run the following command to install it
    ```
    java -Xmx1024M -jar BuildTools.jar --rev 1.16.4
    ```

### Maven
1. `Download Maven`

    Download Buildtools from [maven](https://maven.apache.org/download.cgi).
    
2. `Install Maven on your system`
    Based on the System you use, maven setup is different
    1. `Windows`
        get java, get maven, setup variables in advanced settings (JAVA_HOME, MAVEN_HOME,....)
        check some tutorial for that

    2. `Linux`
        

    3. `Shitty MacOS`

3. `change target path`

    change the Path in the pom.xml (properties -> <dir>). Thats the path where the builded file will be saved

4. `Run the command and build maven in the defined path`

    ```
        mvn clean package
    ```


Bug Reporting
-------------
The development is very open to both bug and feature requests / suggestions. You can submit these as Issues here.


Contributing
------------
Contributions of all sorts are welcome. To manage contributions, we use the merge request functionality of gitlab. In to gain access to gitlab and create a merge request, you will first need to perform the following steps:

* Create an account on [gitlab](https://gitlab.com/).

Once you have performed these steps you can clone the project, push your code changes, and then submit it for review.

Requirement for compilation & usage is Java 8, or maybe even Java 11 but prefere to keep it at the same (11)


Code Requirements
-----------------
* For the most part, CraftBukkit and Bukkit use the [Sun/Oracle coding standards](http://www.oracle.com/technetwork/java/javase/documentation/codeconvtoc-136057.html).
* Use 4 spaces or define tab as so.
    * Empty lines should contain no spaces.
* No trailing whitespaces.
* No 80 character column limit, or 'weird' mid-statement newlines unless absolutely necessary.
    * The 80 character column limit still applies to documentation.
* No one-line methods.
* All major additions should have documentation.
* Try to follow test driven development where available.
* All code should be free of magic values. If this is not possible, it should be marked with a TODO comment indicating it should be addressed in the future.
  * If magic values are absolutely necessary for your change, what those values represent should be documented in the code as well as an explanation in the Merge Request description on why those values are necessary.
* No unnecessary code changes. Look through all your changes before you submit it.
* Do not attempt to fix multiple problems with a single patch or merge request.
* Avoid moving or renaming classes.
* All non-private methods and constructors must have specified nullability through [annotations](https://github.com/JetBrains/java-annotations)
* There needs to be a new line at the end of every file.
* Imports should be organised in a logical manner.
    * Do not group packages
    * __Absolutely no wildcard imports outside of tests.__

Bukkit is a large project and what seems simple to a MR author at the time of writing may easily be overlooked by other authors and updates. 
Including unit tests with your MR will help to ensure the MR can be easily maintained over time and encourage the Spigot team to merge the MR because less or no testing is needed.
Any questions about these requirements can be asked via contact.



Plans
- [x] change naming to "RPGClasses"
- [ ] change ingame commands
- [ ] add info command for classes
- [ ] add permissions update
- [ ] add new classes
- [ ] ....