package xyz.wochib70.domain.credential.cmd;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.wochib70.domain.credential.CredentialRepository;

@RequiredArgsConstructor
@Service
public class ValidCredentialCmdHandler {

    private final CredentialRepository credentialRepository;


    public void handle(ValidCredentialCmd cmd) {
        var credential = credentialRepository.findByIdOrThrow(cmd.credentialId());
        credential.valid();
        credentialRepository.save(credential);
    }
}
