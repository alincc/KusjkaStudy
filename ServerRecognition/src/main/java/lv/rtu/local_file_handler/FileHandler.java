package lv.rtu.local_file_handler;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class FileHandler {

	private static Logger log = Logger.getLogger(FileHandler.class.getName());

	static private File file;

	public synchronized static void createFile(String filePath) throws IOException {
		Path path = Paths.get(filePath);
		file = new File(filePath);
		Files.createDirectories(path.getParent());

		try {
			Files.createFile(path);
		} catch (FileAlreadyExistsException e) {
			System.err.println("already exists: " + e.getMessage());
			log.info("already exists: " + e.getMessage());
		}
	}

	public synchronized static void deleteFile(String filePath) {
		try {
			File file = new File(filePath);

			if (file.delete()) {
				log.info(file.getName() + " is deleted!");
			} else {
				log.info("Delete operation is failed.");
			}

		} catch (Exception e) {

			e.printStackTrace();

		}
	}
}
