package altevie.wanderin.utility.set;
//ridefinizione
import altevie.wanderin.utility.sequence.Sequence;

public class SubtractMerge extends Merge {
	protected void aIsLess(Object a, Sequence C) {
		C.insertLast(a); // aggiungo a: devo difatti considerare solo gli
		//elementi di A che non stanno in B
	}
	protected void bothAreEqual(Object a, Object b, Sequence C) { }
	//se sono uguali, non devo considerarli
	protected void bIsLess(Object b, Sequence C) { }
	//non copia �ora� a, perch� segue l�ordinamento�
}