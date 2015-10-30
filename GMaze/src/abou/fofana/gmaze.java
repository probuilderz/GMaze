package abou.fofana;

import java.util.Random;
import java.util.Scanner;
import java.lang.Math;;

public class gmaze {
	static gmaze myMaze = new gmaze();
	private static int rows;
	private static int col;
	private String[][] maze;
	private static int count1 = 0;
	private static int hit = 0;
	private static int num_intrud = 0;
	private static int mode_int=0;
	private static int inside_wall =0;
	static gmaze myMaze1 = new gmaze(rows, col);
	/*
	 * Constructors and functions
	 */
	private gmaze(){
		rows= 11;
		col = 11;
		this.maze = new String[rows][col];
	}
	
	private gmaze(int r, int c){
		rows = r;
		col = c;
		this.maze = new String[rows][col];
	}
	public String[][] getMaze(){
		return maze;
	}
	public void setMaze(String[][] m){
		this.maze = m;
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
	 * Function: prn()		print the maze
	 * @param maze
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
	 * Function: prn1() , this function is to print the position of walls
	 * @param m			: Array of rows of walls
	 * @param n			: Array of columns of walls
	 * @param r			: The count of walls
	 * Objective:		: for debugging purposes.
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
		System.out.print("Put \"y\" for yes, and \"n\" for no, without the quote :");
		String keep = scan.next().toLowerCase();

		boolean ans = false;
		if (keep.equals("y")|| keep.equals("Y")){
			ans = true;
		}else{
			ans = false;
			System.exit(0);
		}

		System.out.print("Enter the length of the maze (should be a number) :");
		String rt = scan.next();
		boolean check_num= isStringInt(rt);
		int r = 0;

		while(check_num == false){
			System.out.print("Number entered is not an integer");
			System.out.print("\nEnter the length of the maze (should be a number) :");

			rt = scan.next();
			check_num= isStringInt(rt);
		}
		r = Integer.parseInt(rt);

		System.out.print("Enter the percentage of wall inside the maze from 0-100 :");
		String perc = scan.next();
		check_num= isStringInt(perc);
		int percent = 0;

		while(check_num == false){
			System.out.print("Number entered is not an integer");
			System.out.print("\nEnter a correct number e.g. 20, 30 or 80 :");

			perc = scan.next();
			check_num= isStringInt(perc);
		}
		percent = Integer.parseInt(perc);

		System.out.println("\nEnter the scanning mode: ");
		System.out.println("QS: for Quick Scanning ");
		System.out.println("AS: for Average Scanning ");
		System.out.println("FS: for Full Scanning ");

		String mode = scan.next();
		int select_mode;

		switch(mode){
		case "qs":
		case "Qs":
		case "qS":
		case "QS": select_mode = 0;				   
		break;
		case "as":
		case "As":
		case "aS":
		case "AS": select_mode = 1;
		break;
		case "fs":
		case "Fs":
		case "fS":
		case "FS": select_mode = 2;

		break;
		default: select_mode = 0;

		break;

		}
		System.out.println("a:"+select_mode);
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
			if (select_mode ==0){
				mode_int = (int)(num_intrud *(0.1));
				System.out.println("inside wall"+ inside_wall);
				System.out.println(num_intrud);      //For debugging purposes.
				System.out.println(mode_int);
				System.out.println("first");
				isSolve = solveMazeEG(1,1,maze);

			}else if(select_mode ==1){
				mode_int = (int)(num_intrud *(0.2));
				System.out.println("inside wall"+ inside_wall);
				System.out.println(num_intrud);       //For debugging purposes.
				System.out.println(mode_int);
				System.out.println("second");
				isSolve = solveMazeAG(1,1,maze);

			}else{
				mode_int = (int)(num_intrud *(0.4));
				System.out.println("inside wall"+ inside_wall);
				System.out.println(num_intrud);        //For debugging purposes.
				System.out.println(mode_int);
				System.out.println("third");
				isSolve = solveMazeFG(1,1,maze);

			}


			if(isSolve){
				//System.out.println("The maze can be solved after: "+ (count1+1)+ " iterations");
				System.out.println("The system has detected and killed "+ (hit)+ " intruders");
				System.out.println("No more intruder. The system is clean.");
			}else{
				System.out.println("The VRunner has scanned your Maze and killed "+(hit)+" intruders");

				if((num_intrud - hit)>0){
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
			if (keep.equals("y")|| keep.equals("Y")){
				ans = true;
			}else{
				ans = false;
				System.exit(0);
			}

			System.out.print("enter the length of the maze (a number) :");
			rt = scan.next();

			check_num= isStringInt(rt);
			//			r = 0;

			while(check_num == false){
				System.out.print("Number entered is not an integer");
				System.out.print("\nenter the length of the maze (a number) :");

				rt = scan.next();
				check_num= isStringInt(rt);
			}
			r = Integer.parseInt(rt);

			System.out.print("enter the percentage of wall inside the maze from 0-100 :");
			perc = scan.next();
			check_num= isStringInt(perc);
			percent = 0;

			while(check_num == false){
				System.out.print("Number entered is not an integer");
				System.out.print("\nEnter the percentage of wall inside the maze from 0-100 :");

				perc = scan.next();
				check_num= isStringInt(perc);
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
			case "AS": select_mode = 1;
			break;
			case "fs":
			case "Fs":
			case "fS":
			case "FS": select_mode = 2;

			break;
			default: select_mode = 0;

			break;

			}
			System.out.println("b:"+select_mode);
			if(r<0){
				r = r*(-1);
			}else if(r % 2 == 0){
				r++;
			}else if(r == 1){
				r = 3;
			}
			c = r;
		}

	}

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
		
		//gmaze mazeObject1 = new gmaze(m.length,m.length);
//		String[][] maze1 = new String[m.length][m.length];
//		maze1 = m;

		if (row % 2 == 1 && m[row][col-1] != m[row][col+1]) {
			// row is odd; wall separates rooms horizontally
			//fill(row, col-1,mazeObject1.getMaze(), maze1[row][col-1], maze1[row][col+1]);
			fill(row, col-1,gmazeCreator1().getMaze(), m[row][col-1], m[row][col+1]);
			m[row][col] = m[row][col+1];
			gmazeCreator1().setMaze(m);
			//mazeObject1.setMaze(maze1);
			putSquare(row,col,gmazeCreator1().getMaze()," ");
		}
		else if (row % 2 == 0 && m[row-1][col] != m[row+1][col]) {
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
			//mazeObject2.setMaze(maze);
			fill(row+1,col,m,replace,replaceWith);
			fill(row-1,col,m,replace,replaceWith);
			fill(row,col+1,m,replace,replaceWith);
			fill(row,col-1,m,replace,replaceWith);
		}

	}

