/*
 * TurboDownloaderView.java
 */

package turbodownloader;

import java.awt.Color;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 * The application's main frame.
 */

class closing_indic
{
    public static boolean close=true;
}

class eds_tracker
{
    static ArrayList <each_download>eds=eds=new ArrayList<each_download>();
}
public class TurboDownloaderView extends FrameView {

    queuerunner que=null;
    DefaultTableModel mod;
    history hr;
    
    Thread ahut_down_controller,timer;
    static String player_path="C:\\Program Files\\VideoLAN\\VLC\\vlc.exe ";
    static String cycle_time=2+"";
    static String shutdown_time=null;
    static int xv=30,yv=20;
    public TurboDownloaderView(SingleFrameApplication app) {
        super(app);
       
        initComponents();
        
       
        mainPanel.setName("Turbo Downloader");
        
        hr=history.getInstance();
        hr.setVisible(true);
        hr.setBounds(30, 20,691,460);
        
        jDesktopPane1.add(hr,javax.swing.JLayeredPane.DEFAULT_LAYER);
        
        new Thread(new internet_checker()).start();
        
        this.initialize();//initialize history frame with all each_downloads

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = TurboDownloaderApp.getApplication().getMainFrame();
            aboutBox = new TurboDownloaderAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        TurboDownloaderApp.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jPanel1 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        message_indic = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        mainPanel.setName("mainPanel"); // NOI18N
        mainPanel.setPreferredSize(new java.awt.Dimension(700, 450));

        jButton1.setMnemonic('N');
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(turbodownloader.TurboDownloaderApp.class).getContext().getResourceMap(TurboDownloaderView.class);
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText(resourceMap.getString("jButton2.text")); // NOI18N
        jButton2.setName("jButton2"); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jDesktopPane1.setName("jDesktopPane1"); // NOI18N

        jPanel1.setName("jPanel1"); // NOI18N

        jButton4.setText(resourceMap.getString("jButton4.text")); // NOI18N
        jButton4.setName("jButton4"); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "URL", "Output Filename", "Completion"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                true, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setName("jTable1"); // NOI18N
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(jTable1);
        jTable1.getColumnModel().getColumn(0).setResizable(false);
        jTable1.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("jTable1.columnModel.title0")); // NOI18N
        jTable1.getColumnModel().getColumn(1).setResizable(false);
        jTable1.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("jTable1.columnModel.title1")); // NOI18N
        jTable1.getColumnModel().getColumn(2).setResizable(false);
        jTable1.getColumnModel().getColumn(2).setHeaderValue(resourceMap.getString("jTable1.columnModel.title2")); // NOI18N

        jButton3.setText(resourceMap.getString("jButton3.text")); // NOI18N
        jButton3.setName("jButton3"); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton7.setText(resourceMap.getString("jButton7.text")); // NOI18N
        jButton7.setName("jButton7"); // NOI18N
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 493, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton7)
                .addGap(18, 18, 18))
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton7)
                    .addComponent(jButton3))
                .addGap(19, 19, 19)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jPanel1.setBounds(20, 10, 700, 430);
        jDesktopPane1.add(jPanel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jPanel1.setVisible(false);

        jButton5.setText(resourceMap.getString("jButton5.text")); // NOI18N
        jButton5.setName("jButton5"); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText(resourceMap.getString("jButton6.text")); // NOI18N
        jButton6.setName("jButton6"); // NOI18N
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        message_indic.setFont(resourceMap.getFont("message_indic.font")); // NOI18N
        message_indic.setForeground(resourceMap.getColor("message_indic.foreground")); // NOI18N
        message_indic.setText(resourceMap.getString("message_indic.text")); // NOI18N
        message_indic.setName("message_indic"); // NOI18N

        jLabel2.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jLabel2.setForeground(resourceMap.getColor("jLabel2.foreground")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(jDesktopPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 739, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(379, Short.MAX_VALUE))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(29, 29, 29)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addComponent(jButton2)
                                .addGap(35, 35, 35)
                                .addComponent(jButton5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 317, Short.MAX_VALUE)
                                .addComponent(jButton6)
                                .addGap(30, 30, 30))))))
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(235, 235, 235)
                .addComponent(message_indic, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(271, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton1)
                    .addComponent(jButton5)
                    .addComponent(jButton6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(message_indic, javax.swing.GroupLayout.DEFAULT_SIZE, 16, Short.MAX_VALUE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addContainerGap())
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(turbodownloader.TurboDownloaderApp.class).getContext().getActionMap(TurboDownloaderView.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        jMenu1.setText(resourceMap.getString("jMenu1.text")); // NOI18N
        jMenu1.setName("jMenu1"); // NOI18N

        jMenuItem1.setText(resourceMap.getString("jMenuItem1.text")); // NOI18N
        jMenuItem1.setName("jMenuItem1"); // NOI18N
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText(resourceMap.getString("jMenuItem2.text")); // NOI18N
        jMenuItem2.setName("jMenuItem2"); // NOI18N
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setText(resourceMap.getString("jMenuItem3.text")); // NOI18N
        jMenuItem3.setName("jMenuItem3"); // NOI18N
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        menuBar.add(jMenu1);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 759, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 739, Short.MAX_VALUE)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, 739, Short.MAX_VALUE)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        progressBar.setMaximum(8);
        progressBar.setVisible(false);

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

  
    public void initialize()
    {
        File f=new File("init");
        if(!f.exists() | f.list().length==0 |f==null) return;
        String files[]=f.list();
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        try {
            for(int i=0;i<files.length;i++)
            {
                  FileInputStream fino=new FileInputStream("init//"+files[i]);
                try {
                    ObjectInputStream oin=new ObjectInputStream(fino);
                    try {
                         Object obb=oin.readObject();
                         ds ob=(ds)obb;
                         
                        //change startting range of all threds
                       
                        {
                        for(int u=0;u<ob.sr.length;u++)
                        {
                            ob.sr[u]=ob.sr[u]+ob.dcount[u];
                            ob.dcount[u]=0;
                        }
                        }
                        if(!ob.running) {ob.running=true; ob.tfile=true;}
                        each_download ed=new each_download();
                        ed.e_d_url.setText(ob.durl);
                        ed.e_d_fname.setText(ob.dfname);
                        
                           
                        String comple=""+(Math.ceil(Double.valueOf(twoDForm.format((double)ob.total_downloaded/ob.file_len))*100))+"%";
                        
                        ed.e_d_completion.setText(comple);
                        
                        ed.ob=ob;
                        
                        //add each_downloads to eds
                        eds_tracker.eds.add(ed) ;
                        
                        //add ed tohistory frame
                        new Thread(new up_history(ed)).start();   //add each_download to history
                        
                        
                        
                        
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(TurboDownloaderView.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                } catch (IOException ex) {
                    Logger.getLogger(TurboDownloaderView.class.getName()).log(Level.SEVERE, null, ex);
                }
                  
            }
                    
          
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TurboDownloaderView.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        
    }
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        each_download ed=new each_download();
        frame1 f=new frame1(ed);
        f.setVisible(true);
        ed.f=f;
        new Thread(f).start();  //start frame
        new Thread(new up_history(ed)).start();   //add each_download to history
        
    }//GEN-LAST:event_jButton1ActionPerformed

    
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        
        jTable1.updateUI();
        
        
        
        if(jButton4.getText().equals("Start Queue")) {
          
         String [][]urls=new String[jTable1.getRowCount()][2];
        
         for(int i=0;i<jTable1.getRowCount();i++)
         {
             for(int j=0;j<jTable1.getColumnCount();j++)
             {
                 System.out.print("  "+i+" "+j+"-"+jTable1.getValueAt(i, j));
             }
             System.out.println();
                     
         }
         
         
        int j=0;
        
        
        for(int i=0;i<jTable1.getRowCount();i++)
        {
            String ur=(String) jTable1.getValueAt(i, 0);
            String fnam=(String)jTable1.getValueAt(i,1);
            String comp=(String)jTable1.getValueAt(i, 2);
              if(ur!=null & fnam!=null & comp==null)
              {
                  if(ur.length()!=0 &fnam.length()!=0)
              {
                  String fn=(String)jTable1.getValueAt(i, 1);
                  System.out.println(fn+"  "+ur);
                  urls[j][0]=ur;
                  urls[j][1]=fn;
                  j++;
                  
              }}
              else
              {
                  JOptionPane.showMessageDialog(null,"Enter the Url and Filenames");
                  return;
              }
            
            
        }
        
        que=new queuerunner(urls,jTable1);
       new Thread(que).start();
        jButton4.setText("Stop Queue");
       }
       else
       {
           que.rval=false;
           jButton4.setText("Start Queue");
       }
        
       
        
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        jPanel1.setVisible(true);
         hr.setVisible(false);
        
      
}//GEN-LAST:event_jButton2ActionPerformed

private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
// TODO add your handling code here:
    mod=(DefaultTableModel) jTable1.getModel();
    Object ob[]=new Object [1];
    mod.addRow(ob);
    
  
}//GEN-LAST:event_jButton3ActionPerformed

private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
    // TODO add your handling code here:
    hr.setVisible(true);
    jPanel1.setVisible(false);
}//GEN-LAST:event_jButton5ActionPerformed

