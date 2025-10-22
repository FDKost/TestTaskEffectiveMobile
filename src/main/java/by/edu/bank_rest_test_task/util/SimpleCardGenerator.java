package by.edu.bank_rest_test_task.util;

import lombok.experimental.UtilityClass;

import java.security.SecureRandom;

@UtilityClass
public class SimpleCardGenerator {
    private int sum;
    private final SecureRandom rnd = new SecureRandom();

    private int computeLuhnCheckDigit(String numberWithoutCheck) {
        String s = numberWithoutCheck + "0";
        calcs(s);
        return (10 - (sum % 10)) % 10;
    }

    private static void calcs(String s) {
        sum = 0;

        for (int i = s.length() - 1, posFromRight = 1; i >= 0; i--, posFromRight++) {
            int d = s.charAt(i) - '0';

            if (posFromRight % 2 == 0) {
                d *= 2;
                if (d > 9) d -= 9;
            }
            sum += d;
        }
    }

    public String generateCardNumber() {
        final int LENGTH = 16;
        StringBuilder sb = new StringBuilder(LENGTH);
        for (int i = 0; i < LENGTH - 1; i++) {
            sb.append(rnd.nextInt(10));
        }
        int check = computeLuhnCheckDigit(sb.toString());
        sb.append(check);
        return sb.toString();
    }
}
