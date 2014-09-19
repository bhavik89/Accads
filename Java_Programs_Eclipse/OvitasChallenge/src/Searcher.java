
import java.io.File;

public class Searcher {

    private String root;

    public Searcher(String root) {
        this.root = root;
    }

    public void search() {
        System.out.println(root);
        File folder = new File(root);
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            String path = file.getPath().replace('\\', '/');
            System.out.println(path);
            if (!path.contains(".")) {
                new Searcher(path + "/").search();
            }
        }
    }
}
