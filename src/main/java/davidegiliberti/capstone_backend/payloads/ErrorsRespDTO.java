package davidegiliberti.capstone_backend.payloads;

import java.time.LocalDateTime;

public record ErrorsRespDTO(String msg, LocalDateTime timeStamp) {
}
