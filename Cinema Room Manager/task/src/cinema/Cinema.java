package cinema;

import java.util.Scanner;
import java.util.StringJoiner;

public class Cinema {

    private static final int BACK_SEAT_PRICE = 8;
    private static final int FRONT_SEAT_PRICE = 10;

    private final int numberOfRows;
    private final int numberOfSeats;
    private final int totalNumberOfSeats;
    private final int totalIncome;
    private final boolean[][] soldSeats;
    private int income;
    private int numberOfSoldTickets;

    public Cinema(int numberOfRows, int numberOfSeats) {
        this.numberOfRows = numberOfRows;
        this.numberOfSeats = numberOfSeats;
        totalNumberOfSeats = numberOfRows * numberOfSeats;
        totalIncome = getTotalIncome();
        soldSeats = new boolean[numberOfRows][numberOfSeats];
        income = 0;
        numberOfSoldTickets = 0;
    }

    public static void main(String[] args) {
        processCustomer();
    }

    public static void processCustomer() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        final int numberOfRows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        final int numberOfSeats = scanner.nextInt();
        Cinema cinema = new Cinema(numberOfRows, numberOfSeats);
        while (true) {
            System.out.println();
            Cinema.showMenu();
            switch (scanner.nextInt()) {
                case 0:
                    return;
                case 1:
                    System.out.println();
                    cinema.showSeats();
                    break;
                case 2:
                    System.out.println();
                    System.out.println("Enter a row number:");
                    int customerRow = scanner.nextInt();
                    System.out.println("Enter a seat number in that row:");
                    int customerSeat = scanner.nextInt();
                    while (!cinema.setSeatAsSold(customerRow, customerSeat)) {
                        System.out.println();
                        System.out.println("Enter a row number:");
                        customerRow = scanner.nextInt();
                        System.out.println("Enter a seat number in that row:");
                        customerSeat = scanner.nextInt();
                    }
                    break;
                case 3:
                    System.out.println();
                    cinema.showStatistics();
                    break;
            }
        }
    }

    public static void showMenu() {
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
    }

    public void showSeats() {
        System.out.println("Cinema:");
        StringJoiner stringJoiner = new StringJoiner(" ", "  ", "");
        for (int i = 1; i <= numberOfSeats; i++) {
            stringJoiner.add(Integer.toString(i));
        }
        System.out.println(stringJoiner);
        for (int i = 0; i < numberOfRows; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < numberOfSeats; j++) {
                System.out.print(soldSeats[i][j] ? "B " : "S ");
            }
            System.out.println();
        }
    }

    public void showStatistics() {
        System.out.printf("Number of purchased tickets: %d%n", numberOfSoldTickets);
        System.out.printf("Percentage: %.2f%%%n", (double) numberOfSoldTickets / totalNumberOfSeats * 100);
        System.out.printf("Current income: $%d%n", income);
        System.out.printf("Total income: $%d%n", totalIncome);
    }

    public int getPriceOfTicket(int customerRow) {
        if (totalNumberOfSeats <= 60) {
            return FRONT_SEAT_PRICE;
        } else {
            if (customerRow > numberOfRows / 2) {
                return BACK_SEAT_PRICE;
            } else {
                return FRONT_SEAT_PRICE;
            }
        }
    }

    public int getTotalIncome() {
        if (totalNumberOfSeats <= 60) {
            return totalNumberOfSeats * FRONT_SEAT_PRICE;
        } else {
            return numberOfRows / 2 * numberOfSeats * FRONT_SEAT_PRICE +
                    (numberOfRows / 2 + ((numberOfRows & 1) == 0 ? 0 : 1)) * numberOfSeats * BACK_SEAT_PRICE;
        }
    }
    
    public boolean setSeatAsSold(int customerRow, int customerSeat) {
        if (customerRow > numberOfRows || customerSeat > numberOfSeats) {
            System.out.println("Wrong input!");
            return false;
        }
        if (soldSeats[customerRow - 1][customerSeat - 1]) {
            System.out.println("That ticket has already been purchased!");
            return false;
        }
        int price = getPriceOfTicket(customerRow);
        System.out.printf("Ticket price: $%d", price);
        income += price;
        numberOfSoldTickets++;
        return soldSeats[customerRow - 1][customerSeat - 1] = true;
    }

}