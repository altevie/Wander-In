package altevie.wanderin.utility.graphDec;

import altevie.wanderin.utility.exceptions.EdgeNotFoundException;
import altevie.wanderin.utility.exceptions.EmptyStackException;
import altevie.wanderin.utility.exceptions.InvalidKeyException;
import altevie.wanderin.utility.exceptions.InvalidPositionException;
import altevie.wanderin.utility.exceptions.NoSuchElementException;
import altevie.wanderin.utility.exceptions.VertexNotFoundException;
import altevie.wanderin.utility.iterator.Iterator;
import altevie.wanderin.utility.list.List;
import altevie.wanderin.utility.list.NodeList;
import altevie.wanderin.utility.map.DoubleLinkedListMap;
import altevie.wanderin.utility.map.Map;
import altevie.wanderin.utility.position.DecorablePosition;
import altevie.wanderin.utility.position.Position;

public class AdjacencyListGraph implements Graph 
{	
	/** Costruttore di default che crea un grafo vuoto */
	public AdjacencyListGraph() {
		VList = new NodeList();
		EList = new NodeList();
	}
	public boolean isEmpty(){return((VList.isEmpty())&&(EList.isEmpty()));}
	protected MyPosition checkPosition(Position p)throws InvalidPositionException
	{	
		if(p == null) 
			throw new InvalidPositionException("Position nulla");
		
		if(p instanceof Vertex)
			return (MyPosition) checkVertex((Vertex)p);
		if(p instanceof Edge)
			return (MyPosition) checkEdge((Edge)p);
		else
			throw new InvalidPositionException("Position non valida!!");		
	}
	
	protected DecVertex checkVertex(Vertex v)throws InvalidPositionException,VertexNotFoundException
	{
		if(v == null) 
			throw new InvalidPositionException("Vertex nullo passato");
		
		Iterator it = vertices();
		while(it.hasNext()){
			DecVertex v1 = (DecVertex) it.next();

			//if(v1.equals(v))//da modificare
			if(v1.toString().substring(0,16).equals(v.toString().substring(0,16))){
				return (DecVertex) v1;
			}
		}
		throw new VertexNotFoundException("Il vertice "+v.toString()+"non è contenuto nel grafo!!");
	}
	
	protected DecEdge checkEdge(Edge e)throws InvalidPositionException,EdgeNotFoundException
	{
		if (e == null) 
			throw new InvalidPositionException("Edge nullo passato");
		
		Iterator it = edges();
		while(it.hasNext()){
			DecEdge e1 = (DecEdge) it.next();
			if(e1.equals(e))
				return e1;
		}
		throw new EdgeNotFoundException("Il vertice non � contenuto nel grafo!!");
	}
	
	/** Restituisce un iteratore sui vertici del grafo */
	public Iterator vertices() {return new PositionIterator(VList);}
	
	/** Restituisce un iteratore sugli archi del grafo */
	public Iterator edges() {return new PositionIterator(EList);}
	
	/** Restituisce un iteratore sugli archi incidenti un dato vertice */
	public Iterator incidentEdges(Vertex v) throws InvalidPositionException {
		DecVertex vv = checkVertex(v);
		return vv.incidentEdges(); //� iteratore sulla lista I(vv)�
	}
	/** Restituisce i vertici dell'arco e in un array di lunghezza 2 */
	public Vertex[] endVertices(Edge e) throws InvalidPositionException {
		DecEdge ee = checkEdge(e);
		return ee.endVertices(); //restituisce la variabile (array)
	}
	/**Metodo ausiliario: restituisce il grado di un dato vertice invocando quello
	 di DecVertex */
	public int degree(Vertex v) {
		DecVertex vv = checkVertex(v);
		return vv.degree();
	}
	
	/* Inserisce e restituisce un nuovo vertice con un dato elemento */
	public Vertex insertVertex(Object o) {
		DecVertex vv = new DecVertex(o);
		VList.insertLast(vv);
		Position p = VList.last();
		vv.setLocation(p);
		return vv;
	}
	/** Inserisce e restituisce un nuovo arco con un dato elemento tra i
	 due vertici */
	public Edge insertEdge(Vertex v, Vertex w, Object o)throws InvalidPositionException {
		DecVertex vv = checkVertex(v);
		DecVertex ww = checkVertex(w);
		DecEdge ee = new DecEdge(v, w, o);
		Position pv = vv.insertIncidence(ee);
		Position pw = ww.insertIncidence(ee);
		ee.setIncidences(pv, pw);
		Position pe = EList.insertLast(ee);
		ee.setLocation(pe);
		return ee;
	}
	
