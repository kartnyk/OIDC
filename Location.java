import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;

public class LogoutActionTest {

    @Mock
    private AuditSessionPersistor auditSessionPersistor;

    @Mock
    private AuthStateMachineEngine authStateMachineEngine;

    @Mock
    private AuthConfigUtil configData; // Mock configData here

    @Mock
    private AuthStateContext authStateContext;

    @Mock
    private AuthRequestContext requestContext;

    @Mock
    private AuthApplication authApp;

    @InjectMocks
    private LogoutAction logoutAction;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        // Inject mock configData into the superclass (BaseAuthAction)
        injectPrivateField(logoutAction, "configData", configData);
    }

    @Test
    public void testInitLogoutSession() throws AuthException {
        // Mock behavior of dependencies
        when(authStateMachineEngine.getAuthStateContext()).thenReturn(authStateContext);
        when(authStateContext.getAuthApplication()).thenReturn(authApp);
        when(authApp.getName()).thenReturn("TestApp");
        when(configData.getFeatureConfigDataList("TestApp", false)).thenReturn(new HashMap<>());

        // Execute the method under test
        logoutAction.initLogoutSession();

        // Verify interactions
        verify(authStateMachineEngine).getAuthStateContext();
        verify(auditSessionPersistor).setRequestContext(any(AuthRequestContext.class));
    }

    // Utility method to inject private fields using reflection
    private void injectPrivateField(Object target, String fieldName, Object fieldValue) throws Exception {
        Field field = target.getClass().getSuperclass().getDeclaredField(fieldName); // Access superclass field
        field.setAccessible(true);
        field.set(target, fieldValue);
    }
}
