import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.*;

import javax.swing.*;

public class SimulationControl extends JFrame implements ActionListener {
	private static final long serialVersionUID=1L;
	private JPanel panel=new JPanel(new GridBagLayout());
	GridBagConstraints c=new GridBagConstraints();
	private JLabel l1,l2,l3,l4,l5,l6,l7;
	private JTextField tf1,tf2,tf3,tf4,tf5,tf6;
	private JButton btn1;
	private JComboBox<String> cb;
	private SimulationManager gen;
	public SimulationControl(String nume)
	{
		super(nume);
		l1=new JLabel("Time Limit");
		c.gridx=0;
		c.gridy=0;
		panel.add(l1,c);
		l2=new JLabel("Max Processing Time");
		c.gridx=0;
		c.gridy=1;
		panel.add(l2,c);
		l3=new JLabel("Min Processing Time");
		c.gridx=0;
		c.gridy=2;
		panel.add(l3,c);
		tf1=new JTextField(10);
		c.gridx=1;
		c.gridy=0;
		panel.add(tf1,c);
		tf2=new JTextField(10);
		c.gridx=1;
		c.gridy=1;
		panel.add(tf2,c);
		tf3=new JTextField(10);
		c.gridx=1;
		c.gridy=2;
		panel.add(tf3,c);
		l4=new JLabel("Number Of Servers");
		c.gridx=2;
		c.gridy=0;
		panel.add(l4,c);
		l5=new JLabel("Number Of Clients");
		c.gridx=2;
		c.gridy=1;
		panel.add(l5,c);
		l6=new JLabel("Max Tasks Per Server");
		c.gridx=2;
		c.gridy=2;
		panel.add(l6,c);
		tf4=new JTextField(10);
		c.gridx=3;
		c.gridy=0;
		panel.add(tf4,c);
		tf5=new JTextField(10);
		c.gridx=3;
		c.gridy=1;
		panel.add(tf5,c);
		tf6=new JTextField(10);
		c.gridx=3;
		c.gridy=2;
		panel.add(tf6,c);
		btn1=new JButton("Start Simulare");
		btn1.addActionListener(this);
		c.gridx=4;
		c.gridy=1;
		panel.add(btn1,c);
		l7=new JLabel("Change Policy");
		c.gridx=4;
		c.gridy=0;
		panel.add(l7);
		cb=new JComboBox<String>();
		c.gridx=5;
		c.gridy=0;
		cb.addItem(SelectionPolicy.SHORTEST_QUEUE.toString());
		cb.addItem(SelectionPolicy.SHORTEST_TIME.toString());
		panel.add(cb,c);
		
		
		this.add(panel);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source=e.getSource();
		if(source==btn1)
		{
		gen=new SimulationManager();
		gen.setTimeLimit(new Integer(tf1.getText()));
		gen.setMaxProcessingTime(new Integer(tf2.getText()));
		gen.setMinProcessingTime(new Integer(tf3.getText()));
		gen.setNumberOfServers(new Integer(tf4.getText()));
		gen.setNumberOfClients(new Integer(tf5.getText()));
		gen.setMaxTasksPerServer(new Integer(tf6.getText()));
		if(cb.getSelectedItem()=="SHORTEST_QUEUE")
		{
			gen.setSelectionPolicy(SelectionPolicy.SHORTEST_QUEUE);
		}
		else
		{
			gen.setSelectionPolicy(SelectionPolicy.SHORTEST_TIME);
		}
		
		gen.generate();
		Thread t=new Thread(gen);
		t.start();
		this.dispose();
		}
	}
}
