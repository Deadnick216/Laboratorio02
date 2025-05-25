package ui;


//import dao.ConnectionManager;

// import directo a DAO -> violación de la regla “No UI → DAO”
public class UiViolacionDAO {
    public void fallo() {
        //new ConnectionManager().connect();
    }
}
