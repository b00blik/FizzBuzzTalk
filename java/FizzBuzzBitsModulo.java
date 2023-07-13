public class FizzBuzzBitsModulo{
    
    public static void main(String[] args){
        
        long startTime = System.currentTimeMillis();

        int max = 100_000_000;
        String[] result = new String[max+1];
        
        for (int i=1; i<=max; i++) {
            if (modulo3(i)) {
                if (modulo5(i)) {
                    result[i] = "FizzBuzz";
                } else {
                    result[i] = "Fizz";
                }
            } else if (modulo5(i)) {
                result[i] = "Buzz";
            } else {
                result[i] = String.valueOf(i);
            }
        }

        long endTime = System.currentTimeMillis();

        System.out.println("Time in millis: " + (endTime - startTime));
    }

    
    public static boolean modulo3(int x) {
        int divider = 3;
        int diff = x;

        while (diff > 3) {

            while (divider < x) {
                divider = divider << 1;
            }
            divider = divider >> 1;

            diff = x - divider;
            x = diff;

            divider = 3;
        }

        return diff == 3;
    }

    public static boolean modulo5(int x) {
        int divider = 5;
        int diff = x;

        while (diff > 5) {

            while (divider < x) {
                divider = divider << 1;
            }
            divider = divider >> 1;

            diff = x - divider;
            x = diff;

            divider = 5;
        }

        return diff == 5;
    }

}