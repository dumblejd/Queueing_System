package sim;
//Di Jin   dxj170930 UTDID:2021377200
public class Random 
{

	double Seed = 1111.0;

	private double uni_rv()           
	{
	    double k = 16807.0;
	    double m = 2147483647;
	    double rv;
	
	    Seed=(k*Seed)%m;	
	    rv=Seed/m;
	    return(rv);
	}

	double exp_rv(double lambda)
	{
	    double exp;
	    exp = ((-1) / lambda) * Math.log(uni_rv());
	    return(exp);
	}
	double random_posibility(double p)
	{
		return Math.random()>p?0:1;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Random a = new Random();
//		double time = a.exp_rv(4);
//		 time = a.exp_rv(3);

		System.out.println(Math.random());
	}
}
