[10:57 pm, 24/11/2021] Ahmed Adel: package pkg1st.sprint;

import java.util.ArrayList;
import java.util.Scanner;

///////////////////////////////********User class ********//////////////////

abstract class User {
    String userName, mobNumber, Email, password;
    //accountState
    boolean stateSuspend;
    Register r;

    public void setUserName(String Name) {
        userName = Name;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public void setMobNumber(String Number) {
        mobNumber = Number;
    }

    public void setPassword(String Passowrd) {
        password = Passowrd;
    }

    public void setState(boolean state) {
        stateSuspend = state;
    }

    public String getUserName() {
        return userName;
    }

    public boolean getState() {
        return stateSuspend;
    }

    public String getEmail() {
        return Email;
    }

    public String getMobNumber() {
        return mobNumber;
    }

    public String getPassword() {
        return password;
    }

        //why login abstract not function
    abstract public String login(String userName, String PassWord);

    abstract public boolean register();

}
///////////////////////////////********End User class ********//////////////////



////////////////////////////////////****Register interface********///////////////////
interface Register {
    public boolean register();
}



///////////////////////////////********passenger class ********//////////////////
class Passenger extends User {
    String source, distnation;
    Admin admin;
    ArrayList<Integer> prices = new ArrayList<>();

    public Passenger(String userName, String mobNumber, String Email, String password) {
        this.Email = Email;
        this.mobNumber = mobNumber;
        this.password = password;
        this.userName = userName;

    }

    public int showRate(Driver d) {
        return d.getAverageRating();
    }

    @Override
    public String login(String userName, String PassWord) {
        if (stateSuspend == true && this.userName == userName && this.password == PassWord)
            return "login success";
        else
            return "your Faild";
    }

    @Override
    public boolean register() {
        r = new RegisterUser();
        return r.register();
    }

    public void request(String source, String distination) {
        this.source = source;
        this.distnation = distination;
        System.out.println("your request is valid");
    }

    public void rate(Driver d) {
        int rating;
        while (true) {
            System.out.println("plz enter your rating from 1 to 5 (at least1 , at most 5)");
            Scanner input = new Scanner(System.in);
            rating = input.nextInt();
            if (rating > 5 || rating < 1) {
                System.out.println("enter the rating correct");
                continue;
            } else {
                d.rating.add(rating);
                break;

            }

        }

    }

    public void getoffers() {
        if (prices.isEmpty()) {
            System.err.println("there is no offers yet");
        }

        else
            System.out.println("your suggestion offers from driver is " + prices);
    }
}
///////////////////////////////********End User class (passenger) ********//////////////////


/////////////////////////////////////******Register user (passenger)********//////////////////
class RegisterPassenger implements Register {
    Passenger passenger;

    @Override
    public boolean register() {
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.println("Enter UserName - MobileNumber - Password -Email(optinal)");
            String Name = input.nextLine();
            String Mobile = input.nextLine();// assume mobile number contain char
            String Password = input.nextLine();
            String Email = input.nextLine();

            if (Name != null && Password != null & Mobile != null) {
                passenger.setMobNumber(Mobile);
                passenger.setPassword(Password);
                passenger.setUserName(Name);
                if (Email != null)
                    passenger.setEmail(Email);
                System.out.println("Successful  Registration");
                input.close();

                return true;

            }

            System.out.println("Faild Registration");
            input.close();
            return false;

        }
    }

}

/////////////////////////////////////***** End Register user (passenger)*******//////////////////


/////////////////////////////////////***** class Driver*********//////////////////

class Driver extends User {
    //additional attribute for driver
    String licence, nationalId;

    ArrayList<String> favAreas = new ArrayList<>();
    ArrayList<Integer> rating = new ArrayList<>();
    ArrayList<String> rides = new ArrayList<>();
    //why
    Passenger passenger;

    public Driver(String licence, String nId, String userName, String mobNumber, String Email, String password,
            Passenger passenger) {
        this.Email = Email;
        this.mobNumber = mobNumber;
        this.password = password;
        this.userName = userName;
        this.licence = licence;
        this.nationalId = nId;
        this.passenger = passenger;

    }

