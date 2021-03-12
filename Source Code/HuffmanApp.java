import java.util.Arrays;
import java.util.Scanner;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Files;

public class HuffmanApp {
    public static void main(String[] args) {
        //These are the locations of each of the files or where they will be stored.
        String currentDirectory = System.getProperty("user.dir");
        String trainingName = currentDirectory+"/Data Sets/";
        String fileName = currentDirectory+"/Data Sets/";
        System.out.println("All files are from the directory Data Sets and do not include the file extension.");
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter the file name you would like to train the huffman from: ");
            trainingName = trainingName.concat(scanner.nextLine());
            System.out.println("Please enter the file name you would like : ");
            fileName = fileName.concat(scanner.nextLine());

            scanner.close();
        } catch (Exception e) {
            HuffmanCoding.readError(e);
            System.exit(1);
        }
        String compressedName = fileName+" compressed.BIN";
        String decompressedName = fileName+" decompressed.txt";
        trainingName = trainingName.concat(".txt");
        fileName = fileName.concat(".txt");


        long startTime = System.nanoTime();
        System.out.println("Reading file...");
        char[] chars = HuffmanCoding.readFile(fileName);
        String file = Arrays.toString(chars);

        //This is done to first create a map of frequences and characters.
        //Then it will use this map to create a binary tree.
        //Another file will have map created in the same way.
        //Finally, the file will be compressed using the binary tree and the new map and then written to a file.
        System.out.println("Training the huffman tree...");
        HuffmanCoding huffman = new HuffmanCoding(trainingName);
        BinaryTree binaryTree = new BinaryTree(huffman.getMap());
        System.out.println("Compressing the file...");
        HuffmanCoding huffmanCoding = new HuffmanCoding(fileName);
        byte[] compressedFile = binaryTree.compress(file, huffmanCoding.getMap());
        HuffmanCoding.writeFile(compressedName, compressedFile);

        //This is used to calculate the time taken to make huffman tree and compress the file.
        long endTime = System.nanoTime();
        long duration = (endTime - startTime)/1000000;
        System.out.println("Time taken to compress: "+duration+" ms");

        long startTimeDe = System.nanoTime();

        System.out.println("Decompressing the file...");
        HuffmanCoding.writeFile(decompressedName, binaryTree.decompress(compressedFile));

        long endTimeDe = System.nanoTime();
        long durationDe = (endTimeDe - startTimeDe)/1000000;
        System.out.println("Time taken to decompress: "+durationDe+" ms");


        //This will calculate the differences in file sizes.
        long difference = 0;
        double percentage = 0.0;
        try {
            difference = Files.size(Paths.get(fileName)) - Files.size(Paths.get(compressedName));
            percentage = ((double)difference / Files.size(Paths.get(compressedName)))*100;
        } catch (IOException e) {
            HuffmanCoding.readError(e);
        }
    
        //This will print out information about differences in sizes of the compressed and orginal file.
        System.out.println("The compressed file has saved: "+difference+" bytes");
        System.out.println("which is also: "+difference/1024+" kilobytes");
        System.out.println("There has been a "+percentage+"% reduction in file size");
    }
}
