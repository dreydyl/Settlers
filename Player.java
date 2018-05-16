public class Player {

	private ArrayList<DevelopmentCard> dCards;
	private ArrayList<ResourceCard> rCards;
	private int points;
	public static numPlayers = 0;

	public Player() {
		devCards = new ArrayList<Card>();
		resCards = new ArrayList<Card>();
		points = 0;
		numPlayers++;
	}

	public void add(Hex.Resource r) {
		rCards.add(new ResourceCard(r));
	}
	
	private boolean pay(int... resources) {
		if(fulfils(resources)) {
			remove(resources);
			return true;
		}
		return false;
	}
	private boolean fulfils(int... resources) {
		int[] r = new int[resources.length];
		for(int i = 0;i < r.length;i++) r[i] = 0;
		for(ResourceCard rc:rCards) {
			for(int i = 0;i < r.length;i++) {
				r[i]++;
			}
		}
		for(int i = 0;i < r.length;i++) if(r[i] < resources[i]) return false;
		return true;
	}
	private void remove(int... r) {
		for(int i = cards.size()-1;i >= 0;i--) {
			for(int j = 0;j < r.length;j++) {
				if(r[j] > 0) {
					cards.remove(i);
					r[j]--;
				}
			}
		}
	}
	
	public boolean createBuilding() {
		return pay(1,1,0,1,1);
	}
	public boolean upgradeBuilding() {
		return pay(0,0,3,2,0);
	}
	public boolean createRoad() {
		return pay(1,1,0,0,0);
	}
	public boolean getDevelopment() {
		return pay(0,0,1,1,1);
	}
	
}
