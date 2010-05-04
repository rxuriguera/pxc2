package speerker;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileAppender {
	public static void main(String argv[]) throws InterruptedException, IOException {
		String inputPath = "/home/rxuriguera/enlistments/speerker/.temp/a.mp3";
		String outputPath = "/home/rxuriguera/enlistments/speerker/.temp/b.mp3";
		Boolean stop = false;

		FileInputStream infile = new FileInputStream(inputPath);
		BufferedInputStream bis = new BufferedInputStream(infile);

		Integer len =300000;
		FileOutputStream outfile = new FileOutputStream(outputPath, true);
		BufferedOutputStream buf = new BufferedOutputStream(outfile);
		byte[] filedata = null;

		while (stop == false) {
			filedata = new byte[len];
			bis.read(filedata);
			buf.write(filedata);
			System.out.println("File is "+len+" bytes bigger");
			Thread.sleep(1000);
		}

		bis.close();
		infile.close();

		buf.close();
		outfile.close();
	}
}