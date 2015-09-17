
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class SerialExample {
    
    public static void main(String args[]) throws InterruptedException {
        System.out.println("<--Pi4J--> Serial Communication Example ... started.");
        System.out.println(" ... connect using settings: 38400, N, 8, 1.");
        System.out.println(" ... data received on serial port should be displayed below.");
        
        // 初始化
        final Serial serial = SerialFactory.createInstance();
        
        // 建立和註冊，用來處理接收傳過來的資料。
        serial.addListener(new SerialDataListener() {
            @Override
            public void dataReceived(SerialDataEvent event) {
                // 列印收到的資料
                System.out.print(event.getData());
            }
        });
        
        try {
            // open the default serial port provided on the GPIO header
            serial.open(Serial.DEFAULT_COM_PORT, 9600);
            // continuous loop to keep the program running until the user terminates the program
            for (;;) {
                try {
                    // 把現在的時間，傳遞過去。
                    serial.write("CURRENT TIME: %s", new Date().toString());
                    
                    // 把特定的byte ，傳遞過去。
                    serial.write((byte) 13);
                    serial.write((byte) 10);
                    
                    //把字串傳遞過去。
                    serial.write("Second Line");
                    
                    // 把特定的字元 ，傳遞過去。
                    serial.write('\r');
                    serial.write('\n');
                    
                    // 把字串傳遞過去，並且做跳行的動作（ CR+LF ）
                    serial.writeln("Third Line");
                }
                catch(IllegalStateException ex){
                    ex.printStackTrace();
                }
                
                // 休息 1 秒
                Thread.sleep(1000);
            }
            
        }
        catch(SerialPortException ex) {
            System.out.println(" ==>> SERIAL SETUP FAILED : " + ex.getMessage());
            return;
        }
    }
}
