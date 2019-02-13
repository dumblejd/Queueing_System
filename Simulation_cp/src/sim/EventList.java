package sim;
//Di Jin   dxj170930 UTDID:2021377200
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EventList {

	ArrayList<Event> EventArray ;
	
	public EventList() {
		super();
		EventArray = new ArrayList<Event>();
	}
	public void insert(double time, int type,int priority) //insert and sort
	{
		Event event= new Event(time, type,priority);
		this.EventArray.add(event);
		this.sort(this.EventArray);
	}

	Comparator <Event>c = new Comparator<Event>() 
	{  
        @Override  
        public int compare(Event o1, Event o2) {  
            // TODO Auto-generated method stub  
            if(o1.time < o2.time)  
                return -1;  
            else return 1;  
        }
	};
	public void sort(ArrayList<Event> Array)
	{
		Collections.sort(Array,c);
	}
	public Event getfirst()
	{
		Event a = new Event(0, -1,-1);
		a.time= EventArray.get(0).time;
		a.type=EventArray.get(0).type;
		a.priority=EventArray.get(0).priority;
		a.wait=EventArray.get(0).wait;
		return a;
	}
	public void delete()
	{
		EventArray.remove(0);
	}
	public Event get_and_delete() //get first and delete it
	{
		Event a = new Event(0, -1,-1);
		a.time= EventArray.get(0).time;
		a.type=EventArray.get(0).type;
		a.priority=EventArray.get(0).priority;
		a.wait=EventArray.get(0).wait;
		EventArray.remove(0);
		return a;
	}
	public Event find_next_arr()
	{
		int index=0;
		for(int i=0;i<EventArray.size();i++)
		{
			if(EventArray.get(i).getType()==1)
			{
				index=i;
				break;
			}
			
		}
		Event a = new Event(0, -1,-1);
		a.time= EventArray.get(index).time;
		a.type=EventArray.get(index).type;
		a.priority=EventArray.get(index).priority;
		a.wait=EventArray.get(0).wait;
		return a;
	}
	public Event find_next_dep()
	{
		int index=0;
		for(int i=0;i<EventArray.size();i++)
		{
			if(EventArray.get(i).getType()==0)
			{
				index=i;
				break;
			}
			
		}
		Event a = new Event(0, -1,-1);
		a.time= EventArray.get(index).time;
		a.type=EventArray.get(index).type;
		a.priority=EventArray.get(index).priority;
		a.wait=EventArray.get(0).wait;
		return a;
	}
	public Event get_next_not_wait()   //return address
	{
		Event a=null;
		for(int i=0;i<EventArray.size();i++)
		{
			if(EventArray.get(i).wait==0)
			{
				a = EventArray.get(i);
				break;
			}
			
		}
		return a;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
