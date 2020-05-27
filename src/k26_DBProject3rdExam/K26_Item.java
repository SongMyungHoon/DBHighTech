package k26_DBProject3rdExam;

import java.util.*;

/**K26_Item class
 * DB에서 불러온 Itme record를 저장하는 Class
 * @author MHSong
 */
public class K26_Item {
	private int no = 0;
	private String name = "";
	private int weight = 0;
	private double displaySize = 0;
	private int diskVolume = 0;
	private String etc = "";
	private int price = 0;
	StringBuilder stringBuilder = new StringBuilder();
	
	// Default Constructor
	public K26_Item() {};
	// General Constructor
	public K26_Item(int no, String name, int weight,
			double displaySize, int diskVolume, String etc, int price) {
		this.no = no;
		this.name = name;
		this.weight = weight;
		this.displaySize = displaySize;
		this.diskVolume = diskVolume;
		this.etc = etc;
		this.price = price;
	}	// Constructor end
	
	public int getNo() {
		return no;
	}	// getNo end
	public void setNo(int no) {
		this.no = no;
	}	// setNo end
	public String getName() {
		return name;
	}	// getName end
	public void setName(String name) {
		this.name = name;
	}	// setName end
	public int getWeight() {
		return weight;
	}	// getWeight end
	public void setWeight(int weight) {
		this.weight = weight;
	}	// setWeight end
	public double getDisplaySize() {
		return displaySize;
	}	// getDisplaySize end
	public void setDisplaySize(double displaySize) {
		this.displaySize = displaySize;
	}	// setDisplaySize end
	public int getDiskVolume() {
		return diskVolume;
	}	// getDiskVolume end
	public void setDiskVolume(int diskVolume) {
		this.diskVolume = diskVolume;
	}	// setDiskVolume end
	public String getEtc() {
		return etc;
	}	// getEtc end
	public void setEtc(String etc) {
		this.etc = etc;
	}	// setEtc end	
	public int getPrice() {
		return price;
	}	// getPrice end
	public void setPrice(int price) {
		this.price = price;
	}	// setPrice end
	
	/**k26_getFieldType method
	 * K26_Item class의 field의 data type을 key와 value를 String type으로
	 * 갖는 Map instance를 외부에 return하는 method 
	 * @return Map<String, String> fieldMap
	 */
	public Map<String, String> k26_getFieldType() {
		Map<String, String> fieldMap = new HashMap<>();
		fieldMap.put("name", "String");
		fieldMap.put("weight", "int");
		fieldMap.put("displaySize", "double");
		fieldMap.put("diskVolume", "int");
		fieldMap.put("etc", "String");
		fieldMap.put("price", "int");
		return fieldMap;
	}
	
	@Override
	public String toString() {
		return stringBuilder.append(String.format("%s. ", no))
				.append(String.format("%s, ", name))
				.append(String.format("%d, ", weight))
				.append(String.format("%.1f, ", displaySize))
				.append(String.format("%d, ", diskVolume))
				.append(String.format("%s, ", etc))
				.append(String.format("%d", price)).toString();
	}
}
