package davidegiliberti.capstone_backend.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
    public NotFoundException(UUID id) {
        super("L'ID: " + id + " non è presente");
    }

    public NotFoundException(String msg) {
        super(msg);
    }
}
