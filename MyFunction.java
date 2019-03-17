/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sit.int303.demo.function;

/**
 *
 * @author INT303
 */
public class MyFunction {
    private static final String MODEL_IMG_PATH = "model-images" ;
    public static int addMethod(String x, String y) {
        try {
            return Integer.valueOf(x) + Integer.valueOf(y) ;
        } catch (Exception e) {
            return Integer.MIN_VALUE ;
        }
    }
    
    public static String getImageFileName(String productLine, String productCode) {
        String imgPath = productLine.split(" ")[0] ;
        return String.format("%s/%s/%s.jpg", MODEL_IMG_PATH, imgPath.toLowerCase(), productCode );
    }
    
    public static void main(String[] args) {
        System.out.println(getImageFileName("classic car", "S_10119"));
    }
}
