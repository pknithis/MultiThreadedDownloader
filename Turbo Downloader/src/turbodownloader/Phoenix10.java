/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package turbodownloader;
import java.awt.Color;
import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JProgressBar;
import java.io.*;
import java.text.DecimalFormat;
/**
 *
 * @author nithishkp
 */


class ds implements Serializable
{
    static boolean closing=true;
    long []sr;
    long []er;
    long total_downloaded,file_len;
    long dcount[];
    boolean status[];
    boolean running=true;
    boolean tfile=true; //prevents multiple copies of stored object
    boolean resume_support=true;
    boolean net_on=true;
    String durl;
    String dfname;
    
    String temp_path;
    String extension;
     ds()
     {
         sr=new long[5];
         er=new long[5];
         dcount=new long[5];
         status=new boolean[5];
         
     }
}
//main scheduler class
public class Phoenix10  extends Thread{

  int rtime=5;
  String url;
  URL u;
  
  URLConnection urc;
  int otcount=3;
  long dlen;
  f_writer fw;//contains file name
  
  long d_downloaded=0;
  long len=0;
  ds ob=null;
  
  long starttime=0;
  frame1 frm;
  
  Phoenix10(String url,String fname,frame1 fr) 
  {
      try{
      this.url=url;
      u=new URL(url);
      urc=u.openConnection();
      fw=new f_writer(fname);
      len=urc.getContentLength();
      fw.raf.setLength(len);
    
      frm=fr;
      ob=new ds();
      
      ob.dfname=fname;
      ob.durl=url;
      ob.file_len=len;
      ob.temp_path=ob.dfname.replace('.','_');
      ob.extension=ob.dfname.substring(ob.dfname.indexOf("."));
      ob.temp_path+="_temp";
      new File(ob.temp_path).mkdir();
      
      }catch(Exception e){}
      
      this.start();
  }
  Phoenix10(ds o,frame1 fr)
  {
     //after resume
      this.ob=o;
      frm=fr;
      try{
      this.url=ob.durl;
      u=new URL(url);
      urc=u.openConnection();
      fw=new f_writer(ob.dfname);
      len=urc.getContentLength();
      //setting file length is not needed since it will already be there
      
      
      
      this.start();
      
      }catch(Exception e){}
      
      
  }
  
  
  public void run()
  {
   starttime=System.currentTimeMillis();   
   this.idm();   
  }
  
  public boolean resume_support_check()
  {
      HttpURLConnection ht = null;
        try {
            ht = (HttpURLConnection) u.openConnection();
        } catch (IOException ex) {
            Logger.getLogger(Phoenix10.class.getName()).log(Level.SEVERE, null, ex);
        }
       ht.setRequestProperty("Range", "bytes=" + 1 + "-");
        try {
            ht.connect();
        } catch (IOException ex) {
            Logger.getLogger(Phoenix10.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            if(ht.getResponseCode()==206)
            {
                System.out.print("Resume supported");
                frm.jLabel19.setText("Resume Supported");
                ht.disconnect();
               
                
                return true;
            }

            
              
        } catch (IOException ex) {
            Logger.getLogger(Phoenix10.class.getName()).log(Level.SEVERE, null, ex);
        }
        ob.resume_support=false;
        frm.jLabel19.setText("Resume not supported");
        System.out.print("Resume not supported");
        ht.disconnect();
       return false;
      
           
  }
  
  public void save_state()
{
        
        
            FileOutputStream fout=null;
            ObjectOutputStream obj = null;
            String nam=ob.dfname.replace('.','_');
           
           // File f=new File("init\\"+nam);
            //if(f.exists()) f.delete();
            
        try {
            fout=new FileOutputStream("init"+"\\"+nam);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Phoenix10.class.getName()).log(Level.SEVERE, null, ex);
        }
            try {
              
            
                            
                obj = new ObjectOutputStream(fout);
                obj.writeObject(ob);
                obj.flush();
                obj.close();
                
                fout.close();
                
            } catch (IOException ex) {
                Logger.getLogger(downloader.class.getName()).log(Level.SEVERE, null, ex);
            }
        
}
   
