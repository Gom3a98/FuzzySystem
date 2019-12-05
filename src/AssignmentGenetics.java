
import java.io.IOException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author new
 */
public class AssignmentGenetics {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        Fuzzy f = new Fuzzy();
        f.readInput();
        f.FuzziFication();
        f.Inference();
        f.DeFuzzification();
    }
    
}
