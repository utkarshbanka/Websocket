package com.chatApp.server;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.net.URLEncoder;

import com.chatApp.dbutil.Dbutil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

@ServerEndpoint("/websocketendpoint/{token}")
public class WsServer {

	private static HashMap<String, ArrayList<Session>> storemail = new HashMap<>();

	public String emails = null;

	@OnOpen
	public void onOpen(Session session, @PathParam("token") String token) {

		String token1 = "select username from chatapp where uuid = '" + token + "'";

		try (Connection conn = Dbutil.provideconnection()) {

			PreparedStatement getemail = conn.prepareStatement(token1);

			ResultSet ans = getemail.executeQuery();
			if (ans.next()) {
				String email = ans.getString("username");
				if (storemail.containsKey(email)) {
					ArrayList<Session> sessions = storemail.get(email);
					sessions.add(session);
					emails = email;
				} else {
					ArrayList<Session> sessions = new ArrayList<Session>();
					sessions.add(session);
					storemail.put(email, sessions);
					emails = email;
				}
			} else {
				session.close();
			}

		} catch (Exception e) {

		}

	}

	@OnClose
	public void onClose(Session session) {
		System.out.println("Close Connection ...");

		ArrayList<Session> sessions = storemail.get(emails);

		if (session != null) {
			sessions.remove(session);
		}

	}

	@OnMessage
	public void onMessage(String message, Session session) throws IOException {
		System.out.println("Message from the client: " + message);
		String echoMsg = "Echo from the server : " + message;

		ObjectMapper objectmapper = new ObjectMapper();
		HashMap<String, String> ss = objectmapper.readValue(message, new TypeReference<HashMap<String, String>>() {
		});

		String fr = URLEncoder.encode(ss.get("fromt"), "UTF-8");
		String t = URLEncoder.encode(ss.get("to"), "UTF-8");
		String me = URLEncoder.encode(ss.get("message"), "UTF-8");

//		String fr = ss.get("fromt");
//		String t = ss.get("to");
//		String me = ss.get("message");
//		
		String url = "http://localhost:8082/WebscoketChatApp/SendMessages?fromt=" + fr + "&too=" + t + "&messages=" + me
				+ "";
		// POST data to send

		HttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);

		// Set request headers
		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");

		// Set POST data

		try {
			// Execute HTTP POST request
			HttpResponse response = httpClient.execute(httpPost);

			// Get response entity
			HttpEntity entity = response.getEntity();

			// Read response as string
			String responseString = EntityUtils.toString(entity, "UTF-8");
             System.out.println("cateerefkjkjfjsdfjasjf lljlund key put");
			// Print response
			System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
			System.out.println("Response Message : " + responseString);

		} catch (IOException e) {
			System.out.println("checking theerrrrrrrrrrrrrrrr");
			e.printStackTrace();
			System.out.println("checking theerrrrrrrrrrrrrrrr");
		}

		System.err.println(storemail);
		System.out.println(ss.get("to"));
		ArrayList<Session> sessions = storemail.get(ss.get("to"));

		if (sessions != null && !sessions.isEmpty()) {
			// Iterate over the sessions in the ArrayList

			for (Session s : sessions) {
				s.getBasicRemote().sendText(ss.get("message"));
			}

		} else {
			System.out.println("No sessions found for email: ");
		}

	}

	@OnError
	public void onError(Throwable e) {
		e.printStackTrace();
	}

}
