import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Mobile {
	
	private enum MobileType { SIMPLE, COMPOSITE }
	private MobileType type;
	private float weight;                   // Simple
	private float leftLength, rightLength;  // Composite
	private Mobile left, right;
	
	public Mobile( float weight ) {
		type = MobileType.SIMPLE;
		this.weight = weight;
		left = null;
		right = null;
		
	}
	
	public Mobile( Mobile left, float leftLength, Mobile right, float rightLength ) {
		type = MobileType.COMPOSITE;
		this.left = left;
		this.right = right;
	    this.leftLength = leftLength;
	    this.rightLength = rightLength;	
	}
	
	// Return the total mass of the mobile
	public float getWeight() {
		if ( isSimple() )
			return weight;
		else
			return left.getWeight() + right.getWeight();
	}  
	
	// Return the maximum height of the mobile
	public int getHeight() {
	    if( isSimple() )
	    	return 1;
	    else
	    	return 1 + Math.max(left.getHeight(), right.getHeight());
	}  
	
	// Print the leaves of the mobile
	public void flatten()  {
	      if( isSimple() )
	    	  System.out.print(weight + " ");
	      else {
	    	  left.flatten();
	    	  right.flatten();
	      }
	}  
	
//	Print a structured view of the mobile
	public void prettyPrint() {
	      if( isSimple() )
	    	  System.out.printf("(%.0f)", weight);
	      else {
	    	  System.out.printf("[");
	    	  right.prettyPrint();
	    	  System.out.printf(",%.0f,", rightLength);
	    	  left.prettyPrint();
	    	  System.out.printf(",%.0f", leftLength);
	    	  System.out.printf("]");
	      }
	}
	
// Determine if the mobile is balanced
	public boolean isBalanced() {
		final double eps = 0.000001;
		return isSimple() ||
		    left.isBalanced() && right.isBalanced() &&
		        Math.abs( leftLength * left.getWeight() -
				rightLength * right.getWeight() ) < eps;
	}   

// Determine if two mobiles are equal	
	public boolean equals( Object rhs ) {
		
		// False if rhs is not a Mobile
		if( !(rhs instanceof Mobile) )
			return false;

		Mobile m = (Mobile) rhs;
		// False if rhs not the same type
		if( isSimple() != m.isSimple() )
			return false;
		
		final double eps = 0.000001;
		if( isSimple() )
			return Math.abs(getWeight() - m.getWeight()) < eps;
		else
			return (leftLength - m.leftLength) < eps
					&& (rightLength - m.rightLength) < eps
					&& left.equals(m.left) && right.equals(m.right);	
	}
	
	public int hashCode() {
		if( isSimple() )
			return 17 * (int)weight;
		else {
			return    13 * (int)rightLength
					+ right.hashCode()
					+ 31 * (int)leftLength
					+ left.hashCode();
		}
	}
	
//	Return a clone of this mobile
	public Mobile clone() {
         if( isSimple() )
        	 return new Mobile(weight);
         else {
        	 return new Mobile(left.clone(), leftLength, right.clone(), rightLength);
         }
	}
	
// Change this mobile to its mirror image
	public void mirror() {
         if( isSimple() )
        	 return; // Do nothing for a simple type
         else {
        	 // Mirror the sub-mobiles
        	 right.mirror();
        	 left.mirror();
        	 
        	 // Switch right to left and left to right
        	 Mobile tmpMobile = right;
        	 float  tmpLength = rightLength;
        	 right = left; rightLength = leftLength;
        	 left = tmpMobile; leftLength = tmpLength;
         }
	}
	
	private boolean isSimple() { 
		return type == MobileType.SIMPLE; 
	}
	
	public static void main(String[] args) {
		Mobile  m1 = new Mobile( 1 ),
		        m2 = new Mobile( new Mobile( 2 ), 6,  new Mobile( 3 ), 4 ),
		        m = new Mobile( m1, 10, m2, 2 );
	
		System.out.println("Total mass: " + m.getWeight() );

		System.out.println("Height:     " + m.getHeight() );
		m.flatten(); System.out.println();
		m.prettyPrint(); System.out.println();
		if ( m.isBalanced() )
			System.out.println("Balanced!");
		else
			System.out.println("Not balanced!");
	
		Mobile m22 = new Mobile( new Mobile( 2 ), 6,  new Mobile( 3 ), 4 ),
		       m3 = new Mobile( m1, 10, m22, 2 );
		if ( m.equals(m3) )
			System.out.println("Equal!");		// They should be!
		else
			System.out.println("Not equal!");
	
		Mobile c = m.clone();
		if ( c.equals(m) )
			System.out.println("Equal!");		// They should be!
		else
			System.out.println("Not equal!");

		if ( c == m )
			System.out.println("Identical!");	// They should definately not!
		else
			System.out.println("Not identical!");

		m.mirror();
		m.prettyPrint(); System.out.println();
		m.mirror();
		m.prettyPrint(); System.out.println();

	}
}
