package ChatClient;

public class test {

	public test() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		ClientDatabase.CreateDatabase();
		System.out.println(ClientDatabase.CheckIfDBExist());
	}

}
