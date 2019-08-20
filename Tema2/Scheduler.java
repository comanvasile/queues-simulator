import java.util.*;

public class Scheduler {
	public static final int NR_MAXIM_SERVERE_DESCHISE  = 6;
	private List<Server> servers;
	private int initialNoServers;
	private int maxTasksPerServer;
	private Strategy strategy;
	public Scheduler(int initialNoServers,int maxTasksPerServer)
	{	
		
		this.initialNoServers=initialNoServers;
		this.maxTasksPerServer=maxTasksPerServer;
		servers=new ArrayList<Server>(this.initialNoServers);
		int i;
		for(i=0;i<this.initialNoServers;i++)
		{
			Server s=new Server(this.maxTasksPerServer);
			servers.add(s);
			Thread t=new Thread(s);
			t.start();
		}
	}
	public void changeStrategy(SelectionPolicy policy)
	{
		if(policy==SelectionPolicy.SHORTEST_QUEUE)
		{
			strategy=new ConcreteStrategyQueue(maxTasksPerServer);
		}
		if(policy==SelectionPolicy.SHORTEST_TIME)
		{
			strategy=new ConcreteStrategyTime(maxTasksPerServer);
		}
	}	
	public int dispatchTask(Task t)
	{ 	int nrTasks=0;
		for(Server s: servers)
		{
			nrTasks=nrTasks+s.getTasks().length;
		}
	
		if(servers.size()<NR_MAXIM_SERVERE_DESCHISE && nrTasks>=servers.size()* maxTasksPerServer)
		{
				
				Server s=new Server(maxTasksPerServer);
				servers.add(s);
				Thread th=new Thread(s);
				th.start();
		}
		
			return strategy.addTask(servers, t);	
	}
	public List<Server> getServers()
	{
		return servers;
	}
	public int getNumberOfServers()
	{
		return servers.size();
	}
	
}
