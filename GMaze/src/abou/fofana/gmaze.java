package abou.fofana;

import java.util.Random;
import java.util.Scanner;
import java.lang.Math;;

/***************************************************************************************
 * <h1>Function: {@link gmaze}</h1>
 * @author Fofana, Bui, Kozak
 * @version 1.5
 * @Objective:
 *
 */

public class gmaze {
	static gmaze myMaze = new gmaze();
	private static int rows;
	private static int col;
	private static String[][] maze;
	private static int count1 = 0;
	private static int hit = 0;
	private static int num_intrud = 0;
	private static int destroy_intrud = 0;
	private static int mode_int=0;
	private static int inside_wall =0;
	static gmaze myMaze1 = new gmaze(rows, col, maze);
	/*
	 * Constructors and functions
	 */
	private gmaze(){
		rows= 11;
		col = 11;
		maze = new String[rows][col];
	}
	/*
	 * Overloaded constructor
	 */
	
	private gmaze(int r, int c, String[][] ma){
		rows = r;
		col = c;
		maze = ma;
	}
	public String[][] getMaze(){
		return maze;
	}
	public void setMaze(String[][] m){
		maze = m;
	}
	public int getRows(){
		return rows;
	}
	public int getCol(){
		return col;
	}
	public void setRows(int r){
		rows = r;
	}
	public void setCol(int c){
		col = c;
	}

	/****************************************
	 * Function: prn()		print a double string array
	 * @param maze
	 * @see {@link prn1}
	 */
	public void prn (String[][] maze){
		for(int i = 0; i< getRows(); i++){
			System.out.println();
			for(int j = 0; j< getCol(); j++)
				System.out.print(maze[i][j]);
		}

		System.out.println();	
	}

	/****************************************
	 * Function: prn1() , 
	 * @param m			: Array of rows of walls
	 * @param n			: Array of columns of walls
	 * @param r			: The count of walls
	 * @author Fofana	
	 *
	 * @Note: this function is to print the position of walls. For debugging purposes.
	 */
	public void prn1 (int[] m,int[] n,  int r){
		for(int i = 0; i< r; i++){

			System.out.print(" "+m[i]);
		}
		System.out.println();
		for(int i = 0; i< r; i++){

			System.out.print(" "+n[i]);
		}
		System.out.println();
	}
	
