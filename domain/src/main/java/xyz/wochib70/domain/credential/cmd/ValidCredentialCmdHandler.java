package xyz.wochib70.domain.credential.cmd;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import xyz.wochib70.domain.credential.CredentialRepository;

@RequiredArgsConstructor
@Service
public class ValidCredentialCmdHandler {

    private final CredentialRepository credentialRepository;

    private final ApplicationEventPublisher eventPublisher;

    public void handle(ValidCredentialCmd cmd) {
        var credential = credentialRepository.findByIdOrThrow(cmd.credentialId());
        credential.valid();
        credentialRepository.update(credential);
        credential.getEvents().forEach(eventPublisher::publishEvent);
    }
}
