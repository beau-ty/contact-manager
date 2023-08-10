package ContactsManager;

import org.w3c.dom.ls.LSOutput;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ContactsManagerProject {

	public static Input input = new Input();

	public static ArrayList<Contact> contactList = new ArrayList<>();

	public static int displayMenu() {
		System.out.println("1. View contacts");
		System.out.println("2. Add a new contact");
		System.out.println("3. Search a contact by name");
		System.out.println("4. Delete an existing contact");
		System.out.println("5. Exit");
		System.out.println("Enter an option (1, 2, 3, 4 or 5): ");
		int userMenuResponse = input.getInt(1, 5);
		return userMenuResponse;


	}

	public static void searchContacts() {
		do {
			System.out.println("Enter contact name");
			String searchResponse = input.getString();
			for (Contact contact : contactList) {
				if (contact.getName().equalsIgnoreCase(searchResponse)){
					System.out.println(contact.getName()+ "  ||  " + contact.getPhoneNumber());
				}else {
					System.out.println("Please enter a valid contact name.");
				}
			}
		} while(true);

	}

	public static void displayContacts() {
		System.out.println("Name || Phone Number");
		for (Contact contact : contactList) {
			System.out.println(contact.getName()+ "  ||  " + contact.getPhoneNumber());
		}
		System.out.println("\n\n======== End =========");
	}

	public static void addContact() {
		System.out.println("Enter a new contact name: ");
		String inputName = input.getString();
		System.out.println("Enter a new contact phone number: ");
		long inputNumber = input.getInt();
		Contact newContact = new Contact(inputName, inputNumber);
		contactList.add(newContact);
	}



	public static void createFile() {

		String directory = "data";
		String fileName = "contacts.txt";


		Path dataDirectory = Paths.get(directory);
		Path dataFile = Paths.get(directory, fileName);


		if (!Files.exists(dataDirectory)) {
			try {
				Files.createDirectories(dataDirectory);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}


		if (!Files.exists(dataFile)) {
			try {
				Files.createFile(dataFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}



	}



	public static void initialize() {
		readContactList();
		displayMenu();
		addContact();
		addContact();
		searchContacts();



	}

	public static void readContactList() {

		try {
			List<String> listOfContacts = Files.readAllLines(
					Paths.get("data/contacts.txt")
			);

			for (String contact : listOfContacts) {
				System.out.println(contact);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	public static void main (String[]args){
		initialize();


	}

}
