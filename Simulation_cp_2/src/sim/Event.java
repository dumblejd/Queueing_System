package sim;
//Di Jin   dxj170930 UTDID:2021377200
public class Event {

	double time;
	String type;
	String priority;
	String path = "00";
	String detail;
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}

	
	
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public double getTime() {
		return time;
	}
	public void setTime(double time) {
		this.time = time;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Event(double time,String detail) {
		super();
		this.time = time;
		this.detail=detail;
		String [] temp = detail.split("_");
		type = temp[0];
		path = temp[1];
		priority = temp[2];
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
