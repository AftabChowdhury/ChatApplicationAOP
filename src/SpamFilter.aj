
 public aspect SpamFilter {
	 String around(String message) : execution(String SpamFilterAll.spamFilter(String)) && args(message) {
		 String[] spamWords = {"Porn", "porn", "Lottery", "lottery", "Click Here", "click here","100% free"};
		 for (int i=0; i<spamWords.length; i++)
	     {
			 message = message.replaceAll(spamWords[i], "");
	     }
		 return message;
	}
 }

