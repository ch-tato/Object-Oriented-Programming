class Rectangle{
    int length, width;

    // Konstruktor
    Rectangle(int l, int w){
        length = l;
        width = w;
    }

    // Method (fungsi) untuk menghitung luas
    int getArea(){
        return length*width;
    }
}

public class OOP{
    public static void Main(String[] args){
        Rectangle rc = new Rectangle(3, 5);
        System.out.println("Area = " + rc.getArea());
    }
}