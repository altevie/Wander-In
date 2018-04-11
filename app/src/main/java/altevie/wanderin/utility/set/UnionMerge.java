package altevie.wanderin.utility.set;
//ridefinizione
import altevie.wanderin.utility.sequence.Sequence;

public class UnionMerge extends Merge {
	protected void aIsLess(Object a, Sequence C) {
		C.insertLast(a); // add a
	}
	protected void bothAreEqual(Object a, Object b, Sequence C) {
		C.insertLast(a); // add a (but not its duplicate b)
	}
	protected void bIsLess(Object b, Sequence C) {
		C.insertLast(b); // add b
	}

}