  public void idm()
  {
      long rg=len/5;
      
      boolean resumecheck=false;
      
           for(int i=0;i<ob.sr.length;i++)
           {
               if(ob.status[i])//few threads were still running
               {
                   resumecheck=true;
               }
           }
                
      
         //first starting of threads
           if(!resumecheck)
            {
              if(this.resume_support_check())
              {
               for(int i=0;i<ob.sr.length;i++)
             {
                 if(!ob.status[i])
                 {
                     ob.sr[i]=i*rg;
                     ob.er[i]=(i+1)*rg;
                     ob.status[i]=true;
                     
                     Thread t = null;
                    try {
                        t = new Thread(new downloader(u,ob,fw,i,frm));
                    } catch (Exception ex) {
                        Logger.getLogger(Phoenix10.class.getName()).log(Level.SEVERE, null, ex);
                    }
                     t.start();
                     
                 }
             }
             }
              else
              {
                  ob.sr[0]=0;
                  ob.er[0]=len;
                  ob.status[0]=true;
                  for(int i=1;i<ob.sr.length;i++)
                  {
                      ob.status[i]=false;
                  }
                try {
                    new Thread(new downloader(u,ob,fw,0,frm)).start();
                } catch (Exception ex) {
                    Logger.getLogger(Phoenix10.class.getName()).log(Level.SEVERE, null, ex);
                }
              }
  }
           else
           {    
           //threads starting after pause/resume
               this.resume_support_check();//just call so to set status
               
               
           for(int i=0;i<ob.sr.length;i++)
           {
           Thread t=null;
                try {
                    t = new Thread(new downloader(u,ob,fw,i,frm));
                } catch (Exception ex) {
                    Logger.getLogger(Phoenix10.class.getName()).log(Level.SEVERE, null, ex);
                }
           t.start();
               
           }
           }
        try {
            Thread.sleep(4000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Phoenix10.class.getName()).log(Level.SEVERE, null, ex);
        }
                 System.out.print("Wait over");
               
                 
                 
           //keep scanning the threads status
           while(ob.running & ob.tfile)
           {
                             
               //if net is disconnected  
               if(!ob.net_on)
               {
                   rtime=5;
                   frm.net_status.setForeground(Color.red);
                 
                   internet_checker ic=new internet_checker();
                   while(!ic.isConnected())
                   {
                       for(int i=0;(i<rtime)&(rtime!=-1);i++)
                        {    
                            frm.net_status.setText("Trying to Reconnect in "+(rtime-i)+" s");  
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Phoenix10.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                       }
                        frm.net_status.setText("Connecting...");
                       
                       rtime+=2;//increment rtime after each connect failure
                   }
                   
                   rtime=5;
                   frm.net_status.setForeground(Color.green);
                   frm.net_status.setText("Connected");
                   this.dispatch_threads();
                   
               }
               //check when to stop scanning of the threads
               {
               boolean live_status=false;
               for(int i=0;i<ob.status.length;i++)
               {
                   if(ob.status[i])//true if running //false if dead
                   {
                       live_status=true;
                   }
                   else//check whether it is an abnormal termination of that thread
                   {
                       if((ob.er[i]-ob.sr[i])!=ob.dcount[i])//abnormal termination
                       {
                          frm.post_msgs("Abnormal Termination..Restarting Thread...", i);
                          ob.sr[i]=ob.sr[i]+ob.dcount[i];
                          ob.dcount[i]=0;
                          ob.status[i]=true;
                          new Thread(new downloader(u,ob,fw,i,frm)).start();//restart thread
                          
                       }
                           
                       
                   }
                   
               }
               if(live_status)//somme thread is alive
               {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Phoenix10.class.getName()).log(Level.SEVERE, null, ex);
                    }
               }
               else
               {
                    break;
               }
               
               
               }
               
                  
            }
               
               
           
           
     
  }
  
  
public void dispatch_threads()
{
    ob.net_on=true;
    //reset ranges
                  
    for(int y=0;y<ob.sr.length;y++)
           {
              ob.sr[y]=ob.sr[y]+ob.dcount[y];
              ob.dcount[y]=0;
           }
    for(int i=0;i<ob.sr.length;i++)
           {
           Thread t=null;
                try {
                    t = new Thread(new downloader(u,ob,fw,i,frm));
                } catch (Exception ex) {
                    Logger.getLogger(Phoenix10.class.getName()).log(Level.SEVERE, null, ex);
                }
           t.start();
               
           }
    
                       
                        
    
}
  
}



