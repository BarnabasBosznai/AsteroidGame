package materials;

public class Iron extends Material {
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
