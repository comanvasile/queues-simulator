import javax.swing.*;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;


public class SimulationFrame extends JFrame {
	private static final long serialVersionUID=1L;
	private JPanel panel=new JPanel(new GridBagLayout());
	GridBagConstraints c=new GridBagConstraints();
	private JTextArea[] servers=new JTextArea[Scheduler.NR_MAXIM_SERVERE_DESCHISE];
	private JTextArea log;
	private JTextArea time;

	public SimulationFrame(String name)
	{
		super(name);
		for(int i=0;i<Scheduler.NR_MAXIM_SERVERE_DESCHISE;i++)
		{
			c.gridx=i;
			c.gridy=4;
			servers[i]=new JTextArea("Server numarul: "+i+"\n");
			servers[i].setRows(20);
			servers[i].setColumns(20);
			servers[i].setVisible(false);
			panel.add(servers[i],c);
		}
		log=new JTextArea("Log file:\n");
		c.gridx=0;
		c.gridy=5;
		log.setColumns(20);
		log.setRows(20);
		JScrollPane p=new JScrollPane(log);
		panel.add(p,c);
		time=new JTextArea("Current time: ");
		c.gridx=1;
		c.gridy=5;
		panel.add(time,c);
		JScrollPane p2=new JScrollPane(panel);
		this.add(p2);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);	
	}
	
	public void dispalyData(Task [] tasks,int index)
	{
		servers[index].setText("Serverul numarul "+index+"\n");
		servers[index].setVisible(true);
		if(tasks!=null)
		{
			for(Task t: tasks)
			{
				servers[index].append(t.toString());
				servers[index].repaint();
				servers[index].revalidate();
			}
		}
	}
	public void displayTime(int time)
	{
		this.time.setText("Timpul curent: "+time+"\n ");
		this.time.repaint();
		this.time.revalidate();
	}
	public void displayLog(String s)
	{
		log.append(s);
		log.repaint();
		log.revalidate();
	}


}
