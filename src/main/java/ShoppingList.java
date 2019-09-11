import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ShoppingList {
    private static ArrayList<Item> purchaseList = new ArrayList<>();
    private static ArrayList<Item> shoppingList = new ArrayList<>();

    private static void performAddAction (String name, int quantity) {
        Item item = new Item(name, quantity);
        if (!shoppingList.isEmpty() && shoppingList.contains(item)) {
//            System.out.println("this contains " + item);
//            for(Item it: shoppingList)
//                System.out.println(it);
            int i = shoppingList.indexOf(item);
//            System.out.println(i);
            shoppingList.set(i,new Item(name, quantity + shoppingList.get(i).getQuantity()));
//            System.out.println(item);
        } else {
            shoppingList.add(item);
        }
    }

    private static void performBuyAction (String name, int quantity) {
        Item item = new Item(name, quantity);
        if (!purchaseList.isEmpty() && purchaseList.contains(item)) {
//            System.out.println(purchaseList.size() + " contains " + item.getName());
            int i = purchaseList.indexOf(item);
            purchaseList.set(i,new Item(name, quantity + purchaseList.get(i).getQuantity()));
        } else {
            purchaseList.add(item);
        }

        if(shoppingList.contains(item)) {
            int index = shoppingList.indexOf(item);
            int shoppingQuantity = shoppingList.get(index).getQuantity() - quantity;
            if(shoppingQuantity <= 0) {
                shoppingList.remove(index);
            } else {
                shoppingList.set(index, new Item(name, shoppingQuantity));
            }
        }
    }

    private static boolean isIncluded(List<Item> items, String itemName) {
        for (Item i : items) {
            if(i.getName().equals(itemName))
                return true;
        }
        return false;
    }

    private static void readFile(String file) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(file));
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            int quantity = 0;
            String name = "";
            String action = "";
            if(!line.contains(",")) {
                action = line.toString().trim();
            } else {
                String[] elements = line.split(",");
                action = elements[0].trim();
                quantity = Integer.parseInt(elements[1].trim());
                name = elements[2].trim();
            }

            performAction(action, name, quantity);
        }
    }

    private static void performAction(String action, String name, int quantity) {
        if (action.equals("add")) {
            performAddAction(name, quantity);
        }
        else
        if (action.equals("buy")) {
            performBuyAction(name, quantity);
        }
        else if (action.equals("list"))
            displayLists();

//        displayLists();
//        System.out.println("-------------");
    }

    private static void displayLists() {
//        System.out.println("displayLists");
        System.out.println("========================");
        System.out.println("Shopping List:");
        for(Item item : shoppingList)
            System.out.println(item);
        System.out.println();
        System.out.println("Purchase List:");
        for(Item item: purchaseList)
            System.out.println(item);
        System.out.println();
    }

    public static void main(String[] args) {
        try {
            readFile("C:\\Users\\piypatel\\Downloads\\workspace\\scala-programming\\src\\main\\java\\a3a.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
