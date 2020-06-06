package main;

import java.util.Scanner;

import javax.swing.JOptionPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class VirtualATMGUI {
	
	static Scanner sc = new Scanner(System.in);
	static List<Account> existingAccounts = new ArrayList<>();
	
	public static void main(String[] args) {
		new VirtualATMGUI();
	}// main
	
	VirtualATMGUI() {
		existingAccounts = readObject();
		welcomeScreen();
	}
	
	public void welcomeScreen() {
		String[] options = {"Create", "SignIn", "Delete All Accounts","Exit"};
		int input = JOptionPane.showOptionDialog(null, "Select An Option", "Virtual ATM", 0, JOptionPane.QUESTION_MESSAGE, null, options,0);
		switch(input) {
			case 0: createAccount();
					break;
			case 1: signIn();
					break;
			case 2: deleteAllAccounts();
					break;
			default: JOptionPane.showMessageDialog(null, "****Goodbye****");
					System.exit(0);
		}
	}
			
	public void createAccount() {
		// Customer related detail
		String firstName = JOptionPane.showInputDialog(null, "First Name: ");
		String lastName = JOptionPane.showInputDialog(null, "Last Name: ");
		String accountNum = JOptionPane.showInputDialog(null, "Account Number: ");
		String password = JOptionPane.showInputDialog(null, "Password: ");
		
		// Create new account object then check if an account with the attempted AccountNumber already exists
		Account newAccount = new Account(accountNum, password);
		if(!existingAccounts.contains(newAccount)) {
			Customer newCustomer = new Customer(firstName, lastName);
			newAccount.setCustomer(newCustomer);
			existingAccounts.add(newAccount);
			JOptionPane.showMessageDialog(null, "****Account Created Successfully****");
			mainOptionScreen(newAccount);
		} else {
			JOptionPane.showMessageDialog(null, "****ACCOUNT NUMBER EXISTS****", "****ERROR****", JOptionPane.WARNING_MESSAGE);
			welcomeScreen();
		}		
	}
	
	public void signIn() {
		if(existingAccounts.size() < 1) {
			JOptionPane.showMessageDialog(null, "****NO SAVED ACCOUNTS PLEASE CREATE****", "****ERROR****", JOptionPane.WARNING_MESSAGE);
			welcomeScreen();
		}
		String accountNum = JOptionPane.showInputDialog(null, "Account Number: ").trim();
		String password = JOptionPane.showInputDialog(null, "Password: ").trim();
		Account signInAttempt = new Account(accountNum, password);
		if(existingAccounts.contains(signInAttempt)) {
			JOptionPane.showMessageDialog(null, "****Sign in Success****");
			int indexOfExisting = existingAccounts.indexOf(signInAttempt);
			Account existingAccount = existingAccounts.get(indexOfExisting);
			mainOptionScreen(existingAccount);
		} else {
			JOptionPane.showMessageDialog(null, "****INCORRECT USERNAME OR PASSWORD****", "****ERROR****", JOptionPane.WARNING_MESSAGE);
			welcomeScreen();
		}
	}

	public void reAttemptChoice() {
		String[] options = {"Create", "SignIn", "Exit"};
		int input = JOptionPane.showOptionDialog(null, "Select An Option", "Virtual ATM", 0, JOptionPane.QUESTION_MESSAGE, null, options,0);
		switch(input) {
			case 0: createAccount();
					break;
			case 1: signIn();
					break;
			default: JOptionPane.showMessageDialog(null, "****Goodbye****");
					System.exit(0);
		}
	}
		
	
	public void mainOptionScreen(Account account) {
		String[] options = {"Deposit", "Withdraw", "View Balance", "View Account Details", "SignOut", "Save and Sign Out", "Exit"};
		int input = JOptionPane.showOptionDialog(null, "Select An Option", "Virtual ATM", 0, JOptionPane.QUESTION_MESSAGE, null, options,0);
		switch(input) {
			case 0: deposit(account);
					break;
			case 1: withdraw(account);
					break;
			case 2: viewBalance(account);
					break;
			case 3: viewDetails(account);
					break;
			case 4: welcomeScreen();
					break;
			case 5: writeObject(existingAccounts);
					break;
			default: JOptionPane.showMessageDialog(null, "****Goodbye****");				
					System.exit(0);
		}
	}
	
	public void viewDetails(Account account) {
		JOptionPane.showMessageDialog(null, "Account Number: " + account.getAccountNumber()	+ "\nName: " + account.getCustomer());
		mainOptionScreen(account);
	}
	
	public void viewBalance(Account account) {
		JOptionPane.showMessageDialog(null, "Balance: € " + account.getBalance());
		mainOptionScreen(account);
	}
	
	public void deposit(Account account) {
		String amount = JOptionPane.showInputDialog(null, "Enter an amount to Deposit: ");
		account.deposit(Float.parseFloat(amount));
		JOptionPane.showMessageDialog(null, "Deposited: € " + amount);
		JOptionPane.showMessageDialog(null, "Balance: € " + account.getBalance());
		mainOptionScreen(account);
	}
	
	public void withdraw(Account account) {
		String amount = JOptionPane.showInputDialog(null, "Enter an amount to Withdraw: ");
		account.withdraw(Float.parseFloat(amount));
		JOptionPane.showMessageDialog(null, "Withdrew: € " + amount);
		JOptionPane.showMessageDialog(null, "Balance: € " + account.getBalance());
		mainOptionScreen(account);
	}
		
	public void deleteAllAccounts() {
		existingAccounts = new ArrayList<>();
		writeObject(existingAccounts);
	}
	public void writeObject(List<Account> accountList) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("accounts.ser"));
			oos.writeObject(existingAccounts);
			oos.close();
			JOptionPane.showMessageDialog(null, "SUCCESS");
		} catch(IOException e) {
			e.printStackTrace();
		}
		welcomeScreen();
	}
	
	@SuppressWarnings("unchecked")
	public static List<Account> readObject() {
		List<Account> deserializedList = new ArrayList<>();
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("accounts.ser"));
			deserializedList = (List<Account>) ois.readObject();
			ois.close();
			//JOptionPane.showMessageDialog(null, "SUCCESS");
		} catch(ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		return deserializedList;
	}
	
}// class