import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class OrderUpPartB {
	private static List<Order> orders = new ArrayList<>();

	public static void main(String[] args) {
		try {
			readFile("C:\\Users\\piypatel\\Downloads\\workspace\\scala-programming\\src\\main\\java\\a4b.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Summary of Orders:");
		System.out.println("==================");
		System.out.println();
		printOrders();
		System.out.println();
		System.out.println("All orders in sorted order");
		System.out.println("===============================");
		System.out.println();
		Collections.sort(orders);
		printOrders();
		System.out.println();
		System.out.println("Totals product wise:");
		System.out.println("=========================");
		System.out.println();
		System.out.println("Total Coffee orders: " + countCoffeeOrders());
		System.out.println("Total Donut orders: " + countDonutOrders());
		System.out.println("Total Pop orders: " + countPopOrders());
		System.out.println("Total Sandwich orders: " + countSandwichOrders());
		System.out.println();
		System.out.printf("TOTAL PRICE: %.2f", calculateTotalPrice());

	}

	private static void readFile(String file) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File(file));
		while (scanner.hasNext()) {
			String line = scanner.nextLine();
			String[] arr = line.trim().split(",");
			String orderType = arr[0];
			int quantity = Integer.parseInt(arr[1]);
			if(orderType.equalsIgnoreCase("coffee")) {
				String size = arr[2];
				orders.add(new CoffeeOrderSizable(quantity, size));
			} else if (orderType.equalsIgnoreCase("donut")) {
				double price = Double.parseDouble(arr[2]);
				String flavour = arr[3];
				orders.add(new DonutOrderNonSizable(quantity, price, flavour));
			} else if (orderType.equalsIgnoreCase("pop")) {
				String size = arr[2];
				String brand = arr[3];
				orders.add(new PopOrderSizable(quantity, size, brand));
			} else if (orderType.equalsIgnoreCase("sandwich")) {
				double price = Double.parseDouble(arr[2]);
				String filling = arr[3];
				String bread = arr[4];
				orders.add(new SandwichOrderNonSizable(quantity, price, filling, bread));
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
			if(order instanceof DonutOrderNonSizable) {
				total += order.getQuantity();
			}
		}
		return total;
	}

	private static int countPopOrders() {
		int total = 0;
		for(Order order: orders) {
			if(order instanceof PopOrderSizable) {
				total += order.getQuantity();
			}
		}
		return total;
	}

	private static int countCoffeeOrders() {
		int total = 0;
		for(Order order: orders) {
			if(order instanceof CoffeeOrderSizable) {
				total += order.getQuantity();
			}
		}
		return total;
	}

	// Not very efficient way, as we are looping everytime
	// Instead we can keep an array list or map that can hold value of total in one iteration
	private static int countSandwichOrders() {
		int total = 0;
		for(Order order: orders) {
			if(order instanceof SandwichOrderNonSizable) {
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
