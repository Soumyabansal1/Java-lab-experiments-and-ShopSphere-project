public class Cmain {

    public static void main(String[] args) {

        Views view = new Views();
        Models model = new Models();

        new Controller(view, model);
    }
}

