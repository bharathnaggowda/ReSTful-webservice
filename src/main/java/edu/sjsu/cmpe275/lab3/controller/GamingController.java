package edu.sjsu.cmpe275.lab3.controller;

import java.io.IOException;
import java.util.Random;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import edu.sjsu.cmpe275.lab3.config.SpringMongoConfig;
import edu.sjsu.cmpe275.lab3.model.Player;
import edu.sjsu.cmpe275.lab3.model.Sponsor;

import javax.validation.Valid;

@Controller
public class GamingController {
	
	SpringMongoConfig mongoConfig=new SpringMongoConfig();
	MongoClient mongoClient=mongoConfig.getMongoClient();
	DB db =mongoClient.getDB("gaming");
	DBCollection playerCollection = db.getCollection("Player");
	DBCollection sponsorCollection = db.getCollection("Sponsor");
	    
    JSONParser jsonParser = new JSONParser();
    JSONObject jsonObject = null;
    ObjectMapper mapper = new ObjectMapper();
    String result = "";
    Random randomGenerator = new Random();

    /**
     * @param sponsor It is an instance of class Sponsor whose details have to be posted
     * @param bs It is an instance of class BindingResult which is used for validation
     * @param street Attribute of the class Address which is not a required field, hence required=false
     * @param city Attribute of the class Address which is not a required field, hence required=false
     * @param state Attribute of the class Address which is not a required field, hence required=false
     * @param zip Attribute of the class Address which is not a required field, hence required=false
     * @return It returns the newly created sponsor object in JSON in its HTTP payload, including all attributes
     * Id for the sponsor is randomly generating
     * HTTP status code will be 200 if the request is valid and response is successful  
     * HTTP status code will be 400 if the request is invalid   
     */
    @RequestMapping(value = "/sponsor", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseEntity<String> addSponsor(@Valid @ModelAttribute("sponsor") Sponsor sponsor, BindingResult bs,
    		@RequestParam(value="street", required=false) String street,
    		@RequestParam(value="city", required=false) String city,
    		@RequestParam(value="state", required=false) String state,
    		@RequestParam(value="zip", required=false) String zip
    		){
    	
    	if (bs.hasErrors()) {
			return new ResponseEntity<String>("Name of the sponsor is required", HttpStatus.BAD_REQUEST);
		} else {
    	
	    	int id = randomGenerator.nextInt(999999);
	    	
	    	BasicDBObject document = new BasicDBObject();
	    	if(sponsor.getName() != null) document.put("name", sponsor.getName());
	    	if(sponsor.getDescription() != null) document.put("description", sponsor.getDescription());
	    	
	    	document.put("_id", id);
	    	sponsor.setId(id);
	    	
	    		BasicDBObject documentDetail = new BasicDBObject();
	        	if(street != null) documentDetail.put("street", street);
	        	if(city != null) documentDetail.put("city", city);
	        	if(state != null) documentDetail.put("state", state);
	        	if(zip != null) documentDetail.put("zip", zip);
	        	document.put("address", documentDetail);
	    	
	    	
	    	sponsorCollection.insert(document);
	    	
	    	BasicDBObject searchQuery=new BasicDBObject();
	        searchQuery.put("_id",id);
	        DBCursor cursor=sponsorCollection.find(searchQuery);
	        
	        while(cursor.hasNext())
	        {
	        	DBObject theUserObj=cursor.next();
	        	result = writeToSTring(theUserObj);
	        }
	    	
	        return new ResponseEntity<String>(result, HttpStatus.OK);
		}
    }
    

/**
 * @param id Attribute of the class Sponsor whose full details have to be retrieved including the address
 * @param sponsor It is an instance of class Sponsor whose details have to be retrieved 
 * @return It returns a full sponsor object in JSON in its HTTP payload of the sponsor whose Id is passed in the URL as Path variable. 
 * If the sponsor of the given ID does not exist, the HTTP return code will be 404; 
 * otherwise, 200.
 */
@RequestMapping(value="/sponsor/{id}", method = RequestMethod.GET)
@ResponseBody
public ResponseEntity<String> createSponsor(@PathVariable long id, Sponsor sponsor) {
	String result = null;
	BasicDBObject searchQuery=new BasicDBObject();
    searchQuery.put("_id",id);
    DBCursor cursor=sponsorCollection.find(searchQuery);
    
    if(cursor == null || !cursor.hasNext()) return new ResponseEntity<String>("Requested Sponsor does not exist", HttpStatus.NOT_FOUND);
    		
    while(cursor.hasNext())
    {
    	DBObject theUserObj=cursor.next();
    	result = writeToSTring(theUserObj);
    }
	return new ResponseEntity<String>(result, HttpStatus.OK);
}

/**
 * @param id Attribute of the class Sponsor whose full details have to be updated including the address
 * @param sponsor It is an instance of class Sponsor whose details have to be posted
 * @param bs It is an instance of class BindingResult which is used for validation
 * @param street Attribute of the class Address
 * @param city Attribute of the class Address 
 * @param state Attribute of the class Address
 * @param zip Attribute of the class Address
 * @return returns updated sponsor object, including all attributes in JSON
 */
@RequestMapping(value = "/sponsor/{id}", method = RequestMethod.POST)
@ResponseBody
public ResponseEntity<String> updateSponsor(@PathVariable long id, @Valid @ModelAttribute("sponsor") Sponsor sponsor, BindingResult bs,
		@RequestParam(value="street", required=false) String street,
		@RequestParam(value="city", required=false) String city,
		@RequestParam(value="state", required=false) String state,
		@RequestParam(value="zip", required=false) String zip
		){
	
	String result = null;
	if (bs.hasErrors()) {
		return new ResponseEntity<String>("Name of the sponsor is required", HttpStatus.BAD_REQUEST);
	}
	else {
	BasicDBObject searchQuery=new BasicDBObject();
    searchQuery.put("_id",id);
    DBCursor cursor=sponsorCollection.find(searchQuery);
    
    if(cursor == null || !cursor.hasNext()) return new ResponseEntity<String>("Requested Sponsor does not exist", HttpStatus.NOT_FOUND);
    
    
    
	BasicDBObject document = new BasicDBObject();
	 while(cursor.hasNext())
	    {
	    	DBObject theUserObj=cursor.next();
	        BasicDBObject theBasicUserObject= (BasicDBObject)theUserObj;
	        
	        sponsor.setId(theBasicUserObject.getLong("_id"));	   
			if(sponsor.getName() == null) sponsor.setName(theBasicUserObject.getString("name"));		
			if(sponsor.getDescription() == null) sponsor.setDescription(theBasicUserObject.getString("description"));
			
			BasicDBObject documentDetail = new BasicDBObject();
        	if(street != null) documentDetail.put("street", street);
        	if(city != null) documentDetail.put("city", city);
        	if(state != null) documentDetail.put("state", state);
        	if(zip != null) documentDetail.put("zip", zip);
        	document.put("address", documentDetail);
    	
    	
    	//sponsorCollection.insert(document);
			
	    	document.put("_id", sponsor.getId());
			document.put("name", sponsor.getName());
			document.put("description", sponsor.getDescription());
	    }	
	sponsorCollection.remove(searchQuery);
	sponsorCollection.insert(document);
	BasicDBObject Query=new BasicDBObject();
    searchQuery.put("_id",id);
    DBCursor cur=sponsorCollection.find(Query);
    
    while(cur.hasNext())
    {
    	DBObject theUserObj=cur.next();
    	result = writeToSTring(theUserObj);
    }
	
	//result = writeToSTring(sponsor);
	
    return new ResponseEntity<String>(result, HttpStatus.OK);
	}
}

/**
 * @param id Attribute of the class Sponsor whose full details have to be updated including the address
 * @param sponsor It is an instance of class Sponsor whose details have to be deleted
 * @param player It is an instance of class Player
 * @return It returns 400 if there is any player belonging to this sponsor
 * It returns 404 if the sponsor with the given ID does not exist
 * Returns HTTP code 200 and the deleted object in JSON if the object is deleted; 

 */
@RequestMapping(value="/sponsor/{id}", method = RequestMethod.DELETE)
@ResponseBody
public ResponseEntity<String> deleteSponsor(@PathVariable long id, Sponsor sponsor, Player player) {
	String result = null;
	BasicDBObject searchQuery=new BasicDBObject();
    searchQuery.put("_id",id);
    DBCursor cursor=sponsorCollection.find(searchQuery);
    
    if(cursor == null || !cursor.hasNext()) return new ResponseEntity<String>("Requested Sponsor does not exist", HttpStatus.NOT_FOUND);
    
    DBCursor cursorS=playerCollection.find();
    while(cursorS.hasNext())
    {
    	DBObject theUserObj=cursorS.next();
        BasicDBObject theBasicUserObject= (BasicDBObject)theUserObj;
        Object sp = theBasicUserObject.get("sponsor");
        
        if(sp.toString().contains(id+"")) return new ResponseEntity<String>("Requested Sponsor is linked to an existing Player", HttpStatus.NOT_FOUND);
    }
    
    while(cursor.hasNext())
    {
    	DBObject theUserObj=cursor.next();
        result = writeToSTring(theUserObj);
    }
    
    sponsorCollection.remove(searchQuery);
	return new ResponseEntity<String>(result, HttpStatus.OK);
}

private String writeToSTring(DBObject theUserObj) {
	
	try {
		result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(theUserObj);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
}

/**
 * @param player It is an instance of class Player whose details have to be posted
 * @param bs It is an instance of class BindingResult which is used for validation
 * @param street Attribute of the class Address which is not a required field, hence required=false
 * @param city Attribute of the class Address which is not a required field, hence required=false
 * @param state Attribute of the class Address which is not a required field, hence required=false
 * @param zip Attribute of the class Address which is not a required field, hence required=false
 * @return returns the newly created player object in JSON in its HTTP payload, including all attributes.
 * HTTP status code will be 200 if the request is valid and response is successful  
 * HTTP status code will be 400 if the request is invalid   
 */
@RequestMapping(value = "/player", method = RequestMethod.POST)
@ResponseBody
public ResponseEntity<String> addPlayer(@Valid @ModelAttribute("player") Player player, BindingResult bs,
		@RequestParam(value="street", required=false) String street,
		@RequestParam(value="city", required=false) String city,
		@RequestParam(value="state", required=false) String state,
		@RequestParam(value="zip", required=false) String zip
		){
	if (bs.hasErrors()) {
		return new ResponseEntity<String>("firstname, lastname and email of the player is required", HttpStatus.BAD_REQUEST);
	}
		else {
			
		int id = randomGenerator.nextInt(999999);
		BasicDBObject document = new BasicDBObject();
		
			if(player.getSponsor() != 0){
				
				BasicDBObject searchQuery=new BasicDBObject();
				searchQuery.put("_id",player.getSponsor());
				DBCursor cursor=sponsorCollection.find(searchQuery);
				
				if(cursor == null || !cursor.hasNext()) return new ResponseEntity<String>("Requested sponsor does not exist", HttpStatus.NOT_FOUND);
				else {
					BasicDBObject sponsorDetail = new BasicDBObject();
					while(cursor.hasNext())
				    {
				    	DBObject theUserObj=cursor.next();
				        BasicDBObject theBasicUserObject= (BasicDBObject)theUserObj;
				        
						sponsorDetail.put("id", theBasicUserObject.getString("_id"));
						sponsorDetail.put("name", theBasicUserObject.getString("name"));
						sponsorDetail.put("description", theBasicUserObject.getString("description"));
						sponsorDetail.put("address", theBasicUserObject.getString("address"));
						document.put("sponsor", sponsorDetail);		
					
				}
				
				}
			    
			}
			if(player.getFirstname()!=null) document.put("firstname", player.getFirstname());
			if(player.getLastname()!=null) document.put("lastname", player.getLastname());
			if(player.getEmail()!=null) document.put("email", player.getEmail());
			if(player.getDescription() != null) document.put("description", player.getDescription());
			
			BasicDBObject documentDetail = new BasicDBObject();
        	if(street != null) documentDetail.put("street", street);
        	if(city != null) documentDetail.put("city", city);
        	if(state != null) documentDetail.put("state", state);
        	if(zip != null) documentDetail.put("zip", zip);
        	document.put("address", documentDetail);
			
			document.put("_id", id);
			player.setId(id);
		
			playerCollection.insert(document);
				
			BasicDBObject Query=new BasicDBObject();
		    Query.put("_id",id);
		    DBCursor cur=playerCollection.find(Query);
		    
		    while(cur.hasNext())
		    {
		    	DBObject theUserObj=cur.next();
		    	result = writeToSTring(theUserObj);
		    }
				
		return new ResponseEntity<String>(result, HttpStatus.OK);
		}
	
}


/**
 * @param id Attribute of the class Player whose full details have to be retrieved including the address
 * @param player It is an instance of class Player whose details have to be retrieved 
 * @return It returns a full player object in JSON in its HTTP payload of the player whose Id is passed in the URL as Path variable. 
 * If the player of the given ID does not exist, the HTTP return code will be 404; 
 * otherwise, 200.
 */
@RequestMapping(value="/player/{id}", method = RequestMethod.GET)
@ResponseBody
	public ResponseEntity<String> createPlayer(@PathVariable long id, Player player) {
	String result = null;
	BasicDBObject searchQuery=new BasicDBObject();
	searchQuery.put("_id",id);
	DBCursor cursor=playerCollection.find(searchQuery);
	
	if(cursor == null || !cursor.hasNext()) return new ResponseEntity<String>("Requested player does not exist", HttpStatus.NOT_FOUND);
			
	while(cursor.hasNext())
	{
		DBObject theUserObj=cursor.next();
		result = writeToSTring(theUserObj);
	}
	return new ResponseEntity<String>(result, HttpStatus.OK);
}

/**
 * @param id Attribute of the class Player whose full details have to be updated including the address
 * @param player It is an instance of class Player whose details have to be posted
 * @param bs It is an instance of class BindingResult which is used for validation
 * @param street Attribute of the class Address
 * @param city Attribute of the class Address 
 * @param state Attribute of the class Address
 * @param zip Attribute of the class Address
 * @return returns updated player object, including all attributes in JSON
*/
@RequestMapping(value = "/player/{id}", method = RequestMethod.POST)
@ResponseBody
public ResponseEntity<String> updatePlayer(@PathVariable long id, @Valid @ModelAttribute("player") Player player, BindingResult bs,
		@RequestParam(value="street", required=false) String street,
		@RequestParam(value="city", required=false) String city,
		@RequestParam(value="state", required=false) String state,
		@RequestParam(value="zip", required=false) String zip
		){

	BasicDBObject document = new BasicDBObject();
	String result = null;
	if (bs.hasErrors()) {
		return new ResponseEntity<String>("firstname, lastname and email of the player is required", HttpStatus.BAD_REQUEST);
	}
	else {
	
	BasicDBObject searchPlayer=new BasicDBObject();
	searchPlayer.put("_id", id);
	DBCursor cursorP = playerCollection.find(searchPlayer);
	
	if(cursorP == null || !cursorP.hasNext()) return new ResponseEntity<String>("Requested Player does not exist", HttpStatus.NOT_FOUND);
	
	if(player.getSponsor() != 0){
		
		BasicDBObject searchQuery=new BasicDBObject();
		searchQuery.put("_id",player.getSponsor());
		DBCursor cursor=sponsorCollection.find(searchQuery);
		
		if(cursor == null || !cursor.hasNext()) return new ResponseEntity<String>("Requested Sponsor does not exist", HttpStatus.NOT_FOUND);
		else {
			BasicDBObject sponsorDetail = new BasicDBObject();
			while(cursor.hasNext())
		    {
		    	DBObject theUserObj=cursor.next();
		        BasicDBObject theBasicUserObject= (BasicDBObject)theUserObj;
		        
				sponsorDetail.put("id", theBasicUserObject.getString("_id"));
				sponsorDetail.put("name", theBasicUserObject.getString("name"));
				sponsorDetail.put("description", theBasicUserObject.getString("description"));
				sponsorDetail.put("address", theBasicUserObject.getString("address"));
				
		    }
			document.put("sponsor", sponsorDetail);		
			
		}
	}
	while(cursorP.hasNext())
    {
		DBObject theUserObj=cursorP.next();
        BasicDBObject theBasicUserObject= (BasicDBObject)theUserObj;
        
        document.put("opponents", theBasicUserObject.getString("opponents"));
    }
	
	BasicDBObject documentDetail = new BasicDBObject();
	if(street != null) documentDetail.put("street", street);
	if(city != null) documentDetail.put("city", city);
	if(state != null) documentDetail.put("state", state);
	if(zip != null) documentDetail.put("zip", zip);
	document.put("address", documentDetail);
			
	if(player.getFirstname()!=null) document.put("firstname", player.getFirstname());
	if(player.getLastname()!=null) document.put("lastname", player.getLastname());
	if(player.getEmail()!=null) document.put("email", player.getEmail());
	if(player.getDescription() != null) document.put("description", player.getDescription());
	
	document.put("_id", id);
	player.setId(id);
	
	playerCollection.remove(searchPlayer);
	playerCollection.insert(document);
	
	BasicDBObject Query=new BasicDBObject();
    Query.put("_id",id);
    DBCursor cur=playerCollection.find(Query);
    
    while(cur.hasNext())
    {
    	DBObject theUserObj=cur.next();
    	result = writeToSTring(theUserObj);
    }
    return new ResponseEntity<String>(result, HttpStatus.OK);
	}

   }

/**
 * @param id Attribute of Player class and with the given ID it deletes the player object 
 * @return returns 404 If the player with the given ID does not exist
 * Otherwise, delete the player and remove any reference of this player from your persistence of opponent relations
 * and return HTTP status code 200 and the deleted player in JSON
 */
@RequestMapping(value="/player/{id}", method = RequestMethod.DELETE)
@ResponseBody
public ResponseEntity<String> deletePlayer(@PathVariable long id) {
String result = null;
BasicDBObject searchQuery=new BasicDBObject();
searchQuery.put("_id",id);
DBCursor cursor=playerCollection.find(searchQuery);

if(cursor == null || !cursor.hasNext()) return new ResponseEntity<String>("Requested Player does not exist", HttpStatus.NOT_FOUND);
		
while(cursor.hasNext())
{
	DBObject theUserObj=cursor.next();
    result = writeToSTring(theUserObj);
}

playerCollection.remove(searchQuery);
return new ResponseEntity<String>(result, HttpStatus.OK);
}

BasicDBList list;
BasicDBList list2;
/**
 * @param id1 Id of one player which is an attribute of class Player  
 * @param id2 Id of second player which is an attribute of class Player  
 * @param player It is an instance of class Player
 * @return It returns HTTP code 200 and any informative text message "Id1 and Id2 are opponents each other" in the HTTP payload.
 * It returns 404 if either player does not exist
 * If the two players are already opponents, do nothing, just return 200
 */
@RequestMapping(value="/opponents/{id1}/{id2}", method=RequestMethod.PUT)
@ResponseBody
public ResponseEntity<String> addOpponents(@PathVariable long id1, @PathVariable long id2, Player player){
	
	BasicDBObject searchQuery=new BasicDBObject();
	searchQuery.put("_id",id1);
	DBCursor cursor=playerCollection.find(searchQuery);

	if(cursor == null || !cursor.hasNext()) return new ResponseEntity<String>("Requested Player does not exist", HttpStatus.NOT_FOUND);
	
	BasicDBObject searchQuery2=new BasicDBObject();
	searchQuery2.put("_id",id2);
	DBCursor cursor2=playerCollection.find(searchQuery2);

	if(cursor2 == null || !cursor2.hasNext()) return new ResponseEntity<String>("Requested Player does not exist", HttpStatus.NOT_FOUND);
	
	BasicDBObject updateQuery=new BasicDBObject();
	BasicDBObject updateQuery2=new BasicDBObject();
	
	while(cursor.hasNext()) {
		list = (BasicDBList) cursor.next().get("opponents");
	}
	while(cursor2.hasNext()) {
		list2 = (BasicDBList) cursor2.next().get("opponents");
	}
	
	if(list != null){
		if(!list.contains(id2)) list.add(id2);
	}else{
		list = new BasicDBList();
		list.add(id2);
	}
	
	if(list2 != null){
		if(!list2.contains(id1)) list2.add(id1);
	}else{
		list2 = new BasicDBList();
		list2.add(id1);
	}
	
	
	updateQuery.append("$set", new BasicDBObject().append("opponents", list));
	updateQuery2.append("$set", new BasicDBObject().append("opponents", list2));
	
	playerCollection.update(searchQuery, updateQuery);
	playerCollection.update(searchQuery2, updateQuery2);
	
	return new ResponseEntity<String>(id1 + " " +"and"+" "+id2 +" " + "are made opponenets of each other", HttpStatus.OK);
}

/**
 * @param id1 Id of one player which is an attribute of class Player 
 * @param id2 Id of second player which is an attribute of class Player 
 * @param player It is an instance of class Player
 * @return Return HTTP code 200 and a meaningful text message if all is successful.Removes this opponent relation.
 * It returns 404 if either player does not exist
 * It returns 404 if the two players are not opponents
 */
@RequestMapping(value="/opponents/{id1}/{id2}", method=RequestMethod.DELETE)
@ResponseBody
public ResponseEntity<String> deleteOpponents(@PathVariable long id1, @PathVariable long id2, Player player){
	
	BasicDBObject searchQuery=new BasicDBObject();
	searchQuery.put("_id",id1);
	DBCursor cursor=playerCollection.find(searchQuery);

	if(cursor == null || !cursor.hasNext()) return new ResponseEntity<String>("Requested Player does not exist", HttpStatus.NOT_FOUND);
	
	BasicDBObject searchQuery2=new BasicDBObject();
	searchQuery2.put("_id",id2);
	DBCursor cursor2=playerCollection.find(searchQuery2);

	if(cursor2 == null || !cursor2.hasNext()) return new ResponseEntity<String>("Requested Player does not exist", HttpStatus.NOT_FOUND);
	
	BasicDBObject updateQuery=new BasicDBObject();
	BasicDBObject updateQuery2=new BasicDBObject();
	
	while(cursor.hasNext()) {
		list = (BasicDBList) cursor.next().get("opponents");
	}
	while(cursor2.hasNext()) {
		list2 = (BasicDBList) cursor2.next().get("opponents");
	}
	
	if(list.contains(id2)) list.remove(id2);
	if(list2.contains(id1)) list2.remove(id1);
	
	updateQuery.append("$set", new BasicDBObject().append("opponents", list));
	updateQuery2.append("$set", new BasicDBObject().append("opponents", list2));
	
	playerCollection.update(searchQuery, updateQuery);
	playerCollection.update(searchQuery2, updateQuery2);
	
	return new ResponseEntity<String>("opponent relation removed between"+" " +id1 + " " +"and"+" "+id2 , HttpStatus.OK);
}

}