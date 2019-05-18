
 public aspect Rot13 {
	 String around(String value) : execution(String ClientRMIGUI.encryptMethod(String)) && args(value) {
		 char[] values = value.toCharArray();
	        for (int i = 0; i < values.length; i++) {
	            char letter = values[i];

	            if (letter >= 'a' && letter <= 'z') {
	                // Rotate lowercase letters.

	                if (letter > 'm') {
	                    letter -= 13;
	                } else {
	                    letter += 13;
	                }
	            } else if (letter >= 'A' && letter <= 'Z') {
	                // Rotate uppercase letters.

	                if (letter > 'M') {
	                    letter -= 13;
	                } else {
	                    letter += 13;
	                }
	            }
	            values[i] = letter;
	        }
	        // Convert array to a new String.
	        return new String(values);
	}
 }

