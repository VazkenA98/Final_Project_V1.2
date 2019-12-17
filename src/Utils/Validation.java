package Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    public static boolean checkPasswordValidation(String password){
        boolean isValid = false;
        if(password.length()<8){
            isValid = false;
        }else{
            isValid = true;
        }
        return isValid;
    }

    public static boolean checkCustemIDValidation(String custemID){
        boolean isValid = false;
        String str = custemID.substring(1);
        if(str.length()<4){
            isValid = false;
        }else{
            isValid = true;
        }

        return isValid;
    }
    public static boolean checkCustemIdIsOnlyNumbers(String custemID){
        //gestuke yete  digite;
        boolean isValid = true;
        String str = custemID.substring(1);
        for(int i=0;i<str.length()-1;i++) {
        boolean b =Pattern.matches("\\d", str.substring(i,i+1));
        if(b==true){
            continue;
        }else{
            isValid = false;
            i = custemID.length()-1;
        }
        }
       return isValid;
    }
    public static boolean checkUserUniversityEmail(String email){
        boolean isValid = false;
        String str = email.toLowerCase();
        if(!str.substring(str.length()-"@npua.com".length()).equals("@npua.com")){
             isValid = false;
        }else if(str.length()<9){
            isValid = false;
        }else if(str.indexOf(' ')>=0){
            isValid = false;
        }else{
            isValid = true;
        }
        return isValid;
    }
    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
}
