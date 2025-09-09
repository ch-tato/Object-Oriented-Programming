
/**
 * Ticket Machine is a machine to take a ticket.
 *
 * @author Muhammad Quthbi Danish Abqori
 * @version 1.0
 */
public class TicketMachine
{
    // instance variables
    private int price;      // price of one ticket
    private int balance;    // money inserted so far
    private int total;      // total money collected by machine

    /**
     * Constructor for objects of class TicketMachine
     */
    public TicketMachine(int price){
        // initialise instance variables
        this.price = price;
        this.balance = 0; // 
        this.total = 0;
    } 

    // To return the price of the ticket
    public int getPrice(){
        return price;
    }

    // To return the user's balance
    public int getBalance(){
        return balance;
    }

    // To user inserts an amount of money
    public void insertMoney(int amount){
        if(amount > 0){
            balance += amount;
            System.out.println("You inserted " + amount + "Rupiah.");
        }
        else{
            System.out.println("Invalid number. Please insert a positive amount!");
        }
    }

    // To print the ticket (if the balance is enough)
    public void printTicket(){
        if(balance >= price){
            System.out.println("###############################");
            System.out.println("#      Ticket issued          #");
            System.out.println("#      Price: " + price + " Rupiah #");
            System.out.println("###############################");

            total += price;
            balance -= price;

            if (balance > 0){
                System.out.println("Don't forget your change: " + balance);
                balance = 0; // return change
            }
        }
        else{
            System.out.println("You must insert at least " + (price - balance) + " more Rupiah.");
        }
    }

    public int getTotal(){
        return total;
    }

    // To refund the money if the user cancels
    public void refund(){
        if(balance > 0){
            System.out.println("You refunded " + balance + "Rupiah");
            balance = 0;
        }
        else{
            System.out.println("No balance to refund.");
        }
    }
}