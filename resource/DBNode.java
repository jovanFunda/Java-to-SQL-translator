package resource;

public abstract class DBNode {

    private String name;
    private DBNode parent;
    
	public DBNode(String name2, DBNode parent2) {
		this.name = name2;
		this.parent = parent2;
	}

	public Object getName() {
		return name;
	}


}
