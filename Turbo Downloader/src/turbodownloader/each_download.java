/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * each_download.java
 *
 * Created on Nov 29, 2011, 3:52:51 PM
 */
package turbodownloader;

import java.awt.Frame;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author ADMIN
 */
public class each_download extends javax.swing.JPanel {

    frame1 f;
    ds ob;
    /** Creates new form each_download */
    public each_download() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        e_d_url = new javax.swing.JTextField();
        e_d_fname = new javax.swing.JTextField();
        e_d_completion = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox();
        jButton2 = new javax.swing.JButton();

        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(turbodownloader.TurboDownloaderApp.class).getContext().getResourceMap(each_download.class);
        e_d_url.setText(resourceMap.getString("e_d_url.text")); // NOI18N
        e_d_url.setName("e_d_url"); // NOI18N

        e_d_fname.setText(resourceMap.getString("e_d_fname.text")); // NOI18N
        e_d_fname.setName("e_d_fname"); // NOI18N
        e_d_fname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                e_d_fnameActionPerformed(evt);
            }
        });

        e_d_completion.setEditable(false);
        e_d_completion.setText(resourceMap.getString("e_d_completion.text")); // NOI18N
        e_d_completion.setName("e_d_completion"); // NOI18N

        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Action", "Delete Entry", "Delete File", "Resume", "Play" }));
        jComboBox1.setName("jComboBox1"); // NOI18N

        jButton2.setText(resourceMap.getString("jButton2.text")); // NOI18N
        jButton2.setName("jButton2"); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(e_d_url, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(e_d_fname, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(e_d_completion, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap(14, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jButton1)
                .addComponent(jButton2))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(e_d_completion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(e_d_fname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(e_d_url, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

private void e_d_fnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_e_d_fnameActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_e_d_fnameActionPerformed

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    // TODO add your handling code here:
    
    e_d_url.setEditable(false);//redundant
    switch(jComboBox1.getSelectedIndex())
    {
        case 1:refresh_after_delete();break;
        case 2:delete_file(e_d_fname.getText())    ;break;
        case 3:invoke_frame(); break; 
        case 4: {
                   File ff=new File(e_d_fname.getText()) ;
                   new Thread(new player(ff.getPath())).start();
                   break;          
        }    
    }
}//GEN-LAST:event_jButton1ActionPerformed

public void redownload()
{
    ds ob_re=new ds();
    ob_re.dfname=this.e_d_fname.getText();
    ob_re.durl=this.e_d_url.getText();
    ob_re.extension=ob.extension;
    ob_re.running=true;
    
    
            
}
public void refresh_after_delete()
{
    history hr=history.getInstance();
   
     
    
    
    for(each_download ee:eds_tracker.eds)
    {
        if(ee==this)
        {
            System.out.append("match found");
            eds_tracker.eds.remove(ee);
            break;
        }
    }
    
    
    
    
     hr.hjp.removeAll();
       
     hr.updateUI();
    //reset y coordinate
    TurboDownloaderView.yv=20;
    
    
    for(each_download e:eds_tracker.eds)
    {
        new Thread(new up_history(e)).start();
            try {
                Thread.sleep(300);
            } catch (InterruptedException ex) {
                Logger.getLogger(each_download.class.getName()).log(Level.SEVERE, null, ex);
            }
            
    } 
    
    hr.updateUI();
    hr.hjs.updateUI();
    
    
}
public void invoke_frame()
{       
        boolean chk=false;
        for(boolean h:ob.status)
        {
            if(h)
            {
                chk=true;
                break;
            }
                
        }
        if(!chk)
        {
            JOptionPane.showMessageDialog(null, "File Already Downloaded");
            return;
        }
    
    f=new frame1(ob,this);
    f.setVisible(true);
    new Thread(f).start();
}
private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    // TODO add your handling code here:
    if(f==null)
    {
        JOptionPane.showMessageDialog(null,"Frame is not yet ready");
        return;
    }
    if(f.isVisible())
    {
        f.setVisible(false);
        jButton2.setText("-");
    }
    else 
    {
        f.setVisible(true);
        jButton2.setText("+");
    }
    
}//GEN-LAST:event_jButton2ActionPerformed

public void delete_file(String fname)
{
    File f=new File(fname);
    if(!f.exists())
    {
        JOptionPane.showMessageDialog(null, "File does not exists");
    }
    else
    {
            f.delete();
            JOptionPane.showMessageDialog(null,"File successfully deleted");
    }  
       
      
        String tmpfile=e_d_fname.getText();
        tmpfile=tmpfile.replace('.', '_');
        
        File ff=new File("init\\"+tmpfile);
        //delete file in init
         ff.delete();
        //delete any other temporary files(pause files)
        
        ff=new File(tmpfile+"_temp\\"+tmpfile);
        if(ff.exists()) ff.delete();
        
        ff=new File(tmpfile+"_temp");
        if(ff.isDirectory() & ff.exists())
            ff.delete();
        
        
        this.refresh_after_delete();
        
         
    
    
}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTextField e_d_completion;
    public javax.swing.JTextField e_d_fname;
    public javax.swing.JTextField e_d_url;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox jComboBox1;
    // End of variables declaration//GEN-END:variables
}

class player implements Runnable
{
    String fn;
    player(String fname)
    {
        fn=fname;
    }
    
    public void run()
    {
        Runtime r=Runtime.getRuntime();
        try {
            Process p=r.exec(TurboDownloaderView.player_path+fn);
            
        } catch (IOException ex) {
            Logger.getLogger(player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}