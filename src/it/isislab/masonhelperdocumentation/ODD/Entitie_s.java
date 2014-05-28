package it.isislab.masonhelperdocumentation.ODD;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 
 * @author Romano Simone 0512101343
 * This class represent a list of Entities 
 * collected in wizard process.
 */
public class Entitie_s implements Serializable{
	private static final long serialVersionUID = 1;
	private ArrayList<Entity> entitie_s;
	public static String serializedName = "entitie_s.ser";
	
	public Entitie_s(){
		entitie_s = new ArrayList<Entity>();
	}
	
	public void add(Entity e){
		entitie_s.add(e);
	}
	
	public Entity getEntity(String name){
		for (Entity e:entitie_s){
			if (e.getName().equals(name))	return e;
		}
		return null;
	}
	
	public Entity getEntity(int position){
		if (entitie_s.size()>position)	return entitie_s.get(position);
		return null;
	}

	public ArrayList<Entity> getEntitie_s() {
		return entitie_s;
	}	
}
