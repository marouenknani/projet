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

import jade.MicroBoot;
import jade.core.MicroRuntime;
import jade.core.Agent;
import jade.util.leap.Properties;

//#MIDP_EXCLUDE_BEGIN
import java.awt.*;
import java.awt.event.*;

import javax.swing.JOptionPane;


public class Start extends MicroBoot {
	public static void main(String args[]) {
		MicroBoot.main(args);
		startApp dlg = new startApp("planning");
	}

	private static class startApp extends Frame  {
		startApp(String s) {						
			try {
				MicroRuntime.startAgent(s, "chat.client.agent.ChatClientAgent", null);
				dispose();
			} catch (Exception ex) 
			{			
				JOptionPane.showMessageDialog(null, "Nom d'agent utilisé", "Message", JOptionPane.INFORMATION_MESSAGE);
			}					
		}
	}
}