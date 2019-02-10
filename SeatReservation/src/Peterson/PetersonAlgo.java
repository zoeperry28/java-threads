
package Peterson;
import java.lang.Boolean;

//Part 3 — Peterson's Method
//
//Finally, write a solution using Peterson's Method. In this case, just use 2 booking offices. 
//This is quite difficult! 

public class PetersonAlgo {

	public static Boolean[] algo(Boolean[] flag, int turn){

	flag[1] = true;
	turn = 1;
	while(flag[1] && turn==1) {
		 
	}
	flag[0] = false;
	
	flag[0] = true;
	turn = 0;
	while(flag[0] && turn==0) {
		//access the resource
		//acquire release
	}
	flag[1] = false;
	return flag;
	

}
	public static void main (String[] args) {
		Boolean[] flag = new Boolean[2];
		int turn = -1;
		PetersonAlgo.algo(flag, turn);

	}
}
