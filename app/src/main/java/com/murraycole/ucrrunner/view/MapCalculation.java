package com.murraycole.ucrrunner.view;

/**
 * Created by mbrevard on 11/10/14.
 */
public class MapCalculation {

    public double calculateCalories(MapInformation mapInfo, double T) {
        //seconds -> hours?
        User u = new User(); //for milestone 0 fixing a case user

        double avgSPeed = mapInfo.getAverageSpeed() * 3.6;
        double Wkg = 165 * 0.453592; // lbs -> kg
        double Height = 70 * 2.54; //feet, inches -> cm
        double age = 22;
        double BMR = 0;
        double MET = 0;
        double calorie_burn = 0;

        T = T * 2.77778e-7;



        if(true){
            BMR = (13.75 * Wkg) + (5 * Height) - (6.76 * age) + 66;
        }else {
            BMR = (9.56 * Wkg) + (1.85 * Height) - (4.68 * age) + 655;

        }

        if(avgSPeed < 24.1402)
        {
            MET = 3.80;
        }else{
            MET = 7.50;
        }

        calorie_burn = (BMR / 24) * MET * T;

        System.out.println("Calorie: " + calorie_burn);
        return calorie_burn;
    }
}
