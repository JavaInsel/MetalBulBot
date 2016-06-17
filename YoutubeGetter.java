import java.io.*;
import java.util.Random;


public class YoutubeGetter{

	private String[] labels = {"UCoxg3Kml41wE3IPq-PC-LQw", "UCKdA5J4-opjla1aWOjF74mg", "UCSldglor1t-5E-Gy2eBdMrA", "UCG7AaCh_CiG6pq_rRDNw72A"};

	public YoutubeGetter(){
		extractVideoId(getSuggestions());
	}

	private String getSuggestions(){

		int l = new Random().nextInt(3);
		String label = labels[l];
		System.out.println("decided for label: " + l);
		System.out.println(label);
		String result = "";
		String command = "curl https://www.googleapis.com/youtube/v3/search?key=AIzaSyBnwc5SZGA87iRJD1Ruj3BoNgi2rBZWRiQ&part=snippet&channelId="+ label + "&maxResults=50";
		System.out.println(command);
		try
		{
			Process proc=Runtime.getRuntime().exec(command);
			proc.waitFor();
			BufferedReader read=new BufferedReader(new InputStreamReader(proc.getInputStream()));

			while(read.ready())
			{
				result+=(read.readLine());

			}
		}
		catch(IOException e)
		{
			System.out.println(e.getMessage());
		}
		catch(Exception e1){

			e1.printStackTrace();

		}
		System.out.println(result);
		return result;
	}

	private String extractVideoId(String res){
		// "videoId": "tb2gjwq1WXg"
		int rand = 	new Random().nextInt(49);

 		//"videoId": "FVAQQujgSxQ" 11 21

		String result = parse(res, "videoId", rand);		
		
		System.out.println("result: " + result);
		return result;
	}

	private String parse(String text, String variable, int n){

		String result = "";
		for(int i = 0; i<text.length()-30; i++){
			if(text.substring(i, i+variable.length()).equals(variable)){
				System.out.println("checking " + text.substring(i, i+variable.length()) + " against " + variable);		
		
				result = text.substring(i+variable.length()+4, i+variable.length()+15);
				if(n==0){
					i = text.length()-51;
					break;
				}				
				n--;
			}
		}	
		return result;

	}

	public String getUrl(){

		return "http://youtu.be/" + extractVideoId(getSuggestions());

	}

	public static void main(String[] args){
		YoutubeGetter y = new YoutubeGetter();

	}


}
