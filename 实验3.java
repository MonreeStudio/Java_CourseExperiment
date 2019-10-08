class Station extends Thread{
    private Object object = new Object();
    static int ticketNum = 0;
    static int sum = 0;
    public void run(){
        while (true) {
            //加入对象锁解决同步问题
            synchronized (object) {
                if (ticketNum<20){
                    System.out.println(Thread.currentThread().getName() + "卖出了第" + (ticketNum + 1) + "张票");
                    ticketNum++;
                    try {
                        Thread.sleep(100);
                    }
                    catch (InterruptedException e) {}
                }
                else if (sum==0){
                        System.out.println("票卖完啦！");
                        sum++;
                        break;
                    }
                    else
                        break;

            }
        }
    }
}
public class SellTicket{
    public static void main(String[] args) {
        Station st = new Station();
        Thread t1 = new Thread(st,"窗口1");
        Thread t2 = new Thread(st,"窗口2");
        Thread t3 = new Thread(st,"窗口3");
        t1.start();
        t2.start();
        t3.start();
    }
}
