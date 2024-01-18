package com.mindhub.homebanking.utils;

public class Utils {
    public static String getRandomNumber() {
        StringBuilder cardNumber = new StringBuilder();
        for (int i = 0; i<4; i++){
            int seccion=(int) (Math.random()*9000+1000);
            cardNumber.append(seccion).append("-");
        }
        return cardNumber.substring(0,cardNumber.length()-1);
    }
    public static int getRandomNumberAccount(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static String getNumber(){
        String number;
        number= "VIN"+getRandomNumberAccount(00000001,99999999);
        return number;
    }
    public static int getCvv(){
        int number;
      number= (int) (Math.random() * 899+100);
      return number;
    }

}
