package dummy;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class CopyFileTask implements Runnable {

	private String fromPath;
	private final String toPath;

	public CopyFileTask(String fromPath, String toPath) {
		this.fromPath = fromPath;
		this.toPath = toPath;
	}

	@Override
	public void run() {
		try {
			Path to = Paths.get(toPath);
			Path from = Paths.get(fromPath);
			Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}