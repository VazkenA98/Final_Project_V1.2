package Utils;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;

public class Tools {

    public static String convertBlobToString(Blob b) throws SQLException {
        String imgDataBase64 = null;
        if(b==null){
            imgDataBase64 = null;
        }else {
            Blob image = b;
            byte[] imgData = null;
            imgData = image.getBytes(1, (int) image.length());
             imgDataBase64 = new String(Base64.getEncoder().encode(imgData));
        }
        return imgDataBase64;
    }
}
