package k26_DBProject3rdExam;

public class K26_Item {
	private int no = 0;
	private String name = "";
	private int weight = 0;
	private double displaySize = 0;
	private int diskVolume = 0;
	private String etc = "";
	private int price = 0;
	StringBuilder stringBuilder = new StringBuilder();
	
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
	@Override
	public String toString() {
		return stringBuilder.append("no ").append(no)
				.append(". name : ").append(name)
				.append(", weight : ").append(weight).append(" (g)")
				.append(", displaySize : ").append(displaySize).append(" (inch)")
				.append(", diskVolume : ").append(diskVolume).append(" (GB)")
				.append(", etc : ").append(etc)
				.append(", price : ").append(price).append(" (만원)")
				.toString();
	}
	
	
}