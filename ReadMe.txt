My ex3 algoritm explaind:
firsly my algorithm will make a copy of the bord by getting the map from the game and creating a map with a constructor.
then my algroithm will creat a ghost array that determined by yhe ghosts that are given in the map. after the algorithm will determin the current location of the packman in the ,ap by spliting the getPos methodwith "," and set the curren x and y values with the array that we get from the split method. after will set a pixel with pixel cunstructor with the curren x and y values. then will detrmin the obs pixeles to be the blue one. after we got all the things that i mentiond above, we can creat the all distance map as was build in the first part of ex3. after my algoritm use a function that will find the closest pink pixel in the map. the target of the packman will be to get to the closest pink pixel if there is no ghost that is near to him and if the ghosts arent eatable.
after we got all the things that is mentioned above the pack man can start the algorithem:
we will split to 2 diffrent senarios: 
if the map is cyclic:
will check if one of thw ghost is eatable and then will chease to eat the ghost. if there is no eatable ghost will go the the closet pink point.
if the map is not cyclic the packman will try to not get caught by a ghost and will allways try to get to the closest pink point.if the ghost is eatble the pack man will try to chase after the ghost. 
my algorithem uses a few auxiliry functions:
1.minPink- will find the pink dot that is most close to the packman.
2.directionCyclic- will termine where the packman shpuld go in a cyclic map(just will detrmine the directiwon without care about ghost and pink points)
3.directionNotCyclic-will termine where the packman shpuld go in a non cyclic map(just will detrmine the direction without care about ghost and pink points)
4.ghostPixel- will set the ghosts pixeles, will get an input of int i that will run therew every ghost at my main algorithm.
5.ghostIsValid-will check if a ghost is eatable by checking if the ghost is 5 steps from the packman and if the reaminig time to eat the ghost is less then one second.
6.ghostIsTarget- will set the target of the packman to be the ghost if the ghost is vaild. will return the diraction the the pack man shpuld go to chase the ghost.
7.ghostIsNear - will check if one of the ghosts is near to the packman.
8.dirIfGhostIsNear- will determine the diraction that the pack man should go if there is a ghost near to him.