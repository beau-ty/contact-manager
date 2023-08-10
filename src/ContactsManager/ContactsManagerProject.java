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
		int userMenuResponse = input.getInt(1, 5);
		return userMenuResponse;


	}

	public static String searchContacts() {
		boolean continueResponse;
		do {
			System.out.println(contactList);
			System.out.println("Search for a contact name.");
			String searchResponse = input.getString();
			for (Contact contact : contactList) {
				if (contact.getName().equalsIgnoreCase(searchResponse)) {
					System.out.println(contact.getName() + "  ||  " + contact.getPhoneNumber());
					return contact.getName() + "  ||  " + contact.getPhoneNumber();
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

	}

	public static void displayContacts() {
		System.out.println("Name || Phone Number");
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

		Path p = Paths.get("data/contacts.txt");


		contactList.add(newContact);

		try{
			Set<String> existingNames = new HashSet<>(Files.readAllLines(p));
				if(!existingNames.contains(newContact.getName())){
					Files.write(p, Collections.singletonList(newContact.getName() + "  ||  " + newContact.getPhoneNumber()), StandardOpenOption.APPEND);
					existingNames.add(newContact.getName());
				}
		} catch (IOException e){
			e.printStackTrace();
		}
	}

	public static void deleteContact() {


//		try {
//            Files.delete(Paths.get());
//        } catch (IOException e){
//            e.printStackTrace();
//        }
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
		displayContacts();
		displayMenu();
//		addContact();
//		addContact();
		searchContacts();



	}

	public static void readContactList() {

		Path p = Paths.get("data/contacts.txt");
		List<String> textList = new ArrayList<>();
		try {
			textList = Files.readAllLines(p);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(textList);

	}



	public static void main (String[]args){
		initialize();


	}

}
