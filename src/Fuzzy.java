
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import javafx.util.Pair;

public class Fuzzy {

    class M {

        String var;
        String term;
        double result;

        M(String varC, String termC, double resultC) {
            var = varC;
            term = termC;
            result = resultC;
        }
    }
    ArrayList<Rule> Rules = new ArrayList<>();
    ArrayList<M> FinalResult = new ArrayList<M>();
    ArrayList<Variable> Variables = new ArrayList<>();
    ArrayList<M> membership = new ArrayList<M>();

    class Point {

        public int x = 0;
        public int y = 0;
    }

    
    public void FuzziFication() {
        for (int v = 0; v < Variables.size() - 1; v++) {
            ArrayList<Term> myTerms = new ArrayList<>();
            ArrayList<Pair<Double, Double>> equations = new ArrayList<Pair<Double, Double>>();

            myTerms = Variables.get(v).Terms;

            for (Term t : myTerms) {
                ArrayList<Point> points = new ArrayList<>();

                for (int i = 0; i < t.Range.size(); i++) {
                    Point p = new Point();
                    p.x = t.Range.get(i);
                    p.y = 1;
                    if (i == 0 || i == t.Range.size() - 1) {
                        p.y = 0;
                    }
                    points.add(p);
                }

                for (int j = 0; j < points.size() - 1; j++) {
                    Point p1 = new Point();
                    Point p2 = new Point();
                    p1 = points.get(j);
                    p2 = points.get(j + 1);
                    if (p1.x != p2.x) {
                        double slop = (double) (p2.y - p1.y) / (p2.x - p1.x);
                        double b = (double) p1.y - p1.x * slop;
                        double result = Variables.get(v).CrispValue * slop + b;
                        // System.out.println(v.CrispValue+" "+p2.x + " "+result);
                        if (0 < result && result <= 1) {
                            M a = new M(Variables.get(v).name, t.name, result);
                            membership.add(a);
                            System.out.println(result + " " + Variables.get(v).name + " " + t.name);
                        }
                    }
                }

            }

        }
    }

    private double getIndex(String key)
    {
       for (M a : FinalResult) {

            if (a.term.trim().equals(key.trim()))// tirm to remove space
            {

                return a.result;
            }
        }
       return 0.0;
    }
    double getmembership(String equal[]) {
        for (M a : membership) {

            if (a.var.trim().equals(equal[0].trim()) && a.term.trim().equals(equal[1].trim()))// tirm to remove space
            {

                return a.result;
            }
        }
        return 0;
    }

    public void Inference() {
        System.out.println("************************");
        for (Rule r : Rules) {
            ArrayList<Double> R = new ArrayList<>();
            String[] IF = r.body.split("then");
            String AfterIF[] = IF[1].split("=");
            String[] And = IF[0].split("AND");
            String[] Or = IF[0].split("OR");
            System.out.println(r.body);
            if (And.length > 1) {
                for (String a : And) {
                    String[] equal = a.split("=");

                    double y = getmembership(equal);
                    System.out.println(equal[0] + "  " + equal[1] + " " + y);
                    R.add(y);
                }
                int minIndex = R.indexOf(Collections.min(R));
                System.out.println(AfterIF[0] + " " + AfterIF[1] + " " + R.get(minIndex));
                FinalResult.add(new M(AfterIF[0], AfterIF[1], R.get(minIndex)));
            } else {
                for (String o : Or) {

                    String[] equal = o.split("=");

                    double y = getmembership(equal);
                    System.out.println(equal[0] + "  " + equal[1] + " " + y);
                    R.add(y);

                }
                int maxIndex = R.indexOf(Collections.max(R));
                System.out.println(AfterIF[0] + " " + AfterIF[1] + " " + R.get(maxIndex));
                FinalResult.add(new M(AfterIF[0], AfterIF[1], R.get(maxIndex)));
            }
            System.out.println("________________");
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

    public void DeFuzzification() {
        Variable OutPutVariable = Variables.get(Variables.size() - 1);
        ArrayList<Point> points = new ArrayList<>();
//        HashMap<String , Double> CX = new HashMap<String,Double>();
        ArrayList<M> CX = new ArrayList<>();
        for (Term t : OutPutVariable.Terms) {
           
        }
        for (Term t : OutPutVariable.Terms) {

            for (int i = 0; i < t.Range.size(); i++) {
                Point p = new Point();
                p.x = t.Range.get(i);
                p.y = 1;
                if (i == 0 || i == t.Range.size() - 1) {
                    p.y = 0;
                }
                points.add(p);
            }
            double A = 0.0;
            double sum = 0;
            for (int i = 0; i < points.size() - 1; i++) {
                sum += (double) (points.get(i).x * points.get(i + 1).y - points.get(i + 1).x * points.get(i).y);
            }
            A = sum / 2.0;
            sum = 0.0;
            for (int i = 0; i < points.size() - 1; i++) {
                double firstTerm = (double) (points.get(i).x + points.get(i + 1).x);
                double secTerm = (double) (points.get(i).x * points.get(i + 1).y - points.get(i + 1).x * points.get(i).y);
                sum += firstTerm * secTerm;
            }
             CX.add(new M(OutPutVariable.name,t.name , sum / (6.0 * A)));
        }
        double SumOfMemberValues = 0.0;

        for (int i = 0; i < FinalResult.size(); i++) {
            SumOfMemberValues+=FinalResult.get(i).result;
        }
        double Nomrator = 0.0;
        for (int i = 0; i < CX.size(); i++) {
            System.out.println(CX.get(i).result  +"   "+getIndex(FinalResult.get(i).term)+"  "+CX.get(i).term+"    "+FinalResult.get(i).term);
            
//            System.out.println(CX.get(FinalResult.get(i).term).doubleValue());
            System.out.println("-----------");
       Nomrator+=(double)(CX.get(i).result  *getIndex(FinalResult.get(i).term));
        }
        System.out.println(Nomrator/SumOfMemberValues);
        
        

    }
    
}
