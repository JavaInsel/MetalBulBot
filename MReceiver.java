import java.util.LinkedList;
import java.io.*;
import java.util.Random;
import java.net.*;
public class MReceiver{

	private final String API_KEY;
	private LinkedList<JMessage> messages;

	public MReceiver(String API_KEY){
		this.API_KEY = API_KEY;
		messages = new LinkedList<JMessage>();
	}
	//get messages from the server, add them to the queue
	public String sendCommand(String command){
		//build command from command base(address of the bot) and command itself		
		String commandBase = "https://api.telegram.org/bot208235593:AAEHULa6JebWylbQ38sd8d9fuzehmCsUJKg/";
		String getCommand = commandBase + command;
		String result="";
		URLConnection c = null;		
		try
		{
			//self explanatory
			URL u = new URL(getCommand);
			c = u.openConnection();
			c.connect();
			BufferedReader read=new BufferedReader(new InputStreamReader(c.getInputStream()));

			while(read.ready())
			{
				result+=(read.readLine());

			}		
		}
		catch(Exception e1){

			e1.printStackTrace();

		}finally{
			try{
			c.getInputStream().close();
			}catch(Exception f){
				f.printStackTrace();
			}
		}

		return result;
	}

	public void sendMessage(String message, String chat_id){

		//check if we're trying to send a message without a receiver		
		if(chat_id==""){
			return;
		}
		//assembling the command part of the message
		String command = "sendMessage?chat_id=" + chat_id+"&text="+ message;
		System.out.println(sendCommand(command));

	}

	private String pollUpdates(){
		//getting new updates from the telegram server		
		String message = sendCommand("getUpdates");
		return message;


	}

	//puts new messages in the queue
	public void getUpdates(){
		String[] updates = splitUpdate(pollUpdates());
		for(int i = 0; i<updates.length; i++){
			//check if it's a valid message then add to the message queue
			//quick and dirty			
			if(!updates[0].contains("\"result\":[]}")){			
			JMessage m = new JMessage(updates[i]);
			messages.add(m);
			}
		}

	}
	//splits the whole bunch of received update into many many sepparate ones
	private String[] splitUpdate(String update){
		//yep, that's how messages are sepparated
		String[] updates = update.split("\\},\\{");
		for(int i = 0; i<updates.length; i++){
			//put back in those {} because they were lost at splitting			
			if(i==0){
				updates[i] = updates[i]+"}";
			}else if(i==updates.length-1){
				updates[i] = "{" + updates[i];
			}else{
				updates[i] = "{" + updates[i] + "}";
			}
		}
		return updates;
	}

	//confirms messges until given message ID
	public void confirmMessage(String updateID){
			if(updateID==null||updateID.equals("")){
				//in case of empty updateID				
				return;
			}
			int offset = Integer.parseInt(updateID) + 1;			
			sendCommand("getUpdates?offset=" + offset);
	
	}
	
	//returns the head of the message queue
	public JMessage returnLatestMessage() throws NoJMessageFoundException{
		//self explanatory
		if(messages.peek()==null)
			throw new NoJMessageFoundException("Message queue is empty");
		else
			return messages.poll();
	}
	

	


}
