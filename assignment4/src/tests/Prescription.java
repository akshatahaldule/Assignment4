package tests;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Prescription {
    private int prescID;
    private String firstName;
    private String lastName;
    private String address;
    private float sphere;
    private float axis;
    private float cylinder;
    private Date examinationDate;
    private String optometrist;
    private String[] remarkTypes = {"Client", "Optometrist"};
    private ArrayList<String> postRemarks = new ArrayList<>();

    // Constructor to initialize Prescription object
    public Prescription(int prescID, String firstName, String lastName, String address, float sphere, float axis, float cylinder, Date examinationDate, String optometrist) {
        this.prescID = prescID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.sphere = sphere;
        this.axis = axis;
        this.cylinder = cylinder;
        this.examinationDate = examinationDate;
        this.optometrist = optometrist;
    }

    
    // This method checks if the prescription details (names, address, sphere, cylinder, axis, optometrist, and date) are valid. 
    // If any validation fails, it returns false. 
    // If valid, it saves the prescription to presc.txt and returns true.
    public boolean addPrescription() {
        // Validate First and Last Name
        if (firstName == null || firstName.length() < 4 || firstName.length() > 15) {
            return false;
        }
        if (lastName == null || lastName.length() < 4 || lastName.length() > 15) {
            return false;
        }
        
        // Validate Address Length - It must have at least 20 characters
        if (address == null || address.length() < 20) {
            return false;
        }
       
        // Validate Sphere Value - It must be between -20.00 and +20.00
        if (sphere < -20.00 || sphere > 20.00) {
            return false;
        }

        // Validate Cylinder Value - It must be between -4.00 and +4.00
        if (cylinder < -4.00 || cylinder > 4.00) {
            return false;
        }

        // Validate Axis Value - It must be between 0 and 180
        if (axis < 0 || axis > 180) {
            return false;
        }

        // Validate Optometrist's Name - It must be between 8 and 25 characters
        if (optometrist == null || optometrist.length() < 8 || optometrist.length() > 25) {
            return false; 
        }
        
        // Validate Examination Date - It cannot be null or in incorrect format
        if (examinationDate == null) {
            return false; 
        }


        String prescriptionInfo = String.format("ID: %d, Name: %s %s, Address: %s, Sphere: %.2f, Axis: %.2f, Cylinder: %.2f, Date: %s, Optometrist: %s%n",prescID, firstName, lastName, address, sphere, axis, cylinder,new SimpleDateFormat("dd/MM/yy").format(examinationDate), optometrist);


        try (BufferedWriter writer = new BufferedWriter(new FileWriter("presc.txt", true))) {
            writer.write(prescriptionInfo);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

   
    // This method validates if the remark and remark type are valid. 
    // If any validation fails, it returns false. 
    // If valid, it saves the remark to remark.txt and returns true.
    public boolean addRemark(String remark, String remarkType) {
        if (!isValidRemarkType(remarkType) || remark.isEmpty()) {
            System.out.println("Invalid remark or remark type.");
            return false;
        }
        
        //Validate Remark Length to be between 6 and 20 words. 
        //Remark should start with an uppercase letter.

        String[] words = remark.trim().split("\\s+");
        if (words.length < 6 || words.length > 20 || !Character.isUpperCase(remark.charAt(0))) {
            System.out.println("Remark must be between 6 and 20 words and start with an uppercase letter.");
            return false;
        }

        // Check if prescription cannot have more than 2 remarks.
        if (postRemarks.size() >= 2) {
            System.out.println("Cannot add more than two remarks.");
            return false;
        }

        String remarkInfo = String.format("[%s] %s: %s%n",new SimpleDateFormat("yyyy-MM-dd").format(new Date()), remarkType, remark);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("remark.txt", true))) {
            writer.write(remarkInfo);
            postRemarks.add(remark);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isValidRemarkType(String remarkType) {
        for (String type : remarkTypes) {
            if (type.equalsIgnoreCase(remarkType)) {
                return true;
            }
        }
        return false;
    }

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Date getExaminationDate() {
		return examinationDate;
	}
	
}
