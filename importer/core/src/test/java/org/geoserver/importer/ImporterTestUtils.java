package org.geoserver.importer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.geotools.factory.Hints;

public class ImporterTestUtils {

    public static void setComparisonTolerance() {
        //need to set hint which allows for lax projection lookups to match
        // random wkt to an epsg code
        Hints.putSystemDefault(Hints.COMPARISON_TOLERANCE, 1e-9);
    }

    public static File tmpDir() throws Exception {
        File dir = File.createTempFile("importer", "data", new File("target"));
        dir.delete();
        dir.mkdirs();
        return dir;
    }
    
    public static  File unpack(String path) throws Exception {
        return unpack(path, tmpDir());
    }
    
    public static  File unpack(String path, File dir) throws Exception {
        
        File file = file(path, dir);
        
        new VFSWorker().extractTo(file, dir);
        file.delete();
        
        return dir;
    }
    
    public static  File file(String path) throws Exception {
        return file(path, tmpDir());
    }
    
    public static  File file(String path, File dir) throws IOException {
        String filename = new File(path).getName();
        InputStream in = ImporterTestSupport.class.getResourceAsStream("test-data/" + path);
        
        File file = new File(dir, filename);
        
        FileOutputStream out = new FileOutputStream(file);
        IOUtils.copy(in, out);
        in.close();
        out.flush();
        out.close();
    
        return file;
    }

}
