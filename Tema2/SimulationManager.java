import java.util.*;
import javax.swing.*;

public class SimulationManager implements Runnable {
	private int timeLimit;
	private int maxProcessingTime;
	private int minProcessingTime;
	private int numberOfServers;
	private int numberOfClients;
	private int maxTasksPerServer;
	private SelectionPolicy selectionPolicy;

	private Scheduler scheduler;
	private SimulationFrame frame;
	private List<Task> generatedTasks=new ArrayList<Task>();
	
	public SimulationManager()
	{

	}
	public void generate()
	{
		scheduler=new Scheduler(numberOfServers,maxTasksPerServer);
		scheduler.changeStrategy(selectionPolicy);
		frame=new SimulationFrame("Simulare");
		generateNRandomTasks();
	}
	private void generateNRandomTasks()
	{
		int i;
		int contor=0;
		Random randomGenerator=new Random();
		for(i=0;i<numberOfClients;i++)
		{
			if(contor<timeLimit)
			{
				contor++;
			}
			int arrivalTime=randomGenerator.nextInt(contor);
			int processingTime=randomGenerator.nextInt(maxProcessingTime-minProcessingTime+1)+minProcessingTime;
			generatedTasks.add(new Task(arrivalTime,processingTime));
			
		}
		generatedTasks.sort(null);
		
	}
	@Override
	public void run()
	{
		int currentTime=0;
		int contor=0;
		int ok=0;
		int noOfServers=numberOfServers;
		int peekTime=0;
		int max=Integer.MIN_VALUE;
		int[] cozi=new int[Scheduler.NR_MAXIM_SERVERE_DESCHISE];
		int[] clienti=new int[Scheduler.NR_MAXIM_SERVERE_DESCHISE];
 		int[] v=new int[numberOfClients];
		int[] u=new int[numberOfClients];
		try {
		
			while(ok==0 || currentTime<timeLimit)
			{	ok=1;
				Iterator<Task> i=generatedTasks.iterator();
				while(i.hasNext())
				{
					Task t=i.next();
					if(currentTime==t.getArrivalTime())
					{
							int index=scheduler.dispatchTask(t);
							if(index>=noOfServers)
							{
								frame.displayLog("S-a deschis un server nou la timpul "+currentTime+"\n");
								noOfServers++;
							}
							int finishTime=t.getFinishTime();
							v[contor]=finishTime;
							u[contor]=index;
							cozi[index]=cozi[index]+scheduler.getServers().get(index).getWaitingPeriod().intValue();
							clienti[index]++;
							frame.displayLog("In: Taskul "+contor+" la  serverul "+index+" cu arrival time "+t.getArrivalTime()+" si processing time "+t.getProcessingTime()+"\n");
							i.remove();
							contor++;		
					}			
				}	
			int suma=0;
			for(int j=0;j<numberOfClients;j++)
			{
				if(v[j]!=0 && v[j]==currentTime)
				{
				frame.displayLog("Out: Taskul "+j+" din serverul "+u[j]+" cu finish time "+v[j]+"\n");
				}	
			}	
			for(int j=0;j<scheduler.getNumberOfServers();j++)
			{
				suma=suma+scheduler.getServers().get(j).getTasks().length;
				frame.dispalyData(scheduler.getServers().get(j).getTasks(), j);	
			}
			if(suma>max)
			{
				max=suma;
				peekTime=currentTime;
			}
			frame.displayTime(currentTime);
			currentTime++;
			Thread.sleep(1000);
			for(Server s: scheduler.getServers())
			{
				if(s.getTasks2().isEmpty()==false)
				{
					ok=0;
				}
			}
			
			}
		}
		 catch (InterruptedException e) {
			e.printStackTrace();
		}
		for(int j=0;j<numberOfClients;j++)
		{
			if(v[j]!=0 && v[j]==currentTime)
			{
			frame.displayLog("Out: Taskul= "+j+" din serverul "+u[j]+" cu finish time "+v[j]+"\n");
			}	
		}
		int suma=0;
		for(int j=0;j<scheduler.getNumberOfServers();j++)
		{
			frame.dispalyData(scheduler.getServers().get(j).getTasks(), j);	
			frame.displayLog("Timpul mediu de asteptare pt serverul server: "+j+ " este  "+(float)cozi[j]/clienti[j]+"\n");
			suma=suma+cozi[j];
		}
		frame.displayLog("Timpul mediu de astepatare per total este: "+(float)suma/numberOfClients+"\n");
		frame.displayLog("Ora de varf este la timpul: "+peekTime+"\n");
		frame.displayTime(currentTime);	
	} 
	
	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}
	public void setMaxProcessingTime(int maxProcessingTime) {
		this.maxProcessingTime = maxProcessingTime;
	}
	public void setMinProcessingTime(int minProcessingTime) {
		this.minProcessingTime = minProcessingTime;
	}
	public void setNumberOfServers(int numberOfServers) {
		this.numberOfServers = numberOfServers;
	}
	public void setNumberOfClients(int numberOfClients) {
		this.numberOfClients = numberOfClients;
	}
	public void setMaxTasksPerServer(int maxTasksPerServer) {
		this.maxTasksPerServer = maxTasksPerServer;
	}
	public void setSelectionPolicy(SelectionPolicy selectionPolicy) {
		this.selectionPolicy = selectionPolicy;
	}
	public static void main(String[] args)
	{
		JFrame frame2=new SimulationControl("Control");
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.pack();
		frame2.setVisible(true);	
	}
}