class f_writer 
{
    String fname;
    RandomAccessFile raf;
    f_writer(String fname)
    {
        this.fname=fname;
        try {
            
            raf=new RandomAccessFile(this.fname,"rw");
            
            //raf.seek(len);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(f_writer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
   synchronized  void write_data(long skip,byte[]data)
    {
        
         try {
        
               
                    raf.seek(skip);
                
            } catch (IOException ex) {
                Logger.getLogger(downloader.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        try {
            raf.write(data);
        } catch (IOException ex) {
            Logger.getLogger(downloader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}


class downloader implements Runnable
{
    URL url;
    long start;
    long end;
    long count=0;
    HttpURLConnection urc;
    InputStream in;
    BufferedOutputStream buf;
    ByteArrayOutputStream bout;
    ds ob=null;
    f_writer fwr;
    int tid;
   
    frame1 fr;
    
      
    downloader(URL url,ds d,f_writer ff,int i,frame1 f)
    {
        fr=f;
        tid=i;
        this.url=url;
        this.start=d.sr[tid];
        this.end=d.er[tid];
        this.fwr=ff;
        
        try {
            urc=(HttpURLConnection) url.openConnection();
        } catch (IOException ex) {
            Logger.getLogger(downloader.class.getName()).log(Level.SEVERE, null, ex);
        }
        bout=new ByteArrayOutputStream();
        buf= new BufferedOutputStream(bout);
        ob=d;
        //initialize each progress bar
        fr.jpar[tid].setMaximum(100);
        fr.makevisibe_jpbs();
        
    }
     void post_msgs(String msgs,int index)
    {
            
       fr.array[index].setText(msgs);
        
        
    }
    
    
    void download() throws IOException
    {
        //if thread is done after resume just return
        if(!ob.status[tid]) 
        {
            fr.jpar[tid].setValue(fr.jpar[tid].getMaximum());
            fr.fjpar[tid].setValue(fr.fjpar[tid].getMaximum());
            return;
        }
        
        post_msgs("Requesting byte range", tid);
        
        if(start!=0)
        {
            try{
            urc.setRequestProperty("Range", "bytes=" + start + "-");
            
            }catch(Exception e)
                    {
                    ob.net_on=false;    
                    fr.net_status.setForeground(Color.red);
                    fr.net_status.setText("Trying to Reconnect...");
                    return;                        
                    }
        }
        
        
        post_msgs("connecting...", tid);
        try{
        urc.connect();
        in=urc.getInputStream();
        }catch(IOException e)
        {
            ob.net_on=false;
            fr.net_status.setForeground(Color.red);
            fr.net_status.setText("Trying to Reconnect...");
            return;
        }
        fr.net_status.setForeground(Color.green);
        fr.net_status.setText("Connected");
    
       
        byte bb=0;
                     
        while(true)
        {
            
         if(Thread.currentThread().getPriority()!=fr.priorities[tid])   
         {
             Thread.currentThread().setPriority(fr.priorities[tid]);
         }
           try{
            bb=(byte) in.read();
           }catch(IOException e)
           {
               ob.net_on=false;
               fr.net_status.setForeground(Color.red);
               fr.net_status.setText("Trying to Reconnect...");
               break;
               
           }
          
           if(!ob.running | !ob.tfile | !closing_indic.close) break;
           
           
           count=count+1;
           
          
           
          DecimalFormat twoDForm = new DecimalFormat("#.##");
                
          post_msgs(("  "+(Double.valueOf(twoDForm.format((double)(ob.dcount[tid])/1024)))+" KB"),tid);
           
           
           buf.write(bb);
           
           ob.dcount[tid]+=1;
           ob.total_downloaded+=1;
           
                                      
           if(count>=(end-start)) break;
            
        }
        
         synchronized(ob) 
         {
            
          buf.flush();
          bout.flush();
           
          System.out.println(this.tid+" entered") ;
          //write to file
          post_msgs("Writing to the file...", tid);
          fwr.write_data(start, bout.toByteArray());
        
          System.out.println(this.tid+" exited") ;  
        
          
        
          }
         
         if(!closing_indic.close)
             System.out.println("Close signal");
      
        
        
        buf.close();
        bout.close();
        in.close();
        urc.disconnect();
        
            if(!ob.net_on)//net disconnected//doesn t matter if it is paused/or not
            {
             for(int i=0;i<ob.sr.length;i++)
             {
                 post_msgs("Net Disrupted", i);
             }
             ob.status[tid]=true;   
             System.out.println("net disconnect");
            }
            else if(ob.tfile & closing_indic.close)//no pause happened//completeion of download
            {
                ob.status[tid]=false;
               
            }
            else//pause/resume happened
            {
                ob.status[tid]=true;
                fr.net_status.setText("Download Paused");
            }
        
        try {
            Thread.sleep(1000);//wait for every thread to write it's status
        } catch (InterruptedException ex) {
            Logger.getLogger(downloader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(!ob.running)
        {
            synchronized(ob)
            {
                if(!ob.running & !ob.tfile)
                {
                    //change starting range of all threads by adding with their dcounts 
                    
                    if(!ob.tfile)
                    {
                    post_msgs("Saving State", tid);
                    this.save_state();
                    post_msgs("State saved", tid);
                    }    
                    
                }
                
            }
            
        }
        
       
              
     
    }    
       
       
    
public void save_state()
{
        ob.tfile=true;
        
        FileOutputStream fout = null;
        {
            ObjectOutputStream obj = null;
            try {
                for(int y=0;y<ob.sr.length;y++)
                               {
                                   
                                   ob.sr[y]=ob.sr[y]+ob.dcount[y];
                                   ob.dcount[y]=0;
                                   
                     
                               }
                String nam=ob.dfname.replace('.','_');
                File f=new File(ob.temp_path+"//"+nam);
                if(f.exists())
                {
                    f.delete();
                }
                
                fout = new FileOutputStream(ob.temp_path+"//"+nam);
                obj = new ObjectOutputStream(fout);
                obj.writeObject(ob);
                obj.flush();
                obj.close();
                
                
                
                
                //ob.running=true;
            } catch (IOException ex) {
                Logger.getLogger(downloader.class.getName()).log(Level.SEVERE, null, ex);
            }finally {
                try {
                    fout.close();
                
                } catch (IOException ex) {
                    Logger.getLogger(downloader.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    obj.close();
                } catch (IOException ex) {
                    Logger.getLogger(downloader.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
       
}
        
        
    
    
    public void run()
    {
        try {
            
            System.out.println("Downloading..."+tid);
            download();
            
            System.out.println("Finished..."+tid);
            post_msgs("Disconneced", tid);
           
            
            
           
        } catch (IOException ex) {
            Logger.getLogger(downloader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}