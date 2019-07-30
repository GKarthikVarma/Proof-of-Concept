import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class client {

public static void main(String args[]) throws IOException{

    while(true){
    String address="10.10.101.246";
    Socket s1=null;
    String line=null;
    BufferedReader br=null;
    BufferedReader is=null;
    PrintWriter os=null;
    try {
        br= new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter client id: ");
        line = br.readLine();
        if(line.compareTo("1") == 0){
          s1=new Socket(address, 4445);
        }else{
          s1=new Socket(address, 4447);
        }
        is=new BufferedReader(new InputStreamReader(s1.getInputStream()));
        os= new PrintWriter(s1.getOutputStream());
    }
    catch (IOException e){
        e.printStackTrace();
        System.err.print("IO Exception");
    }

    System.out.println("Client Address : "+address);

    try{
        System.out.println("Enter a timer:");
        line=br.readLine();
        os.println(line);
        os.flush();
        }
    catch(IOException e){
        e.printStackTrace();
        System.out.println("Socket read Error");
    }finally{
      //is.close();os.close();br.close();s1.close();
      //System.out.println("Connection Closed");
  }
  }

}
}
