import javax.naming.ServiceUnavailableException;
import javax.xml.transform.Source;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.StringTokenizer;

public class Test1 {
    public static void main(String[] args) {
        String pathName = "D:\\采购清单.txt";
        int count = 0;
        int sum = 0;
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(pathName), StandardCharsets.UTF_8))
        {
            BufferedReader br = new  BufferedReader(reader);
            String s = br.readLine();
            while (s!=null){
                if (count>=2) {
                    //System.out.println(s.split(","));
                    //String[] a = s.replaceAll("\\D", ",").split(",");
                    StringTokenizer tokenizer = new StringTokenizer(s.replaceAll("\\D", ","), ",");
                    while (tokenizer.hasMoreTokens()) {
                        int a= Integer.parseInt(tokenizer.nextToken());
                        int b= Integer.parseInt(tokenizer.nextToken());
                        sum += a*b;
                        //System.out.printf("%s %s\n", tokenizer.nextToken(), tokenizer.nextToken());
                    }
//                    for (String str : a){
//                        if (!str.equals(""))
//                            System.out.println(str + " ");
//                    }
                }
                s = br.readLine();
                count++;
            }
            br.close();
            System.out.println("总价格为："+ sum+"元");
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
