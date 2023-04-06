public class Codec {

    private final static String ALPHABET = "abcdefghijklmnopqrstuvwxyz" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final static String KEY = "zyxwvutsrqponmlkjihgfedcba" + "ZYXWVUTSRQPONMLKJIHGFEDCBA";

    public static String Code(String message){
        String code_messaage = "";
        for (int i = 0; i < message.length(); i++) {
            int char_pos = ALPHABET.indexOf(message.charAt(i));
            if (char_pos == -1) {
                code_messaage += message.charAt(i);
            } else {
                char replaceChar = KEY.charAt(char_pos);
                code_messaage += replaceChar;
            }
        }
        return code_messaage; 
    }


    public static String Decode(String code_message){
        String decode_message = "";
        for (int i = 0; i < code_message.length(); i++) {
            int char_pos = KEY.indexOf(code_message.charAt(i));
            if (char_pos == -1) {
                decode_message += code_message.charAt(i);
            } else {
                char replaceChar = ALPHABET.charAt(char_pos);
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