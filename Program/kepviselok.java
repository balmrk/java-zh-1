package Program;

public class kepviselok{
    public int valasztokerulet, kapottSzavazat;
    public String vezeteknev,keresztnev,part;
    public kepviselok(int valasztokerulet, int kapottSzavazat, String vezeteknev, String keresztnev, String part){
        this.valasztokerulet = valasztokerulet;
        this.kapottSzavazat = kapottSzavazat;
        this.vezeteknev = vezeteknev;
        this.keresztnev = keresztnev;
        this.part = part;
    }
    public kepviselok(String vez, String ker){
        this.vezeteknev = vez;
        this.keresztnev = ker;
    }

    public void setPart(String uj){
        this.part = uj;
    }

    public void kiir(){
        System.out.println("Vezetéknév: "+this.vezeteknev);
        System.out.println("Keresztnév: "+this.keresztnev);
        System.out.println("Választókerület: "+this.valasztokerulet);
        System.out.println("Kapott szavazat: "+this.kapottSzavazat);
        System.out.println("Párt: "+this.part);
    }
    public boolean nevStimmel(kepviselok o){
        if(o.vezeteknev.equals(vezeteknev) && o.keresztnev.equals(keresztnev)){
            return true;
        }
        return false;
    }
}
