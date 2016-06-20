import java.util.LinkedList;
import java.io.*;
import java.util.Random;

public class Main{

	public static void main(String[] args){

		simpleBot();

	}

public static void simpleBot(){

		MReceiver m = new MReceiver("");
		boolean cont = false;
		JMessage ms=null;
		while(true){
			try{
				//getting updates + queueing them
				m.getUpdates();
				ms = m.returnLatestMessage();
				cont = true;
			}catch(NoJMessageFoundException n){
				//no message left in queue;
				cont = false;
				//System.out.println(n);
			}
			if(cont){
				m.confirmMessage(ms.update_id);
				//confirms latest message
				//System.out.println("\n" + ms.chat_id + "\n");
				//ms.printJMessage();
				String reply="defaultMessage";
				//-----------------------------------
				//work with message here
				//watch out for spaces in the message
				// " those don't work either yet
				//System.out.println(ms.text);
				if(ms.text.contains("/start")){
					//do nothing
				}else if(ms.text.contains("/dice")){
					String str = ms.text.replaceAll("\\D+","");					
					int zahl = 6;
					if(!str.equals("")){
						try{
						zahl = Integer.parseInt(str);
						}catch(Exception nfe){
							reply = "da→war→" + ms.from_first_name + "→wohl→ein→böhses→Mädchen";
							m.sendMessage(reply, ms.chat_id);
						}					
					}
					if(zahl == 0) 
					zahl = 1;					
					System.out.println("rollan");
					zahl = new Random().nextInt(zahl) + 1;
					reply = ms.from_first_name + "→rolled:" + zahl;
					m.sendMessage(reply, ms.chat_id);
				
				}else if(ms.text.contains("/metal")){
				
					System.out.println("Trying to get METAL");
					YoutubeGetter y = new YoutubeGetter();
					String url = y.getUrl();
					m.sendMessage(url, ms.chat_id);
				
				

				}else if(ms.text.contains("/8ball")){

				String[] ballz = {"yes", "no", "reply→hazy,try→again", "outlook→not→so→good", "as→i→see→it,yes", "repeat→the→question", "not→in→a→million→years", "it→is→certain", "it→is→decidedly→so", "my→sources→say→no", "better→not→tell→you→now", "signs→point→to→yes", "count→on→it", "meh"};
				int i = new Random().nextInt(13);
				String msg = ballz[i];
				//"\/8ball"
				if(ms.text.length()<10){
					msg = "whaddya say?";
				}
				m.sendMessage(msg, ms.chat_id);

				}
				else if(ms.text.contains("/debug")){
					//to debug stuff and stuff
					m.sendMessage("leer leer", ms.chat_id);
				}
				else{
					//Spongebob!!!
					m.sendMessage("What→Zit→Tooya,→" + ms.from_first_name + "?!?", ms.chat_id);

				}
				//-----------------------------------				
				
			}

		}

	}

}
