package src;
public class Codec {

    //El algoritmo mapea cada letra del mensaje con una letra clave
    //para revertirlo se ocupa el mismo procedimiento pero de forma inversa
    //es decir, cada letra clave por una letra del abecedario normal


    private final static String ALPHABET = "abcdefghijklmnopqrstuvwxyz" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final static String KEY = "zyxwvutsrqponmlkjihgfedcba" + "ZYXWVUTSRQPONMLKJIHGFEDCBA";

    
    public static String Code(String message){
        String code_messaage = "";
        for (int i = 0; i < message.length(); i++) {
            int char_index = ALPHABET.indexOf(message.charAt(i));
            if (char_index == -1) {
                code_messaage += message.charAt(i);
            } else {
                char replaceChar = KEY.charAt(char_index);
                code_messaage += replaceChar;
            }
        }
        return code_messaage; 
    }


    public static String Decode(String code_message){
        String decode_message = "";
        for (int i = 0; i < code_message.length(); i++) {
            int char_index = KEY.indexOf(code_message.charAt(i));
            if (char_index == -1) {
                decode_message += code_message.charAt(i);
            } else {
                char replaceChar = ALPHABET.charAt(char_index);
                decode_message += replaceChar;
            }
        }
        return decode_message; 
    }
    
    public static void main(String[] args) {
            String test = " Hola este es un mensaje!!";
            System.out.println( test );
            System.out.println( Code(test)  );
            System.out.println( Decode( Code(test) ) );
    }



}