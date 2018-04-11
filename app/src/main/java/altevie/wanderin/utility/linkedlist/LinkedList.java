package altevie.wanderin.utility.linkedlist;

import altevie.wanderin.utility.exceptions.EmptyListException;
import altevie.wanderin.utility.iterator.Iterator;
import altevie.wanderin.utility.iterator.LinkedListElementIterator;

public class LinkedList 
{	
	public LinkedList() {
		super();
	}

	public void inserisciInTesta(Node node)
	{
		if(isEmpty())
			tail = node;
		
		node.setNext(head);
		head = node;		
		size++;
	}
	
	public void inserisciInCoda(Node node)
	{
		if(isEmpty())
			head = node;
		else
			tail.setNext(node);
		
		tail = node;
		tail.setNext(null);
		size++;
	}
	
	public Node cancellaInTesta()
	{
		Node temp = head;
		head = head.getNext();
		size--;
		return temp;
	}
	
	public Node cancellaInCoda()
	{
		Node temp = tail;
		Node node = head;
		
		for(int i = 0; i < size() - 2; i++)
			node = node.getNext();
		
		node.setNext(null);
		tail = node;
		size--;
		return temp;
	}
	
	public int size()
	{
		return size;
	}
	
	public boolean isEmpty()
	{
		return (this.head == null);
	}
	
	public Node getHead(){return head;}
	public Node getTail(){return tail;}
	public void setHead(Node n){head = n;}
	public void setTail(Node n){tail = n;}
	public Iterator elements() {return new LinkedListElementIterator(this);}	
	public void invertiLista()
	{
		Node precedente = getHead();
		Node corrente = precedente.getNext();
		precedente.setNext(null);
		
		while(corrente != null)
		{
			Node successivo = corrente.getNext();
			corrente.setNext(precedente);
			precedente = corrente;
			corrente = successivo;
		}
		
		setTail(getHead());
		setHead(precedente);
	}
	
	public boolean equals(Object obj)
	{
		LinkedList temp = (LinkedList) obj;
		if(temp.size() != this.size())
			return false;
		else
		{
			boolean ret = true;
			Node node1 = temp.getHead();
			Node node2 = this.getHead();
			
			for(int i = 0; i < temp.size();i++){
				if(!node1.equals(node2))
				{
					ret = false;
					break;
				}
				node1 = node1.getNext();
				node2 = node2.getNext();
			}
			
			return ret;
		}
	}
	
	public Object clone()
	{
	
		LinkedList cloned = new LinkedList();
		Node current = head;
		while(current != null){
			cloned.inserisciInCoda((Node) current.clone());
			current = current.getNext();
		}
		
		return cloned;
	}

	public String toString()
	{
		String str = "";
		if(this.isEmpty())
			throw new EmptyListException("Struttura Dati vuota!!");

		str += "Classe che contiene :( Front : ";
		Node corrente = getHead();
		while(corrente != null)
		{
			str += "" + corrente.getElement().toString() + " ";
			corrente = corrente.getNext();
		}
		str +=")";
		
		return str;
	}
	
private Node head;
private Node tail;
private int size;

	
}
