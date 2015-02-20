package home.call;

import java.util.Set;

public class Names{
	String name;
	String id;
	Set<String> numbers;
	//Set mySet1 = new HashSet();

	Names(String id,String name,Set<String> numbers){
		this.id = id;
		this.numbers = numbers;
		this.name = name;
	}
	public String toString(){
		String numberss = "";
		for (String num : numbers) {
			if(numberss.equals(""))
				numberss=num;
			else
				numberss+=","+num;
	}
		return name+" : "+numberss;
	}
}