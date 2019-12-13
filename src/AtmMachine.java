
import java.io.*; //Import all necessary packages
import java.util.*; //
import java.nio.file.*;
import java.nio.charset.*;

public class AtmMachine {

	public static String user; // Declare globally user variable
	public static String directory = "resources/"; //getting the path of the text file

	public static void main(String[] args) {
		welcome(); // calling custom method welcome
		login(); // calling custom method login

	}

	public static void welcome() {
		System.out.println("************************************");
		System.out.println("      WELCOME TO ATM MACHINE        ");
		System.out.println("************************************");
		// printing welcome message
	}

	public static void login() {
		Scanner keyboard = null;
		try {

			keyboard = new Scanner(System.in); // initialising keyboard variable for reading input
			System.out.print("Enter UserId: ");
			user = keyboard.nextLine(); // storing user ID in to user variable
			System.out.print("Enter Pin Number:");
			String pass = keyboard.nextLine();
			// storing pin number to pass variable reading user details file
			String record = null;
			boolean f = false;
			String userDetailsFile = directory + "userDetails.txt";
			BufferedReader br = new BufferedReader(new FileReader(userDetailsFile));
			while ((record = br.readLine()) != null) {
				String[] split = record.split("\\s");
				// splitting each line of the user detail file in to to split array until end of the file

				if (user.equals(split[0]) && pass.equals(split[1])) {
					// checking weather user name and pin number matching to the user details file
					f = true;
					break;

				}

			}
			br.close();
			if (f) {
				System.out.println("-----------------------------------------------------------------------------------------------");
				menu(); // calling menu function if the details are correct
			} else {
				System.out.println("------------------------------------------------------------------------------------------------");
				System.err.println("Please enter correct user id and pin number.");
				// printing the error message is the details entered are wrong
				login(); // calling to login function
			}

		} catch (IOException e) {
			e.getCause(); // to get the cause of exception
		} finally {
			if (keyboard != null)
				keyboard.close(); //closing the variable
		}

	}

	public static void menu() {
		// printing all the menu
		System.out.println("1.Current account balance");
		System.out.println("2.Withdraw money ");
		System.out.println("3.Change current password");
		System.out.println("4.Latest stock prices for the bank");
		System.out.println("5.Logout");
		System.out.println("6.Bank Summary");
		System.out.println("7.Mini Statement");
		System.out.print("Choose the operation you want to perform:");
		try {
			Scanner s = new Scanner(System.in);
			int n = s.nextInt(); // storing the entering input to n

			switch (n) {
			case 1:
				currentBalance(); // calling Current balance method if entering 1
				break;
			case 2:
				withdrawMoney(); // calling Withdraw Money method if entering 2
				break;
			case 3:
				changePassword(); // calling Change Password method if entering 3
				break;
			case 4:
				stockPrice(); // calling Stock Price method if entering 4
				break;
			case 5:
				logout(); // calling Logout method if entering 5
				break;
			case 6:
				totalBalance(); // calling Total balance method if entering 6
				break;
			case 7:
				MiniStatement(); // calling Mini Statement method if entering 7
				break;
			default:
				System.out.println("------------------------------------------------------------------------------------------------------");
				System.err.println("Enter valid option"); // calling menu method if the user enter other than 1 to 7
				menu(); // calling to menu function
				s.close(); //closing the variable

			}
		} catch (Exception e) {
			System.out.println("----------------------------------------------------------------------------------------------------------");
			System.err.println("Enter only numbers"); // if the user enter rather than number will print this message and calling the menu function
			e.getCause();
			menu(); // calling to menu function
		}
	}

	public static void currentBalance() {
		try {
			// reading the corresponding user file
			FileReader userDetails = new FileReader(directory + user + ".txt");
			BufferedReader AB = new BufferedReader(userDetails);
			String sCurrentLine; // declare a string sCurrentLine
			String lastLine = ""; // declare an initialise as string lastline to be null

			while ((sCurrentLine = AB.readLine()) != null) {
				lastLine = sCurrentLine;

			}
			String bal[] = (lastLine).split("\\s");
			// splitting each line of the user detail file in to to split array until end of the file
			int balance = Integer.parseInt(bal[1]);
			System.out.println("----------------------------------------------------------------------------------------------------------");
			System.out.println("Current balance is:" + balance);
			System.out.println("-----------------------------------------------------------------------------------------------------------");
			menu(); // calling to menu function
			AB.close(); //closing the variable
		} catch (IOException e) {
			e.getCause();
		}

	}

