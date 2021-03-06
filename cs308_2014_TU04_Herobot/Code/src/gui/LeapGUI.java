/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import java.awt.event.KeyEvent;
import java.net.SocketException;
import java.util.Scanner;

import network.Commander;
import network.VideoThread;

import com.leapmotion.leap.Controller;

import controller.HandyListener;

/**
 *
 * @author praveen
 */
public class LeapGUI extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L;

	Commander commander; // for sending commands over TCP
	Commander secCommander; // secondary commander; uses Xbee
	Controller leapController;
	HandyListener leapListener;
	
	VideoThread videoThread;
	boolean videoStreamOn = false;

    /**
     * Creates new form LeapGUI
     */
    public LeapGUI() {
        initComponents();
        try {
			videoThread = new VideoThread(videoPanel);			
		} catch (SocketException e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
        
        // Now connect to the Leap Motion Device
		leapController = new Controller();
		leapListener = new HandyListener();
		leapController.addListener(leapListener);			
		
    }
    
    public void setCommander(Commander commander) {
		this.commander = commander;
	}
    
    public void connectSerial(Commander commander) {
    	this.secCommander = commander;
    	leapListener.setCommander(commander);
        secCommander.connectXbee("/dev/ttyUSB0"); // change this appropriately
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        videoPanel = new VideoPanel();
        connectButton = new javax.swing.JButton();
        disconnectButton = new javax.swing.JButton();
        ipField = new javax.swing.JTextField();
        videoButton = new javax.swing.JButton();
        status = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        
        videoPanel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                videoPanelKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                videoPanelKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout videoPanelLayout = new javax.swing.GroupLayout(videoPanel);
        videoPanel.setLayout(videoPanelLayout);
        videoPanelLayout.setHorizontalGroup(
            videoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        videoPanelLayout.setVerticalGroup(
            videoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 349, Short.MAX_VALUE)
        );

        connectButton.setText("Connect");
        connectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectButtonActionPerformed(evt);
            }
        });

        disconnectButton.setText("Disconnect");
        disconnectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disconnectButtonActionPerformed(evt);
            }
        });

        videoButton.setText("Toggle Video");
        videoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                videoButtonActionPerformed(evt);
            }
        });

        status.setText("Status bar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(videoButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(203, 203, 203))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(videoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(ipField, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(connectButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(disconnectButton)
                        .addGap(62, 62, 62))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(status)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(videoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ipField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(connectButton)
                    .addComponent(disconnectButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(videoButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(status)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>                         

    // Performs action after server IP is entered in the text box and
    // connect action is invoked
    private void connectButtonActionPerformed(java.awt.event.ActionEvent evt) {       	
		String serverIP = ipField.getText();
		videoThread.setVideoIP(serverIP);
		status.setText("Connected to " + serverIP);        
    }                                             

    private void disconnectButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                 
    	if (commander != null)
			commander.disconnect();
		if (leapController != null && leapListener != null)			
			leapController.removeListener(leapListener);
		status.setText("Disconnected");
    }                                                

    // Used for toggling video stream
    private void videoButtonActionPerformed(java.awt.event.ActionEvent evt) {                                            
		if (videoStreamOn) {
			videoThread.pauseStreaming();
			videoStreamOn = false;
		} else {
			if (videoThread.getState() == Thread.State.NEW) {
				videoThread.start();
			} else {
				videoThread.resumeStreaming();
			}
			videoStreamOn = true;
		}
    } 
    
    private void videoPanelKeyPressed(java.awt.event.KeyEvent evt) {
    	if (secCommander == null)
    		return;
    	switch (evt.getKeyCode()) {
			case KeyEvent.VK_S:			// move bot backward
				System.out.println("Moving backward");
				secCommander.sendXbee(2);
				break;
			case KeyEvent.VK_W:			// move bot forward
				System.out.println("Moving forward");
				secCommander.sendXbee(8);
				break;
			case KeyEvent.VK_A:			// move bot left
				System.out.println("Moving left");
				secCommander.sendXbee(4);
				break;
			case KeyEvent.VK_D:			// move bot right
				System.out.println("Moving right");
				secCommander.sendXbee(6);
				break;			
		}
    }                                     

    private void videoPanelKeyReleased(java.awt.event.KeyEvent evt) {                                       
        // TODO add your handling code here:
    } 

    // Variables declaration - do not modify                     
    private javax.swing.JButton connectButton;
    private javax.swing.JButton disconnectButton;
    private javax.swing.JTextField ipField;
    private javax.swing.JButton videoButton;
    private VideoPanel videoPanel;
    private javax.swing.JLabel status;
    // End of variables declaration                   
}
