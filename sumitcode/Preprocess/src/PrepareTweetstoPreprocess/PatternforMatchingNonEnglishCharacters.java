package PrepareTweetstoPreprocess;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
	
public class PatternforMatchingNonEnglishCharacters {
	String Regex = "[\\w]";
	Pattern pattern = Pattern.compile(Regex);
	boolean containsSpecialCharacters(String string)
	{
		Matcher m = pattern.matcher(string);
		return m.find();
	}
}