	public static void withdrawMoney() {
		try {
			System.out.print("Enter amount to be withdraw: (eg: 10, 20, 30, 40, 50, .......) : ");
			Scanner amount = new Scanner(System.in);
			int wAmount = amount.nextInt();
			int rem = wAmount % 10; // to getting the reminder
			// reading the corresponding user file
			FileReader userDetails = new FileReader(directory + user + ".txt");
			BufferedReader AB = new BufferedReader(userDetails);
			String sCurrentLine; // declare a string sCurrentLine
			String lastLine = ""; // declare an initialise as string last line to be null

			while ((sCurrentLine = AB.readLine()) != null) {
				lastLine = sCurrentLine;

			}
			String bal[] = (lastLine).split("\\s");
			// spliting each line of the user detail file in to to split array until end of the file
			int balance = Integer.parseInt(bal[1]);
			if (rem == 0) { // checking is the amount that entered is multiple of 10
				if (wAmount > balance) { // checking withdraw amount is grater than balance
					System.out.println("Not enough balance to withdraw");
					System.out.println("--------------------------------------------------------------------------------------------");
					menu(); // calling to menu function

				} else {
					int diff = balance - wAmount;
					FileWriter fstream = new FileWriter(directory + user + ".txt", true); // write new balance and date in to user file
					BufferedWriter writer = new BufferedWriter(fstream);
					Date date = new Date();
					writer.newLine();
					writer.write(date.toString() + " WidrawedAmount " + wAmount);
					writer.newLine();
					writer.write("accountBalance " + diff);
					System.out.println("New balance is:" + diff); // printing current balance
					writer.close();
					System.out.println("--------------------------------------------------------------------------------------------");
					menu(); // calling to menu function

				}

			} else {
				System.out.println("----------------------------------------------------------------------------------------------");
				System.err.println("Please enter multiples of 10 ");	
				withdrawMoney();
				amount.close(); //closing the variable
				AB.close(); //closing the variable
			}
		} catch (IOException e) {
			e.getCause();
		}

	}

