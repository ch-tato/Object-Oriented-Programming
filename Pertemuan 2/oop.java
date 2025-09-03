class rectangle{
    int lenght, width;

    // Konstruktor
    rectangle(int l, int w){
        lenght = l;
        width = w;
    }

    // Method (fungsi) untuk menghitung luas
    int getArea(){
        return lenght*width;
    }
}

public class oop{
    public static void main(String[] args){
        rectangle rc = new rectangle(3, 5);
        System.out.println("Area = " + rc.getArea());
    }
}