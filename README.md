# OOP-EX3 Packman
In this project we made our one version for "packman" game. the red car should take as many hitchhikers as she can (there isn't 
limit of seats) so that she will get the largest score in the game.

Int this project we used diercted illustrated graphs ,every node_data in the graph is represent a datum-point in Ariel (we can see 
the point in 'Google Earth' api) .We used 23 scenario as a game level for the user, the user can change them manualy and decide if to use
manual or automatic game. In every scenarion there is a diffrent num of cars, and diffrend amount of hitchhikers . Every game is 60 sec.

In the manual game the user will click on the red car and will move her to the next point by his choice by clicking the next point. 
Int the automatic game we made the efficient game so that the user will get the largest amount of points by Collect hitchhikers in the way.

### The automatic game:
The automatic game is assisted by 2 function that we made in the 'OOP_Ex2' project.
1.'shortestPathDist' function- gave as the length of the shortest path  between 2 points 
2. 'shortestPath' function  - gave as the path of the shortest path between 2 points 

In this function we check for every car who is the closest hitchhiker and direct the car to him, every car has her owm path to her 
destination and when she will get to him the function will calculate for her the next hitchhiker she will take. The function will
continue to calculate path for the cars until the game is over.

### The maual game:
Ths manual game is choosen by the user choice, by clicking on the screen. The user is asked to click first on the red car he wants to move 
and only after click on the point he want the car to go to .
* Notice: the user can click only on the next edges.

### Code information:
In this project we used few class, we will explain them here to more information go to section 'code' . 
* Fruits -this interface and class that are implements him represents the hitchhikers.
* Robots -this interface and the class that are implements him represents the cars. 
* KML_logger- this class is made for the kml file (that we will able to see the game in real time in 'Google Earth'.
* ThreadGame- this class contains the thread that we used for the game. 
* SimpleGame- test and a simple game 
* MyGameGui - this class conatins all the gui function that we made so the user can see in on the screen . we used StdDrawGame in 
the util pack for all the gui. 

For more information please check our Wiki !!