private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        try {
            // TODO add your handling code here:
            closing_indic.close=false;
            progressBar.setVisible(true);
            new Thread(new Runnable()
                    {
                        public void run()
                    {
                        jLabel1.setText("Please wait application is being closed...");
                        jLabel1.updateUI();
                        
                    }}).start();
            
            Thread t=new Thread(new exiting());
            t.start();
                     
            t.join();
            
           
         
        } catch (Exception ex) {
            Logger.getLogger(TurboDownloaderView.class.getName()).log(Level.SEVERE, null, ex);
        }
    System.exit(0);
    
}//GEN-LAST:event_jButton6ActionPerformed

private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
    // TODO add your handling code here:
    String plpath=null;
    do{
    plpath=JOptionPane.showInputDialog(null,"Please specify the exact path for the player:",this.player_path);
            
       
     }while((!(new File(plpath).exists())) | plpath==null);
    player_path=plpath+" ";
    
}//GEN-LAST:event_jMenuItem1ActionPerformed

private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
    // TODO add your handling code here:
    String ctime=null;
    while(true){
        ctime=JOptionPane.showInputDialog(null,"Input the state save cycle time(min=2s,max=10s)",this.cycle_time);
        try{
        if(Integer.parseInt(ctime)>10 || Integer.parseInt(ctime)<2 || ctime==null) continue;
        else
        {
            this.cycle_time=Integer.parseInt(ctime)+"";
            break;
        }
        }catch(Exception e)
        {
            continue;
        }
        
        
    }
    
}//GEN-LAST:event_jMenuItem2ActionPerformed

