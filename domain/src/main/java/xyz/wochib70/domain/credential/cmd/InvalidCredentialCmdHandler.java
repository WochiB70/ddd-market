package xyz.wochib70.domain.credential.cmd;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.wochib70.domain.credential.CredentialRepository;

@RequiredArgsConstructor
@Service
public class InvalidCredentialCmdHandler {

    private final CredentialRepository credentialRepository;


    public void handle(InvalidCredentialCmd cmd) {
        var credential = credentialRepository.findByIdOrThrow(cmd.credentialId());
        credential.invalid();
        credentialRepository.save(credential);
    }
}
