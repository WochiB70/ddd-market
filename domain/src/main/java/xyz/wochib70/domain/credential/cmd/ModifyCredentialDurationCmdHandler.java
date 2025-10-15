package xyz.wochib70.domain.credential.cmd;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import xyz.wochib70.domain.credential.Credential;
import xyz.wochib70.domain.credential.CredentialRepository;

@RequiredArgsConstructor
@Service
public class ModifyCredentialDurationCmdHandler {

    private final CredentialRepository credentialRepository;

    private final ApplicationEventPublisher eventPublisher;

    public void handle(ModifyCredentialDurationCmd cmd) {
        Credential credential = credentialRepository.findByIdOrThrow(cmd.credentialId());
        credential.modifyDuration(cmd.duration());
        credentialRepository.update(credential);
        credential.getEvents().forEach(eventPublisher::publishEvent);
    }
}
