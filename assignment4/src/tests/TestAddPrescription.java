package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;

class TestAddPrescription {
	
    // Test Case 1: Invalid First and Last Name

    @Test
    void testInValidFirstName() {
        Prescription prescription1 = new Prescription(1, "Sky", "Smith", "Test Address", 0.0f, 0.0f, 0.0f, new Date(), "Dr. Grant");
        System.out.println("Invalid Fist Name: " + prescription1.getFirstName());
        assertFalse(prescription1.addPrescription(), "Expected Result: Invalid First Name.");
    }
    
    @Test
    void testInValidLastName() {
        Prescription prescription1 = new Prescription(2, "John", "Lee", "Address", 0.0f, 0.0f, 0.0f, new Date(), "Dr. Grant");
        System.out.println("Invalid Last Name: " + prescription1.getLastName());
        assertFalse(prescription1.addPrescription(), "Expected Result: Invalid Last Name.");
    }
    
    
    //Test Case 2: Short First and Last Name

    @Test
    void testShortFirstName() {
        Prescription prescription1 = new Prescription(3, "Joe", "Smith", "Address", 0.0f, 0.0f, 0.0f, new Date(), "Dr. Smith");
        assertFalse(prescription1.addPrescription(), "Expected Result: Prescription should not be added with a short first name.");
    }
    
    @Test
    void testShortLastName() {
        Prescription prescription2 = new Prescription(4, "John", "Li", "Address", 0.0f, 0.0f, 0.0f, new Date(), "Dr. Smith");
        assertFalse(prescription2.addPrescription(), "Expected Result: Prescription should not be added with a short last name.");
    }
    
    
    //Test Case 3: Long First and Last Name

    @Test
    void testLongFirstName() {
        Prescription prescription1 = new Prescription(5, "LongCharacterFirstName", "Smith", "Address", 0.0f, 0.0f, 0.0f, new Date(), "Dr. Smith");
        assertFalse(prescription1.addPrescription(), "Expected Result: Prescription should not be added with a long first name.");
    }
    
    @Test
    void testLongLastName() {
        Prescription prescription2 = new Prescription(6, "John", "LongCharacterLastName", "Address", 0.0f, 0.0f, 0.0f, new Date(), "Dr. Smith");
        assertFalse(prescription2.addPrescription(), "Expected Result: Prescription should not be added with a long last name.");
    }
 
  
    //Test Case 4: Invalid Address

    @Test
    void testValidAddress() {
        Prescription prescription = new Prescription(7, "John", "Smith", "124 La Trobe St, Melbourne VIC 3000", 0.0f, 0.0f, 0.0f, new Date(), "Dr. Grant");
        assertTrue(prescription.addPrescription(), "Expected Result: Prescription should be added successfully with a valid address.");
    }

    @Test
    void testShortAddress() {
        Prescription prescription = new Prescription(8, "John", "Smith", "Short Address", 0.0f, 0.0f, 0.0f, new Date(), "Dr. Grant");
        assertFalse(prescription.addPrescription(), "Expected Result: Prescription should not be added with an address shorter than 20 characters.");
    }
    
    //Test Case 5: Invalid Sphere Value

    @Test
    void testInvalidSphereTooLow() {
        Prescription prescription = new Prescription(9, "John", "Smith", "124 La Trobe St, Melbourne VIC 3000", -25.0f, 0.0f, 0.0f, new Date(), "Dr. Grant");
        assertFalse(prescription.addPrescription(), "Expected Result: Prescription should not be added with sphere less than -20.00.");
    }

    @Test
    void testInvalidSphereTooHigh() {
        Prescription prescription = new Prescription(10, "John", "Smith", "124 La Trobe St, Melbourne VIC 3000", 25.0f, 0.0f, 0.0f, new Date(), "Dr. Grant");
        assertFalse(prescription.addPrescription(), "Expected Result: Prescription should not be added with sphere greater than +20.00.");
    }
    
    // Test Case 6: Invalid Cylinder Value

    @Test
    void testInvalidCylinderTooLow() {
        Prescription prescription = new Prescription(11, "John", "Smith", "124 La Trobe St, Melbourne VIC 3000", 0.0f, 0.0f, -5.0f, new Date(), "Dr. Grant");
        assertFalse(prescription.addPrescription(), "Expected Result: Prescription should not be added with cylinder less than -4.00.");
    }
    

