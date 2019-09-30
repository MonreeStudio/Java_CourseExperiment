import javax.sound.midi.Soundbank;
import javax.xml.transform.sax.SAXTransformerFactory;
import java.security.Principal;
import java.util.Calendar;
import java.util.Scanner;
import java.util.zip.CheckedOutputStream;

public class Test1 {
    public static void main(String[] args) {
        int days;
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入年份：");
        int year = scanner.nextInt();
        System.out.print("请输入月份：");
        int month = scanner.nextInt();
        System.out.println("==========================");
        System.out.printf("         %d年%d月           \n",year,month);
        System.out.println("--------------------------");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month-1);
        calendar.set(Calendar.DATE,0);
        if (((year%4==0&&year%100!=0)||(year%400==0))&&month==2){
            days = 29;
        }
        else {
            days = getDaysOfMonth(month);
        }
        int count = 1;
        System.out.println("日\t一\t二\t三\t四\t五\t六");
        while (count<=days){
            calendar.add(Calendar.DAY_OF_MONTH,1);
            int day = calendar.getTime().getDay();
            if (count ==1) {
                for (int i = 0; i < day; i++) {
                    System.out.print("\t");
                }
            }
            if (day == 0) {
                System.out.println();
                }
            System.out.print(calendar.getTime().getDate() + "\t");
            count++;
        }
        System.out.println("\n==========================");
    }

    public static int getDaysOfMonth(int month){
        int days= 0;
        switch (month){
            case 2: days = 29;break;
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12: days = 31;break;
            case 4:
            case 6:
            case 9:
            case 11: days = 30;break;
        }
        return days;
    }
}
