# Final Project of Software Engineering [GC26].

Lorenzo Prada       10529212        869775

Federico Romeo      10566536        890975 

Riccardo Pozzan     10581128         891097


# Santorini
Santorini is a simple table-game playable in 2 or 3 players.
Choose your God Card, place your tokens onto the battlefield and get ready to climb the 3rd level before your enemies even notice it.


## Introduction
This project consists in the implementation of a distribuited system.
It is entirely implemented using the MVC pattern.




FEATURES | done? |
--- | --- |
Complete rules | ![#c5f015](https://via.placeholder.com/15/c5f015/000000?text=+) |
CLI | ![#c5f015](https://via.placeholder.com/15/c5f015/000000?text=+) | --- |
GUI | ![#c5f015](https://via.placeholder.com/15/c5f015/000000?text=+) | --- |
Socket | ![#c5f015](https://via.placeholder.com/15/c5f015/000000?text=+) | --- |
1 Advanced feature | ![#c5f015](https://via.placeholder.com/15/c5f015/000000?text=+)  | --- |
2 Advanced feature | ![#f03c15](https://via.placeholder.com/15/f03c15/000000?text=+)  | --- |

Advanced feature: ADVANCED GODS
  + Chronus;
  + Hera;
  + Hestia;
  + Limus;
  + Zeus.

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
Inside the JAR folder there will be 3 different .jar files.
Each one have to be launched by open a terminal and type the corresponding line of code.

###### Server.jar 

It is the server that hosts the game. It can be runned passing the argument -port. If the port is not specified, it uses 12345.

```
   java -jar path/to/Server.jar -port MY_PORT
```

###### ClientCLI.jar

It is the client that runs a terminal to play. It is higly recommended not to use cmd. 
It can be runned passing the IPv4 address and the port where the server is listening. If those are not specified it uses 127.0.0.1 as IP, and 12345 as port.

```
java -jar path/to/ClientCLI.jar -ip MY_IP_ADDRESS -port MY_PORT
```

###### ClientGUI.jar 

It is the client to use a graphic interface. 
It can be runned passing the IPv4 address and the port where the server is listening. If those are not specified it uses 127.0.0.1 as IP, and 12345 as port.

```
   java -jar path/to/ClientGUI.jar -ip MY_IP_ADDRESS -port MY_PORT
```

#### PRESENTATION
[This](https://github.com/MrPratula/ing-sw-2020-romeo-pozzan-prada/tree/recover/deliveries/slide) is a brief presentation we used to explain how our project works during the project exposition.




###### The GUI will look like this:

![alt text](https://github.com/MrPratula/ing-sw-2020-romeo-pozzan-prada/blob/superbranch/src/main/resources/gui.png?raw=true)


###### The CLI will look like this:

![alt text](https://github.com/MrPratula/ing-sw-2020-romeo-pozzan-prada/blob/superbranch/src/main/resources/CLI.png?raw=true)