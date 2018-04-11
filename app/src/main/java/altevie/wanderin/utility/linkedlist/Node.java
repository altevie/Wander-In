package altevie.wanderin.utility.linkedlist;

public class Node 
{

	
	public Node()
	{
		this(null, null);
	}

	public Node(Object object, Node node)
	{
		this.next = node;
		this.element = object;
	}
	
	public Object getElement()
	{
		return this.element;
	}
	
	public Node getNext()
	{
		return this.next;
	}
	
	public void setElement(Object element)
	{
		this.element = element;
	}
	
	public void setNext(Node node)
	{
		this.next = node;
	}
	
	public boolean equals(Object obj)
	{
		Node temp = (Node) obj;
		return (temp.getElement().equals(this.getElement()));
	}
	
	public Object clone()
	{
		return new Node(this.getElement(), null);
	}
	
	public String toString(){
		String ris = "";
		ris = element.toString();
		return ris;
	}
	
private Node next;
private Object element;
}
