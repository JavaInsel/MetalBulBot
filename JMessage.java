public class JMessage{

	//Variables for each field in the JSON
	public String message_id;
	public String from_id;
	public String from_first_name;

	public String chat_id;
	public String chat_first_name;
	public String chat_type;

	public String date;
	public String text;
	
	public String update_id;
	public String others;
	String JSON_Message;
	
	public JMessage(String JSON_Message){
	
	this.JSON_Message = JSON_Message;	
	/*System.out.println("DEBUG: \n-------------------------------------\n" + "current message " + JSON_Message + "\n-------------------------------------\n");
	
	//--------------------------------------------------
	this.JSON_Message = "{\"ok\":true,\"result\":[{\"update_id\":414250308,\"message\":{\"message_id\":10,\"from\":{\"id\":22407528,\"first_name\":\"Leonhard\"},\"chat\":{\"id\":22407528,\"first_name\":\"Leonhard\",\"type\":\"private\"},\"date\":1460036262,\"text\":\"Hallo bot\"}}]";
	//--------------------------------------------------
	*/	
	//parse the given JSON-Message into the variables
	
	message_id = parse("message_id");
	from_id = parse("from\":{\"id");
	from_first_name = parse("first_name");
	chat_id = parse("chat\":{\"id");
	//chat_first_name = parse("first_name");
	chat_type = parse("type");
	date = parse("date");
	text = parse("text");
	update_id = parse("update_id");
	others = "";
	}

	public void printJMessage(){

		
		System.out.println("update_id: " + update_id);
		System.out.println("msg_id: " + message_id);
		System.out.println("from_id: "+ from_id);
		System.out.println("from_first_name: " + from_first_name);
		System.out.println("chat_id: " + chat_id);
		System.out.println("date: "+ date);
		System.out.println("text: "+ text);

	}

	//find occurrence of variable in string and return its value
	private String parse(String variable){
		//default case, no duplicates available
		int length;
		length = variable.length();
		String result = "";
		for(int index = 0;(index+length+3)<JSON_Message.length(); index++){
			//System.out.println(index + " " + length + " " + (index+length) + " " + JSON_Message.length());
			if(JSON_Message.substring(index, index+length).equals(variable)){
				//found occurence
				//check whether it's a string or not
				char delimiter;
				int sub_end = index+length+4;

				if(JSON_Message.charAt(index+length+3)=='\"')
				{while(true){
					if(JSON_Message.charAt(sub_end)=='\"'){
						break;
					}
					sub_end++;
				}
				}else{

				while(true){
					if(JSON_Message.charAt(sub_end)==','||JSON_Message.charAt(sub_end)=='}')
						break;
					sub_end++;
				}
				return result = JSON_Message.substring(index+length+2, sub_end);
			}}

		}
		return result;
	}

}
