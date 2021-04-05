package com.init.users.rest;

import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Hashtable;
import javax.naming.*;
import javax.naming.ldap.*;
import javax.naming.directory.*;

import com.init.users.MicroserviceUser1Application;
import com.init.users.dao.UsersDao;
import com.init.users.entity.User;

@RestController
@RequestMapping("/users")
public class UserRest {
	
	@Autowired
	private UsersDao userDAO;
	
	@GetMapping
	public ResponseEntity<List <User>> getUser() {		
		List<User> users = userDAO.findAll();
		return ResponseEntity.ok(users);		
	}

    @RequestMapping(value="{userId}")
    public ResponseEntity <User> getUserById(@PathVariable("userId") String userId){
        Optional<User> optionalUser = userDAO.findById(userId);
        if(optionalUser.isPresent()){
            return ResponseEntity.ok(optionalUser.get());
        }else{
            return ResponseEntity.noContent().build();
        }        
    }
    
    @PostMapping("/login")
    public ResponseEntity <User> Login(@RequestBody User user){
    	 Optional<User> optionalUser = userDAO.findById(user.getEmail());    	
    	 if(optionalUser.isPresent()){    		 
    		 if(optionalUser.get().getPassword().compareTo(user.getPassword())==0) {
    			 System.out.println("base de datos corrio");
            	 if(Ldap(user.getEmail(),user.getPassword())) {
    			 return ResponseEntity.ok(optionalUser.get());}else {
    				 return ResponseEntity.noContent().build();
    			 }
             }else {            	 
            	 return ResponseEntity.noContent().build();
             }
         }else{
             return ResponseEntity.noContent().build();
         } 
       
    }
  
    
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        User newUser = userDAO.save(user);
        return ResponseEntity.ok(newUser);
    }
    
    @DeleteMapping(value="{userId}")
    public String  deleteUser(@PathVariable("userId") String userId){
        userDAO.deleteById(userId);        
        return userId;
    }
    
    @PutMapping(value="{userId}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable("userId") String userId ){
        Optional<User> optionalUser = userDAO.findById(userId);
        if(optionalUser.isPresent()){
            User updateUser = optionalUser.get();
            updateUser.setName(user.getName());
            updateUser.setEmail(user.getEmail());
            updateUser.setPassword(user.getPassword());
            updateUser.setPuntaje(user.getPuntaje());
            userDAO.save(updateUser);
            return ResponseEntity.ok(updateUser);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    Boolean  Ldap(String userId,String password)
    {       Boolean bool=false;
            Hashtable<String, String> environment = new Hashtable<String, String>();
            String nombr = "cn="+userId+",ou=sa,dc=arqsoft,dc=unal,dc=edu,dc=co";

            environment.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            environment.put(Context.PROVIDER_URL, MicroserviceUser1Application.LdapUrl);
            environment.put(Context.SECURITY_AUTHENTICATION, "simple");
            environment.put(Context.SECURITY_PRINCIPAL, nombr);
            environment.put(Context.SECURITY_CREDENTIALS, password);

            try 
            {
                DirContext context = new InitialDirContext(environment);
                System.out.println("Connected..");
                bool=true;
                System.out.println(context.getEnvironment());
                context.close();
            } 
            catch (AuthenticationNotSupportedException exception) 
            {
                System.out.println("The authentication is not supported by the server");
            }

            catch (AuthenticationException exception)
            {
                System.out.println("Incorrect password or username");
            }

            catch (NamingException exception)
            {
                System.out.println("Error when trying to create the context");
            }
            return bool;
        
    }

}
