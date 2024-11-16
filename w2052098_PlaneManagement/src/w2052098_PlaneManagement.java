import java.util.Scanner;

public class w2052098_PlaneManagement {
    private static char[][] seatPlan = new char[][]{
            {'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O'},
            {'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O'},
            {'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O'},
            {'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O'}
    };

    public static Ticket[] Purchased_Ticket_Array;
    public static int Purchased_Ticket_Count;

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        int option;

        do {
            System.out.println("\n*  Welcome to the Plane Management Application  *");
            printMenu();
            System.out.print("\nSelect option from menu: ");
            option = Integer.parseInt(input.next());
            switch (option) {
                case 1:
                    buy_seat();
                    break;
                case 2:
                    cancel_seat();
                    break;
                case 3:
                    find_first_available();
                    break;
                case 4:
                    show_seating_plan();
                    break;
                case 5:
                    print_ticket_info();
                    break;
                case 6:
                    Search_Ticket();
                    break;
            }
        } while (option != 0);

        input.close();
    }


    public static void printMenu() {
        System.out.println("\n*************************************************");
        System.out.println("*\t\t\t\t Menu Options \t\t\t\t\t*");
        System.out.println("*************************************************");
        System.out.println("1) Buy a Seat");
        System.out.println("2) Cancel a Seat");
        System.out.println("3) Find First Available Seat");
        System.out.println("4) Show Seating Plan");
        System.out.println("5) Print Tickets Information and Total Sales");
        System.out.println("6) Search Ticket");
        System.out.println("0) Quit");
        System.out.println("\n*************************************************");
    }

    // Method for buying seat
    public static void buy_seat() {
        Scanner input = new Scanner(System.in);
        do {
            System.out.println(" \nThis is the Current Seating Plan:");
            for (char[] row : seatPlan) {
                for (char seat : row) {
                    System.out.print(seat + "  ");
                }
                System.out.println();
            }

            System.out.println("Enter the seat row letter (A-D): ");
            char seat_row_letter = input.next().toUpperCase().charAt(0);

            if (seat_row_letter < 'A' || seat_row_letter > 'D') {
                System.out.println("The row letter is incorrect. Pls enter correct row letter");
                return;
            }

            if ((seat_row_letter == 'B' || seat_row_letter == 'C')) {
                System.out.print("Enter seat number (1 to 12): ");
            } else {
                System.out.print("Enter seat number (1 to 14): ");
            }

            int seat_num = input.nextInt();

            if ((seat_row_letter == 'B' || seat_row_letter == 'C') && (seat_num < 1 || seat_num > 12)) {
                System.out.println("The seat number is invalid. Enter a number from 1-12");
                continue;
            } else if ((seat_row_letter == 'A' || seat_row_letter == 'D') && (seat_num < 1 || seat_num > 14)) {
                System.out.println("The seat number is invalid. Enter a number from 1-14");
                continue;
            }

            if (seatPlan[seat_row_letter - 'A'][seat_num - 1] == 'X') {
                System.out.println("This seat has already been purchased. Please choose another seat.");
            } else {
                double price = calculatePrice(seat_row_letter, seat_num);
                System.out.println("Enter passenger name: ");
                String passengerName = input.next();
                System.out.println("Enter passenger surname: ");
                String passengerSurname = input.next();
                System.out.println("Enter passenger email: ");
                String passengerEmail = input.next();

                Person passenger = new Person(passengerName, passengerSurname, passengerEmail); //person object

                Ticket newTicket = new Ticket(seat_row_letter, seat_num, price, passenger); //ticket object

                newTicket.save();

                Purchased_Ticket_Array[Purchased_Ticket_Count] = newTicket;
                Purchased_Ticket_Count++;

                seatPlan[seat_row_letter - 'A'][seat_num - 1] = 'X';
                System.out.println("The seat has been purchased.");
            }


            System.out.println("\nWould you like to buy another ticket? (Y/N)");
            String answer = input.next().toUpperCase();

            if (answer.equals("N")) {
                break;
            }
        } while (true);
    }



    //Method for cancelling the seat
    public static void cancel_seat() {
        Scanner input = new Scanner(System.in);
        do { // a loop for the user to cancel more tickets
            // Printing out the current seating plan
            System.out.println("The Current Seating Plan:");
            for (char[] row : seatPlan) {
                for (char seat : row) {
                    System.out.print(seat + "  ");
                }
                System.out.println();
            }
            // Getting user input for seats and verifying them
            System.out.println("\nEnter the seat row letter (A-D) : ");
            char seat_row_letter = input.next().toUpperCase().charAt(0);

            if (seat_row_letter < 'A' || seat_row_letter > 'D') {
                System.out.println("The row letter is incorrect. Please enter a correct row letter");
                continue;
            }
            if ((seat_row_letter == 'B' || seat_row_letter == 'C')) {
                System.out.print("Enter seat number (1 to 12): "); // the seat numbers from row  B and C
            } else {
                System.out.print("Enter seat number (1 to 14): "); // the seat numbers from row A and D
            }
            int seat_num = input.nextInt();

            if ((seat_row_letter == 'B' || seat_row_letter == 'C') && (seat_num < 1 || seat_num > 12)) {
                System.out.println("The seat number is invalid. Enter a number from 1-12");
                continue;
            } else if ((seat_row_letter == 'A' || seat_row_letter == 'D') && (seat_num < 1 || seat_num > 14)) {
                System.out.println("The seat number is invalid. Enter a number from 1-14");
                continue;
            }

            if (seatPlan[seat_row_letter - 'A'][seat_num - 1] == 'X') {
                seatPlan[seat_row_letter - 'A'][seat_num - 1] = 'O';
                System.out.println("Your seat reservation have been cancelled");

                // Remove the canceled ticket from the ticket array
                for (int i = 0; i < Purchased_Ticket_Count; ++i) {
                    Ticket ticket = Purchased_Ticket_Array[i];
                    if (ticket.getRow() == seat_row_letter && ticket.getSeat() == seat_num) {
                        for (int j = i; j < Purchased_Ticket_Count - 1; j++) {
                            Purchased_Ticket_Array[j] = Purchased_Ticket_Array[j + 1];
                        }
                        Purchased_Ticket_Count--;
                        break;
                    }
                }
            } else {
                System.out.println("The seat you picked is available");
            }
            System.out.println("\nWould you like to cancel another ticket? (Y/N)");
            String answer = input.next().toUpperCase();

            if (answer.equals("N")) {
                break;
            }
        } while (true);
    }



    //Method to show the seat plan array
    public static void show_seating_plan() {
        System.out.println("\nSeating Plan:\n");


        for (char[] row : seatPlan) {
            for (char seat : row) {
                // Print 'O' for available seats and 'X' for sold seats
                System.out.print(seat + "  ");
            }
            // Move to the next row
            System.out.println();
        }
    }


    //Method to find the first available seat
    public static void find_first_available() {
        for (int i = 0; i < 4; i++) {
            char rowLetter = (char) ('A' + i); // Calculate the row letter dynamically
            int rowIndex = i; // Use the loop variable i as the row index

            // Determine the maximum number of seats based on the row
            int maxSeats = (rowLetter == 'A' || rowLetter == 'D') ? 14 : 12;

            // Iterate through the seats in the row to find the first available seat
            for (int j = 0; j < maxSeats; j++) {
                if (seatPlan[rowIndex][j] == 'O') {
                    System.out.println("The first available seat is: " + rowLetter + (j + 1));
                    return;
                }
            }
        }
        System.out.println("No available seats found.");
    }


    //Method to print ticket info and sales
    public static void print_ticket_info() {
        double Total_Sales = 0.0;


        for(int i = 0; i < Purchased_Ticket_Count; ++i) {
            Ticket ticket = Purchased_Ticket_Array[i];
            ticket.printTicketInfo();
            Total_Sales += ticket.getPrice();
        }

        System.out.println("\nTotal Sales: £" + Total_Sales);
    }

    //Method to search a specific ticket
    public static void Search_Ticket() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter row letter (A to D): ");
        char Row_Label = input.next().toUpperCase().charAt(0);
        if (Row_Label >= 'A' && Row_Label <= 'D') {
            System.out.print("Enter seat number (1 to 14): ");
            int Seat_Number = input.nextInt();
            if (Row_Label != 'B' && Row_Label != 'C' || Seat_Number >= 1 && Seat_Number <= 12) {
                if (Row_Label != 'B' && Row_Label != 'C' && (Seat_Number < 1 || Seat_Number > 14)) {
                    System.out.println("Invalid seat number. Please enter a valid seat number (1 to 14).");
                } else {
                    boolean Found = false;

                    for(int i = 0; i < Purchased_Ticket_Count; ++i) {
                        Ticket ticket = Purchased_Ticket_Array[i];
                        if (ticket.getRow() == Row_Label && ticket.getSeat() == Seat_Number) {
                            ticket.printTicketInfo();
                            Found = true;
                            break;
                        }
                    }

                    if (!Found) {
                        System.out.println("\nThis seat is available.");
                    }

                }
            } else {
                System.out.println("Invalid seat number. Please enter a valid seat number (1 to 12).");
            }
        } else {
            System.out.println("Invalid row letter. Please enter a valid row (A to D).");
        }
    }

    static {
        Purchased_Ticket_Array = new Ticket[seatPlan.length * seatPlan[0].length];
        Purchased_Ticket_Count = 0;
    }



    //Method to calculate the price of the tickets
    public static double calculatePrice(char row, int seatNumber) {
        if (seatNumber >= 1 && seatNumber <= 5)
            return 200.0;
        else if (seatNumber >= 6 && seatNumber <= 9)
            return 150.0;
        else if (seatNumber >= 10 && seatNumber <= 12)
            return 180.0;
        else
            return 180.0;
    }
}