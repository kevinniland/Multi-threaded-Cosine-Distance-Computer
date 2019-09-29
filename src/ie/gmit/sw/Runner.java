package ie.gmit.sw;

/**
 * @author Kevin Niland
 * @version 1.0
 * @since 1.8
 * 
 * Runs the application. Creates a new instance of 'Menu' and calls the 'run()' function from the Menu class
 *
 */
public class Runner {
	public static void main(String[] args) throws InterruptedException {
		Menu menu = new Menu();
		
		menu.run();
	}
}