	/** Verifica se due vertici sono adiacenti */
	public boolean areAdjacent(Vertex u, Vertex v)throws InvalidPositionException {
		//cerchiamo nella lista di incidenza �pi� corta�, cio� quella del vertice con grado pi� piccolo
		Iterator iterToSearch;
		if (degree(u) < degree(v))
			iterToSearch=incidentEdges(u);
		else 
			iterToSearch=incidentEdges(v);
		
		while (iterToSearch.hasNext() ) {
			Vertex[] endV = endVertices((Edge)iterToSearch.next());
			//controllo se esiste un arco con estremit� u e v
			if ( ((endV[0]==u) && (endV[1]==v)) || ((endV[0]==v) && (endV[1]==u)))
				return true;
		}
		
		return false;
	}
	
	/** Sostituisce l�element in una data position (vertex o edge) con un
	 nuovo element e restituisce il vecchio element */
	public Object replace(Position p, Object o) throws InvalidPositionException {
		MyPosition pp = checkPosition(p);
		Object temp = p.element();
		pp.setElement(o);
		return temp;
	}
	
	/** Restituisce l�altro vertice estremit� dell�arco in questione */
	public Vertex opposite(Vertex v, Edge e) throws InvalidPositionException {
		checkVertex(v);
		DecEdge ee = checkEdge(e);
		Vertex[ ] endv = ee.endVertices();
		//Log.i("OPPOSITE->v",v.toString());
		//Log.i("endv",endv[0].toString());
		//Log.i("endv",endv[1].toString());


		//MM*********
		/* if (v == endv[0])
			return endv[1];
		else if (v == endv[1])
			return endv[0];
		else
			throw new InvalidPositionException("No such vertex exists");
		*/
		if (v.toString().substring(0,16).equals(endv[0].toString().substring(0,16)))
			return endv[1];
		else if (v.toString().substring(0,16).equals(endv[1].toString().substring(0,16)))
			return endv[0];
		else
			throw new InvalidPositionException("No such vertex exists");
	}
	
	/** Rimuove un vertice (e tutti gli archi incidenti) */
	public Object removeVertex(Vertex v) throws InvalidPositionException {
		
		DecVertex vv = checkVertex(v);
		Iterator inc = incidentEdges(v);
		while (inc.hasNext()) {
			DecEdge e = (DecEdge) inc.next();
			if (e.location() != null) // if the edge has not been marked invalid
				removeEdge(e);
		}
		VList.remove(vv.location());
		return v.element();
	}
	
	public int numEdges() {return EList.size();}
	
	public int numVertices() {return VList.size();}
	
	public Object removeEdge(Edge e) throws InvalidPositionException 
	{
		DecEdge ee = checkEdge(e);
		Vertex[ ] endv = ee.endVertices();
		DecVertex u = (DecVertex) endv[0];
		DecVertex v = (DecVertex) endv[1];
		u.removeIncidence(ee.Inc[0]);
		v.removeIncidence(ee.Inc[1]);
		EList.remove(ee.location());
		return ee.element();
	}
	public Object replace(Vertex p, Object o) throws InvalidPositionException {
		DecVertex pp = checkVertex(p);
		Object temp = p.element();
		pp.setElement(o);
		return temp;
	}
	public Object replace(Edge p, Object o) throws InvalidPositionException {
		DecEdge pp = checkEdge(p);
		Object temp = p.element();
		pp.setElement(o);
		return temp;
	}
	/// METODI PER IL GRAFO DECORATO UTILIZZANTI LE CLASSI DECVERTEX E DECEDGE
	/// DECVERTEX
	public int numDecoration(Vertex v){
		DecVertex vv = (DecVertex) checkVertex(v);
		return vv.size();
	}
	public boolean noDecoration(Vertex v){
		DecVertex vv = (DecVertex) checkVertex(v);
		return vv.isEmpty();
	}
	public Object getDecoration(Vertex v, Object key){
		DecVertex vv = (DecVertex) checkVertex(v);
		return vv.get(key);
	}
	public Object putDecoration(Vertex v, Object key, Object value){
		DecVertex vv = (DecVertex) checkVertex(v);
		return vv.put(key, value);
	}
	public Object removeDecoration(Vertex v, Object key){
		DecVertex vv = (DecVertex) checkVertex(v);
		return vv.remove(key);
	}
	public Iterator iterDecoration(Vertex v){
		DecVertex vv = (DecVertex) checkVertex(v);
		return vv.entries();
	}
	/// DECEDGE
	public int numDecoration(Edge e){
		DecEdge ee = (DecEdge) checkEdge(e);
		return ee.size();
	}
	public boolean noDecoration(Edge e){
		DecEdge ee = (DecEdge) checkEdge(e);
		return ee.isEmpty();
	}
	public Object getDecoration(Edge e, Object key){
		DecEdge ee = (DecEdge) checkEdge(e);
		//Log.i("ee_K:",ee.toStringKey());
		//Log.i("ee_getK:",ee.get(key).toString());
		return ee.get(key);
	}
	public Object putDecoration(Edge e, Object key, Object value){
		DecEdge ee = (DecEdge) checkEdge(e);
		return ee.put(key, value);
	}
	public Object removeDecoration(Edge e, Object key){
		DecEdge ee = (DecEdge) checkEdge(e);
		return ee.remove(key);
	}
	public Iterator iterDecoration(Edge e){
		DecEdge ee = (DecEdge) checkEdge(e);
		return ee.entries();
	}
	///////////////////////////////////////////////////////
	
