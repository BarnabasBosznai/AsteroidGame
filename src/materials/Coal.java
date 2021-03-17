package materials;

public class Coal extends Material {
    @Override
    public void count(MaterialCounter counter) {

    }

    @Override
    public boolean compatibleWith(Material material) {
        if(this.getClass()==material.getClass()){
            return true;
        }
        else {return false;}
    }
}
