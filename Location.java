@Test
public void testInitLogoutSession() throws AuthException {
    // Mock the behavior of dependencies
    when(authStateMachineEngine.getAuthStateContext()).thenReturn(authStateContext);

    // Mock the required methods for createRequestContext
    HeaderInfo mockHeaderInfo = new HeaderInfo(); // Provide a mock or real instance if required
    AuthApplication mockAuthApp = new AuthApplication(); // Provide a mock or real instance if required

    // Mocking authStateContext behavior
    when(authStateContext.get(BaseAuthConstants.HEADER_INFO)).thenReturn(mockHeaderInfo);
    when(authStateContext.getAuthApplication()).thenReturn(mockAuthApp);

    // Execute the method under test
    logoutAction.initLogoutSession();

    // Verify interactions
    verify(authStateMachineEngine).getAuthStateContext();
    verify(auditSessionPersistor).setRequestContext(any(AuthRequestContext.class));
}