	/// CLASSI INTERNE
	
	protected class PositionIterator implements Iterator
	{
//	//	creo un iteratore su collezione vuota
		public PositionIterator(){ list = null; cur = null; }

		public PositionIterator(List L)
		{ //creo un iteratore sulla lista L
			list = L;
			if (list.isEmpty()) 
				cur = null; // lista vuota: cursore � messo a null
			else 
				cur = list.first(); //altrimenti � la prima posizione
		}
		
		public boolean hasNext() { return (cur != null); }
		
		public Object next() throws NoSuchElementException 
		{
			if (!hasNext()) throw new NoSuchElementException("No next position");
			
			Object el = cur.element();
			if (cur == list.last()) 
				cur = null; // fine della lista: cursore messo a null
			else 
				cur = list.after(cur); // sposta il cursore in avanti
			
			return el;
		}	
//		due variabili istanza
		private List list; //riferimento alla lista da scandire
		private Position cur = null; //posizione corrente (next) - cursore
	}
	
	protected class MyPosition extends DoubleLinkedListMap implements DecorablePosition {
		/** L'elemento memorizzato in questa position. */
		protected Object elem;
		protected Map M;
		
		/** Restituisce l'elemento memorizzato in questa position. */
		public Object element() {
			return elem;
		}
		
		/** Modifica l'elemento memorizzato in questa position. */
		public void setElement(Object o) {
			elem = o;
		}

		public int size(){ return M.size(); }
		public boolean isEmpty(){ return M.isEmpty();}
		public Object get(Object key) throws InvalidKeyException{return M.get(key);}
		public Object put(Object key, Object value) throws InvalidKeyException{return M.put(key,value);}
		public Object remove(Object key) throws InvalidKeyException{return M.remove(key);}
		/** Restituisce tutte le chiavi-decorazioni per questa position **/
		public Iterator entries() {return M.keys();}
	}
	
	public class MyVertex extends MyPosition implements Vertex {
		/** Lista di incidenza del vertice. */
		protected List incEdges;
		/** Posizione del vertice nel contenitore V del grafo. */
		protected Position loc;
		/** Costruisce il vertice con il dato elemento. */
		MyVertex(Object o) {
			elem = o;
			incEdges = new NodeList();
		}
		/** Restituisce il grado del vertice */
		public int degree() {
			return incEdges.size();
		}
		/** Restituisce un iteratore sugli archi incidenti su questo vertice. */
		public Iterator incidentEdges() {
			return incEdges.elements();
		}
		/** Inserisce un nuovo arco nella lista di incidenza di questo vertice. */
		public Position insertIncidence(Edge e) {
			incEdges.insertLast(e);
			return incEdges.last();
		}
		/** Rimuove un arco dalla lista di incidenza di questo vertice. */
		public void removeIncidence(Position p) {
			incEdges.remove(p);
		}
		/** Restituisce la posizione di questo vertice nel contenitore V del grafo. */
		public Position location() {
			return loc;
		}
		/** Modifica la posizione di questo vertice nel contenitore V del grafo. */
		public void setLocation(Position p) {
			loc = p;
		}
		/** Uguaglia due vertici **/
		public boolean equals(Object v){
			MyVertex vv = (MyVertex) v;
			if(v == null)
				return false;

			boolean okLista = false, okElem = false;
			
			// controllo lista di incidenza
			if((vv.incEdges.isEmpty() && this.incEdges.isEmpty())
					|| vv.incEdges.equals(this.incEdges))
				okLista = true;
			
			// controllo elemento
			if(vv.element().equals(this.element()))
				okElem = true;
			
			return (okLista && okElem);
		}		
		
