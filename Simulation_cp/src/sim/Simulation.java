package sim;
//Di Jin   dxj170930 UTDID:2021377200
public class Simulation {
	EventList Elist;                // Create event list
	int ARR=1;                 // Define the event types arr=1 depart=0
	int DEP=0;

	int capacity=4;     //Capacity
	int processor=2;    // M
	int limit_block=2;  //L
	double mu = 3.0; 
	double lambda_user = 4.0;            // Arrival rate
	double lambda_admin =6.0;          

	// Service rate

	double clock = 0.0;             // System clock
	int N = 0;                      // Number of customers in system
	int Ndep = 0;                   // Number of departures from system
	double EN = 0.0;                // For calculating E[N]
	double []arrival_num = {0.0,0.0};
	double []block_num = {0.0,0.0};


	boolean done = false;                   // End condition satisfied?
	Random rd= new Random();
	//	public void init()
	//	{
	//		 clock = 0.0;             // System clock
	//		 N = 0;                      // Number of customers in system
	//		Ndep = 0;                   // Number of departures from system
	//		 EN = 0.0;                // For calculating E[N]
	//		arrival_num[0] = 0.0;
	//		arrival_num[1] = 0.0;
	//		block_num[0] = 0.0;
	//		block_num[1] = 0.0;
	//	}
	Simulation()
	{
		this.Elist=new EventList();

	}
	public void run_ten()
	{

		for(int i=1;i<=10;i++)
		{
			Simulation s = new Simulation();
			s.lambda_admin = i*0.1*processor*mu;
			s.run();
		}
	}
	public void simu_with_para(int capacity, int processor, int limit,double mu, double lambda_user)
	{
		this.capacity=capacity;
		this.processor=processor;
		this.limit_block=limit;
		this.mu=mu;
		this.lambda_user=lambda_user;
		run_ten();
	}
	public void run()
	{
		//this.Elist.insert(clock+rd.exp_rv(lambda_user),ARR,0);
		//		if(rd.random_1divide2()==0)     //random pick one from admin/user
		//		{
		Elist.insert(clock+rd.exp_rv(lambda_user),ARR,0); //  generate first arrival
		//		}
		//		else 
		//		{
		Elist.insert(clock+rd.exp_rv(lambda_admin),ARR,1); //  generate first arrival
		//		}
		while (!done)
		{
			//Event e_temp = new Event(Elist.getfirst().getTime(),Elist.getfirst().getType(),Elist.getfirst().getPriority());               // Get next Event from list
			Event e_temp = Elist.getfirst(); //return address
			double prev = clock;                      // Store old clock value
			clock = e_temp.getTime();                 // Update system clock 

			switch (e_temp.getType()) 
			{
			case 1:                                 	// If arrival 
				arrival_num[e_temp.priority]++;
				EN += N*(clock-prev);                   //  update system statistics
				double judge = rd.random_1divide2();
				if(e_temp.priority==0)   //generate according to priority
				{
					Elist.insert(clock+rd.exp_rv(lambda_user),ARR,0); //  generate next arrival
				}
				else
				{
					Elist.insert(clock+rd.exp_rv(lambda_admin),ARR,1); //  generate next arrival
				}

				if(N >= capacity)    						// Capacity
				{
					block_num[e_temp.priority]++;
					break;
				}
				if(N >= limit_block && e_temp.getPriority() < 1) //more than L
				{
					block_num[e_temp.priority]++;
					break;
				}
				N++;                                    //  update system size

				if (N <= processor) 
				{                             
					Elist.insert(clock+rd.exp_rv(mu),DEP,e_temp.getPriority());   //  generate its departure event
				} 
				break;
			case 0:                                 // If departure
				EN += N*(clock-prev);                   //  update system statistics
				N--;                                    //  decrement system size
				Ndep++;                                 //  increment num. of departures
				if (N >= processor) {                            // If customers r-emain
					Elist.insert(clock+rd.exp_rv(mu),DEP,Elist.find_next_arr().priority);   //  generate next departure
				} 
				break;
			}
			Elist.delete();//delete CurrentEvent;
			if (Ndep > 100000 ) done=true;        // End condition
		}
		System.out.println("ρ:"+lambda_admin/mu/processor);
		System.out.println("lam admin:"+lambda_admin);
		System.out.println("clock:"+clock);
		System.out.println("E(n):"+EN/clock);
		System.out.println("E(t):"+EN/((double)Ndep));
		System.out.println("p(block):"+(block_num[0]+block_num[1])/(arrival_num[0]+arrival_num[1]));
		System.out.println(block_num[0]+" "+block_num[1]+" "+arrival_num[0]+" "+arrival_num[1]);
		System.out.println("p(bolck_admin):"+block_num[1]/arrival_num[1]);
		System.out.println("p(bolck_user):"+block_num[0]/arrival_num[0]);
		System.out.println("==========================");
		
	}
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		Simulation s = new Simulation();
		s.simu_with_para(4,2,2,3.0,4.0);

	}

}