private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
    // TODO add your handling code here:
    String shut_time=null;
    while(true)
    {
        shut_time=JOptionPane.showInputDialog(null,"Input the number of minutes after which windows have to shutdown");
         try{
         if(Integer.parseInt(shut_time)<0)
             continue;
         else
         {
             int i=JOptionPane.showConfirmDialog(null,"Are you sure want to start the timer? (Action can not be aborted)");
             if(i==JOptionPane.YES_OPTION)
            {
             this.shutdown_time=60*Integer.parseInt(shut_time)+"";
             
             this.ahut_down_controller=new Thread(new shut_down());
             this.ahut_down_controller.start();
              
             this.timer=new Thread(new shut_down_indicator());
             this.timer.start();
             
             jMenuItem3.setEnabled(false);
             progressBar.setMaximum(Integer.parseInt(this.shutdown_time));
             progressBar.setVisible(true);
            }
             break;
         }
         }catch(Exception e)
         {
             continue;
         }
         

        
    }
    
}//GEN-LAST:event_jMenuItem3ActionPerformed

private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
    // TODO add your handling code here:
     mod=(DefaultTableModel) jTable1.getModel();
     
    if(mod.getRowCount()>1)
    { 
       
        mod.removeRow(mod.getRowCount()-1);
    }
    
    
}//GEN-LAST:event_jButton7ActionPerformed


