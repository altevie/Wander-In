package altevie.wanderin.utility.set;

import altevie.wanderin.utility.position.Position;

public class SetPosition implements Position, Comparable {
	public SetPosition(Object o){
		obj =o;
		set = null;
	}
	public Set getSet(){return set;}
	public Object element() {return obj;}
	public void setSet(Set s){set = s;}
	public void setElement(Object o) {obj = o;}
	
	public boolean equals(Object obj){
		SetPosition s = (SetPosition)obj;
		if(this.element().equals(s.element()))
			return true;
		return false;
	}

	public int compareTo(Object obj)
	{
		SetPosition s = (SetPosition)obj;
		Comparable c1 = (Comparable) this.element();
		Comparable c2 = (Comparable) s.element();
		return c1.compareTo(c2);
	}
	
	public String toString()
	{
		return this.element().toString();
	}

private Object obj;
private Set set;
}
