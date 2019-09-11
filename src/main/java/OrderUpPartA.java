import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderUpPartA {
	private static List<Order> orders = new ArrayList<>();

	public static void main(String[] args) {
		try {
			readFile("C:\\Users\\piypatel\\Downloads\\workspace\\scala-programming\\src\\main\\java\\a4a.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Summary of Orders:");
		System.out.println("==================");
		printOrders();
		System.out.println();

		System.out.println("Total:");
		System.out.println("=====================");
		System.out.println("Total Donut orders: " + countDonutOrders());
		System.out.printf("TOTAL ORDER PRICE: %.2f", calculateTotalPrice());
	}

	private static void readFile(String file) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File(file));
		while (scanner.hasNext()) {
			String line = scanner.nextLine();
			String[] arr = line.trim().split(",");
			int quantity = Integer.parseInt(arr[1]);
			if(arr[0].equalsIgnoreCase("coffee")) {
				String size = arr[2];
//				System.out.println("Creating order of " + quantity + " " + size);
				orders.add(new CoffeeOrder(quantity, size));
			} else if (arr[0].equalsIgnoreCase("donut")) {
				double price = Double.parseDouble(arr[2]);
				String flavour = arr[3];
				orders.add(new DonutOrder(quantity, price, flavour));
			}
		}
	}

	private static void printOrders() {
		for(Order order: orders) {
			System.out.println(order);
		}
	}

	private static int countDonutOrders() {
		int total = 0;
		for(Order order: orders) {
			if(order instanceof DonutOrder) {
				total += order.getQuantity();
			}
		}
		return total;
	}

	private static double calculateTotalPrice() {
		double total = 0;
		for (Order order: orders) {
			total += order.totalPrice();
		}
		return total;
	}
}
