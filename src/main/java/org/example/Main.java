package org.example;

import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {


        //1. Vytvořit Super rostoucí sekvenci
        int[] superGrowingSeq = generateSuperGrowingSequence(8);

        //2. Vygenerovat U a V a V^-1
        int u = generateU(superGrowingSeq);
        int v = generateV(u);
        int vInverse = pleaseGiveMeVInverse(v, u);

        //3. Předelat SRS do modulované sekvence(SGS[0] * v % u)
        int[] modulatedSequence = generateModulatedSequence(superGrowingSeq, v, u);

        //4. Vytvořit public klíč (Et) podle vstupu
        Scanner sc = new Scanner(System.in);
        System.out.println("Please put char input to code");
        char input = sc.nextLine().charAt(0);
        int publicKey = pleaseGiveMeET(input, modulatedSequence);

        //5. Rozkódovat private Klíčem
        char decodedInput = pleaseGiveMeDT(publicKey, vInverse, u, superGrowingSeq);
        System.out.println(decodedInput);

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

    public static int[] generateSuperGrowingSequence(int length) {
        Random random = new Random();
        int[] a = new int[length];
        int sum = 0;
        for (int i = 0; i < a.length; i++) {
            int rand = random.nextInt(10) + sum + 1;
            a[i] = rand;
            sum += rand;
        }
        return a;
    }

    public static void intReverse(int[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            int j = array[i];
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

    public static int pleaseGiveMeET(char input, int[] moduledSuperGrowingSeq) {
        String binary = Integer.toBinaryString(input);
        int sum = 0;
        String[] arr = binary.split("");
        stringReverse(arr);
        intReverse(moduledSuperGrowingSeq);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals("1")) sum += moduledSuperGrowingSeq[i];
        }
        return sum;
    }

    public static char pleaseGiveMeDT(int sum, int vMinus, int u, int[] superGrowingSeq) {
        StringBuilder sb = new StringBuilder();
        int Dt = sum * vMinus % u;
        intReverse(superGrowingSeq);
        for (int i = 0; i < superGrowingSeq.length; i++) {
            if (Dt - superGrowingSeq[i] >= 0) {
                sb.append("1");
                Dt -= superGrowingSeq[i];
            } else sb.append("0");


        }
        return (char) (Integer.parseInt(sb.reverse().toString(), 2));
    }

    public static int[] generateModulatedSequence(int[] superGrowingSeq, int V, int U) {
        int[] E = new int[superGrowingSeq.length];
        for (int i = 0; i < E.length; i++) {
            E[i] = superGrowingSeq[i] * V % U;
        }
        return E;
    }

    public static int generateU(int[] superGrowingSeq) {
        Random random = new Random();
        int sumOfSGS = Arrays.stream(superGrowingSeq).sum();
        int U = random.nextInt(50) + sumOfSGS + 1;
        while (!isNumberPrimary(U)) U = random.nextInt(50) + sumOfSGS + 1;
        return U;
    }

    public static int generateV(int U) {
        Random rand = new Random();
        int V = rand.nextInt((U - 1) - 1) + 1;
        while (!isNumberPrimary(V)) V = rand.nextInt((U - 1) - 1) + 1;
        return V;
    }

    public static int pleaseGiveMeVInverse(int V, int U) {
        for (int i = 1; i < U - 1; i++) {
            if (V * i % U == 1) return i;
        }
        return -1;
    }

    public static boolean isNumberPrimary(int number) {
        for (int i = 2; i < Math.sqrt(number); i++) {
            if (number % i == 0) return false;
        }
        return true;
    }
}