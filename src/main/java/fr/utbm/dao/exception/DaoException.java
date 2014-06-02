package fr.utbm.dao.exception;

public class DaoException extends RuntimeException {

  public DaoException() {
  }

  public DaoException(String msg) {
    super(msg);
  }

  public DaoException(Throwable cause) {
    super(cause);
  }

  public DaoException(String msg, Throwable cause) {
    super(msg, cause);
  }
}
