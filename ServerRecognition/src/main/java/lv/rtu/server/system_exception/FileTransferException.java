package lv.rtu.server.system_exception;

public class FileTransferException extends Exception {

	private static final long serialVersionUID = 1L;

	public FileTransferException() {
		super();
	}

	public FileTransferException(String message) {
		super(message);
	}

	public FileTransferException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileTransferException(Throwable cause) {
		super(cause);
	}
}