    public int getAverageRating() {
        int temp = 0;
        for (int i = 0; i < rating.size(); i++)
            temp = temp+rating.get(i);
        return temp / rating.size();
    }

    public void setLicense(String License) {
        licence = License;
    }

    public void setId(String id) {
        nationalId = id;
    }

    public String getLicense() {
        return licence;
    }

    public String getId() {
        return nationalId;
    }

    @Override
    public String login(String userName, String PassWord) {
        if (stateSuspend == true & this.userName == userName & this.password == PassWord)
            return "login success";
        else
            return "your Faild";
    }

    @Override
    public boolean register() {
        r = new RegisterDriver();
        return r.register();
    }

    public void getrate() {

        System.out.println(rating);
    }

    public void Fav() {
        String place;

        while (true) {
            System.out
                    .println("if you have a favourite area you would like to add plz enter it and you havent enter no");
            Scanner input = new Scanner(System.in);
            place = input.nextLine();
            if ("no".equals(place))
                break;
            favAreas.add(place);
        }

    }

    public void ntfy() {
        boolean t = false;
        if (favAreas.isEmpty()) {
            System.out.println("you haven't a favourite list to get a notification ");
        }

        else {
            for (int i = 0; i < favAreas.size(); i++) {
                if (passenger.source.equals(favAreas.get(i))) {
                    rides.add(passenger.source);
                    System.out.println("you have a new notification ");
                    t = true;
                }
            }
            if (t == false) {
                System.out.println("you haven't a notification yet");
            }
        }
    }

    public int offers() {
        int price = 0;
        for (int i = 0; i < rides.size(); i++) {
            if (passenger.source.equals(rides.get(i))) {
                System.out.println("enter the price for " + passenger.source + " and destination " + passenger.distnation);
                Scanner input = new Scanner(System.in);
                price = input.nextInt();
                passenger.prices.add(price);

            }
        }
        return price;
    }

}
/////////////////////////////////////***** End class Driver*********//////////////////

/////////////////////////////////////***** class RegisterDriver*********//////////////////
class RegisterDriver implements Register {
    Driver driver;

    @Override
    public boolean register() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter UserName - MobileNumber - Password -Email(optinal) -License - NationalId");
        String Name = input.nextLine();
        String Mobile = input.nextLine();// assume mobile number contain char
        String Password = input.nextLine();
        String Email = input.nextLine();
        String license = input.nextLine();
        String id = input.nextLine();

        if (Name != null && Password != null & Mobile != null & id != null & license != null) {
            driver.setMobNumber(Mobile);
            driver.setPassword(Password);
            driver.setUserName(Name);
            driver.setId(id);
            driver.setLicense(license);

            if (Email != null)
                driver.setEmail(Email);
            System.out.println("Successful  Registration");
            input.close();

            return true;
        }
        System.out.println("Faild Registration");
        input.close();
        return false;

    }
}
/////////////////////////////////////******End class RegisterDriver********//////////////////

/////////////////////////////////////******Admin class********//////////////////

class Admin {
    // public boolean verify(viewer v) {
    //     v.setState(true);
    //     return true;
    // }
      public boolean verify(Driver d) {
        v.setVerified(true);
        return true;
    }

    public boolean suspend(User v) {
        v.setState(false);
        return false;
    }

    // public boolean susped() {
    //     boolean verify = true;

    //     return verify;
    // }
    public boolean unsusped() {
    v.setState(true);
        return true;
    }
}
/////////////////////////////////////***** End of Admin class *******//////////////////

public class Sprint {

    public static void main(String[] args) {
        Passenger ahmed = new Passenger("ahmed adel", "01102531105", "ahmed@yahoo.com", "123456");
        ahmed.request("giza", "mokatam");

        Driver adel = new Driver("1's degree licence", "1122554477", "adel", "0111002255331", "adel111@yahoo.com",
                "123555669", ahmed);
        adel.Fav();
        adel.ntfy();

        adel.offers();

        ahmed.getoffers();

        ahmed.rate(adel);
        ahmed.rate(adel);
        ahmed.rate(adel);
        ahmed.rate(adel);
        System.out.println(ahmed.showRate(adel));
        adel.getrate();
    }

}
