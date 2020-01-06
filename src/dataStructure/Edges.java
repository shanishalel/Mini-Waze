package dataStructure;

public class Edges implements edge_data, java.io.Serializable {
	
	
	private static final long serialVersionUID = -6012857914742052453L;
	private node_data SRC;
	private node_data DEST;
	private double WEIGHT;
	private String INFO;
	private int TAG;
	
	public Edges (node_data SRC, node_data DEST, double WEIGHT) {
		this.SRC = SRC;
		this.DEST = DEST;
		this.WEIGHT =WEIGHT;
	}
		
	@Override
	public int getSrc() {
		return this.SRC.getKey();
	}
	
	@Override
	public int getDest() {
		return this.DEST.getKey();
	}

	@Override
	public double getWeight() {
		return this.WEIGHT;
	}

	@Override
	public String getInfo() {
		return this.INFO;
	}

	@Override
	public void setInfo(String s) {
		this.INFO =s;
	}

	@Override
	public int getTag() {
		return TAG;
	}

	@Override
	public void setTag(int t) {
		this.TAG=t;
	}
}
