package sim;
//Di Jin   dxj170930 UTDID:2021377200
public class Simulation {
	EventList Elist;                // Create event list

	double mu1 = 40; 
	double mu2 = 50;
	double lambda = 1.0;          
    double ph = 0.4;     //pl
    double pl = 0.6;
    double pa1 = 0.5;    //a2
    double pa2 = 0.5;
    double pr2d = 0.25;  //r21
    double pr21 = 0.75;
	// Service rate
    double []highinqueue = {0,0};   // processor's high in queue 
    double [][]numinqueue_now = {{0,0},{0,0}};   // total count of num in queue now
    double [][]getoutnum = {{0,0},{0,0}};   //number of getting in two processor
    double [][]depart = {{0,0},{0,0}};  //num of depart
    double [][]EN = {{0,0},{0,0}};
    
    double num_depart_sys=0;
    double []num_dep_pro2 = {0,0};
    double num_processor_1 = 0;
    double num_processor_2 = 0;
    double n1=0;
    double n2=0;
    
	double clock = 0.0;             // System clock
	double []arrival_num = {0.0,0.0};
	double []block_num = {0.0,0.0};
	boolean done = false;                   // End condition satisfied?
	Random rd= new Random();
	//0:outside 1:processor1 2: processore2 3:endout
	//arr_01_high 
	//arr_01_low  
	//arr_02_high
	//dep_12_high 
	//dep_12_low
	//dep_21_high
	//dep_21_low
	//dep_23_low
	//dep_23_high
	Simulation()
	{
		this.Elist=new EventList();
	}
//	public void run_ten()
//	{
//
//		for(int i=1;i<=10;i++)
//		{
//			Simulation s = new Simulation();
//			s.lambda_admin = i*0.1*processor*mu;
//			s.run();
//		}
//	}
	public void simu_with_para(double ph,double pl,double pa1,double pa2,double pr2d,double pr21,double mu1,double mu2,double lambda)
	{
		this.ph=ph;
		this.pl=pl;
		this.pa1=pa1;
		this.pa2=pa2;
		this.pr2d=pr2d;
		this.pr21=pr21;
		this.mu1=mu1;
		this.mu2=mu2;
		this.lambda=lambda;
	}
	
