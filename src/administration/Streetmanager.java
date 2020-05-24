package administration;

import java.util.ArrayList;

public class Streetmanager{

    private ArrayList<Street> streetList = new ArrayList<Street>();

    int year = 2000;
    int properties;
    


    public void setup(){
        System.out.println("How many Poperties should the Street have? \ntype 'exit' to exit.");
        String props = System.console().readLine();
        this.properties = Integer.parseInt(props);
        Street street = new Street(properties);
        streetList.add(street);
        while(true){
            System.out.println("Would you like to build another street?('yes' or 'no')");
            String answer = System.console().readLine();
            if(answer.equals("yes")){
                System.out.println("How many Poperties should the Street have? \ntype 'exit' to exit.");
                String props2 = System.console().readLine();
                this.properties = Integer.parseInt(props2);
                Street street2 = new Street(properties);
                streetList.add(street2);
            }
            else{
                break;
            }
        }
    }

    public void loop(Street street){

        int emptyProperty = -1; // "houseNumber" of an empty property

        System.out.println(String.format("current year: %d", year));
        for(int houseNumber=0; houseNumber<street.getStreetSize(); houseNumber++) {
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
        for(int houseNumber=0; houseNumber<street.getStreetSize(); houseNumber++) {
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
        for(int houseNumber=0; houseNumber<street.getStreetSize(); houseNumber++) {
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

    public void start(){
        setup();
        for(int i = 0; i < streetList.size(); i++){
            while(true){
                loop(streetList.get(i));
            }
        }
    }    
}