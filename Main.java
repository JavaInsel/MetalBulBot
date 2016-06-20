import java.util.LinkedList;
import java.io.*;
import java.util.Random;

public class Main{

	public static void main(String[] args){

		simpleBot();

	}


//just a simple bot
public static void simpleBot(){

		//possibility to use on a different telegram bot
		//needs to be implemented in the MReceiver.java
		//bot is hardcoded in there so far
		MReceiver m = new MReceiver("");
		boolean cont = false;
		JMessage ms=null;
		//endless loop of checking for updates and eventually replying to them		
		while(true){
			try{
				//getting updates + queueing them
				m.getUpdates();
				ms = m.returnLatestMessage();
				cont = true;
			}catch(NoJMessageFoundException n){
				//no message left in queue;
				cont = false;
			}
			if(cont){
				//got a message
				m.confirmMessage(ms.update_id);
				//confirms latest message
				
				String reply="defaultMessage";
				if(ms.text.contains("/start")){
					//do nothing
				}else if(ms.text.contains("/dice")){
					//simple dice roll

					//black regex magic					
					String str = ms.text.replaceAll("\\D+","");					
					int zahl = 6;
					if(!str.equals("")){
						try{
						zahl = Integer.parseInt(str);
						}catch(Exception nfe){
							reply = "da war " + ms.from_first_name + " wohl ein böhses Mädchen";
							m.sendMessage(reply, ms.chat_id);
						}					
					}
					if(zahl == 0) 
					zahl = 1;					
					System.out.println("rollan");
					zahl = new Random().nextInt(zahl) + 1;
					reply = ms.from_first_name + " rolled:" + zahl;
					m.sendMessage(reply, ms.chat_id);
				
				}else if(ms.text.contains("/metal")){
					//simple youtube metal music getter
					System.out.println("Trying to get METAL");
					YoutubeGetter y = new YoutubeGetter();
					String url = y.getUrl();
					m.sendMessage(url, ms.chat_id);
				
				

				}else if(ms.text.contains("/8ball")){
					//simple 8ball
					System.out.println("got 8ball");
					String[] ballz = {"yes", "no", "reply hazy, try again", "outlook not so good", "as i see it, yes", "repeat the question", "not in a million years", "it is certain", "it is decidedly so", "my sources say no", "better not tell you now", "signs point to yes", "count on it", "meh"};
					int i = new Random().nextInt(14);
					String msg = ballz[i];
					m.sendMessage(msg, ms.chat_id);
				}
				else if(ms.text.contains("/debug")){
					//to debug stuff and stuff
					m.sendMessage("PUT DEBUGGING INFO HERE", ms.chat_id);
				}

			}

		}

	}

}
