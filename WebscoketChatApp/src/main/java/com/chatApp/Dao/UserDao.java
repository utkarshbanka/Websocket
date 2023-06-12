package com.chatApp.Dao;

import java.util.HashMap;
import java.util.List;

import com.chatApp.Model.Register;

public interface UserDao {

	
	
	public  String Registeruser(Register register);
	
	
	public String checkUser(String email, String password);
	
	
	public List<String> getuserdata(String username);
	
	
	public HashMap<List<String>,List<String>> getMessages(String from ,String to);
	
	
	public String savemessage(String fromt ,String too, String message);
	
	
}
