package tests;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestAddRemark {
	
	private Prescription prescription;

    @BeforeEach
    void setUp() {
        prescription = new Prescription(1, "Sky", "Smith", "Test Address", 0.0f, 0.0f, 0.0f, new Date(), "Dr. Grant");
    }

    //Test Case 1:Valid Remark

	@Test
    void testAddValidRemark() {
        assertTrue(prescription.addRemark("This is a valid remark for testing.", "Client"));     
        assertTrue(prescription.addRemark("Another valid test remark to check.", "Client"));
    }

	
	//Test Case 2: Short Remark

    @Test
    void testAddRemarkTooShort() {
        assertFalse(prescription.addRemark("Too short", "Client"));      
        assertFalse(prescription.addRemark("Another short remark", "Client"));
    }

    
    //Test Case 3: Long Remark

    @Test
    void testAddRemarkTooLong() {
        String longRemark1 = "This remark is intentionally crafted to exceed the limit of twenty words " +
                "to test the validation functionality of the addRemark method in the Prescription class. " +
                "It should fail the validation checks and return false.";
        assertFalse(prescription.addRemark(longRemark1, "Client"));
        
        String longRemark2 = "This is another excessively long remark that is designed to fail the validation rules " +
                             "for the number of words allowed in a valid remark.";
        assertFalse(prescription.addRemark(longRemark2, "Client"));
    }

    //Test Case 4: Invalid First Character of remark

    @Test
    void testAddRemarkFirstCharacterNotUppercase() {
        assertFalse(prescription.addRemark("this remark does not start with an uppercase letter.", "Client"));
        assertFalse(prescription.addRemark("another invalid remark because it starts with a lowercase letter.", "Client"));
    }

    
    //Test Case 5: Invalid Remark Type

    @Test
    void testAddInvalidRemarkType() {
        assertFalse(prescription.addRemark("This is a valid remark for testing.", "INVALID_TYPE"));
        assertFalse(prescription.addRemark("This is a valid remark for testing.", "UNKNOWN_TYPE"));
    }
    
    //Test Case 6: Invalid Empty Remark

    @Test
    void testAddEmptyRemark() {
        assertFalse(prescription.addRemark("", "Client"));
        assertFalse(prescription.addRemark(" ", "Client")); 
    }
    
    @Test
    void testAddMoreThanTwoRemarks() {
        // Add two valid remarks
        assertTrue(prescription.addRemark("This is a valid First remark for testing.", "Client"));
        assertTrue(prescription.addRemark("This is a valid Second remark for testing.", "Client"));

        // Attempt to add a third remark
        assertFalse(prescription.addRemark("This third remark should not be allowed.", "Client"));
    }

}
