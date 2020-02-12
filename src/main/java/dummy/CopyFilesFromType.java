package dummy;


import java.awt.FlowLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class CopyFilesFromType
{
	static final String[] EXTENSIONS = new String[] { "gif", "png", "bmp", "JPG", "jpg", "bmp", "BMP", "gif", "GIF",
			"WBMP", "png", "PNG", "wbmp", "jpeg", "JPEG"
			// and other formats you need
	};
    public static void main(String[] args)
    {
    	File SourcePath =new File("C:\\Users\\HDMI\\Downloads\\list");
    	File DestPath= new File("C:\\Users\\HDMI\\Downloads\\tokens\\test");
    	
        new CopyFilesFromType().copy( SourcePath.toString(),        DestPath.toString());
    }

  

    private void copy(String fromPath, String outputPath)
    {
//        filter = new FileTypeOrFolderFilter(fileType);
        File currentFolder = new File(fromPath);
        File outputFolder = new File(outputPath);
        scanFolder(currentFolder, outputFolder);
    }

    private void scanFolder( File currentFolder, File outputFolder)
    {
        System.out.println("Scanning folder [" + currentFolder + "]...");
        File[] files  = currentFolder.listFiles(new FilenameFilter() {

			public boolean accept(File dir, String name) {
				List<String> images = Arrays.asList(ImageIO.getReaderFormatNames());
				String extension = "";
				int i = name.lastIndexOf('.');
				if (i > 0) {
					extension = name.substring(i + 1);
				}

				return images.contains(extension.toLowerCase());
			}
		});
        for (File file : files) {
            if (file.isDirectory()) {
                scanFolder( file, outputFolder);
            } else {
            	
            	
                copy(file, outputFolder);
            }
        }
    }

    private void copy(File file, File outputFolder)
    {
        try {
            System.out.println("\tCopying [" + file + "] to folder [" + outputFolder + "]...");
            
            final JFrame f = new JFrame("Sample");
            f.getContentPane().setLayout(new FlowLayout());
            InputStream input = new FileInputStream(file);
            OutputStream out = new FileOutputStream(new File(outputFolder + File.separator + file.getName()));
            byte data[] = new byte[input.available()];
            input.read(data);
            out.write(data);
            out.flush();
            out.close();
            input.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

	/*
	 * private final class FileTypeOrFolderFilter implements FileFilter { private
	 * final String fileType;
	 * 
	 * private FileTypeOrFolderFilter(String fileType) { this.fileType = fileType; }
	 * 
	 * public boolean accept(File pathname) { return pathname.getName().endsWith("."
	 * + fileType) || pathname.isDirectory(); } }
	 */}