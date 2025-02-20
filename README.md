# Project #: Project Name

* Author: Parker Smith, Sabastian Leeper
* Class: CS361 Section #1
* Semester: Spring 2025
## Overview
This project is a Java implementation of a **Deterministic Finite Automaton (DFA)** for CS 361. The project models DFA behavior, including state transitions, string acceptance, and symbol swapping.

## Features
- Implements `DFA.java` following the `DFAInterface.java` specification.
- Uses `DFAState.java` to represent DFA states.
- Supports adding states, setting start and final states, and defining transitions.
- Implements `swap(char symb1, char symb2)` to swap transition labels.
- Provides a `toString()` method for DFA representation.
- Includes JUnit 5 test cases in `DFATest.java`.


## Reflection

Throughout this project, I believe the greatest feat for our group was simply the communication we had with eachother. It felt like we both understood when we could work on the project, how we should do it, and when we had any concerns we had consistent conversation. More concretely, I feel like understanding the theory and setup of the project went great -- we understood how we needed to setup the project, which Set and Map to use (hash), how we needed to implement the variables, and overall just had a strong sense of direction the whole project. This extended to our documentation as well - via the method commenting, our aforementioned communication, and setup, looking at the code itself felt easy and understandable, even for content I didn't write or  hadn't seen yet.

However, while the writing of the DFA and DFAState files went well, I'm still a bit hazy on how to comprehensively test such a large undertaking that DFA's have. It feels like there are plenty of potential outliers to cover for this topic, so personally, it is a bit stressful to not have a fully concrete list of tests from the start. I feel like this is the point I would tell myself to focus on more -- focus on implementing as many tests as possible, so I know the end goal, before finishing DFA and DFAState.
 
 
## Running Tests
To run the JUnit tests:
"mvn test"

Expected output:

[INFO] Running test.dfa.DFATest
[INFO] Tests run: X, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS

## Running Main.java
Open the terminal
Run the following command to make sure everything is good to go: mvn clean install
Run: mvn exec:java -Dexec.mainClass="Main"



## Usage
The DFA implementation supports the following operations:
- **Add states:** `dfa.addState("a");`
- **Define alphabet:** `dfa.addSigma('0');`
- **Set start/final states:** `dfa.setStart("a"); dfa.setFinal("b");`
- **Add transitions:** `dfa.addTransition("a", "b", '1');`
- **Check string acceptance:** `dfa.accepts("101");`
- **Swap transition symbols:** `dfa.swap('0', '1');`


## Sources used

 The only thing I can think of is that I used w3Schools and Geeks4Geeks to brush up on HashMaps/HashSets oncemore. Nothing too much, just needed to revisit the concepts.

 https://www.w3schools.com/java/java_hashmap.asp
 https://www.geeksforgeeks.org/java-util-hashmap-in-java-with-examples/
https://www.w3schools.com/java/java_hashset.asp
https://www.geeksforgeeks.org/hashset-in-java/


