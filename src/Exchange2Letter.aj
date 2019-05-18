
 public aspect Exchange2Letter {
	 String around(String value) : execution(String ClientRMIGUI.encryptMethod(String)) && args(value) {
		 char[] values = value.toCharArray();
		 if(values.length > 1){
		            char temp = values[0];
		            values[0] = values[1];
		            values[1] = temp;		 
		 }
	        
	        return new String(values);
	}
 }

