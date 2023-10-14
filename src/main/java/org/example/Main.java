package org.example;

import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Kdo jsi?");
        System.out.println("1. Alice");
        System.out.println("2. Bob");
        int choice = Integer.parseInt(sc.nextLine());

        //1. Vytvořit Super rostoucí sekvenci
        long[] superGrowingSeq = generateSuperGrowingSequence(16);

        //2. Vygenerovat U a V a V^-1
        long u = generateU(superGrowingSeq);
        long v = generateV(u);
        long vInverse = pleaseGiveMeVInverse(v, u);

        //3. Předelat SRS do modulované sekvence(SGS[0] * v % u)
        long[] modulatedSequence = generateModulatedSequence(superGrowingSeq, v, u);
        if (choice == 1) {
            sout(modulatedSequence);
        } else {
            System.out.println("Zadej public klíč");
            String input = sc.nextLine();
            String[] numbers = input.substring(1, input.length() - 1).split(", ");
            long[] et = new long[numbers.length];
            for (int i = 0; i < numbers.length; i++) {
                et[i] = Long.parseLong(numbers[i]);
            }
            System.out.println("Zadej zpravu");
            String message = sc.nextLine();
            System.out.println(pleaseGiveMeET(message, et));

            System.out.println("Ahoj alice, zadej zasifrovanou zpravu");
            String codedMessage = sc.nextLine();
            System.out.println(pleaseGiveMeDT(Long.parseLong(codedMessage), vInverse, u, superGrowingSeq));
        }


        /*
        //1. Vytvořit Super rostoucí sekvenci
        long[] superGrowingSeq = generateSuperGrowingSequence(16);

        //2. Vygenerovat U a V a V^-1
        long u = generateU(superGrowingSeq);
        long v = generateV(u);
        long vInverse = pleaseGiveMeVInverse(v, u);

        //3. Předelat SRS do modulované sekvence(SGS[0] * v % u)
        long[] modulatedSequence = generateModulatedSequence(superGrowingSeq, v, u);

        //4. Vytvořit public klíč (Et) podle vstupu
        Scanner sc = new Scanner(System.in);
        System.out.println("Please put char input to code");
        String input = sc.nextLine();
        long publicKey = pleaseGiveMeET(input, modulatedSequence);

        //5. Rozkódovat private Klíčem
        String decodedInput = pleaseGiveMeDT(publicKey, vInverse, u, superGrowingSeq);
        System.out.println(decodedInput);
        */

        /*
        int[] superGrowingSeq = new int[]{4, 14, 19, 39, 79, 161, 325, 643};
        int[] superGrowingSeq2FromSchool = new int[]{1, 2, 4, 8, 17, 33, 66, 144};
        int u = 313;
        int v = 47;
        //int vMinus = pleaseGiveMeVInverse(v, u);
        System.out.println(u);
        System.out.println(isNumberPrimary(u));
        System.out.println(v);
        System.out.println(isNumberPrimary(v));
        System.out.println();
        System.out.println();
        for (int i : generateE(superGrowingSeq, v, u)) {
            System.out.println(i);
        }
        char o = 'u';
        System.out.println((int) o);
        String a = Integer.toBinaryString(o);
        System.out.println(a);
        int et = pleaseGiveMeET(o, moduledSeq);
        System.out.println(pleaseGiveMeDT(et, vMinus, u, superGrowingSeq2FromSchool));
         */
    }

    private static void sout(long[] arr) {
        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) System.out.print(", ");
        }
        System.out.print("]");
        System.out.println();
    }

    public static long[] generateSuperGrowingSequence(int length) {
        Random random = new Random();
        long[] a = new long[length];
        long sum = 0;
        for (int i = 0; i < a.length; i++) {
            long rand = random.nextInt(10) + sum + 1;
            a[i] = rand;
            sum += rand;
        }
        return a;
    }

    public static void intReverse(long[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            long j = array[i];
            array[i] = array[array.length - i - 1];
            array[array.length - i - 1] = j;
        }
    }

    public static void stringReverse(String[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            String j = array[i];
            array[i] = array[array.length - i - 1];
            array[array.length - i - 1] = j;
        }
    }

    public static long pleaseGiveMeET(String input, long[] moduledSuperGrowingSeq) {
        String binary = getBinaryFromInput(input);
        long sum = 0;
        String[] arr = binary.split("");
        stringReverse(arr);
        intReverse(moduledSuperGrowingSeq);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals("1")) sum += moduledSuperGrowingSeq[i];
        }
        return sum;
    }

    public static String getBinaryFromInput(String input) {
        char input1 = input.charAt(0);
        char input2 = input.charAt(1);
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        StringBuilder sb3 = new StringBuilder();
        String binary1 = Integer.toBinaryString(input1);
        String binary2 = Integer.toBinaryString(input2);
        sb1.append(binary1).reverse();
        sb2.append(binary2).reverse();
        while (sb1.length() <= 7) sb1.append("0");
        while (sb2.length() <= 7) sb2.append("0");
        return sb3.append(sb1.reverse()).append(sb2.reverse()).toString();
    }

    public static String pleaseGiveMeDT(long sum, long vMinus, long u, long[] superGrowingSeq) {
        StringBuilder sb = new StringBuilder();
        long Dt = sum * vMinus % u;
        intReverse(superGrowingSeq);
        for (int i = 0; i < superGrowingSeq.length; i++) {
            if (Dt - superGrowingSeq[i] >= 0) {
                sb.append("1");
                Dt -= superGrowingSeq[i];
            } else sb.append("0");


        }
        char first = (char) (Integer.parseInt(sb.reverse().substring(0, 8), 2));
        char second = (char) (Integer.parseInt(sb.substring(8), 2));
        sb.setLength(0);
        sb.append(first).append(second);
        return sb.toString();
    }

    public static long[] generateModulatedSequence(long[] superGrowingSeq, long V, long U) {
        long[] E = new long[superGrowingSeq.length];
        for (int i = 0; i < E.length; i++) {
            E[i] = superGrowingSeq[i] * V % U;
        }
        return E;
    }


    public static long generateU(long[] superGrowingSeq) {
        Random random = new Random();
        long sumOfSGS = Arrays.stream(superGrowingSeq).sum();
        long U = random.nextInt(50) + sumOfSGS + 1;
        while (!isNumberPrimary(U)) U = random.nextInt(50) + sumOfSGS + 1;
        return U;
    }

    public static long generateV(long U) {
        Random rand = new Random();
        long V = rand.nextLong((U - 1) - 1) + 1;
        while (!isNumberPrimary(V)) V = rand.nextLong((U - 1) - 1) + 1;
        return V;
    }

    public static long pleaseGiveMeVInverse(long V, long U) {
        for (int i = 1; i < U - 1; i++) {
            if (V * i % U == 1) return i;
        }
        return -1;
    }

    public static boolean isNumberPrimary(long number) {
        for (int i = 2; i < Math.sqrt(number); i++) {
            if (number % i == 0) return false;
        }
        return true;
    }
}