    @Test
    void testInvalidCylinderTooHigh() {
        Prescription prescription = new Prescription(12, "John", "Smith", "124 La Trobe St, Melbourne VIC 3000", 0.0f, 0.0f, 5.0f, new Date(), "Dr. Grant");
        assertFalse(prescription.addPrescription(), "Expected Result: Prescription should not be added with cylinder greater than +4.00.");
    }

    
    //Test Case 7:Invalid Axis Value

    @Test
    void testInvalidAxisTooLow() {
        Prescription prescription = new Prescription(13, "John", "Smith", "124 La Trobe St, Melbourne VIC 3000", 0.0f, -1.0f, 0.0f, new Date(), "Dr. Grant");
        assertFalse(prescription.addPrescription(), "Expected Result: Prescription should not be added with axis less than 0.");
    }

    @Test
    void testInvalidAxisTooHigh() {
        Prescription prescription = new Prescription(14, "John", "Smith", "124 La Trobe St, Melbourne VIC 3000", 0.0f, 181.0f, 0.0f, new Date(), "Dr. Grant");
        assertFalse(prescription.addPrescription(), "Expected Result: Prescription should not be added with axis greater than 180.");
    }
    

    
    //Test Case 8:Date Format: DD/MM/YY.

    @Test
    void testValidDateFormat() {
        Prescription prescription = new Prescription(15, "John", "Smith", "124 La Trobe St, Melbourne VIC 3000", 0.0f, 0.0f, 0.0f, new Date(), "Dr. Grant");
        
        assertTrue(prescription.addPrescription(), "Expected Result: Prescription should be added successfully.");
        
        String filePath = "presc.txt";
        String expectedDateFormat = new SimpleDateFormat("dd/MM/yy").format(prescription.getExaminationDate());

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean dateFound = false;
            while ((line = reader.readLine()) != null) {
                if (line.contains("Date: " + expectedDateFormat)) {
                    dateFound = true;
                    break;
                }
            }
            assertTrue(dateFound, "Expected Result: The date should be recorded in the format DD/MM/YY.");
        } catch (IOException e) {
            fail("IOException occurred while reading the prescription file: " + e.getMessage());
        }
    }
    
    @Test
    void testInvalidDateFormat() {
        String invalidDateString = "31-12-2024";
        Date invalidDate = null;

        try {
            invalidDate = new SimpleDateFormat("dd/MM/yy").parse(invalidDateString);
        } catch (ParseException e) {
            invalidDate = null; 
        }

        Prescription prescription = new Prescription(16, "John", "Smith", "124 La Trobe St, Melbourne VIC 3000", 0.0f, 0.0f, 0.0f, invalidDate, "Dr. Grant");
        
        assertFalse(prescription.addPrescription(), "Expected Result: Prescription should not be added with an invalid date format.");

        String filePath = "presc.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean entryFound = false;
            while ((line = reader.readLine()) != null) {
                if (line.contains("ID: 19")) { 
                    entryFound = true;
                    break;
                }
            }
            assertFalse(entryFound, "Expected Result: No new prescription entry should be found in the file.");
        } catch (IOException e) {
            fail("IOException occurred while reading the prescription file: " + e.getMessage());
        }
    }
    
    
    //Test Case 9: Optometrist Name

    @Test
    void testShortOptometristName() {
        Prescription prescription = new Prescription(17, "John", "Smith","124 La Trobe St, Melbourne VIC 3000", 0.0f, 0.0f, 0.0f, new Date(), "Dr. "); // Invalid name (4 characters)
        assertFalse(prescription.addPrescription(), "Expected Result: Prescription should not be added with optometrist name less than 8 characters.");
    }
    
    @Test
    void testLongOptometristName() {
        Prescription prescription = new Prescription(18, "John", "Smith", "124 La Trobe St, Melbourne VIC 3000", 0.0f, 0.0f, 0.0f,  new Date(), "Dr. Johnathan Alexander Grant"); // Invalid name (27 characters)       
        assertFalse(prescription.addPrescription(), "Expected Result: Prescription should not be added with optometrist name greater than 25 characters.");
    }

}
