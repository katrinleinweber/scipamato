package ch.difty.scipamato.web;

import java.util.Locale;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import ch.difty.scipamato.DateTimeService;
import ch.difty.scipamato.ScipamatoPublicApplication;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.checkboxx.CheckBoxX;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.select.BootstrapMultiSelect;

@SpringBootTest
@RunWith(SpringRunner.class)
public abstract class WicketTest {

    private static final String USERNAME = "testuser";
    private static final String PASSWORD = "secretpw";

    @Autowired
    private ScipamatoPublicApplication application;

    @Autowired
    private ApplicationContext applicationContextMock;

    @Autowired
    private DateTimeService dateTimeService;

    private WicketTester tester;

    public WebApplication getApplication() {
        return application;
    }

    public WicketTester getTester() {
        return tester;
    }

    public DateTimeService getDateTimeService() {
        return dateTimeService;
    }

    @Before
    public final void setUp() {
        ReflectionTestUtils.setField(application, "applicationContext", applicationContextMock);
        tester = new WicketTester(application);
        Locale locale = new Locale("en_US");
        tester.getSession().setLocale(locale);
        setUpHook();
        login(USERNAME, PASSWORD);
    }

    /**
     * override if needed
     */
    protected void setUpHook() {
    }

    private void login(String username, String password) {
        //        SecureWebSession session = (SecureWebSession) tester.getSession();
        //        session.signOut();
        //        tester.startPage(PublicPage.class);
        //        FormTester formTester = tester.newFormTester("form");
        //        formTester.setValue("username", username);
        //        formTester.setValue("password", password);
        //        formTester.submit();
        //        tester.assertNoErrorMessage();
        //        tester.assertRenderedPage(PublicPage.class);
    }

    protected void assertLabeledTextArea(String b, String id) {
        final String bb = b + ":" + id;
        getTester().assertComponent(bb + "Label", Label.class);
        getTester().assertComponent(bb, TextArea.class);
    }

    protected void assertLabeledTextField(String b, String id) {
        final String bb = b + ":" + id;
        getTester().assertComponent(bb + "Label", Label.class);
        getTester().assertComponent(bb, TextField.class);
    }

    protected void assertLabeledCheckBoxX(String b, String id) {
        final String bb = b + ":" + id;
        getTester().assertComponent(bb + "Label", Label.class);
        getTester().assertComponent(bb, CheckBoxX.class);
    }

    protected void assertLabeledMultiSelect(String b, String id) {
        final String bb = b + ":" + id;
        getTester().assertComponent(bb + "Label", Label.class);
        getTester().assertComponent(bb, BootstrapMultiSelect.class);
    }

}