import java.util.Random;
import java.util.Scanner;

public class Hello {			
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		boolean Start = true;
		while(Start) {
			int sum = 0;
			Random rand = new Random();
			int randNum = rand.nextInt(100)+1;
			System.out.println("开始猜数！\n");
			System.out.print("请输入你猜测的数：");
			boolean input = true;
			while(input) {
				int inputNum = scanner.nextInt();
				if(inputNum>randNum) {
					System.out.println("猜大了\n请继续猜测：");
					sum++;
				}
				else {
					if(inputNum<randNum) {
						System.out.println("猜小了\n请继续猜测：");	
						sum++;
					}
					else {
						System.out.println("猜对了");
						sum++;
						System.out.println("猜测次数为："+sum+"\n\n");
						input = false;
					}						
				}
			}
			System.out.println("“重新开始”请按1，“退出”请按0");
			int goon = scanner.nextInt();
			if(goon!=1) {
				Start = false;
				System.out.println("程序已退出");
			}
		}
		scanner.close();
	}
}
