
public class AssemblyLines {

	int[][] a;		//time at single station
	int[][] t;		//times to switch between assembly lines 
	int e1, e2;		// entry time of chasis in a line 1 and 2 
	int x1, x2=0;	// exit time of complete car from line 1 and 2
	int n=0;		// total number stations in single line 
	
	
	int[] f1;		//fastest possible time to get a chasis from starting point through station S(1,j)
	int[] f2;		//fastest possible time to get a chasis from starting point through station S(2,j)
	public int f=0;		//fastest time to get a chasis all the way through the factory
	
	int[] l1;		//fastest path involving line 1 , whose station S(j-1) is used in a fastest way through station S(1,j)
	int[] l2;		//fastest path involving line 2 , whose station S(j-1) is used in a fastest way through station S(2,j)
	int l=0;		//line, whose station n is used in a fastest way through the entire factory
	
	
	public AssemblyLines(int[][] sTimes,int[][] swT, int[] enT, int[] exT, int stations)
	{
		
		a = new int [2][stations];
		t = new int [2][stations];
		for(int i=0; i<2; i++)
		{
			for(int j=0; j<stations; j++)
			{
				a[i][j]=sTimes[i][j];
				t[i][j]=swT[i][j];
			}
		}
		
		e1 = enT[0];
		e2 = enT[1];
		x1 = exT[0];
		x2 = exT[1];
		n = stations;
		
		
		f1 = new int [stations];
		f2 = new int [stations];
		
		l1 = new int [stations];
		l2 = new int [stations];
	}
	
	
	public int[] AssemblyLineOptimization()
	{
		
		f1[0] = e1 + a[0][0];				//time to get through station 1 on line 1
		f2[0] = e2 + a[1][0];				//time to get through station 1 on line 2
		
		
		
		for(int j=1; j<n; j++)
		{
			
			if(f1[j-1]+a[0][j] <= f2[j-1]+ t[1][j-1] +a[0][j])
			{
				f1[j] = f1[j-1]+a[0][j];
				l1[j-1]=1;
			}
			else
			{
				f1[j] = f2[j-1]+ t[1][j-1] +a[0][j];
				l1[j-1]=2;
			}
			
			if(f2[j-1]+a[1][j] <= f1[j-1]+ t[0][j-1] +a[1][j])
			{
				f2[j] = f2[j-1]+a[1][j];
				l2[j-1]=2;
			}
			else
			{
				f2[j] = f1[j-1]+ t[0][j-1] +a[1][j];
				l2[j-1]=1;
			}
		}
		
		if (f1[n-1]+ x1 <= f2[n-1] +x2)
		{
			f = f1[n-1]+x1;
			l = 1;
		}
		else
		{
			f = f2[n-1]+x2;
			l = 2;
		}
		
		System.out.println("Final Minimum Cost: "+f);
		System.out.println("Last station is at line: " +l);
		System.out.println("----------------------------");
		

		if(l==1)
		{
			l1[n-1]=l;
			return l1;
		}
		else
		{
			l2[n-1]=2;
			return l2;
		}
	}
}
