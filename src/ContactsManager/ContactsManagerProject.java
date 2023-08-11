package ContactsManager;

import org.w3c.dom.ls.LSOutput;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;


public class ContactsManagerProject {

	static ArrayList<Contact> contactList = new ArrayList<>();

	public static Input input = new Input();

	public static int displayMenu() {
		System.out.println("1. View contacts");
		System.out.println("2. Add a new contact");
		System.out.println("3. Search a contact by name");
		System.out.println("4. Delete an existing contact");
		System.out.println("5. Exit");
		System.out.println("Enter an option (1, 2, 3, 4 or 5): ");
		 return input.getInt(1, 5);
	}

	public static String searchContacts() {
		boolean continueResponse;
		do {
			System.out.println(contactList);
			System.out.println("Search for a contact name.");
			String searchResponse = input.getString();
			for (Contact contact : contactList) {
				if (contact.getName().equalsIgnoreCase(searchResponse)) {
					System.out.println(contact.getName() + "  II  " + contact.getPhoneNumber());
					return contact.getName() + "  II  " + contact.getPhoneNumber();
				}
			}
			System.out.println("Contact was not found.");
			System.out.println("Would you like to continue your search? (y/N)");
			continueResponse = input.yesNo();
			if (continueResponse) {

			} else {
				break;
			}
		} while (continueResponse);

		return null;
	}

	public static void displayContacts() {
		System.out.println("Name II Phone Number");
		for (Contact contact : contactList) {
			System.out.println(contact);
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

		Path p = Paths.get("data/contacts.txt");
		try{
			Set<String> existingNames = new HashSet<>(Files.readAllLines(p));
				if(!existingNames.contains(newContact.getName())){
					Files.write(p, Collections.singletonList(newContact.getName() + "  II  " + newContact.getPhoneNumber()), StandardOpenOption.APPEND);
					existingNames.add(newContact.getName());
				}
		} catch (IOException e){
			e.printStackTrace();
		}
	}

	public static void exitFunction() {
		Path p = Paths.get("data/contacts.txt");
		try{
			Set<String> existingNames = new HashSet<>(Files.readAllLines(p));
			for(Contact contact : contactList) {
				String contactToString = contact.toString();
				if(!existingNames.contains(contactToString)) {
					Files.write(p, Collections.singletonList(contact.toString()), StandardOpenOption.APPEND);
//					existingNames.add(contact.getName());
				}
			}
		} catch (IOException e){
			e.printStackTrace();
		}
	}

	public static void deleteContact() {
		System.out.println("Enter the name of a contact you would like to delete: ");
		String deleteResponse = input.getString();
		contactList.removeIf(contact -> contact.getName().equalsIgnoreCase(deleteResponse));
		Path p = Paths.get("data/contacts.txt");
		String lineToRemove = "";
		try{
			ArrayList<String> existingNames = new ArrayList<>(Files.readAllLines(p));
			for(String line : existingNames) {
				String[] lineArray = line.split("  II  ", 2);
				String name = lineArray[0].toString();
				if(name.trim().equalsIgnoreCase(deleteResponse)) {
					lineToRemove = line;
				}
			}
			existingNames.remove(lineToRemove);
			Files.write(p, existingNames);
		} catch (ConcurrentModificationException | IOException e){
			e.printStackTrace();
		}
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
	}

	public static void readContactList() {

		Path p = Paths.get("data/contacts.txt");
		List<String> textList = new ArrayList<>();
		try {
			textList = Files.readAllLines(p);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(String entry : textList) {
			String[] initArray = entry.split("II", 2);
			long parsedNumber = Long.parseLong(initArray[1].trim());
			contactList.add(new Contact(initArray[0].trim(), parsedNumber));
		}

	}

public static void runApplication() {
	initialize();
		while (true){
			int menuResponse = displayMenu();
			if (menuResponse == 1) {
				displayContacts();
			} else if (menuResponse == 2) {
				addContact();
			} else if (menuResponse == 3) {
				searchContacts();
			} else if (menuResponse == 4) {
				deleteContact();
			} else if (menuResponse == 5) {
				System.out.println("Bye, have a good time.");
				break;
			}
		}
		exitFunction();
}

	public static void main (String[]args){
	runApplication();
	}
}