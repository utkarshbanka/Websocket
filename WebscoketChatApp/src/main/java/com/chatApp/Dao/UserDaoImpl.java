package com.chatApp.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.chatApp.Model.Register;
import com.chatApp.dbutil.Dbutil;

public class UserDaoImpl implements UserDao {

	@Override
	public String Registeruser(Register register) {

		String username = register.getEmail();
		String password = register.getPassword();

		String message = "Something went Wrong";

		try (Connection conn = Dbutil.provideconnection()) {
			PreparedStatement inse = conn.prepareStatement("insert into chatapp (username,password) values(?,?)");

			inse.setString(1, username);
			inse.setString(2, password);

			int y = inse.executeUpdate();
			if (y > 0) {
				return "User Register Successfully";
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		// TODO Auto-generated method stub
		return message;
	}

	@Override
	public String checkUser(String email, String password) {
		// TODO Auto-generated method stub

		String message = null;

		try (Connection conn = Dbutil.provideconnection()) {

			PreparedStatement su = conn
					.prepareStatement("SELECT username, password FROM chatapp WHERE username = ? AND password = ?");
			System.out.print("error happen");
			su.setString(1, email);
			su.setString(2, password);

			ResultSet ans = su.executeQuery();

			if (ans.next()) {

				String username = ans.getString("username");
				String passworda = ans.getString("password");

				String uuidexit = "select uuid from chatapp where username='" + username + "' and password ='"
						+ passworda + "'";

				PreparedStatement already = conn.prepareStatement(uuidexit);
				System.out.println("eroor check ");
				ResultSet ex = already.executeQuery();

				if (ans.next()) {

					String details = ans.getString("uuid");
					return details;
				} else {
					String uuid = UUID.randomUUID().toString();
					System.out.println("eeeeeeee");
					String sqluuid = "update chatapp set uuid='" + uuid + "' where username='" + username
							+ "' and password='" + passworda + "'";

					PreparedStatement getuuuid = conn.prepareStatement(sqluuid);

					int row = getuuuid.executeUpdate();

					if (row > 0) {
						return uuid;
					}

				}

			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return message;
	}

	@Override
	public List<String> getuserdata(String username) {

		String sql = "SELECT DISTINCT too FROM chat WHERE fromt = '" + username + "'";
		if (username != null) {
			try (Connection con = Dbutil.provideconnection()) {

				PreparedStatement req = con.prepareStatement(sql);

				ResultSet ans = req.executeQuery();

				List<String> listofemail = new ArrayList<>();
				while (ans.next()) {
					String email = ans.getString("too");
					listofemail.add(email);

				}
				return listofemail;

			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap<List<String>, List<String>> getMessages(String from, String to) {
	   
	    String sql = "SELECT messages,fromt,too FROM chat WHERE (fromt = ? AND too = ?) OR (fromt = ? AND too = ?) ORDER BY time";

	    try (Connection con = Dbutil.provideconnection();
	         PreparedStatement stmt = con.prepareStatement(sql)) {

	        stmt.setString(1, from);
	        stmt.setString(2, to);
	        stmt.setString(3, to);
	        stmt.setString(4, from);

	        ResultSet rs = stmt.executeQuery();
            
	       HashMap<List<String>, List<String>> re = new HashMap<>();
	       List<String> user = new ArrayList<>();
	       List<String> messages = new ArrayList<>();

	        while (rs.next()) {
	        	String username = rs.getString("fromt");
	            String message = rs.getString("messages");
	            
                 user.add(username);
                 messages.add(message);
	            
	        }
               re.put(user, messages);
	        return re;

	    } catch (SQLException e) {
	        // Log the exception
	        e.printStackTrace();
	        // Return an empty map
	        return  null;
	    }
	}


	@Override
	public String savemessage(String fromt, String too, String message) {

		if (fromt != null && too != null && message != null) {

			try (Connection conn = Dbutil.provideconnection()) {

				String popmes = "INSERT INTO chat (messages,fromt, too,time)\r\n" + "VALUES (?,?,?, now())";

				PreparedStatement res = conn.prepareStatement(popmes);

				res.setString(1, message);
				res.setString(2, fromt);
				res.setString(3, too);

				int row = res.executeUpdate();

				if (row > 0) {

					return message;

				} else {
					return null;
				}

			} catch (Exception e) {
				// TODO: handle exception
			}

		}

		return null;
	}
}