class shut_down implements Runnable
{
    public void run()
   {
       Runtime r=Runtime.getRuntime();
            try {
                Process p=r.exec("shutdown -s -t "+TurboDownloaderView.shutdown_time);
                
            } catch (IOException ex) {
                Logger.getLogger(TurboDownloaderView.class.getName()).log(Level.SEVERE, null, ex);
            }
                
   }
}

class shut_down_indicator implements Runnable
{
    public void run()
    {
        long l=Long.parseLong(TurboDownloaderView.shutdown_time);
        while(l>0)
        {
            l--;
            progressBar.setValue(progressBar.getValue()+1);
            jLabel1.setText("Shutting Down in T-"+l+" seconds");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(TurboDownloaderView.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
    }
}
class exiting implements Runnable
{
    public void run()
    {
        for(int i=0;i<8;i++)
        {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(TurboDownloaderView.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        
    }
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    static javax.swing.JLabel message_indic;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;

    private JDialog aboutBox;




class queuerunner implements Runnable
{
    String urls[][];
    boolean rval=true;
    JTable jtble;
    
    queuerunner(String [][]urls,JTable jt)
    {
        this.urls=urls;
        jtble=jt;
        
                
    }
    public void runner()
    {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        for(int i=0;((i<urls.length) & (this.rval));i++)
        {
            if(urls[i][0].length()!=0 & urls[i][0]!=null)
            {   
                        each_download ed=new each_download();    
                        frame1 f=new frame1(urls[i][0],urls[i][1],ed);
                        ed.f=f;
                       
                        f.setVisible(true);
                        new Thread(f).start();
                        new Thread(new up_history(ed)).start();
                        while(!f.status & this.rval)
                        {
                            
                             try {
                                 
                                 
                                 String completion=""+(Math.ceil(Double.valueOf(twoDForm.format((double)f.px.ob.total_downloaded/f.px.len))*100))+"%";
                                 jtble.setValueAt(completion, i, 2);
                                    Thread.sleep(2000);
                                 } catch (InterruptedException ex) {
                                        Logger.getLogger(queuerunner.class.getName()).log(Level.SEVERE, null, ex);
                                 }
                             
                             
                        }
                        if(f.status)//download complete
                        {
                            f.dispose();
                            jtble.setValueAt("100%", i, 2);
                            
                            
                        }
                        
                        
            }
        }
    }
    public void run()
    {
        this.runner();
    }
}
}

class up_history implements Runnable
{
   
    each_download ed=null;
    history hr;
    each_download edar[]=null;
    up_history(each_download e)
    {
        ed=e;
        hr=history.getInstance();
               
    }
    up_history(each_download []ar)
    {
        edar=ar;
        hr=history.getInstance();
    }
    
    public void run()
   {
     
 
   ed.setBounds(TurboDownloaderView.xv, TurboDownloaderView.yv, 503,23);
   TurboDownloaderView.yv+=25;
   ed.setVisible(true);
  
   hr.hjp.add(ed);
   hr.updateUI();
   hr.hjp.updateUI();
   ed.updateUI();
   hr.hjs.updateUI();
   
    }
}


class internet_checker implements Runnable
{
    public void run()
    {
        while(true)
        {
            if(this.isConnected())
            {
                TurboDownloaderView.message_indic.setForeground(Color.green);
                TurboDownloaderView.message_indic.setText("Connected");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(internet_checker.class.getName()).log(Level.SEVERE, null, ex);
                }
              
            }
            try {
                Thread.sleep(4000);
            } catch (InterruptedException ex) {
                Logger.getLogger(internet_checker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    public boolean isConnected()
    {
        try {
            URL u=new URL("http://www.google.com/");
            try {
                URLConnection uc=u.openConnection();
                InputStream in=uc.getInputStream();
                if(in.read()==-1)
                    throw (new IOException());
            } catch (Exception ex) {
                TurboDownloaderView.message_indic.setForeground(Color.RED);
                TurboDownloaderView.message_indic.setText("Internet Connection Not Availabel");
              //  Logger.getLogger(internet_checker.class.getName()).log(Level.SEVERE, null, ex);
                return false;
                
            }
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(internet_checker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
}