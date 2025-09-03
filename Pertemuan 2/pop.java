public class pop{
    // Fungsi untuk menghitung luas persegi panjang
    public static int getArea(int l, int w){
        return l*w;
    }

    public static void main(String[] args){
        int lenght = 5, width = 3;
        int area = getArea(lenght, width);
        System.out.println("Area = " + area);
    }
}