	public String generateenter(double phigh,double p01)
	{
		String temp = rd.random_posibility(phigh)>0?"high":"low";
		String temp2=null;
		if(temp.equals("high"))
		{
		temp2 = rd.random_posibility(p01)>0?"01":"02";
		}
		else
		{
		temp2 = "01";
		}
		return "arr_"+temp2+"_"+temp;
	}
	public String generateexit(String priority,double p2d)
	{
		String temp2=null;
		 
		temp2 = rd.random_posibility(p2d)==1?"23":"21";
		return "dep_"+temp2+"_"+priority;
	}
//	public String generatePath_enter(double a1)
//	{
//		double temp = rd.random_posibility(pa1);
//		return temp>0?"01":"02";
//	}
	//0:outside 1:processor1 2: processore2 3:endout
			//1 arr_01_high   
			//2 arr_01_low  
			//3 arr_02_high
			//4 dep_12_high 
			//5 dep_12_low
			//6 dep_21_high
			//7 dep_21_low
			//8 dep_23_high
			//9 dep_23_low
	public void run()
	{
		int count=0;
		Elist.insert(clock+rd.exp_rv(lambda*ph),"arr_01_high"); //  generate first arrival
		Elist.insert(clock+rd.exp_rv(lambda*pl),"arr_01_low"); //  generate first arrival
		while (!done)
		{
			Event e_temp = Elist.getfirst(); //return address
			double prev = clock;                      // Store old clock value
			clock = e_temp.getTime();                 // Update system clock
			
			EN[0][0]+= numinqueue_now[0][0]*(clock-prev);
			EN[0][1]+= numinqueue_now[0][1]*(clock-prev);
			EN[1][0]+= numinqueue_now[1][0]*(clock-prev);
			EN[1][1]+= numinqueue_now[1][1]*(clock-prev);

			switch (e_temp.type) 
			{
				case "arr":                                 	// If arrival
					//Elist.insert(clock+rd.exp_rv(lambda),generateenter(ph,pa1)); //insert arrival
						if(e_temp.priority.equals("high")) //11111111111111111111111arr_01_high  
						{
							Elist.insert(clock+rd.exp_rv(lambda*ph),"arr_01_high"); //insert arrival
							if(rd.random_posibility(pa1)==1)
							{
								n1++;
								numinqueue_now[0][1]++;
								if(num_processor_1<1)
								{
									num_processor_1++;
									Elist.insert(clock+rd.exp_rv(mu1),"dep_12_high"); //depart
								}
							}
							else   //3333333333333333333333
							{
								n2++;
								numinqueue_now[1][1]++;
								if(num_processor_2<1)
								{
									num_processor_2++;
									Elist.insert(clock+rd.exp_rv(mu2),generateexit("high",pr2d)); //depart
								}
							}
						}
						else if(e_temp.priority.equals("low"))  //2222222222222222arr_01_low
						{
							n1++;
							Elist.insert(clock+rd.exp_rv(lambda*pl),"arr_01_low"); //insert arrival
							//getinnum[0][0]++;
							numinqueue_now[0][0]++;
							if(num_processor_1<1)
							{
								num_processor_1++;
								if(numinqueue_now[0][1]>0)
								{
								Elist.insert(clock+rd.exp_rv(mu1),"dep_12_high"); //depart
								}
								else if(numinqueue_now[0][0]>0)
								{
								Elist.insert(clock+rd.exp_rv(mu1),"dep_12_low"); //depart
								}
							}
						}
				break;
				case "dep":                                 // If departure
					//dep_12_high 
					//dep_12_low
					//dep_21_high
					//dep_21_low
					//dep_23_high
					//dep_23_low
					if(e_temp.path.equals("12"))
					{ 
						n1--;
						n2++;
						num_processor_1--;
						if(e_temp.priority.equals("high")) //44444444444444   dep_12_high
						{
							getoutnum[0][1]++;
							//getinnum[1][1]++;
							numinqueue_now[0][1]--;
							numinqueue_now[1][1]++;
							
							if(n1>0&&num_processor_1<1)
							{
								num_processor_1++;
								if(numinqueue_now[0][1]>0)
								{
								Elist.insert(clock+rd.exp_rv(mu1),"dep_12_high"); //depart
								}
								else if(numinqueue_now[0][0]>0)
								{
								Elist.insert(clock+rd.exp_rv(mu1),"dep_12_low"); //depart
								}
							}
							
							
							if(num_processor_2<1)
							{
								num_processor_2++;
								Elist.insert(clock+rd.exp_rv(mu2),generateexit("high",pr2d)); //depart
							}
						}
						else if(e_temp.priority.equals("low")) //55555555555555dep_12_low
						{
							//getinnum[1][0]++;
							getoutnum[0][0]++;
							numinqueue_now[0][0]--;
							numinqueue_now[1][0]++;
							
							if(n1>0&&num_processor_1<1)
							{
								num_processor_1++;
								if(numinqueue_now[0][1]>0)
								{
								Elist.insert(clock+rd.exp_rv(mu1),"dep_12_high"); //depart
								}
								else if(numinqueue_now[0][0]>0)
								{
								Elist.insert(clock+rd.exp_rv(mu1),"dep_12_low"); //depart
								}
							}
							
							
							if(num_processor_2<1)
							{
								num_processor_2++;
								if(numinqueue_now[1][1]>0)
								{
								Elist.insert(clock+rd.exp_rv(mu2),generateexit("high",pr2d)); //depart
								}
								else if(numinqueue_now[1][0]>0)
								{
								Elist.insert(clock+rd.exp_rv(mu2),generateexit("low",pr2d)); //depart
								}
							}
							
						}
					}
					else if(e_temp.path.equals("21"))
					{
						n2--;
						n1++;
						num_processor_2--;
						
						if(e_temp.priority.equals("high")) //66666666666666dep_21_high
						{
							num_dep_pro2[1]++;
							getoutnum[1][1]++;
							//getinnum[0][1]++;
							numinqueue_now[1][1]--;
							numinqueue_now[0][1]++;
							
							
							if(n2>0&&num_processor_2<1)
							{
								num_processor_2++;
								if(numinqueue_now[1][1]>0)
								{
								Elist.insert(clock+rd.exp_rv(mu2),generateexit("high",pr2d)); //depart
								}
								else if(numinqueue_now[1][0]>0)
								{
								Elist.insert(clock+rd.exp_rv(mu2),generateexit("low",pr2d)); //depart
								}
							}
							
							if(num_processor_1<1)
							{
								num_processor_1++;
								Elist.insert(clock+rd.exp_rv(mu1),"dep_12_high"); //depart
							}
						}
						else if(e_temp.priority.equals("low"))//7777777777777dep_21_low
						{
							num_dep_pro2[0]++;
							getoutnum[1][0]++;
							//getinnum[0][0]++;
							numinqueue_now[1][0]--;
							numinqueue_now[0][0]++;
							
							if(n2>0&&num_processor_2<1)
							{
								num_processor_2++;
								if(numinqueue_now[1][1]>0)
								{
								Elist.insert(clock+rd.exp_rv(mu2),generateexit("high",pr2d)); //depart
								}
								else if(numinqueue_now[1][0]>0)
								{
								Elist.insert(clock+rd.exp_rv(mu2),generateexit("low",pr2d)); //depart
								}
							}
							
							if(num_processor_1<1)
							{
								num_processor_1++;
								if(numinqueue_now[0][1]>0)
								{
								Elist.insert(clock+rd.exp_rv(mu1),"dep_12_high"); //depart
								}
								else if(numinqueue_now[0][0]>0)
								{
								Elist.insert(clock+rd.exp_rv(mu1),"dep_12_low"); //depart
								}
							}
						}
					}
					else if(e_temp.path.equals("23"))
					{
						n2--;
						num_processor_2--;
						if(e_temp.priority.equals("high")) //888888888888888dep_23_high
						{
							getoutnum[1][1]++;
							numinqueue_now[1][1]--;
							num_depart_sys++;
							num_dep_pro2[1]++;
							
							if(n2>0&&num_processor_2<1)
							{
								num_processor_2++;
								if(numinqueue_now[1][1]>0)
								{
								Elist.insert(clock+rd.exp_rv(mu2),generateexit("high",pr2d)); //depart
								}
								else if(numinqueue_now[1][0]>0)
								{
								Elist.insert(clock+rd.exp_rv(mu2),generateexit("low",pr2d)); //depart
								}
							}
						}
						else if(e_temp.priority.equals("low"))//99999999999999dep_23_low
						{
							getoutnum[1][0]++;
							numinqueue_now[1][0]--;
							num_depart_sys++;
							num_dep_pro2[0]++;
							
							if(n2>0&&num_processor_2<1)
							{
								num_processor_2++;
								if(numinqueue_now[1][1]>0)
								{
								Elist.insert(clock+rd.exp_rv(mu2),generateexit("high",pr2d)); //depart
								}
								else if(numinqueue_now[1][0]>0)
								{
								Elist.insert(clock+rd.exp_rv(mu2),generateexit("low",pr2d)); //depart
								}
							}
						}
					}
				break;
			}
			count++;
			Elist.delete();//delete CurrentEvent;
			//System.out.println(temp_n);
			//System.out.println(num_depart_sys);
			//System.out.println(num_processor_1);
			if (num_depart_sys > 500000 ) done=true;        // End condition
		}
		
		//double the_2h=lambda*ph*pa2+the_1h;
		//double the_1h=lambda*ph*pa1+the_2h*pr21;
		//double the_2l=the_1l;
		//double the_1l=lambda*pl+the_2l*pr21;
		double the_2h = lambda*(ph*pa1+ph*pa2)/(1-pr21);
		double the_1h = lambda*ph*pa1+the_2h*pr21;
		double the_1l = lambda*pl/(1-pr21);
		double the_2l = the_1l;
		double rou1 = (the_1h+the_1l)/mu1;
		double rou2 = (the_2h+the_2l)/mu2;
		double expectedN1 = rou1/(1-rou1);
		double expectedN2 = rou2/(1-rou2);
		
		//System.out.println(expectedN2);
		//System.out.println(EN[1][0]/num_dep_pro2[0]);
		
		System.out.println("Theoretical through put in first high: "+the_1h+"   low: "+the_1l);
		System.out.println("Theoretical through put in second high: "+the_2h+"   low: "+the_2l);
		System.out.println("*****************theoretical↑");
		//System.out.println(getoutnum[0][0]+"   "+getoutnum[0][1]+"   "+ getoutnum[1][0]+"  "+getoutnum[1][1]);
		System.out.println("through put in first high: "+getoutnum[0][1]/clock+"   low: "+getoutnum[0][0]/clock);
		System.out.println("through put in second high: "+getoutnum[1][1]/clock+"   low: "+getoutnum[1][0]/clock);
		System.out.println("================");
		System.out.println("Theoretical EN of first: "+expectedN1);
		System.out.println("Theoretical EN of second: "+expectedN2);
		System.out.println("*****************theoretical↑");
		System.out.println("EN in first: "+((EN[0][1]+EN[0][0])/clock));
		System.out.println("EN in second: "+((EN[1][0]+EN[1][1])/clock));
		System.out.println("================");
		System.out.println("EN in first high: "+EN[0][1]/clock+"   low: "+EN[0][0]/clock);
		System.out.println("EN in second high: "+EN[1][1]/clock+"   low: "+EN[1][0]/clock);
		System.out.println("================");
		System.out.println("service time of high in queue 2: "+EN[1][1]/num_dep_pro2[1]);
		System.out.println("service time of low in queue 2: "+EN[1][0]/num_dep_pro2[0]);
	}
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		Simulation s = new Simulation();
		//s.simu_with_para(0.4,0.6,0.5,0.5,0.25,0.75,40,50,1);
		for(int i=1;i<=10;i++)
		{
			System.out.println("入="+i+"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			s=new Simulation();
			s.simu_with_para(0.4,0.6,0.5,0.5,0.25,0.75,40,50,i);
			s.run();
		}
	}

}
