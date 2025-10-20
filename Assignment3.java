//ASSIGNMENT 3:
//        Scenario: Emergency Relief Supply Distribution
//        A devastating flood has hit multiple villages in a remote area, and the government, along
//        with NGOs, is organizing an emergency relief operation. A rescue team has a limited-
//        capacity boat that can carry a maximum weight of W kilograms. The boat must transport
//        critical supplies, including food, medicine, and drinking water, from a relief center to the
//        affected villages.
//        Each type of relief item has:
//        ● A weight (wi) in kilograms.
//        ● Utility value (vi) indicating its importance (e.g., medicine has higher value than food).
//        ● Some items can be divided into smaller portions (e.g., food and water), while others must
//        be taken as a whole (e.g., medical kits).
//        As the logistics manager, you must:
//        1. Implement the Fractional Knapsack algorithm to maximize the total utility value of the
//        supplies transported.
//        2. Prioritize high-value items while considering weight constraints.
//        3. Allow partial selection of divisible items (e.g., carrying a fraction of food packets).
//        4. Ensure that the boat carries the most critical supplies given its weight limit W.
//        Code :
import java.util.*;

class ReliefItem {
    String itemName;
    double weightKg;
    double importance;    // Utility value
    boolean canDivide;    // Whether partial loading is allowed

    public ReliefItem(String itemName, double weightKg, double importance, boolean canDivide) {
        this.itemName = itemName;
        this.weightKg = weightKg;
        this.importance = importance;
        this.canDivide = canDivide;
    }

    // Calculate importance per kg
    public double importancePerKg() {
        return importance / weightKg;
    }
}

public class Assignment3 {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("=== Emergency Relief Supply Distribution ===");

        // Input number of items
        System.out.print("Enter number of relief items: ");
        int itemCount = input.nextInt();
        input.nextLine(); // Consume newline

        if (itemCount <= 0) {
            System.out.println("No items to load. Exiting.");
            input.close();
            return;
        }

        List<ReliefItem> supplies = new ArrayList<>();

        // Input item details
        for (int i = 0; i < itemCount; i++) {
            System.out.println("\nItem #" + (i + 1));

            System.out.print("Name: ");
            String name = input.nextLine();

            System.out.print("Weight (kg): ");
            double weight = input.nextDouble();
            if (weight <= 0) {
                System.out.println("Invalid weight. Must be > 0. Exiting.");
                input.close();
                return;
            }

            System.out.print("Importance Value: ");
            double value = input.nextDouble();
            if (value < 0) {
                System.out.println("Invalid importance. Must be >= 0. Exiting.");
                input.close();
                return;
            }

            System.out.print("Divisible? (1 = yes, 0 = no): ");
            boolean divisible = input.nextInt() == 1;
            input.nextLine(); // consume newline

            supplies.add(new ReliefItem(name, weight, value, divisible));
        }

        // Input boat capacity
        System.out.print("\nEnter boat capacity (kg): ");
        double boatCapacity = input.nextDouble();
        if (boatCapacity <= 0) {
            System.out.println("Boat capacity must be > 0. Exiting.");
            input.close();
            return;
        }

        long startTime = System.nanoTime();

        // Sort items by importance per kg descending
        supplies.sort((a, b) -> Double.compare(b.importancePerKg(), a.importancePerKg()));

        double totalImportance = 0;
        double remainingCapacity = boatCapacity;

        System.out.println("\n=== Supplies Loaded on Boat ===");

        for (ReliefItem item : supplies) {
            if (remainingCapacity <= 0) break; // boat full

            if (item.canDivide) {
                double loadWeight = Math.min(item.weightKg, remainingCapacity);
                double loadImportance = item.importancePerKg() * loadWeight;
                totalImportance += loadImportance;
                remainingCapacity -= loadWeight;

                System.out.printf(" - %s: %.2f kg, Importance: %.2f%n", item.itemName, loadWeight, loadImportance);
            } else {
                if (item.weightKg <= remainingCapacity) {
                    totalImportance += item.importance;
                    remainingCapacity -= item.weightKg;

                    System.out.printf(" - %s: %.2f kg, Importance: %.2f%n", item.itemName, item.weightKg, item.importance);
                }
            }
        }

        System.out.printf("\nTotal importance carried: %.2f%n", totalImportance);

        long endTime = System.nanoTime();
        System.out.printf("Execution time: %.3f ms%n", (endTime - startTime) / 1_000_000.0);

        input.close();
    }
}
