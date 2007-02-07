package bo_gui.gui;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;



public class JPanelExtended extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4898229359770081928L;
	private GridBagConstraints constr;
	public JPanelExtended(){
		super();
		setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		setLayout(new GridBagLayout());
		constr = new GridBagConstraints();
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.gridx = 0;
		constr.gridy = 0;
		constr.insets = new Insets(3,3,2,2);
	}
	
	public void doloz(Component wkladnik){
		super.add(wkladnik,constr);
		constr.gridy+=1*constr.gridx;
		constr.gridx=(constr.gridx+1)%2;
	}
	
	public void doloz(Component wkladnik,boolean szeroko){
		lf();
		constr.gridwidth = 2;
		super.add(wkladnik,constr);
		lf();
		constr.gridwidth=1;
	}
	
	public void lf(){
		constr.gridy+=1;
		constr.gridx=0;
	}
}
