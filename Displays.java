import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Displays {
  // NOTE: Below functions is code to use mulitple displays for a single Lane
  //       Modify code below to specific needs.
	// public static void sendRequestDisplay2(int time) {
	// 	 String address="10.10.101.246";
	// 	    Socket s1=null;
	// 	    String line=null;
	// 	    BufferedReader br=null;
	// 	    BufferedReader is=null;
	// 	    PrintWriter os=null;
  //
	// 	    try {
	// 	        s1=new Socket(address, 4446);
	// 	        br= new BufferedReader(new InputStreamReader(System.in));
	// 	        is=new BufferedReader(new InputStreamReader(s1.getInputStream()));
	// 	        os= new PrintWriter(s1.getOutputStream());
	// 	    }
	// 	    catch (IOException e){
	// 	        e.printStackTrace();
	// 	        System.err.print("IO Exception");
	// 	    }
  //
	// 	    try {
	// 	    	os.println(time);
	// 	        os.flush();
	// 	    }catch(Exception e) {
  //
	// 	    }
  //
  //       System.out.println("Exe");
  //       try{
  //
  //           is.close();os.close();br.close();s1.close();
  //                   System.out.println("Connection Closed");
  //
  //       }catch(IOException e){
  //           e.printStackTrace();
  //       System.out.println("Socket read Error");
  //       }
  //
  //
	// 	}

    public static void main(String args[]) {


        Socket s = null;
        ServerSocket ss2 = null;
        System.out.println("Display Listening......");
        try {
            ss2 = new ServerSocket(4445); // can also use static final PORT_NUM , when defined

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Display error");

        }

        while (true) {
            try {
                s = ss2.accept();
                System.out.println("connection Established");
                ServerThread st = new ServerThread(s);
                st.start();

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Connection Error");

            }
        }

    }


}

class ServerThread extends Thread {

    String line = null;
    BufferedReader is = null;
    PrintWriter os = null;
    Socket s = null;


    public ServerThread(Socket s) {
        this.s = s;
    }

    @SuppressWarnings("deprecation")
	public void run() {
        try {
            is = new BufferedReader(new InputStreamReader(s.getInputStream()));
            os = new PrintWriter(s.getOutputStream());

        } catch (IOException e) {
            System.out.println("IO error in server thread");
        }
        long startTime = System.currentTimeMillis();

        try {
            line = is.readLine();
            if (line.compareTo("stop")!=0) {

                System.out.println("Timer Starting");
                System.out.println(line);
                //Displays.sendRequestDisplay2(Integer.parseInt(line));
                long time = Integer.parseInt(line) * 60;
                long timer = time;
                while(!Thread.currentThread().isInterrupted()) {
				        while ((timer - time) >= 0) {
                    try
                    {
                      Thread.sleep(1000);
                      time = ((System.currentTimeMillis() - startTime) / 1000);
                      System.out.println((timer - time) +" : " +line);
                    }catch(Exception e){

                    }
                  }
				        }

            }else if(line.compareTo("stop") == 0){
              System.out.println(line);
              for (Thread t : Thread.getAllStackTraces().keySet())
              {  if (t.getState()==Thread.State.RUNNABLE)
                   t.interrupt();
              }
            }
    } catch (IOException e) {

        line = this.getName(); //reused String line for getting thread name
        System.out.println("IO Error/ Client " + line + " terminated abruptly");
    }
    catch (NullPointerException e) {
        line = this.getName(); //reused String line for getting thread name
        System.out.println("Client " + line + " Closed");
    } finally {
        try {
            System.out.println("Connection Closing..");
            if (is != null) {
                is.close();
                System.out.println(" Socket Input Stream Closed");
            }

            if (os != null) {
                os.close();
                System.out.println("Socket Out Closed");
            }
            if (s != null) {
                s.close();
                System.out.println("Socket Closed");
            }

        } catch (IOException ie) {
            System.out.println("Socket Close Error");
        }
    } //end finally
}

}
