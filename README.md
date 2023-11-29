# My Team Manager
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

# Instructions for Grader

- You can generate the first required action related to the user story "adding multiple Xs to a Y" by using the button "add player"
and filling out the required fields. 
- You can generate the second required action related to the user story "adding multiple Xs to a Y" by clicking either of the two filter buttons by goals or by rating. 
- You can locate my visual component by looking to the bottom of the panel
- You can save the state of my application by clicking the save to file button
- You can reload the state of my application by clicking the load to file button. 

### Phase 4: Task 2
If a user were to open this application, they would probably start by setting a team name. Then they would add a couple players,
maybe remove one, then add a game rating to these players. The user can now do player filtering such as filter by rating and filter by goals.
Once the user closes the application, this is what the print to the console should look like: 

Tue Nov 28 18:48:17 PST 2023 <br>
Team name set to: Rangers


Tue Nov 28 18:48:24 PST 2023 <br>
Successfully added new player: Noah (8) to Rangers


Tue Nov 28 18:48:30 PST 2023 <br>
Successfully added new player: Aiden (10) to Rangers


Tue Nov 28 18:48:38 PST 2023 <br>
Removed Aiden from Rangers


Tue Nov 28 18:49:00 PST 2023 <br>
Rating of 8.5 added for Noah


Tue Nov 28 18:49:07 PST 2023 <br>
Viewed: filtered players by 0.0 rating


Tue Nov 28 18:49:10 PST 2023 <br>
Viewed: filtered players by 0 goals

### Phase 4: Task 3
#### Note to grader:
There was confusion on piazza about what to do with the UML diagram
when you have a class inside a class. The consensus was to go with
a line leading to a circle with a cross in it. You can see this happen many times
with classes being inside TeamManagerGUI. 

#### Possible Improvements: 
The note to grader above leads into what I could've done better with the
design of this project. My TeamManagerGUI class is way too cluttered and does not follow the
single responsibility principle. That class has very low cohesion and as it is doing many things at the same time. 
To refactor this I would make several new classes. One of them being a panel with all the functional
buttons being located on it. I could then remove most of the subclasses inside TeamManagerGUI,
and it would be a lot cleaner. I would also certainly have another class creating the prompt
panels for the user. For this project I needed to create different prompts with different numbers of inputs 
for the user and these should've been in their own class and not in the same cluttered class as everything else. 
Splitting up TeamMangerGUI into at least 2 other classes would've increased the cohesion and made
for a better design of this project. 

If I had more time on this project, something I would do would be to make it more user-friendly. By this I mean
that there are currently some scenarios the user can get some exceptions thrown at them if they do not use the app as 
designed. For example if you leave the player number blank when adding a player, a null pointer exception will occur. 
Something I could add would be some catch blocks that lead to immediate error messages and the opportunity to prompt
the user for correct input. 