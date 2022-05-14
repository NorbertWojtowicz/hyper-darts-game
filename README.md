# hyper-darts-game
Spring boot service application made for centralized scoring and statistics, that can track dart throws for players.

There is also a cool feature to roll the game back to its previous states.


# REST API Endpoints
|       METHOD         |URL                          |DESCRIPTION                       |SCOPE AND ROLE|
|----------------|-------------------------------|-----------------------------|-----------------------------|
|POST|`api/game/create`|Creates a new game.|WRITE AND GAMER|
|GET|`api/game/list`|To get the list of current games on the server.|READ AND GAMER|
|GET|`api/game/join/{game_id}`|To join to a game.|READ AND GAMER|
|GET|`api/game/status`|To get the game status.|READ AND GAMER|
|POST|`api/game/throws`|To send the information about the player's throws.|WRITE AND GAMER|
|PUT|`api/game/cancel`|To close the game (for example if one of the participants left the tournament due to any reason).|UPDATE AND REFEREE|
|GET|`api/history/{game_id}`|To get moves history of certain game.|READ|
|PUT|`api/game/revert`|Roll back the current status of the game to one of the states available in the game history.|UPDATE AND REFEREE|

# Security
Project imitate OAuth mechanism. I didnt use real accounts of an authorization provider but I implemented provider by myself.
Authorization server is just an emulator. 
I used *Password flow* schema where the user provides the application with a user name and password that can be used to access the user's data. 
Following this, the client will directly contact the provider API to request an access token.
## Schema of OAuth
![Password flow schema](https://i.imgur.com/KPAcOVE.jpg)
## Scopes and roles
Scopes:
- Read
- Update
- Write

Roles:
- Gamer
- Referee
