import java.util.Scanner;

class Car {
    private String model;
    private double showroomPrice;
    private double discount;

    public Car(String model, double showroomPrice) {
        this.model = model;
        this.showroomPrice = showroomPrice;
        this.discount = 0;
    }

    public double calculateTotalCost(boolean insurance, boolean accessories, double dealerDiscount) {
        double totalCost = showroomPrice;

        if (insurance)
            totalCost += 47300;
        if (accessories)
            totalCost += 15000;

        if (dealerDiscount > 0) {
            if (!insurance && !accessories) {
                System.out.println("Error: Any one of the additional features has to be added, and discount cannot be applied.");
                return totalCost;
            }

            if (dealerDiscount > 30000) {
                System.out.println("Error: Maximum discount cannot exceed 30,000. Applying maximum discount instead.");
                dealerDiscount = 30000;
            }

            totalCost -= dealerDiscount;
        }

        totalCost += 113990; // RTO charges
        totalCost += 11000; // TCS charges

        return totalCost;
    }

    public String getModel() {
        return model;
    }

    public double getShowroomPrice() {
        return showroomPrice;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}

public class CarDealerBilling {
    public static void main(String[] args) {
        Car[] cars = {
                new Car("Polo Trendline", 870000),
                new Car("Polo Highline", 1009000),
                new Car("Virtus Trendline", 1105000),
                new Car("Virtus Highline", 1308000),
                new Car("Taigun Trendline", 1489000),
                new Car("Taigun Highline", 1542000),
                new Car("Taigun Topline", 1771000)
        };

        Scanner scanner = new Scanner(System.in);

        System.out.println("Available Car Models:");
        for (int i = 0; i < cars.length; i++) {
            System.out.println((i + 1) + ". " + cars[i].getModel());
        }

        System.out.print("Select car model (Enter the model number): ");
        int carChoice = scanner.nextInt();

        Car selectedCar = cars[carChoice - 1];

        System.out.print("Do you need Insurance (yes/no): ");
        String insuranceChoice = scanner.next();

        boolean needInsurance = insuranceChoice.equalsIgnoreCase("yes");

        System.out.print("Do you need Additional Accessories (yes/no): ");
        String accessoriesChoice = scanner.next();

        boolean needAccessories = accessoriesChoice.equalsIgnoreCase("yes");

        double dealerDiscount = 0;

        if (needInsurance || needAccessories) {
            System.out.print("Dealer discount (% or amount in rupees, max 30,000): ");
            String discountChoice = scanner.next();

            if (discountChoice.endsWith("%")) {
                double discountPercentage = Double.parseDouble(discountChoice.substring(0, discountChoice.length() - 1));
                dealerDiscount = (selectedCar.getShowroomPrice() * discountPercentage) / 100;
            } else {
                discountChoice = discountChoice.replace(",", "");
                dealerDiscount = Double.parseDouble(discountChoice);
            }

            if (dealerDiscount > 30000) {
                System.out.println("Error: Maximum discount cannot exceed 30,000. Applying maximum discount instead.");
                dealerDiscount = 30000;
            }
        }

        selectedCar.setDiscount(dealerDiscount);

        double totalCost = selectedCar.calculateTotalCost(needInsurance, needAccessories, dealerDiscount);

        System.out.println("\nTotal cost: " + totalCost);
    }
}
