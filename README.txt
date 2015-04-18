Submitted by

Bharath Nagananda 			    009991917			bharath.nag.gowda@gmail.com

Steps to Run

1. goto /PremierLeague in the command prompt
2. compile the project using the command 
       mvn package
3. Copy the "RestPersistence.war" file from the target folder of the Project
4. Paste the "RestPersistence.war" file into the webapps folder of apache-tomcat
5. We are using "Postman-Rest client" while working with APIs
6. We have handled all the requests and responses which are as follows:
 				1)Adding a sponsor with valid data
 				2)Adding a sponsor without name – a required parameter
 				3)Getting a sponsor, which does not exist
 				4)Getting a sponsor
				5)Updating a sponsor without the required parameter
				6)Updating a sponsor
				7)Adding a player without required parameter
				8)Adding a player with valid data
				9)Adding a player with invalid sponsor details
				10)Adding a player with existing sponsor
				11)Updating the player details
				12)Adding opponents for invalid player IDs
				13)Adding opponents for existing players
				14)Getting the player with all details
				15)Another GET of different player
				16)Updating the player
				17)Deleting a player
				18)Deleting a player with invalid ID
				19)Data before deleting the opponents
				20)Deleting the Opponents
				21)Data after deleting opponents
				22)Deleting a player
				23)Deleting a player with invalid ID
				24)Deleting a sponsor that is linked to a player
				25)Deleting an invalid sponsor
				26)Deleting a sponsor
 				
7. The Screenshots of all the outputs is present in the Screenshots folder.

       