		public String toString()
		{
			String str = new String();
			str += this.element().toString();
			return str;
		}
	}
	
	protected class MyEdge extends MyPosition implements Edge {
		
		/** Costruisce un arco con i dati vertici estremit� e il dato elemento. */
		MyEdge (Vertex v, Vertex w, Object o) {
			elem = o;
			endVertices = new MyVertex[2];
			endVertices[0]=(MyVertex) v;
			endVertices[1] = (MyVertex) w;
			Inc = new Position[2];
		}
		
		/** Restituisce i due vertici estremit� dell'arco in un array di due elementi */
		public MyVertex[] endVertices() {
			return endVertices;
		}
		/** Restituisce in un array le posizioni associate all'arco nei contenitori di incidenza dei suoi vertici
		 estremit�. */
		public Position[] incidences() {
			return Inc;
		}
		/** Modifica le posizioni dell'arco nei contenitori di incidenza
		 dei suoi vertici estremit�. */
		public void setIncidences(Position pv, Position pw) {
			Inc[0] = pv;
			Inc[1] = pw;
		}
		/** Restituisce la posizione di questo arco nel contenitore E del grafo. */
		public Position location() {return loc;}
		/** Modifica la posizione di questo arco nel contenitore E del grafo. */
		public void setLocation(Position p){loc = p;}
					
		/** Uguaglia due archi **/
		public boolean equals(Object e){
			MyEdge ee = (MyEdge) e;
			if(e == null)
				return false;

			boolean okCoppia = false, okElem = false;
			
			// controllo coppia vertici
			if(ee.endVertices()[0].element().equals(this.endVertices()[0].element())
					&& ee.endVertices()[1].element().equals(this.endVertices()[1].element()))
				okCoppia = true;
			
			// controllo elemento
			if(ee.element().equals(this.element()))
				okElem = true;
			
			return (okCoppia && okElem);
		}		
		
		public String toString()
		{
			String str = new String();
			str += this.element().toString();
			str += "(" + this.endVertices[0].element() + ", " + this.endVertices[1].element() + ")";
			return str;
		}
		
		/** I vertici alle estremit� dell'arco. */
		protected MyVertex[ ] endVertices;
		/** La posizione dell'arco nel contenitore E degli archi del grafo, che verr� settata*/
		protected Position loc;
		/** Le posizioni associate all�arco e nei due contenitori di incidenza dei
		 vertici estremit� di e. */
		protected Position[ ] Inc;
	}

// CLASSi INTERNE PER IL GRAFO DECORATO
	
	public class DecVertex extends MyVertex implements Vertex{
		public DecVertex(Object o) {
			super(o);
			M = new DoubleLinkedListMap();
		}
		public String toString(){
			String str= "";
			str += super.toString();
			Iterator itK = M.keys();
			Iterator itV = M.values();
			if(M.isEmpty())
				return str+=" 'senza decorazioni'";
			str += ",con decorazioni :";
			while(itK.hasNext())
				str += "("+itK.next()+","+itV.next()+")";
			return str;
		}
	}
	
	public class DecEdge extends MyEdge implements Edge{
		public DecEdge (Vertex v, Vertex w, Object o) {
			super(v, w, o);
			M = new DoubleLinkedListMap();
		}
		public String toString(){
			String str= "";
			str += super.toString();
			Iterator itK = M.keys();
			Iterator itV = M.values();
			if(M.isEmpty())
				return str+=" 'senza decorazioni'";
			str += ",con decorazioni :";
			while(itK.hasNext())
				str += "("+itK.next()+","+itV.next()+")";
			return str;
		}
	}
	//	//////////////////////////////////////////////
		
