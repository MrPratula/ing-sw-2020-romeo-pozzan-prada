# Santorini
Final Project of Software Engineering [GC26].

Santorini is a simple table-game playable in 2 or 3 players.
Choose your God Card, place your tokens onto the battlefield and get ready to climb the 3rd level before your enemies even notice it.


## Introduction
This project consists in the implementation of a distribuited system.
It is entirely implemented using the MVC pattern.

## Features
- Complete rules;
- Command Line Interface;
- Graphic User Interface with Java Swing;
- Socket TCP network;
- Advanced features: 5 Advanced Gods.

## Deliveries
Inside this folder you can find the following things:
- UML diagrams;
- executables JAR;
- Presentation.

### UML
Part of this project are also UML diagrams.

[Here](https://github.com/MrPratula/ing-sw-2020-romeo-pozzan-prada/blob/recover/deliveries/uml/uml_mvc.jpg) you can find an high level diagram to show links between classes, and how the MVC pattern works, in order to pass informations beetween server and client.
[here](https://github.com/MrPratula/ing-sw-2020-romeo-pozzan-prada/blob/recover/deliveries/uml/uml_full.jpg) a low level UML diagram to show more accurately all classes details.

### JAR
Inside the JAR folder there are 3 different .jar files:
```
- Server.jar 

It is the server that hosts the game. It can be runned in different ways:

-1: it will be runned on default port 12345:
```
java -jar path/to/Server.jar
```

-2: it will be runned port MY_PORT:
```
java -jar path/to/Server.jar -port MY_PORT
```

- ClientCLI.jar

It is the client that runs a terminal to play. It is higly recommended not to use cmd. It can be runned in different ways:

-1: it will be runned on localHost and on default port 12345:
```
java -jar path/to/ClientCLI.jar
```

-2: it will be runned on address MY_IP_ADDRESS and on port MY_PORT:
```
java -jar path/to/ClientCLI.jar -ip MY_IP_ADDRESS -port MY_PORT
```

- ClientGUI.jar 

It is the client to use a graphic interface. 
It can be run by double clicking on it or exactly as the ClientCLI.jar in the terminal or in different ways:

-2: it will be runned on localHost and on default port 12345:
```
java -jar path/to/ClientGUI.jar
```

2: it will be runned on address MY_IP_ADDRESS and on port MY_PORT:
```
java -jar path/to/ClientGUI.jar -ip MY_IP_ADDRESS -port MY_PORT
```

###### PRESENTATION
[This](https://github.com/MrPratula/ing-sw-2020-romeo-pozzan-prada/tree/recover/deliveries/slide) is a brief presentation we used to explain how our project works during the project exposition.