	public static void changePassword() {
		try {
			System.out.println("Enter new 4 digit password");
			Scanner s1 = new Scanner(System.in); // asking the new password
			String newpass = s1.nextLine();
			if (newpass.length() == 4) { // checking whether the password contains 4 digit.
				System.out.println("Renter your new 4 digit password to verify");
				Scanner s2 = new Scanner(System.in); // confirming the new password
				String verifiedpass = s2.nextLine();

				if (verifiedpass.equals(newpass)) {

					FileReader exist = new FileReader(directory + "userDetails.txt"); // file reading
					BufferedReader userExist = new BufferedReader(exist);
					String usrExist = null;
					int lineNumber = 0;
					while ((usrExist = userExist.readLine()) != null) {

						String[] usrSplit = usrExist.split("\\s");

						if (user.equals(usrSplit[0])) {

							String data = user + " " + newpass; // storing new password along with the user name

							Path path = Paths.get(directory + "userDetails.txt"); // reading user details file
							List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8); // reading all lines of the file in the lines
							lines.set(lineNumber, data); // setting corresponding data to the appropriate line.
							Files.write(path, lines, StandardCharsets.UTF_8); // writing the changed password to the user details file
							System.out.println("----------------------------------------------------------------------------------");
							System.out.println("You have successfully changed your password!");
							System.out.println("----------------------------------------------------------------------------------");
							menu(); // calling to menu function
							userExist.close(); // closing the variable

						} else {
							lineNumber = lineNumber + 1;
						}
					}
				} else {
					System.out.println(
							"----------------------------------------------------------------------------------");
					System.err.println("Entered password do not match. Please try again");
					System.out.println("----------------------------------------------------------------------------------");
					changePassword(); // calling to Change password function if the password didn't match
					s1.close(); // closing the variable
					s2.close(); // closing the variable

				}
			} else {
				System.out
						.println("----------------------------------------------------------------------------------");
				System.err.println("Enter only 4 digit");
				System.out.println("----------------------------------------------------------------------------------");
				changePassword(); // calling to Change password function if the password didn't match
			}
		} catch (IOException e) {
			e.getCause();
		}

	}

	public static void stockPrice() {
		try {
			System.out.println("-----------------------------------------------------------------------------------------------");
			System.out.println("**********  Stock Price  **************");
			System.out.println(new String(Files.readAllBytes(Paths.get(directory + "stocks.txt")))); // reading all the contents of stock file and printing to the console
			System.out.println("Do you want to return to main menu?  press Y or N");
			Scanner input = new Scanner(System.in);
			String output = input.nextLine();

			if (output.equalsIgnoreCase("Y")) { // if user enter y will go to menu
				System.out.println("--------------------------------------------------------------------------------------------");
				menu();
			} else if (output.equalsIgnoreCase("N")) { // if user enter N will exit
				logout(); // calling the logout function if user choose N
				input.close(); //closing the variable
			}
		} catch (IOException e) {
			e.getCause();

		}
	}

	public static void logout() {
		// logging out
		System.out.println("*******************************************************");
		System.out.println("            Thank you for using this ATM               ");
		System.out.println("                    Good Bye.......!                   "); // printing Good bye message
		System.out.println("*******************************************************");
		System.exit(0);
	}

	public static void totalBalance() {
		try {
			BufferedReader allUserExist = new BufferedReader(new FileReader(directory + "userDetails.txt")); // reading user detail file
			String allUsers = null;
			int total = 0;
			int small = 0;
			int medium = 0;
			int large = 0;
			int exraLarge = 0;
			while ((allUsers = allUserExist.readLine()) != null) {
				String[] allUsersSplit = allUsers.split("\\s");
				BufferedReader input = new BufferedReader(new FileReader(directory + allUsersSplit[0] + ".txt")); 
				// reading each user file
				String line;
				String last = null;
				while ((line = input.readLine()) != null) {
					last = line;

				}
				String tot[] = (last).split("\\s");
				total = total + Integer.parseInt(tot[1]);
				if (Integer.parseInt(tot[1]) <= 100) {
					small++;
					// all the customers who have less than 100 balance will add in small
				} else if (101 <= Integer.parseInt(tot[1]) && Integer.parseInt(tot[1]) <= 200) {
					medium++;
					// all the customers who have balance in between 101 and 200 will add in medium
				} else if (201 <= Integer.parseInt(tot[1]) && Integer.parseInt(tot[1]) <= 300) {
					large++;
					// all the customers who have balance in between 201 and 300 will add in large
				} else if (Integer.parseInt(tot[1]) > 300) {
					exraLarge++;
					// all the customers who have more than 300 balance will add in extralarge
					input.close(); //closing the variable

				}
			}

			System.out.println("--------------------------------------------------------------------------------------------");
			System.out.println("Total account balance for the entire bank is:" + total); // printing the total account balance for the entire bank
			System.out.println("Account Catgories");
			System.out.println("Total number of small account:" + small); // printing total number of small account
			System.out.println("Total number of medium account:" + medium); // printing total number of medium account
			System.out.println("Total number of large account:" + large); // printing total number of large account
			System.out.println("Total number of extra large account:" + exraLarge); // printing total number of extra large account
			System.out.println("--------------------------------------------------------------------------------------------");
			menu(); // calling to menu function
			allUserExist.close(); //closing the variable

		} catch (IOException e) {
			e.getCause();
		}
	}

	public static void MiniStatement() {
		try {
			System.out.println("Mini Statement ");
			System.out.println("--------------------------------------------------------------------------------------------");
			System.out.println(new String(Files.readAllBytes(Paths.get(directory + user + ".txt")))); // printing mini statement
			System.out.println("--------------------------------------------------------------------------------------------");
			menu(); // calling to menu function

		} catch (IOException e) {
			e.getCause();

		}

	}

}
