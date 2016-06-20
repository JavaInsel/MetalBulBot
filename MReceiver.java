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
		//String getCommand = "https://api.telegram.org/bot208235593:AAEHULa6JebWylbQ38sd8d9fuzehmCsUJKg/getUpdates";
		String commandBase = "https://api.telegram.org/bot208235593:AAEHULa6JebWylbQ38sd8d9fuzehmCsUJKg/";
		//String SEND = "sendMessage -d text=\"Hi thar too\" -d chat_id=22407528";
		String getCommand = commandBase + command;
		System.out.println("Sending command " + getCommand);
		String result="";
		URLConnection c = null;		
		try
		{
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

		//"sendMessage -d text=\"Hi thar too\" -d chat_id=22407528"
		//WATCH OUT FOR STRING BEING PARSED FOR "
		if(chat_id==""){
			return;
		}
		System.out.println("sending: " + message + chat_id);
		String command = "sendMessage?chat_id=" + chat_id+"&text="+ message;
		System.out.println(sendCommand(command));

	}

	//parse to JMEssage later on
	private String pollUpdates(){
		//INSERT COMMAND FOR GETTING THE UPDATES HERE
		String message = sendCommand("getUpdates");
		//System.out.println(message);
		return message;


	}

	//puts new messages in the queue
	public void getUpdates(){

		String[] updates = splitUpdate(pollUpdates());
		for(int i = 0; i<updates.length; i++){
/*
System.out.println("DEBUG: \n-------------------------------------\n" + "Current update " + updates[i] + "\n-------------------------------------\n");
			*/
			//System.out.println(updates[0]);
			if(!updates[0].contains("\"result\":[]}")){			
			JMessage m = new JMessage(updates[i]);
			messages.add(m);
			//m.printJMessage();
			}
		}

	}

	private String[] splitUpdate(String update){
		String[] updates = update.split("\\},\\{");
		for(int i = 0; i<updates.length; i++){
			if(i==0){
				updates[i] = updates[i]+"}";
			}else if(i==updates.length-1){
				updates[i] = "{" + updates[i];
			}else{
				updates[i] = "{" + updates[i] + "}";
			}

			/*System.out.println("DEBUG: \n-------------------------------------\n" + updates[i] + "\n-------------------------------------\n");
				*/
		}
		return updates;
	}

	//confirms messges until given message ID
	public void confirmMessage(String updateID){
			if(updateID==null||updateID.equals("")){
				return;
			}
			int offset = Integer.parseInt(updateID) + 1;
			
			/*System.out.println("DEBUG: \n-------------------------------------\n" + "sending: " + "/getUpdates -d offset=" + offset + "\n-------------------------------------\n");
			*/

			sendCommand("getUpdates?offset=" + offset);
	
	}
	public JMessage returnLatestMessage() throws NoJMessageFoundException{

		if(messages.peek()==null)
			throw new NoJMessageFoundException("Message queue is empty");
		else
			return messages.poll();
	}
	

	


}
