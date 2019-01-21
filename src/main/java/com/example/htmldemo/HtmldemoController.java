package com.example.htmldemo;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
public class HtmldemoController {

    @RequestMapping("/greeting")
    public String index(@RequestParam(name="name", required=false, defaultValue="World") String name) {
        return "Hello " + name;
    }
    
    @RequestMapping("/downloadtest")
    public ResponseEntity<InputStreamResource> downloadFile1() throws IOException {
    	//Create temp text file
    	File file = File.createTempFile("file",".txt");
    	file.deleteOnExit();
    	String filepath = file.getAbsolutePath();
    	System.out.println(filepath);
    	
    	String mystr = "Hello World!";
    	BufferedWriter writer = new BufferedWriter(new FileWriter(file));
    	writer.write(mystr);
    	writer.flush();
    	writer.close();
    	
    	HttpHeaders headers = new HttpHeaders();
    	headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
    	headers.add("Pragma", "no-cache");
    	headers.add("Expires", "0");
    	headers.add("Content-Disposition", "attachment; filename=\"file.txt\"");
    	
    	return ResponseEntity
    			.ok()
    			.headers(headers)
    			.contentLength(file.length())
    			.contentType(MediaType.parseMediaType(MediaType.APPLICATION_OCTET_STREAM_VALUE))
    			.body(new InputStreamResource(new FileInputStream(file)));
    }

    @RequestMapping("/downloadtest2")
    public ResponseEntity<InputStreamResource> downloadFile2() throws IOException {
    	//Create temp text file
    	File file = File.createTempFile("file",".txt");
    	file.deleteOnExit();
    	String filepath = file.getAbsolutePath();
    	System.out.println(filepath);
    	
    	String mystr = "Hello World!";
    	BufferedWriter writer = new BufferedWriter(new FileWriter(file));
    	writer.write(mystr);
    	writer.flush();
    	writer.close();
    	
    	File zip = File.createTempFile("myzip", ".zip");
    	zip.deleteOnExit();
    	ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zip));
    	out.putNextEntry(new ZipEntry(file.getName()));
    	FileInputStream fis = new FileInputStream(file);
    	IOUtils.copy(fis, out);
    	fis.close();
    	out.closeEntry();
    	out.flush();
    	out.close();
    	
    	HttpHeaders headers = new HttpHeaders();
    	headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
    	headers.add("Pragma", "no-cache");
    	headers.add("Expires", "0");
    	headers.add("Content-Disposition", "attachment; filename=\"myzip.zip\"");
    	
    	return ResponseEntity
    			.ok()
    			.headers(headers)
    			.contentLength(zip.length())
    			.contentType(MediaType.parseMediaType(MediaType.APPLICATION_OCTET_STREAM_VALUE))
    			.body(new InputStreamResource(new FileInputStream(zip)));
    	
    }
    
    @RequestMapping("/downloadtest3")
    public ResponseEntity<InputStreamResource> downloadtest3() throws IOException {
    	//Create temp text file
    	File file = File.createTempFile("file",".txt");
    	file.deleteOnExit();
    	String filepath = file.getAbsolutePath();
    	System.out.println(filepath);
    	
    	String mystr = "Hello World!";
    	BufferedWriter writer = new BufferedWriter(new FileWriter(file));
    	writer.write(mystr);
    	writer.flush();
    	writer.close();
    	
    	File file2 = File.createTempFile("fileb", ".txt");
    	file2.deleteOnExit();
    	String filepath2 = file2.getAbsolutePath();
    	System.out.println(filepath2);
    	
    	String mystr2 = "Hello World from 2nd file!";
    	writer = new BufferedWriter(new FileWriter(file2));
    	writer.write(mystr2);
    	writer.flush();
    	writer.close();
    	
    	Path tempdir = Files.createTempDirectory("mydir");
    	System.out.println("Tempdir Path: " + tempdir.toString());
//    	System.out.println("Rename 1 Success: " + file.renameTo(new File(tempdir.toString() + "\\" + file.getName())));
//    	System.out.println("Rename 1 path: " + file.getPath());
    	
    	Path temp = Files.move(file.toPath(), Paths.get(tempdir.toString() + "\\" + file.getName()));
    	System.out.println("move1 path: " + temp.toString());
    	
//    	System.out.println("Rename 2 Success: " + file2.renameTo(new File(tempdir.toString() + "\\level\\" + file.getName())));
//    	System.out.println("Rename 2 path: " + file2.getPath());
    	
    	temp = Files.move(file2.toPath(), Paths.get(tempdir.toString() + "\\" + file2.getName()));
    	System.out.println("move2 path: " + temp.toString());
    	
    	File dir = tempdir.toFile();
    	File[] files = dir.listFiles();
    	for(File f : files)
    		System.out.println(f.getAbsolutePath());
    	
    	File zip = File.createTempFile("myzip", ".zip");
    	zip.deleteOnExit();
    	ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zip));
    	
    	for(File f : files)
    	{
    		FileInputStream fis = new FileInputStream(f);
    		out.putNextEntry(new ZipEntry(f.getName()));
    		IOUtils.copy(fis, out);
    		fis.close();
    		out.closeEntry();
    	}
    	
    	out.close();
    	
    	HttpHeaders headers = new HttpHeaders();
    	headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
    	headers.add("Pragma", "no-cache");
    	headers.add("Expires", "0");
    	headers.add("Content-Disposition", "attachment; filename=\"myzip.zip\"");
    	
    	return ResponseEntity
    			.ok()
    			.headers(headers)
    			.contentLength(zip.length())
    			.contentType(MediaType.parseMediaType(MediaType.APPLICATION_OCTET_STREAM_VALUE))
    			.body(new InputStreamResource(new FileInputStream(zip)));
    }
}