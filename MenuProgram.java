import java.util.ArrayList;
import java.util.Scanner;

public class MenuProgram {
    private ArrayList<CallableMenuItem> menuItems;
    private Scanner scan;

    public MenuProgram(ArrayList<CallableMenuItem> menuItems) {
        this.menuItems = menuItems;
        scan = new Scanner(System.in);
    }

    public void run() {
        while (true) {
            menuItems.get(menu()-1).handle();
            System.out.println("Please press ENTER to continue ...");
            scan.nextLine();
        }
    }

    private int menu() {
        int choice = -1;
        int i = 1;
        System.out.println("*********************************");
        for (CallableMenuItem menuItem : menuItems) {
            System.out.println(i++ + ". " + menuItem.getDisplayString());
        }
        System.out.println("*********************************");
        while (true) {
            System.out.print("Please choose a menu option (1-" + menuItems.size()+ "): ");
            try {
                choice = Integer.parseInt(scan.nextLine());
                if((choice >= 1) && (choice <= menuItems.size()))
                    break;
            } catch (Exception e) {
                System.out.println("Invalid input: " + e.getMessage());
            }
        }
        return choice;
    }

    public int readInteger(String prompt) {
        int choice = -1;
        while (true) {
            System.out.print(prompt);
            try {
                choice = Integer.parseInt(scan.nextLine());
                break;
            } catch (Exception e) {
                System.out.println("Invalid input: " + e.getMessage());
            }
        }
        return choice;
    }

    public String readString(String prompt) {
        String input = null;
        while (true) {
            System.out.print(prompt);
            try {
                input = scan.nextLine();
                break;
            } catch (Exception e) {
                System.out.println("Invalid input: " + e.getMessage());
            }
        }
        return input;
    }
}