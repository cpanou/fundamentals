package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	    // this is an in-line comment
        /*
            this
            is
            a
            multi-line
            comment
        */

        Person kwstas = new Person(30, "Kwstas", "Pappas",true);
        kwstas.speak();

        Person george = new Person(35);
        george.setFirstName("George");
        george.setLastName( "Pappadopoulos");
        george.setAlive(true);
        george.speak();

        Person manwlis = new Person();
        manwlis.setAge(50);
        manwlis.setFirstName("Manwlis");
        manwlis.setLastName("Tziolis");
        manwlis.setAlive(false);
        manwlis.speak();

        String name = kwstas.getFirstName();
        manwlis.wave(name);

        System.out.println( "Hens " + ( 25 + 30 / 6 ) );
        System.out.println( "Roosters " + ( 100 - 25 * 3 % 4 ) );
        System.out.println( 3 + 2 + 1 - 5 + 4 % 2 - 1 / 4 + 6 );
        System.out.println( "Is it true that 3 + 2 < 5 - 7?" );
        System.out.println( 3 + 2 < (5 - 7) );
        System.out.println( "What is 3 + 2? " + ( 3 + 2 ) );
        System.out.println( "What is 5 - 7? " + ( 5 - 7 ) );
        System.out.println( "Is it greater? " + ( 5 > -2 ) );
        System.out.println( "Is it greater or equal? " + ( 5 >= -2 ) );
        System.out.println( "Is it less or equal? " + ( 5 <= -2 ) );

        System.out.println("Remember Operator precedence: " + ((3 + 2 < (5 - 7) || 3 + 2 == (5 - 7)) && (3 + 2 == 5)));

        int combinedAge = kwstas.getAge() + george.getAge() + manwlis.getAge();
        boolean alive = kwstas.isAlive();
    }

}