	private static String[][] makeMaze(int r, int c, int percent_wall){
		//gmaze mazeObject = new gmaze();
		
		gmazeCreator1().setRows(r);
		gmazeCreator1().setCol(c);

//		mazeObject.setRows(r);
//		mazeObject.setCol(c);

		int empty= 0;
		int empty1=0;
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
				//empty1++;
				empty++;
				mazet[i][j] = (- empty) + ""; 
				//mazet[i][j] =String.valueOf(empty1).;          // create a grid, with ASCII characters inside			

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
		//		mazeObject.prn1(wallrow, wallcol, wallCt);   // this display the walls position in the grid. 
		
		gmazeCreator1().setMaze(mazet);
		gmazeCreator1().prn(mazet);
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

		//-----------------
		//		int rt;
		//		//for (int k=(mazeObject.getCol()-2)*(mazeObject.getRows()-2)-1; k>0; k--) {
		//		for (int k=wallCt-1; k>0; k--) {
		//			rt = (int)(Math.random() * (k));  // choose a wall randomly and maybe tear it down
		//			
		//			tearDown(wallrow[rt],wallcol[rt],mazeObject.getMaze());
		//			wallrow[rt] = wallrow[k];
		//			wallcol[rt] = wallcol[k];
		//		}

		mazet = gmazeCreator1().getMaze();

		// Replace all remaining characters that are not walls
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
		//		mazet[5][5] = "T";
		//		mazet[3][7] = "T";
		gmazeCreator1().setMaze(mazet);
		return gmazeCreator1().getMaze();

	}
	/*****************************************************************
	 * Function: solveMazeEG()
	 * @param row	:
	 * @param col	:
	 * @param m		:
	 * @return		:
	 * Goal			:Try to solve the maze by continuing current path from position
		         (row,col).  Return true if a solution is found.  The maze is
		         considered to be solved if the path reaches the lower right cell.
	 */
	private static boolean solveMazeEG(int row, int col, String[][]m) {

		String[][] maze = m;
		gmaze mazeObject2 = new gmaze(m.length, m.length);

		int speedSleep = 500;


		if (maze[row][col] == " ") {
			maze[row][col] = "@";      // add this cell to the path
			count1++;
			putSquare(row,col,m,"@");
			mazeObject2.setMaze(m);
			mazeObject2.prn(mazeObject2.getMaze());
			//			if (row == mazeObject2.getRows()-2 && col == mazeObject2.getCol()-2)
			//				return true;  // path has reached goal

			try { Thread.sleep(speedSleep); }
			catch (InterruptedException e) { }
			if ( (solveMazeEG(row,col+1,m) ) ||     // try to solve maze by extending path
					(solveMazeEG(row+1,col,m))   ||     //    in each possible direction
					(solveMazeEG(row-1,col,m))||
					(solveMazeEG(row,col-1,m))){
				//				count1++;
				return true;

			}


			// maze can't be solved from this cell, so backtrack out of the cell
			maze[row][col] = "0";   // mark cell as having been visited
			//count1++;
			putSquare(row,col,m,"0");
			mazeObject2.setMaze(m);
			//count1++;

			try {
				Thread.sleep(speedSleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}else if (maze[row][col]=="T"){
			maze[row][col] = "@";
			putSquare(row,col,m,"@");
			hit++;			
			mazeObject2.setMaze(m);
			mazeObject2.prn(mazeObject2.getMaze());
			if(hit == num_intrud) return true;
			try { Thread.sleep(speedSleep); }
			catch (InterruptedException e) { }
			if ( (solveMazeEG(row,col+1,m) ) ||     // try to solve maze by extending path
					(solveMazeEG(row+1,col,m))   ||     //    in each possible direction
					(solveMazeEG(row-1,col,m))||
					(solveMazeEG(row,col-1,m))){
				//				count1++;
				return true;

			}
			maze[row][col] = "0";   // mark cell as having been visited
			//count1++;
			putSquare(row,col,m,"0");
			mazeObject2.setMaze(m);
			mazeObject2.prn(mazeObject2.getMaze());
			//			if(hit>1){
			//				maze[row][col] = " ";
			//				putSquare(row,col,m," ");
			//				mazeObject2.setMaze(m);
			//				mazeObject2.prn(mazeObject2.getMaze());
			//				return true;  // path has reached goal
			//			}
		}else if((count1 <=mode_int) && (row !=0)&&(row < mazeObject2.getRows()-1)&&(col!=0)
				&&(col < mazeObject2.getCol()-1)&&(maze[row][col]=="x") ){ //This is to avoid the Runner to be
			maze[row][col] = "@";									// stuck in the first move
			putSquare(row,col,m,"@");
			mazeObject2.setMaze(m);
			mazeObject2.prn(mazeObject2.getMaze());
			if ( (solveMazeEG(row,col+1,m) ) ||     // try to solve maze by extending path
					(solveMazeEG(row+1,col,m))   ||     //    in each possible direction
					(solveMazeEG(row-1,col,m))||
					(solveMazeEG(row,col-1,m))){
				//				count1++;
				return true;

			}
			maze[row][col] = "0";   // mark cell as having been visited
			//count1++;
			putSquare(row,col,m,"0");
			mazeObject2.setMaze(m);
			mazeObject2.prn(mazeObject2.getMaze());
		}


		//count1++;

		return false;
	}
	/*************************************************************************
	 * Function: solveMazeAG(): Enable when user want 50% of the intruders eliminated.
	 * @param row
	 * @param col
	 * @param m
	 * @return
	 */
	private static boolean solveMazeAG(int row, int col, String[][]m) {

		String[][] maze = m;
		gmaze mazeObject2 = new gmaze(m.length, m.length);

		int speedSleep = 500;


		if (maze[row][col] == " ") {
			maze[row][col] = "@";      // add this cell to the path
			count1++;
			putSquare(row,col,m,"@");
			mazeObject2.setMaze(m);
			mazeObject2.prn(mazeObject2.getMaze());
			//			if (row == mazeObject2.getRows()-2 && col == mazeObject2.getCol()-2)
			//				return true;  // path has reached goal

			try { Thread.sleep(speedSleep); }
			catch (InterruptedException e) { }
			if ( (solveMazeAG(row,col+1,m) ) ||     // try to solve maze by extending path
					(solveMazeAG(row+1,col,m))   ||     //    in each possible direction
					(solveMazeAG(row-1,col,m))||
					(solveMazeAG(row,col-1,m))){
				//				count1++;
				return true;

			}


			// maze can't be solved from this cell, so backtrack out of the cell
			maze[row][col] = "0";   // mark cell as having been visited
			//count1++;
			putSquare(row,col,m,"0");
			mazeObject2.setMaze(m);
			//count1++;

			try {
				Thread.sleep(speedSleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}else if (maze[row][col]=="T"){
			maze[row][col] = "@";
			putSquare(row,col,m,"@");
			hit++;			
			mazeObject2.setMaze(m);
			mazeObject2.prn(mazeObject2.getMaze());
			if(hit == num_intrud) return true;
			try { Thread.sleep(speedSleep); }
			catch (InterruptedException e) { }
			if ( (solveMazeAG(row,col+1,m) ) ||     // try to solve maze by extending path
					(solveMazeAG(row+1,col,m))   ||     //    in each possible direction
					(solveMazeAG(row-1,col,m))||
					(solveMazeAG(row,col-1,m))){
				//				count1++;
				return true;

			}
			maze[row][col] = "0";   // mark cell as having been visited
			//count1++;
			putSquare(row,col,m,"0");
			mazeObject2.setMaze(m);
			mazeObject2.prn(mazeObject2.getMaze());
			//			if(hit>1){
			//				maze[row][col] = " ";
			//				putSquare(row,col,m," ");
			//				mazeObject2.setMaze(m);
			//				mazeObject2.prn(mazeObject2.getMaze());
			//				return true;  // path has reached goal
			//			}
		}else if((count1 <= mode_int) &&(row !=0)&&(row < mazeObject2.getRows()-1)&&(col!=0)
				&&(col < mazeObject2.getCol()-1)&&(maze[row][col]=="x") ){  //This section is to knock down the
			maze[row][col] = "@";									// wall depending on the mode selected
			putSquare(row,col,m,"@");
			mazeObject2.setMaze(m);
			mazeObject2.prn(mazeObject2.getMaze());
			if ( (solveMazeAG(row,col+1,m) ) ||     // try to solve maze by extending path
					(solveMazeAG(row+1,col,m))   ||     //    in each possible direction
					(solveMazeAG(row-1,col,m))||
					(solveMazeAG(row,col-1,m))){
				//				count1++;
				return true;

			}
			maze[row][col] = "0";   // mark cell as having been visited
			//count1++;
			putSquare(row,col,m,"0");
			mazeObject2.setMaze(m);
			mazeObject2.prn(mazeObject2.getMaze());
		}


		//count1++;

		return false;
	}

	/*********************************************************************************
	 * Function: solveMazeFG() 
	 * @param row
	 * @param col
	 * @param m
	 * @return
	 */
	private static boolean solveMazeFG(int row, int col, String[][]m) {

		String[][] maze = m;
		gmaze mazeObject2 = new gmaze(m.length, m.length);

		int speedSleep = 500;


		if (maze[row][col] == " ") {
			maze[row][col] = "@";      // add this cell to the path
			count1++;
			putSquare(row,col,m,"@");
			mazeObject2.setMaze(m);
			mazeObject2.prn(mazeObject2.getMaze());
			//		if (row == mazeObject2.getRows()-2 && col == mazeObject2.getCol()-2)
			//			return true;  // path has reached goal

			try { Thread.sleep(speedSleep); }
			catch (InterruptedException e) { }
			if ( (solveMazeFG(row,col+1,m) ) ||     // try to solve maze by extending path
					(solveMazeFG(row+1,col,m))   ||     //    in each possible direction
					(solveMazeFG(row-1,col,m))||
					(solveMazeFG(row,col-1,m))){
				//			count1++;
				return true;

			}


			// maze can't be solved from this cell, so backtrack out of the cell
			maze[row][col] = "0";   // mark cell as having been visited
			//count1++;
			putSquare(row,col,m,"0");
			mazeObject2.setMaze(m);
			//count1++;

			try {
				Thread.sleep(speedSleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}else if (maze[row][col]=="T"){			
			maze[row][col] = "@";
			putSquare(row,col,m,"@");
			hit++;			
			mazeObject2.setMaze(m);
			mazeObject2.prn(mazeObject2.getMaze());
			if(hit == num_intrud) return true;
			try { Thread.sleep(speedSleep); }
			catch (InterruptedException e) { }
			if ( (solveMazeFG(row,col+1,m) ) ||     // try to solve maze by extending path
					(solveMazeFG(row+1,col,m))   ||     //    in each possible direction
					(solveMazeFG(row-1,col,m))||
					(solveMazeFG(row,col-1,m))){
				//			count1++;
				return true;

			}
			maze[row][col] = "0";   // mark cell as having been visited
			//count1++;
			putSquare(row,col,m,"0");
			mazeObject2.setMaze(m);
			mazeObject2.prn(mazeObject2.getMaze());
			//		if(hit>1){
			//			maze[row][col] = " ";
			//			putSquare(row,col,m," ");
			//			mazeObject2.setMaze(m);
			//			mazeObject2.prn(mazeObject2.getMaze());
			//			return true;  // path has reached goal
			//		}
		}else if((count1 <= mode_int) && (row !=0)&&(row < mazeObject2.getRows()-1)&&(col!=0)
				&&(col < mazeObject2.getCol()-1)&&(maze[row][col]=="x") ){  //Knock down the wall depending
			maze[row][col] = "@";									// on the selecting mode
			putSquare(row,col,m,"@");
			mazeObject2.setMaze(m);
			mazeObject2.prn(mazeObject2.getMaze());
			if ( (solveMazeFG(row,col+1,m) ) ||     // try to solve maze by extending path
					(solveMazeFG(row+1,col,m))   ||     //    in each possible direction
					(solveMazeFG(row-1,col,m))||
					(solveMazeFG(row,col-1,m))){
				//			count1++;
				return true;

			}
			maze[row][col] = "0";   // mark cell as having been visited
			//count1++;
			putSquare(row,col,m,"0");
			mazeObject2.setMaze(m);
			mazeObject2.prn(mazeObject2.getMaze());
		}


		//count1++;

		return false;
	}

}
