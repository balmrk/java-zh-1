package Program;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class valasztas {
    public int indultak;
    public int maxSzavazo = 12345;
    public int leadottSzavazatok;
    List<kepviselok> jeloltek = new ArrayList<kepviselok>();

    public valasztas() throws IOException {
        System.out.println("1. Feladat:");
        Beolvas();
        System.out.println("Beolvasva.");

        fuggetlenAtir();
        System.out.println("'-' kicserélve 'független'-re");

        System.out.println("2. Feladat:");
        indulokSzama();

        System.out.println("3. Feladat:");
        keresesNevvel();

        System.out.println("4. Feladat:");
        reszveteliArany();

        System.out.println("5. Feladat:");
        partokSzavazatai();

        System.out.println("6. Feladat:");
        nyertesKiir();

        System.out.println("7. Feladat:");
        ujKepviselok();
    }
    public void fuggetlenAtir(){
        for(kepviselok a : jeloltek){
            if(a.part.equals("-")) a.part = "fuggetlen";
        }
    }
    public void ujKepviselok() throws IOException {
        HashMap<Integer,kepviselok> ujak = new HashMap<Integer,kepviselok>();

        for (kepviselok a : jeloltek){
            ujak.putIfAbsent(a.valasztokerulet,a);
            if (ujak.containsKey(a.valasztokerulet) && ujak.get(a.valasztokerulet).kapottSzavazat < a.kapottSzavazat){
                ujak.replace(a.valasztokerulet, a);
            }
        }
        //ujak.forEach((k,v)-> System.out.println("Körzet: "+k + "Képviselő: " + v.vezeteknev + " " + v.keresztnev + " " + v.part));

        FileWriter kiiro = new FileWriter("kepviselok.txt");
        for (Map.Entry<Integer, kepviselok> entry : ujak.entrySet()) {
            Integer k = entry.getKey();
            kepviselok v = entry.getValue();
            kiiro.write("Körzet:" + k + "Képviselő: " + v.vezeteknev + " " + v.keresztnev + " " + v.part+"\n");
        }
        kiiro.close();
    }
    public void nyertesKiir() {
        int max = 0;
        for (Program.kepviselok kepviselok : jeloltek) {
            if (kepviselok.kapottSzavazat > max) {
                max = kepviselok.kapottSzavazat;
            }
        }
        for (Program.kepviselok kepviselok : jeloltek) {
            if (kepviselok.kapottSzavazat == max) {
                if (kepviselok.part.equals("-")) kepviselok.setPart("független");
                System.out.println("A választást a " + kepviselok.valasztokerulet + ". választókerületben " + kepviselok.vezeteknev + " " + kepviselok.keresztnev + " (" + kepviselok.part + ") nyerte " + kepviselok.kapottSzavazat + " szavazattal.");
            }
        }
    }
    public void partokSzavazatai(){
        int max = 0;
        HashSet<String> partok = new HashSet<String>();
        for (Program.kepviselok kepviselok : jeloltek) {
            partok.add(kepviselok.part);
        }
        Iterator iter = partok.iterator();
        while(iter.hasNext()){
            String neve = iter.next().toString();
            int darab = 0;
            for (Program.kepviselok kepviselok : jeloltek) {
                if (neve.equals(kepviselok.part)) {
                    darab += kepviselok.kapottSzavazat;
                }
            }

            System.out.println("A "+neve+" part "+darab+" szavazatot kapott.");
        }
    }
    public void reszveteliArany(){
        int reszvetel = 0;
        double arany = 0;

        for (int i=0; i<jeloltek.size();i++){
            reszvetel += jeloltek.get(i).kapottSzavazat;
        }
        leadottSzavazatok = reszvetel;

        double reszv = reszvetel;

        arany = reszv / maxSzavazo;
        arany *= 100;

        System.out.println("A szavazáson " + reszv + " polgár, a jogosultak " + String.format("%.2f", arany) + "%-a.");
    }
    public void keresesNevvel() {
        Scanner beker = new Scanner(System.in);
        System.out.println("A kereséshez adjon meg egy vezetéknevet:");
        String vezeteknev = beker.nextLine();
        System.out.println("A kereséshez adjon meg egy utónevet:");
        String keresztnev = beker.nextLine();

        kepviselok keresett = new kepviselok(vezeteknev, keresztnev);

        for (int i = 0; i < jeloltek.size(); i++) {
            if (jeloltek.get(i).nevStimmel(keresett)) {
                System.out.println(vezeteknev + " " + keresztnev + " " + jeloltek.get(i).kapottSzavazat + " szavazatot kapott.");
                return;
            }
        }
        System.out.println("Ilyen nevű képviselő nem szerepel a nyilvántartásban!");
    }

    public void indulokSzama(){
        System.out.println("A helyhatósági választáson "+this.indultak+" képviselőjelölt indult.");
    }
    public void Beolvas(){
        String sor = "";
        File db = new File("szavazatok.txt");
        Scanner scan = null;
        try{
            scan = new Scanner(db);
            while (scan.hasNextLine()){
                sor = scan.nextLine();
                String[] bontott = sor.split(" ");
                jeloltek.add(new kepviselok(Integer.parseInt(bontott[0]),Integer.parseInt(bontott[1]),bontott[2],bontott[3],bontott[4]));
                this.indultak++;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {
        valasztas gyoztunk = new valasztas();
    }
}
