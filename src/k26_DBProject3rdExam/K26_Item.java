package k26_DBProject3rdExam;

import java.util.*;

public class K26_Item {
	private int no = 0;
	private String name = "";
	private int weight = 0;
	private double displaySize = 0;
	private int diskVolume = 0;
	private String etc = "";
	private int price = 0;
	StringBuilder stringBuilder = new StringBuilder();
	
	public K26_Item() {};
	
	public K26_Item(int no, String name, int weight,
			double displaySize, int diskVolume, String etc, int price) {
		this.no = no;
		this.name = name;
		this.weight = weight;
		this.displaySize = displaySize;
		this.diskVolume = diskVolume;
		this.etc = etc;
		this.price = price;
	}
	
//	public String getFieldType(String fieldName) {
//		Class<?> itemClass = Class.forName("K26_Item");
//		Field type = itemClass.getField(fieldName).getType();
//
//		return type.;
//	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public double getDisplaySize() {
		return displaySize;
	}
	public void setDisplaySize(double displaySize) {
		this.displaySize = displaySize;
	}
	public int getDiskVolume() {
		return diskVolume;
	}
	public void setDiskVolume(int diskVolume) {
		this.diskVolume = diskVolume;
	}
	public String getEtc() {
		return etc;
	}
	public void setEtc(String etc) {
		this.etc = etc;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
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
	public List<String> k26_getFieldValue() {
		List<String> fieldList = new ArrayList<>();
		fieldList.add("weight");
		fieldList.add("displaySize");
		fieldList.add("diskVolume");
		fieldList.add("etc");
		fieldList.add("price");
		return fieldList;
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