	public static gmaze gmazeCreator(){
		return myMaze;
	}
	public static gmaze gmazeCreator1(){
		return myMaze1;
	}

	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);

		System.out.print("Do you want to play?\n");
		System.out.println("Put \"y\" for yes, and \"n\" for no, without the quote :");
		String keep = scan.next().toLowerCase();

		boolean ans = false;
		boolean test = false;
		if((keep.equals("y"))||(keep.equals("n"))){
			test = true;
		}
		
		while(!test){
			System.out.print("Put \"y\" for yes, and \"n\" for no, without the quote :");
			keep = scan.next().toLowerCase();
			if((keep.equals("y"))||(keep.equals("n"))){
				test = true;
			}else{
				test= false;
			}
				
		}
		
		if (keep.equals("y")|| keep.equals("Y")){
			ans = true;
		}else{
			ans = false;
			System.exit(0);
		}

		System.out.print("Enter the length of the maze (should be a number) :");   //The maze is a square maze 
		String rt = scan.next();													// size = Length x Length
		boolean check_num= isStringInt(rt);							//Check if the length is an integer
		int r = 0;

		while(check_num == false){
			System.out.print("Number entered is not an integer");
			System.out.print("\nEnter the length of the maze (should be a number) :");

			rt = scan.next();
			check_num= isStringInt(rt);
		}
		r = Integer.parseInt(rt);
		r = toPositiveInteger(r);					// the length will be turned into positive number

		System.out.print("Enter the percentage of wall inside the maze from 0-100 :");
		String perc = scan.next();
		check_num = checkValue(perc);
		//check_num= isStringInt(perc);
		
		int percent = 0;

		while(!check_num){
			System.out.print("Number entered is not an integer or it is greater than 100");
			System.out.print("\nEnter a correct number e.g. 20, 30 or 80 :");

			perc = scan.next();
			check_num= checkValue(perc);
		}
		
		percent = Integer.parseInt(perc);

		System.out.println("\nEnter the scanning mode: ");
		System.out.println("QS: for Quick Scanning ");
		System.out.println("AS: for Average Scanning ");
		System.out.println("FS: for Full Scanning ");
		System.out.println("Note: All other scanning modes have been linked to the QS scan.");

		String mode = scan.next();
		int select_mode;
		/*
		 * This section was intended to target different solveMaze function
		 * based on the select_mode, which is determined by the scan mode QS, AS, FS
		 * In this final implementation those functions have become obsolete due to the
		 * fact that the developing team suggested only one scan, the QS.
		 * All other scans are now linked to QS.
		 */

		switch(mode){
		case "qs":
		case "Qs":
		case "qS":
		case "QS": select_mode = 0;				   
		break;
		case "as":
		case "As":
		case "aS":
		case "AS": select_mode = 0;   //no other mode needed. linked to the QS select_mode ==0
		break;
		case "fs":
		case "Fs":
		case "fS":
		case "FS": select_mode = 0;  // linked to the QS select_mode ==0

		break;
		default: select_mode = 0;
		break;
		}
		
		if(r <0){
			r = r *(-1);
		}else if(r % 2 == 0){
			r++;
		}else if(r == 1){
			r = 3;
		}
		int c = r;

		String[][] maze = new String[r][c];
		//String[][] previous_maze = new String[r][c];   //finding a way to store previous maze for re-scanning 
		while(ans){
			boolean isSolve = false;

			maze = makeMaze(r,c, percent);
			System.out.println("You have " + num_intrud + " intruders in the maze"); 
			System.out.println("How many intruders are you willing to destroy?");
			
			String intru = scan.next();
			check_num= isStringInt(intru);

			while(check_num == false){
				System.out.print("Enter a number please:");

				intru = scan.next();
				check_num= isStringInt(intru);
			}
			destroy_intrud= Integer.parseInt(intru);
			destroy_intrud = toPositiveInteger(destroy_intrud);
			if (destroy_intrud > num_intrud)
				destroy_intrud = num_intrud;        // Set the value of destroy_intrud to 
													// num_intrud if greater.
		
				isSolve = solveMazeEG(1,1,maze,select_mode);


			if(isSolve){
				//System.out.println("The maze can be solved after: "+ (count1+1)+ " iterations");
				System.out.println("The system has detected and killed "+ (hit)+ " intruders");
				System.out.println("No more intruder. The system is clean.");
			}else{
				System.out.println("The VRunner has scanned your Maze and killed "+(hit)+" intruders");
				if(hit == destroy_intrud){
					System.out.println("The goal has been reached");
				}else if((num_intrud - hit)>0){				
					
					System.out.println((num_intrud - hit)+ " intruders still remain in the system. ");
					System.out.println("The remaining intruders are unreachable");
					System.out.println("Reduce the percentage of walls for better performance.");
				}else System.out.println("No more intruder. The system is clean.");

				//System.out.println("The maze has no solving path");
			}
			num_intrud = 0;
			mode_int=0;
			hit=0;
			count1=0;
			inside_wall =0;

			System.out.print("Do you want to play again? \n");
			System.out.print("Put \"y\" for yes, and \"n\" for no, without the quote :");
			keep = scan.next().toLowerCase();
			
			ans = false;
			test = false;
			if((keep.equals("y"))||(keep.equals("n"))){
				test = true;
			}
			
			while(!test){
				System.out.print("Put \"y\" for yes, and \"n\" for no, without the quote :");
				keep = scan.next().toLowerCase();
				if((keep.equals("y"))||(keep.equals("n"))){
					test = true;
				}else{
					test= false;
				}
					
			}
			
			if (keep.equals("y")|| keep.equals("Y")){
				ans = true;
			}else{
				ans = false;
				System.exit(0);
			}


			System.out.print("Enter the length of the maze (a number) :");
			rt = scan.next();

			check_num= isStringInt(rt);
			//			r = 0;

			while(check_num == false){
				System.out.print("Number entered is not an integer");
				System.out.print("\nEnter the length of the maze (a number) :");

				rt = scan.next();
				check_num= isStringInt(rt);
			}
			r = Integer.parseInt(rt);
			r= toPositiveInteger(r);

			System.out.print("Enter the percentage of wall inside the maze from 0-100 :");
			perc = scan.next();
			check_num = checkValue(perc);
			
			percent = 0;

			while(!check_num){
				System.out.print("Number entered is not an integer or it is greater than 100");
				System.out.print("\nEnter a correct number e.g. 20, 30 or 80 :");

				perc = scan.next();
				check_num= checkValue(perc);
			}
			
			percent = Integer.parseInt(perc);


			System.out.println("\nEnter the scanning mode: ");
			System.out.println("QS: for Quick Scanning ");
			System.out.println("AS: for Average Scanning ");
			System.out.println("FS: for Full Scanning ");

			mode = scan.next();

			switch(mode){
			case "qs":
			case "Qs":
			case "qS":
			case "QS": select_mode = 0;				   
			break;
			case "as":
			case "As":
			case "aS":
			case "AS": select_mode = 0;
			break;
			case "fs":
			case "Fs":
			case "fS":
			case "FS": select_mode = 0;

			break;
			default: select_mode = 0;

			break;

			}
			if(r<0){
				r = r*(-1);
			}else if(r % 2 == 0){
				r++;
			}else if((r == 1)||(r ==0)){
				r = 3;
			}
			c = r;
		}

	}
	/**********************************************************************************
	 * 
	 * @param perc
	 * @return	True
	 */
	private static boolean checkValue(String perc) {
		// TODO Auto-generated method stub
		int correct_num = 0;
		boolean check_value = isStringInt(perc);
		if(check_value){
			correct_num = Integer.parseInt(perc);
			correct_num =  toPositiveInteger(correct_num);
			if (correct_num > 100){
				check_value  = false;
			}else{
				check_value = true;
			}
			
		}	
		return check_value;
	}

	/******************************************************************************
	 * toPositiveInteger
	 * @param r
	 * @return Return a positive integer
	 */
	private static int toPositiveInteger(int r) {
		// TODO Auto-generated method stub
		if(r < 0)
			return (-r);
		return r;
	}

	/*******************************************************************************
	 * Function <b>isStringInt</b>
	 * <hr>
	 * <h3>private static boolean isStringInt(String rt)</h3>
	 * @param 
	 *  rt - a String containing the int representation to be parsed
	 * @return
	 * Goal: Integer Data handler. Return a True when the value is an integer. Otherwise,
	 *       the function return False.
	 */

	private static boolean isStringInt(String rt) {
		try
		{
			Integer.parseInt(rt);
			return true;
		} catch (NumberFormatException ex)
		{
			return false;
		}

	}

	/***********************************************************************
	 * Function		: tearDown()
	 * @param row	:
	 * @param col	:
	 * @param m		:
	 */
	private static void tearDown(int row, int col, String[][] m) {
	
		if (row % 2 == 1 && (!m[row][col-1].equals(m[row][col+1]))) {
			// row is odd; wall separates rooms horizontally
			fill(row, col-1,gmazeCreator1().getMaze(), m[row][col-1], m[row][col+1]);
			m[row][col] = m[row][col+1];
			gmazeCreator1().setMaze(m);
			putSquare(row,col,gmazeCreator1().getMaze()," ");
		}
		else if (row % 2 == 0 && (!m[row-1][col].equals(m[row+1][col]))) {
			// row is even; wall separates rooms vertically
			fill(row-1, col,gmazeCreator1().getMaze(), m[row-1][col], m[row+1][col]);
			m[row][col] = m[row+1][col];
			gmazeCreator1().setMaze(m);
			putSquare(row,col,gmazeCreator1().getMaze()," ");		
		}

	}

	private static void putSquare(int row, int col,String[][] m, String c) {

		//String[][]maze= m;
		//gmaze mazeObject1 = new gmaze(maze.length, maze.length);
		m[row][col] = c;
		gmazeCreator1().setMaze(m);
		//mazeObject1.setMaze(maze);
		//count1++;

	}

	private static void fill(int row, int col, String[][] m,String replace, String replaceWith) {

		//String[][]maze = m;
		//gmaze mazeObject2 = new gmaze(maze.length, maze.length);

		if (m[row][col] ==replace) {
			m[row][col] = replaceWith;
			gmazeCreator1().setMaze(m);
			fill(row+1,col,m,replace,replaceWith);
			fill(row-1,col,m,replace,replaceWith);
			fill(row,col+1,m,replace,replaceWith);
			fill(row,col-1,m,replace,replaceWith);
		}

	}
	
	/************************************************************************************
	 * <h3>makeMaze</h3>
	 * @param r - Represent the row of the maze
	 * @param c - Represent the column of the maze
	 * @param percent_wall - The percentage of wall
	 * @return
	 * A square maze of size r x c 
	 */

	private static String[][] makeMaze(int r, int c, int percent_wall){
		
		gmazeCreator1().setRows(r);
		gmazeCreator1().setCol(c);

		int empty= 0;

		/*Create a square maze where everything is wall */
		int i=0,j=0;
		//int emptyCt = 0; // number of rooms
		int wallCt = 0;  // number of walls
		int[] wallrow = new int[(gmazeCreator1().getRows()*gmazeCreator1().getCol())/2];  // position of walls between rooms
		int[] wallcol = new int[(gmazeCreator1().getRows()*gmazeCreator1().getCol())/2];

		String[][] mazet = new String[r][c];
		for(i = 0; i< gmazeCreator1().getRows(); i++)
			for(j = 0; j< gmazeCreator1().getCol(); j++)
				mazet[i][j] = "x";        // every space is a wall

		/* Initialize the starting point */

		for( i = 1; i<gmazeCreator1().getRows()-1; i+=2)
			for(j = 1; j<gmazeCreator1().getCol() -1; j+=2){
				
				empty++;
				mazet[i][j] = (- empty) + "";  // create a grid, with ASCII characters inside						    	

				if (i < gmazeCreator1().getRows()-2) {  // record info about wall below this room
					wallrow[wallCt] = i+1;
					wallcol[wallCt] = j;
					wallCt++;
				}
				if (j < gmazeCreator1().getCol()-2) {  // record info about wall to right of this room
					wallrow[wallCt] = i;
					wallcol[wallCt] = j+1;
					wallCt++;
				}
			}
		//		mazeObject.prn1(wallrow, wallcol, wallCt);   // this display bottom, right and left side walls position of the grid. 
		
		gmazeCreator1().setMaze(mazet);
		//gmazeCreator1().prn(mazet);
		/* Create randoms wall*/
		Random rand = new Random();
		int wall_destroy=0;
		// Randomly create wall based on user's level of difficulty input

		for (int k=wallCt-1; k>0; k--) {
			int rand_value = rand.nextInt(100);
			
			if(rand_value > percent_wall ){
				tearDown(wallrow[k],wallcol[k],gmazeCreator1().getMaze());
				//				wallrow[rt] = wallrow[k];
				//				wallcol[rt] = wallcol[k];
				wall_destroy++;
			}

			inside_wall = wallCt - wall_destroy;
		}

		mazet = gmazeCreator1().getMaze();

		// Replace all remaining characters that are not walls into space character " "
		for(i =1; i<gmazeCreator1().getRows()-1; i++)
			for(j =1; j<gmazeCreator1().getRows()-1; j++){
				int wrap = 0;
				boolean wraping = isStringInt(mazet[i][j]);
				
				if(wraping){
					wrap = Integer.parseInt(mazet[i][j]);
					if(wrap < 0)
					mazet[i][j] = " ";
				}
				
			}
		mazet[1][0] = "@";                 // position of the object
		Random intruders = new Random();
		for(i =2; i<gmazeCreator1().getRows()-1; i++)   // Injection of intruders at random level 
			for(j =2; j<gmazeCreator1().getRows()-1; j++){
				if(intruders.nextInt(10)>6){
					mazet[i][j] = "T";
					num_intrud++;
				}

			}
		gmazeCreator1().setMaze(mazet);
		return gmazeCreator1().getMaze();

	}
	/*****************************************************************
	 * Function: solveMazeEG()
	 * @param row	: row
	 * @param col	: column
	 * @param m		: maze
	 * @return		 {@literal true when search is completed based on the number of  target specified, 
	 * 				  or false when all the targets hasn't been reached.}
	 * @Note	  	: {@link row},  {@link col} are the walls or rooms position that the Navigator 
	 * 				  check before moving.
	 */
	private static boolean solveMazeEG(int row, int col, String[][]m, int mode) {

		int speedSleep = 500;
		if(mode == 0){
			mode_int = (int)(num_intrud *(0.1));
		}else if(mode == 1){
			mode_int = (int)(num_intrud *(0.2));
		}else{
			mode_int = (int)(num_intrud *(0.3));
		}
		
		
			if ((m[row][col] == " ") &&(hit <destroy_intrud)) {
				m[row][col] = "@";      // add this cell to the path
				count1++;
				putSquare(row,col,m,"@");
				gmazeCreator().setMaze(m);
				//mazeObject2.setMaze(m);
				gmazeCreator().prn(gmazeCreator().getMaze());
				//mazeObject2.prn(mazeObject2.getMaze());
				//			if (row == mazeObject2.getRows()-2 && col == mazeObject2.getCol()-2)
				//				return true;  // path has reached goal
	
				try { Thread.sleep(speedSleep); }
				catch (InterruptedException e) { }
				if ( (solveMazeEG(row,col+1,m,mode) ) ||     // try to solve maze by extending path
						(solveMazeEG(row+1,col,m,mode))   ||     //    in each possible direction
						(solveMazeEG(row-1,col,m,mode))||
						(solveMazeEG(row,col-1,m,mode))){
					//				count1++;
					return true;
	
				}
	
	
				// maze can't be solved from this cell, so backtrack out of the cell
				maze[row][col] = ".";   // mark cell as having been visited
				//count1++;
				putSquare(row,col,m,".");
				gmazeCreator().setMaze(m);
				//mazeObject2.setMaze(m);
				gmazeCreator().prn(gmazeCreator().getMaze());
				//mazeObject2.prn(mazeObject2.getMaze());
				//count1++;
	
				try {
					Thread.sleep(speedSleep);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}else if ((maze[row][col]=="T") && (hit < destroy_intrud)){
				maze[row][col] = "@";
				putSquare(row,col,m,"@");
				hit++;			
				//mazeObject2.setMaze(m);
				gmazeCreator().setMaze(m);
				gmazeCreator().prn(gmazeCreator().getMaze());
				//mazeObject2.prn(mazeObject2.getMaze());
	//			if(hit == num_intrud){
	//				
	//				
	//				return true;
	//			}
				try { Thread.sleep(speedSleep); }
				catch (InterruptedException e) { }
				if ( (solveMazeEG(row,col+1,m,mode) ) ||     // try to solve maze by extending path
						(solveMazeEG(row+1,col,m,mode))   ||     //    in each possible direction
						(solveMazeEG(row-1,col,m,mode))||
						(solveMazeEG(row,col-1,m,mode))){
					//				count1++;
					return true;
	
				}
				maze[row][col] = ".";   // mark cell as having been visited
				//count1++;
				putSquare(row,col,m,".");
				gmazeCreator().setMaze(m);
				gmazeCreator().prn(gmazeCreator().getMaze());

			}else if((count1 <=mode_int) && (row !=0)&&(row < gmazeCreator().getRows()-1)&&(col!=0)
					&&(col < gmazeCreator().getCol()-1)&&(maze[row][col]=="x") &&(hit <destroy_intrud)){ //This is to avoid the Runner to be
				maze[row][col] = "@";									// stuck in the first move
				putSquare(row,col,m,"@");
				gmazeCreator().setMaze(m);
				gmazeCreator().prn(gmazeCreator().getMaze());
//				mazeObject2.setMaze(m);
//				mazeObject2.prn(mazeObject2.getMaze());
				if ( (solveMazeEG(row,col+1,m,mode) ) ||     // try to solve maze by extending path
						(solveMazeEG(row+1,col,m,mode))   ||     //    in each possible direction
						(solveMazeEG(row-1,col,m,mode))||
						(solveMazeEG(row,col-1,m,mode))){
					//				count1++;
					return true;
	
				}
				maze[row][col] = ".";   // mark cell as having been visited
				//count1++;
				putSquare(row,col,m,".");
				gmazeCreator().setMaze(m);
				gmazeCreator().prn(gmazeCreator().getMaze());

			}
		//count1++;

		return false;
	}

}
