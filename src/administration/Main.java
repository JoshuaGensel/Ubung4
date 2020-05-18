package administration;

public class Main {
    
    public static void main(String[] args) {
        Street street = new Street(5);
        int year = 2000;
        // street.buildHouse(3, new House(4, 1999));

        System.out.println("The street has 5 properties and every new house has 4 flats.\ntype 'exit' to exit.");
        while(true) {
            int emptyProperty = -1; // "houseNumber" of an empty property

            System.out.println(String.format("current year: %d", year));
            for(int houseNumber=0; houseNumber<5; houseNumber++) {
                House house = street.getHouses()[houseNumber];
                if(house != null) {
                    System.out.println(String.format("New occupied flats for house: %d? \n(current occ. flats: %d)", houseNumber, house.getOccupiedFlats()));
                    String input = System.console().readLine();
                    
                    // If user enters 'exit'
                    if(input.equals("exit")) {
                        System.exit(0);
                    }
                    
                    int occupiedFlats = Integer.parseInt(input);
                    street.getHouses()[houseNumber].setOccupiedFlats(occupiedFlats);
                } else {
                    emptyProperty = houseNumber;
                }
            }

            /**
             * Wrecks a house if its empty and 5 years or older.
             */
            for(int houseNumber=0; houseNumber<5; houseNumber++) {
                House house = street.getHouses()[houseNumber];
                if(house != null && house.checkWreck(year)) {
                    street.getHouses()[houseNumber] = null;
                    System.out.println(String.format("House: %d got wrecked! :(", emptyProperty));
                }
            }
            
            /**
             * 
             */
            boolean buildNewHouse = true;
            for(int houseNumber=0; houseNumber<5; houseNumber++) {
                House house = street.getHouses()[houseNumber];
                if(house != null && !house.checkFullHouse()) {
                    buildNewHouse = false;
                }
            }

            /**
             * Builds new House every year if theres an empty property and every house is full.
             */
            if(buildNewHouse) {
                if(emptyProperty == -1) {
                    System.out.println("there is no empty property");
                } else {
                    street.buildHouse(emptyProperty, new House(4, year));
                    System.out.println(String.format("Build house at: %d", emptyProperty));
                }
            }
            
            year++;
            
            if(year == 2021) {
                System.out.println("Humanity got erradicated by the so called Coronavirus. All flats are empty.");
                System.exit(0);
            }
        }
    }
}