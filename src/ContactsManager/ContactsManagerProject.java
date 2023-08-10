package ContactsManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ContactsManagerProject {
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
		createFile();
		initialize();


	}

}
