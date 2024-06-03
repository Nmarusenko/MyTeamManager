# MyTeamManager
## A passion project by Noah

My Team Manager is an application where you
can keep track of information surrounding your
soccer team. This project will provide the 
functionality of storing information about a team 
that can be accessed as statistics. For example seeing
the top goalscorer on your team. This application
will be used by coaches and managers of soccer teams
who want to keep track of their teams performance
and access statistics in order to make decisions about 
the team. I am interested in this project because as
someone who plays in a soccer league I would like a 
software to manage all of our past games.

## User Stories
- As a user I want to be able to log games to a team
- As a user I want to be able to create players for my team
- As a user I want to be able to see a list of all players on a team
- As a user I want to be able to see all the games my team has played
- As a user I want to be able to view average game ratings from players on my team
- As a user I want to be able to remove players from a team
- As a user I want to be able to filter the players on my team by their rating and goals
- As a user I want to be able to view how many goals each player on my team has
- As a user I want to be able to save my team to file
- As a user I want to be able to have the option to load up a team from file



#### Possible Improvements: 
The TeamManagerGUI class has low cohesion and as it is doing many things at the same time. 
To refactor this I want to make several new classes. One of them being a panel with all the functional
buttons being located on it. I could then remove most of the subclasses inside TeamManagerGUI,
and it would be a lot cleaner. I would also certainly have another class creating the prompt
panels for the user. For this project I needed to create different prompts with different numbers of inputs 
for the user and these should've been in their own class and not in the same cluttered class as everything else. 
Splitting up TeamMangerGUI into at least 2 other classes would've increased the cohesion and made
for a better design of this project. 

I would do would be to make this project more robust. By this I mean
that there are currently some scenarios the user can get some exceptions thrown at them if they do not use the app as 
designed. For example if you leave the player number blank when adding a player, a null pointer exception will occur. 
Something I could add would be some catch blocks that lead to immediate error messages and the opportunity to prompt
the user for correct input. 
