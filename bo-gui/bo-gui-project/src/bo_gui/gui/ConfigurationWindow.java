package bo_gui.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import bo_gui.gui.Executor_Thread_Menager;
public class ConfigurationWindow extends JFrame 
								implements ActionListener, ChangeListener 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7106188094123797333L;
	private Executor_Thread_Menager menager;
	private static final int YPOS_MAX = 13;
	private JPanel panel1;
	private JPanel panel2;
	private JPanel panel3;
	
	private JPanel panel_down;
	
	private JButton openBtn, saveBtn, closeBtn, okBtn, addBtn, removeBtn;

	private List<JSpinner> buildings_edit_list;
	
	private List<JSpinner> transmitter_range_list;
	private List<JSpinner> transmitter_cost_list;
	private List<JLabel> transmitter_type_label;
	
	private File filename;
	/*
	 * do obliczania polozenia w gridbagu dla panelu2
	 */
	private int ypos=1,xpos=0,count=0;
	private GridBagConstraints constr;
	private Hashtable<String, List<Float>> optionsTable = null;
	
	private boolean val_changed=false, ignore = false;
	//private List<>
	private JFileChooser fc_OpenSaveFile;
	
	ConfigurationWindow ( Executor_Thread_Menager menager ){
		super("Configure parameters...");
		setSize( 600, 500 );
		setVisible( true );
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	
		this.menager = menager;
		//optionsTable = new Hashtable<String, List<Float>>();
		//Hashtable<String, List<Float>> clone = (Hashtable<String, List<Float>>) menager.getOptTable().clone();
		this.optionsTable = copyHashTable ( menager.getOptTable() );
		//optionsTable = clone;
		
		buildings_edit_list = new ArrayList<JSpinner>();
		transmitter_cost_list = new ArrayList<JSpinner>();
		transmitter_range_list = new ArrayList<JSpinner>();
		transmitter_type_label = new ArrayList<JLabel>();
		
		constr = new GridBagConstraints();
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.gridwidth = 1;
		
		fc_OpenSaveFile = new JFileChooser();
		
		JTabbedPane tabbedPane = new JTabbedPane();
		panel1 = new JPanel();
		tabbedPane.addTab("Buildings", null, panel1,
		                  "Buildings population/profit/");
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

		panel2 = new JPanel();
		tabbedPane.addTab("Transmitter", null, panel2,
		                  "Transmitter types and costs");
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
		
		panel3 = new JPanel();
		tabbedPane.addTab("Open/Save", null, panel3,
		                  "Open/Save from/to file");
		tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
		
		add(tabbedPane);
		panel1.setLayout( new GridBagLayout() );
		
		panel3.setLayout( new GridBagLayout() );
		fillPanel1();
		fillPanel2();
		fillPanel3();
		set_sliders( optionsTable );
	}
	
	public void set_sliders ( Hashtable<String, List<Float>> table ){
		for (int i=0; i< buildings_edit_list.size();i++){
			buildings_edit_list.get(i).removeChangeListener(this);
			buildings_edit_list.get(i).setValue( table.get("profit").get(i) );
			buildings_edit_list.get(i).addChangeListener(this);
		}
		/*
		 * ustawianie innych wart
		 */
		int size = transmitter_range_list.size();
		
		List<Float> cost_list = table.get("cost");
		List<Float> range_list = table.get("range");
		
		if (size != table.get("range").size()){
			for( int i=0; i<size; i++ ){
				panel_down.remove(transmitter_type_label.get(i));
				panel_down.remove(transmitter_range_list.get(i));
				panel_down.remove(transmitter_cost_list.get(i));
			}
			transmitter_cost_list.clear();
			transmitter_range_list.clear();
			transmitter_type_label.clear();
			count = 0;
			ypos = 1;
			xpos = 0;
			for (int i=0; i<range_list.size();i++){
				addTransmitterSpinner( range_list.get(i), cost_list.get(i) );
			}
			transmitter_range_list.get(0).setEnabled(false);
			transmitter_cost_list.get(0).setEnabled(false);
		} else {
			ignore = true;
			for (int i=0; i<size;i++){
				transmitter_range_list.get(i).setValue( range_list.get(i) );
				transmitter_cost_list.get(i).setValue( cost_list.get(i) );
			}
			ignore = false;
		}
	}
	
	private void fillPanel1(){
		int u = 0, h=0;
		int w = 0;
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		c.insets = new Insets(1,1,1,5);
		for ( int i='a'; i<='z'; i++ ){ 
			c.gridx = w;
			c.gridy = h;
			
			JLabel lab = new JLabel(""+(char)i);
			panel1.add(lab,c);
			c.gridx = w+1;
			final SpinnerModel p_model = new SpinnerNumberModel(0, //wart pocz
					0, //min
					50000, //max
					1);
			buildings_edit_list.add( new JSpinner() );
			buildings_edit_list.get(u).setSize(40, 25);
			buildings_edit_list.get(u).setToolTipText("Profit generated by building");
			buildings_edit_list.get(u).setModel(p_model);
			//buildings_edit_list.get(u).setValue( 400 + 50*u);
			panel1.add(buildings_edit_list.get(u),c);
			buildings_edit_list.get(u).setName("build:"+u);
			u++;
			if( 25*h > 275 ){
				w+=2;
				h=0;
			}
			else{
				h++;
			}
		}
		/*
		 * setting initial values and setting listener
		 */
		List<Float> profit = menager.getOptTable().get("profit");
		for (int i=0;i<profit.size();i++){
			if (i < buildings_edit_list.size())
				buildings_edit_list.get(i).setValue(profit.get(i));
				buildings_edit_list.get(i).addChangeListener(this);
		}
	}
	
	private void fillPanel2(){
		//GridBagConstraints c = new GridBagConstraints();
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.insets = new Insets(3,1,1,1);
		constr.gridwidth = 1;
		constr.gridx = 0;
		constr.gridy = 0;
		
		JPanelExtended panel_top = new JPanelExtended();
		panel_top.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		
		
		panel2.setLayout( new BorderLayout() );
		panel2.add(panel_top, BorderLayout.NORTH);
		
		addBtn = new JButton("Add transmitter");
		//panel_top.setLayout(new BorderLayout());
		panel_top.setFillType( GridBagConstraints.HORIZONTAL );
		panel_top.doloz(addBtn);
		addBtn.addActionListener(this);
		
		removeBtn = new JButton(" Remove transmitter");
		panel_top.doloz(removeBtn);
		removeBtn.addActionListener(this);
		
		panel_top.lf();
		
		JLabel label = new JLabel("Syntax: Transmitter Number - Range - Cost");
		panel_top.doloz(label, true);
		
		panel_down = new JPanel();
		panel_down.setLayout( new GridBagLayout() );
		panel2.add(panel_down,BorderLayout.CENTER);
		
	}
	
	private void fillPanel3(){
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		
		openBtn = new JButton( "Open Config File" );
		saveBtn = new JButton( "Save Config to File" );
		closeBtn = new JButton( "Cancel" );
		okBtn = new JButton( "Ok" );
		
		openBtn.addActionListener(this);
		saveBtn.addActionListener(this);
		closeBtn.addActionListener(this);
		okBtn.addActionListener(this);
		
		c.gridx = 0;
		c.gridy = 0;
		panel3.add(openBtn,c);
		
		c.gridx = 1;
		panel3.add(saveBtn,c);
		
		c.gridx = 0;
		c.gridy = 1;
		panel3.add(okBtn,c);
		
		c.gridx = 1;
		panel3.add(closeBtn,c);
	}
	
	private File ShowOpenFilePopup(String title){
		File file= null;
		fc_OpenSaveFile.setDialogTitle(title);
		fc_OpenSaveFile.setCurrentDirectory(new File(System.getProperty("user.dir")));
		int returnVal = fc_OpenSaveFile.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fc_OpenSaveFile.getSelectedFile();
		} else {
			
		}
		return file;
	}

	public void stateChanged(ChangeEvent event) {
		JSpinner source = (JSpinner)event.getSource();
		String str, source_name = source.getName(); 
		if ( source_name.contains("build") ){
			str = source_name.substring(source_name.indexOf(':')+1);
			float u = Float.parseFloat(buildings_edit_list.get(Integer.parseInt(str)).getValue().toString());
			optionsTable.get("profit").set(Integer.parseInt(str), u);
		} else if( !ignore && (source_name.contains("cost") || source_name.contains("range"))){
			str = source_name.substring(source_name.indexOf(':')+1);
			float range = Float.parseFloat(transmitter_range_list.get(Integer.parseInt(str)).getValue().toString());
			float cost = Float.parseFloat(transmitter_cost_list.get(Integer.parseInt(str)).getValue().toString());
			optionsTable.get("range").set(Integer.parseInt(str), range);
			optionsTable.get("cost").set(Integer.parseInt(str), cost);
		}
		if ( !ignore )val_changed = true;
	}
	
	private void addTransmitterSpinner( float rangeVal, float costVal){
		final SpinnerModel r_model = new SpinnerNumberModel(rangeVal, //wart pocz
				0, //min
				500, //max
				1);
		final SpinnerModel c_model = new SpinnerNumberModel(costVal, //wart pocz
				0, //min
				50000, //max
				1);
		
		JLabel lab = new JLabel(""+count);
		JSpinner range_spinner = new JSpinner();
		JSpinner cost_spinner = new JSpinner();
		
		cost_spinner.setModel(c_model);
		range_spinner.setModel(r_model);
		
		//cost_spinner.setValue(costVal);
		//range_spinner.setValue(rangeVal);
		
		transmitter_type_label.add(lab);
		transmitter_cost_list.add(cost_spinner);
		transmitter_range_list.add(range_spinner);
		
		cost_spinner.setName("cost:"+count);
		range_spinner.setName("range:"+count);
		
		cost_spinner.addChangeListener(this);
		range_spinner.addChangeListener(this);
		
		constr.gridx = xpos;
		constr.gridy = ypos;
		
		panel_down.add( lab, constr );
		
		constr.gridx = xpos+1;
		panel_down.add( range_spinner, constr );
		
		constr.gridx = xpos+2;
		panel_down.add( cost_spinner, constr );
		
		this.repaint();
		count++;
		if( ypos >  YPOS_MAX ){
			ypos=1;
			xpos+=5;
		} else{
			ypos++;
		}
	}
	
	public void actionPerformed(ActionEvent event) {
		if ( event.getSource() == addBtn ){
			addTransmitterSpinner(5, 500);
			optionsTable.get("range").add(5.0f);
			optionsTable.get("cost").add(500.0f);
			val_changed = true;
		} 
		else if ( event.getSource() == removeBtn ){
			if( transmitter_type_label.size() != 1 ){
				int last = transmitter_type_label.size()-1;
				
				panel_down.remove(transmitter_type_label.get(last));
				panel_down.remove(transmitter_range_list.get(last));
				panel_down.remove(transmitter_cost_list.get(last));
				
				transmitter_type_label.remove(last);
				transmitter_range_list.remove(last);
				transmitter_cost_list.remove(last);
				
				optionsTable.get("range").remove(last);
				optionsTable.get("cost").remove(last);
				
				this.repaint();
				val_changed = true;
				count--;
				if( ypos <  1){
					ypos=YPOS_MAX;
					xpos-=5;
				} else{
					ypos--;
				}
			}
		} 
		else if ( event.getSource() == openBtn ){
			File file = ShowOpenFilePopup("Open Config File");
			if (file != null ) if (parseConfigFile(file) ) val_changed = false;
		} else if( event.getSource() == okBtn ) {
			Hashtable<String, List<Float>> clone = copyHashTable ( optionsTable );
			/*
			 * przekazanie informacji o opcjach do menagera
			 */
			if ( val_changed ){
				/*
				 * to zapisz to pliku config_auto i ten zwroc
				 */
				File file = ShowOpenFilePopup("Save current config to file...");
				if (file != null && file.getName() != "" ) {
					saveConfigToFile(file);
					filename = file;
					val_changed = false;
					menager.print_debug_info("[C] Config saved..");
				}
			} 
			if ( filename != null ) 
					menager.setOptionFileName(filename.getAbsolutePath());
			if ( clone != null && filename!= null ) {
				menager.setOptTable(clone);
				menager.print_debug_info("[C] Config accepted");
			}
			this.setVisible(false);
			val_changed = false;
		} else if ( event.getSource() == closeBtn ){
			this.setVisible(false);
			val_changed = false;
		} else if ( event.getSource() == saveBtn ){
			File file = ShowOpenFilePopup("Save current config to file...");
			if (file != null && file.getName() != "" ) {
				saveConfigToFile(file);
				filename = file;
			}
		}
	}
	
	private boolean saveConfigToFile(File file){
		try {
		       PrintWriter out =
		         new PrintWriter (new BufferedWriter (new FileWriter (file)));
		       List<Float> lista;
		       String[] opcje = {"range","cost","profit"};
		       for(int u=0;u<3;u++){
		    	   lista = optionsTable.get(opcje[u]);
		    	   out.print(opcje[u]+":");
		    	   for(int i=0;i<lista.size();i++){
		    		   out.print(lista.get(i)+";");
		    	   }
		    	   out.print("\n");
		       }
		       out.flush();
		       out.close();
		    }
		    catch (IOException e) {
		       return false;
		    }
		    return true;
	}
	
	private boolean parseConfigFile(File filename){
		String temp,input,optName="range";
		BufferedReader in = null;
		int end_index=0, start_index=0,cnt=0, size=0;
		char znak = ':';
			
		List<Float> range = new ArrayList<Float>();
		List<Float> cost = new ArrayList<Float>();
		List<Float> profit = new ArrayList<Float>();
		
		try {
			//System.out.println(filename);
			in = new BufferedReader(new FileReader(filename.getAbsolutePath()));
		} catch (FileNotFoundException e) {
			menager.print_debug_info("[C] Config file not found!");
			e.printStackTrace();
		}
		try {
			while( (input = in.readLine()) != null){
				cnt = 0;
				znak = ':';
				start_index = 0;
				end_index = 0;
				do {
					size = input.length();
					end_index = input.indexOf(znak,start_index);
					temp = input.substring(start_index, end_index);
					if ( cnt == 0){
						znak = ';';
						optName = temp.toString();
					}
					else{
						//System.out.println(temp);
						if ( optName.equals("profit") )
						//optionsTable.get(optName)
							profit.add(Float.valueOf(temp));
						else if ( optName.equals( "range" ) )
							range.add(Float.valueOf(temp));
						else if ( optName.equals("cost") )
							cost.add(Float.valueOf(temp));
					}
					start_index = end_index+1;
					cnt++;
				} while( start_index < size);
			}
			optionsTable.clear();
			optionsTable.put("range", range);
			optionsTable.put("cost", cost);
			optionsTable.put("profit", profit);
			set_sliders(optionsTable);
			this.filename = filename;
			//
		} catch (IOException e) {
			menager.print_debug_info("[C] IOException");
			e.printStackTrace();
			return false;
		} catch (Exception e){
			menager.print_debug_info("[C] Error! Wrong config file?");
			//e.printStackTrace();
			return false;
		}
		
		/*
		 * Koniec wypelniania listy parametrow;
		 */
		
		/*
		 * Ustawianie wartosci na spinnerach, bezpieczna funkcja
		 */
		return true;
	}
	/*
	 * Defensive copying
	 */
private Hashtable<String, List<Float>> copyHashTable(Hashtable<String, List<Float>> input ){
	Hashtable<String, List<Float>> newTable = new Hashtable<String, List<Float>>();
	for (Iterator iter =input.keySet().iterator(); iter.hasNext();) {
		String key = (String) iter.next();
		List<Float> list = input.get(key);
		List<Float> newList = new ArrayList<Float>();
		for(int i=0;i<list.size();i++){
			newList.add(list.get(i));
		}
		newTable.put(key, newList);
	}
	return newTable;
	}
	
}
