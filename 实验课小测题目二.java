import javax.xml.transform.Source;
import java.io.*;
import java.util.Random;
import java.util.zip.CheckedOutputStream;

public class Test1 {
    public static void main(String[] args) {
        String pathName = "D:\\名单.txt";
        int i1 = new Random().nextInt(5)+2;
        int i2 = new Random().nextInt(5)+2;
        while (i1==i2){
            i2 = new Random().nextInt(5)+2;
        }
        int i3 = new Random().nextInt(5)+2;
        while (i1==i3||i2==i3){
            i3 = new Random().nextInt(5)+2;
        }
        int count = 0;
        try (FileReader reader = new FileReader(pathName))
        {
            BufferedReader br = new  BufferedReader(reader);
            String s = br.readLine();
            while (s!=null){
                    if(count==i1||count==i2|| count==i3) {
                        System.out.println(s);
                    }
                    s = br.readLine();
                    count++;
            }
            br.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }


    }

}
