/*****************************************************************
JADE - Java Agent DEvelopment Framework is a framework to develop 
multi-agent systems in compliance with the FIPA specifications.
Copyright (C) 2000 CSELT S.p.A. 

GNU Lesser General Public License

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation, 
version 2.1 of the License. 

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the
Free Software Foundation, Inc., 59 Temple Place - Suite 330,
Boston, MA  02111-1307, USA.
*****************************************************************/

package chat.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import chat.client.agent.ChatClientAgent;

import com.google.gson.Gson;

/**
   @author Giovanni Caire - TILAB
 */
public class AWTChatGui extends Frame implements ChatGui {
	private ChatClientAgent myAgent;
	private TextArea allTa;
	Gson gson ;
	
	public AWTChatGui(ChatClientAgent a) {
		myAgent = a;
		
		setTitle(myAgent.getLocalName());
		setSize(getProperSize(500, 800));
		Panel p = new Panel();
		p.setLayout(new BorderLayout());
		
		
		allTa = new TextArea();
		allTa.setEditable(false);
		allTa.setBackground(Color.white);
		add(allTa, BorderLayout.CENTER);				
		
		addWindowListener(new	WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				myAgent.doDelete();
			}
		} );
		
		show();
		
		gson = new Gson();
	}
	
	
	public void notifySpoken(String speaker, String sentence) {
		allTa.setText(sentence);
		Contexte c = gson.fromJson(sentence, Contexte.class);
		allTa.setText(c.toString() + "\n\nCours Java :\n" + new Cours(c.getNiveau(), c.getTypeReseau()).get());
	}
	
	Dimension getProperSize(int maxX, int maxY) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screenSize.width < maxX ? screenSize.width : maxX);
		int y = (screenSize.height < maxY ? screenSize.height : maxY);
		return new Dimension(x, y);
	}
	
	public void dispose() {		
		super.dispose();
	}


	@Override
	public void notifyParticipantsChanged(String[] names) {
		// TODO Auto-generated method stub
		
	}
}