package ru.stas.microservices.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class IdNumberUtils {

    public static boolean isValid(final String idNumber) {
        boolean isValid = false;

        if (idNumber.length() == 11) {

            final long tcNo = Long.parseLong(idNumber);

            long aTcNo = tcNo / 100;
            final long bTcNo = tcNo / 100;
            final long n1 = aTcNo % 10;
            aTcNo = aTcNo / 10;
            final long n2 = aTcNo % 10;
            aTcNo = aTcNo / 10;
            final long n3 = aTcNo % 10;
            aTcNo = aTcNo / 10;
            final long n4 = aTcNo % 10;
            aTcNo = aTcNo / 10;
            final long n5 = aTcNo % 10;
            aTcNo = aTcNo / 10;
            final long n6 = aTcNo % 10;
            aTcNo = aTcNo / 10;
            final long n7 = aTcNo % 10;
            aTcNo = aTcNo / 10;
            final long n8 = aTcNo % 10;
            aTcNo = aTcNo / 10;
            final long n9 = aTcNo % 10;

            final long sumOdd = n1 + n3 + n5 + n7 + n9;
            final long sumEven = n2 + n4 + n6 + n8;

            final long control1 = (10 - (sumOdd * 3 + sumEven) % 10) % 10;
            final long control2 = (10 - ((sumEven + control1) * 3 + sumOdd) % 10) % 10;

            isValid = bTcNo * 100 + control1 * 10 + control2 == tcNo;
        }

        return isValid;
    }
}
