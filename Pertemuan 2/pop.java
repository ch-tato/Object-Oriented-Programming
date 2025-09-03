public class POP{
    // Fungsi untuk menghitung luas persegi panjang
    public static int getArea(int l, int w){
        return l*w;
    }

    public static void Main(String[] args){
        int length = 5, width = 3;
        int area = getArea(length, width);
        System.out.println("Area = " + area);
    }
}