	//METODI AGGIUNTIVI 
	public String toString(){
		String str = "";
		if(this.isEmpty()){
			try {
				throw new EmptyStackException("Struttura Dati vuota!!");
			} catch (EmptyStackException e) {
				e.printStackTrace();
				return str;
			}
		}
		
		// STAMPA VERTICI:
		int i = 0;
		Iterator it = this.vertices();
		str += new String("Classe AdjacencyListGraph che contiene\ni vertici:\n(");
		while(it.hasNext()){	
			str += (it.next()).toString();
			if(i < this.numVertices()-1)
				str+=",";
			else
				str+=")";
			i++;
		}
		
		// STAMPA ARCHI:
		int j = 0;
		it = this.edges();
		str += new String("\ne gli archi:\n(");
		while(it.hasNext()){	
			str += (it.next()).toString();
			if(j < this.numEdges()-1)
				str+=",";
			else
				str+=")";
			j++;
		}
		return str;
	}
	public boolean equals(Object obj){
		Iterator it, it1;
		Vertex v ,v1;
		boolean var = false;;
		
		AdjacencyListGraph eq = (AdjacencyListGraph)obj;
		
		if(this.isEmpty() && eq.isEmpty())
			return true;
		
		if((this.numEdges()!= eq.numEdges())||((this.numVertices() != eq.numVertices())))
			return false;
		
		it = this.vertices();
		while(it.hasNext())
		{
			var = false;
			v = (Vertex)it.next();
			
			it1 = eq.vertices();
			while(it1.hasNext())
			{
				v1 = (Vertex)it1.next();
				if(v.equals(v1)){
					var = true;
					break;
				}	
			}
			if(!var)
				break;
		}
		
		return var;
	}
	
	public Object clone()
	{
		AdjacencyListGraph cloned = new AdjacencyListGraph();

		// clona e inserisce i vertici
		int i = 0;
		while(i < VList.size())
		{	
			DecVertex v = (DecVertex) this.VList.remove(VList.first()); 
			this.VList.insertLast(v);
			cloned.insertVertex(v.element());
			i++;
		}
		
		// clona e inserisce gli archi
		int j = 0;
		while(j < VList.size())
		{
			DecEdge e = (DecEdge) this.EList.remove(EList.first());
			this.EList.insertLast(e);
			
			// cerco il primo vertice
			DecVertex f = null;
			i = 0;
			while(i < VList.size())
			{
				f = (DecVertex) this.VList.remove(VList.first()); 
				cloned.VList.insertLast(f);
				if(f.element().equals(e.endVertices()[0].element()))
					break;
				i++;
			}
			
			// cerco il secondo vertice
			DecVertex s = null;
			i = 0;
			while(i < VList.size())
			{
				s = (DecVertex) this.VList.remove(VList.first()); 
				cloned.VList.insertLast(s);
				if(s.element().equals(e.endVertices()[1].element()))
					break;
				i++;
			}
			
			cloned.insertEdge(f, s, e.element());
			j++;
		}

		return cloned;
	}
	
	public boolean sameAdj(Vertex v, Vertex w)
	{
		DecVertex vv = this.checkVertex(v);
		DecVertex ww = this.checkVertex(w);
		
		Iterator it = vv.incidentEdges();
		boolean var = false;
		
		while(it.hasNext()){ // per ogni vertice nella lista di adiacenza di vv
			DecEdge myE = (DecEdge)it.next();
			DecVertex oppositeVV = (DecVertex) opposite(vv, myE);
			Iterator it1 = ww.incidentEdges();
			while(it1.hasNext())
			{
				DecEdge myE1 = (DecEdge) it1.next();
				DecVertex oppositeWW = (DecVertex) opposite(ww, myE1);
				if(oppositeVV.equals(oppositeWW))
				{
					var = true;
					break;
				}
			}
		}
		return var;
	}
	
	public boolean HasThreeCycle(Vertex v)
	{
		DecVertex vv =this.checkVertex(v);
		
		Iterator it= vv.incidentEdges();
		boolean var = false;
		while(it.hasNext())
		{
			DecEdge myE = (DecEdge)it.next();
			Vertex myV = this.opposite(v, myE);
			
			Iterator it1= ((DecVertex)myV).incidentEdges();
			while(it1.hasNext())
			{
				DecEdge myE1 = (DecEdge)it1.next();
				Vertex myV1 = this.opposite(myV, myE1);
				if((!myE.equals(myE1)) && this.areAdjacent(v, myV1))
					var = true;
			}
		}
		return var;	
	}
	
	protected List VList; // contenitore per i vertici
	protected List EList; // contenitore per gli archi
}
