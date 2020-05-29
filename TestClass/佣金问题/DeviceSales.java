public class DeviceSales {
    public int getHostNum() {
        return hostNum;
    }

    public int getScreenNum() {
        return screenNum;
    }

    public int getPeripheralNum() {
        return peripheralNum;
    }

    private int hostNum;
    private int screenNum;
    private int peripheralNum;

    private int hostPrice = 25;
    private int screenPrice = 30;
    private int peripheralPrice = 45;

    private int maxHostNum = 70;
    private int maxScreenNum = 80;
    private int maxPeripheralNum = 90;

    private double firstLevel = 0.1;
    private double secondLevel = 0.15;
    private double thirdLevel = 0.2;


    private void setDeviceNum(int hostNum, int screenNum, int peripheralNum){
        this.hostNum = hostNum;
        this.screenNum = screenNum;
        this.peripheralNum = peripheralNum;
    }

    public Integer getSaleCount(int hostNum, int screenNum, int peripheralNum){
        setDeviceNum(hostNum, screenNum, peripheralNum);
        if(!checkParams()){
            return -1;
        }
        int saleCount = hostNum * hostPrice + screenNum * screenPrice + peripheralNum * peripheralPrice;

        return saleCount;
    }

    private boolean checkParams(){
        if (hostNum<1||hostNum>maxHostNum){
            return false;
        }
        if (screenNum<1||screenNum>maxScreenNum){
            return false;
        }
        if (peripheralNum<1||peripheralNum>maxPeripheralNum){
            return false;
        }
        return true;
    }

    public Double getBrokerage(int hostNum, int screenNum, int peripheralNum){
        setDeviceNum(hostNum, screenNum, peripheralNum);
        if(!checkParams()){
            return -1.0;
        }
        var saleCount=getSaleCount(hostNum, screenNum, peripheralNum);
        if (saleCount <= 1000 ){
            return firstLevel * saleCount;
        }
        else if (saleCount <= 1800){
            return secondLevel * saleCount;
        }
        else return thirdLevel * saleCount;
    }
}
