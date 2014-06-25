import org.json.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class road{
	private String name;
	private int dif_mon;
	private int[] month;
	private int[] prices;
	private int num_month, num_prices;
	
	public road(String s,int mon,int pri){
		this.name = s;
		this.month = new int[1000];
		this.month[0] = mon;
		this.num_month = 1;
		this.prices = new int[30000];
		this.prices[0] = pri;
		this.num_prices = 1;
	}
	public void process(int mon, int pri){
		prices[num_prices++]=pri;
		for(int i=0;i<num_month;i++){
			if(mon==month[i])
				break;
			else if(i==(num_month-1) ){
				month[num_month++]=mon;
				break;
			}
		}
	}
	public int getDifMonth(){
		return num_month;
	}
	public String getName(){
		return name;
	}
	public int getMaxPrice(){
		int max=0;
		for(int i=0;i<num_prices;i++)
			if(prices[i]>max)
				max = prices[i];
		return max;
	}
	public int getMinPrice(){
		int min=prices[0];
		for(int i=0;i<num_prices;i++)
			if(prices[i]<min)
				min = prices[i];
		return min;
	}
}

public class TocHw4 {

    public static String url_to_string(String url) throws Exception 
    {
        URL website = new URL(url);
        URLConnection connection = website.openConnection();
        InputStreamReader Sread = new InputStreamReader(connection.getInputStream(), "UTF-8");
        BufferedReader in = new BufferedReader(Sread);
        StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
        in.close();
        return response.toString();
    }	
	public static void main(String[] args) throws JSONException, Exception {
	
		JSONArray data = null;
		data = new JSONArray(url_to_string(args[0]));
		LinkedHashMap<String, road> map = new LinkedHashMap<String, road>();
		road Rd = null;
		boolean find_road=false;
		String roadname = null;
		for(int i=0;i<data.length();i++){
			JSONObject item = data.getJSONObject(i);
			find_road=false;
			if(item.getString("�g�a�Ϭq��m�Ϋت��Ϫ��P").matches(".*��.*") ){
	//			String[] result = item.getString("�g�a�Ϭq��m�Ϋت��Ϫ��P").split("��", 2);
	//			roadname = result[0].concat("��");
				Pattern ptn = Pattern.compile("(.*)��");
				Matcher mtr = ptn.matcher(item.getString("�g�a�Ϭq��m�Ϋت��Ϫ��P") );
				while(mtr.find()){
					roadname = new String(mtr.group(0) );
				}
				find_road=true;
		//		System.out.println(item.length()+" "+item.getInt("�`����"));
			}else if(item.getString("�g�a�Ϭq��m�Ϋت��Ϫ��P").matches(".*��.*") ){
				String[] result = item.getString("�g�a�Ϭq��m�Ϋت��Ϫ��P").split("��", 2);
				roadname = result[0].concat("��");	
				find_road=true;
		//		System.out.println(roadname);
			}else if(item.getString("�g�a�Ϭq��m�Ϋت��Ϫ��P").matches(".*�j�D.*") ){
				String[] result = item.getString("�g�a�Ϭq��m�Ϋت��Ϫ��P").split("�j�D", 2);
				roadname = result[0].concat("�j�D");	
				find_road=true;
		//		System.out.println(roadname);
			}else if(item.getString("�g�a�Ϭq��m�Ϋت��Ϫ��P").matches(".*��.*") ){
				String[] result = item.getString("�g�a�Ϭq��m�Ϋت��Ϫ��P").split("��", 2);
				roadname = result[0].concat("��");
				find_road=true;
		//		System.out.println(roadname);
			}
			if(find_road){
				if(map.containsKey(roadname) ){
					map.get(roadname).process(item.getInt("����~��"), item.getInt("�`����"));;
				}else{
					Rd = new road(roadname,item.getInt("����~��"), item.getInt("�`����") );
					map.put(roadname, Rd);
				}
			}
		}
		int max=0;
		for(String key:map.keySet()){
			if( map.get(key).getDifMonth() > max )
				max = map.get(key).getDifMonth();
		}
		for(String key:map.keySet()){
			if( map.get(key).getDifMonth() == max )
				System.out.println( map.get(key).getName() +", �̰������: "+ map.get(key).getMaxPrice()+", �̧C�����: "+ map.get(key).getMinPrice() );
		}
	}
}
