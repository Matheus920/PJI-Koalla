package app.model;


public class CriteriaWithValue {
    
    private Criteria criteria;
    private int peso;
    
    public CriteriaWithValue(Criteria criteria, int peso){
        this.criteria = criteria;
        this.peso = peso;
    }

    public Criteria getCriteria() {
        return criteria;
    }

    public void setCriteria(Criteria criteria) {
        this.criteria = criteria;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }
    
    
}
