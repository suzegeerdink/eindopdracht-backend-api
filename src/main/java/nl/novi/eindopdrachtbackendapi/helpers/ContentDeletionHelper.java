package nl.novi.eindopdrachtbackendapi.helpers;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
public class ContentDeletionHelper {

    public void deleteAndHandleIntegrity(Runnable deleteAction) {
        try {
            deleteAction.run();
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete content: content is still referenced by one or more loans or watch-history entries");
        }
    }
}
