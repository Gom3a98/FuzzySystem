
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javafx.util.Pair;

public class Fuzzy {

    ArrayList<Rule> Rules = new ArrayList<>();
    ArrayList<Variable> Variables = new ArrayList<>();
    ArrayList<Pair<String ,Double>> membership = new ArrayList<Pair<String ,Double>>();
    class Point{
        public int x = 0;
        public int y =0 ;
    }
    private void FuzziFication(){
        for (Variable v : Variables)
        {
            ArrayList<Term>myTerms = new ArrayList<>();
            ArrayList<Pair<Double ,Double>> equations = new ArrayList<Pair<Double ,Double>>();  

            myTerms = v.Terms;
            
            for (Term t : myTerms)
            {
            ArrayList<Point>points = new ArrayList<>();
            
                for (int i = 0; i < t.Range.size(); i++) {
                    Point p = new Point();
                    p.x = t.Range.get(i);
                    p.y = 1;
                    if(i==0)
                    {
                        p.y = 0;        
                    }
                    if (i==t.Range.size()-1)
                    {
                        p.y = 1;
                    }
                    points.add(p);
                    
                }
                for(int j =0 ;j<points.size()-1;j++)
                {
                    Point p1 = new Point();
                    Point p2 = new Point();
                    p1= points.get(j);
                    p2= points.get(j);
                    if(p1.x!=p2.x)
                    {
                        double slop = (p2.y-p1.y)/(p2.x-p1.x);
                        double b = p1.y - p1.x * slop ;
                        equations.add(new Pair<Double ,Double>(slop,b));
                    }
                }
            }
            ArrayList<Double>temp = new ArrayList<>();
            for(int n =0 ;n<equations.size();n++)
            {
                double result = v.CrispValue * equations.get(n).getKey()+equations.get(n).getValue();
                 temp.add(result);
            }
            for (int y =0 ;y<temp.size();y++)
            {
                if (temp.get(y)!=0)
                {
                    membership.add(new Pair<String , Double>(myTerms.indexOf(y)));
                }
            }
        }
    }
    public void readInput() throws FileNotFoundException, IOException {
        File file = new File("input.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        int nOfVariables = Integer.parseInt(br.readLine());

        for (int i = 0; i < nOfVariables + 1; i++) {
            Variable v = new Variable();

            String st = br.readLine();

            if (i != nOfVariables) {
                String arrrrr[] = st.split(" ");
                v.name = arrrrr[0];
                v.CrispValue = Integer.parseInt(arrrrr[1]);
            } else {
                v.name = st;
            }
            v.NumOfTerms = Integer.parseInt(br.readLine());

            for (int j = 0; j < v.NumOfTerms; j++) {
                String ins[] = br.readLine().split(" ");
                Term t = new Term();
                t.name = ins[0];
                t.Type = ins[1];
                ins = br.readLine().split(" ");

                for (String k : ins) {
                    t.Range.add(Integer.parseInt(k));
                }

                v.Terms.add(t);

            }
            Variables.add(v);
        }
        int numOfRules = Integer.parseInt(br.readLine());

        for (int i = 0; i < numOfRules; i++) {
            Rule r = new Rule();
            r.numOfPremises = br.read() - 48;
            r.body = br.readLine();
            Rules.add(r);
        }
    }
}
