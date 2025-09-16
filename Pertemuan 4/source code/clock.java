import java.util.Random;
public class clock {
    public class numberDisplay{
        private int curr;
        private int max;

        public numberDisplay(int maxValue){
            curr = 0;
            max = maxValue;
        }

        public void setValue(int value){
            if(value >= 0 && value < max) curr = value;
        }

        public int getValue(){
            return curr;
        }

        public String getDisplay(){
            if(curr < 10) return "0" + curr;
            else return "" + curr;
        }

        public void increment(){
            curr = (curr + 1) % max;
        }
    }

    public class clockDisplay{
        private numberDisplay second;
        private numberDisplay minute;
        private numberDisplay hour;
        private numberDisplay day;
        private numberDisplay month;
        private numberDisplay year;
        private String currTime;
        private String currDate;
        private String currInfo;

        public class temp{
            private int min, max;
            private int value;
            private Random random = new Random();

            public temp(){
                min = 28;
                max = 33;
                value = random.nextInt(max - min + 1) + min;
            }

            public int getValue(){
                value = random.nextInt(max - min + 1) + min;
                return value;   
            }
        }

        private temp tempValue;
        private int temperature;

        public clockDisplay(){
            second = new numberDisplay(60);
            minute = new numberDisplay(60);
            hour = new numberDisplay(24);
            month = new numberDisplay(12);
            if(month.getValue()%2 == 0)
                day = new numberDisplay(31);
            else{
                if(month.getValue() == 2)
                    day = new numberDisplay(28);
                else
                    day = new numberDisplay(30);
            }
            year = new numberDisplay(9999);
            tempValue = new temp();
            temperature = tempValue.getValue();
            setTimeStr();
        }

        private void setTimeStr(){
            currTime = hour.getDisplay() + ":" + minute.getDisplay() + ":" + second.getDisplay();
            currDate = day.getDisplay() + "/" + month.getDisplay() + "/" + year.getDisplay();
            currInfo = currTime + "\n" + currDate + "\n" + temperature + "°C";
        }

        public void setTime(int hh, int mm, int ss, int dd, int mo, int yy, int t){
            hour.setValue(hh);
            minute.setValue(mm);
            second.setValue(ss);
            day.setValue(dd);
            month.setValue(mo);
            year.setValue(yy);
            temperature = t;
            setTimeStr();
        }

        public String getTime(){
            return currInfo;
        }
        public void timeIncrement(){
            temperature = tempValue.getValue();
            second.increment();
            if(second.getValue() == 0){
                minute.increment();
                if(minute.getValue() == 0){
                    hour.increment();
                    if(hour.getValue() == 0){
                        day.increment();
                        if(day.getValue() == 0){
                            day.increment();
                            month.increment();
                            if(month.getValue() == 0){
                                month.increment();
                                year.increment();
                            }
                        }
                    }
                }
            }
            setTimeStr();
        }
    }

    public static void main(String[] arg){
        clock c = new clock();
        clockDisplay clock = c.new clockDisplay();
        clock.setTime(15, 18, 55, 16, 9, 2025, 30);

        
        while(true){
            clock.timeIncrement();
            System.out.println("tick...");
            System.out.println("===================");
            System.out.println("" + clock.getTime());
            System.out.println("===================");
            sleepMilis(1000);
        }
    }
    
    public static void sleepMilis(int milisecond){
        try{Thread.sleep(milisecond);}
        catch(Exception e){}
    }
}
