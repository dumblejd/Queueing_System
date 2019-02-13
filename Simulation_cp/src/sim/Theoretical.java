package sim;

public class Theoretical {

	double lem_user=4;
	double lem_admin=6;
	double mu=3;

	double A1;
	double A2;
	double A3;
	public void cal_p()
	{
		A1=lem_admin;
		A2=lem_user;
		A3=mu;
		double p0=1.0/(1+(A1+A2)/A3+(A1+A2)*(A1+A2)/(2*A3*A3)+A1*(A1+A2)*(A1+A2)/(2*A3*A3*2*A3)+A1*A1*(A1+A2)*(A1+A2)/(2*A3*A3*2*A3*2*A3));
		double p1=p0*((A1+A2)/A3);
		double p2=p0*((A1+A2)*(A1+A2)/(2*A3*A3));
		double p3=p0*(A1*(A1+A2)*(A1+A2)/(2*A3*A3*2*A3));
		double p4=p0*(A1*A1*(A1+A2)*(A1+A2)/(2*A3*A3*2*A3*2*A3));
		double EN=p1+2*p2+3*p3+4*p4;
		double Et=EN/(p0*(A1+A2)+p1*(A1+A2)+(p2+p3)*(A1));
		//System.out.println(p0);
		double block_user=p2+p3+p4;
		double block_admin=p4;
		System.out.println("EN:"+EN);
		System.out.println("Et:"+Et);
		System.out.println("E block user:"+block_user);
		System.out.println("E block admin:"+block_admin);
		System.out.println("//==========");
		
	}
	public void ten()
	{
		for(int i=1;i<=10;i++)
		{
			this.lem_admin=0.6*i;
			cal_p();
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Theoretical a =new Theoretical();
		//a.cal_p();
		a.ten();
	}

}
