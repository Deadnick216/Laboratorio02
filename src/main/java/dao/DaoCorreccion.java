package dao;

// DAO → DAO (OK), no DAO → UI ni DAO → Service
public class DaoCorreccion {
    public void ejecuta() {
        new UserRepository().save("Anto");
    }
}
