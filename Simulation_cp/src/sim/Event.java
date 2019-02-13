package sim;
//Di Jin   dxj170930 UTDID:2021377200
public class Event {

	double time;
	int type;
	int priority;
	int wait;
	
	public int getWait() {
		return wait;
	}
	public void setWait(int wait) {
		this.wait = wait;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public double getTime() {
		return time;
	}
	public void setTime(double time) {
		this.time = time;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Event(double time, int type,int priority) {
		super();
		this.time = time;
		this.type = type;
		this.priority=priority;
		this.wait=0;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
